package com.sevdi.postcollege.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sevdi.postcollege.data.model.Account;
import com.sevdi.postcollege.data.service.AccountStore;
import com.sevdi.postcollege.databinding.FragmentProfileBinding;

import java.util.concurrent.atomic.AtomicReference;

public class ProfileFragment extends Fragment {
    FirebaseFirestore db;

    private FragmentProfileBinding binding;
    private String credentialId;
    private Account account;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        credentialId = requireActivity().getIntent().getStringExtra("accountId");
        if (credentialId == null) {
            if (getActivity() != null) {
                getActivity().finish();
            }
        }
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance();
        View root = binding.getRoot();

        binding.workTable.setVisibility(View.GONE);
        binding.socialTable.setVisibility(View.GONE);

        binding.toggleContact.setOnCheckedChangeListener((i, isChecked) -> binding.contactTable.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        binding.toggleEducation.setOnCheckedChangeListener((i, isChecked) -> binding.educationTable.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        binding.toggleWork.setOnCheckedChangeListener((i, isChecked) -> binding.workTable.setVisibility(isChecked ? View.VISIBLE : View.GONE));
        binding.toggleSocial.setOnCheckedChangeListener((i, isChecked) -> binding.socialTable.setVisibility(isChecked ? View.VISIBLE : View.GONE));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("CREDENTIAL CAPTURED::" + credentialId);
        if (credentialId.equals(AccountStore.getInstance().getAccount().getCredentialsId())) {
            System.out.println("PROFILE::SELF");
            binding.editProfile.setVisibility(View.VISIBLE);
        }
        account = fetchAccount(credentialId);
        if (account == null) {
            new AlertDialog.Builder(
                    requireContext())
                    .setTitle("User not found!")
                    .setMessage("User you are trying to access is not available")
                    .setPositiveButton("OK", null)
                    .show();
            if (getActivity() != null) {
                getActivity().finish();
            }
        }
        write(account);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public Account fetchAccount(String credentialsId) {
        System.out.println("START FETCHING");
        if (AccountStore.getInstance().getAccount().getCredentialsId().equals(credentialsId)) {
            return AccountStore.getInstance().getAccount();
        }
        AtomicReference<Account> a = new AtomicReference<>();
        db.collection("users").whereEqualTo("credentialsId", credentialsId).get().
                addOnCompleteListener(i -> {
                    if (i.isSuccessful()) {
                        DocumentSnapshot d = i.getResult().getDocuments().get(0);
                        if (d != null) {
                            a.set(Account.from(d));
                        }
                    }
                });
        return a.get();
    }

    @SuppressLint("SetTextI18n")
    public void write(Account account) {
        binding.firstName.setText(binding.firstName.getText() + " :    " + account.getFirstName());
        binding.lastName.setText(binding.lastName.getText() + " :    " + account.getLastName());
        binding.email.setText(binding.email.getText() + " :    " + account.getEmail());
        binding.phone.setText(binding.phone.getText() + " :    " + account.getPhoneNumber());
        binding.university.setText(binding.university.getText() + " :    " + account.getEducation().getUniversity());
        binding.degree.setText(binding.degree.getText() + " :    " + account.getEducation().getDegree().string());
        binding.faculty.setText(binding.faculty.getText() + " :    " + account.getEducation().getFaculty());
        binding.startYear.setText(binding.startYear.getText() + " :    " + account.getEducation().getStartYear().toString());
        binding.endYear.setText(binding.endYear.getText() + " :    " + account.getEducation().getEndYear().toString());
        binding.company.setText(binding.company.getText() + " :    " + account.getWorkInformation().getCompany());
        binding.country.setText(binding.country.getText() + " :    " + account.getWorkInformation().getCountry());
        binding.city.setText(binding.city.getText() + " :    " + account.getWorkInformation().getCity());
        binding.facebook.setText(binding.facebook.getText() + " :    " + account.getSocialMedia().getFacebook());
        binding.instagram.setText(binding.instagram.getText() + " :    " + account.getSocialMedia().getInstagram());
        binding.twitter.setText(binding.twitter.getText() + " :    " + account.getSocialMedia().getTwitter());
        binding.github.setText(binding.github.getText() + " :    " + account.getSocialMedia().getGithub());
        binding.website.setText(binding.website.getText() + " :    " + account.getSocialMedia().getWebsite());

    }
}