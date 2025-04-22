package com.disha.app.fractal.logic;

import com.disha.converter.Border;

import java.io.Serializable;

public class Step implements Serializable {
    public final Border border;

    public Step(Border border) {
        this.border = border;
    }
}
