package com.wyble.procesagro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wyble.procesagro.helpers.DB;
import com.wyble.procesagro.models.Departamento;
import com.wyble.procesagro.models.Municipio;
import com.wyble.procesagro.models.Tramite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Call_Form2Activity extends ActionBarActivity {

    private Button form2_next;
    //private EditText municipio, departamento;
    private Spinner municipio, departamento;
    Context context= this;
    private static final String TRAMITE_TABLE = "tramites";
    private String iddepartamento = null;
    private DB db;

    private ArrayList<HashMap> tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call__form2);

        Serializable dataFromPaso1 = getIntent().getSerializableExtra("TRAMITE_PASO1");
        final Tramite tramite = (Tramite) dataFromPaso1;

        tables = new ArrayList<HashMap>();

        db = new DB(this,tables);

        departamento = (Spinner) findViewById(R.id.departamento);
        municipio = (Spinner) findViewById(R.id.municipio);

        ArrayAdapter<Municipio> adaptadorMun =
                new ArrayAdapter<Municipio>(Call_Form2Activity.this, android.R.layout.simple_list_item_1,getMunicipio());

        municipio.setAdapter(adaptadorMun);

        ArrayAdapter<Departamento> adaptador =
                new ArrayAdapter<Departamento>(this, android.R.layout.simple_list_item_1,getDepartamentos());

        departamento.setAdapter(adaptador);
       // departamento.setOnItemSelectedListener(new SpinnerLis());



        //ArrayList arrayDepa = getDepartamentos();

        //municipio.setText(tramite.getMunicipio());
        //departamento.setText(tramite.getDepartamento());
        //municipio.setText(tramite.getMunicipio());
        //departamento.setText(tramite.getDepartamento());

        form2_next = (Button) findViewById(R.id.form2_next);
        form2_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //final String municipio_v = municipio.getText().toString().trim();
                //final String departamento_v = departamento.getText().toString().trim();
                String municipio_v = municipio.getSelectedItem().toString();
                String departamento_v = departamento.getSelectedItem().toString();
                Log.d("//log", "//log" + municipio_v + " - " + departamento_v);

                if(municipio_v.equals("Seleccione un municipio") || departamento_v.equals("Seleccione un departamento")){
                    Toast.makeText(context, "Seleccione un departamento y municipio.", Toast.LENGTH_SHORT).show();
                }else{
                    tramite.paso2(municipio_v, departamento_v);

                    HashMap hmTramite = new HashMap();
                    hmTramite.put(TRAMITE_TABLE, tramite.toJSONArray());

                    ArrayList<HashMap> tables = new ArrayList<HashMap>();
                    tables.add(hmTramite);
                    DB db = new DB(context, tables);
                    db.updateData(TRAMITE_TABLE, tramite.toJSONArray(), tramite.getId());

                    Intent intent = new Intent(Call_Form2Activity.this, Call_Form3Activity.class);
                    intent.putExtra("TRAMITE_PASO2", tramite);
                    startActivity(intent);
                }
            }
        });

    }

    private ArrayList<Departamento> getDepartamentos() {
        ArrayList departamentos = new ArrayList();
        ArrayList<HashMap> data = db.getAllData("departamentos");
        for (HashMap d : data) {
            departamentos.add(new Departamento(
                    Integer.parseInt(d.get("id").toString()),
                    d.get("nombreDepartamento").toString()
            ));
        }

        this.db.close();
        return departamentos;
    }


    private ArrayList<Municipio> getMunicipio() {
        ArrayList municipios = new ArrayList();
        ArrayList<HashMap> data = db.getAllData("municipios");
        for (HashMap d : data) {
            municipios.add(new Municipio(
                    Integer.parseInt(d.get("autoId").toString()),
                    d.get("nombreMunicipio").toString(),
                    d.get("departamento").toString()
            ));
        }

        this.db.close();
        return municipios;
    }


    private ArrayList<Municipio> getMunicipio2(String iddepartamento) {
        ArrayList municipios = new ArrayList();
        ArrayList<HashMap> data = db.getDataByName("municipios","departamento", iddepartamento);
        for (HashMap d : data) {
            municipios.add(new Municipio(
                    Integer.parseInt(d.get("autoId").toString()),
                    d.get("nombreMunicipio").toString(),
                    d.get("departamento").toString()
            ));
        }

        this.db.close();
        return municipios;
    }

    public class SpinnerLis implements AdapterView.OnItemSelectedListener {
        private long itemIdAtPosition;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            TextView tex = (TextView) findViewById(R.id.tituloUbicacion);

            tex.setText("has seleccionado "+ departamento.getSelectedItem().toString());

            ArrayAdapter<Municipio> adaptadorMun =
                    new ArrayAdapter<Municipio>(Call_Form2Activity.this, android.R.layout.simple_list_item_1,getMunicipio2("2"));

            municipio.setAdapter(adaptadorMun);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}


