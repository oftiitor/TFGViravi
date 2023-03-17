package com.example.tfgviravidam;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Usuario {

    private String nombre, telefono;
    private Date fechaNacimiento;
    private String correo, contrasenya;
    private int seguidores, seguidos;
    private File fotoPerfil;
    private ArrayList<Evento> eventosApuntado, eventosParticipado, eventosCreados;

    public Usuario(String nombre, Date fechaNacimiento, String telefono, String correo, String contrasenya, int seguidores, int seguidos, File fotoPerfil, ArrayList<Evento> eventosApuntado, ArrayList<Evento> eventosParticipado, ArrayList<Evento> eventosCreados) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasenya = contrasenya;
        this.seguidores = seguidores;
        this.seguidos = seguidos;
        this.fotoPerfil = fotoPerfil;
        this.eventosApuntado = eventosApuntado;
        this.eventosParticipado = eventosParticipado;
        this.eventosCreados = eventosCreados;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public int getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(int seguidores) {
        this.seguidores = seguidores;
    }

    public int getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(int seguidos) {
        this.seguidos = seguidos;
    }

    public File getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(File fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public ArrayList<Evento> getEventosApuntado() {
        return eventosApuntado;
    }

    public void setEventosApuntado(ArrayList<Evento> eventosApuntado) {
        this.eventosApuntado = eventosApuntado;
    }

    public ArrayList<Evento> getEventosParticipado() {
        return eventosParticipado;
    }

    public void setEventosParticipado(ArrayList<Evento> eventosParticipado) {
        this.eventosParticipado = eventosParticipado;
    }

    public ArrayList<Evento> getEventosCreados() {
        return eventosCreados;
    }

    public void setEventosCreados(ArrayList<Evento> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }
}
