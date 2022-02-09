package com.example.miscelaneadonpancho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarProducto extends AppCompatActivity {

    public EditText nombre, cdb, precio, costo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        nombre=findViewById(R.id.Nombre);
        cdb=findViewById(R.id.CDB);
        precio= findViewById(R.id.Precio);
        costo= findViewById(R.id.Costo);
    }
    public void añadirProducto(View view){
        String _nombre, _cdb, _precio, _costo;
        _nombre=nombre.getText().toString();
        _cdb=cdb.getText().toString();
        if (_cdb.isEmpty()){
            Toast.makeText(AgregarProducto.this, "Falta el codigo de barras", Toast.LENGTH_LONG).show();
            return;
        }
        _precio=precio.getText().toString();
        if (_cdb.isEmpty()){
            Toast.makeText(AgregarProducto.this, "Falta el precio", Toast.LENGTH_LONG).show();
            return;
        }
        _costo=costo.getText().toString();
        Producto producto;
        if (_costo.isEmpty()){
            producto = new Producto(_cdb,_nombre, Float.parseFloat(_precio), Float.parseFloat("0"));
        }
        else {
            producto= new Producto(_cdb,_nombre, Float.parseFloat(_precio), Float.parseFloat(_costo));
        }

        DBHelper dbHelper = new DBHelper(AgregarProducto.this);
        boolean b = dbHelper.añadirProducto(producto);
        if (b){
            Toast.makeText(AgregarProducto.this, "Producto agregado", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(AgregarProducto.this, "Producto no agregado", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}