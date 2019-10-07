package com.hillel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alpa on 2019-09-19
 */
public class Embedded {

    @SerializedName("customer")
    @Expose
    private List<Customer> customer = null;

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }
}
