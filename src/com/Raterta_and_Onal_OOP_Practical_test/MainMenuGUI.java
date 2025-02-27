package com.Raterta_and_Onal_OOP_Practical_test;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainMenuGUI {

    private ArrayList<Student> studentLists;
    private ArrayList<Subject> subjectLists;
    private JFrame frame;
    private DataManagement dataManager;

    public MainMenuGUI(DataManagement dataManager) {
    	
    	this.dataManager = dataManager;
    	
        // INITIALIZE THE STUDENT AND SUBJECT LISTS
        studentLists = new ArrayList<>();
        subjectLists = new ArrayList<>();
        
        // TITLE AND LAYOUT
        frame = new JFrame("Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        // BUTTONS
        JButton studentManagementButton = new JButton("Student Management");
        JButton subjectManagementButton = new JButton("Subject Management");
        JButton displayStudentStatusButton = new JButton("Display Student Status");
        JButton displaySubjectStatusButton = new JButton("Display Subject Status");
        JButton exitButton = new JButton("Exit");

        panel.add(studentManagementButton);
        panel.add(subjectManagementButton);
        panel.add(displaySubjectStatusButton);
        panel.add(displayStudentStatusButton);
        panel.add(exitButton);

        frame.add(panel);

        // CREATE INTANCES OF GUI CLASSES 
        StudentManagementGUI studentManagementGUI = new StudentManagementGUI(dataManager, studentLists, new Scanner(System.in), frame);
        SubjectManagementGUI subjectManagementGUI = new SubjectManagementGUI(dataManager, new Scanner(System.in), frame);
        DisplayStudentStatusGUI displayStudentStatusGUI = new DisplayStudentStatusGUI(dataManager, studentLists);
        DisplaySubjectStatusGUI displaySubjectStatusGUI = new DisplaySubjectStatusGUI(dataManager, subjectLists);
        
        // COLORS
        studentManagementButton.setBackground(Color.white);
        studentManagementButton.setForeground(Color.black);
        subjectManagementButton.setBackground(Color.white);
        subjectManagementButton.setForeground(Color.black);
        displaySubjectStatusButton.setBackground(Color.white);
        displaySubjectStatusButton.setForeground(Color.black);
        displayStudentStatusButton.setBackground(Color.white);
        displayStudentStatusButton.setForeground(Color.black);
        exitButton.setBackground(Color.white);
        exitButton.setForeground(Color.black);
        
        // FONTS
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        studentManagementButton.setFont(buttonFont);
        subjectManagementButton.setFont(buttonFont);
        displaySubjectStatusButton.setFont(buttonFont);
        displayStudentStatusButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);
        
        // WHEN THE BUTTONS ARE CLICKED
        studentManagementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentManagementGUI.display();
            }
        });

        subjectManagementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                subjectManagementGUI.display();
            }
        });

        displayStudentStatusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayStudentStatusGUI.display();
            }
        });

        displaySubjectStatusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	displaySubjectStatusGUI.display();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // METHODS FOR THE BUTTON
    public void displayMainMenu() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        DataManagement dataManager = new DataManagement();

        SwingUtilities.invokeLater(() -> {
            MainMenuGUI mainMenu = new MainMenuGUI(dataManager);
            mainMenu.displayMainMenu();
        });
    }

}
