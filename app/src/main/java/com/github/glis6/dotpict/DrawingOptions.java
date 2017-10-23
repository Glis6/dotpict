package com.github.glis6.dotpict;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

public class DrawingOptions extends Fragment {

    public DrawingOptions() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_drawing_options, container, false);

        if (getActivity() instanceof OnPickColor) {
            final OnPickColor callback = (OnPickColor)getActivity();
            final SeekBar colorPicker = view.findViewById(R.id.colorPicker);
            LinearGradient gradient = new LinearGradient(0.f, 0.f, container.getWidth(), 0.0f,
                    new int[]{
                            0xFF000000,
                            0xFF0000FF,
                            0xFF00FF00,
                            0xFF00FFFF,
                            0xFFFF0000,
                            0xFFFF00FF,
                            0xFFFFFF00,
                            0xFFFFFFFF},
                    null, Shader.TileMode.CLAMP);
            ShapeDrawable shape = new ShapeDrawable(new RectShape());
            shape.getPaint().setShader(gradient);
            colorPicker.setProgressDrawable(shape);

            colorPicker.setMax(256 * 7 - 1);
            colorPicker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int r = 0;
                        int g = 0;
                        int b = 0;

                        if (progress < 256) {
                            b = progress;
                        } else if (progress < 256 * 2) {
                            g = progress % 256;
                            b = 256 - progress % 256;
                        } else if (progress < 256 * 3) {
                            g = 255;
                            b = progress % 256;
                        } else if (progress < 256 * 4) {
                            r = progress % 256;
                            g = 256 - progress % 256;
                            b = 256 - progress % 256;
                        } else if (progress < 256 * 5) {
                            r = 255;
                            g = 0;
                            b = progress % 256;
                        } else if (progress < 256 * 6) {
                            r = 255;
                            g = progress % 256;
                            b = 256 - progress % 256;
                        } else if (progress < 256 * 7) {
                            r = 255;
                            g = 255;
                            b = progress % 256;
                        }
                        int color = Color.argb(255, r, g, b);
                        callback.setColor(color);
                        colorPicker.setBackgroundColor(color);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }

        if (getActivity() instanceof OnReset) {
            final OnReset callback = (OnReset) getActivity();
            view.findViewById(R.id.resetBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.reset();
                }
            });
        }

        if (getActivity() instanceof OnShowCanvas) {
            final OnShowCanvas callback = (OnShowCanvas) getActivity();
            view.findViewById(R.id.showCanvasBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.showCanvas();
                }
            });
        }


        if (getActivity() instanceof ToggleGrid) {
            final ToggleGrid callback = (ToggleGrid) getActivity();
            Button toggleGridButton = view.findViewById(R.id.toggleGridBtn);
            toggleGridButton.setPressed(callback.isGridToggled());

            toggleGridButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.toggleGrid();
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public interface OnReset {
        void reset();
    }

    public interface OnShowCanvas {
        void showCanvas();
    }

    public interface ToggleGrid {
        boolean isGridToggled();

        void toggleGrid();
    }

    public interface OnPickColor {
        void setColor(int color);
    }
}
