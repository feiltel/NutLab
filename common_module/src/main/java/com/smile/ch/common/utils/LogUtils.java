package com.smile.ch.common.utils;

import android.util.Log;

/**
 * Created by admin on 2016/1/21.
 */
public class LogUtils {


    public static void log(String TAG, String msg) {

        int LOG_MAXLENGTH = 2000;
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.d(TAG + i, showLog(msg.substring(start, end)));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.d(TAG, showLog(msg.substring(start, strLength)));
                break;
            }
        }
    }
    public static void log(String msg) {

        int LOG_MAXLENGTH = 2000;
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.d(getNowClassName() + i, showLog(msg.substring(start, end)));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.d(getNowClassName(), showLog(msg.substring(start, strLength)));
                break;
            }
        }
    }
    private static String showLog(Object object) {
        StackTraceElement[] stackTraceElement = Thread.currentThread()
                .getStackTrace();
        //避免定位到本方法
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("log") == 0) {
                currentIndex = i + 1;
                break;
            }
        }
        String fullClassName = stackTraceElement[currentIndex].getClassName();
        String className = fullClassName.substring(fullClassName
                .lastIndexOf(".") + 1);
        String methodName = stackTraceElement[currentIndex].getMethodName();
        String lineNumber = String
                .valueOf(stackTraceElement[currentIndex].getLineNumber());
        String startLine = "                                            \n";
        String endLine = "\n                                            \n";
        return startLine + "at " + fullClassName + "." + methodName + "("
                + className + ".java:" + lineNumber + ")━━>" + methodName + "\n" + object.toString() + endLine;

    }
    private static String getNowClassName(){
        StackTraceElement[] stackTraceElement = Thread.currentThread()
                .getStackTrace();
        //避免定位到本方法
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("log") == 0) {
                currentIndex = i + 1;
                break;
            }
        }
        String fullClassName = stackTraceElement[currentIndex].getClassName();
        String className = fullClassName.substring(fullClassName
                .lastIndexOf(".") + 1);
        return className ;

    }

}
