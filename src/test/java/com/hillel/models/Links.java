package com.hillel.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alpa on 2019-09-19
 */
public class Links {

    @SerializedName("addresses")
    @Expose
    private Addresses addresses;
    @SerializedName("cards")
    @Expose
    private Cards cards;
    @SerializedName("customer")
    @Expose
    private Customer_ customer;
    @SerializedName("self")
    @Expose
    private Self self;

    public Addresses getAddresses() {
        return addresses;
    }

    public void setAddresses(Addresses addresses) {
        this.addresses = addresses;
    }

    public Cards getCards() {
        return cards;
    }

    public void setCards(Cards cards) {
        this.cards = cards;
    }

    public Customer_ getCustomer() {
        return customer;
    }

    public void setCustomer(Customer_ customer) {
        this.customer = customer;
    }

    public Self getSelf() {
        return self;
    }

    public void setSelf(Self self) {
        this.self = self;
    }
}
