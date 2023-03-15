package com.example.tfgviravidam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.sagarkoli.chetanbottomnavigation.chetanBottomNavigation;

public class ViraviActivity extends AppCompatActivity {

    HomeFragment homeFragment = new HomeFragment();
    ExploreFragment exploreFragment = new ExploreFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viravi);
        fab = findViewById(R.id.fab_home);
        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnItemSelectedListener(mOnNavigationItemSelectedListener);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
            }
        });
    }

    private final BottomNavigationView.OnItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.explore:
                    loadFragment(new ExploreFragment());
                    return true;
                case R.id.profile:
                    loadFragment(new ProfileFragment());
                    return true;
                default:
                    loadFragment(new HomeFragment());
                    return false;
            }

        }
    };

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();

    }
}
