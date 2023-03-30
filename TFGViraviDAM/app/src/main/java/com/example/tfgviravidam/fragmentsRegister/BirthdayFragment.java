package com.example.tfgviravidam.fragmentsRegister;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tfgviravidam.R;

public class BirthdayFragment extends Fragment {

    Button btn;
    EditText txtFechaNacimiento;
    String nombre;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We set the listener on the child fragmentManager

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_birthday, container, false);
        btn = view.findViewById(R.id.btnBirth);
        txtFechaNacimiento = view.findViewById(R.id.txtBirth);
        txtFechaNacimiento.requestFocus();
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        Bundle datosRecuperados = getArguments();
        nombre = datosRecuperados.getString("nombre");

        txtFechaNacimiento.addTextChangedListener(textWatcher);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle datosRecuperados = getArguments();
                nombre = datosRecuperados.getString("nombre");

                Animation fuera = AnimationUtils.loadAnimation(getContext(),R.anim.to_left);
                Animation dentro = AnimationUtils.loadAnimation(getContext(),R.anim.to_rigth);

                Bundle datosAEnviar = new Bundle();
                datosAEnviar.putString("nombre",nombre);
                datosAEnviar.putString("fecha",txtFechaNacimiento.getText().toString().trim());
                Fragment fragmento = new PhoneFragment();
                fragmento.setArguments(datosAEnviar);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.to_left,R.anim.to_rigth);
                fragmentTransaction.replace(R.id.fragmentContainerView, fragmento);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                /*Navigation.findNavController(view).navigate(R.id.action_birthdayFragment_to_phoneFragment);
                im.hideSoftInputFromWindow(textView.getWindowToken(), 0);*/

            }
        });
        return view;

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String text = txtFechaNacimiento.getText().toString().trim();
            btn.setEnabled(!text.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}