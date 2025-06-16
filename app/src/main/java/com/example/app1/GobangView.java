package com.example.app1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GobangView extends View {
    private static final int LINE_COUNT = 15; // 棋盘线数
    private static final int CHESS_RADIUS = 20; // 棋子半径
    private static final int LINE_MARGIN = 40; // 边距

    private Paint linePaint; // 画线的画笔
    private Paint chessPaint; // 画棋子的画笔
    private int[][] chessArray; // 棋盘数组，0表示空，1表示黑棋，2表示白棋
    private boolean isBlack = true; // true表示黑棋，false表示白棋
    private boolean gameOver = false; // 游戏是否结束

    public GobangView(Context context) {
        super(context);
        init();
    }

    public GobangView(Context context, AttributeSet attrs) {
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

    // 绘制棋盘
    private void drawBoard(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int cellWidth = (width - 2 * LINE_MARGIN) / (LINE_COUNT - 1);

        // 画横线
        for (int i = 0; i < LINE_COUNT; i++) {
            canvas.drawLine(LINE_MARGIN, LINE_MARGIN + i * cellWidth,
                    width - LINE_MARGIN, LINE_MARGIN + i * cellWidth, linePaint);
        }

        // 画竖线
        for (int i = 0; i < LINE_COUNT; i++) {
            canvas.drawLine(LINE_MARGIN + i * cellWidth, LINE_MARGIN,
                    LINE_MARGIN + i * cellWidth, height - LINE_MARGIN, linePaint);
        }
    }

    // 绘制棋子
    private void drawChess(Canvas canvas) {
        int cellWidth = (getWidth() - 2 * LINE_MARGIN) / (LINE_COUNT - 1);

        for (int i = 0; i < LINE_COUNT; i++) {
            for (int j = 0; j < LINE_COUNT; j++) {
                if (chessArray[i][j] != 0) {
                    chessPaint.setColor(chessArray[i][j] == 1 ? Color.BLACK : Color.WHITE);
                    canvas.drawCircle(LINE_MARGIN + j * cellWidth,
                            LINE_MARGIN + i * cellWidth,
                            CHESS_RADIUS, chessPaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && !gameOver) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int cellWidth = (getWidth() - 2 * LINE_MARGIN) / (LINE_COUNT - 1);

            // 计算落子的行列
            int row = Math.round((y - LINE_MARGIN) / (float) cellWidth);
            int col = Math.round((x - LINE_MARGIN) / (float) cellWidth);

            // 检查是否在有效范围内
            if (row >= 0 && row < LINE_COUNT && col >= 0 && col < LINE_COUNT) {
                // 检查该位置是否已有棋子
                if (chessArray[row][col] == 0) {
                    chessArray[row][col] = isBlack ? 1 : 2;
                    invalidate();

                    // 检查是否获胜
                    if (checkWin(row, col)) {
                        gameOver = true;
                        String winner = isBlack ? "黑棋" : "白棋";
                        Toast.makeText(getContext(), winner + "获胜！", Toast.LENGTH_SHORT).show();
                    } else {
                        isBlack = !isBlack;
                    }
                }
            }
        }
        return true;
    }

    // 检查是否获胜
    private boolean checkWin(int row, int col) {
        int chess = chessArray[row][col];
        int count;

        // 检查横向
        count = 1;
        for (int i = col - 1; i >= 0 && chessArray[row][i] == chess; i--) count++;
        for (int i = col + 1; i < LINE_COUNT && chessArray[row][i] == chess; i++) count++;
        if (count >= 5) return true;

        // 检查纵向
        count = 1;
        for (int i = row - 1; i >= 0 && chessArray[i][col] == chess; i--) count++;
        for (int i = row + 1; i < LINE_COUNT && chessArray[i][col] == chess; i++) count++;
        if (count >= 5) return true;

        // 检查左上到右下
        count = 1;
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0 && chessArray[i][j] == chess; i--, j--) count++;
        for (int i = row + 1, j = col + 1; i < LINE_COUNT && j < LINE_COUNT && chessArray[i][j] == chess; i++, j++) count++;
        if (count >= 5) return true;

        // 检查右上到左下
        count = 1;
        for (int i = row - 1, j = col + 1; i >= 0 && j < LINE_COUNT && chessArray[i][j] == chess; i--, j++) count++;
        for (int i = row + 1, j = col - 1; i < LINE_COUNT && j >= 0 && chessArray[i][j] == chess; i++, j--) count++;
        if (count >= 5) return true;

        return false;
    }

    // 重新开始游戏
    public void restart() {
        chessArray = new int[LINE_COUNT][LINE_COUNT];
        isBlack = true;
        gameOver = false;
        invalidate();
    }
}
