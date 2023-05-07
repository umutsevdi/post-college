package com.sevdi.postcollege.data.service;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

public class ImageStore {
    private static ImageStore store;
    private final StorageReference reference;
    private final FirebaseStorage storage;

    private ImageStore() {
        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();
    }

    public static ImageStore getInstance() {
        if (store == null) store = new ImageStore();
        return store;
    }


    public void getImage(Context context, String path, Consumer<Bitmap> onDownload) {
        System.out.println("getImage#" + context + " " + path);
        File file = new File(context.getFilesDir(), path);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            onDownload.accept(bitmap);
        } else {
            Objects.requireNonNull(file.getParentFile()).mkdirs();
            reference.child(path).getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(bytes);
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("DONE");
                onDownload.accept(bitmap);
            });
        }
    }

}