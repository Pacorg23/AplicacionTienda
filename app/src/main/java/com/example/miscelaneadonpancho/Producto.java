package com.example.miscelaneadonpancho;

public class Producto {
    public  String Nombre;
    public String CDB;
    public Float precio;
    public Float Costo;

    public Producto(String CDB, String nombre, Float precio, Float costo) {
        this.CDB = CDB;
        Nombre = nombre;
        this.precio = precio;
        Costo = costo;
    }
}
