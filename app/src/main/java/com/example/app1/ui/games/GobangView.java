package com.example.app1.ui.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class GobangView extends View {
    private static final int LINE_COUNT = 15;
    private static final int GRID_SIZE = 40;
    private static final int CHESS_RADIUS = 18;

    private Paint linePaint;
    private Paint chessPaint;
    private int[][] chessArray;
    private boolean isBlack = true;

    public GobangView(Context context) {
        super(context);
        init();
    }

    public GobangView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(2);
        linePaint.setAntiAlias(true);

        chessPaint = new Paint();
        chessPaint.setAntiAlias(true);

        chessArray = new int[LINE_COUNT][LINE_COUNT];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawChess(canvas);
    }

    private void drawBoard(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int startX = (width - (LINE_COUNT - 1) * GRID_SIZE) / 2;
        int startY = (height - (LINE_COUNT - 1) * GRID_SIZE) / 2;

        for (int i = 0; i < LINE_COUNT; i++) {
            canvas.drawLine(startX, startY + i * GRID_SIZE,
                    startX + (LINE_COUNT - 1) * GRID_SIZE, startY + i * GRID_SIZE, linePaint);
            canvas.drawLine(startX + i * GRID_SIZE, startY,
                    startX + i * GRID_SIZE, startY + (LINE_COUNT - 1) * GRID_SIZE, linePaint);
        }
    }

    private void drawChess(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int startX = (width - (LINE_COUNT - 1) * GRID_SIZE) / 2;
        int startY = (height - (LINE_COUNT - 1) * GRID_SIZE) / 2;

        for (int i = 0; i < LINE_COUNT; i++) {
            for (int j = 0; j < LINE_COUNT; j++) {
                if (chessArray[i][j] != 0) {
                    chessPaint.setColor(chessArray[i][j] == 1 ? Color.BLACK : Color.WHITE);
                    canvas.drawCircle(startX + j * GRID_SIZE, startY + i * GRID_SIZE,
                            CHESS_RADIUS, chessPaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int width = getWidth();
            int height = getHeight();
            int startX = (width - (LINE_COUNT - 1) * GRID_SIZE) / 2;
            int startY = (height - (LINE_COUNT - 1) * GRID_SIZE) / 2;

            int i = Math.round((float) (y - startY) / GRID_SIZE);
            int j = Math.round((float) (x - startX) / GRID_SIZE);

            if (i >= 0 && i < LINE_COUNT && j >= 0 && j < LINE_COUNT && chessArray[i][j] == 0) {
                chessArray[i][j] = isBlack ? 1 : 2;
                isBlack = !isBlack;
                invalidate();
            }
        }
        return true;
    }
}