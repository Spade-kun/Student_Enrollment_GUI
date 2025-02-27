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

public class StudentManagementGUI extends JFrame {
	
    private ArrayList<Student> studentLists = new ArrayList<>();
    private Scanner scanner;
    private JFrame frame;
    private DataManagement dataManager;
    
    public StudentManagementGUI(DataManagement dataManager, ArrayList<Student> studentLists, Scanner scanner, JFrame frame) {
    	
        this.studentLists = studentLists;
        this.scanner = scanner;
        this.frame = frame;
        this.dataManager = dataManager;
        
        // TITLE AND LAYOUT
        setTitle("Student Management");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        // BUTTONS
        JButton addStudentButton = new JButton("Add Student");
        JButton updateStudentButton = new JButton("Update Student");
        JButton deleteStudentButton = new JButton("Delete Student");
        JButton searchStudentButton = new JButton("Search Student");
        JButton displayAllStudentsButton = new JButton("Display All Students");
        JButton returnToMainMenuButton = new JButton("Return to Main Menu");

        panel.add(addStudentButton);
        panel.add(updateStudentButton);
        panel.add(deleteStudentButton);
        panel.add(searchStudentButton);
        panel.add(displayAllStudentsButton);
        panel.add(returnToMainMenuButton);

        add(panel);
        
        // COLORS
        addStudentButton.setBackground(Color.white);
        addStudentButton.setForeground(Color.black);
        updateStudentButton.setBackground(Color.white);
        updateStudentButton.setForeground(Color.black);
        deleteStudentButton.setBackground(Color.white);
        deleteStudentButton.setForeground(Color.black);
        searchStudentButton.setBackground(Color.white);
        searchStudentButton.setForeground(Color.black);
        displayAllStudentsButton.setBackground(Color.white);
        displayAllStudentsButton.setForeground(Color.black);
        returnToMainMenuButton.setBackground(Color.white);
        returnToMainMenuButton.setForeground(Color.black);
        
        // FONTS
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        addStudentButton.setFont(buttonFont);
        updateStudentButton.setFont(buttonFont);
        deleteStudentButton.setFont(buttonFont);
        searchStudentButton.setFont(buttonFont);
        displayAllStudentsButton.setFont(buttonFont);
        returnToMainMenuButton.setFont(buttonFont);
        
        // BUTTONS
        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent(studentLists);
            }
        });

        updateStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStudent(studentLists, scanner);
            }
        });

        deleteStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudent(studentLists, scanner);
            }
        });

        searchStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchStudent(studentLists, scanner);
            }
        });

        displayAllStudentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllStudents();
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

    // METHODS FOR BUTTONS
    private void addStudent(ArrayList<Student> studentLists) {
       
        ArrayList<Student> dataManagerStudentLists = dataManager.getStudentLists();

        String input = JOptionPane.showInputDialog("Enter student information (add, Student ID, Name, Address, Age, Course):");
        String[] parts = input.split(", ");

        if (parts.length == 6 && parts[0].trim().equalsIgnoreCase("add")) {
            try {
                long studentID = Long.parseLong(parts[1].trim());
                String name = parts[2].trim();
                String address = parts[3].trim();
                int age = Integer.parseInt(parts[4].trim());
                String course = parts[5].trim(); 

                if (studentID >= 0 && age >= 0) {
                    if (!name.isEmpty() && !address.isEmpty() && !course.isEmpty()) {
                        boolean studentExists = false;

                        for (Student student : dataManagerStudentLists) {
                            if (student.getStudentID() == studentID) {
                                studentExists = true;
                                break;
                            }
                        }

                        if (studentExists) {
                            JOptionPane.showMessageDialog(null, "Student with ID " + studentID + " already exists.");
                        } else {
                            dataManagerStudentLists.add(new Student(studentID, name, address, age, course));
                            JOptionPane.showMessageDialog(null, "Student added successfully.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Name, Address, and Course cannot be blank.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Student ID and age cannot be negative.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid student ID or age format.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input format.");
        }
    }

    private void updateStudent(ArrayList<Student> studentLists, Scanner scanner) {
    	
    	ArrayList<Student> dataManagerStudentLists = dataManager.getStudentLists();
        String input = JOptionPane.showInputDialog("Enter student information (update, Student ID, Name, Address, Age, Course):");
        String[] parts = input.split(", ");

        if (parts.length == 6 && parts[0].trim().equalsIgnoreCase("update")) {
            try {
                long studentID = Long.parseLong(parts[1].trim());
                Student studentToUpdate = findStudentByID(dataManagerStudentLists, studentID);

                if (studentToUpdate != null) {
                    String updatedName = parts[2].trim();
                    String updatedAddress = parts[3].trim();
                    int updatedAge = Integer.parseInt(parts[4].trim());
                    String updatedCourse = parts[5].trim();

                    if (!updatedName.trim().isEmpty() && !updatedAddress.trim().isEmpty()) {
                        for (Student student : dataManagerStudentLists) {
                            if (!student.equals(studentToUpdate) && student.getName().equalsIgnoreCase(updatedName)) {
                                JOptionPane.showMessageDialog(null, "Student named \"" + updatedName + "\" already exists.");
                                return;
                            }
                        }

                        for (Student student : dataManagerStudentLists) {
                            if (!student.equals(studentToUpdate) && student.getStudentID() == studentID) {
                                JOptionPane.showMessageDialog(null, "Student ID \"" + studentID + "\" already exists.");
                                return;
                            }
                        }

                        studentToUpdate.setStudentID(studentID);
                        studentToUpdate.setName(updatedName);
                        studentToUpdate.setAddress(updatedAddress);
                        studentToUpdate.setAge(updatedAge);
                        studentToUpdate.setCourse(updatedCourse);

                        JOptionPane.showMessageDialog(null, "Student information updated successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Name, Address, and Course cannot be blank.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Student not found.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid student ID or age format.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input format.");
        }
    }



    private void deleteStudent(ArrayList<Student> studentLists, Scanner scanner) {
        ArrayList<Student> dataManagerStudentLists = dataManager.getStudentLists();
        String input = JOptionPane.showInputDialog("Enter Student ID to delete (delete, Student ID):");
        String[] parts = input.split(", ");

        if (parts.length == 2 && parts[0].trim().equalsIgnoreCase("delete")) {
            try {
                long deleteStudentID = Long.parseLong(parts[1].trim());
                Student studentToDelete = findStudentByID(dataManagerStudentLists, deleteStudentID);

                if (studentToDelete != null) {
                    dataManagerStudentLists.remove(studentToDelete); // Remove from the data manager
                    studentLists.remove(studentToDelete); // Optionally, remove from the local list too
                    JOptionPane.showMessageDialog(null, "Student deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Student not found.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid student ID format.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input format. Please use 'delete, studentID'.");
        }
    }



    private void searchStudent(ArrayList<Student> studentLists, Scanner scanner) {
    	
    	ArrayList<Student> dataManagerStudentLists = dataManager.getStudentLists();
        String input = JOptionPane.showInputDialog("Enter Student ID to search (search, Student ID):");
        String[] parts = input.split(", ");

        if (parts.length == 2 && parts[0].trim().equalsIgnoreCase("search")) {
            try {
                long searchStudentID = Long.parseLong(parts[1].trim());
                Student studentToSearch = findStudentByID(dataManagerStudentLists, searchStudentID);

                if (studentToSearch != null) {
                    JOptionPane.showMessageDialog(null, "Student found:\n" + studentToSearch.getStudentInfo(), "Student Found", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No student found.", "Student Not Found", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid student ID format.", "Invalid Format", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input format. Please use 'search, studentID'.", "Invalid Format", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void displayAllStudents() {
    	
        ArrayList<Student> dataManagerStudentLists = dataManager.getStudentLists();
        
        if (dataManagerStudentLists.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no students to display.", "No Students Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder studentsInfo = new StringBuilder("All Students:\n");
            for (Student student : dataManagerStudentLists) {
                studentsInfo.append(student.getStudentInfo()).append("\n\n");
            }

            JTextArea textArea = new JTextArea(studentsInfo.toString());
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 400));

            JOptionPane.showMessageDialog(null, scrollPane, "Students", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public Student findStudentByID(ArrayList<Student> studentLists, long studentID) {
        for (Student student : studentLists) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null; 
    }

    public void displayEnrolledStudents(ArrayList<Student> studentLists, ArrayList<Subject> subjectLists) {
    	
        if (studentLists.isEmpty() || subjectLists.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Either students or subjects are empty.", "Empty Data", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder enrolledStudentsInfo = new StringBuilder();
            enrolledStudentsInfo.append("Enrolled Students:\n");

            for (Student student : studentLists) {
                enrolledStudentsInfo.append("Student: ").append(student.getName()).append(" (ID: ").append(student.getStudentID()).append(")\n");
                enrolledStudentsInfo.append("Course: ").append(student.getCourse()).append("\n\n");
                enrolledStudentsInfo.append("Subjects Enrolled:\n");

                for (Subject subject : student.getSubjects()) {
                    enrolledStudentsInfo.append("Subject Code: ").append(subject.getSubjectCode()).append("\n");
                    enrolledStudentsInfo.append("Subject Name: ").append(subject.getSubjectName()).append("\n");
                    enrolledStudentsInfo.append("Grades: ").append(subject.getGrades()).append("\n\n");
                }
                enrolledStudentsInfo.append("\n");
            }

            JTextArea textArea = new JTextArea(enrolledStudentsInfo.toString());
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(null, scrollPane, "Enrolled Students and Subjects", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public void display() {
        setVisible(true);
    }
    
}
