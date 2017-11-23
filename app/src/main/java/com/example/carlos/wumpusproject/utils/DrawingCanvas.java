package com.example.carlos.wumpusproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Clase que representa el canvas en donde se dibuja el laberinto.
 */
public class DrawingCanvas extends View {

    /** Path used to draw lines. */
    private Path drawPath;
    /** Paint objects to draw and paint the canvas. */
    public static Paint drawPaint, canvasPaint;
    /** Default color. */
    private static int defaultColor = 0xFFFFFFFF;
    /** Black color. */
    private static int blackColor = 0xFF000000;
    /** This is the drawing and painting area. */
    public Canvas canvas;
    /** Saving canvas. */
    private Bitmap canvasBitmap;

    /**
     * Constructor
     */
    public DrawingCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    /**
     * Initialize workspace.
     */
    private void setupDrawing(){
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(defaultColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

    }

    /**
     * Size assigned to the view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBitmap);
    }

    /**
     * Metodo que dibuja.
     */
    @Override
    protected void onDraw (Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);

    }

    /**
     * Registers users finger touch actions.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                //drawPath.reset();
                break;
            default:
                return false;
        }
    
        // Repaint
        //invalidate();
        return true;
    }

    /**
     * Borra una línea en el canvas.
     */
    public void borrarLinea(float iX, float iY, float fX, float fY){
        drawPaint.setColor(blackColor);
        this.dibujarLinea(iX, iY, fX, fY);
        drawPaint.setColor(defaultColor);
    }

    /**
     * Dibuja una linea en el canvas.
     */
    public void dibujarLinea(float iX, float iY, float fX, float fY){
        drawPath.moveTo(iX, iY);
        drawPath.lineTo(fX, fY);
        canvas.drawPath(drawPath, drawPaint);
        drawPath.reset();
        invalidate();
    }

    /**
     * Actualiza color.
     */
    public void setColor(String newColor){
        invalidate();
        defaultColor = Color.parseColor(newColor);
        drawPaint.setColor(defaultColor);
    }
}