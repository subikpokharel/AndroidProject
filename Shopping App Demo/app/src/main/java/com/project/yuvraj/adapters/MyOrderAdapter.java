package com.project.yuvraj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.yuvraj.R;
import com.project.yuvraj.database.DatabaseHelper;
import com.project.yuvraj.parsing.Cart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo on 6/12/2016.
 */
public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.CustomViewHolder> {


    ArrayList<Cart> list;
    Context mContext;
    String id;

    public MyOrderAdapter(Context context, ArrayList<Cart> list, String id) {

        this.list = list;
        this.mContext = context;
        this.id = id;

    }


    @Override
    public MyOrderAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_cart, parent, false);
        CustomViewHolder cvh = new CustomViewHolder(v);
        return cvh;

    }


    @Override
    public void onBindViewHolder(MyOrderAdapter.CustomViewHolder holder, int position) {

        final Cart mCart = list.get(position);

        Picasso.with(mContext).load(mCart.getUrl()).into(holder.imageView);
        holder.txtName.setText(mCart.getName());
        holder.txtPrice.setText(mCart.getPrice());
        holder.txtQuantity.setText(mCart.getQuantity());
        holder.relativeLayoutEdit.setVisibility(View.GONE);
        holder.relativeLayoutRemove.setVisibility(View.GONE);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, txtQuantity,txtPrice;
        public ImageView imageView;
        public RelativeLayout relativeLayoutEdit, relativeLayoutRemove;


        CustomViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ImageViewCart);
            txtName = (TextView) itemView.findViewById(R.id.TextViewCart);
            txtQuantity = (TextView) itemView.findViewById(R.id.TextViewQuantity);
            txtPrice = (TextView) itemView.findViewById(R.id.TextViewPrice);
            relativeLayoutEdit = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutEdit);
            relativeLayoutRemove = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutRemove);

        }
    }
}
