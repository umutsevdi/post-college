package com.sevdi.postcollege.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Post {
    protected String id;
    protected String credentialsId;
    protected String message;
    protected String image;
    protected LocalDateTime created;
    protected String postOwnerName;
    protected String profile;

    protected HashSet<String> upIdList;
    protected HashSet<String> downIdList;

    public Post(String id, String credentialsId, String message, String image, LocalDateTime created, HashSet<String> upIdList, HashSet<String> downIdList, String postOwnerName, String profile) {
        this.id = id;
        this.credentialsId = credentialsId;
        this.message = message;
        this.image = image;
        this.created = created;
        this.postOwnerName = postOwnerName;
        this.profile = profile;
        this.upIdList = upIdList;
        this.downIdList = downIdList;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>(5);
        m.put("credentialsId", credentialsId);
        m.put("message", message);
        m.put("image", image);
        m.put("created", new Timestamp(Date.from(created.atZone(ZoneId.systemDefault()).toInstant())));
        m.put("upIdList", new ArrayList<>(upIdList));
        m.put("downIdList", new ArrayList<>(downIdList));
        m.put("postOwnerName", postOwnerName);
        m.put("profile", profile);
        return m;
    }

    @SuppressWarnings("unchecked")
    public static Post from(Map<String, Object> m) {
        if (m == null) return null;
        return new Post(
                (String) m.get("Document Id"),
                (String) m.get("credentialsId"),
                (String) m.get("message"),
                (String) m.get("image"),
                LocalDateTime.ofInstant(((Timestamp) Objects.requireNonNull(m.get("created"))).toDate().toInstant(), ZoneId.systemDefault()),
                new HashSet<>((ArrayList<String>) Objects.requireNonNull(m.get("upIdList"))),
                new HashSet<>((ArrayList<String>) Objects.requireNonNull(m.get("downIdList"))),
                (String) m.get("postOwnerName"),
                (String) m.get("profile")
        );
    }

    @SuppressWarnings("unchecked")
    public static Post from(DocumentSnapshot m) {
        if (m == null) return null;
        return new Post(
                (String) m.get("Document Id"),
                (String) m.get("credentialsId"),
                (String) m.get("message"),
                (String) m.get("image"),
                LocalDateTime.ofInstant(((Timestamp) Objects.requireNonNull(m.get("created"))).toDate().toInstant(), ZoneId.systemDefault()),
                new HashSet<>((ArrayList<String>) Objects.requireNonNull(m.get("upIdList"))),
                new HashSet<>((ArrayList<String>) Objects.requireNonNull(m.get("downIdList"))),
                (String) m.get("postOwnerName"),
                (String) m.get("profile")
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(String credentialsId) {
        this.credentialsId = credentialsId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getPostOwnerName() {
        return postOwnerName;
    }

    public void setPostOwnerName(String postOwnerName) {
        this.postOwnerName = postOwnerName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getCount() {
        return upIdList.size() - downIdList.size();
    }

    public HashSet<String> getUpvIdList() {
        return upIdList;
    }

    public void setUpIdList(HashSet<String> upvIdList) {
        this.upIdList = upvIdList;
    }

    public HashSet<String> getDownIdList() {
        return downIdList;
    }

    public void setDownIdList(HashSet<String> downIdList) {
        this.downIdList = downIdList;
    }

    @NonNull
    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", credentialsId='" + credentialsId + '\'' +
                ", message='" + message + '\'' +
                ", count=" + getCount() +
                ", image=" + image +
                ", created=" + created +
                ", postOwnerName='" + postOwnerName + '\'' +
                ", profile=" + profile +
                '}';
    }
}
