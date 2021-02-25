package com.example.basicbankingapp;

public class Transaction {
    String mSenderName, mReceiverName, mTransAmount, mTransDate, mTransStatus;

    public Transaction() {

    }

    public Transaction(String transDate, String senderName, String receiverName, String transAmount, String transStatus) {
        this.mSenderName = senderName;
        this.mReceiverName = receiverName;
        this.mTransAmount = transAmount;
        this.mTransDate = transDate;
        this.mTransStatus = transStatus;
    }

    public String getSenderName() {
        return mSenderName;
    }

    public String getReceiverName() {
        return mReceiverName;
    }

    public String getTransAmount() {
        return mTransAmount;
    }

    public String getTransDate() {
        return mTransDate;
    }

    public String getTransStatus() { return mTransStatus; }

    public void setSenderName(String sName) { this.mSenderName = sName; }

    public void setReceiverName(String rName) { this.mReceiverName = rName; }

    public void setTransAmount(String amount) {
        this.mTransAmount = amount;
    }

    public void setTransDate(String date) { this.mTransDate = date; }

    public void setTransStatus(String status) { this.mTransStatus = status; }
}
