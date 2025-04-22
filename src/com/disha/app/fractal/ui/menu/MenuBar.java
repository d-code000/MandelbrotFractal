package com.disha.app.fractal.ui.menu;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        super();
        
        this.add(new FileMenu());
    }
}
