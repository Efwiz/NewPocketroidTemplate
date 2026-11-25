package com.badlogic.gdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.FloatCounter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/PerformanceCounter.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PerformanceCounter {
    private static final float nano2seconds = 1.0E-9f;
    public float current;
    private long lastTick;
    public final FloatCounter load;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final String f126name;
    private long startTime;
    public final FloatCounter time;
    public boolean valid;

    public PerformanceCounter(String str) {
        this(str, 5);
    }

    public PerformanceCounter(String str, int i) {
        this.startTime = 0L;
        this.lastTick = 0L;
        this.current = 0.0f;
        this.valid = false;
        this.f126name = str;
        this.time = new FloatCounter(i);
        this.load = new FloatCounter(1);
    }

    public void tick() {
        long jNanoTime = TimeUtils.nanoTime();
        long j = this.lastTick;
        if (j > 0) {
            tick((jNanoTime - j) * 1.0E-9f);
        }
        this.lastTick = jNanoTime;
    }

    public void tick(float f) {
        if (!this.valid) {
            Gdx.f82app.error("PerformanceCounter", "Invalid data, check if you called PerformanceCounter#stop()");
            return;
        }
        this.time.put(this.current);
        float f2 = f == 0.0f ? 0.0f : this.current / f;
        FloatCounter floatCounter = this.load;
        if (f <= 1.0f) {
            f2 = (f2 * f) + ((1.0f - f) * floatCounter.latest);
        }
        floatCounter.put(f2);
        this.current = 0.0f;
        this.valid = false;
    }

    public void start() {
        this.startTime = TimeUtils.nanoTime();
        this.valid = false;
    }

    public void stop() {
        if (this.startTime > 0) {
            this.current += (TimeUtils.nanoTime() - this.startTime) * 1.0E-9f;
            this.startTime = 0L;
            this.valid = true;
        }
    }

    public void reset() {
        this.time.reset();
        this.load.reset();
        this.startTime = 0L;
        this.lastTick = 0L;
        this.current = 0.0f;
        this.valid = false;
    }

    public String toString() {
        return toString(new StringBuilder()).toString();
    }

    public StringBuilder toString(StringBuilder stringBuilder) {
        stringBuilder.append(this.f126name).append(": [time: ").append(this.time.value).append(", load: ").append(this.load.value).append("]");
        return stringBuilder;
    }
}
