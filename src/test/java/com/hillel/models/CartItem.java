package com.hillel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alpa on 9/22/19
 */
public class CartItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("itemId")
    @Expose
    private String itemId;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("unitPrice")
    @Expose
    private double unitPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
