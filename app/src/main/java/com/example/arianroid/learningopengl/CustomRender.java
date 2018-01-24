package com.example.arianroid.learningopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CustomRender implements GLSurfaceView.Renderer {

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    private int[] rotateParam = new int[]{1, 0, 0};
    private Triangle mTriangle;
    private float angle = 0.0f;
    private GL10 gl10;

    static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f
                , 0.0f, 1.0f);

        // Enable use of vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        mTriangle = new Triangle();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        this.gl10 = gl;

        //initShapes();
        onDrawSimpleFrame();

        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT
                | GLES20.GL_COLOR_BUFFER_BIT);


        long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

        // Draw the triangle facing straight on.
        Matrix.setIdentityM(mMVPMatrix, 0);
        Matrix.rotateM(mMVPMatrix, 0, angleInDegrees, 0.0f, 0.0f, 1.0f);

        //onDrawRotateFrame(unused);
    }

    private void onDrawSimpleFrame() {
        // Redraw background color
        float[] scratch = new float[16];

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Create a rotation transformation for the triangleFloatBuffer
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0
                , angle, 0, 0, -1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0
                , mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangleFloatBuffer
        mTriangle.draw(scratch);

        mTriangle.draw();

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0
                , 0, 0, -3
                , 0f, 0f, 0f
                , 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0
                , mProjectionMatrix, 0
                , mViewMatrix, 0);

        // Draw shape
        mTriangle.draw(mMVPMatrix);

    }

    void setRotateParam(int[] rotateParam) {
        this.rotateParam = rotateParam;
    }

    private void initShapes() {
        float vertices[] = {
                //(x,y,z) of triangleFloatBuffer
                -1f, -0.5f, 0,
                1f, -0.5f, 0,
                0.0f, 1f, 0
        };

        //init vertex buffer for triangleFloatBuffer
        //argument=(# of coordinate values * 4 bytes per float)
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);

        //use the device hardware's native byte order
        vbb.order(ByteOrder.nativeOrder());

        //create a floating point buffer from   the ByteBuffer
      //  triangleFloatBuffer = vbb.asFloatBuffer();

     //   //add the coordinates to the FloatBuffer
      //  triangleFloatBuffer.put(vertices);

        //set the buffer to read the first vertex coordinates
      //  triangleFloatBuffer.position(0);

    }

    void setAngle(float angle) {
        this.angle += angle;
    }

    void changeColorWithClearUI() {
        //Redraw background color
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);


        gl10.glClearColor(0.7f, 0.0f, 0.0f, 0f);

        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0
                , -ratio, ratio
                , -1, 1, 3, 7);

    }

   /* void rotateByClock() {
        SystemClock.sleep(1000);
        angle += 6;
        gl10.glRotatef(angle, 0.0f, 0.0f, 1.0f);

        gl10.glColor4f(0.1f, 1.0f, 0.1f, 0.3f);
        gl10.glClearColor(0.1f, 1.0f, 0.1f, 0.3f);

        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT
                | GL10.GL_DEPTH_BUFFER_BIT);


    }
*/

    /*public void onDrawRotateFrame(GL10 gl) {
        float[] scratch = new float[16];


        // Create a rotation transformation for the triangle
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        mTriangle.draw(scratch);
        mTriangle.draw();


    }*/

}
