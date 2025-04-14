package com.disha.app.fractal.painting;

import java.awt.*;

public interface Painter {
    Dimension getSize();
    void setSize(Dimension size);
    void setSize(int width, int height);
    void paint(Graphics graphics);
}
