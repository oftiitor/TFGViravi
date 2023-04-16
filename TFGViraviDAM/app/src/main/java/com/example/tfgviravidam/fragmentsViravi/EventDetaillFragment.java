package com.example.tfgviravidam.fragmentsViravi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.databinding.ActivityNewEventBinding;
import com.example.tfgviravidam.databinding.FragmentEventDetaillBinding;
import com.example.tfgviravidam.databinding.FragmentExploreBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventDetaillFragment extends Fragment {
    private FragmentEventDetaillBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRootreference = database.getReference("Events");


    FirebaseAuth firebaseAuth;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEventDetaillBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Evento event = getArguments().getParcelable("evento");
        if (event != null) {
            Picasso.get().load(event.getImagen()).resize(300, 200).centerCrop().into(binding.ivEventImage);
            binding.tvName.setText(event.getNombre());
            binding.tvDescription.setText(event.getDescripcion());
            binding.tvCategoria.setText(event.getCategoria());
            binding.tvCiudad.setText(event.getCiudad());
            binding.tvDateStart.setText(event.getFechaInicio());
            binding.tvDateEnd.setText(event.getFechaFin());
            binding.tvCreator.setText(event.getUsuarioCreador());
            binding.tvPersonas.setText(String.valueOf(event.getUsuariosApuntados().size()));
        }
        a(event.getNombre());
        return view;

    }

    private void a(String nombre) {
        binding.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRootreference = FirebaseDatabase.getInstance().getReference("Usuarios");
                mRootreference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            Usuario user = snapshot.getValue(Usuario.class);
                            String nombreUsuario = user.getNombreUsuario();
                            Log.i("Usuario", nombreUsuario);
                            Log.i("event", nombre);

                            DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Events").child(nombre);

                            DatabaseReference usersRef = eventRef.child("usuariosApuntados");

                            usersRef.child(nombreUsuario).setValue(nombreUsuario);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });
    }
}