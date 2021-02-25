package com.example.basicbankingapp;

public class Customer {
    String mId, mName, mBalance;

    public Customer() {

    }

    public Customer(String id, String name, String balance) {
        this.mId = id;
        this.mName = name;
        this.mBalance = balance;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getBalance() {
        return mBalance;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setBalance(String balance) {
        this.mBalance = balance;
    }
}
