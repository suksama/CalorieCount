package com.asd.caloriecount.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asd.caloriecount.R;
import com.asd.caloriecount.model.Food;

import java.util.List;

/**
 * Created by Name name on 2017/2/7.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {
    private List<Food> data;
    LayoutInflater inflater;
    OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public RecordAdapter(Context context, List<Food> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(inflater.inflate(R.layout.recycleview_items, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_food_name.setText(data.get(i).getfName());
        myViewHolder.tv_food_weight.setText(data.get(i).getWeight()+" g");
        myViewHolder.tv_food_calorie.setText(String.valueOf(Float.parseFloat(data.get(i).getfCalorie()) * (Float.parseFloat(data.get(i).getWeight()))/100));
        if(mOnItemClickListener != null){
            myViewHolder.rl_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = myViewHolder.getLayoutPosition();
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


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_food_weight;
        TextView tv_food_calorie;
        TextView tv_food_name;
        RelativeLayout rl_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_food_name = (TextView) itemView.findViewById(R.id.tv_food_name);
            tv_food_weight = (TextView) itemView.findViewById(R.id.tv_food_weight);
            tv_food_calorie = (TextView) itemView.findViewById(R.id.tv_food_calorie);
            rl_item = (RelativeLayout) itemView.findViewById(R.id.rl_item);

        }
    }

    public interface OnItemClickListener{
        //void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }
}
