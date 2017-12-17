package com.example.arianroid.learningopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    class HelloESSurfaceView extends GLSurfaceView {
        public HelloESSurfaceView(Context context) {
            super(context);

            // Set the Render for drawing on the GLSurfaceView
            setRenderer(new HelloESRender());
        }
    }


}
