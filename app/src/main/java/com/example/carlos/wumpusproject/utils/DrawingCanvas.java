package com.example.carlos.wumpusproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by carlos on 8/23/17.
 */

public class DrawingCanvas extends View {

    private Path drawPath; // Path used to draw lines.
    public static Paint drawPaint, canvasPaint; // Paint objects to draw and paint the canvas
    private static int defaultColor = 0xFFFFFFFF; // Default color
    private static int whiteColor = 0xFFFFFFFF;
    private static int blackColor = 0xFF000000;
    public Canvas canvas; // This is the drawing and painting area
    private Bitmap canvasBitmap; // Saving canvas

    private static float dotSize;
    private static boolean erased = false;

    public float inicioX = -1;
    private float inicioY = -1;
    private float finalX = -1;
    private float finalY = -1;


    public DrawingCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing(){
        // Initialize workspace
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

    // Size assigned to the view
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw (Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);

    }

    // Registers users finger touch actions
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

    public void borrarLinea(float iX, float iY, float fX, float fY){
        drawPaint.setColor(blackColor);
        this.dibujarLinea(iX, iY, fX, fY);
        drawPaint.setColor(defaultColor);
    }

    public void dibujarLinea(float iX, float iY, float fX, float fY){
        //canvas.drawLine(iX, iY, fX, fY, drawPaint);
        drawPath.moveTo(iX, iY);
        drawPath.lineTo(fX, fY);
        canvas.drawPath(drawPath, drawPaint);
        drawPath.reset();
        invalidate();
    }

    //Actualiza color
    public void setColor(String newColor){
        invalidate();
        defaultColor = Color.parseColor(newColor);
        drawPaint.setColor(defaultColor);
    }

    //Poner tamaño del punto
    public static void setDotSize(float nuevoTamanyo){
        drawPaint.setStrokeWidth(nuevoTamanyo);
    }


    //set borrado true or false
    public static void setErasen(boolean estaborrado){
        erased = estaborrado;
        if(erased) {
            drawPaint.setColor(Color.WHITE);
        }
        else {
            drawPaint.setColor(defaultColor);
        }
    }

    public void newDrawing(){
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();

    }

    public void setCoordInicio(float x, float y){
        inicioX = x;
        inicioY = y;
    }

    public void setCoordFinal(float x, float y){
        finalX = x;
        finalY = y;
    }

}

