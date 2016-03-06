package com.apollo.cbb.biz.utils;

import android.util.Log;

/**
 * Created by Panda on 2015/10/25.
 */
public class EsLog {

    private EsLog(){

    }

    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int LEVEL_VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int LEVEL_DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int LEVEL_INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int LEVEL_WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int LEVEL_ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    private static final int MIN_LOG_LEVEL = LEVEL_DEBUG;


    private static boolean isLoggable(int logLevel){
        return logLevel >= MIN_LOG_LEVEL;
    }

    public static void d(String tag, String msg){
        if(isLoggable(LEVEL_DEBUG))
            Log.d(tag,msg);
    }
    public static void e(String tag, String msg){
        if(isLoggable(LEVEL_ERROR))
            Log.e(tag, msg);
    }


    public static void e(String tag, String msg, Throwable throwable){
        if(isLoggable(LEVEL_ERROR))
            Log.e(tag, msg, throwable);
    }


    public static void i(String tag, String msg){
        if(isLoggable(LEVEL_INFO))
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg){
        if(isLoggable(LEVEL_VERBOSE))
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg){
        if(isLoggable(LEVEL_WARN))
            Log.v(tag, msg);
    }


    public static void logStackTrack(String tag){
        if(isLoggable(LEVEL_ERROR)){
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            for(StackTraceElement el : stackTrace){
                sb.append(el.toString()+"\n");
            }
            Log.e(tag,sb.toString());
        }
    }

}

