package com.Raterta_and_Onal_OOP_Practical_test;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DataManagement {
	
    private ArrayList<Student> studentLists;
    private ArrayList<Subject> subjectLists;

    public DataManagement() {
    	
    	// STORE THE STUDENTS AND SUBJECTS LISTS
        studentLists = new ArrayList<>();
        subjectLists = new ArrayList<>();
    }

    // METHODS
    public ArrayList<Student> getStudentLists() {
        return studentLists;
    }

    public ArrayList<Subject> getSubjectLists() {
        return subjectLists;
    }

    public void addStudent(Student student) {
        studentLists.add(student);
    }

    public void removeStudent(Student student) {
        studentLists.remove(student);
    }

    public void addSubject(Subject subject) {
        subjectLists.add(subject);
    }

    public void deleteSubject(Subject subject) {
        if (subject.hasEnrolledStudents()) {
            JOptionPane.showMessageDialog(null, "Subject \"" + subject.getSubjectName() + "\" has enrolled students and cannot be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            subjectLists.remove(subject);
        }
    }

    public void updateStudentData(Student student, Student newStudentData) {
    	
    	student.setCourse(newStudentData.getCourse());
        if (studentLists.contains(student)) {
            int index = studentLists.indexOf(student);
            studentLists.set(index, newStudentData);
        }
    }

    public void updateSubjectData(Subject subject, Subject newSubjectData) {
        if (subjectLists.contains(subject)) {
            int index = subjectLists.indexOf(subject);
            subjectLists.set(index, newSubjectData);
        }
    }
}
