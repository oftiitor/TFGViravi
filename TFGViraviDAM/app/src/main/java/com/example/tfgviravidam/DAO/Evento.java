package com.example.tfgviravidam.DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Evento {

    private int id;
    private String nombre, descripcion, categoria, fecha1,fecha2,foto;
    private File imagen;
    private String direccion;
    private boolean esFavorito;
    private String fechaInicio, fechaFin;
    private ArrayList<Usuario> usuariosApuntados, usuarioCreador;

    /*public Evento(int id, String nombre, String descripcion,String categoria, File imagen, String direccion, boolean esFavorito, Date fechaInicio, Date fechaFin, ArrayList<Usuario> usuariosApuntados, ArrayList<Usuario> usuarioCreador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.direccion = direccion;
        this.esFavorito = esFavorito;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuariosApuntados = usuariosApuntados;
        this.usuarioCreador = usuarioCreador;
        this.categoria = categoria;
    }*/

    public Evento(String nombre,String categoria, String imagen, String fechaInicio, String fechaFin) {
        this.nombre = nombre;
        this.fecha1 = fechaInicio;
        this.fecha2 = fechaFin;
        this.foto=imagen;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public File getImagen() {
        return imagen;
    }

    public void setImagen(File imagen) {
        this.imagen = imagen;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isEsFavorito() {
        return esFavorito;
    }

    public void setEsFavorito(boolean esFavorito) {
        this.esFavorito = esFavorito;
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

    public ArrayList<Usuario> getUsuariosApuntados() {
        return usuariosApuntados;
    }

    public void setUsuariosApuntados(ArrayList<Usuario> usuariosApuntados) {
        this.usuariosApuntados = usuariosApuntados;
    }

    public ArrayList<Usuario> getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(ArrayList<Usuario> usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }
}
