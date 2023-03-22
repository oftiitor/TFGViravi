package com.example.tfgviravidam.fragmentsRegister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tfgviravidam.AppActivity;
import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.RegisterActivity;
import com.example.tfgviravidam.SplashActivity;
import com.example.tfgviravidam.ViraviActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class UsernameFragment extends Fragment {

    Button btn;
    TextView textView;
    // EditText EditTextNombre, EditTextFecha, EditTextTelefono, EditTextEmail, EditTextContrasenya, EditTextNombreUsuario;
    FirebaseDatabase database;
    DatabaseReference mRootreference = database.getReference("https://tfgviravi-default-rtdb.europe-west1.firebasedatabase.app/");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_username, container, false);
        btn = view.findViewById(R.id.btnUser);
        textView = view.findViewById(R.id.txtUser);
        textView.requestFocus();
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        textView.addTextChangedListener(textWatcher);

        // EditText con los ID de los fragments

        /*EditTextNombre = getActivity().findViewById(R.id.txtName);
        EditTextFecha = getActivity().findViewById(R.id.txtBirth);
        EditTextTelefono = getActivity().findViewById(R.id.txtPhone);
        EditTextEmail = getActivity().findViewById(R.id.txtMail);
        EditTextContrasenya = getActivity().findViewById(R.id.txtPass);
        EditTextNombreUsuario = getActivity().findViewById(R.id.txtUser);*/

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = "yo";

                String fecha = "17-06-2001";
                /*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date fecha = null;
                try {
                    fecha = sdf.parse(f);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                String telefono = "615106907";
                String email = "email";
                String contrasenya = "contra";
                String nombreUsuario = "nomusu";

                /*EditTextNombre.setText("");
                EditTextFecha.setText("");
                EditTextTelefono.setText("");
                EditTextEmail.setText("");
                EditTextContrasenya.setText("");
                EditTextNombreUsuario.setText("");*/

                registrarUsuarioFirebase(nombre, fecha, telefono, email, contrasenya, nombreUsuario);

                im.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                Intent intent = new Intent(UsernameFragment.this.getActivity(), AppActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void registrarUsuarioFirebase(String nombre, String fecha, String telefono, String email, String contrasenya, String nombreUsuario) {

        Usuario u = new Usuario(nombre, fecha, telefono, email, contrasenya, 1, 2 /*new File("java/imagenes/FotoPerfilDefault.png")*/, new ArrayList<Evento>(), new ArrayList<Evento>(),new ArrayList<Evento>());
        mRootreference.child(nombreUsuario).setValue(u);

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String text = textView.getText().toString().trim();
            btn.setEnabled(!text.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }


    };
}