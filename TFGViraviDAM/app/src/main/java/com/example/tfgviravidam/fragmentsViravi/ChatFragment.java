package com.example.tfgviravidam.fragmentsViravi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfgviravidam.Adapter.MessageAdapter;
import com.example.tfgviravidam.DAO.Chat;
import com.example.tfgviravidam.DAO.Message;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.databinding.FragmentChatBinding;
import com.example.tfgviravidam.databinding.FragmentChatListBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private ArrayList<String> nombre = new ArrayList<String>();
    private ArrayList<String> telefono = new ArrayList<String>();
    private ArrayList<Message> messageList = new ArrayList<Message>();


    private RecyclerView recyclerView;
    private MessageAdapter adapter;

    private Chat chat = null;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChatBinding.inflate(inflater, container, false);
        recyclerView = binding.messageList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }

    private void initListeners() {
        Bundle bundle = getArguments();

        if(bundle != null){
            chat = (Chat) bundle.getSerializable("Chat");
            Log.i("Chat",chat.toString());
            binding.tvUserName.setText(chat.getName());
            binding.tvEventName.setText(chat.getNameEvent());
        }

        Chat finalChat = chat;
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference chatRef = database.getReference("Chats").child(finalChat.getId());
                DatabaseReference newMessageRef = chatRef.child("messages").push();
                String horaActual = new SimpleDateFormat("HH:mm").format(new Date());
                Map<String, Object> messageData = new HashMap<>();
                messageData.put("date", horaActual);
                messageData.put("sender", nombre.get(0));
                messageData.put("text", binding.etMessage.getText());
                Message m = new Message(nombre.get(0),binding.etMessage.getText().toString(),horaActual,null);
                ArrayList<Message> messagesList = new ArrayList<>();
                messagesList.add(m);
                newMessageRef.setValue(m);
                loadMessages();
                binding.etMessage.setText("");
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserName();
        loadMessages();
        Bundle bundle = getArguments();
        if(bundle != null){
            chat = (Chat) bundle.getSerializable("Chat");
            Log.i("Chat23",chat.toString());
            // Usa el objeto Chat según sea necesario
        }

        Chat finalChat = chat;
        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("Chats").child(chat.getId()).child("messages");
        messagesRef.addChildEventListener(messagesListener);

    }

    private void loadMessages() {
        Bundle bundle = getArguments();
        messageList = new ArrayList<Message>();
        if(bundle != null){
            chat = (Chat) bundle.getSerializable("Chat");
            Log.i("Chat23",chat.toString());
            // Usa el objeto Chat según sea necesario
        }

        Chat finalChat = chat;

        DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("Chats").child(finalChat.getId()).child("messages");
        messagesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String messageId = messageSnapshot.getKey();
                    String sender = messageSnapshot.child("sender").getValue(String.class);
                    String text = messageSnapshot.child("text").getValue(String.class);
                    String time = messageSnapshot.child("timestamp").getValue(String.class);

                    if(sender.equals(nombre.get(0))){
                        Message m = new Message(sender,text,time,true);
                        messageList.add(m);
                    }else {
                        Message m = new Message(sender,text,time,false);
                        messageList.add(m);
                    }
                }
                adapter = new MessageAdapter(messageList);

                recyclerView.setAdapter(adapter);
                adapter.notifyItemInserted(adapter.messageList.size() - 1);
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.messageList.size() - 1);
                Log.i("mensajes",messageList.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private ChildEventListener messagesListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            String messageId = snapshot.getKey();
            String sender = snapshot.child("sender").getValue(String.class);
            String text = snapshot.child("text").getValue(String.class);
            String time = snapshot.child("timestamp").getValue(String.class);

            if (sender == telefono.get(0)) {
                Message m = new Message(sender, text, time, true);
                messageList.add(m);
            } else {
                Message m = new Message(sender, text, time, false);
                messageList.add(m);
            }
            adapter.notifyItemInserted(messageList.size() - 1);
            binding.messageList.smoothScrollToPosition(messageList.size() - 1);}

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

        @Override
        public void onCancelled(@NonNull DatabaseError error) {}
    };

    private void getUserName(){
        DatabaseReference mRootreference = FirebaseDatabase.getInstance().getReference("Usuarios");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        mRootreference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Usuario user = snapshot.getValue(Usuario.class);
                    String nombreUsuario = user.getNombreUsuario();
                    String phone = user.getTelefono();

                    nombre.add(0,nombreUsuario);
                    telefono.add(0,phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}