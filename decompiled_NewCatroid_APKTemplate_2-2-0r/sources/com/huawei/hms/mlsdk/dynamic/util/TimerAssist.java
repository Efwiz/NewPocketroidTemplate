package com.huawei.hms.mlsdk.dynamic.util;

import android.os.SystemClock;
import com.huawei.hms.ml.common.utils.SmartLog;

/* JADX INFO: loaded from: classes10.dex */
public class TimerAssist {
    private static final long DIFF_DETECT_TIME = 3000;
    private static final String TAG = "TimerAssist";
    private long start;

    private static final class Holder {
        private static final TimerAssist INSTANCE = new TimerAssist();

        private Holder() {
        }
    }

    private TimerAssist() {
        this.start = -1L;
    }

    public static TimerAssist getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized boolean isLock() {
        if (SystemClock.elapsedRealtime() - this.start < 3000) {
            return true;
        }
        this.start = SystemClock.elapsedRealtime();
        SmartLog.i(TAG, "unlock time = " + this.start);
        return false;
    }
}
