package com.example.arianroid.learningopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.content.ContentValues.TAG;

public class CustomRender implements GLSurfaceView.Renderer {

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    private float[] rotateParam = new float[]{1f, 0f, 0f};
    private Triangle mTriangle;
    private float mAngle = 0.0f;
    private boolean clockwiseRotation = false;

    static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
    public void changeColor(float[] newColor){
        mTriangle.setColor(newColor);
    }

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

        GLES20.glClearColor(0.9f, 0.9f
                , 1.0f, 0.2f);

        mTriangle = new Triangle();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        float[] scratch = new float[16];

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT
                | GLES20.GL_DEPTH_BUFFER_BIT);


        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0
                , 0, 0, 5 //camera at (0, 0, 5)
                , 0f, 0f, 0f //look at (0,0,0)
                , 0f, 1.0f, 0.0f); //up = (0, 1, 0)


        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0
                , mProjectionMatrix, 0, mViewMatrix, 0);

        // mTriangle.draw(mMVPMatrix);

        if (clockwiseRotation) {
            long time = SystemClock.uptimeMillis() % 4000L;
            mAngle = 0.090f * ((int) time);
        }

        Matrix.setRotateM(mRotationMatrix, 0, mAngle,
                rotateParam[0], rotateParam[1], rotateParam[2]);


        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix
                , 0, mRotationMatrix, 0);


        //  mSquare.draw(scratch);
        mTriangle.draw(scratch);

    }

    void setRotateParam(float[] rotateParam) {
        this.rotateParam = rotateParam;
    }

    void setAngle(float angle) {
        this.mAngle += angle;
    }

    /*private void onDrawSimpleFrame() {
        float[] scratch = new float[16];
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        // Draw square
        mTriangle.draw(mMVPMatrix);

        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        // Draw triangle
        //mSquare.draw(scratch);

    }*/


   /* void changeColorWithClearUI() {
        //Redraw background color
        //  gl10.glClear(GL10.GL_COLOR_BUFFER_BIT
        //    | GL10.GL_DEPTH_BUFFER_BIT);


        //   gl10.glClearColor(0.7f, 0.0f, 0.0f, 0f);

        //   gl10.glClear(GL10.GL_COLOR_BUFFER_BIT
        //     | GL10.GL_DEPTH_BUFFER_BIT);

    }
*/

    public void setClockwaiseRotation(boolean rotationState) {
        clockwiseRotation = rotationState;
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

}
