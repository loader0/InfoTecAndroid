package com.example.infotec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

public class ProyectosServicioS extends AppCompatActivity {

    ListView listView;
    private String url = "";
    Boolean consultaExitosa;
    private ProgressDialog pDialog;
    String token;
    String jsonStr;

    ArrayList<String> nombreProyectoSS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectos_servicio_s);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView =(ListView) findViewById(R.id.listViewSS);

        new ProyectosServicioS.LlamarProyectoS().execute();

        if(!consultaExitosa)
        {
            return;
        }


        ArrayAdapter arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,nombreProyectoSS);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ejecutar(null);

            }
        });

    }

    private class LlamarProyectoS extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            consultaExitosa = false;
            super.onPreExecute();
            pDialog = new ProgressDialog(ProyectosServicioS.this);
            pDialog.setMessage("Procesando datos...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            DatosJson datosJson = new DatosJson();
            Gson gson = new Gson();

            datosJson.setToken(token);


            String json = gson.toJson(datosJson);

            try {
                String jsonStr = sh.makeServiceCall(url,json,1);

            }
            catch (Exception e)
            {

            }

            if(jsonStr != null && jsonStr != ""){
                try {
                    JSONObject obj = new JSONObject(json);


                    for(int i = 0; i < obj.length(); i++) {

                        nombreProyectoSS.add(String.valueOf(obj));
                    }
                    if(datosJson.getToken().length() == 0 || datosJson.getUser().length() == 0  || datosJson.getUserName().length() == 0 )
                    {
                        consultaExitosa = false;
                    }
                    else
                    {
                        consultaExitosa = true;
                    }
                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ProyectosServicioS.this,
                                    "Ocurrio un error, intente mas tarde",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProyectosServicioS.this,
                                "No se pudo obtener conexion con el servidor",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(pDialog.isShowing()){
                try {
                    pDialog.dismiss();
                } catch (RuntimeException ex) {
                }
            }

        }

    }



    public void ejecutar (View view){

        Intent i  = new Intent(this, InfoProyectoServicioS.class);
        startActivity(i);
    }
}
