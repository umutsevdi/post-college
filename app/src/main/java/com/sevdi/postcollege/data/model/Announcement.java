package com.sevdi.postcollege.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Announcement extends Post {
    LocalDateTime startTime;
    LocalDateTime deadline;

    public Announcement(String id, String credentialsId, String message, String image, LocalDateTime created, HashSet<String> upIdList, HashSet<String> downIdList, String postOwnerName, String profile, LocalDateTime startTime, LocalDateTime deadline) {
        super(id, credentialsId, message, image, created, upIdList, downIdList, postOwnerName, profile);
        this.startTime = startTime;
        this.deadline = deadline;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = super.toMap();
        m.put("startTime", new Timestamp(Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant())));
        m.put("deadline", new Timestamp(Date.from(deadline.atZone(ZoneId.systemDefault()).toInstant())));
        return m;
    }

    @SuppressWarnings("unchecked")
    public static Announcement from(Map<String, Object> m) {
        if (m == null) return null;
        Announcement a = new Announcement(
                (String) m.get("Document Id"),
                (String) m.get("credentialsId"),
                (String) m.get("message"),
                (String) m.get("image"),
                LocalDateTime.ofInstant(((Timestamp) Objects.requireNonNull(m.get("created"))).toDate().toInstant(), ZoneId.systemDefault()),
                new HashSet<>((ArrayList<String>) Objects.requireNonNull(m.get("upIdList"))),
                new HashSet<>((ArrayList<String>) Objects.requireNonNull(m.get("downIdList"))),
                (String) m.get("postOwnerName"),
                (String) m.get("profile"),
                null,
                null);
        if (m.get("startTime") != null && m.get("deadline") != null) {
            a.startTime = LocalDateTime.ofInstant(((Timestamp) Objects.requireNonNull(m.get("startTime"))).toDate().toInstant(), ZoneId.systemDefault());
            a.deadline = LocalDateTime.ofInstant(((Timestamp) Objects.requireNonNull(m.get("deadline"))).toDate().toInstant(), ZoneId.systemDefault());
        }
        return a;
    }

    @SuppressWarnings("unchecked")
    public static Announcement from(DocumentSnapshot m) {
        if (m == null) return null;
        Announcement a = new Announcement(
                (String) m.get("Document Id"),
                (String) m.get("credentialsId"),
                (String) m.get("message"),
                (String) m.get("image"),
                LocalDateTime.ofInstant(((Timestamp) Objects.requireNonNull(m.get("created"))).toDate().toInstant(), ZoneId.systemDefault()),
                new HashSet<>((ArrayList<String>) Objects.requireNonNull(m.get("upIdList"))),
                new HashSet<>((ArrayList<String>) Objects.requireNonNull(m.get("downIdList"))),
                (String) m.get("postOwnerName"),
                (String) m.get("profile"),
                null,
                null);
        if (m.get("startTime") != null && m.get("deadline") != null) {
            a.startTime = LocalDateTime.ofInstant(((Timestamp) Objects.requireNonNull(m.get("startTime"))).toDate().toInstant(), ZoneId.systemDefault());
            a.deadline = LocalDateTime.ofInstant(((Timestamp) Objects.requireNonNull(m.get("deadline"))).toDate().toInstant(), ZoneId.systemDefault());
        }
        return a;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "startTime=" + startTime +
                ", deadline=" + deadline +
                ", id='" + id + '\'' +
                ", credentialsId='" + credentialsId + '\'' +
                ", message='" + message + '\'' +
                ", image=" + image +
                ", created=" + created +
                ", postOwnerName='" + postOwnerName + '\'' +
                ", profile=" + profile +
                '}';
    }
}
