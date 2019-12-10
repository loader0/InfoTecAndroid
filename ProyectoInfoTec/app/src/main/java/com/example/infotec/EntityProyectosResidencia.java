package com.example.infotec;

public class EntityProyectosResidencia {

    public int idProyecto;
    public String tipo_proyecto;
    public String nombre_proy;
    public String nombre_emp;
    public String nombre_cont;
    public int tel_empre;
    public String correo_empre;
    public int num_vacantes;
    public String direccion_empre;

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getTipo_proyecto() {
        return tipo_proyecto;
    }

    public String getNombre_proy() {
        return nombre_proy;
    }

    public String getNombre_emp() {
        return nombre_emp;
    }

    public String getNombre_cont() {
        return nombre_cont;
    }

    public int getTel_empre() {
        return tel_empre;
    }

    public String getCorreo_empre() {
        return correo_empre;
    }

    public int getNum_vacantes() {
        return num_vacantes;
    }

    public String getDireccion_empre() {
        return direccion_empre;
    }

    public void setTipo_proyecto(String tipo_proyecto) {
        this.tipo_proyecto = tipo_proyecto;
    }

    public void setNombre_proy(String nombre_proy) {
        this.nombre_proy = nombre_proy;
    }

    public void setNombre_emp(String nombre_emp) {
        this.nombre_emp = nombre_emp;
    }

    public void setNombre_cont(String nombre_cont) {
        this.nombre_cont = nombre_cont;
    }

    public void setTel_empre(int tel_empre) {
        this.tel_empre = tel_empre;
    }

    public void setCorreo_empre(String correo_empre) {
        this.correo_empre = correo_empre;
    }

    public void setNum_vacantes(int num_vacantes) {
        this.num_vacantes = num_vacantes;
    }

    public void setDireccion_empre(String direccion_empre) {
        this.direccion_empre = direccion_empre;
    }
}
