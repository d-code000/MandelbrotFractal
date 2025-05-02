package com.disha.app.fractal.logic;

import com.disha.app.fractal.ui.PaintPanel;
import com.disha.converter.Converter;

import java.util.ArrayList;

public class HistoryManager {
    private final ArrayList<Step> history = new ArrayList<>();
    private final Converter converter;
    private final PaintPanel mainPanel;

    public HistoryManager(Converter converter, PaintPanel mainPanel) {
        this.converter = converter;
        this.mainPanel = mainPanel;
    }

    private void checkHistorySize(){
        int MAX_STEP_COUNT = 1000;
        if (history.size() >= MAX_STEP_COUNT) {
            history.removeFirst();
        }
    }

    public void addStep(Step step){
        history.addLast(step);
        checkHistorySize();
    }

    public void saveState(){
        var step = new Step(converter.border.clone());
        addStep(step);
    }

    public Step getLastStep(){
        return history.getLast();
    }

    public void undo(){
        if (history.size() > 1) {
            history.removeLast();
        }
        converter.border = history.getLast().border.clone();
        mainPanel.repaint();
    }

    public void setState(Step step) {
        converter.border = step.border.clone();
        addStep(step);
        mainPanel.repaint();
    }
}
