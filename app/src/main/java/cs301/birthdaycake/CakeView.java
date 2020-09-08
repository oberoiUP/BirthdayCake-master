package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class CakeView extends SurfaceView {

    private CakeModel cakeModel;

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();

    Paint locationPaint = new Paint();
    Paint balloonPaint = new Paint();
    Paint stringPaint = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 40.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;

    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
       cakeModel = new CakeModel();

        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFFC755B5);  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);

        locationPaint.setTextSize(32);
        locationPaint.setColor(Color.RED);
        balloonPaint.setColor(Color.BLUE);
        stringPaint.setColor(Color.RED);

        setBackgroundColor(Color.WHITE);  //better than black default
    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        canvas.drawRect(left, bottom - candleHeight, left + candleWidth, bottom, candlePaint);

        if(this.cakeModel.areCandlesLit) {
            //draw the outer flame
            float flameCenterX = left + candleWidth / 2;
            float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
            canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

            //draw the inner flame
            flameCenterY += outerFlameRadius / 3;
            canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);
        }

        //draw the wick
        float wickLeft = left + candleWidth/2 - wickWidth/2;
        float wickTop = bottom - wickHeight - candleHeight;
        canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);

    }

    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     *
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas)
    {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        if(this.cakeModel.hasCandles) {
            //int offset = 0;
            int divisor = this.cakeModel.numCandles;
            for(int i = 1; i <= this.cakeModel.numCandles; i++) {

                drawCandle(canvas, cakeLeft + i*cakeWidth/(this.cakeModel.numCandles+1) - candleWidth/2, cakeTop);
                //offset += 200;

                //Now a candle in the center
                //drawCandle(canvas, cakeLeft + cakeWidth/2 - candleWidth/2, cakeTop);
                //drawCandle(canvas, cakeLeft + cakeWidth / 3 - candleWidth / 2, cakeTop);
                //drawCandle(canvas, cakeLeft + 2 * cakeWidth / 3 - candleWidth / 2, cakeTop);
            }
        }

        // person A changes
        /*if(this.cakeModel.xCoord > -1) {
            canvas.drawText("(" + this.cakeModel.xCoord + ",  " + this.cakeModel.yCoord + ")", 1500, 650, locationPaint);
        }*/

        // person B changes
        if(this.cakeModel.xCoord > -1) {
            int balloonWidth = 120;
            int balloonHeight = 170;
            canvas.drawOval(this.cakeModel.xCoord-balloonWidth/2, this.cakeModel.yCoord-balloonHeight/2,this.cakeModel.xCoord+balloonWidth/2,this.cakeModel.yCoord+balloonHeight/2, balloonPaint);
            canvas.drawLine(this.cakeModel.xCoord, this.cakeModel.yCoord+balloonHeight/2, this.cakeModel.xCoord, this.cakeModel.yCoord+2*balloonHeight, stringPaint);


            /* // just practicing drawing ovals
            RectF oval = new RectF(0,0,150,150);
            canvas.drawOval(oval, balloonPaint);

            RectF oval2 = new RectF(75,75,150,150);
            canvas.drawOval(oval2, stringPaint);*/
        }

        // person C changes
       /* if(this.cakeModel.xCoord > -1) {
            int squareWidth = 100;
            //top left square
            canvas.drawRect(this.cakeModel.xCoord - squareWidth / 2, this.cakeModel.yCoord - squareWidth / 2, this.cakeModel.xCoord, this.cakeModel.yCoord, balloonPaint);
            //top right square
            canvas.drawRect(this.cakeModel.xCoord, this.cakeModel.yCoord - squareWidth/2, this.cakeModel.xCoord+squareWidth/2, this.cakeModel.yCoord, stringPaint);
            //bottom left square
            canvas.drawRect(this.cakeModel.xCoord - squareWidth / 2, this.cakeModel.yCoord, this.cakeModel.xCoord, this.cakeModel.yCoord+squareWidth/2, stringPaint);
            //bottom right square
            canvas.drawRect(this.cakeModel.xCoord, this.cakeModel.yCoord, this.cakeModel.xCoord+squareWidth/2, this.cakeModel.yCoord+squareWidth/2, balloonPaint);

        }*/
    }//onDraw

    public CakeModel getCakeModel() { return this.cakeModel; }
}//class CakeView

