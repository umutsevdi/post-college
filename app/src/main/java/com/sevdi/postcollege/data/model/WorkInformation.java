package com.sevdi.postcollege.data.model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class WorkInformation {
    private String city;
    private String country;
    private String company;

    public WorkInformation() {
        city = "";
        country = "";
        company = "";
    }

    public WorkInformation(String city, String country, String company) {
        this.city = city;
        this.country = country;
        this.company = company;
    }

    public Map<String, String> toMap() {
        Map<String, String> m = new HashMap<>(3);
        m.put("city", city);
        m.put("country", country);
        m.put("company", company);
        return m;
    }

    public static WorkInformation from(Map<String, Object> m) {
        if (m == null) return new WorkInformation();
        return new WorkInformation((String) m.get("city"), (String) m.get("country"), (String) m.get("company"));
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkInformation{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
