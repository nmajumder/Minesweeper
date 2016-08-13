package hu.ait.android.minesweeper.Model;

/**
 * Created by nathanmajumder on 2/28/16.
 */
public class MinesweeperModel {

    private static MinesweeperModel instance = null;

    private MinesweeperModel () {
    }

    public static MinesweeperModel getInstance(int gridSize, int numMines) {
        if (instance == null) {
            instance = new MinesweeperModel();
            initializeModel(gridSize,numMines);
        }
        return instance;
    }

    private static int num_mines;
    private static int gridDim;
    public static final short MINE = -1;
    public static final short NONE = 0;

    private static short[][] model = null;

    private static void initializeModel(int gridSize, int numMines) {
        gridDim = gridSize;
        num_mines = numMines;
        model = new short[gridDim][gridDim];

        // initialize all spots in model to NONE
        for (int i = 0; i < gridDim; i++) {
            for (int j = 0; j < gridDim; j++) {
                model[i][j] = NONE;
            }
        }

        // place mines in model board
        int x,y;
        for (int i = 0; i < num_mines; i++) {
            do {
                x = (int) Math.floor(Math.random() * gridDim);
                y = (int) Math.floor(Math.random() * gridDim);
            } while (model[x][y] == MINE);
            model[x][y] = MINE;
        }

        // calculate other space numbers
        int sum = 0;
        for (int i = 0; i < gridDim; i++) {
            for (int j = 0; j < gridDim; j++) {
                if (model[i][j] == MINE) {
                    continue;
                }
                sum = 0;
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (k >= 0 && k < gridDim && l >= 0 && l < gridDim) {
                            if (model[k][l] == MINE) {
                                sum++;
                            }
                        }
                    }
                }
                model[i][j] = (short) sum;
            }
        }
    }

    public int getSquareVal(int i, int j) {
        return model[i][j];
    }

    public void resetModel() {
        instance = null;
    }

}
