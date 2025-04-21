package com.disha.app.fractal.ui;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        super();
        
        this.add(new FileMenu());
    }
}
