package com.example.tfgviravidam.Adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfgviravidam.DAO.Chat;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.databinding.ChatUserCardBinding;
import com.example.tfgviravidam.fragmentsViravi.ChatFragment;
import com.example.tfgviravidam.fragmentsViravi.EventDetaillFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {


    private List<Chat> chats;
    private OnChatClickListener listener;

    public ChatsAdapter(List<Chat> chats) {
        this.chats = chats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_user_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        holder.bind(chat, listener);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir otro fragment
                Fragment fragment = new ChatFragment();
                Bundle args = new Bundle();
                args.putSerializable("Chat", (Serializable) chat);
                fragment.setArguments(args);

                FragmentManager fragmentManager = ((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileFoto;
        private TextView message,time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileFoto = itemView.findViewById(R.id.ivProfileImage);
            message = itemView.findViewById(R.id.tvName);
        }

        public void bind(Chat chat, OnChatClickListener listener) {
            message.setText(chat.getName());
            itemView.setOnClickListener(v -> listener.onChatClick(chat));
        }
    }

    public interface OnChatClickListener {
        void onChatClick(Chat chat);
    }
}
