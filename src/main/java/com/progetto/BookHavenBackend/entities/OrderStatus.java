package com.progetto.BookHavenBackend.entities;

public enum OrderStatus {
    PENDING("Pending"),
    PAID("Paid"), //Ordine Pagato
    PROCESSING("Processing"), //Ordine in fase di elaborazione
    SHIPPED("Shipped"), //Ordine spedito
    DELIVERED("Delivered"), //Ordine consegnato
    CANCELED("Canceled"); //Ordine annullato


    private final String status;

    OrderStatus(String status){
        this.status= status;
    }

    public String getStatus(){
        return status;
    }
}
