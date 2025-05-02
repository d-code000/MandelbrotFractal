package com.disha.app.fractal.logic;

import com.disha.converter.Border;

import java.io.Serializable;

public class State implements Serializable {
    public final Border border;

    public State(Border border) {
        this.border = border;
    }
}
