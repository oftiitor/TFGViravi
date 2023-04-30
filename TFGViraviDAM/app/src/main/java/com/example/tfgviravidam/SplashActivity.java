package com.example.tfgviravidam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.tfgviravidam.fragmentsLogin.LoginActivity;
import com.example.tfgviravidam.fragmentsViravi.ViraviActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Usuarios").child("6qz0wD4MTZeRnHlu5E68je7bWQT2").child("eventosCreados").child("Sala Fabrik");
        firebaseDatabase.setValue("Sala Fabrik");
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Usuarios").child("6qz0wD4MTZeRnHlu5E68je7bWQT2").child("eventosCreados").child("Patinaje");
        firebaseDatabase.setValue("Patinaje");
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Usuarios").child("6qz0wD4MTZeRnHlu5E68je7bWQT2").child("eventosCreados").child("Crucero Mediterráneo");
        firebaseDatabase.setValue("Crucero Mediterráneo");
        Animation animacion1 = AnimationUtils.loadAnimation(this,R.anim.splash);

        ImageView ivLogo = findViewById(R.id.ivLogo);

        ivLogo.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, AppActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this).toBundle();
                startActivity(intent,b);
            }
        },1800);
    }
}