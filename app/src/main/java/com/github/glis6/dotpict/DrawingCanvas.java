package com.github.glis6.dotpict;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DrawingCanvas extends Fragment implements DrawingOptions.OnReset, DrawingOptions.ToggleGrid, DrawingOptions.OnPickColor {

    private CanvasView canvasView;

    public DrawingCanvas() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_drawing_canvas, container, false);
        canvasView = view.findViewById(R.id.canvasView);
        Button button = view.findViewById(R.id.showOptionsBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof OnShowOptions) {
                    OnShowOptions callback = (OnShowOptions) getActivity();
                    callback.showOptions();
                }
            }
        });

        return view;
    }

    @Override
    public void reset() {
        canvasView.reset();
    }

    @Override
    public void setColor(int color) {
        canvasView.setColor(color);
    }

    @Override
    public boolean isGridToggled() {
        return canvasView.isGridEnabled();
    }

    @Override
    public void toggleGrid() {
        canvasView.toggleGrid();
    }

    public interface OnShowOptions {
        void showOptions();
    }
}
