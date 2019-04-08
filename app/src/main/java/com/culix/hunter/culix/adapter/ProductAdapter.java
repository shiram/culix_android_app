package com.culix.hunter.culix.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.culix.hunter.culix.R;

import com.culix.hunter.culix.activity.ViewMoreActivity;
import com.culix.hunter.culix.config.Config;
import com.culix.hunter.culix.data.ProductData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private Context context;
    private List<ProductData> list;
    private ArrayList<ProductData> arrayList;

    public ProductAdapter(Context context, List<ProductData> list) {
        this.context = context;
        this.list = list;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_items, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ProductData productData = list.get(position);
        holder.product_name.setText(productData.getProduct_name());
        holder.product_price.setText(productData.getProduct_price()+" Shs");
        holder.product_category.setText(productData.getProduct_category());
        //holder.product_date_created.setText(productData.getCreated_at());
        Picasso.with(context).load(Config.url+"/"+productData.getProduct_image()).into(holder.product_image);


        holder.view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int id = list.get(position).getProduct_id();
                final String name = list.get(position).getProduct_name();
                final String price = list.get(position).getProduct_price();
                final String category = list.get(position).getProduct_category();
                final String created_at = list.get(position).getCreated_at();
                final String location = list.get(position).getBusiness_country()+", "+list.get(position).getBusiness_city()+", "+list.get(position).getBusiness_address();
                final String description = list.get(position).getProduct_description();
                final String image = list.get(position).getProduct_image();

                Intent intent = new Intent(context, ViewMoreActivity.class);
                intent.putExtra("product_id", id);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("category", category);
                intent.putExtra("created_at", created_at);
                intent.putExtra("location", location);
                intent.putExtra("description", description);
                intent.putExtra("image", image);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        AppCompatTextView product_name, product_price, product_category, product_date_created, view_more;
        AppCompatImageView product_image;

        public ViewHolder(View view){
            super(view);
            product_name = view.findViewById(R.id.product_name);
            product_price = view.findViewById(R.id.product_price);
            product_category = view.findViewById(R.id.product_category);
           // product_date_created = view.findViewById(R.id.product_date_created);
            view_more = view.findViewById(R.id.view_more);
            product_image = view.findViewById(R.id.product_image);
        }

        @Override
        public void onClick(View v) {


        }
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if(charText.length() == 0){
            list.addAll(arrayList);
        }else{
            for(ProductData productData : arrayList){
                if(productData.getProduct_category().toLowerCase(Locale.getDefault()).contains(charText) || productData.getProduct_name().toLowerCase(Locale.getDefault()).contains(charText)  || productData.getProduct_price().toLowerCase(Locale.getDefault()).contains(charText) ){
                    list.add(productData);
                }
            }
        }

        notifyDataSetChanged();
    }
}
