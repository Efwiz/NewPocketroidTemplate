package org.jetbrains.kotlin.com.intellij.diagnostic;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes3.dex */
public final class ActivityImpl implements Activity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static volatile Consumer<ActivityImpl> listener;
    private final ActivityCategory category;
    private long end;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f705name;
    private final ActivityImpl parent;
    private final String pluginId;
    private final long start;
    private long threadId;
    private String threadName;

    /* JADX WARN: Code duplicated, block: B:24:0x0037  */
    /* JADX WARN: Code duplicated, block: B:32:0x004f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4 || i == 5) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "name";
        } else if (i == 3) {
            objArr[0] = "value";
        } else if (i == 4) {
            objArr[0] = "name";
        } else if (i != 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/diagnostic/ActivityImpl";
        } else {
            objArr[0] = "builder";
        }
        if (i == 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/diagnostic/ActivityImpl";
        } else if (i == 2) {
            objArr[1] = "getName";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/diagnostic/ActivityImpl";
        } else {
            objArr[1] = "getThreadName";
        }
        if (i == 1) {
            objArr[2] = "startChild";
        } else if (i == 3) {
            objArr[2] = "setDescription";
        } else if (i == 4) {
            objArr[2] = "endAndStart";
        } else if (i == 5) {
            objArr[2] = "nanoToString";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    ActivityImpl(String str, long j, ActivityImpl activityImpl, String str2, ActivityCategory activityCategory) {
        this.f705name = str;
        this.start = j;
        this.parent = activityImpl;
        this.pluginId = str2;
        this.category = activityCategory;
        updateThreadName();
        Consumer<ActivityImpl> consumer = listener;
        if (consumer != null) {
            consumer.accept(this);
        }
    }

    public void updateThreadName() {
        Thread threadCurrentThread = Thread.currentThread();
        this.threadId = threadCurrentThread.getId();
        this.threadName = threadCurrentThread.getName();
    }

    void setEnd(long j) {
        this.end = j;
    }

    @Override // org.jetbrains.kotlin.com.intellij.diagnostic.Activity
    public void end() {
        this.end = StartUpMeasurer.getCurrentTime();
        StartUpMeasurer.addActivity(this);
        Consumer<ActivityImpl> consumer = listener;
        if (consumer != null) {
            consumer.accept(this);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ActivityImpl(name=").append(this.f705name).append(", start=");
        nanoToString(this.start, sb);
        sb.append(", end=");
        nanoToString(this.end, sb);
        sb.append(", category=").append(this.category).append(Constant.AFTER_QUTO);
        return sb.toString();
    }

    private static void nanoToString(long j, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(5);
        }
        sb.append(TimeUnit.NANOSECONDS.toMillis(j - StartUpMeasurer.getStartTime())).append("ms (").append(TimeUnit.NANOSECONDS.toMicros(j - StartUpMeasurer.getStartTime())).append("μs)");
    }
}
