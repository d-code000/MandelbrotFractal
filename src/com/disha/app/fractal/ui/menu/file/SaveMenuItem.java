package com.disha.app.fractal.ui.menu.file;

import com.disha.app.fractal.logic.HistoryManager;
import com.disha.app.fractal.painting.fractal.FractalPainter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Objects;

public class SaveMenuItem extends JMenuItem implements ActionListener {
    private static final String fractalExtension = "frac";
    private static final String[] imageExtensions = new String[] {"png", "jpg"};
    private static final String defaultExtension = fractalExtension;
    
    private final FractalPainter fractalPainter;
    private final HistoryManager historyManager;
    
    public SaveMenuItem(HistoryManager historyManager, FractalPainter fractalPainter) {
        super("Save");
        this.fractalPainter = fractalPainter;
        this.historyManager = historyManager;

        addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e) {
        var fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file");
        
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fractal files", fractalExtension));
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image", imageExtensions));
        fileChooser.setAcceptAllFileFilterUsed(false);
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            var file = fileChooser.getSelectedFile();
            var fileName = file.getName();
            if (!fileName.isEmpty()) {
                var split = file.getName().split("\\.");
                String extension;
                if (split.length >= 2) {
                    var lastSwitch = split[split.length - 1];
                    
                    if (Arrays.asList(imageExtensions).contains(lastSwitch)) {
                        extension = lastSwitch;
                    } else if (Objects.equals(lastSwitch, fractalExtension)) {
                        extension = lastSwitch;
                    } else {
                        extension = defaultExtension;
                    }
                } else {
                    fileName += "." + defaultExtension;
                    extension = defaultExtension;
                }
                
                file = new File(file.getParentFile(), fileName);
                
                if (Arrays.asList(imageExtensions).contains(extension)) {
                    saveAsImage(file, extension);
                }
                
                else {
                    saveAsFractal(file);
                }
            }
        }
    }
    
    private void saveAsFractal(File file){
        var step = historyManager.getLastState();
        
        try {
            var oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(step);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    private void saveAsImage(File file, String extension){
        var image = fractalPainter.calcImage();
        
        // Удаляем альфа-канал, заменяя его на белый
        var rgbImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        var g = rgbImage.createGraphics();
        g.drawImage(image, 0, 0, java.awt.Color.WHITE, null);
        g.dispose();
        image = rgbImage;

        try {
            ImageIO.write(image, extension, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
