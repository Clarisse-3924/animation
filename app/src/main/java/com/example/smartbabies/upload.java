package com.example.smartbabies;

import com.google.firebase.database.Exclude;

public class upload {
    private String Pname;
    private String Description;
    private String Price;
    private String mImageUrl;
    private String mKey;

    public upload() {

    }

    public upload(String pname, String description, String price, String ImageUrl) {
        if(pname.trim().equals("")){
            pname="no name";
        }


        Pname = pname;
        Description = description;
        Price = price;
        mImageUrl = ImageUrl;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImageUrl() {
        return mImageUrl;
    }


    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
    @Exclude
    public String getKey(){
        return mKey;

    }
    @Exclude
    public void setKey(String key){
        mKey = key;
    }
}

