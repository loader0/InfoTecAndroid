package com.example.infotec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

public class InfoProyectoServicioS extends AppCompatActivity {

    Button btnSuscribirse;
    TextView nomSS;
    TextView empresaSS;
    TextView contactoSS;
    TextView telSS;
    TextView correoSS;
    TextView vacanteSS;
    TextView direccionSS;
    TextView descSS;
    private String url = "";
    Boolean consultaExitosa;
    private ProgressDialog pDialog;
    String token;
    String jsonStr;
    String proyectoNom;
    String proTipo;
    String proEmpresa;
    String proContacto;
    int proTelefono;
    String proCorreo;
    int proVacante;
    String proDireccion;
    String proDesc;
    int idProye;

    EntityProyectosResidencia infoP = new EntityProyectosResidencia();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_proyecto_servicio_s);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSuscribirse = findViewById(R.id.btnSuscribirseSS);
        nomSS = findViewById(R.id.nomProyectoSS);
        empresaSS = findViewById(R.id.nomEmpresaSS);
        contactoSS = findViewById(R.id.nomContactoSS);
        telSS = findViewById(R.id.telefonoSS);
        correoSS = findViewById(R.id.correoSS);
        vacanteSS = findViewById(R.id.vacanteSS);
        direccionSS = findViewById(R.id.direccionSS);
        descSS = findViewById(R.id.descripcionSS);

        proyectoNom = String.valueOf(nomSS.getText());
        proEmpresa = String.valueOf(empresaSS.getText());
        proContacto = String.valueOf(contactoSS.getText());
        proTelefono = Integer.valueOf((String) telSS.getText());
        proCorreo = String.valueOf(correoSS.getText());
        proVacante = Integer.valueOf((String) vacanteSS.getText());
        proDireccion = String.valueOf(direccionSS.getText());
        proDesc = String.valueOf(descSS.getText());

        new InfoProyectoServicioS.LlamarInfoProyectosS().execute();

        if(!consultaExitosa)
        {
            return;
        }

        proyectoNom = infoP.getNombre_proy();
        proTipo = infoP.getTipo_proyecto();
        proEmpresa = infoP.getNombre_emp();
        proContacto = infoP.getNombre_cont();
        proTelefono = infoP.getTel_empre();
        proCorreo = infoP.getCorreo_empre();
        proVacante = infoP.getNum_vacantes();
        proDireccion = infoP.getDireccion_empre();


        btnSuscribirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutar(null);
            }
        });
    }

    private class LlamarInfoProyectosS extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            consultaExitosa = false;
            super.onPreExecute();
            pDialog = new ProgressDialog(InfoProyectoServicioS.this);
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

                    infoP.setNombre_proy(obj.getString("nombre_proy"));
                    infoP.setTipo_proyecto(obj.getString("tipo_proyecto"));
                    infoP.setNombre_emp(obj.getString("nombre_emp"));
                    infoP.setNombre_cont(obj.getString("nombre_cont"));
                    infoP.setTel_empre(obj.getInt("tel_empre"));
                    infoP.setCorreo_empre(obj.getString("correo_empre"));
                    infoP.setNum_vacantes(obj.getInt("num_vacantes"));
                    infoP.setDireccion_empre(obj.getString("direccion_empre"));

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
                            Toast.makeText(InfoProyectoServicioS.this,
                                    "Ocurrio un error, intente mas tarde",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InfoProyectoServicioS.this,
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

        Intent i  = new Intent(this, ServicioS.class);
        startActivity(i);
    }
}
