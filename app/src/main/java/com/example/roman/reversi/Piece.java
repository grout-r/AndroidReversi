package com.example.roman.reversi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Roman on 27/03/2017.
 */


public class Piece {
    Piece(int x, int y, int color, float caseWidth, float caseHeight)
    {
        this.x = x;
        this.y = y;
        this.color = color;
        this.caseHeight = caseHeight;
        this.caseWidth = caseWidth;
        dcolor = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (color == BLACK)
        {
            dcolor.setColor(0xFF000000);
        }
        if (color == WHITE)
        {
            dcolor.setColor(0xFFFFFFFF);
        }
    }

    void changeColor(int color)
    {
        this.color = color;
        if (color == WHITE)
        {
            dcolor.setColor(0xFFFFFFFF);
            return;
        }
        dcolor.setColor(0xFF000000);
    }


    void drawCircle(Canvas canvas)
    {
        int x = (this.x * (int)caseWidth)   - ((int)caseWidth / 2 );
        int y = (this.y * (int)caseHeight)   - ((int)caseWidth / 2 );

        canvas.drawCircle(x, y, caseHeight / 2, dcolor);
    }

    int WHITE = 0;
    int BLACK = 1;

    int x;
    int y;
    float caseHeight;
    float caseWidth;
    int color;
    Paint dcolor;
}
