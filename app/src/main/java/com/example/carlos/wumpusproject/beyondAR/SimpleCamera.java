package com.example.carlos.wumpusproject.beyondAR;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.Toast;

import com.beyondar.android.fragment.BeyondarFragmentSupport;
import com.beyondar.android.view.BeyondarGLSurfaceView;
import com.beyondar.android.view.OnClickBeyondarObjectListener;
import com.beyondar.android.view.OnTouchBeyondarViewListener;
import com.beyondar.android.world.BeyondarObject;
import com.beyondar.android.world.World;
import com.example.carlos.wumpusproject.R;

import java.util.ArrayList;
import java.util.Iterator;

public class SimpleCamera extends AppCompatActivity implements OnClickBeyondarObjectListener, OnTouchBeyondarViewListener{

    private BeyondarFragmentSupport mBeyondarFragment;
    private World mWorld;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_simple_camera);

        mBeyondarFragment = (BeyondarFragmentSupport) getSupportFragmentManager().findFragmentById(
                R.id.beyondarFragment);

        // We create the world and fill it ...
        mWorld = CustomWorldHelper.generateObjects(this);
        // ... and send it to the fragment
        mBeyondarFragment.setWorld(mWorld);

        // We also can see the Frames per seconds
        mBeyondarFragment.showFPS(true);
    }

    @Override
    public void onClickBeyondarObject(ArrayList<BeyondarObject> arrayList) {
        Toast.makeText(this, "Clicked on: " + arrayList.get(0).getName(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTouchBeyondarView(MotionEvent motionEvent, BeyondarGLSurfaceView beyondarGLSurfaceView) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        ArrayList<BeyondarObject> geoObjects = new ArrayList<BeyondarObject>();

        // This method call is better to don't do it in the UI thread!
        // This method is also available in the BeyondarFragment
        mBeyondarFragment.getBeyondarObjectsOnScreenCoordinates(x, y, geoObjects);

        String textEvent = "";
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                textEvent = "Event type ACTION_DOWN: ";
                break;
            case MotionEvent.ACTION_UP:
                textEvent = "Event type ACTION_UP: ";
                break;
            case MotionEvent.ACTION_MOVE:
                textEvent = "Event type ACTION_MOVE: ";
                break;
            default:
                break;
        }

        Iterator<BeyondarObject> iterator = geoObjects.iterator();
        while (iterator.hasNext()) {
            BeyondarObject geoObject = iterator.next();
            // ...
            // Do something
            // ...
        }
    }
}
