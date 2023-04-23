package com.example.tfgviravidam.fragmentsViravi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfgviravidam.DAO.Chat;
import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Message;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDetaillFragment extends Fragment {
    private FragmentEventDetaillBinding binding;
    private ArrayList<String> lista = new ArrayList<String>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRootreference = database.getReference("Events");


    FirebaseAuth firebaseAuth;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        mRootreference = FirebaseDatabase.getInstance().getReference("Usuarios");
        mRootreference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Usuario user = snapshot.getValue(Usuario.class);
                    String nombreUsuario = user.getNombreUsuario();
                    lista.add(0,nombreUsuario);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
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
                            lista.add(0,nombreUsuario);
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

        binding.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createChat();

            }
        });
    }

    private String getUserName(){
        String nombreUsuario = lista.get(0);
        return nombreUsuario;
    }
    private String getEventName(){
        Evento event = getArguments().getParcelable("evento");
        String eventName = event.getUsuarioCreador();
        return eventName;
    }

    private String getEvent(){
        Evento event = getArguments().getParcelable("evento");
        String eventName = event.getNombre();
        return eventName;
    }

    private void createChat(){

        String userName = getUserName();
        String eventName = getEventName();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference chatRef = database.getReference("Chats").push();
        String chatKey = chatRef.getKey();
        DatabaseReference messageRef = chatRef.child("messages").push();

        String horaActual = new SimpleDateFormat("HH:mm").format(new Date());

        Map<String, Object> chatData = new HashMap<>();
        chatData.put("name", getEventName() + " - " + getEvent());
        chatData.put("users", Arrays.asList(userName, eventName));

        chatRef.setValue(chatData);

        Map<String, Object> messageData = new HashMap<>();
        messageData.put("text", "!Bienvenidos!");
        messageData.put("sender", getUserName());
        messageData.put("timestamp", horaActual);
        Message m = new Message(getUserName(),"!Bienvenidos!",horaActual,null);
        ArrayList<Message> messagesList = new ArrayList<>();
        messagesList.add(m);

        messageRef.setValue(messageData);

        Chat c = new Chat(chatKey,getEventName() + " - " + getEvent(),Arrays.asList(userName, eventName),messagesList);

        Bundle bundle = new Bundle();
        bundle.putSerializable("Chat", (Serializable) c);
        Fragment nuevoFragmento = new ChatFragment();
        nuevoFragmento.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frame_layout, nuevoFragmento).commit();


    }
}