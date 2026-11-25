package com.reandroid.apk.framework;

import com.reandroid.apk.FrameworkApk;

/* JADX INFO: loaded from: classes10.dex */
public abstract class FrameworkManager {
    private FrameworkApk mCurrent;

    public abstract FrameworkApk get(int i);

    public abstract FrameworkApk getBestMatch(int i);

    public abstract FrameworkApk getLatest();

    public abstract Integer getLatestVersion();

    public abstract Integer getNearestVersion(int i);

    public void setCurrent(FrameworkApk frameworkApk) {
        synchronized (this) {
            this.mCurrent = frameworkApk;
        }
    }

    public FrameworkApk getCurrent() {
        synchronized (this) {
            FrameworkApk frameworkApk = this.mCurrent;
            if (frameworkApk == null) {
                return null;
            }
            if (!frameworkApk.isDestroyed()) {
                return frameworkApk;
            }
            this.mCurrent = null;
            return null;
        }
    }
}
