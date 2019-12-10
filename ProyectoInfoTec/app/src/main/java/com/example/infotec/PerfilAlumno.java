package com.example.infotec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

public class PerfilAlumno extends AppCompatActivity {

    DatosJson datosJson = new DatosJson();
    EditText nombre;
    EditText planEstudios;
    EditText moduloEspecialidad;
    EditText situacionVigencia;
    EditText promedio;
    EditText creditosAcumulados;
    EditText periodoIngreso;
    EditText periodosConvalidados;
    EditText periodoActual;
    EditText tutor;
    EditText curp;
    EditText nacimiento;
    EditText direccion;
    EditText telefonoD;
    EditText celular;
    EditText correo;
    EditText escuelaProced;
    String idAlumno = datosJson.getUserName();
    private String url = "https://192.168.1.78:44345/api/alumno/"+idAlumno;
    String jsonStr;
    Boolean consultaExitosa;
    String alumnoNom;
    String alumnoPlanEstudios;
    String alumnoModuloEspecialidad;
    String alumnoSituacion;
    String alumnoPromedio;
    String alumnoCreditosAcumulados;
    String alumnoperiodoIngreso;
    String alumnoPeriodoConvalidados;
    String alumnoperiodoActual;
    String alumnoTutor;
    String alumnoCurp;
    String alumnoNacimiento;
    String alumnoDireccion;
    String alumnoTelefonoD;
    String alumnoCelular;
    String alumnoCorreo;
    String alumnoEscuelaProced;
    String token;

    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nombre = findViewById(R.id.txtNombre);
        planEstudios = findViewById(R.id.txtPlanEstudios);
        moduloEspecialidad = findViewById(R.id.txtModuloEspecialidad);
        situacionVigencia = findViewById(R.id.txtSituacionVigencia);
        promedio = findViewById(R.id.txtPromedio);
        creditosAcumulados = findViewById(R.id.txtCreditosAcumulados);
        periodoIngreso = findViewById(R.id.txtPeriodoIngreso);
        periodosConvalidados = findViewById(R.id.txtPeriodosConvalidados);
        periodoActual = findViewById(R.id.txtPeriodoActual);
        tutor = findViewById(R.id.txtTutor);
        curp = findViewById(R.id.txtCurp);
        nacimiento = findViewById(R.id.txtFechaNacimiento);
        direccion = findViewById(R.id.txtDireccion);
        telefonoD = findViewById(R.id.txtTelefonoD);
        celular = findViewById(R.id.txtCelular);
        correo = findViewById(R.id.txtCorreo);
        escuelaProced = findViewById(R.id.txtEscuelaProcedencia);

        alumnoNom = String.valueOf(nombre.getText());
        alumnoPlanEstudios = String.valueOf(planEstudios.getText());
        alumnoModuloEspecialidad = String.valueOf(moduloEspecialidad.getText());
        alumnoSituacion = String.valueOf(situacionVigencia.getText());
        alumnoPromedio = String.valueOf(promedio.getText());
        alumnoCreditosAcumulados = String.valueOf(creditosAcumulados.getText());
        alumnoperiodoIngreso = String.valueOf(periodoIngreso.getText());
        alumnoPeriodoConvalidados = String.valueOf(periodosConvalidados.getText());
        alumnoperiodoActual = String.valueOf(periodoActual.getText());
        alumnoTutor = String.valueOf(tutor.getText());
        alumnoCurp = String.valueOf(curp.getText());
        alumnoNacimiento = String.valueOf(nacimiento.getText());
        alumnoDireccion = String.valueOf(direccion.getText());
        alumnoTelefonoD = String.valueOf(telefonoD.getText());
        alumnoCelular = String.valueOf(celular.getText());
        alumnoCorreo = String.valueOf(correo.getText());
        alumnoEscuelaProced = String.valueOf(escuelaProced.getText());


        new PerfilAlumno.LlamarAlumno().execute();

        if(!consultaExitosa)
        {
            return;
        }
        Alumno datos = new Alumno();

        alumnoNom = datos.getNombre();
        alumnoPlanEstudios = datos.getPlan_estudio();
        alumnoModuloEspecialidad = datos.getPlan_estudio();
        alumnoSituacion = datos.getPlan_estudio();
        alumnoPromedio = datos.getPlan_estudio();
        alumnoCreditosAcumulados = datos.getPlan_estudio();
        alumnoperiodoIngreso = datos.getPlan_estudio();
        alumnoPeriodoConvalidados = datos.getPlan_estudio();
        alumnoperiodoActual = datos.getPlan_estudio();
        alumnoTutor = datos.getPlan_estudio();
        alumnoCurp = datos.getPlan_estudio();
        alumnoNacimiento = datos.getPlan_estudio();
        alumnoDireccion = datos.getPlan_estudio();
        alumnoTelefonoD = datos.getPlan_estudio();
        alumnoCelular = datos.getPlan_estudio();
        alumnoCorreo = datos.getPlan_estudio();
        alumnoEscuelaProced = datos.getPlan_estudio();

    }
    private class LlamarAlumno extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            consultaExitosa = false;
            super.onPreExecute();
            pDialog = new ProgressDialog(PerfilAlumno.this);
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

                    alumnoNom = obj.getString(alumnoNom);
                    alumno.setNombre(obj.getString("nombre"));
                    alumno.setApellido(obj.getString("apellido"));
                    alumno.setTelefono(obj.getString("telefono"));
                    alumno.setCarrera(obj.getString("carrera"));
                    alumno.setSexo(obj.getString("sexo"));
                    alumno.setIdrol(obj.getInt("idrol"));
                    alumno.setCorreo(obj.getString("correo"));
                    alumno.setNombreProyecto(obj.getString("nombreProyecto"));
                    alumno.setIdSeg(obj.getInt("idSeg"));
                    alumno.setPlan_estudio(obj.getString("plan_estudio"));
                    alumno.setMod_especialidad(obj.getString("mod_especialidad"));
                    alumno.setSit_vigencia(obj.getString("sit_vigencia"));
                    alumno.setPromedio(obj.getDouble("promedio"));
                    alumno.setCreditos_acumulados(obj.getInt("creditos_acumulados"));
                    alumno.setPeriodo_ingreso(obj.getString("periodo_ingreso"));
                    alumno.setPeriodo_convalidado(obj.getInt("periodo_convalidado"));
                    alumno.setPeriodo_actual_ultimo(obj.getString("periodo_actual_ultimo"));
                    alumno.setTutor(obj.getString("tutor"));
                    alumno.setCurp(obj.getString("curp"));
                    alumno.setFecha_naci(obj.getString("fecha_naci"));
                    alumno.setDireccion(obj.getString("direccion"));
                    alumno.setEscuela_procedencia(obj.getString("escuela_procedencia"));


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
                            Toast.makeText(PerfilAlumno.this,
                                    "Ocurrio un error, intente mas tarde",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PerfilAlumno.this,
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

        Intent i  = new Intent(this, PerfilAlumno.class);
        startActivity(i);
    }

    public void irActResidencia (View view){
        Intent i  = new Intent(this, Residencia.class);
        startActivity(i);
    }

    public void irActSS (View view){
        Intent i  = new Intent(this, ServicioS.class);
        startActivity(i);
    }

    public void irMain (View view){
        Intent i  = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu primerMenu){

        getMenuInflater().inflate(R.menu.menu_lateral, primerMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem opcionMenu){

        int id = opcionMenu.getItemId();

        if (id == R.id.inicio){

            ejecutar(null);

        }
        if (id == R.id.residencia){

            irActResidencia(null);

        }
        if (id == R.id.serviciosocial){

            irActSS(null);

        }
        if (id == R.id.salir){

            irMain(null);
        }
        return super.onOptionsItemSelected(opcionMenu);
    }
}
