package com.sevdi.postcollege.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private String id;
    private String credentialsId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String image;

    /* Details */
    private String phoneNumber;
    private Education education;
    private WorkInformation workInformation;
    private SocialMedia socialMedia;

    public Account(String id, String credentialsId, String firstName, String lastName, String email, String password, String phoneNumber, Education education, WorkInformation workInformation, SocialMedia socialMedia, String image) {
        this.id = id;
        this.credentialsId = credentialsId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.education = education;
        this.workInformation = workInformation;
        this.socialMedia = socialMedia;
        this.image = image;

    }

    public Account(String credentialsId, String firstName, String lastName, String email, String password, String university, String faculty, Long startYear, Long endYear) {
        this(null, credentialsId, firstName, lastName, email, password, "",
                new Education(university, faculty, Degree.BACHELOR, startYear, endYear
                ), new WorkInformation(), new SocialMedia(), null);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>(10);
        m.put("credentialsId", credentialsId);
        m.put("firstName", firstName);
        m.put("lastName", lastName);
        m.put("email", email);
        m.put("password", password);
        m.put("phoneNumber", phoneNumber);
        m.put("education", education.toMap());
        m.put("workInformation", workInformation.toMap());
        m.put("social", socialMedia.toMap());
        m.put("image", image);
        return m;
    }

    @SuppressWarnings("unchecked")
    public static Account from(DocumentSnapshot m) {
        return new Account(
                (String) m.get("Document Id"),
                (String) m.get("credentialsId"),
                (String) m.get("firstName"),
                (String) m.get("lastName"),
                (String) m.get("email"),
                (String) m.get("password"),
                (String) m.get("phoneNumber"),
                Education.from((Map<String, Object>) m.get("education")),
                WorkInformation.from((Map<String, Object>) m.get("workInformation")),
                SocialMedia.from((Map<String, Object>) m.get("social")),
                (String) m.get("image")
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", credentialsId='" + credentialsId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image=" + image +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", education=" + education +
                ", workInformation=" + workInformation +
                ", socialMedia=" + socialMedia +
                '}';
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public WorkInformation getWorkInformation() {
        return workInformation;
    }

    public void setWorkInformation(WorkInformation workInformation) {
        this.workInformation = workInformation;
    }

    public SocialMedia getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMedia socialMedia) {
        this.socialMedia = socialMedia;
    }


}
