/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package insertion_sort_emulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class SortSimulationPage extends JFrame {

    private JPanel arrayPanel, historyPanel;
    private JTextField inputField;
    private JButton startBtn, ascBtn, descBtn;
    private boolean ascending = true;
    private Timer timer;
    private List<int[]> steps;
    private int stepIndex;

    public SortSimulationPage() {
        initUI();
    }

    private void initUI() {
        setTitle("Insertion Sort Emulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // HEADER
        JLabel title = new JLabel("Insertion Sort Emulator", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(25, 24, 61));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        //CONTROL PANEL
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controls.setBackground(new Color(25, 24, 61));

        inputField = new JTextField("", 20);
        inputField.setFont(new Font("Poppins", Font.PLAIN, 16));
        controls.add(inputField);

        ascBtn = createButton("Ascending");
        descBtn = createButton("Descending");
        startBtn = createButton("Start");

        ascBtn.setBackground(new Color(255, 179, 71));
        descBtn.setBackground(new Color(180, 180, 180));

        ascBtn.addActionListener(e -> {
            ascending = true;
            ascBtn.setBackground(new Color(255, 179, 71));
            descBtn.setBackground(new Color(180, 180, 180));
        });
        descBtn.addActionListener(e -> {
            ascending = false;
            descBtn.setBackground(new Color(255, 179, 71));
            ascBtn.setBackground(new Color(180, 180, 180));
        });

        startBtn.addActionListener(e -> startSorting());

        JButton backBtn = createButton("Back");
        backBtn.setBackground(new Color(255, 77, 77));
        backBtn.addActionListener(e -> {
            new SortFrontPage().setVisible(true);
            dispose();
        });

        controls.add(ascBtn);
        controls.add(descBtn);
        controls.add(startBtn);
        controls.add(backBtn);

        add(controls, BorderLayout.SOUTH);

        //MAIN 
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        add(mainPanel, BorderLayout.CENTER);

        // Array display (top)
        arrayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
        arrayPanel.setBackground(Color.WHITE);
        arrayPanel.setBorder(BorderFactory.createTitledBorder("Current Step"));
        mainPanel.add(arrayPanel, BorderLayout.NORTH);

        // History display (scrollable)
        historyPanel = new JPanel();
        historyPanel.setLayout(new BoxLayout(historyPanel, BoxLayout.Y_AXIS));
        historyPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(historyPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Sorting History"));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Poppins", Font.BOLD, 15));
        btn.setForeground(Color.BLACK);
        btn.setBackground(new Color(255, 179, 71));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(140, 40));
        return btn;
    }

    private void startSorting() {
        int[] arr = parseInput(inputField.getText());
        if (arr.length == 0) {
            JOptionPane.showMessageDialog(this, "Enter valid comma-separated numbers (e.g. 5, -2, 3, 7)");
            return;
        }

        steps = getInsertionSortSteps(arr.clone(), ascending);
        stepIndex = 0;
        historyPanel.removeAll(); // Clear old records

        startBtn.setEnabled(false);
        ascBtn.setEnabled(false);
        descBtn.setEnabled(false);
        inputField.setEnabled(false);

        if (timer != null && timer.isRunning()) timer.stop();

        timer = new Timer(900, (ActionEvent e) -> {
            if (stepIndex < steps.size()) {
                int[] currentStep = steps.get(stepIndex);
                showArray(currentStep, stepIndex);
                addToHistory(currentStep, stepIndex);
                stepIndex++;
            } else {
                timer.stop();
                startBtn.setEnabled(true);
                ascBtn.setEnabled(true);
                descBtn.setEnabled(true);
                inputField.setEnabled(true);
            }
        });
        timer.start();
    }

    private void showArray(int[] arr, int step) {
        arrayPanel.removeAll();

        for (int i = 0; i < arr.length; i++) {
            JLabel lbl = new JLabel(String.valueOf(arr[i]), SwingConstants.CENTER);
            lbl.setFont(new Font("Poppins", Font.BOLD, 20));
            lbl.setPreferredSize(new Dimension(70, 70));
            lbl.setOpaque(true);
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            if (i < step) {
                lbl.setBackground(new Color(144, 238, 144)); // sorted green
            } else if (i == step) {
                lbl.setBackground(new Color(102, 153, 255)); // current
            } else {
                lbl.setBackground(Color.WHITE);
            }

            arrayPanel.add(lbl);
        }

        arrayPanel.revalidate();
        arrayPanel.repaint();
    }

    private void addToHistory(int[] arr, int step) {
        JPanel recordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        recordPanel.setBackground(step % 2 == 0 ? new Color(250, 250, 250) : new Color(240, 240, 240));

        JLabel stepLabel = new JLabel("Step " + (step + 1) + ": ");
        stepLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        recordPanel.add(stepLabel);

        for (int i = 0; i < arr.length; i++) {
            JLabel lbl = new JLabel(String.valueOf(arr[i]), SwingConstants.CENTER);
            lbl.setFont(new Font("Poppins", Font.PLAIN, 16));
            lbl.setPreferredSize(new Dimension(45, 35));
            lbl.setOpaque(true);
            lbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

            if (i < step) lbl.setBackground(new Color(144, 238, 144)); // green sorted
            else lbl.setBackground(Color.WHITE);

            recordPanel.add(lbl);
        }

        historyPanel.add(recordPanel);
        historyPanel.revalidate();
        historyPanel.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollPane scrollPane = (JScrollPane) historyPanel.getParent().getParent();
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        });
    }

    private List<int[]> getInsertionSortSteps(int[] arr, boolean ascending) {
        List<int[]> list = new ArrayList<>();
        list.add(arr.clone());

        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && (ascending ? arr[j] > key : arr[j] < key)) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
            list.add(arr.clone());
        }

        return list;
    }

    private int[] parseInput(String text) {
        try {
            String[] parts = text.trim().split("\\s*,\\s*");
            int[] arr = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                arr[i] = Integer.parseInt(parts[i]);
            }
            return arr;
        } catch (Exception e) {
            return new int[0];
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new SortSimulationPage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
