package com.example.tfgviravidam.fragmentsViravi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tfgviravidam.Adapter.PopularAdapter;
import com.example.tfgviravidam.Adapter.UserEventsAdapter;
import com.example.tfgviravidam.AppActivity;
import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.SplashActivity;
import com.example.tfgviravidam.databinding.FragmentExploreBinding;
import com.example.tfgviravidam.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ArrayList<String> lista = new ArrayList<String>();
    FirebaseAuth firebaseAuth;
    TextView txtNombre;
    String nombreUsuario;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        getUserName();
        loadUserEvents();

        firebaseAuth = FirebaseAuth.getInstance();
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(ProfileFragment.this.getActivity(), AppActivity.class));
                finishF();
            }
        });

        /*Bundle datosRecuperados = getArguments();
        nombreUsuario = datosRecuperados.getString("user");
        txtNombre = view.findViewById(R.id.textView16);
        txtNombre.setText(nombreUsuario);*/

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewEventActivity.class));
            }
        });

        return  view;

    }

    private void finishF() {
        Activity activity = (Activity)getContext();
        activity.finish();
    }

    private void loadUserEvents(){
        ArrayList<Evento> listaEvento = new ArrayList<Evento>();

        binding.userEvents.setLayoutManager(new GridLayoutManager(getContext(),2));

        DatabaseReference eventosRef = FirebaseDatabase.getInstance().getReference().child("Events");
        eventosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventoSnapshot : dataSnapshot.getChildren()) {
                    String nombre = eventoSnapshot.child("nombre").getValue(String.class);
                    String descripcion = eventoSnapshot.child("descripcion").getValue(String.class);
                    String usuarioCreador = eventoSnapshot.child("usuarioCreador").getValue(String.class);
                    String imagen = eventoSnapshot.child("imagen").getValue(String.class);
                    String ciudad = eventoSnapshot.child("ciudad").getValue(String.class);
                    String categoria = eventoSnapshot.child("categoria").getValue(String.class);
                    String fechaInicio = eventoSnapshot.child("fechaInicio").getValue(String.class);
                    String fechaFin = eventoSnapshot.child("fechaFin").getValue(String.class);

                    ArrayList<String> usuariosApuntados = new ArrayList<>();
                    for (DataSnapshot usuarioSnapshot : eventoSnapshot.child("usuariosApuntados").getChildren()) {
                        usuariosApuntados.add(usuarioSnapshot.getKey());
                    }

                    Evento evento = new Evento(nombre, descripcion, fechaInicio, fechaFin, usuarioCreador, ciudad, categoria, imagen, usuariosApuntados);
                    Log.i("EVEN",evento.toString());

                    if (usuarioCreador.equals(lista.get(0))){
                        listaEvento.add(evento);
                        UserEventsAdapter adapterUserEvento = new UserEventsAdapter(listaEvento);
                        binding.userEvents.setAdapter(adapterUserEvento);
                    }
                    Log.i("EVENTOS",listaEvento.size()+"");
                }
            }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Manejar errores si es necesario
                    // ...
                }
            });
        }

        private void getUserName(){
            DatabaseReference mRootreference;
            FirebaseAuth firebaseAuth;
            firebaseAuth = FirebaseAuth.getInstance();

            mRootreference = FirebaseDatabase.getInstance().getReference("Usuarios");
            mRootreference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        String nombreUsuario = snapshot.child("nombreUsuario").getValue(String.class);
                        lista.add(0,nombreUsuario);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
    }