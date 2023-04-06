package com.example.tfgviravidam.DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Evento {

    private String nombre, descripcion, fechaInicio, fechaFin, usuarioCreador, ciudad, categoria;
    private File imagen;
    private ArrayList<Usuario> usuariosApuntados;


    public Evento(String nombre, String descripcion, String fechaInicio, String fechaFin, String ciudad, String categoria, ArrayList<Usuario> usuariosApuntados) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.ciudad = ciudad;
        this.categoria = categoria;
        this.usuariosApuntados = usuariosApuntados;
    }

    public Evento(String nombre, String descripcion, String fechaInicio, String fechaFin, String usuarioCreador, String ciudad, File imagen, ArrayList<Usuario> usuariosApuntados) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuarioCreador = usuarioCreador;
        this.ciudad = ciudad;
        this.imagen = imagen;
        this.usuariosApuntados = usuariosApuntados;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

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

    public File getImagen() {
        return imagen;
    }

    public void setImagen(File imagen) {
        this.imagen = imagen;
    }

    public ArrayList<Usuario> getUsuariosApuntados() {
        return usuariosApuntados;
    }

    public void setUsuariosApuntados(ArrayList<Usuario> usuariosApuntados) {
        this.usuariosApuntados = usuariosApuntados;
    }
}
