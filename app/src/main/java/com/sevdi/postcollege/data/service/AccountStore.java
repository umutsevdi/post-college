package com.sevdi.postcollege.data.service;


import android.content.Context;
import android.util.Base64;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sevdi.postcollege.data.model.Account;

import java.util.function.Consumer;

public class AccountStore {
    private final FirebaseAuth auth;
    private final FirebaseFirestore db;
    private static Account account;
    private static AccountStore accountStore;

    public static AccountStore getInstance() {
        if (accountStore == null)
            return new AccountStore();
        return accountStore;
    }

    private AccountStore() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        AccountStore.account = account;
    }

    public void authenticate(Context context, Runnable onSuccess, Runnable onFail) {
        String token = context.getSharedPreferences("PostCollege", Context.MODE_PRIVATE).getString("user_token", null);
        System.out.println("accountStore#authenticate(" + token + ")");
        if (token == null) {
            onFail.run();
        }
        String decryptedToken = new String(Base64.decode(token, Base64.DEFAULT));
        String[] emailPassword = decryptedToken.split(":");
        auth.signInWithEmailAndPassword(emailPassword[0], emailPassword[1]).addOnCompleteListener(i -> {
            if (i.isSuccessful() && i.getResult().getUser() != null)
                onSuccess.run();
            else
                onFail.run();
        });
    }

    public void getAccountAsync(String credentialsId, Consumer<Account> onSuccess, Runnable onFail) {
        if (db == null || credentialsId == null) onFail.run();
        System.out.println("accountStore#init(" + credentialsId + ")");
        assert db != null;
        db.collection("users").whereEqualTo("credentialsId", credentialsId).get()
                .addOnCompleteListener(t -> {
                    if (t.isSuccessful()) {
                        try {
                            Account a = Account.from(t.getResult().getDocuments().get(0));
                            System.out.println(a);
                            onSuccess.accept(a);
                            return;
                        } catch (Exception ignore) {
                        }
                    }
                    onFail.run();
                });
    }

    public void updateAccount(FirebaseFirestore db) {
        db.collection("users").whereEqualTo("credentialsId", account.getCredentialsId()).get()
                .addOnCompleteListener(t -> {
                    if (t.isSuccessful()) {
                        try {
                            Account a = Account.from(t.getResult().getDocuments().get(0));
                            setAccount(a);
                        } catch (Exception ignored) {
                        }
                        System.out.println(accountStore.getAccount());
                    }
                });
    }
}
