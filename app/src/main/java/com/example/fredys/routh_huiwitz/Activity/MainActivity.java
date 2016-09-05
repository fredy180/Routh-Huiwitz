package com.example.fredys.routh_huiwitz.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.fredys.routh_huiwitz.LogicaNegocio.ConfigurarTabla;
import com.example.fredys.routh_huiwitz.LogicaNegocio.Routh;
import com.example.fredys.routh_huiwitz.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtPolinomio;

    TableLayout tablaDatos;
    Button btOk;
    TextView tvInforme, ddd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtPolinomio = (EditText)findViewById(R.id.edtPolinomio);
        tablaDatos =(TableLayout)findViewById(R.id.tablaRouth) ;
        btOk = (Button)findViewById(R.id.btOk);
        tvInforme = (TextView)findViewById(R.id.tvInforme);
        btOk.setOnClickListener(this);
        ddd =(TextView)findViewById(R.id.ddd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            //Routh.obtenerPolinomio(edtPolinomio.getText().toString(), this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btOk:{
                Routh.iniciarRouth(this,edtPolinomio.getText().toString(),tablaDatos,tvInforme);
               // ddd.setText(Html.fromHtml("X <sup><small> 2 </small></sup>"));


                break;
            }
        }

    }
}
