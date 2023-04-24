package com.sevdi.postcollege.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.sevdi.postcollege.AuthenticationActivity;
import com.sevdi.postcollege.R;
import com.sevdi.postcollege.databinding.FragmentRegisterBinding;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    FirebaseAuth auth;

    /*    private LoginViewModel loginViewModel;*/
    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        auth = FirebaseAuth.getInstance();
        setFocusChangeListeners();
        binding.register.setOnClickListener(i -> {
            String email = binding.username.getText().toString();
            String password = binding.password.getText().toString();
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Registration complete", Toast.LENGTH_SHORT).show();
                            ((AuthenticationActivity) requireActivity()).getNavView().setSelectedItemId(R.id.navigation_menu_login);
                        } else {
                            new AlertDialog.Builder(
                                    requireContext())
                                    .setTitle("Registration Failed")
                                    .setMessage(task.getException().getMessage())
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    }
            );
        });

        return binding.getRoot();
    }


    void setFocusChangeListeners() {
        binding.firstName.setOnFocusChangeListener(onFocusChangeListener());
        binding.lastName.setOnFocusChangeListener(onFocusChangeListener());
        binding.username.setOnFocusChangeListener(onFocusChangeListener());
        binding.university.setOnFocusChangeListener(onFocusChangeListener());
        binding.faculty.setOnFocusChangeListener(onFocusChangeListener());
        binding.yearEnter.setOnFocusChangeListener(onFocusChangeListener());
        binding.yearExit.setOnFocusChangeListener(onFocusChangeListener());
        binding.password.setOnFocusChangeListener((view, isFocus) -> {
            if (!isFocus) {
                CharSequence p = null;
                if (binding.password.getText().length() < 8) {
                    p = "Passwords can not be less than 8 characters!";
                } else if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", binding.password.getText())) {
                    p = "Invalid password! Passwords should include at least a number an uppercase and lowercase letter";
                }
                if (p != null)
                    new AlertDialog.Builder(
                            view.getContext())
                            .setTitle("Invalid password")
                            .setMessage(p)
                            .setPositiveButton("OK", null)
                            .show();

            }
            onFocusChangeListener();
        });
        binding.passwordAgain.setOnFocusChangeListener((view, isFocus) -> {
            if (!isFocus && !binding.password.getText().toString().equals(binding.passwordAgain.getText().toString())) {
                new AlertDialog.Builder(
                        view.getContext())
                        .setTitle("Invalid password")
                        .setMessage("Passwords do not match!")
                        .setPositiveButton("OK", null)
                        .show();
            }
            onFocusChangeListener();
        });
    }

    View.OnFocusChangeListener onFocusChangeListener() {
        return (view, b) -> {
            int number = 0;
            if (binding.firstName.getText().length() > 0) number++;
            if (binding.lastName.getText().length() > 0) number++;
            if (binding.username.getText().length() > 0) number++;
            if (binding.password.getText().length() > 0) number++;
            if (binding.passwordAgain.getText().length() > 0) number++;
            if (binding.university.getText().length() > 0) number++;
            if (binding.faculty.getText().length() > 0) number++;
            if (binding.yearEnter.getText().length() > 0) number++;
            if (binding.yearExit.getText().length() > 0) number++;
            binding.progressBar.setProgress(number * 9);
            binding.register.setEnabled(number == 9);
        };
    }
}