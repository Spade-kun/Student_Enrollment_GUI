package com.Raterta_and_Onal_OOP_Practical_test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DisplaySubjectStatusGUI extends JFrame {
    private DataManagement dataManager;

    public DisplaySubjectStatusGUI(DataManagement dataManager, ArrayList<Subject> subjectLists) {
        this.dataManager = dataManager;

        // TITLE AND LAYOUT
        setTitle("Subject Status");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this frame only
    }

    // METHOD FOR THE BUTTON
    public void display() {
        ArrayList<Student> studentLists = dataManager.getStudentLists();
        ArrayList<Subject> subjectLists = dataManager.getSubjectLists();
        StringBuilder subjectInfo = new StringBuilder("Subject Status:\n");

        if (subjectLists.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No subjects available.", "Subject Status", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Subject subject : subjectLists) {
            boolean isEnrolled = false;

            for (Student student : studentLists) {
                if (student.getSubjects().contains(subject)) {
                    isEnrolled = true;
                    break;
                }
            }

            String status = isEnrolled ? "Enrolled" : "Not Enrolled";

            subjectInfo.append("-----------------------------------------\n");
            subjectInfo.append("Subject Code: " + subject.getSubjectCode() + "\n");
            subjectInfo.append("Subject Name: " + subject.getSubjectName() + "\n");
            subjectInfo.append("Status: " + status + "\n");
        }

        // CREATE TEXT AREA
        JTextArea textArea = new JTextArea(subjectInfo.toString());
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
