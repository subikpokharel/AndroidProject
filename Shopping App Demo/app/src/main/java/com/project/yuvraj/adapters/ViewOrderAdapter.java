package com.project.yuvraj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.yuvraj.R;
import com.project.yuvraj.parsing.Cart;
import com.project.yuvraj.parsing.OrderParshing;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo on 6/14/2016.
 */
public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.CustomViewHolder> {


    ArrayList<OrderParshing> list;
    Context mContext;
    onOrderClickedListner monOrderClickedListner;

    public ViewOrderAdapter(Context context, ArrayList<OrderParshing> list) {

        this.list = list;
        this.mContext = context;
        monOrderClickedListner = (onOrderClickedListner) context;

    }
    @Override
    public ViewOrderAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_list, parent, false);
        CustomViewHolder cvh = new CustomViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(ViewOrderAdapter.CustomViewHolder holder, int position) {

        final OrderParshing orderParshing = list.get(position);

        holder.order_id.setText(orderParshing.getOrder_id());
        holder.order_date.setText(orderParshing.getOrder_date());
        holder.amount.setText("Rs "+orderParshing.getOrder_amount());
        holder.count.setText(orderParshing.getCount()+" items");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }






    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView order_id, order_date,amount, count;
        public LinearLayout container;



        CustomViewHolder(final View itemView) {
            super(itemView);
            container = (LinearLayout) itemView.findViewById(R.id.orderListContainer);
            order_id = (TextView) itemView.findViewById(R.id.orderId);
            order_date = (TextView) itemView.findViewById(R.id.orderDate);
            amount = (TextView) itemView.findViewById(R.id.ordetAmount);
            count = (TextView) itemView.findViewById(R.id.orderCount);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OrderParshing orderParshing = list.get(getLayoutPosition());
                    String order_id = orderParshing.getOrder_id();
                    String user_id = orderParshing.getUser_id();
                    String amount = orderParshing.getOrder_amount();
                    monOrderClickedListner.showClickedOrder(order_id,user_id, amount);
                }
            });

        }
    }

    public interface onOrderClickedListner{
        void showClickedOrder(String order_id,String user_id, String amount);
    }
}
