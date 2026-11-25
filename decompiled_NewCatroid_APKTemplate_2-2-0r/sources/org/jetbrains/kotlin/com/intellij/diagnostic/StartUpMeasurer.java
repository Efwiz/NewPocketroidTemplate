package org.jetbrains.kotlin.com.intellij.diagnostic;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class StartUpMeasurer {
    static final AtomicReference<LoadingState> currentState = new AtomicReference<>(LoadingState.BOOTSTRAP);
    public static final long MEASURE_THRESHOLD = TimeUnit.MILLISECONDS.toNanos(10);
    public static boolean measuringPluginStartupCosts = true;
    private static long startTime = System.nanoTime();
    private static final ConcurrentLinkedQueue<ActivityImpl> items = new ConcurrentLinkedQueue<>();
    private static boolean isEnabled = true;
    public static final Map<String, Object2LongOpenHashMap<String>> pluginCostMap = new ConcurrentHashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 2:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13:
                objArr[0] = "category";
                break;
            case 3:
            case 4:
            case 10:
            case 12:
            default:
                objArr[0] = "name";
                break;
            case 6:
            case 8:
                objArr[0] = "clazz";
                break;
            case 14:
            case 17:
                objArr[0] = "state";
                break;
            case 15:
                objArr[0] = "expectedState";
                break;
            case 16:
                objArr[0] = "newState";
                break;
            case 18:
                objArr[0] = "consumer";
                break;
            case 19:
                objArr[0] = "activity";
                break;
            case 20:
                objArr[0] = "timings";
                break;
            case 21:
                objArr[0] = "groupName";
                break;
            case 22:
            case 24:
                objArr[0] = "pluginId";
                break;
            case 23:
            case 25:
                objArr[0] = TypedValues.CycleType.S_WAVE_PHASE;
                break;
            case 26:
                objArr[0] = "pluginCostMap";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/diagnostic/StartUpMeasurer";
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                objArr[2] = "startActivity";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "addCompletedActivity";
                break;
            case 14:
                objArr[2] = "setCurrentState";
                break;
            case 15:
            case 16:
                objArr[2] = "compareAndSetCurrentState";
                break;
            case 17:
                objArr[2] = "stateSet";
                break;
            case 18:
                objArr[2] = "processAndClear";
                break;
            case 19:
                objArr[2] = "addActivity";
                break;
            case 20:
            case 21:
                objArr[2] = "addTimings";
                break;
            case 22:
            case 23:
                objArr[2] = "addPluginCost";
                break;
            case 24:
            case 25:
            case 26:
                objArr[2] = "doAddPluginCost";
                break;
            default:
                objArr[2] = "addInstantEvent";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static boolean isEnabled() {
        return isEnabled;
    }

    public static long getCurrentTime() {
        return System.nanoTime();
    }

    public static Activity startActivity(String str, ActivityCategory activityCategory) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (activityCategory == null) {
            $$$reportNull$$$0(2);
        }
        return new ActivityImpl(str, getCurrentTime(), null, null, activityCategory);
    }

    public static long addCompletedActivity(long j, Class<?> cls, ActivityCategory activityCategory, String str, long j2) {
        if (cls == null) {
            $$$reportNull$$$0(8);
        }
        if (activityCategory == null) {
            $$$reportNull$$$0(9);
        }
        if (!isEnabled) {
            return -1L;
        }
        long currentTime = getCurrentTime();
        long j3 = currentTime - j;
        if (j3 <= j2) {
            return j3;
        }
        addCompletedActivity(j, currentTime, cls.getName(), activityCategory, str);
        return j3;
    }

    public static void addCompletedActivity(long j, long j2, String str, ActivityCategory activityCategory, String str2) {
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (activityCategory == null) {
            $$$reportNull$$$0(13);
        }
        if (isEnabled) {
            ActivityImpl activityImpl = new ActivityImpl(str, j, null, str2, activityCategory);
            activityImpl.setEnd(j2);
            addActivity(activityImpl);
        }
    }

    public static long getStartTime() {
        return startTime;
    }

    static void addActivity(ActivityImpl activityImpl) {
        if (activityImpl == null) {
            $$$reportNull$$$0(19);
        }
        if (isEnabled) {
            items.add(activityImpl);
        }
    }
}
