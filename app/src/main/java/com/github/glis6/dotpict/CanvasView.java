package com.github.glis6.dotpict;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.Serializable;

/**
 * @author Glis
 */

public class CanvasView extends View implements Serializable {
    private int numberOfColumns = 40;
    private int numberOfRows = 60;
    private int cellWidth;
    private int cellHeight;
    private final Paint linePaint = new Paint();
    private final Paint squarePaint = new Paint();
    private int currentColor = Color.BLACK;
    private boolean gridEnabled = false;
    private int[][] cells;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setColor(Color.BLACK);
        squarePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (numberOfColumns < 1 || numberOfRows < 1) {
            return;
        }
        setNumberOfColumns(numberOfColumns);
        setNumberOfRows(numberOfRows);
        reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (numberOfColumns == 0 || numberOfRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < numberOfColumns; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                if (cells[i][j] != -1) {
                    squarePaint.setColor(cells[i][j]);
                    canvas.drawRect(i * cellWidth, j * cellHeight, (i + 1) * cellWidth, (j + 1) * cellHeight, squarePaint);
                }
            }
        }
        if(gridEnabled) {
            for (int i = 1; i < numberOfColumns; i++) {
                canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, linePaint);
            }

            for (int i = 1; i < numberOfRows; i++) {
                canvas.drawLine(0, i * cellHeight, width, i * cellHeight, linePaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);
            cells[column][row] = currentColor;
            invalidate();
        }
        return true;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        cellWidth = getWidth() / numberOfColumns;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
        cellHeight = getHeight() / numberOfRows;
    }

    public boolean isGridEnabled() {
        return gridEnabled;
    }

    public void toggleGrid() {
        this.gridEnabled = !gridEnabled;
    }

    public void setColor(final int color) {
        currentColor = color;
    }

    public void reset() {
        cells = new int[numberOfColumns][numberOfRows];
    }
}
