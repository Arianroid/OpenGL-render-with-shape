package com.example.arianroid.learningopengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private CustomGLSurface mGLView;
    private EditText etX, etY, etZ;
    private float x = 1f, y = 1f, z = 1f;
    private Button rotateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(
                new OpenGLUncatchExceptionHandler(this));

        mGLView = (CustomGLSurface) findViewById(R.id.mainGlSurfaceView);
        rotateBtn = (Button) findViewById(R.id.btnClockWise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etX = (EditText) findViewById(R.id.xFloat);
        etY = (EditText) findViewById(R.id.yFloat);
        etZ = (EditText) findViewById(R.id.zFloat);

        x = Float.parseFloat(etX.getText().toString());
        y = Float.parseFloat(etY.getText().toString());
        z = Float.parseFloat(etZ.getText().toString());
        rotateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGLView.startClockwiseRotation();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.X_Axis:
                mGLView.setEnum(GLUenum.ROTATE_X);
                break;
            case R.id.Y_Axis:
                mGLView.setEnum(GLUenum.ROTATE_Y);
                break;
            case R.id.Z_Axis:
                mGLView.set3DParam(x, y, z);
                mGLView.setEnum(GLUenum.ROTATE_Z);
                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
