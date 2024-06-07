/*
 * Copyright (C) 2024 The RabbitEscape Project
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package com.rabbitescape.keyhandler;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.view.KeyEvent;
import android.util.Log;

import com.android.internal.os.DeviceKeyHandler;

import java.util.List;

public class KeyHandler implements DeviceKeyHandler {

    private static final String TAG = "FANCY-KeyHandler";

    private static ActivityManager am;
    private static PackageManager pm;
    private static final String R1_LAUNCHER = "tech.rabbit.r1launcher.r1";

    public KeyHandler(Context context) {
        am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        pm = context.getPackageManager();
    }

    public KeyEvent handleKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        int scanCode = event.getScanCode();
        int action = event.getAction();
        String packageName = getRunningActivityInfo().packageName;
        boolean isPackageRabbitLauncher = packageName.equals(R1_LAUNCHER);
        // Bind POWER to FOCUS in rabbit launcher
        if (event.getKeyCode() == KeyEvent.KEYCODE_POWER && isPackageRabbitLauncher) {
            KeyEvent newEvent = new KeyEvent(
                event.getDownTime(),
                event.getEventTime(),
                event.getAction(),
                KeyEvent.KEYCODE_FOCUS,
                event.getRepeatCount(),
                event.getMetaState(),
                event.getDeviceId(),
                event.getScanCode(),
                event.getFlags(),
                event.getSource()
            );
            return newEvent;
        }
        // Bind DPAD_UP to VOLUME_UP outside rabbit launcher
        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP && !isPackageRabbitLauncher) {
            KeyEvent newEvent = new KeyEvent(
                event.getDownTime(),
                event.getEventTime(),
                event.getAction(),
                KeyEvent.KEYCODE_VOLUME_UP,
                event.getRepeatCount(),
                event.getMetaState(),
                event.getDeviceId(),
                event.getScanCode(),
                event.getFlags(),
                event.getSource()
            );

            return newEvent;
        }

        // Bind DPAD_DOWN to VOLUME_DOWN outside rabbit launcher
        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN && !isPackageRabbitLauncher) {
            KeyEvent newEvent = new KeyEvent(
                event.getDownTime(),
                event.getEventTime(),
                event.getAction(),
                KeyEvent.KEYCODE_VOLUME_DOWN,
                event.getRepeatCount(),
                event.getMetaState(),
                event.getDeviceId(),
                event.getScanCode(),
                event.getFlags(),
                event.getSource()
            );
            return newEvent;
        }

        return event;
    }

    private static ActivityInfo getRunningActivityInfo() {
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);

        if (tasks != null && !tasks.isEmpty()) {
            ActivityManager.RunningTaskInfo top = tasks.get(0);
            try {
                return pm.getActivityInfo(top.topActivity, 0);
            } catch (PackageManager.NameNotFoundException e) {
                // Do nothing
            }
        }

        return null;
    }

}
