package com.sevdi.postcollege.ui.dialog;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.sevdi.postcollege.R;
import com.sevdi.postcollege.data.model.Account;
import com.sevdi.postcollege.data.model.Announcement;
import com.sevdi.postcollege.data.model.Post;
import com.sevdi.postcollege.data.service.AccountStore;
import com.sevdi.postcollege.data.service.PostStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

public class PostDialogFragment extends DialogFragment {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private boolean defaultAnnouncement;
    private EditText message;
    private ImageView imageView;
    private Button imageButton;

    private Switch isAnnouncement;
    private Button startDate;
    private Button endDate;

    private Button send;
    LocalDateTime startDateResult;
    LocalDateTime endDateResult;

    public PostDialogFragment(boolean defaultAnnouncement) {
        super();
        this.defaultAnnouncement = defaultAnnouncement;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_dialog, container, false);

        message = view.findViewById(R.id.editTextTextMultiLine);
        imageView = view.findViewById(R.id.imagePreview);
        imageButton = view.findViewById(R.id.uploadImage);
        isAnnouncement = view.findViewById(R.id.announcementSwitch);
        startDate = view.findViewById(R.id.start_date);
        endDate = view.findViewById(R.id.end_date);
        send = view.findViewById(R.id.send);

        imageView.setVisibility(View.GONE);
        isAnnouncement.setOnCheckedChangeListener((i, isChecked) -> {
            startDate.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            endDate.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        isAnnouncement.toggle();
        if (!defaultAnnouncement) isAnnouncement.toggle();

        startDate.setOnClickListener(i -> showDateTimePicker(true));
        endDate.setOnClickListener(i -> showDateTimePicker(false));

        imageButton.setOnClickListener(i -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);//1 pick image
                }
        );
        send.setOnClickListener(i -> {
            if (message.getText().toString().isEmpty() ||
                    (isAnnouncement.isChecked() && (startDateResult == null || endDateResult == null))) {
                new AlertDialog.Builder(
                        view.getContext())
                        .setTitle("Can not post!")
                        .setMessage("Please fill all required fields!")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }
            Account account = AccountStore.getInstance().getAccount();
            try {
                if (isAnnouncement.isChecked()) {
                    Announcement a = new Announcement(null, account.getCredentialsId(),
                            message.getText().toString(), null, LocalDateTime.now(),
                            new HashSet<>(Collections.singleton(account.getCredentialsId())), new HashSet<>(),
                            account.getFirstName() + " " + account.getLastName(),
                            account.getImage(), startDateResult, endDateResult);
                    PostStore.announcementInstance().publish(a, j -> Toast.makeText(view.getContext(), "Post published successfully", Toast.LENGTH_SHORT).show());
                } else {
                    Post a = new Post(null, account.getCredentialsId(),
                            message.getText().toString(), null, LocalDateTime.now(),
                            new HashSet<>(Collections.singleton(account.getCredentialsId())), new HashSet<>(),
                            account.getFirstName() + " " + account.getLastName(),
                            account.getImage());
                    PostStore.postInstance().publish(a, j -> Toast.makeText(view.getContext(), "Post published successfully", Toast.LENGTH_SHORT).show());
                }
                Toast.makeText(view.getContext(), "Sending", Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getDialog()).dismiss();
            } catch (Exception e) {
                new AlertDialog.Builder(
                        view.getContext())
                        .setTitle("Something went wrong")
                        .setMessage(e.getMessage())
                        .setPositiveButton("OK", null)
                        .show();
            }

        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageURI(uri);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    private void showDateTimePicker(boolean isStart) {
        LocalDateTime now = LocalDateTime.now();
        if (isStart) {
            new DatePickerDialog(
                    getContext(),
                    (datePicker, year, month, dayOfMonth) -> {
                        startDateResult = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0);
                        startDate.setText(startDateResult.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        new TimePickerDialog(
                                getContext(),
                                (timePicker, hourOfDay, minute) -> {
                                    startDateResult = startDateResult.withHour(hourOfDay).withMinute(minute);
                                    startDate.setText(startDateResult.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                                },
                                now.getHour(),
                                now.getMinute(),
                                true
                        ).show();
                    },
                    now.getYear(),
                    now.getMonthValue() - 1,
                    now.getDayOfMonth()
            ).show();
        } else {
            new DatePickerDialog(
                    getContext(),
                    (datePicker, year, month, dayOfMonth) -> {
                        endDateResult = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0);
                        endDate.setText(endDateResult.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        new TimePickerDialog(
                                getContext(),
                                (timePicker, hourOfDay, minute) -> {
                                    endDateResult = endDateResult.withHour(hourOfDay).withMinute(minute);
                                    endDate.setText(endDateResult.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                                },
                                now.getHour(),
                                now.getMinute(),
                                true
                        ).show();
                    },
                    now.getYear(),
                    now.getMonthValue() - 1,
                    now.getDayOfMonth()
            ).show();
        }
    }

}
