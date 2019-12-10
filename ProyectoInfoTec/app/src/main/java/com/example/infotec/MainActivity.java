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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText usuario, contrasenia;
    Button btnLogin;
    private String url = "https://192.168.1.78:44345/api/login/authalumn";
    private String TAG = MainActivity.class.getSimpleName();
    String alumnoid;
    String alumnoContrasenia;
    String jsonStr;
    Boolean consultaExitosa;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnLogin=findViewById(R.id.botonlg);
        usuario=findViewById(R.id.txtId);
        contrasenia=findViewById(R.id.txtPwd);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alumnoid = String.valueOf(usuario.getText());
                alumnoContrasenia = String.valueOf(contrasenia.getText());

                if(alumnoid.length() == 0 || alumnoContrasenia.length() == 0)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,
                                    "Favor de llenar ambos campos.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }


                new LlamarLogin().execute();

                if(!consultaExitosa)
                {
                    return;
                }

                Intent click = new Intent(MainActivity.this, PerfilAlumno.class);
                MainActivity.this.startActivity(click);

            }
        });

    }
    private class LlamarLogin extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            consultaExitosa = false;
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Procesando datos...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            Gson gson = new Gson();
            AlumnoLogin alumnoLogin = new AlumnoLogin();
            alumnoLogin.setUserName(alumnoid);
            alumnoLogin.setPassword(alumnoContrasenia);

            String json = gson.toJson(alumnoLogin);

            try {
                String jsonStr = sh.makeServiceCall(url,json,2);

            }
            catch (Exception e)
            {

            }

            if(jsonStr != null && jsonStr != ""){
                try {
                    JSONObject obj = new JSONObject(json);
                    DatosJson datosJson = new DatosJson();

                    datosJson.setToken(obj.getString("token"));
                    datosJson.setUser(obj.getString("user"));
                    datosJson.setUserName(obj.getString("userName"));

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
                            Toast.makeText(MainActivity.this,
                                    "Ocurrio un error, intente mas tarde",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,
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


}
