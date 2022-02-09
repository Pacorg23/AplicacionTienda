package com.example.miscelaneadonpancho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper  {

    public static final String PRODUCTOS = "PRODUCTOS";
    public static final String CODIGO_DE_BARRAS = "CODIGO_DE_BARRAS";
    public static final String NOMBRE = "NOMBRE";
    public static final String PRECIO = "PRECIO";
    public static final String PROVEEDOR_ID = "PROVEEDOR_ID";
    public static final String COSTO = "COSTO";

    public DBHelper(@Nullable Context context) {
        super(context, "MiscelaneDonPancho.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE " + PRODUCTOS + "(" + CODIGO_DE_BARRAS + " VARCHAR(20) , " + NOMBRE + " VARCHAR(80) , " + PRECIO + " INT, " + COSTO + " INT," + PROVEEDOR_ID + " INT)";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean añadirProducto(Producto producto){

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NOMBRE, producto.Nombre);
        cv.put(CODIGO_DE_BARRAS, producto.CDB);
        cv.put(PRECIO,producto.precio );
        cv.put(COSTO, producto.Costo);
        cv.put(PROVEEDOR_ID, 0);
        long insert = db.insert(PRODUCTOS, null, cv);
        if (insert ==-1){
            return false;
        }
        return true;
    }
    public Producto BuscarProducto_nombre(String nombre){
        SQLiteDatabase db= this.getReadableDatabase();
        String query= "SELECT * FROM " + PRODUCTOS + " WHERE " + NOMBRE + " = '" + nombre + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            Producto producto = new Producto(cursor.getString(0), cursor.getString(1), cursor.getFloat(2), cursor.getFloat(3)  );
            /*cursor.close();
            db.close();*/
            return producto;
        }else{
            return null;
        }
    }
    public List<String> ObtenerBase(){
        SQLiteDatabase db= this.getReadableDatabase();
        String query= "SELECT " + NOMBRE + " FROM " + PRODUCTOS + ";";
        Cursor cursor = db.rawQuery(query, null);
        List<String> nombres= new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                nombres.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }else {
            return null;
        }
        return nombres;
    }
    public Producto BuscarProducto_codigo(String cdb){
        SQLiteDatabase db= this.getReadableDatabase();
        String query= "SELECT * FROM " + PRODUCTOS + " WHERE " + CODIGO_DE_BARRAS + " = '" + cdb + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            Producto producto = new Producto(cursor.getString(0), cursor.getString(1), cursor.getFloat(2), cursor.getFloat(3)  );
            /*cursor.close();
            db.close();*/
            return producto;
        }else{
            return null;
        }
    }
    public int EditarProd(Producto producto){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CODIGO_DE_BARRAS, producto.CDB);
        cv.put(NOMBRE, producto.Nombre);
        cv.put(PRECIO,producto.precio );
        cv.put(COSTO, producto.Costo);
        cv.put(PROVEEDOR_ID, 0);

        /*String query = "UPDATE " + PRODUCTOS + " SET "+ CODIGO_DE_BARRAS +" = '" + producto.CDB + "',"+ NOMBRE + " = '" +
                producto.Nombre +"'," + PRECIO + " = " + producto.precio + "," +COSTO +" = "+ producto.Costo +
                " WHERE " + CODIGO_DE_BARRAS + " = '"  + _cdb + "';";*/

         return db.update(PRODUCTOS, cv,NOMBRE + " = '" +producto.Nombre+"'",null );


    }
    public boolean isEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ PRODUCTOS+";";
        Cursor cursor = db.rawQuery(query, null);
        return !cursor.moveToFirst();
    }
    public  int borrar(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(PRODUCTOS, NOMBRE + " = '"+ nombre + "'", null);
    }
}
