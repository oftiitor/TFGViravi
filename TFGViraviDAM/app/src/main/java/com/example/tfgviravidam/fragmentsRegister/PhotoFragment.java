package com.example.tfgviravidam.fragmentsRegister;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.databinding.ActivityNewEventBinding;
import com.example.tfgviravidam.databinding.FragmentPhotoBinding;
import com.example.tfgviravidam.fragmentsViravi.ViraviActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;


public class PhotoFragment extends Fragment {
    Uri path;
    String nombre, fecha, phone, mail, contra, user, photo;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRootreference = database.getReference("Usuarios");
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;

    private FragmentPhotoBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPhotoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        if(path ==null){
            binding.btnPhoto.setEnabled(false);
        }
        binding.btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();

            }
        });

        return view;
    }
    private void cargarImagen() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent,"Seleccione la Aplicacion"),10);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            path=data.getData();
            Log.i("path",path.toString());
            binding.btnFoto.setImageURI(path);
            binding.btnPhoto.setEnabled(true);
            final StorageReference reference = storage.getReference().child("image");
            reference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Bundle datosRecuperados = getArguments();
                            nombre = datosRecuperados.getString("nombre");
                            fecha = datosRecuperados.getString("fecha");
                            phone = datosRecuperados.getString("phone");
                            mail = datosRecuperados.getString("mail");
                            contra = datosRecuperados.getString("pass");
                            user = datosRecuperados.getString("user");

                            ArrayList<Evento> eventosApuntado = null;
                            ArrayList<Evento> eventosParticipado = null;
                            ArrayList<Evento> eventosCreado = null;


                            registrarUsuarioFirebase(user, nombre, phone, fecha, mail, contra, String.valueOf(uri), 0, 0,eventosApuntado,eventosParticipado,eventosCreado );
                            startActivity(new Intent(getActivity(), ViraviActivity.class));
                        }
                    });
                }
            });
        }

    }
    private void registrarUsuarioFirebase(String nombreUsuario, String nombre, String telefono, String fechaNacimiento, String correo, String contrasenya, String fotoPerfil, int seguidores, int seguidos, ArrayList<Evento> eventosApuntado, ArrayList<Evento> eventosParticipado, ArrayList<Evento> eventosCreados) {

        firebaseAuth.createUserWithEmailAndPassword(correo,contrasenya);
        mRootreference.child(nombreUsuario).setValue(new Usuario(nombreUsuario,nombre,telefono,fechaNacimiento,correo,contrasenya,fotoPerfil,seguidores,seguidos,eventosApuntado,eventosParticipado,eventosCreados));

    }
}