package com.example.tfgviravidam.fragmentsViravi;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.transition.Fade;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tfgviravidam.DAO.Chat;
import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Message;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.databinding.FragmentEventDetaillBinding;
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
                    String nombreUsuario = snapshot.child("nombreUsuario").getValue(String.class);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setEnterTransition(new Fade());
        }
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
        loadEvent(event.getNombre());
        return view;

    }

    private void loadEvent(String nombre) {
        binding.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRootreference = FirebaseDatabase.getInstance().getReference("Usuarios");
                mRootreference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            ArrayList<String> listaEvent = new ArrayList<String>();
                            String nombreUsuario = snapshot.child("nombreUsuario").getValue(String.class);
                            lista.add(0,nombreUsuario);
                            Log.i("Usuario", nombreUsuario);
                            Log.i("event", nombre);
                            DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Events").child(nombre);
                            listaEvent.clear();
                            listaEvent.add(nombre);
                            mRootreference.child(firebaseAuth.getCurrentUser().getUid()).child("eventosApuntados").child(nombre).setValue(nombre);

                            DatabaseReference usersRef = eventRef.child("usuariosApuntados");

                            usersRef.child(nombreUsuario).setValue(nombreUsuario);
                            Evento event = getArguments().getParcelable("evento");
                            binding.btnSign.setText("Apuntado");
                            Toast.makeText(getContext(),"Te has apuntado a " + event.getNombre(),Toast.LENGTH_LONG);
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

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ExploreFragment();
                Bundle args = new Bundle();

                FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.zoom_in,R.anim.zoom_out,R.anim.zoom_in,R.anim.zoom_out)
                        .replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
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
        chatData.put("name", getUserName() +" & " + getEventName());
        chatData.put("event", getEvent());
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

        Chat c = new Chat(chatKey,getUserName() +" & " + getEventName(),getEvent(),Arrays.asList(userName, eventName),messagesList);

        Bundle bundle = new Bundle();
        bundle.putSerializable("Chat", (Serializable) c);
        Fragment nuevoFragmento = new ChatFragment();
        nuevoFragmento.setArguments(bundle);
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_left_in,R.anim.slide_left_out,R.anim.slide_left_in,R.anim.slide_left_out).replace(R.id.frame_layout, nuevoFragmento).commit();


    }
}