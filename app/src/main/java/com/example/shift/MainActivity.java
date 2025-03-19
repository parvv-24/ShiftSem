package com.example.shift;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bottomNavigationView = findViewById(R.id.bottom_nav);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

//        bottomNavigationView = findViewById(R.id.bottom_nav);
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//
//                if (id==R.id.Home_menu){
//                    Toast.makeText(MainActivity.this,"Home",Toast.LENGTH_SHORT).show();
//                }
//                else if (id==R.id.Book_menu){
//                    Toast.makeText(MainActivity.this,"Booking",Toast.LENGTH_SHORT).show();
//                }
//                else if (id==R.id.Carpool_menu){
//                    Toast.makeText(MainActivity.this,"Taxi",Toast.LENGTH_SHORT).show();
//                }else if (id==R.id.Account_menu){
//                    Toast.makeText(MainActivity.this,"Profile",Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });
    }
}