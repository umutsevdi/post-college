package com.sevdi.postcollege.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sevdi.postcollege.MainActivity;
import com.sevdi.postcollege.data.model.Account;
import com.sevdi.postcollege.data.service.AccountStore;
import com.sevdi.postcollege.databinding.FragmentLoginBinding;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class LoginFragment extends Fragment {

    /*    private LoginViewModel loginViewModel;*/
    private FragmentLoginBinding binding;
    EditText username;
    EditText password;
    Button loginButton;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        username = binding.username;
        password = binding.password;
        loginButton = binding.login;

        binding.username.setOnFocusChangeListener((view, b) -> {
            if (!b)
                loginButton.setEnabled(!(TextUtils.isEmpty(binding.username.getText()) || TextUtils.isEmpty(binding.password.getText())));
        });
        binding.password.setOnFocusChangeListener((view, b) -> {
            if (!b)
                loginButton.setEnabled(!(TextUtils.isEmpty(binding.username.getText()) || TextUtils.isEmpty(binding.password.getText())));
        });
        binding.login.setOnClickListener(i -> {
            String email = binding.username.getText().toString();
            String password = binding.password.getText().toString();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                new AlertDialog.Builder(
                        requireContext())
                        .setTitle("Login Failed")
                        .setMessage("Please fill all required fields.")
                        .setPositiveButton("OK", null)
                        .show();
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Welcome", Toast.LENGTH_SHORT).show();
                    AccountStore.getInstance().getAccountAsync(
                            Objects.requireNonNull(task.getResult().getUser()).getUid(),
                            (Account account) -> {
                                AccountStore.getInstance().setAccount(account);
                                requireActivity().getSharedPreferences("PostCollege", Context.MODE_PRIVATE).edit()
                                        .putString("user_token",
                                                Base64.encodeToString((account.getEmail() +
                                                                ":" + account.getPassword())
                                                                .getBytes(StandardCharsets.UTF_8),
                                                        Base64.DEFAULT)).apply();
                                Intent main = new Intent(getContext(), MainActivity.class);
                                main.putExtra("accountId", AccountStore.getInstance().getAccount().getCredentialsId());
                                requireActivity().startActivity(main);
                            },
                            () -> new AlertDialog.Builder(
                                    requireContext())
                                    .setTitle("Login Failed")
                                    .setPositiveButton("OK", null)
                                    .show());
                } else {
                    new AlertDialog.Builder(
                            requireContext())
                            .setTitle("Login Failed")
                            .setMessage(Objects.requireNonNull(task.getException()).getMessage())
                            .setPositiveButton("OK", null)
                            .show();
                }
            });
        });

        return binding.getRoot();
    }
}