package com.example.tfgviravi;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Usuario {

    private int id;
    private String nombre, telefono;
    private Date fehchaNacimiento;
    private String correo, contraseña;
    private int seguidores, seguidos;
    private File fotoPerfil;
    private ArrayList<Evento> eventosApuntado, eventosParticipado, eventosCreados;

    public Usuario(int id, String nombre, String telefono, Date fehchaNacimiento, String correo, String contraseña, int seguidores, int seguidos, File fotoPerfil, ArrayList<Evento> eventosApuntado, ArrayList<Evento> eventosParticipado, ArrayList<Evento> eventosCreados) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fehchaNacimiento = fehchaNacimiento;
        this.correo = correo;
        this.contraseña = contraseña;
        this.seguidores = seguidores;
        this.seguidos = seguidos;
        this.fotoPerfil = fotoPerfil;
        this.eventosApuntado = eventosApuntado;
        this.eventosParticipado = eventosParticipado;
        this.eventosCreados = eventosCreados;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFehchaNacimiento() {
        return fehchaNacimiento;
    }

    public void setFehchaNacimiento(Date fehchaNacimiento) {
        this.fehchaNacimiento = fehchaNacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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
