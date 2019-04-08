package com.culix.hunter.culix.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.culix.hunter.culix.R;
import com.culix.hunter.culix.config.Config;
import com.culix.hunter.culix.data.Accounts;
import com.culix.hunter.culix.data.ProductData;
import com.culix.hunter.culix.network.Uploader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {

    private Context context;
    private List<Accounts> list;
    private ArrayList<Accounts> arrayList;

    public AccountsAdapter(Context context, List<Accounts> list) {
        this.context = context;
        this.list = list;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.accounts, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Accounts accounts = list.get(position);
        holder.user_name.setText(accounts.getFirstname()+" "+accounts.getLastname());
        holder.user_email.setText(accounts.getEmail());

        holder.grant_rights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int id = list.get(position).getUser_id();
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("Adding...");
                progressDialog.show();
                new Uploader(context,progressDialog).makeMaketer(id);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        AppCompatTextView user_name, user_email, grant_rights;
        public ViewHolder(View view){
            super(view);
            user_name = view.findViewById(R.id.user_name);
            user_email = view.findViewById(R.id.user_email);
            grant_rights = view.findViewById(R.id.grant_rights);
        }

        @Override
        public void onClick(View v) {


        }
    }

}
