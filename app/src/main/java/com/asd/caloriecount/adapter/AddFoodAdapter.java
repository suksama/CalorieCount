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
 * Created by Name name on 2017/2/14.
 */
public class AddFoodAdapter extends RecyclerView.Adapter<AddFoodAdapter.MyViewHolder>{

    List<Food> data;
    LayoutInflater inflater;
    OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public AddFoodAdapter(Context context,List<Food> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(inflater.inflate(R.layout.rv_addfood_items,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv_name.setText(data.get(position).getfName());
        holder.tv_calorie.setText(data.get(position).getfCalorie());
        if(mOnItemClickListener != null){
            holder.rl_food_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(view,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private final RelativeLayout rl_food_item;
        private final TextView tv_name;
        private final TextView tv_calorie;

        public MyViewHolder(View itemView) {
            super(itemView);
            rl_food_item = (RelativeLayout)itemView.findViewById(R.id.rl_food_item);
            tv_name = (TextView)itemView.findViewById(R.id.tv_name);
            tv_calorie = (TextView)itemView.findViewById(R.id.tv_calorie);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        //void onItemLongClick(View view,int position);
    }
}
