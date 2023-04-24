package com.sevdi.postcollege;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sevdi.postcollege.databinding.ActivityAuthBinding;

public class AuthenticationActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view_auth);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_menu_login, R.id.navigation_menu_register)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_auth);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navViewAuth, navController);

        navView.setOnItemSelectedListener(i -> {
            if (i.getItemId() == R.id.navigation_menu_register) {
                navController.navigate(R.id.action_navigation_login_to_navigation_register);
            } else {
                navController.navigate(R.id.action_navigation_register_to_navigation_login);
            }
            return true;
        });
    }


}