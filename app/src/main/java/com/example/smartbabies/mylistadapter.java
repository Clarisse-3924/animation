package com.example.smartbabies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class mylistadapter extends ArrayAdapter<listview> {
    Context context;
    int resource;
    List<listview>list;
    public mylistadapter(Context context, int resource, List<listview>list){
        super(context, resource, list);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource,null);
        TextView textViewName= view.findViewById(R.id.name);
        TextView textViewPrice=view.findViewById(R.id.price);
        TextView textViewCart=view.findViewById(R.id.cart);
        ImageView imageView=view.findViewById(R.id.image);

        listview list1= list.get(position);
        textViewName.setText(list1.getName());
        textViewPrice.setText((list1.getPrice()));
        textViewCart.setText(list1.getAddToCart());
        imageView.setImageDrawable(context.getResources().getDrawable(list1.getImage()));
        return view;

    }
}
