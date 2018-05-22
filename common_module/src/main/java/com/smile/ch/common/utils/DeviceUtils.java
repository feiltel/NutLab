package com.smile.ch.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
 * Author：CHENHAO
 * date：2018/5/3
 * desc：
 */

public class DeviceUtils {
    /**
     * 是否有网
     *
     * @return boolean
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager!=null){
                NetworkInfo mNetworkInfo = mConnectivityManager
                        .getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }

        }

        return false;
    }

    /**
     * 获取imei 设备标识 需要有对应权限
     * @param context 上下文
     * @return imei
     */
  /*  public static String getImei(Context context) {
        String imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        return imei;
    }*/

    /**
     * 获取电池电量
     * @param context 上下文
     * @return 电池电量 浮点型
     */
    public static float getBatteryStatus(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        //当前剩余电量
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        //电量最大值
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        //电量百分比
        float batteryPct = level / (float) scale;
        return batteryPct;
    }

    /**
     * 获取屏幕像素宽度
     * @param activity 传入activity
     * @return 屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param activity 当前activity
     * @return 屏幕盖度
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;
        return height;
    }

    /**
     * 根据相对尺寸获取像素尺寸
     * @param activity 当前activity
     * @param dp 相对尺寸
     * @return 像素尺寸
     */
    public static int dp2px(Activity activity, int dp) {
        return (int) (dp * getDensity(activity));
    }

    /**
     * 获取屏幕像素密度
     * @param activity 当前activity
     * @return 像素密度
     */
    public static float getDensity(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.density;
    }
}
