package com.example.tfgviravidam.fragmentsRegister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.ViraviActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UsernameFragment extends Fragment {

    Button btn;
    TextView textView;
    String nombre, fecha, phone, mail, contra, user;
    EditText EditTextNombreUsuario;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRootreference = database.getReference("Usuarios");
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;

    /*mRootreference = FirebaseDatabase.getInstance().getReference("https://tfgviravi-default-rtdb.europe-west1.firebasedatabase.app/");*/

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

        // Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.txtMail, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.txtPass, ".{6,}", R.string.invalid_password);

        // EditText con los ID de los fragments
        EditTextNombreUsuario = view.findViewById(R.id.txtUser);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle datosRecuperados = getArguments();
                nombre = datosRecuperados.getString("nombre");
                fecha = datosRecuperados.getString("fecha");
                phone = datosRecuperados.getString("phone");
                mail = datosRecuperados.getString("mail");
                contra = datosRecuperados.getString("pass");
                user = EditTextNombreUsuario.getText().toString().trim();

                Toast.makeText(getActivity(), nombre, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), fecha, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), phone, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), mail, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), contra, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), user, Toast.LENGTH_SHORT).show();


                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date fechaFin;
                try {
                    fechaFin = new Date(sdf.parse(fecha).getTime());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                registrarUsuarioFirebase(nombre, fechaFin, phone, mail, contra, user);

                im.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                Intent intent = new Intent(getActivity(), ViraviActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }

    private void registrarUsuarioFirebase(String nombre, Date fechaFin, String telefono, String email, String contrasenya, String nombreUsuario) {

        Usuario u = new Usuario(nombre, fechaFin, telefono, email, contrasenya,user);
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