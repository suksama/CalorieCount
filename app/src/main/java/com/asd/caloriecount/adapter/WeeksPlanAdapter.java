package com.asd.caloriecount.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asd.caloriecount.R;
import com.asd.caloriecount.model.WeeksPlan;

import java.util.List;

/**
 * Created by Name name on 2017/2/10.
 */
public class WeeksPlanAdapter extends RecyclerView.Adapter<WeeksPlanAdapter.MyViewHolder>{
    private List<WeeksPlan> data;
    LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public WeeksPlanAdapter(Context context, List<WeeksPlan> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(inflater.inflate(R.layout.rv_weeksplan_items,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv_week_no.setText(data.get(position).getWeekNo());
        holder.tv_week_data.setText(data.get(position).getWeekDate());
        holder.tv_week_plan.setText(data.get(position).getWeekPlan());
        if(data.get(position).isPass()){
            if(data.get(position).isSaved()){
                holder.rv_weekplan_items.setBackgroundResource(R.color.saved);
                holder.rv_weekplan_items.setEnabled(false);
            }else{
                holder.rv_weekplan_items.setBackgroundResource(R.color.white);
            }
        }else{
            holder.rv_weekplan_items.setBackgroundResource(R.color.darker);
            holder.rv_weekplan_items.setEnabled(false);
        }

        if(mOnItemClickListener != null){
            holder.rv_weekplan_items.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(view,position);
                }
            });

            holder.rv_weekplan_items.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(view,position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView tv_week_no;
        private final TextView tv_week_data;
        private final TextView tv_week_plan;
        private final LinearLayout rv_weekplan_items;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_week_no = (TextView)itemView.findViewById(R.id.tv_week_no);
            tv_week_data = (TextView)itemView.findViewById(R.id.tv_week_date);
            tv_week_plan = (TextView)itemView.findViewById(R.id.tv_week_plan);
            rv_weekplan_items = (LinearLayout)itemView.findViewById(R.id.rv_weekplan_item);
        }
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
}
