package com.example.arianroid.learningopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GlSurfaceView instance and set it
        // as the ContentView for this Activity.

        mGLView = new HelloESSurfaceView(this);
        setContentView(mGLView);


    }


    class HelloESSurfaceView extends GLSurfaceView {
        private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
        private HelloESRender render;
        private float previousX;
        private float previousY;


        public HelloESSurfaceView(Context context) {
            super(context);
            render = new HelloESRender();
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

                    render.angle += (dx + dy) * TOUCH_SCALE_FACTOR;
                    requestRender();
                    break;

            }

            previousY = y;
            previousX = x;
            return true;
        }
    }


}
