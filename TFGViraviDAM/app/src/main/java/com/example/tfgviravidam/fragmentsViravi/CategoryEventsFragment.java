package com.example.tfgviravidam.fragmentsViravi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfgviravidam.Adapter.OtherUserEventsAdapter;
import com.example.tfgviravidam.Adapter.PopularAdapter;
import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.databinding.FragmentCategoryEventsBinding;
import com.example.tfgviravidam.databinding.FragmentProfile2Binding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryEventsFragment extends Fragment {

    private FragmentCategoryEventsBinding binding;
    private List<Evento> eventos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoryEventsBinding.inflate(inflater, container, false);
        loadEvents();
        return binding.getRoot();
    }

    private void loadEvents() {
        DatabaseReference eventosRef = FirebaseDatabase.getInstance().getReference().child("Events");

        binding.rvEventCategory.setLayoutManager(new GridLayoutManager(getContext(),2));

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

                    Evento evento = new Evento(nombre, descripcion,fechaInicio,fechaFin, usuarioCreador, ciudad, categoria, imagen, usuariosApuntados);
                    String category = getArguments().getString("category");
                    if(categoria.equals(category)){
                        eventos.add(evento);
                        Log.i("EVENTOSCATEGORIA",eventos.toString());
                        OtherUserEventsAdapter adapter1=new OtherUserEventsAdapter(eventos);
                        binding.rvEventCategory.setAdapter(adapter1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}