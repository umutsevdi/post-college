package com.sevdi.postcollege.data.model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Education {

    private String university;
    private String faculty;
    private Degree degree;
    private Long startYear;
    private Long endYear;


    public Education(String university, String faculty, Degree degree, Long startYear, Long endYear) {
        this.university = university;
        this.faculty = faculty;
        this.degree = degree;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public Education(String university, String faculty, Long startYear, Long endYear) {
        this(university, faculty, Degree.BACHELOR, startYear, endYear);
    }

    public Education() {
        this("", "", Degree.BACHELOR, 0L, 0L);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>(5);
        m.put("university", university);
        m.put("faculty", faculty);
        m.put("degree", degree);
        m.put("startYear", startYear);
        m.put("endYear", endYear);
        return m;
    }

    public static Education from(Map<String, Object> m) {
        if (m == null) return null;
        return new Education(
                (String) m.get("university"),
                (String) m.get("faculty"),
                Degree.valueOf((String) m.get("degree")),
                (Long) m.get("startYear"),
                (Long) m.get("endYear")
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "Education{" +
                "university='" + university + '\'' +
                ", faculty='" + faculty + '\'' +
                ", degree=" + degree +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                '}';
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Long getStartYear() {
        return startYear;
    }

    public void setStartYear(Long startYear) {
        this.startYear = startYear;
    }

    public Long getEndYear() {
        return endYear;
    }

    public void setEndYear(Long endYear) {
        this.endYear = endYear;
    }
}
