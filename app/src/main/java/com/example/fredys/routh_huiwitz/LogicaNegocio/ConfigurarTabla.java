package com.example.fredys.routh_huiwitz.LogicaNegocio;

import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.fredys.routh_huiwitz.R;

/**
 * Created by ing. Fredys vergara on 26/06/2016.
 */
public class ConfigurarTabla {

    public void agregarCabeceras( TableLayout tabladatos,int numeroColunas, Context context){
        tabladatos.removeAllViews();
        TableRow fila=null;
        TextView cabecera;
        fila = new TableRow(context);
        for (int i = 0; i <numeroColunas-1 ; i++) {
            cabecera=new TextView(context);
            if(i==00)
                cabecera.setText("S^n      ");
            else
                cabecera.setText("    -   -   -    ");
            cabecera.setBackgroundResource(R.drawable.cabeceras);
            cabecera.setPadding(0, 0, 5, 0);
            fila.addView(cabecera);// se agraga la celda a la fila
        }
        tabladatos.addView(fila);// se agrega la fila a la tabla
    }
    public void agregarFilas(TableLayout tabladatos,Context context,String [][]dato){

        TableRow fila=null;
        TextView celda;
        for (int i = 0; i <dato.length ; i++) {
            fila=new TableRow(context);
            for (int j = 0; j <dato[i].length-1 ; j++) {
                celda=new TextView(context);
                if(dato[i][j] != null)
                    celda.setText(""+dato[i][j]);
                celda.setPadding(0, 0, 5, 0);
                celda.setBackgroundResource(R.drawable.celdas);
                fila.addView(celda);
            }
            tabladatos.addView(fila);
        }
    }
}

