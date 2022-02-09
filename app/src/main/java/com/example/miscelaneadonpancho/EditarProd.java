package com.example.miscelaneadonpancho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditarProd extends AppCompatActivity {
    EditText  cdb, precio, costo;
    DBHelper dbHelper;
    TextView Nombre;
    Producto producto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_prod);
        Nombre = findViewById(R.id.Nombre);
        cdb=findViewById(R.id.CDB);
        precio = findViewById(R.id.Precio);
        costo = findViewById(R.id.Costo);
        dbHelper = new DBHelper(this);
        String nombre = getIntent().getStringExtra("Nombre");
         producto= dbHelper.BuscarProducto_nombre(nombre);
        Nombre.setText(producto.Nombre);
        cdb.setText(producto.CDB);
        precio.setText(producto.precio +"");
        costo.setText(producto.Costo.toString());
    }
    public void editarProd(View view){

        Producto producto = new Producto(cdb.getText().toString(), Nombre.getText().toString(), Float.parseFloat(precio.getText().toString()), Float.parseFloat(costo.getText().toString()));
        int i = dbHelper.EditarProd(producto);
        Toast.makeText(this, "Se cambiaron "+i+" productos", Toast.LENGTH_SHORT).show();
        finish();

    }
    public void borrarProd(View view){
        if(dbHelper.BuscarProducto_nombre(producto.Nombre)!=null){
            int borrar = dbHelper.borrar(producto.Nombre);
            Toast.makeText(this, "se borraron "+borrar+ "productos", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this, "no hay ningun producto con este nombre", Toast.LENGTH_SHORT).show();
        }
    }
}