package com.disha.app.fractal.logic;

import com.disha.app.fractal.ui.PaintPanel;
import com.disha.converter.Converter;

import java.util.ArrayList;

public class HistoryManager {
    private final ArrayList<State> history = new ArrayList<>();
    private final Converter converter;
    private final PaintPanel mainPanel;

    public HistoryManager(Converter converter, PaintPanel mainPanel) {
        this.converter = converter;
        this.mainPanel = mainPanel;
    }

    private void checkHistorySize(){
        int MAX_STEP_COUNT = 100;
        if (history.size() >= MAX_STEP_COUNT) {
            history.removeFirst();
        }
    }

    public void addState(State state){
        history.addLast(state);
        checkHistorySize();
    }

    public void saveState(){
        var step = new State(converter.border.clone());
        addState(step);
    }

    public State getLastState(){
        return history.getLast();
    }

    public void undo(){
        if (history.size() > 1) {
            history.removeLast();
        }
        converter.border = history.getLast().border.clone();
        mainPanel.repaint();
    }

    public void setState(State state) {
        converter.border = state.border.clone();
        addState(state);
        mainPanel.repaint();
    }
}
