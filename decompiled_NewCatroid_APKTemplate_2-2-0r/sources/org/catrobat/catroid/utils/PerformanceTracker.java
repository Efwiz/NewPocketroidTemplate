package org.catrobat.catroid.utils;

import android.util.Log;
import org.catrobat.catroid.content.Script;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/PerformanceTracker.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PerformanceTracker {
    public static long activeThreads;
    public static long blocksExecuted;
    public static long formulaEvaluations;
    private static long frames;
    private static long lastLogTime = System.currentTimeMillis();
    public static long totalBlockTimeNs;
    private static long totalLogicTime;
    private static long totalPhysicsTime;
    private static long totalRenderTime;

    public static void recordFrame(long physicsNs, long logicNs, long renderNs) {
        totalPhysicsTime += physicsNs;
        totalLogicTime += logicNs;
        totalRenderTime += renderNs;
        frames++;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - lastLogTime >= 1000) {
            printStats();
            reset();
            lastLogTime = jCurrentTimeMillis;
        }
    }

    public static void logHeavyScript(Script script, long durationNs) {
        Log.w("CAT_PROFILER_HEAVY", "🔥 HEAVY SCRIPT DETECTED: " + (script != null ? script.getClass().getSimpleName() : "Unknown Script") + " took " + String.format("%.2f", Double.valueOf(durationNs / 1000000.0d)) + " ms in a single frame!");
    }

    private static void printStats() {
        long j = frames;
        if (j == 0) {
            return;
        }
        double d = (totalPhysicsTime / j) / 1000000.0d;
        double d2 = (totalLogicTime / j) / 1000000.0d;
        double d3 = (totalRenderTime / j) / 1000000.0d;
        Log.i("CAT_PROFILER", String.format("FPS: %d | Threads: %d | Logic: %.2fms (Blocks alone: %.2fms) | Render: %.2fms | Physics: %.2fms | Formulas: %d | Blocks: %d", Long.valueOf(j), Long.valueOf(activeThreads / j), Double.valueOf(d2), Double.valueOf((totalBlockTimeNs / j) / 1000000.0d), Double.valueOf(d3), Double.valueOf(d), Long.valueOf(formulaEvaluations), Long.valueOf(blocksExecuted)));
    }

    private static void reset() {
        totalPhysicsTime = 0L;
        totalLogicTime = 0L;
        totalRenderTime = 0L;
        frames = 0L;
        formulaEvaluations = 0L;
        blocksExecuted = 0L;
        activeThreads = 0L;
        totalBlockTimeNs = 0L;
    }
}
