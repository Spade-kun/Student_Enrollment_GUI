package com.Raterta_and_Onal_OOP_Practical_test;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SubjectManagementGUI extends JFrame {
	
    private ArrayList<Student> studentLists = new ArrayList<>();
    private ArrayList<Subject> subjectLists = new ArrayList<>();
    private Scanner scanner;
    private JFrame frame;
    private DataManagement dataManager;
    private double studentAverage;

    public SubjectManagementGUI(DataManagement dataManager, Scanner scanner, JFrame frame) {

        this.scanner = scanner;
        this.frame = frame;
        this.dataManager = dataManager;
        this.studentLists = dataManager.getStudentLists(); 
        this.subjectLists = dataManager.getSubjectLists(); 
        
        // TITLE AND LAYOUT
        setTitle("Subject Management");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        // BUTTONS
        JButton addSubjectButton = new JButton("Add Subject");
        JButton enrollSubjectButton = new JButton("Enroll Subject");
        JButton addGradeButton = new JButton("Add Grade");
        JButton deleteSubjectButton = new JButton("Delete Subject");
        JButton displayAllSubjectsButton = new JButton("Display All Subjects");
        JButton returnToMainMenuButton = new JButton("Return to Main Menu");

        panel.add(addSubjectButton);
        panel.add(enrollSubjectButton);
        panel.add(addGradeButton);
        panel.add(deleteSubjectButton);
        panel.add(displayAllSubjectsButton);
        panel.add(returnToMainMenuButton);
        
        add(panel);
        
        // COLORS
        addSubjectButton.setBackground(Color.white);
        addSubjectButton.setForeground(Color.black);
        enrollSubjectButton.setBackground(Color.white);
        enrollSubjectButton.setForeground(Color.black);
        addGradeButton.setBackground(Color.white);
        addGradeButton.setForeground(Color.black);
        deleteSubjectButton.setBackground(Color.white);
        deleteSubjectButton.setForeground(Color.black);
        displayAllSubjectsButton.setBackground(Color.white);
        displayAllSubjectsButton.setForeground(Color.black);
        returnToMainMenuButton.setBackground(Color.white);
        returnToMainMenuButton.setForeground(Color.black);
        
        // FONTS
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        addSubjectButton.setFont(buttonFont);
        enrollSubjectButton.setFont(buttonFont);
        addGradeButton.setFont(buttonFont);
        deleteSubjectButton.setFont(buttonFont);
        displayAllSubjectsButton.setFont(buttonFont);
        returnToMainMenuButton.setFont(buttonFont);

        // BUTTONS
        addSubjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSubject();
            }
        });

        enrollSubjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enrollSubject();
            }
        });

        addGradeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addGrade(studentLists);
            }
        });

        deleteSubjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSubject(subjectLists, studentLists, scanner);
            }
        });


        displayAllSubjectsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllSubjects();
            }
        });
        
        returnToMainMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current window
                MainMenuGUI mainMenu = new MainMenuGUI(dataManager); // Open the main menu
                mainMenu.displayMainMenu();
            }
        });
    }

    //METHODS FOR BUTTONS
    private void addSubject() {
    	
        String input = JOptionPane.showInputDialog(null, "Enter subject information (add, Subject Code, Subject Name):");

        if (input == null) {
            return;
        }

        input = input.trim();
        String[] parts = input.split(", ");

        if (parts.length == 3 && parts[0].trim().equalsIgnoreCase("add")) {
            String subjectCode = parts[1].trim();
            String subjectName = parts[2].trim();

            if (subjectCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Subject Code cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (subjectName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Subject Name cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ArrayList<Subject> subjectLists = dataManager.getSubjectLists();

            boolean subjectCodeExists = subjectLists.stream()
                    .anyMatch(subject -> subject.getSubjectCode().equalsIgnoreCase(subjectCode));

            if (!subjectCodeExists) {
                Subject newSubject = new Subject(subjectCode, subjectName);
                dataManager.addSubject(newSubject);
                JOptionPane.showMessageDialog(null, "Subject added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Subject Code \"" + subjectCode + "\" already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enrollSubject() {
    	
        String input = JOptionPane.showInputDialog(null, "Enter enrollment information (enroll, Student ID, Subject Code):");

        if (input == null) {
            return;
        }

        input = input.trim();
        String[] parts = input.split(", ");

        if (parts.length == 3 && parts[0].trim().equalsIgnoreCase("enroll")) {
            String studentIDStr = parts[1].trim();
            String subjectCode = parts[2].trim();

            if (studentIDStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Student ID cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (subjectCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Subject Code cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                long studentID = Long.parseLong(studentIDStr);
                
                ArrayList<Student> studentLists = dataManager.getStudentLists();
                ArrayList<Subject> subjectLists = dataManager.getSubjectLists();

                Student studentToEnroll = null;
                for (Student student : studentLists) {
                    if (student.getStudentID() == studentID) {
                        studentToEnroll = student;
                        break;
                    }
                }

                Subject subjectToEnroll = findSubjectByCode(subjectLists, subjectCode);

                if (studentToEnroll != null && subjectToEnroll != null) {
                    boolean isAlreadyEnrolled = studentToEnroll.isEnrolledInSubject(subjectCode);

                    if (isAlreadyEnrolled) {
                        JOptionPane.showMessageDialog(null, "Student \"" + studentToEnroll.getName() + "\" is already enrolled in subject \"" + subjectToEnroll.getSubjectName() + "\".", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        studentToEnroll.enrollSubject(subjectToEnroll);
                        subjectToEnroll.hasEnrolledStudent(studentToEnroll);  // Update subject's enrolled students
                        JOptionPane.showMessageDialog(null, "Enrolled student \"" + studentToEnroll.getName() + "\" in subject \"" + subjectToEnroll.getSubjectName() + "\".", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Student or subject not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid student ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input format. Please use 'enroll, studentID, subjectCode'.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addGrade(ArrayList<Student> studentLists) {
    	
        String input = JOptionPane.showInputDialog(null, "Enter student ID and subject code to add grade (add, Student ID, Subject Code, Grade):");

        if (input == null) {
            return;
        }

        input = input.trim();
        String[] parts = input.split(", ");

        if (parts.length != 4 || !parts[0].trim().equalsIgnoreCase("add")) {
            JOptionPane.showMessageDialog(null, "Invalid input format. Please use 'add, Student ID, Subject Code, Grade'.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String studentIDStr = parts[1].trim();
        String subjectCode = parts[2].trim();
        String gradeStr = parts[3].trim();

        if (studentIDStr.isEmpty() || subjectCode.isEmpty() || gradeStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student ID, Subject Code, and Grade cannot be blank.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            long studentID = Long.parseLong(studentIDStr);
            double grade = Double.parseDouble(gradeStr);
            StudentManagementGUI studentManagementGUI = new StudentManagementGUI( dataManager, studentLists, scanner, frame);
            Student student = studentManagementGUI.findStudentByID(studentLists, studentID);

            if (student != null) {
                boolean isEnrolled = false;
                for (Subject enrolledSubject : student.getSubjects()) {
                    if (enrolledSubject.getSubjectCode().equals(subjectCode)) {
                        isEnrolled = true;
                        break;
                    }
                }

                if (!isEnrolled) {
                    JOptionPane.showMessageDialog(null, "The student \"" + student.getName() + "\" is not enrolled in subject \"" + subjectCode + "\".", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (grade >= 0 && grade <= 100) {
                        boolean alreadyGraded = student.isAlreadyGraded(subjectCode, grade);
                        if (alreadyGraded) {
                            JOptionPane.showMessageDialog(null, "The student \"" + student.getName() + "\" is already graded in subject \"" + subjectCode + "\".", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            student.addGrade(subjectCode, grade);
                            student.calculateStudentAverage();

                            JOptionPane.showMessageDialog(null, "Grade added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showMessageDialog(null, "Remarks computed for student \"" + student.getName() + "\": " + student.getRemarks(), "Information", JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showMessageDialog(null, "Student Average for \"" + student.getName() + "\": " + student.getStudentAverage(), "Information", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid grade. Grade must be between 0 and 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid student ID or grade format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


	private void deleteSubject(ArrayList<Subject> subjectLists, ArrayList<Student> studentLists, Scanner scanner) {
		
        String input = JOptionPane.showInputDialog(null, "Enter Subject Code to delete (delete, Subject Code):");

        if (input == null) {
            return;
        }

        input = input.trim();
        String[] parts = input.split(", ");

        if (parts.length == 2 && parts[0].trim().equalsIgnoreCase("delete")) {
            String subjectCode = parts[1].trim();

            Subject subjectToDelete = findSubjectByCode(subjectLists, subjectCode);

            if (subjectToDelete != null) {
                if (hasEnrolledStudents(subjectToDelete, studentLists)) {
                    JOptionPane.showMessageDialog(null, "Subject \"" + subjectCode + "\" has enrolled students and cannot be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    dataManager.deleteSubject(subjectToDelete);
                    JOptionPane.showMessageDialog(null, "Subject \"" + subjectCode + "\" deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Subject not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input format. Please use 'delete, subjectCode'.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean hasEnrolledStudents(Subject subject, ArrayList<Student> studentLists) {
        for (Student student : studentLists) {
            if (student.isEnrolledInSubject(subject.getSubjectCode())) {
                return true;
            }
        }
        return false;
    }

    private void displayAllSubjects() {
    	
        ArrayList<Subject> subjectLists = dataManager.getSubjectLists();

        if (subjectLists.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no subjects to display.", "Subjects", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder subjectsInfo = new StringBuilder("All Subjects:\n");
            for (Subject subject : subjectLists) {
                subjectsInfo.append(subject.getSubjectInfo()).append("\n\n");
            }

            JTextArea textArea = new JTextArea(subjectsInfo.toString());
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 400));

            JOptionPane.showMessageDialog(null, scrollPane, "Subjects", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public Subject findSubjectByCode(ArrayList<Subject> subjectLists, String subjectCode) {

        for (Subject subject : subjectLists) {
            if (subject.getSubjectCode().equals(subjectCode)) {
                return subject;
            }
        }
        return null;
    }

    public void display() {
        setVisible(true);
    }
    
}
