package com.disha.app.fractal.logic;

import java.util.ArrayList;

public class HistoryManager {
    private final ArrayList<Step> history = new ArrayList<>();
    
    public void addStep(Step step){
        int MAX_STEP_COUNT = 1000;
        if (history.size() >= MAX_STEP_COUNT) {
            history.removeFirst();
        }
        history.addLast(step);
    }
    
    public Step getLastStep(){
        return history.getLast();
    }
    
    public Step undo(){
        if (history.size() > 1) {
            history.removeLast();
        }
        
        return getLastStep();
    }
}
