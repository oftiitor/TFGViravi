package com.example.tfgviravidam.fragmentsViravi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tfgviravidam.DAO.Evento;
import com.example.tfgviravidam.DAO.Usuario;
import com.example.tfgviravidam.R;
import com.example.tfgviravidam.databinding.ActivityNewEventBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewEventActivity extends AppCompatActivity {

    private ActivityNewEventBinding binding;
    private String nombreUsuario = "";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRootreference = database.getReference("Eventos");

    String user;
    FirebaseAuth firebaseAuth;
    FirebaseStorage storage;

    String storage_path = "event/*";

    private static final int COD_SEL_STORAGE = 200;
    private static final int COD_SEL_IMAGE = 300;

    private Uri image_url;
    String photo = "photo";
    String id;

    String fechaInicio;
    String fechaFin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewEventBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.txtName.addTextChangedListener(textWatcherYear);
        binding.txtDesc.addTextChangedListener(textWatcherYear);
        binding.txtCategory.addTextChangedListener(textWatcherYear);
        binding.txtCity.addTextChangedListener(textWatcherYear);
        binding.txtDayInicio.addTextChangedListener(textWatcherYear);
        binding.txtMonthInicio.addTextChangedListener(textWatcherYear);
        binding.txtYearInicio.addTextChangedListener(textWatcherYear);
        binding.txtDayFin.addTextChangedListener(textWatcherYear);
        binding.txtMonthFin.addTextChangedListener(textWatcherYear);
        binding.txtYearFin.addTextChangedListener(textWatcherYear);
        firebaseAuth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        initListeners();
    }

    private void initListeners() {
        binding.btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });
        binding.txtPulsar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        binding.btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewEventActivity.this, ViraviActivity.class);
                startActivity(intent);
            }
        });
        binding.btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!binding.txtName.getText().toString().isEmpty() &&
                        !binding.txtName.getText().toString().isEmpty() &&
                        !binding.txtDesc.getText().toString().isEmpty() &&
                        !binding.txtCity.getText().toString().isEmpty() &&
                        !binding.txtCategory.getText().toString().isEmpty() &&
                        !binding.txtDayInicio.getText().toString().isEmpty() &&
                        !binding.txtMonthInicio.getText().toString().isEmpty() &&
                        !binding.txtYearInicio.getText().toString().isEmpty() &&
                        !binding.txtDayFin.getText().toString().isEmpty() &&
                        !binding.txtMonthFin.getText().toString().isEmpty() &&
                        !binding.txtYearFin.getText().toString().isEmpty()) {

                    fechaInicio = binding.txtDayInicio.getText().toString()+"-"+binding.txtMonthInicio.getText().toString()+"-"+binding.txtYearInicio.getText().toString();
                    fechaFin = binding.txtDayFin.getText().toString()+"-"+binding.txtMonthFin.getText().toString()+"-"+binding.txtYearFin.getText().toString();
                    crearEvento(binding.txtName.getText().toString(),binding.txtDesc.getText().toString(),fechaInicio,fechaFin,binding.txtCity.getText().toString(),binding.txtCategory.getText().toString());
                    Log.i("dasd","aaaaa");

                } else {
                    Toast.makeText(NewEventActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void cargarImagen() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent,"Seleccione la Aplicacion"),10);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            Uri path=data.getData();
            binding.btnGaleria.setImageURI(path);
            final StorageReference reference = storage.getReference().child("event/"+path.toString());
            reference.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            image_url=uri;
                        }
                    });
                }
            });
        }

    }

    private TextWatcher textWatcherYear = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if(!binding.txtName.getText().toString().isEmpty()
                    && !binding.txtDesc.getText().toString().isEmpty()
                    && !binding.txtCity.getText().toString().isEmpty()
                    && !binding.txtCategory.getText().toString().isEmpty()
                    && !fechaInicio.isEmpty()
                    && !fechaFin.isEmpty()) {
                binding.btnCrear.setEnabled(true);
            }else{
                binding.btnCrear.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void showDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_dialog);

        LinearLayout fiestaLayout = dialog.findViewById(R.id.layoutFiesta);
        LinearLayout turismoLayout = dialog.findViewById(R.id.layoutTurismo);
        LinearLayout actividadesLayout = dialog.findViewById(R.id.layoutActividades);
        LinearLayout viajesLayout = dialog.findViewById(R.id.layoutViajes);
        LinearLayout gastronomiaLayout = dialog.findViewById(R.id.layoutGastronomia);
        LinearLayout deportesLayout = dialog.findViewById(R.id.layoutDeportes);

        fiestaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                binding.txtCategory.setText("Fiesta");

            }
        });

        turismoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                binding.txtCategory.setText("Turismo");

            }
        });

        actividadesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                binding.txtCategory.setText("Actividades");

            }
        });

        viajesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                binding.txtCategory.setText("Viajes");

            }
        });

        gastronomiaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                binding.txtCategory.setText("Gastronomia");

            }
        });

        deportesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                binding.txtCategory.setText("Deportes");

            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    private void crearEvento(String nombre,String descripcion, String fechaInicio, String fechaFin, String ciudad, String categoria) {
        ArrayList<String> usuariosApuntados = new ArrayList<String>();
        mRootreference= FirebaseDatabase.getInstance().getReference("Usuarios");
        mRootreference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Usuario user = snapshot.getValue(Usuario.class);
                    nombreUsuario =user.getNombreUsuario();
                    Log.i("Usuario",nombreUsuario);
                    mRootreference= FirebaseDatabase.getInstance().getReference("Events");
                    usuariosApuntados.add(nombreUsuario);
                    usuariosApuntados.add("Juan");

                    Evento e = new Evento();
                    e.setNombre(nombre);
                    e.setDescripcion(descripcion);
                    e.setFechaInicio(fechaInicio);
                    e.setFechaFin(fechaFin);
                    e.setUsuarioCreador(nombreUsuario);
                    e.setCiudad(ciudad);
                    e.setCategoria(categoria);
                    e.setImagen(image_url.toString());
                    e.setUsuariosApuntados(new ArrayList<>());
                    mRootreference.child(nombre).setValue(e);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}