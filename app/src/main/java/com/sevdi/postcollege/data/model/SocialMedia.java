package com.sevdi.postcollege.data.model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class SocialMedia {
    private String twitter;
    private String facebook;
    private String instagram;
    private String github;
    private String website;

    public SocialMedia() {
        twitter = "";
        facebook = "";
        instagram = "";
        github = "";
        website = "";
    }

    public SocialMedia(String twitter, String facebook, String instagram, String github, String website) {
        this.twitter = twitter;
        this.facebook = facebook;
        this.instagram = instagram;
        this.github = github;
        this.website = website;
    }

    public Map<String, String> toMap() {
        Map<String, String> m = new HashMap<>(5);
        m.put("twitter", twitter);
        m.put("facebook", facebook);
        m.put("instagram", instagram);
        m.put("github", github);
        m.put("website", website);
        return m;
    }

    public static SocialMedia from(Map<String, Object> m) {
        if (m == null) return new SocialMedia();
        return new SocialMedia(
                (String) m.get("twitter"),
                (String) m.get("facebook"),
                (String) m.get("instagram"),
                (String) m.get("github"),
                (String) m.get("website")
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "SocialMedia{" +
                "twitter='" + twitter + '\'' +
                ", facebook='" + facebook + '\'' +
                ", instagram='" + instagram + '\'' +
                ", github='" + github + '\'' +
                ", website='" + website + '\'' +
                '}';
    }


    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
