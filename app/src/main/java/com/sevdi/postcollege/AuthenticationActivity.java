package com.sevdi.postcollege;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sevdi.postcollege.data.service.AccountStore;
import com.sevdi.postcollege.databinding.ActivityAuthBinding;

public class AuthenticationActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;

    private ActivityAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            System.out.println("USER LOGGED IN BEFORE");
            auth.signOut();
            AccountStore.getInstance().authenticate(this,
                    () -> AccountStore.getInstance().getAccountAsync(auth.getUid(),
                            i -> {
                                AccountStore.getInstance().setAccount(i);
                                System.out.println("Loading:MainActivity");
                                Toast.makeText(this, "Welcome back " + AccountStore.getInstance().getAccount().getFirstName(), Toast.LENGTH_SHORT).show();
                                System.out.println("CREATING MAIN");
                                Intent main = new Intent(this, MainActivity.class);
                                main.putExtra("accountId", i.getCredentialsId());
                                startActivity(main);
                            },
                            null),
                    null);
        }
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

        navView.setOnItemReselectedListener(null);
        navView.setOnItemSelectedListener(i -> {
            if (i.getItemId() == R.id.navigation_menu_register) {
                navController.navigate(R.id.action_navigation_login_to_navigation_register);
            } else {
                navController.navigate(R.id.action_navigation_register_to_navigation_login);
            }
            return true;
        });
    }

    public BottomNavigationView getNavView() {
        return binding.navViewAuth;
    }

}