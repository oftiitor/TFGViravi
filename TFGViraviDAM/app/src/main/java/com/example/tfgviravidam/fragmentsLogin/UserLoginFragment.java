package com.example.tfgviravidam.fragmentsLogin;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfgviravidam.R;
import com.example.tfgviravidam.fragmentsRegister.BirthdayFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLoginFragment extends Fragment {

    TextView textView;
    Button btn;
    private boolean comprobarmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);
        btn = view.findViewById(R.id.btnUserLogin);
        textView = view.findViewById(R.id.txtUserLogin);
        textView.requestFocus();
        /*textView.setText("victor@gmail.com");*/
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        textView.addTextChangedListener(textWatcher);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = textView.getText().toString().trim();
                String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(text);
                boolean isMatch = matcher.matches();

                FirebaseAuth.getInstance().fetchSignInMethodsForEmail(textView.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.isSuccessful()){
                                    boolean check =!task.getResult().getSignInMethods().isEmpty();
                                    if (check){
                                        comprobarmail = true;
                                    }
                                    else {
                                        comprobarmail = false;
                                        textView.setError("El email introducido no existe");

                                    }
                                }
                            }
                        });

                if (!isMatch) {
                    textView.setError("El email introducido no es válido");
                }

                if(isMatch&&comprobarmail){
                    Bundle datosAEnviar = new Bundle();
                    datosAEnviar.putString("email", textView.getText().toString().trim());
                    Fragment fragmento = new PassLoginFragment();
                    fragmento.setArguments(datosAEnviar);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.to_rigth, R.anim.to_left);
                    fragmentTransaction.replace(R.id.fragmentContainerView, fragmento);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

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

            if(!textView.getText().toString().isEmpty()) {
                btn.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}