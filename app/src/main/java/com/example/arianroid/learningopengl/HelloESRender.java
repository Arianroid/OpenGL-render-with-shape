package com.example.arianroid.learningopengl;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by arianroid on 2017-12-17.
 */

public class HelloESRender implements GLSurfaceView.Renderer {

    private FloatBuffer triangle;
    private float angle =  0.0f;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color to blue
        gl.glClearColor(0.0f, 0.0f
                , 0.9f, 1.0f);
        // Enable use of vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        SystemClock.sleep(1000);
        angle +=6;
        gl.glRotatef(angle,0.0f,0.0f,1.0f);
        gl.glScalef(1,2,1);

        //is called like display();method
        //Redraw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);
        initShapes();
        //Draw the triangle using green color
        gl.glColor4f(0.0f, 1.0f, 0.0f, 0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangle);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);


        //Redraw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity(); //reset matrix


        // When using GL_MODELVIEW, you must set the view point.
        GLU.gluLookAt(gl, 0, 0, 5   //camera at (0, 0, 5)
                , 0f, 0f, 0f //look at (0,0,0)
                , 0.0f, 1.0f, 0.0f); //up = (0, 1, 0)

        //rotate about z-axis for 30 degrees
        gl.glRotatef(angle, 0, 0, 1);
        //magnify triangle by x3 in y-direction
        gl.glScalef(1, 2, 1);

        // Draw the triangle
        gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangle);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);



    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // is called when the geometry of the GLSurfaceView changes
        gl.glViewport(0, 0, width, height);

        float aspectRatio = (float) width / height;//aspect ratio

        float left, right, bottom, top, nearZ, farZ;
        bottom = -1.5f;
        top = 1.5f;
        nearZ = 3.0f;
        farZ = 7.0f;
        left = bottom * aspectRatio;
        right = top * aspectRatio;


        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(left, right, bottom, top, nearZ, farZ);
        //apply the projection matrix


    }

    private void initShapes() {
        float vertices[] = {
                //(x,y,z) of triangle
                -0.6f, -0.5f, 0,
                0.6f, -0.5f, 0,
                0.0f, 0.5f, 0
        };

        //init vertex buffer for triangle
        //argument=(# of coordinate values * 4 bytes per float)
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);

        //use the device hardware's native byte order
        vbb.order(ByteOrder.nativeOrder());

        //create a floating point buffer from   the ByteBuffer
        triangle = vbb.asFloatBuffer();

        //add the coordinates to the FloatBuffer
        triangle.put(vertices);

        //set the buffer to read the first vertex coordinates
        triangle.position(0);

    }

}
