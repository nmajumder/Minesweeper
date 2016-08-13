package hu.ait.android.minesweeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ToggleButton;

import hu.ait.android.minesweeper.View.MinesweeperBoard;

public class MainActivity extends AppCompatActivity {

    static public int totalNumMines;
    static public int gridDim;
    private ScrollView layoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = (ScrollView) findViewById(R.id.layoutContent);

        if (getIntent().hasExtra(MenuActivity.KEY_GRID_SIZE)) {
            gridDim = getIntent().getIntExtra(MenuActivity.KEY_GRID_SIZE,4);
        }
        if (getIntent().hasExtra(MenuActivity.KEY_NUM_MINES)) {
            totalNumMines = getIntent().getIntExtra(MenuActivity.KEY_NUM_MINES,3);
        }

        final MinesweeperBoard gameField = (MinesweeperBoard) findViewById(R.id.gameField);

        final ToggleButton toggleBtn = (ToggleButton) findViewById(R.id.toggleBtn);
        toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameField.toggleFlagSquare = !gameField.toggleFlagSquare;
            }
        });

        final Button restartBtn = (Button) findViewById(R.id.restartButton);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameField.clearGameField();
                toggleBtn.setChecked(false);
                finish();
            }
        });
    }

    public void showGameOverMessage(String msg) {
        Snackbar.make(layoutContent, msg, Snackbar.LENGTH_LONG).setAction(
                R.string.cancelAction, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // this code is called when action is pressed on Snackbar

                    }
                }
        ).show();
    }

}
