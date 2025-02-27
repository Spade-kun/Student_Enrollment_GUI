package com.Raterta_and_Onal_OOP_Practical_test;

import javax.swing.SwingUtilities;

public class Main {
	
    public static void main(String[] args) {
    	
    	// INSTANCE OF DATA MANAGER
        DataManagement dataManager = new DataManagement();

        // WHEN THE PROGRAM RUN IT WILL DISPLAY THE MAIN MENU 
        SwingUtilities.invokeLater(() -> {
            MainMenuGUI mainMenu = new MainMenuGUI(dataManager);
            mainMenu.displayMainMenu();
        });
    }
    
}
