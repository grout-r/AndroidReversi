package com.example.roman.reversi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class CustomView extends View {


    public CustomView(Context c)
    {
        super(c);
        init();
    }

    public CustomView(Context c, AttributeSet as)
    {
        super(c, as);
        init();
    }

    public CustomView(Context c, AttributeSet as, int default_style)
    {
        super(c, as, default_style);
        init();
    }

    private void init()
    {
        red = new Paint(Paint.ANTI_ALIAS_FLAG);
        blue = new Paint(Paint.ANTI_ALIAS_FLAG);
        red.setColor(0xFFFF0000);
        blue.setColor(0xFF0000FF);

        turn = BLACK;
        map = new ArrayList<>();
        mapPieces = new ArrayList<>();
    }

    //event handling to update the activity
    public interface IMyEventListener {
        public void onEventAccured();
    }

    private IMyEventListener mEventListener;

    public void setEventListener(IMyEventListener mEventListener) {
        this.mEventListener = mEventListener;
    }

    //genetate the grid
    private void createMap()
    {
        float x = caseWidth;
        float y = 0;

        for(int i = 0; i != 7; i++)
        {
            map.add(new Rect((int)x, (int)y, (int)x + 5, (int)y + 30000));
            x += caseWidth;
        }

        x = 0;
        y = caseHeight;

        for(int i = 0; i != 7; i++)
        {
            map.add(new Rect((int)x, (int)y, (int)x + 30000, (int)y + 5));
            y += caseHeight;
        }

    }

    public void onDraw(Canvas canvas)
    {
        //super call
        super.onDraw(canvas);
        //init / first call
        if (canvasWidth == -1 && canvasHeigth == -1)
        {
            canvasHeigth = canvas.getHeight();
            canvasWidth = canvas.getWidth();
            caseHeight = canvasHeigth / 8;
            caseWidth = canvasWidth / 8;
            createMap();
            mapPieces.add(new Piece(4, 4, WHITE, caseWidth, caseHeight));
            mapPieces.add(new Piece(5, 5, WHITE, caseWidth, caseHeight));
            mapPieces.add(new Piece(4, 5, BLACK, caseWidth, caseHeight));
            mapPieces.add(new Piece(5, 4, BLACK, caseWidth, caseHeight));
        }
        //score

        //background
        canvas.drawPaint(blue);
        //draw the grid
        for (int i = 0; i != map.size(); i++)
        {
            canvas.drawRect(map.get(i), red);
        }
        //draw the pieces

        for (int i = 0; i != mapPieces.size(); i++)
        {
            mapPieces.get(i).drawCircle(canvas);
        }
    }

    //find a piece on the board
    private Piece findPiece(int x, int y)
    {
        for(int i = 0; i != mapPieces.size(); i++)
        {
            if (mapPieces.get(i).x == x && mapPieces.get(i).y == y) {return mapPieces.get(i);}
        }
        return null;
    }

    //check if a piece can be placed and wich piecs will be converted
    private boolean moveAllowed(int x, int y, int color, boolean convert)
    {
        int tmpx;
        int tmpy;

        ArrayList<Piece> converted = new ArrayList<>();
        ArrayList<Piece> bufconverted = new ArrayList<>();

        if (findPiece(x, y) != null) {return false;}

        tmpx = x + 1;
        tmpy = y;
        bufconverted.clear();
        while (findPiece(tmpx, tmpy) != null && tmpx != 9)
        {
            if (findPiece(tmpx, tmpy).color == color)
            {
                converted.addAll(bufconverted);
                bufconverted.clear();
                break;
            }
            bufconverted.add(findPiece(tmpx, tmpy));
            tmpx++;
        }

        tmpx = x - 1;
        tmpy = y;
        bufconverted.clear();
        while (findPiece(tmpx, tmpy) != null && tmpx != 0)
        {
            if (findPiece(tmpx, tmpy).color == color)
            {
                converted.addAll(bufconverted);
                bufconverted.clear();
                break;
            }
            bufconverted.add(findPiece(tmpx, tmpy));
            tmpx--;
        }

        tmpx = x;
        tmpy = y + 1;
        bufconverted.clear();
        while (findPiece(tmpx, tmpy) != null && tmpy != 9)
        {
            if (findPiece(tmpx, tmpy).color == color)
            {
                converted.addAll(bufconverted);
                bufconverted.clear();
                break;
            }
            bufconverted.add(findPiece(tmpx, tmpy));
            tmpy++;
        }

        tmpx = x;
        tmpy = y - 1;
        bufconverted.clear();
        while (findPiece(tmpx, tmpy) != null && tmpy != 0)
        {
            if (findPiece(tmpx, tmpy).color == color)
            {
                converted.addAll(bufconverted);
                bufconverted.clear();
                break;
            }
            bufconverted.add(findPiece(tmpx, tmpy));
            tmpy--;
        }

        tmpx = x - 1;
        tmpy = y - 1;
        bufconverted.clear();
        while (findPiece(tmpx, tmpy) != null && tmpy != 0 && tmpx != 0)
        {
            if (findPiece(tmpx, tmpy).color == color)
            {
                converted.addAll(bufconverted);
                bufconverted.clear();
                break;
            }
            bufconverted.add(findPiece(tmpx, tmpy));
            tmpy--;
            tmpx--;
        }

        tmpx = x + 1;
        tmpy = y - 1;
        bufconverted.clear();
        while (findPiece(tmpx, tmpy) != null && tmpy != 0 && tmpx != 9)
        {
            if (findPiece(tmpx, tmpy).color == color)
            {
                converted.addAll(bufconverted);
                bufconverted.clear();
                break;
            }
            bufconverted.add(findPiece(tmpx, tmpy));
            tmpy--;
            tmpx++;
        }

        tmpx = x + 1;
        tmpy = y + 1;
        bufconverted.clear();
        while (findPiece(tmpx, tmpy) != null && tmpy != 9 && tmpx != 9)
        {
            if (findPiece(tmpx, tmpy).color == color)
            {
                converted.addAll(bufconverted);
                bufconverted.clear();
                break;
            }
            bufconverted.add(findPiece(tmpx, tmpy));
            tmpy++;
            tmpx++;
        }

        tmpx = x - 1;
        tmpy = y + 1;
        bufconverted.clear();
        while (findPiece(tmpx, tmpy) != null && tmpy != 9 && tmpx != 0)
        {
            if (findPiece(tmpx, tmpy).color == color)
            {
                converted.addAll(bufconverted);
                bufconverted.clear();
                break;
            }
            bufconverted.add(findPiece(tmpx, tmpy));
            tmpy++;
            tmpx--;
        }

        if (convert)
        {
            for (int i = 0; i != converted.size(); i++) {
                converted.get(i).changeColor(color);
            }
        }
        if (converted.size() == 0) { return false;}
        return true;
    }

    private void computeScore()
    {
        bscore = 0;
        wscore = 0;
        for (int i = 0; i != mapPieces.size(); i++)
        {
            if (mapPieces.get(i).color == BLACK) { bscore++; }
            if (mapPieces.get(i).color == WHITE) { wscore++; }
        }
    }

    private boolean checkEmptySlots()
    {
        int y;
        int x;

        for (x = 0; x != 9; x++)
        {
            for (y = 0; y != 9; y++)
            {
                if (findPiece(x, y ) == null) { return true;}
            }
        }
        return false;
    }

    private boolean checkMoveAvalaible(int color)
    {
        int y;
        int x;


        for (x = 0; x != 9; x++)
        {
            for (y = 0; y != 9; y++)
            {
                if (moveAllowed(x, y,color, false)) { return true; }
            }
        }
        return false;
    }

    private void gameContinue()
    {
        if (!checkEmptySlots()) { cont = false;}
        if (!checkMoveAvalaible(BLACK) && !checkMoveAvalaible(WHITE)) {cont = false;}

        if (bscore == 0 || wscore == 0)
        {
            cont = false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {

        if (!cont)
            return true;
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            int pointer_id = event.getPointerId(event.getActionIndex());
            float touchx = event.getX();
            float touchy = event.getY();
            float piecex = (touchx / caseWidth) + 1;
            float piecey = (touchy / caseHeight) + 1;
            if (!moveAllowed((int)piecex, (int)piecey, turn, true)) { return false;}
            mapPieces.add(new Piece((int)piecex, (int)piecey, turn, caseWidth, caseHeight));
            invalidate();
            computeScore();
            gameContinue();
            if (turn == BLACK) {turn = WHITE;}
            else if (turn == WHITE) {turn = BLACK;}
            mEventListener.onEventAccured();
            return true;
        } else if(event.getActionMasked() == MotionEvent.ACTION_UP) {
            return true;
        } else if(event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            return true;
        } else if(event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            return true;
        } else if(event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            return true;
        }
         return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    int bscore = 2;
    int wscore = 2;
    boolean cont = true;
    int turn;

    private int WHITE = 0;
    private int BLACK = 1;
    private int canvasHeigth = -1;
    private int canvasWidth = -1;
    private float caseHeight = -1;
    private float caseWidth = -1;

    private Paint red, blue;
    private ArrayList<Rect> map;
    private ArrayList<Piece> mapPieces;

}
