package com.badlogic.gdx.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/PerformanceCounters.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PerformanceCounters {
    private static final float nano2seconds = 1.0E-9f;
    private long lastTick = 0;
    public final Array<PerformanceCounter> counters = new Array<>();

    public PerformanceCounter add(String str, int i) {
        PerformanceCounter performanceCounter = new PerformanceCounter(str, i);
        this.counters.add(performanceCounter);
        return performanceCounter;
    }

    public PerformanceCounter add(String str) {
        PerformanceCounter performanceCounter = new PerformanceCounter(str);
        this.counters.add(performanceCounter);
        return performanceCounter;
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
        for (int i = 0; i < this.counters.size; i++) {
            this.counters.get(i).tick(f);
        }
    }

    public StringBuilder toString(StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        for (int i = 0; i < this.counters.size; i++) {
            if (i != 0) {
                stringBuilder.append("; ");
            }
            this.counters.get(i).toString(stringBuilder);
        }
        return stringBuilder;
    }
}
