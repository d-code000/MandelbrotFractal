package main.java.ui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PaintPanel extends JPanel {
    
    private Consumer<Graphics> paintAction;
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (paintAction != null) {
            paintAction.accept(g);
        }
    }
    
}
