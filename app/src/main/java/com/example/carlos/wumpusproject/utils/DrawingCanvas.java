package com.example.carlos.wumpusproject.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.carlos.wumpusproject.R;

/**
 * Created by carlos on 8/23/17.
 */

public class DrawingCanvas extends View {

    private Path drawPath; // Path used to draw lines.
    public static Paint drawPaint, canvasPaint; // Paint objects to draw and paint the canvas
    private static int paintColor = 0xFF660000; // Default color
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
        drawPaint.setColor(paintColor);
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
   /*     Resources res = getResources();
        Bitmap bitPunto = BitmapFactory.decodeResource(res, R.drawable.cueva1);
        //fila 1
        canvas.drawBitmap(bitPunto, 0, 150, drawPaint);
        canvas.drawBitmap(bitPunto, 250, 150, drawPaint);
        canvas.drawBitmap(bitPunto, 500, 150, drawPaint);
        canvas.drawBitmap(bitPunto, 750, 150, drawPaint);
        canvas.drawBitmap(bitPunto, 1000, 150, drawPaint);
        //fila 2
        canvas.drawBitmap(bitPunto, 0, 450, drawPaint);
        canvas.drawBitmap(bitPunto, 250, 450, drawPaint);
        canvas.drawBitmap(bitPunto, 500, 450, drawPaint);
        canvas.drawBitmap(bitPunto, 750, 450, drawPaint);
        canvas.drawBitmap(bitPunto, 1000, 450, drawPaint);
        //fila 3
        canvas.drawBitmap(bitPunto, 0, 750, drawPaint);
        canvas.drawBitmap(bitPunto, 250, 750, drawPaint);
        canvas.drawBitmap(bitPunto, 500, 750, drawPaint);
        canvas.drawBitmap(bitPunto, 750, 750, drawPaint);
        canvas.drawBitmap(bitPunto, 1000, 750, drawPaint);
        //fila 4
        canvas.drawBitmap(bitPunto, 0, 1050, drawPaint);
        canvas.drawBitmap(bitPunto, 250, 1050, drawPaint);
        canvas.drawBitmap(bitPunto, 500, 1050, drawPaint);
        canvas.drawBitmap(bitPunto, 750, 1050, drawPaint);
        canvas.drawBitmap(bitPunto, 1000, 1050, drawPaint);
        //fila 5
        canvas.drawBitmap(bitPunto, 0, 1350, drawPaint);
        canvas.drawBitmap(bitPunto, 250, 1350, drawPaint);
        canvas.drawBitmap(bitPunto, 500, 1350, drawPaint);
        canvas.drawBitmap(bitPunto, 750, 1350, drawPaint);
        canvas.drawBitmap(bitPunto, 1000, 1350, drawPaint);*/
    }

    // Registers users finger touch actions
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    /*    float touchX = event.getX();
        float touchY = event.getY();

        if(inicioX == -1 && inicioY == -1){
            inicioX = touchX;
            inicioY = touchY;
        }
        else{
            finalX = touchX;
            finalY = touchY;
        }
     */
     
        
    
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
             /*   drawPaint.setColor(Color.BLACK);
                if (inicioX != -1 && inicioY != -1 && finalX != -1 && finalY != -1) {
                    
                   boolean dibujar = verificarCoordenadas(inicioX, inicioY, finalX, finalY); //Verifica si seleccionó 2 cuevas correctamente
                    
                    if(dibujar == true) { //Seleccionó 2 cuevas, se cambiaron las imágenes y se puede dibujar la arista
                        drawPath.moveTo(inicioX, inicioY);
                        drawPath.lineTo(finalX, finalY);
                        canvas.drawPath(drawPath, drawPaint);
                        // canvas.drawLine(inicioX, inicioY, finalX, finalY, drawPaint);
                        inicioX = -1;
                        inicioY = -1;
                        finalX = -1;
                        finalY = -1;
                    }
                }
                drawPath.reset(); */
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

    public void dibujarLinea(float iX, float iY, float fX, float fY){
        //canvas.drawLine(iX, iY, fX, fY, drawPaint);
        drawPath.moveTo(iX, iY);
        drawPath.lineTo(fX, fY);
        canvas.drawPath(drawPath, drawPaint);
        drawPath.reset();
        invalidate();
    }

    
/*    public boolean verificarCoordenadas(float inicioX, float inicioY, float finalX, float finalY ){
        boolean dibujar = false;
        Resources res = getResources();
        Bitmap bitCueva = BitmapFactory.decodeResource(res, R.drawable.cueva1);
        
        boolean coordInicio = false; //Cueva de inicio correcta
        int countInicio = 1; //Cueva de inicio seleccionada
        boolean coordFinal = false;//Cueva de final correcta
        int countFinal = 1; //Cueva de final seleccionada
        
        //Busca la cueva de inicio, sino no hace nada
        for (int y = 150; y <= 1050; y++) {

            for (int x = 0; x <= 1400; x++) {


                if(inicioX >= x && inicioX <= x+50 && inicioY >= y && inicioY <= y+50) {

                    coordInicio = true;
                    
                }
                x+=250;
                countInicio++;
            }
            y+=300;
          
        }

        //Busca la cueva final, sino no hace nada

        if(coordInicio == true){
            for (int y = 150; y <= 1050; y++) {

                for (int x = 0; x <= 1400; x++) {


                    if (finalX >= x && finalX <= x + 50 && finalY >= y && finalY <= y + 50) {

                        if(countInicio != countFinal) { // Si no tocó otra vez la cueva de inicio
                            coordFinal = true;
                        }
                    }
                    x+=250;
                    countFinal++;
                }
                y+=300;
            }
        }
        
        //Si tocó 2 cuevas correctamente
        if(coordInicio == true && coordFinal == true){
            
            switch (countInicio) { // Busca cual cueva de inicio hay que cambiarle la imagen
                //fila 1
                case 1:
                canvas.drawBitmap(bitCueva, 0, 150, drawPaint);
                break;
                case 2:
                    canvas.drawBitmap(bitCueva, 250, 150, drawPaint);
                    break;
                case 3:
                    canvas.drawBitmap(bitCueva, 500, 150, drawPaint);
                    break;
                case 4:
                    canvas.drawBitmap(bitCueva, 750, 150, drawPaint);
                    break;
                case 5:
                    canvas.drawBitmap(bitCueva, 1000, 150, drawPaint);
                    break;
                case 6:
                    //fila 2
                canvas.drawBitmap(bitCueva, 0, 450, drawPaint);
                    break;
                case 7:
                    canvas.drawBitmap(bitCueva, 250, 450, drawPaint);
                    break;
                case 8:
                    canvas.drawBitmap(bitCueva, 500, 450, drawPaint);
                    break;
                case 9:
                    canvas.drawBitmap(bitCueva, 750, 450, drawPaint);
                    break;
                case 10:
                    canvas.drawBitmap(bitCueva, 1000, 450, drawPaint);
                    break;
                case 11:
                    //fila 3
                canvas.drawBitmap(bitCueva, 0, 750, drawPaint);
                    break;
                case 12:
                    canvas.drawBitmap(bitCueva, 250, 750, drawPaint);
                    break;
                case 13:
                    canvas.drawBitmap(bitCueva, 500, 750, drawPaint);
                    break;
                case 14:
                    canvas.drawBitmap(bitCueva, 750, 750, drawPaint);
                    break;
                case 15:
                    canvas.drawBitmap(bitCueva, 1000, 750, drawPaint);
                    break;
                case 16:
                    //fila 4
                canvas.drawBitmap(bitCueva, 0, 1050, drawPaint);
                    break;
                case 17:
                    canvas.drawBitmap(bitCueva, 250, 1050, drawPaint);
                    break;
                case 18:
                    canvas.drawBitmap(bitCueva, 500, 1050, drawPaint);
                    break;
                case 19:
                    canvas.drawBitmap(bitCueva, 750, 1050, drawPaint);
                    break;
                case 20:
                    canvas.drawBitmap(bitCueva, 1000, 1050, drawPaint);
                    break;
                case 21:
                    //fila 5
                canvas.drawBitmap(bitCueva, 0, 1350, drawPaint);
                    break;
                case 22:
                    canvas.drawBitmap(bitCueva, 250, 1350, drawPaint);
                    break;
                case 23:
                    canvas.drawBitmap(bitCueva, 500, 1350, drawPaint);
                    break;
                case 24:
                    canvas.drawBitmap(bitCueva, 750, 1350, drawPaint);
                    break;
                case 25:
                    canvas.drawBitmap(bitCueva, 1000, 1350, drawPaint);
                    break;
            }


            switch (countFinal) { // Busca cual cueva de final hay que cambiarle la imagen
                //fila 1
                case 1:
                    canvas.drawBitmap(bitCueva, 0, 150, drawPaint);
                    break;
                case 2:
                    canvas.drawBitmap(bitCueva, 250, 150, drawPaint);
                    break;
                case 3:
                    canvas.drawBitmap(bitCueva, 500, 150, drawPaint);
                    break;
                case 4:
                    canvas.drawBitmap(bitCueva, 750, 150, drawPaint);
                    break;
                case 5:
                    canvas.drawBitmap(bitCueva, 1000, 150, drawPaint);
                    break;
                case 6:
                    //fila 2
                    canvas.drawBitmap(bitCueva, 0, 450, drawPaint);
                    break;
                case 7:
                    canvas.drawBitmap(bitCueva, 250, 450, drawPaint);
                    break;
                case 8:
                    canvas.drawBitmap(bitCueva, 500, 450, drawPaint);
                    break;
                case 9:
                    canvas.drawBitmap(bitCueva, 750, 450, drawPaint);
                    break;
                case 10:
                    canvas.drawBitmap(bitCueva, 1000, 450, drawPaint);
                    break;
                case 11:
                    //fila 3
                    canvas.drawBitmap(bitCueva, 0, 750, drawPaint);
                    break;
                case 12:
                    canvas.drawBitmap(bitCueva, 250, 750, drawPaint);
                    break;
                case 13:
                    canvas.drawBitmap(bitCueva, 500, 750, drawPaint);
                    break;
                case 14:
                    canvas.drawBitmap(bitCueva, 750, 750, drawPaint);
                    break;
                case 15:
                    canvas.drawBitmap(bitCueva, 1000, 750, drawPaint);
                    break;
                case 16:
                    //fila 4
                    canvas.drawBitmap(bitCueva, 0, 1050, drawPaint);
                    break;
                case 17:
                    canvas.drawBitmap(bitCueva, 250, 1050, drawPaint);
                    break;
                case 18:
                    canvas.drawBitmap(bitCueva, 500, 1050, drawPaint);
                    break;
                case 19:
                    canvas.drawBitmap(bitCueva, 750, 1050, drawPaint);
                    break;
                case 20:
                    canvas.drawBitmap(bitCueva, 1000, 1050, drawPaint);
                    break;
                case 21:
                    //fila 5
                    canvas.drawBitmap(bitCueva, 0, 1350, drawPaint);
                    break;
                case 22:
                    canvas.drawBitmap(bitCueva, 250, 1350, drawPaint);
                    break;
                case 23:
                    canvas.drawBitmap(bitCueva, 500, 1350, drawPaint);
                    break;
                case 24:
                    canvas.drawBitmap(bitCueva, 750, 1350, drawPaint);
                    break;
                case 25:
                    canvas.drawBitmap(bitCueva, 1000, 1350, drawPaint);
                    break;
            }

            dibujar = true;  //Tocó 2 cuevas, se cambiaron las imagenes, se puede dibujar la arista
        }

        return dibujar;
    }
 */

    //Actualiza color
    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
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
            drawPaint.setColor(paintColor);
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

