/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package insertion_sort_emulator;

import javax.swing.*;

/**
 * 
 * @author Carl Joseph
 */
public class InsertionSortEmulator {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Look & Feel error: " + e);
        }

        SwingUtilities.invokeLater(() -> new SortFrontPage().setVisible(true));
    }
}

