package com.wyble.procesagro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wyble.procesagro.models.Tramite;

import java.io.Serializable;


public class Call_Form4Activity extends ActionBarActivity {

    private Button form4_next;
    private EditText bovino1, bovino2, bovino3, bovino4;
    Context context= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__form4);

        Serializable dataFromPaso3 = getIntent().getSerializableExtra("TRAMITE_PASO3");
        final Tramite tramite = (Tramite) dataFromPaso3;

        bovino1 = (EditText) findViewById(R.id.bovino1);
        bovino2 = (EditText) findViewById(R.id.bovino2);
        bovino3 = (EditText) findViewById(R.id.bovino3);
        bovino4 = (EditText) findViewById(R.id.bovino4);

        form4_next = (Button) findViewById(R.id.form4_next);
        form4_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String bovino1_v= bovino1.getText().toString();
                final String bovino2_v= bovino1.getText().toString();
                final String bovino3_v= bovino3.getText().toString();
                final String bovino4_v= bovino4.getText().toString();
                if(bovino1_v.equals("") || bovino2_v.equals("") || bovino3_v.equals("") || bovino4_v.equals("")){

                    Toast.makeText(context, "Todos los campos son requeridos.", Toast.LENGTH_SHORT).show();
                }else{
                    tramite.paso4(Integer.parseInt(bovino1_v),
                            Integer.parseInt(bovino2_v),
                            Integer.parseInt(bovino3_v),
                            Integer.parseInt(bovino4_v));
                    Intent intent = new Intent(Call_Form4Activity.this, Call_Form5Activity.class);
                    intent.putExtra("TRAMITE_PASO4", tramite);
                    startActivity(intent);
                }
            }
        });
    }
}
