
package com.model;

import java.util.ArrayList;



public class Line {
    private String item;
    private double price;
    private int count;
    private Invoice invoice;
    public Line() {
    }



    public Line(String item, double price, int count, Invoice invoice) {
        this.item = item;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

  

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalOfItem(){
        return count*price;
    }
    @Override
    public String toString() {
        return "Line{" + "num=" + invoice.getNum() + ", item=" + item + ", price=" + price + ", count=" + count + ", invoice=" + invoice + '}';
    }
    

    public Invoice getInvoice() {
        return invoice;
    }
    
    public String getCSVFile(){
        return invoice.getNum() + "," + item +"," +price+"," + count;
        
    }

    
    
    
    
}
