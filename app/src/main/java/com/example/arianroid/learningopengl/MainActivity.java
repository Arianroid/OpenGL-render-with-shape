package com.example.arianroid.learningopengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private CustomGLSurface mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGLView = (CustomGLSurface) findViewById(R.id.mainGlSurfaceView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.first:
                mGLView.setEnum(GLUenum.ROTATE_X);
                break;
            case R.id.second:
                mGLView.setEnum(GLUenum.ROTATE_Y);
                break;
            case R.id.third:
                mGLView.setEnum(GLUenum.ROTATE_Z);
                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
