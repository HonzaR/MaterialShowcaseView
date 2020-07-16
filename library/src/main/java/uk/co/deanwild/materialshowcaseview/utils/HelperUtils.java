package uk.co.deanwild.materialshowcaseview.utils;

import android.content.Context;
import android.os.Build;

public class HelperUtils {

    public static int getNavigationBarSize(Context context)
    {
        if (!checkDeviceHasSoftwareNavBar(context))
            return 0;

        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : 0;
    }

    public static boolean checkDeviceHasSoftwareNavBar(Context context)
    {
        // Emulator
        if (isProbablyEmulator())
            return true;

        // Device
        int resId = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        return resId > 0 && context.getResources().getBoolean(resId);
    }

    public static boolean isProbablyEmulator()
    {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator");
    }
}
