package main.java.ui;

import javax.swing.*;

public class MainWindow extends JFrame {
    private PaintPanel mainPanel;
    
    public MainWindow() {
        setVisible(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initialComponents();
    }
    
    private void initialComponents(){
        mainPanel = new PaintPanel();
        
        mainPanel.setPaintAction(graphics -> {
            
        });
        
        this.add(mainPanel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
