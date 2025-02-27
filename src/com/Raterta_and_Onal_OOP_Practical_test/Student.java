package com.Raterta_and_Onal_OOP_Practical_test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Student {
	
    private long studentID;
    private String name;
    private int age;
    private String address;
    private ArrayList<Subject> subjectLists = new ArrayList<>();
    private String course;
    private String remarks;
    private Map<String, Double> grades = new HashMap<>();
    private double studentAverage; 

    // METHODS
    public Student(long studentID, String name, String address, int age, String course) {
        this.studentID = studentID;
        this.name = name;
        this.age = age;
        this.address = address;
        this.course = course;
        this.remarks = "";
        this.studentAverage = 0.0;
    }

    public Student(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public long getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<Subject> getSubjects() {
        return subjectLists;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void enrollSubject(Subject subject) {
        subjectLists.add(subject);
    }

    public String getStudentInfo() {
        return "Student ID : " + studentID + "\nName          : " + name + "\nAddress     : " + address + "\nAge             : " + age + "\nCourse       : " + course;
    }

    public void addGrade(String subjectCode, double grade) {
        grades.put(subjectCode, grade);
        calculateStudentAverage();
    }

    public double getGrade(String subjectCode) {
        return grades.getOrDefault(subjectCode, 0.0);
    }

    public boolean isEnrolledInSubject(String subjectCode) {
        for (Subject subject : subjectLists) {
            if (subject.getSubjectCode().equals(subjectCode)) {
                return true;
            }
        }
        return false;
    }

    public void computeRemarks() {
        double studentAverage = calculateStudentAverage();

        if (studentAverage <= 1.00) {
            remarks = "Excellent";
        } else if (studentAverage <= 1.50) {
            remarks = "Very Good";
        } else if (studentAverage <= 2.00) {
            remarks = "Good";
        } else if (studentAverage <= 3.00) {
            remarks = "Pass";
        } else {
            remarks = "Fail";
        }
    }


    public double getStudentAverage() {
        return studentAverage;
    }

    public boolean isAlreadyGraded(String subjectCode, double grade) {
        return grades.containsKey(subjectCode) && grades.get(subjectCode) == grade;
    }

    public double calculateStudentAverage() {
        double totalGrades = 0;
        int numGrades = 0;

        for (Double grade : grades.values()) {
            totalGrades += grade;
            numGrades++;
        }

        if (numGrades > 0) {
            studentAverage = totalGrades / numGrades;
        } else {
            studentAverage = 0.0;
        }
		return numGrades;
    }

    public Map<Subject, Double> getGrades() {
        Map<Subject, Double> enrolledSubjects = new HashMap<>();
        for (Subject subject : subjectLists) {
            enrolledSubjects.put(subject, getGrade(subject.getSubjectCode()));
        }
        return enrolledSubjects;
    }

    public double computeAverageGrade() {
        double totalGrades = 0.0;
        int totalSubjects = 0;

        for (Subject subject : subjectLists) {
            double grade = getGrade(subject.getSubjectCode());
            if (grade > 0.0) {
                totalGrades += grade;
                totalSubjects++;
            }
        }

        if (totalSubjects > 0) {
            return totalGrades / totalSubjects;
        } else {
            return 0.0; 
        }
    }

    public void enrollInSubject(String subjectCode) {
        for (Subject subject : subjectLists) {
            if (subject.getSubjectCode().equalsIgnoreCase(subjectCode)) {
                // Subject is already enrolled
                return;
            }
        }

        Subject subject = null;
        for (Subject s : subjectLists) {
            if (s.getSubjectCode().equalsIgnoreCase(subjectCode)) {
                subject = s;
                break;
            }
        }

        if (subject != null) {
            subjectLists.add(subject);
            subject.enrollStudent(this, this.getStudentID());
        }
    }

}
