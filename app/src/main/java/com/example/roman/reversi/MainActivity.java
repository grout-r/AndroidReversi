package com.example.roman.reversi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        grid = (CustomView) findViewById(R.id.grid);
        bscore = (TextView)findViewById(R.id.black_score_display);
        wscore = (TextView)findViewById(R.id.white_score_display);
        winner = (TextView)findViewById(R.id.winner);
        exit = (Button)findViewById(R.id.exit_bt);
        reset = (Button)findViewById(R.id.reset_bt);
        turn = (TextView) findViewById(R.id.turn);

        turn.setText(String.format(getResources().getString(R.string.turn) , "BLACK"));
        bscore.setText(String.valueOf(2));
        wscore.setText(String.valueOf(2));

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        grid.setEventListener(new CustomView.IMyEventListener() {
            @Override
            public void onEventAccured() {
                bscore.setText(String.valueOf(grid.bscore));
                wscore.setText(String.valueOf(grid.wscore));
                if (grid.turn == BLACK) { turn.setText(String.format(getResources().getString(R.string.turn) , "BLACK"));}
                if (grid.turn == WHITE) { turn.setText(String.format(getResources().getString(R.string.turn) , "WHITE"));}
                if (!grid.cont)
                {
                    if (grid.bscore > grid.wscore)
                    winner.setText(String.format(getResources().getString(R.string.win_disclaimer) , "BLACK"));
                    if (grid.bscore < grid.wscore)
                        winner.setText(String.format(getResources().getString(R.string.win_disclaimer) , "WHITE"));
                }
            }
        });
    }

    int WHITE = 0;
    int BLACK = 1;
    Button reset;
    Button exit;
    CustomView grid;
    TextView turn;
    TextView bscore;
    TextView wscore;
    TextView winner;
}
