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


public class Call_Form2Activity extends ActionBarActivity {

    private Button form2_next;
    private EditText municipio, departamento;
    Context context= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__form2);

        Serializable dataFromPaso1 = getIntent().getSerializableExtra("TRAMITE_PASO1");
        final Tramite tramite = (Tramite) dataFromPaso1;

        municipio = (EditText) findViewById(R.id.municipio);
        departamento = (EditText) findViewById(R.id.departamento);

        form2_next = (Button) findViewById(R.id.form2_next);
        form2_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String municipio_v= municipio.getText().toString();
                final String departamento_v= departamento.getText().toString();
                if(municipio_v.equals("") || departamento_v.equals("")){

                    Toast.makeText(context, "Todos los campos son requeridos.", Toast.LENGTH_SHORT).show();
                }else{
                    tramite.paso2(municipio_v, departamento_v);
                    Intent intent = new Intent(Call_Form2Activity.this, Call_Form3Activity.class);
                    intent.putExtra("TRAMITE_PASO2", tramite);
                    startActivity(intent);
                }
            }
        });

    }
}
