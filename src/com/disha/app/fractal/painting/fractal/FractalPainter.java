package com.disha.app.fractal.painting.fractal;

import com.disha.app.fractal.painting.AbstractPainter;
import com.disha.app.fractal.painting.Painter;
import com.disha.converter.Converter;
import com.disha.math.complex.ComplexNumber;
import com.disha.math.fractal.SetPointsComplex;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

public abstract class FractalPainter extends AbstractPainter implements Painter {
    protected SetPointsComplex set;
    public Color color = Color.BLACK;
    private final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    
    public FractalPainter(Converter converter, SetPointsComplex set) {
        super(converter);
        setSet(set);
    }
    
    public void setSet(SetPointsComplex set) {
        this.set = set;
    }
    
    public <T> T[][] calcDisplayResult(Function<ComplexNumber, T> function, Class<T> clazz){
        int width = converter.getWidthPixels();
        int height = converter.getHeightPixels();
        
        T[][] result = (T[][]) Array.newInstance(clazz, width, height);
        
        // делаю многопоток через ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        
        // Future<?> - это когда нам не важен тип возвращаемого результата у потока
        ArrayList<Future<?>> futures = new ArrayList<>();
        
        int columnsPerThread = width / THREAD_COUNT;
        
        for (int i = 0; i < THREAD_COUNT; i++) {

            final int xStart = i * columnsPerThread;
            final int xEnd = (i == THREAD_COUNT - 1) ? width : (i + 1) * columnsPerThread;

            futures.add(executor.submit(() -> {
                for (int xPixel = xStart; xPixel < xEnd; xPixel++) {
                    for (int yPixel = 0; yPixel < height; yPixel++) {
                        var point = new ComplexNumber(converter.xScr2Crt(xPixel), converter.yScr2Crt(yPixel));
                        result[xPixel][yPixel] = function.apply(point);
                    }
                }
            }));
        }
        
        // ждём завершения всех задач
        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        
        executor.shutdown();
        
        return result;
    }
}
