package com.example.tfgviravi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Principal extends AppCompatActivity {/*

    TextView nombre, fechaNacimiento, telefono, email, contrasenya, nombreUsuario;
    Button registrarAlumno;

    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;

    // final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference ref = database.getReference("https://tfgviravi-default-rtdb.europe-west1.firebasedatabase.app");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.password, ".{6,}", R.string.invalid_password);

        nombre = findViewById(R.id.nombre);
        fechaNacimiento = findViewById(R.id.fechaNacimiento);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        contrasenya = findViewById(R.id.password);
        nombreUsuario = findViewById(R.id.nombreUsu);

        registrarAlumno = findViewById(R.id.registrarAlumno);

        registrarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom = nombre.getText().toString();
                String fecha = fechaNacimiento.getText().toString();
                String telf = telefono.getText().toString();
                String e = email.getText().toString();
                String p = contrasenya.getText().toString();
                String nomUsu = nombreUsuario.getText().toString();

                Map<String, Object> datosUsuario = new HashMap<>();
                datosUsuario.put("nombre", nom);
                datosUsuario.put("fechaNacimiento", fecha);
                datosUsuario.put("telefono", telf);
                datosUsuario.put("email", e);
                datosUsuario.put("password", p);
                datosUsuario.put("nombreUsuario", nomUsu);

                ref.child("Usuario").push().setValue(datosUsuario);

                if (awesomeValidation.validate()) {
                    firebaseAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Principal.this, "Alumno Registrado Correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeerror(errorCode);
                            }
                        }
                    });
                } else {
                    Toast.makeText(Principal.this, "Ingrese un email y una contraseña", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /*
    public void registrarUsuario() {


        Usuario usuario = new Usuario(
                nombre.getText().toString(),
                fechaNacimiento.getText().toString(),
                telefono.getText().toString(),
                email.getText().toString(),
                contrasenya.getText().toString(),
                nombreUsuario.getText().toString());

        DatabaseReference usersRef = ref.child("users");
        Map<String, Usuario> users = new HashMap<>();
        users.put(nombreUsuario.getText().toString(), usuario);
        usersRef.setValue(users);


    }
    */
    /*
    public void iniciarSesion(View view) {
        Intent actividad = new Intent(Principal.this, MainActivity.class);
        startActivity(actividad);
    }

    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(Principal.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(Principal.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(Principal.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(Principal.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                email.setError("La dirección de correo electrónico está mal formateada.");
                email.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(Principal.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                contrasenya.setError("Contraseña incorrecta.");
                contrasenya.requestFocus();
                contrasenya.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(Principal.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(Principal.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(Principal.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(Principal.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                email.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                email.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(Principal.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(Principal.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(Principal.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(Principal.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(Principal.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(Principal.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(Principal.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                email.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                contrasenya.requestFocus();
                break;

        }

    }*/

}