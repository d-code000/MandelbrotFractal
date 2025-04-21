package com.disha.app.fractal.painting.fractal;

import com.disha.app.fractal.painting.AbstractPainter;
import com.disha.app.fractal.painting.Painter;
import com.disha.converter.Converter;
import com.disha.math.complex.ComplexNumber;
import com.disha.math.fractal.SetPointsComplex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    
    // Возвращает пиксель в формате ARGB
    protected abstract int drawPixel(ComplexNumber point);
    
    public BufferedImage calcImage(){
        int width = size.width;
        int height = size.height;
        
        if (width <= 0 || height <= 0) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
        
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // делаю многопоток через ExecutorService
        try (ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT)) {

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
                            image.setRGB(xPixel, yPixel, drawPixel(point));
                        }
                    }
                }));
            }

            // ждём завершения всех задач
            for (Future<?> f : futures) {
                f.get();
            }

            executor.shutdown();
        
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    @Override
    public void paint(Graphics graphics) {
        var image = calcImage();
        graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }
}
