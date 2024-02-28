package com.example.ishantest2;

public class Expense {
    private int expid;
    private String expenseType;
    private int amount;
    private String date;

    public Expense(int expid, String expenseType, int amount, String date) {
        this.expid = expid;
        this.expenseType = expenseType;
        this.amount = amount;
        this.date = date;
    }

    public int getExpid() {
        return this.expid;
    }

    public void setExpid(int expid) {
        this.expid = expid;
    }

    public String getExpenseType() {
        return this.expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

