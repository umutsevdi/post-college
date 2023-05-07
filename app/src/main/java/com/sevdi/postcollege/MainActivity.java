package com.sevdi.postcollege;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sevdi.postcollege.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("LOADING MAIN");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_menu_feed_post, R.id.navigation_menu_notification, R.id.navigation_menu_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navView.setOnItemReselectedListener(null);
        navView.setOnItemSelectedListener(i -> {
            int from = navView.getSelectedItemId();
            int to = i.getItemId();

            if (from == R.id.navigation_menu_feed_post) {
                if (to == R.id.navigation_menu_profile) {
                    navController.navigate(R.id.action_navigation_home_to_navigation_profile);
                } else if (to == R.id.navigation_menu_notification) {
                    navController.navigate(R.id.action_navigation_home_to_navigation_notification);
                }
            } else if (from == R.id.navigation_menu_profile) {
                if (to == R.id.navigation_menu_feed_post) {
                    navController.navigate(R.id.action_navigation_profile_to_navigation_home);
                } else if (to == R.id.navigation_menu_notification) {
                    navController.navigate(R.id.action_navigation_profile_to_navigation_notification);
                }
            } else if (from == R.id.navigation_menu_notification) {
                if (to == R.id.navigation_menu_feed_post) {
                    navController.navigate(R.id.action_navigation_notification_to_navigation_home);
                } else if (to == R.id.navigation_menu_profile) {
                    navController.navigate(R.id.action_navigation_notification_to_navigation_profile);
                }
            }
            return true;
        });
    }


}