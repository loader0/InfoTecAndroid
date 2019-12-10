package com.example.infotec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

public class Residencia extends AppCompatActivity {

    TextView status;
    TextView proyecto;
    TextView desc;
    TextView asesor;
    private String url = "";
    String proStatus;
    String alumProyecto;
    String proDesc;
    String alumAsesor;
    Boolean consultaExitosa;
    private ProgressDialog pDialog;
    String token;
    String jsonStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residencia);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        status = findViewById(R.id.status);
        proyecto = findViewById(R.id.nomP);
        desc = findViewById(R.id.desc);
        asesor = findViewById(R.id.nomAsesor);

        proStatus = String.valueOf(status.getText());
        alumProyecto = String.valueOf(proyecto.getText());
        proDesc = String.valueOf(desc.getText());
        alumAsesor = String.valueOf(asesor.getText());

        new Residencia.LlamarProyecto().execute();

        if(!consultaExitosa)
        {
            return;
        }

    }

    private class LlamarProyecto extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            consultaExitosa = false;
            super.onPreExecute();
            pDialog = new ProgressDialog(Residencia.this);
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
                    Alumno alumno = new Alumno();

                    alumProyecto = obj.getString(alumProyecto);
                    alumno.setNombre(obj.getString("alumnoNom"));

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
                            Toast.makeText(Residencia.this,
                                    "Ocurrio un error, intente mas tarde",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Residencia.this,
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

    @Override
    public boolean onCreateOptionsMenu(Menu primerMenu){

        getMenuInflater().inflate(R.menu.menu_residencia, primerMenu);
        return true;
    }
    public void ejecutar (View view){

        Intent i  = new Intent(this, PerfilAlumno.class);
        startActivity(i);
    }

    public void irActResidencia (View view){
        Intent i  = new Intent(this, ProyectosResidencia.class);
        startActivity(i);
    }


    public void irMain (View view){
        Intent i  = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem opcionMenu){


        int id = opcionMenu.getItemId();

        if (id == R.id.inicio){

            ejecutar(null);
            return true;
        }
        if (id == R.id.proyectosResidencia){

            irActResidencia(null);
        }
        if (id == R.id.salir){

            irMain(null);
        }
        return super.onOptionsItemSelected(opcionMenu);
    }
}
