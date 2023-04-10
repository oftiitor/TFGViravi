package com.example.tfgviravidam.fragmentsLogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfgviravidam.R;
import com.example.tfgviravidam.fragmentsViravi.ViraviActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class PassLoginFragment extends Fragment {

    TextView textView;
    String nombre;
    Button btn;

    FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pass_login, container, false);
        btn = view.findViewById(R.id.btnPassLogin);
        textView = view.findViewById(R.id.txtPassLogin);
        textView.requestFocus();
        textView.setText("123456Victor");


        firebaseAuth= FirebaseAuth.getInstance();
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        textView.addTextChangedListener(textWatcher);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle datosRecuperados = getArguments();
                nombre = datosRecuperados.getString("nombre");

                firebaseAuth.signInWithEmailAndPassword(nombre,textView.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getActivity(), ViraviActivity.class));

                        }else{
                            String errorcode = String.valueOf(((FirebaseAuthException) task.getException()));
                            Toast.makeText(getContext(),errorcode,Toast.LENGTH_SHORT).show();
                            Log.i("error",errorcode);

                            Fragment fragmento = new UserLoginFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.setCustomAnimations(R.anim.to_left,R.anim.to_rigth);
                            fragmentTransaction.replace(R.id.fragmentContainerView, fragmento);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();


                        }
                    }
                });



                Intent intent = new Intent(getActivity(), ViraviActivity.class);
                startActivity(intent);
                im.hideSoftInputFromWindow(textView.getWindowToken(), 0);
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

            String text = textView.getText().toString().trim();
            btn.setEnabled(!text.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}