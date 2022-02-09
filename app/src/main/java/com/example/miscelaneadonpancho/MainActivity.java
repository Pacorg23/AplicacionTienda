package com.example.miscelaneadonpancho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DBHelper(this);
    }

   public void cambio_checador(View view) {
        Intent intent = new Intent(this, ChecadorDePrecios.class);
        startActivity(intent);

    }
    public void cambio_tickets(View view) {
        if (!db.isEmpty()){
            return;
        }
        try {
            int fallos=0;
            String data ="";
            StringBuffer sb = new StringBuffer();
            InputStream is = this.getResources().openRawResource(R.raw.mientras);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            for (int j =0 ; j<476; j++){
                Producto prod = adaptador(reader.readLine());
                if (prod==null){
                    fallos++;
                }
                else if (!db.añadirProducto(prod)){
                    fallos++;
                }
            }

            Toast.makeText(this, fallos + "", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void cambio_productos(View view) {
        Intent intent = new Intent(this, AgregarProducto.class);
        startActivity(intent);
    }
    public Producto adaptador (String linea){
        String Nombre = "", cdb;
        Float precio;
        int i=1;
        String[] result= linea.split("\\s");
        /*for (String s :
                result) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }*/

        while (!result[i].equals(">")){
            Nombre = Nombre + " " + result[i];
            i++;
        }
        i++;
        if (result.length-i==1){
            if (result[result.length-1].length()<6){
                return new Producto("por escanear", Nombre, Float.parseFloat(result[result.length-1]), (float) 0);
            }
            else {
                return new Producto(result[result.length-1], Nombre, (float) -1, (float) 0);
            }
        }
        else if(result.length-i==0){
           return null;
        }
        else {
            return new Producto(result[result.length-2], Nombre, Float.parseFloat(result[result.length-1]), (float) 0);
        }

    }
}