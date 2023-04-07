package com.example.tfgviravidam.DAO;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class Usuario {

    private String nombreUsuario, nombre, telefono, fechaNacimiento, correo, contrasenya, fotoPerfil;
    private int seguidores, seguidos;
    private ArrayList<Evento> eventosApuntado, eventosParticipado, eventosCreados;

    public Usuario(String nombreUsuario, String nombre, String telefono, String fechaNacimiento, String correo, String contrasenya, String fotoPerfil, int seguidores, int seguidos, ArrayList<Evento> eventosApuntado, ArrayList<Evento> eventosParticipado, ArrayList<Evento> eventosCreados) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.contrasenya = contrasenya;
        this.fotoPerfil = fotoPerfil;
        this.seguidores = seguidores;
        this.seguidos = seguidos;
        this.eventosApuntado = eventosApuntado;
        this.eventosParticipado = eventosParticipado;
        this.eventosCreados = eventosCreados;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
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
