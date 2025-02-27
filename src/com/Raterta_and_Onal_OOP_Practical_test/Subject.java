package com.Raterta_and_Onal_OOP_Practical_test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Subject {
    private String subjectCode;
    private String subjectName;
    private long id;
    private Map<Student, Double> enrolledStudents;

    // METHODS
    public Subject(long id, String subjectCode, String subjectName) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.enrolledStudents = new HashMap<>();
    }

    public Subject(String subjectCode, String subjectName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.enrolledStudents = new HashMap<>();
    }

    public void enrollStudent(Student student, double grade) {
        enrolledStudents.put(student, grade);
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public long getId() {
        return id;
    }

    public String getSubjectInfo() {
        return "\nSubject Code: " + subjectCode + "\nSubject Name: " + subjectName;
    }

    public Map<Student, Double> getGrades() {
        return enrolledStudents;
    }

    public Set<Student> getEnrolledStudents() {
        return enrolledStudents.keySet();
    }

    public boolean hasEnrolledStudents() {
        return !enrolledStudents.isEmpty();
    }


    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public boolean hasEnrolledStudent(Student student) {
        return enrolledStudents.containsKey(student);
    }
}
