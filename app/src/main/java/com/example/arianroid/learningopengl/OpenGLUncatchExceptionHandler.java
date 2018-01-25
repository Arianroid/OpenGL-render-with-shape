package com.example.arianroid.learningopengl;

import android.content.Context;
import android.util.Log;


public class OpenGLUncatchExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;

    public OpenGLUncatchExceptionHandler(Context context) {
        this.mContext = context;
    }


    @Override
    public void uncaughtException(Thread t, Throwable e) {

        Log.i("Log", "uncaughtException: "
                + Log.getStackTraceString(e));

    }
}
