package com.example.tfgviravi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity{

    DatabaseReference mRootreference;
    Button mButtonSubirDatosFirebase;
    EditText mEditTextDatoNombreUsuario, mEditTextDatoFechaUsuario, mEditTextDatoTelefonoUsuario, mEditTextDatoEmailUsuario,mEditTextDatoContrasenyaUsuario,mEditTextDatoUsuarioUsuario;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        mButtonSubirDatosFirebase = findViewById(R.id.registrarAlumno);

        mRootreference = FirebaseDatabase.getInstance().getReference();

        mButtonSubirDatosFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = mEditTextDatoNombreUsuario.getText().toString();
                String fecha = mEditTextDatoFechaUsuario.getText().toString();
                int telefono = Integer.parseInt(mEditTextDatoTelefonoUsuario.getText().toString());
                String email = mEditTextDatoEmailUsuario.getText().toString();
                String contrasenya = mEditTextDatoContrasenyaUsuario.getText().toString();
                String usuario = mEditTextDatoUsuarioUsuario.getText().toString();

                Map<String, Object> datosUsuario = new HashMap<>();
                datosUsuario.put("nombre", nombre);
                datosUsuario.put("fecha", fecha);
                datosUsuario.put("telefono", telefono);
                datosUsuario.put("email", email);
                datosUsuario.put("contraseña", contrasenya);
                datosUsuario.put("nombreUsuario", usuario);

                mRootreference.child("Usuario").push().setValue(datosUsuario);

            }
        });

    }
}
