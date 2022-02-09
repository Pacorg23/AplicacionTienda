package com.example.miscelaneadonpancho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ChecadorDePrecios extends AppCompatActivity {
    EditText CDB;
    TextView Precio ;
    AutoCompleteTextView ACTV;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checador_de_precios);
        CDB = findViewById(R.id.codigo_de_barras);
        DBHelper   dbHelper =new DBHelper(this);
        Precio = findViewById(R.id.Precio);

        ACTV=findViewById(R.id.producto);
        ACTV.setThreshold(1);

        List<String> list= dbHelper.ObtenerBase();

        adapter = new ArrayAdapter<String>(ChecadorDePrecios.this, android.R.layout.simple_dropdown_item_1line, list);
        ACTV.setAdapter(adapter);
        ACTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Producto producto = dbHelper.BuscarProducto_nombre(adapter.getItem(i));
                Toast.makeText(ChecadorDePrecios.this, producto.Nombre, Toast.LENGTH_SHORT).show();
                if (producto == null){
                    Toast.makeText(ChecadorDePrecios.this, "fallo", Toast.LENGTH_SHORT).show();
                    return;
                }
                Precio.setText(producto.precio + "");
                CDB.setText(producto.CDB);

            }
        });
        CDB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ACTV.getText().toString()!=""){
                    return;
                }
                if (editable.toString().length()<9){
                    return;
                }
                else {

                    Producto producto = dbHelper.BuscarProducto_codigo(editable.toString());
                    if (producto==null){
                        Toast.makeText(ChecadorDePrecios.this, "falta", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ACTV.setText(producto.Nombre);
                        Precio.setText(producto.precio+"");
                    }
                }
            }
        });

    }
    public void Limpiar(View view){
        CDB.setText("");
        Precio.setText("");
        ACTV.setText("");
    }
    public void editarProd(View view){
        String Nombre = ACTV.getText().toString();
        String cdb = CDB.getText().toString();
        if(!Nombre.isEmpty()){
            Intent intent = new Intent(this, EditarProd.class);
            intent=intent.putExtra("Nombre", Nombre);
            startActivity(intent);
        }

        else{
            Toast.makeText(ChecadorDePrecios.this, "No hay ningun producto con estos datos", Toast.LENGTH_LONG ).show();
        }


    }


}