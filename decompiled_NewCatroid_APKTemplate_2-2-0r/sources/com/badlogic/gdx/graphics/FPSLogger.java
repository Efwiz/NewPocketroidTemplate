package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/FPSLogger.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class FPSLogger {
    int bound;
    long startTime;

    public FPSLogger() {
        this(Integer.MAX_VALUE);
    }

    public FPSLogger(int i) {
        this.bound = i;
        this.startTime = TimeUtils.nanoTime();
    }

    public void setBound(int i) {
        this.bound = i;
        this.startTime = TimeUtils.nanoTime();
    }

    public void log() {
        int framesPerSecond;
        long jNanoTime = TimeUtils.nanoTime();
        if (jNanoTime - this.startTime <= 1000000000 || (framesPerSecond = Gdx.graphics.getFramesPerSecond()) >= this.bound) {
            return;
        }
        Gdx.f82app.log("FPSLogger", "fps: " + framesPerSecond);
        this.startTime = jNanoTime;
    }
}
