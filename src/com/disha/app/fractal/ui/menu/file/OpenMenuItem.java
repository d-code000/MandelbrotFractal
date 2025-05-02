package com.disha.app.fractal.ui.menu.file;

import com.disha.app.fractal.logic.HistoryManager;
import com.disha.app.fractal.logic.State;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class OpenMenuItem extends JMenuItem implements ActionListener {
    private static final String fractalExtension = "frac";
    private final HistoryManager historyManager;

    public OpenMenuItem(HistoryManager historyManager) {
        super("Open");
        this.historyManager = historyManager;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open file");

        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal files", fractalExtension));
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            var file = fileChooser.getSelectedFile();
            if (file.exists() && file.getName().toLowerCase().endsWith("." + fractalExtension)) {
                openFractalFile(file);
            }
        }
    }

    private void openFractalFile(File file) {
        try {
            var ois = new ObjectInputStream(new FileInputStream(file));
            var step = (State) ois.readObject();
            ois.close();

            // Set the state using HistoryManager
            historyManager.setState(step);

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, 
                "Error opening file: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
