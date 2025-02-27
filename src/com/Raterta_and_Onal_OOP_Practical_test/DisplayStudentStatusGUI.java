package com.Raterta_and_Onal_OOP_Practical_test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DisplayStudentStatusGUI extends JFrame {
	
	private DataManagement dataManager;
	
    public DisplayStudentStatusGUI(DataManagement dataManagement, ArrayList<Student> studentLists) {
    	
    	this.dataManager = dataManagement;
    	
        // TITLE AND LAYOUT
        setTitle("Students with Subjects and Grades");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this frame only
    }
    
    // METHOD FOR THE BUTTON
    public void display() {
        ArrayList<Student> studentLists = dataManager.getStudentLists();
        if (studentLists.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No students to display.", "Students with Subjects and Grades", JOptionPane.INFORMATION_MESSAGE);
            return; 
        }

        StringBuilder studentsInfo = new StringBuilder("All Students with Subjects and Grades:\n");
        for (Student student : studentLists) {
            studentsInfo.append("-----------------------------------------\n");
            studentsInfo.append("Student ID     : " + student.getStudentID() + "\n");
            studentsInfo.append("Name           : " + student.getName() + "\n");
            studentsInfo.append("Address        : " + student.getAddress() + "\n");
            studentsInfo.append("Age            : " + student.getAge() + "\n");
            studentsInfo.append("Course         : " + student.getCourse() + "\n");

            for (Subject subject : student.getSubjects()) {
                studentsInfo.append("-----------------------------------------\n");
                studentsInfo.append("Subject Code   : " + subject.getSubjectCode() + "\n");
                studentsInfo.append("Subject Name   : " + subject.getSubjectName() + "\n");

                Map<Student, Double> grades = subject.getGrades();
                for (Map.Entry<Student, Double> entry : grades.entrySet()) {
                    if (entry.getKey() == student) {
                        studentsInfo.append("Your Grade     : " + entry.getValue() + "\n");
                    }
                }

                student.computeRemarks();
                studentsInfo.append("Remarks        : " + student.getRemarks() + "\n");

                studentsInfo.append("Student Average: " + String.format("%.2f", student.getStudentAverage()) + "\n");
            }
            
            studentsInfo.append("-----------------------------------------\n\n");
        }

        // CREATE A TEXT AREA
        JTextArea textArea = new JTextArea(studentsInfo.toString());
        textArea.setEditable(false);

        // FOR SCROLL PANE
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // BUTTONS
        JButton closeButton = new JButton("Close");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        setVisible(true);
    }
}
