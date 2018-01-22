package com.example.arianroid.learningopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class CustomGLSurface extends GLSurfaceView {


    public static HelloESRender render =new HelloESRender();
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;

    public static void setRender(HelloESRender render) {
        CustomGLSurface.render = render;
    }

    public CustomGLSurface(Context context) {
        super(context);

        setRenderer(render);
        //Render the view  only when there is a change
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public CustomGLSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        setRenderer(render);
        //Render the view  only when there is a change
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:


                float dx = x - previousX;
                float dy = y - previousY;

                //Reverse direction of rotation above the mid line
                if (y > getHeight() / 2)
                    dx = dx * -1;

                //Reverse direction of roation to left of the mid-line
                if (x < getWidth() / 2)
                    dy = dy * -1;

                render.setAngle((dx + dy) * TOUCH_SCALE_FACTOR);
                requestRender();
                break;

        }

        previousY = y;
        previousX = x;
        return true;
    }
}

