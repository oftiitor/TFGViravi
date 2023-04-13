package com.example.tfgviravidam.fragmentsViravi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfgviravidam.Adapter.CategoryAdapter;
import com.example.tfgviravidam.Adapter.PopularAdapter;
import com.example.tfgviravidam.DAO.Categorias;
import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
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


public class ExploreFragment extends Fragment {

    private FragmentExploreBinding binding;

    private RecyclerView.Adapter adapter;

    private  String user;

    private RecyclerView recyclerViewCategory;
    private RecyclerView recyclerViewPopularplans;

    FirebaseAuth firebaseAuth;
    DatabaseReference firebaseDatabase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerViewCategory = view.findViewById(R.id.viewCategory);
        recyclerViewPopularplans = view.findViewById(R.id.viewPopuPlans);
        recyclerViewCategory(view);
        recyclerViewPopular(view);

        return view;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase= FirebaseDatabase.getInstance().getReference("Usuarios");

        recogerDatosUser();

    }

    private void recogerDatosUser() {
        Log.i("dasdasdadadassdads","asdasdadadas");

        firebaseDatabase.child(user).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Usuario user = snapshot.getValue(Usuario.class);
                    String nombreUsuario =user.getNombreUsuario();
                    String nombre=user.getNombre();
                    String telefono=user.getTelefono();
                    String fechaNacimiento=user.getFechaNacimiento();
                    String correo=user.getCorreo();
                    String contrasenya=user.getContrasenya();
                    String foto = user.getFotoPerfil();

                    Picasso.get().load(foto).into(binding.foto);
                    Log.i("dasdasdadadassdads","asdasdadadas");
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void recyclerViewCategory(View view) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategory.setLayoutManager(linearLayoutManager);

        ArrayList<Categorias> categoria = new ArrayList<>();
        categoria.add(new Categorias("Fiesta","copa-de-champan","yellow_pastel"));
        categoria.add(new Categorias("Turismo","museo_britanico","blue_pastel"));
        categoria.add(new Categorias("Actividades","parque","red_pastel"));
        categoria.add(new Categorias("Viajes","passport","orange_pastel"));
        categoria.add(new Categorias("Gastronomia","restaurant","purple_pastel"));
        categoria.add(new Categorias("Deportes","sports","brown_pastel"));

        adapter=new CategoryAdapter(categoria);
        binding.viewCategory.setAdapter(adapter);

    }
    private void recyclerViewPopular(View view) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularplans.setLayoutManager(linearLayoutManager);

        List<Evento> eventos = recogerEventos();

        PopularAdapter adapter1=new PopularAdapter(eventos);
        binding.viewPopuPlans.setAdapter(adapter1);

    }

    private List<Evento> recogerEventos(){
        DatabaseReference eventosRef = FirebaseDatabase.getInstance().getReference().child("Eventos");

        List<Evento> eventos = new ArrayList<>();

        eventosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot eventoSnapshot : dataSnapshot.getChildren()) {
                    String nombre = eventoSnapshot.child("nombre").getValue(String.class);
                    String descripcion = eventoSnapshot.child("descripcion").getValue(String.class);
                    String usuarioCreador = eventoSnapshot.child("usuarioCreador").getValue(String.class);
                    String foto = eventoSnapshot.child("foto").getValue(String.class);
                    String ciudad = eventoSnapshot.child("ciudad").getValue(String.class);
                    String categoria = eventoSnapshot.child("categoria").getValue(String.class);
                    String fechaInicio = eventoSnapshot.child("fechaInicio").getValue(String.class);
                    String fechaFin = eventoSnapshot.child("fechaFin").getValue(String.class);

                    ArrayList<String> usuariosApuntados = new ArrayList<>();
                    for (DataSnapshot usuarioSnapshot : eventoSnapshot.child("usuariosApuntados").getChildren()) {
                        usuariosApuntados.add(usuarioSnapshot.getKey());
                    }

                    Evento evento = new Evento(nombre, descripcion, usuarioCreador, foto, ciudad, categoria, fechaInicio, fechaFin, usuariosApuntados);
                    eventos.add(evento);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return eventos;
    }

}