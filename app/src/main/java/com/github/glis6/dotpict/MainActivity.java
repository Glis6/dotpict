package com.github.glis6.dotpict;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements DrawingOptions.OnReset, DrawingOptions.OnShowCanvas, DrawingCanvas.OnShowOptions, DrawingOptions.OnPickColor, DrawingOptions.ToggleGrid {
    private final DrawingOptions drawingOptions = new DrawingOptions();

    private final DrawingCanvas drawingCanvas = new DrawingCanvas();

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(fragmentManager.findFragmentById(R.id.frame) == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frame, drawingCanvas)
                    .commit();
        }
        showCanvas();
    }

    @Override
    public void showCanvas() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame, drawingCanvas)
                .commit();
    }

    @Override
    public void showOptions() {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, drawingOptions)
                    .commit();
    }

    @Override
    public void reset() {
        drawingCanvas.reset();
    }

    @Override
    public void setColor(int color) {
        drawingCanvas.setColor(color);
    }

    @Override
    public boolean isGridToggled() {
        return drawingCanvas.isGridToggled();
    }

    @Override
    public void toggleGrid() {
        drawingCanvas.toggleGrid();
    }
}
