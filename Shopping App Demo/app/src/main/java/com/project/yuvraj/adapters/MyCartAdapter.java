package com.project.yuvraj.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.yuvraj.R;
import com.project.yuvraj.parsing.Cart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 5/19/2016.
 */
public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CustomViewHolder> {

    ArrayList<Cart> list;
    Context mContext;

    public MyCartAdapter(Context context, ArrayList<Cart> list) {

        this.list = list;
        this.mContext = context;

    }

    @Override
    public MyCartAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_cart, parent, false);
        CustomViewHolder cvh = new CustomViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(MyCartAdapter.CustomViewHolder holder, int position) {

        final Cart mCart = list.get(position);

        Picasso.with(mContext).load(mCart.getUrl()).into(holder.imageView);
        holder.txtName.setText(mCart.getName());
        holder.txtPrice.setText(mCart.getPrice());
        holder.txtQuantity.setText(mCart.getQuantity());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName,txtPrice,txtQuantity;
        public ImageView imageView;


        CustomViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ImageViewCart);
            txtName = (TextView) itemView.findViewById(R.id.TextViewCart);
            txtPrice = (TextView) itemView.findViewById(R.id.TextViewPrice);
            txtQuantity = (TextView) itemView.findViewById(R.id.TextViewQuantity);

        }
    }
}
