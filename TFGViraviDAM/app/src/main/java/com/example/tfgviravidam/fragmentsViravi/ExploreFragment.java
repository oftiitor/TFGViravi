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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tfgviravidam.Adapter.CategoryAdapter;
import com.example.tfgviravidam.Adapter.CategoryAdapter2;
import com.example.tfgviravidam.Adapter.PopularAdapter;
import com.example.tfgviravidam.Adapter.UserAdapter;
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

    private  String user;

    /* DECLARACIÓN DE LOS IMAGEBUTTONS */
    private ImageButton imageButtonFiesta, imageButtonTurismo, imageButtonActividades, imageButtonViajes, imageButtonGastronomia, imageButtonDeportes;
    private RecyclerView recyclerViewPopularplans;
    private List<Evento> eventos = new ArrayList<>();

    FirebaseAuth firebaseAuth;
    DatabaseReference firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recyclerViewPopularplans = view.findViewById(R.id.viewPopuPlans);
        recyclerViewPopular(view);

        /* SÓLO ES PARA SABER SI FUNCIONA EL BOTÓN */
        imageButtonFiesta.setOnClickListener(view16 -> Toast.makeText(getActivity(), "Fiesta", Toast.LENGTH_LONG).show());
        imageButtonTurismo.setOnClickListener(view15 -> Toast.makeText(getActivity(), "Turismo", Toast.LENGTH_LONG).show());
        imageButtonActividades.setOnClickListener(view14 -> Toast.makeText(getActivity(), "Actividades", Toast.LENGTH_LONG).show());
        imageButtonViajes.setOnClickListener(view13 -> Toast.makeText(getActivity(), "Viajes", Toast.LENGTH_LONG).show());
        imageButtonGastronomia.setOnClickListener(view12 -> Toast.makeText(getActivity(), "Gastronomia", Toast.LENGTH_LONG).show());
        imageButtonDeportes.setOnClickListener(view1 -> Toast.makeText(getActivity(), "Deportes", Toast.LENGTH_LONG).show());

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase= FirebaseDatabase.getInstance().getReference("Usuarios");
        loadUser();
        recogerDatosUser();

    }

    private void recogerDatosUser() {
        Log.i("dasdasdadadassdads","asdasdadadas");

        firebaseDatabase.child(user).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Usuario user = snapshot.getValue(Usuario.class);
                    String nombreUsuario = user.getNombreUsuario();
                    String nombre = user.getNombre();
                    String telefono = user.getTelefono();
                    String fechaNacimiento = user.getFechaNacimiento();
                    String correo = user.getCorreo();
                    String contrasenya = user.getContrasenya();
                    String foto = user.getFotoPerfil();

                    Log.i("dasdasdadadassdads","asdasdadadas");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void recyclerViewPopular(View view) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularplans.setLayoutManager(linearLayoutManager);

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

                    Evento evento = new Evento(nombre, descripcion,fechaInicio,fechaFin, usuarioCreador, ciudad, categoria, imagen, usuariosApuntados);
                    eventos.add(evento);
                    Log.i("as",eventos.toString());
                    PopularAdapter adapter1=new PopularAdapter(eventos);
                    binding.viewPopuPlans.setAdapter(adapter1);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void loadUser(){
        DatabaseReference usuariosRef = FirebaseDatabase.getInstance().getReference().child("Usuarios");
        ArrayList<Usuario> listaUser = new ArrayList<Usuario>();
        usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot usuarioSnapshot : dataSnapshot.getChildren()) {
                    String userKey = usuarioSnapshot.getKey();
                    String nombreUsuario = usuarioSnapshot.child("nombreUsuario").getValue(String.class);
                    String nombre = usuarioSnapshot.child("nombre").getValue(String.class);
                    String telefono = usuarioSnapshot.child("telefono").getValue(String.class);
                    String fechaNacimiento = usuarioSnapshot.child("fechaNacimiento").getValue(String.class);
                    String correo = usuarioSnapshot.child("correo").getValue(String.class);
                    String contrasena = usuarioSnapshot.child("contrasenya").getValue(String.class);
                    String fotoPerfil = usuarioSnapshot.child("fotoPerfil").getValue(String.class);
                    Usuario u = new Usuario(userKey,fotoPerfil,nombreUsuario);
                    listaUser.add(u);

                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                    binding.viewUsers.setLayoutManager(linearLayoutManager);
                    UserAdapter adapterUser =new UserAdapter(listaUser);
                    binding.viewUsers.setAdapter(adapterUser);
                    Log.i("ListaUser",listaUser.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores si es necesario
                // ...
            }
        });
    }

    private void recogerEventos(){

    }

}