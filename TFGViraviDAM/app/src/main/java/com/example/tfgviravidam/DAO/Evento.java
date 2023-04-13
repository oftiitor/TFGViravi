package com.example.tfgviravidam.DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Evento {

    private String nombre, descripcion, fechaInicio, fechaFin, usuarioCreador, ciudad, categoria,imagen;
    private ArrayList<String> usuariosApuntados;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ArrayList<String> getUsuariosApuntados() {
        return usuariosApuntados;
    }

    public void setUsuariosApuntados(ArrayList<String> usuariosApuntados) {
        this.usuariosApuntados = usuariosApuntados;
    }

    public Evento(String nombre, String descripcion, String fechaInicio, String fechaFin, String usuarioCreador, String ciudad, String categoria, String imagen, ArrayList<String> usuariosApuntados) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuarioCreador = usuarioCreador;
        this.ciudad = ciudad;
        this.categoria = categoria;
        this.imagen = imagen;
        this.usuariosApuntados = usuariosApuntados;


    }
}
