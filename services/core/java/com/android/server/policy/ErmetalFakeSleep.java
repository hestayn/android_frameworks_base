package com.android.server.policy;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

public class ErmetalFakeSleep {
    private static boolean mFakeSleep = false;
    private static int mOldBrightness = 255;

    public static void toggle(Context context) {
        mFakeSleep = !mFakeSleep;
        
        Intent intent1 = new Intent("com.ermetal.TOGGLE_SLEEP");
        intent1.setPackage("com.ermetal.launcher");
        intent1.putExtra("sleep", mFakeSleep);
        context.sendBroadcastAsUser(intent1, UserHandle.ALL);
        
        Intent intent2 = new Intent("com.ermetal.TOGGLE_SLEEP");
        intent2.setPackage("com.enes.sensorstream");
        intent2.putExtra("sleep", mFakeSleep);
        context.sendBroadcastAsUser(intent2, UserHandle.ALL);
        
        if (mFakeSleep) {
            try {
                mOldBrightness = android.provider.Settings.System.getInt(context.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
            } catch (Exception e) {}
            android.provider.Settings.System.putInt(context.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, 0);
            android.provider.Settings.System.putInt(context.getContentResolver(), "ermetal_fake_sleep", 1);
        } else {
            android.provider.Settings.System.putInt(context.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, mOldBrightness == 0 ? 255 : mOldBrightness);
            android.provider.Settings.System.putInt(context.getContentResolver(), "ermetal_fake_sleep", 0);
        }
    }
}
