package com.culix.hunter.culix.data;

import java.util.Date;

public class ProductData {

    private int product_id;
    private int user_id;
    private String product_name;
    private String product_price;
    private String product_description;
    private String product_category;
    private String product_image;
    private String business_country;
    private String business_city;
    private String business_address;
    private String location_lat;
    private String location_lgt;
    private String created_at;

    public ProductData(int product_id, int user_id, String product_name, String product_price, String product_description, String product_category, String product_image, String business_country, String business_city, String business_address, String location_lat, String location_lgt, String created_at) {
        this.product_id = product_id;
        this.user_id = user_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_description = product_description;
        this.product_category = product_category;
        this.product_image = product_image;
        this.business_country = business_country;
        this.business_city = business_city;
        this.business_address = business_address;
        this.location_lat = location_lat;
        this.location_lgt = location_lgt;
        this.created_at = created_at;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getBusiness_country() {
        return business_country;
    }

    public void setBusiness_country(String business_country) {
        this.business_country = business_country;
    }

    public String getBusiness_city() {
        return business_city;
    }

    public void setBusiness_city(String business_city) {
        this.business_city = business_city;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public String getLocation_lgt() {
        return location_lgt;
    }

    public void setLocation_lgt(String location_lgt) {
        this.location_lgt = location_lgt;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
