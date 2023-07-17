package com.example.contactlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder>{

    private Context context;
    private List<ModelContact> contactList;

    public AdapterContact(Context context, List<ModelContact> contactList){
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contact_item,parent,false);
        ContactViewHolder vh = new ContactViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ModelContact modelContact =contactList.get(position);
        String name= modelContact.getName();
        holder.ContactName.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ContactDetailsss.class);
                intent.putExtra("ContactName",name);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{
        ImageView contactImage,contactDial;
        TextView ContactName;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            ContactName=itemView.findViewById(R.id.contact_name);
        }
    }
}
