package hu.ait.android.minesweeper.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.MotionEvent;

import hu.ait.android.minesweeper.MainActivity;
import hu.ait.android.minesweeper.R;
import hu.ait.android.minesweeper.Model.MinesweeperModel;

/**
 * Created by nathanmajumder on 2/21/16.
 */

public class MinesweeperBoard extends View {


    private Paint paintBackground;
    private Paint paintLine;
    private Paint squareShownBackground;
    private Bitmap zeroIcon;
    private Bitmap oneIcon;
    private Bitmap twoIcon;
    private Bitmap threeIcon;
    private Bitmap fourIcon;
    private Bitmap fiveIcon;
    private Bitmap sixIcon;
    private Bitmap sevenIcon;
    private Bitmap eightIcon;
    private Bitmap mineIcon;
    private Bitmap flagIcon;

    private boolean endOfGame;

    public boolean toggleFlagSquare = false;
    public int gridDim;
    public int numMines;
    private static final short INVISIBLE = 2;
    private static final short SHOWN = 1;
    private static final short ZERO = 0;
    private static final short FLAGGED = -1;

    private short[][] shown = null;

    public MinesweeperBoard(Context context, AttributeSet attrs) {
        super(context, attrs);

        resetView();
        paintBackground = new Paint();
        paintBackground.setColor(Color.GRAY);
        paintBackground.setStyle(Paint.Style.FILL);

        squareShownBackground = new Paint();
        squareShownBackground.setColor(Color.LTGRAY);
        squareShownBackground.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(3);

        endOfGame = false;

        zeroIcon = BitmapFactory.decodeResource(getResources(), R.drawable.zeroicon);
        oneIcon = BitmapFactory.decodeResource(getResources(), R.drawable.oneicon);
        twoIcon = BitmapFactory.decodeResource(getResources(), R.drawable.twoicon);
        threeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.threeicon);
        fourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.fouricon);
        fiveIcon = BitmapFactory.decodeResource(getResources(), R.drawable.fiveicon);
        sixIcon = BitmapFactory.decodeResource(getResources(), R.drawable.sixicon);
        sevenIcon = BitmapFactory.decodeResource(getResources(), R.drawable.sevenicon);
        eightIcon = BitmapFactory.decodeResource(getResources(), R.drawable.eighticon);
        mineIcon = BitmapFactory.decodeResource(getResources(), R.drawable.bomb);
        flagIcon = BitmapFactory.decodeResource(getResources(), R.drawable.flag);

    }

    void initializeView(int gridSize, int mines) {
        gridDim = gridSize;
        numMines = mines;
        shown = new short[gridDim][gridDim];
        resetView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (shown == null) {
            initializeView(MainActivity.gridDim, MainActivity.totalNumMines);
        }


        super.onDraw(canvas);

        int wid = canvas.getWidth();
        int hgt = canvas.getHeight();

        canvas.drawRect(0, 0, wid, hgt, paintBackground);

        drawSquares(canvas);
        canvas.drawRect(0, 0, wid, hgt, paintLine);
        for (int i = 1; i < gridDim; i++) {
            canvas.drawLine(i*wid/gridDim, 0, i*wid/gridDim, hgt, paintLine);
            canvas.drawLine(0, i*hgt/gridDim, wid, i*hgt/gridDim, paintLine);
        }

    }

    private void drawSquares(Canvas canvas) {
        int boxWid = getWidth() / gridDim;
        int boxHgt = getHeight() / gridDim;
        double numberIconWid = (boxWid * .6) * .8;
        double numberIconHgt = boxHgt * .8;
        double otherIconWid = boxWid * .8;

        zeroIcon = Bitmap.createScaledBitmap(zeroIcon, (int)numberIconWid, (int)numberIconHgt, true);
        oneIcon = Bitmap.createScaledBitmap(oneIcon, (int)numberIconWid, (int)numberIconHgt, true);
        twoIcon = Bitmap.createScaledBitmap(twoIcon, (int)numberIconWid, (int)numberIconHgt, true);
        threeIcon = Bitmap.createScaledBitmap(threeIcon, (int)numberIconWid, (int)numberIconHgt, true);
        fourIcon = Bitmap.createScaledBitmap(fourIcon, (int)numberIconWid, (int)numberIconHgt, true);
        fiveIcon = Bitmap.createScaledBitmap(fiveIcon, (int)numberIconWid, (int)numberIconHgt, true);
        sixIcon = Bitmap.createScaledBitmap(sixIcon, (int)numberIconWid, (int)numberIconHgt, true);
        sevenIcon = Bitmap.createScaledBitmap(sevenIcon, (int)numberIconWid, (int)numberIconHgt, true);
        eightIcon = Bitmap.createScaledBitmap(eightIcon, (int)numberIconWid, (int)numberIconHgt, true);
        mineIcon = Bitmap.createScaledBitmap(mineIcon, (int)otherIconWid, (int)numberIconHgt, true);
        flagIcon = Bitmap.createScaledBitmap(flagIcon, (int)otherIconWid, (int)numberIconHgt, true);

        for (int i = 0; i < gridDim; i++) {
            for (int j = 0; j < gridDim; j++) {
                if (shown[i][j] == SHOWN) {
                    int val = MinesweeperModel.getInstance(gridDim,numMines).getSquareVal(i,j);
                    Bitmap thisSquare;
                    if (val == 0) {
                        thisSquare = zeroIcon;
                    } else if (val == 1) {
                        thisSquare = oneIcon;
                    } else if (val == 2) {
                        thisSquare = twoIcon;
                    } else if (val == 3) {
                        thisSquare = threeIcon;
                    } else if (val == 4) {
                        thisSquare = fourIcon;
                    } else if (val == 5) {
                        thisSquare = fiveIcon;
                    } else if (val == 6) {
                        thisSquare = sixIcon;
                    } else if (val == 7) {
                        thisSquare = sevenIcon;
                    } else if (val == 8) {
                        thisSquare = eightIcon;
                    } else {
                        thisSquare = mineIcon;
                    }
                    if (thisSquare == mineIcon) {
                        canvas.drawBitmap(thisSquare, (int) (i * boxWid + (.1 * boxWid)),
                                (int) (j * boxHgt + (.1 * boxHgt)), null);
                    } else { // drawing a number (which are narrower than mines or flags)
                        canvas.drawRect(i*boxWid, j*boxHgt, (i+1)*boxWid, (j+1)*boxHgt, squareShownBackground);
                        if (val != 0) {
                            canvas.drawBitmap(thisSquare, (int) (i * boxWid + (.24 * boxWid)),
                                    (int) (j * boxHgt + (.1 * boxHgt)), null);
                        }
                    }
                } else if (shown[i][j] == FLAGGED) {
                    canvas.drawBitmap(flagIcon, (int) (i * boxWid + (.1 * boxWid)),
                            (int) (j * boxHgt + (.1 * boxHgt)), null);
                } else if (shown[i][j] == ZERO) {
                    canvas.drawRect(i*boxWid, j*boxHgt, (i+1)*boxWid, (j+1)*boxHgt, squareShownBackground);
                } else {
                        // it is invisible, so draw nothing
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (endOfGame) {
            return super.onTouchEvent(event);
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int tX = ((int) event.getX()) / (getWidth() / gridDim);
            int tY = ((int) event.getY()) / (getHeight() / gridDim);

            if (shown[tX][tY] == SHOWN || shown[tX][tY] == FLAGGED) {
                return super.onTouchEvent(event);
            }

            int squareValue = MinesweeperModel.getInstance(gridDim,numMines).getSquareVal(tX,tY);
            // if trying to place a flag
            if (toggleFlagSquare) {
                if (squareValue == MinesweeperModel.MINE) {
                    shown[tX][tY] = FLAGGED;
                    if (playerDidWin()) {
                        ((MainActivity) getContext()).showGameOverMessage(getContext().getString(R.string.winDisplayMsg));
                        showAllSquares(true);
                    }
                } else {
                    shown[tX][tY] = FLAGGED;
                    ((MainActivity) getContext()).showGameOverMessage(getContext().getString(R.string.loseWrongFlagMsg));
                    showAllSquares(false);
                }
            // if trying to uncover a number
            } else {
                if (squareValue == MinesweeperModel.MINE) {
                    ((MainActivity) getContext()).showGameOverMessage(getContext().getString(R.string.loseDigMineMsg));
                    showAllSquares(false);
                } else {
                    if (squareValue == 0) {
                        uncoverNeighbors(tX,tY);
                    } else {
                        shown[tX][tY] = SHOWN;
                    }
                    if (playerDidWin()) { // if uncovered all non-mines
                        ((MainActivity) getContext()).showGameOverMessage(getContext().getString(R.string.winDisplayMsg));
                        showAllSquares(true);
                    }
                }
            }
            invalidate(); // redraw the view, indirectly calls onDraw
        }

        return super.onTouchEvent(event);
    }

    private void uncoverNeighbors(int x, int y) {
        int val = MinesweeperModel.getInstance(gridDim,numMines).getSquareVal(x,y);
        if (val != 0) {
            shown[x][y] = SHOWN;
            return;
        }

        shown[x][y] = ZERO;
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (i >= 0 && i < gridDim && j >= 0 && j < gridDim) {
                    if (x != i || y != j) { // make sure we aren't recursing on same point
                        if (shown[i][j] == INVISIBLE) {
                            uncoverNeighbors(i, j);
                        }
                    }
                }
            }
        }
    }

    private boolean playerDidWin() {
        int numFlagged = 0;
        int numInvisible = 0;
        for (int i = 0; i < gridDim; i++) {
            for (int j = 0; j < gridDim; j++) {
                if (shown[i][j] == FLAGGED) {
                    numFlagged++;
                } else if (shown[i][j] == INVISIBLE) {
                    numInvisible++;
                }
            }
        }
        if (numFlagged == numMines || (numInvisible + numFlagged) <= numMines) {
            return true;
        }
        return false;
    }

    private void showAllSquares(boolean win) {
        for (int i = 0; i < gridDim; i++) {
            for (int j = 0; j < gridDim; j++) {
                if (shown[i][j] == INVISIBLE) {
                    if (MinesweeperModel.getInstance(gridDim,numMines).getSquareVal(i,j) == MinesweeperModel.MINE
                            && win) {
                        shown[i][j] = FLAGGED;
                    } else {
                        shown[i][j] = SHOWN;
                    }
                }
            }
        }
        endOfGame = true;
    }

    private void resetView() {
        for (int i = 0; i < gridDim; i++) {
            for (int j = 0; j < gridDim; j++) {
                shown[i][j] = INVISIBLE;
            }
        }
        toggleFlagSquare = false;
    }

    public void clearGameField() {
        MinesweeperModel.getInstance(gridDim,numMines).resetModel();
        resetView();
        endOfGame = false;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h; // force view to have square size
        setMeasuredDimension(d, d);
    }
}
