package com.sevdi.postcollege.ui.auth;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.sevdi.postcollege.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    /*    private LoginViewModel loginViewModel;*/
    private FragmentLoginBinding binding;
    EditText username;
    EditText password;
    Button loginButton;
    FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
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

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            requireActivity().finish();
                        } else if (task.getException() != null) {
                            new AlertDialog.Builder(
                                    requireContext())
                                    .setTitle("Login Failed")
                                    .setMessage(task.getException().getMessage())
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    }
            );
        });

        return binding.getRoot();
    }
}