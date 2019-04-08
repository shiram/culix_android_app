package com.culix.hunter.culix.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.culix.hunter.culix.R;
import com.culix.hunter.culix.config.Config;
import com.culix.hunter.culix.data.ProductData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductManageAdapter extends RecyclerView.Adapter<ProductManageAdapter.ViewHolder> {

    private Context context;
    private List<ProductData> list;
    private ArrayList<ProductData> arrayList;

    public ProductManageAdapter(Context context, List<ProductData> list) {
        this.context = context;
        this.list = list;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_items_manage, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ProductData productData = list.get(position);
        holder.product_name.setText(productData.getProduct_name());
        holder.product_price.setText(productData.getProduct_price()+" Shs");
        holder.product_date_created.setText(productData.getCreated_at());
        Picasso.with(context).load(Config.url+"/"+productData.getProduct_image()).into(holder.product_image);

/* Handle VIEW mORE hERE
        holder.make_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int id = list.get(position).getId();
                final String contract_item = list.get(position).getContract_item();
                final String contract_price = list.get(position).getContract_price();
                final String country = list.get(position).getCountry();
                final String city = list.get(position).getCity();
                final String contract_cost = list.get(position).getContract_cost();
                final String amount_needed = list.get(position).getAmount_needed();
                final String profit_to_investor = list.get(position).getProfit_to_investor();
                final String period = list.get(position).getPeriod();

                Intent intent = new Intent(context, MakeOffer.class);
                intent.putExtra("contract_id", id);
                intent.putExtra("contract_item", contract_item);
                intent.putExtra("contract_price", contract_price);
                intent.putExtra("country", country);
                intent.putExtra("city", city);
                intent.putExtra("contract_cost", contract_cost);
                intent.putExtra("amount_needed", amount_needed);
                intent.putExtra("profit", profit_to_investor);
                intent.putExtra("period", period);

                context.startActivity(intent);
            }
        });
        */

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        AppCompatTextView product_name, product_price, product_date_created;
        AppCompatImageView product_image;
        AppCompatTextView edit_product, delete_product;

        public ViewHolder(View view){
            super(view);
            product_name = view.findViewById(R.id.product_name);
            product_price = view.findViewById(R.id.product_price);
            product_date_created = view.findViewById(R.id.date_created);
            product_image = view.findViewById(R.id.product_image);
            edit_product = view.findViewById(R.id.edit_product);
            delete_product = view.findViewById(R.id.delete_product);
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
