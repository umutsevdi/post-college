package com.sevdi.postcollege.data.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;

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
        m.put("startTime", startTime);
        m.put("deadline", deadline);
        return m;
    }

    public static Announcement from(Map<String, Object> m) {
        if (m == null) return null;
        Announcement a = (Announcement) Post.from(m);
        a.startTime = (LocalDateTime) m.get("startTime");
        a.deadline = (LocalDateTime) m.get("deadline");
        return a;
    }

    public static Announcement from(DocumentSnapshot m) {
        if (m == null) return null;
        Announcement a = (Announcement) Post.from(m);
        a.startTime = (LocalDateTime) m.get("startTime");
        a.deadline = (LocalDateTime) m.get("deadline");
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
