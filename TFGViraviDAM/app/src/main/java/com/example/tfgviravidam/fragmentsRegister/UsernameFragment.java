package com.example.tfgviravidam.fragmentsRegister;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tfgviravidam.AppActivity;
import com.example.tfgviravidam.Evento;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.SplashActivity;
import com.example.tfgviravidam.Usuario;
import com.example.tfgviravidam.ViraviActivity;
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
    EditText EditTextNombre, EditTextFecha, EditTextTelefono, EditTextEmail, EditTextContrasenya, EditTextNombreUsuario;
    FirebaseDatabase database;
    DatabaseReference mRootreference;

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

        EditTextNombre = getActivity().findViewById(R.id.txtName);
        EditTextFecha = EditTextFecha.findViewById(R.id.txtBirth);
        EditTextTelefono = EditTextTelefono.findViewById(R.id.txtPhone);
        EditTextEmail = EditTextEmail.findViewById(R.id.txtMail);
        EditTextContrasenya = EditTextContrasenya.findViewById(R.id.txtPass);
        EditTextNombreUsuario = EditTextNombreUsuario.findViewById(R.id.txtUser);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = EditTextNombre.getText().toString();

                String f = EditTextFecha.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date fecha = null;
                try {
                    fecha = sdf.parse(f);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String telefono = EditTextTelefono.getText().toString();
                String email = EditTextEmail.getText().toString();
                String contrasenya = EditTextContrasenya.getText().toString();
                String nombreUsuario = EditTextNombreUsuario.getText().toString();

                EditTextNombre.setText("");
                EditTextFecha.setText("");
                EditTextTelefono.setText("");
                EditTextEmail.setText("");
                EditTextContrasenya.setText("");
                EditTextNombreUsuario.setText("");

                registrarUsuarioFirebase(nombre, fecha, telefono, email, contrasenya, nombreUsuario);

                im.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                Intent intent = new Intent(getActivity(), ViraviActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void registrarUsuarioFirebase(String nombre, Date fecha, String telefono, String email, String contrasenya, String nombreUsuario) {

        Usuario u = new Usuario(nombre, fecha, telefono, email, contrasenya, 0, 0, new File("../imagenes/FotoPerfilDefault.png"), new ArrayList<Evento>(), new ArrayList<Evento>(),new ArrayList<Evento>());
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