package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.database.core.ServerValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: PerformanceCounter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J!\u0010\u0010\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0013H$¢\u0006\u0002\u0010\u0014J\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u000fH\u0004J\u001a\u0010\u0019\u001a\u00020\u00162\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00160\u001bJ&\u0010\u0019\u001a\u00020\u00162\u001e\u0010\u001a\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00160\u001cJ\u0006\u0010\u001d\u001a\u00020\u0016J\u001f\u0010\u001e\u001a\u0002H\u0011\"\u0004\b\u0000\u0010\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0013¢\u0006\u0002\u0010\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/util/PerformanceCounter;", "", "name", "", "(Ljava/lang/String;)V", "count", "", "excludedFrom", "", "Lorg/jetbrains/kotlin/util/CounterWithExclude;", "getExcludedFrom$util_runtime", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "totalTimeNanos", "", "countTime", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", ServerValues.NAME_OP_INCREMENT, "", "incrementTime", "delta", "report", "consumer", "Lkotlin/Function1;", "Lkotlin/Function3;", "reset", "time", "Companion", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PerformanceCounter {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ArrayList<PerformanceCounter> allCounters = new ArrayList<>();
    private static boolean enabled;
    private int count;
    private final List<CounterWithExclude> excludedFrom;
    private final String name;
    private long totalTimeNanos;

    protected abstract <T> T countTime(Function0<? extends T> block);

    /* JADX INFO: compiled from: PerformanceCounter.kt */
    @Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J'\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0011\"\u00020\u0005¢\u0006\u0002\u0010\u0012J\u001a\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0013\u001a\u00020\bH\u0007J\u0006\u0010\u0014\u001a\u00020\u0015J5\u0010\u0016\u001a\u0002H\u0017\"\u0004\b\u0000\u0010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00170\u001bH\u0080\bø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u001a\u0010\u001e\u001a\u00020\u001f2\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u001f0!J&\u0010\u001e\u001a\u00020\u001f2\u001e\u0010 \u001a\u001a\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u001f0\"J\u0006\u0010#\u001a\u00020\u001fJ\u000e\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\bR\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/util/PerformanceCounter$Companion;", "", "()V", "allCounters", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/util/PerformanceCounter;", "Lkotlin/collections/ArrayList;", CompilerOptions.ENABLED, "", "numberOfCounters", "", "getNumberOfCounters", "()I", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "name", "", "excluded", "", "(Ljava/lang/String;[Lorg/jetbrains/kotlin/util/PerformanceCounter;)Lorg/jetbrains/kotlin/util/PerformanceCounter;", "reenterable", "currentTime", "", "getOrPut", ExifInterface.GPS_DIRECTION_TRUE, "threadLocal", "Ljava/lang/ThreadLocal;", "default", "Lkotlin/Function0;", "getOrPut$util_runtime", "(Ljava/lang/ThreadLocal;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "report", "", "consumer", "Lkotlin/Function1;", "Lkotlin/Function3;", "resetAllCounters", "setTimeCounterEnabled", CommonCompilerArguments.ENABLE, "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PerformanceCounter create(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return create$default(this, name2, false, 2, null);
        }

        private Companion() {
        }

        public final long currentTime() {
            return System.nanoTime();
        }

        public final void report(Function1<? super String, Unit> consumer) {
            int i;
            PerformanceCounter[] performanceCounterArr;
            Intrinsics.checkNotNullParameter(consumer, "consumer");
            synchronized (PerformanceCounter.allCounters) {
                performanceCounterArr = (PerformanceCounter[]) PerformanceCounter.allCounters.toArray(new PerformanceCounter[0]);
            }
            for (PerformanceCounter performanceCounter : performanceCounterArr) {
                performanceCounter.report(consumer);
            }
        }

        public final void report(Function3<? super String, ? super Integer, ? super Long, Unit> consumer) {
            int i;
            PerformanceCounter[] performanceCounterArr;
            Intrinsics.checkNotNullParameter(consumer, "consumer");
            synchronized (PerformanceCounter.allCounters) {
                performanceCounterArr = (PerformanceCounter[]) PerformanceCounter.allCounters.toArray(new PerformanceCounter[0]);
            }
            for (PerformanceCounter performanceCounter : performanceCounterArr) {
                performanceCounter.report(consumer);
            }
        }

        public final int getNumberOfCounters() {
            int size;
            synchronized (PerformanceCounter.allCounters) {
                size = PerformanceCounter.allCounters.size();
            }
            return size;
        }

        public final void setTimeCounterEnabled(boolean enable) {
            PerformanceCounter.enabled = enable;
        }

        public final void resetAllCounters() {
            synchronized (PerformanceCounter.allCounters) {
                Iterator it = PerformanceCounter.allCounters.iterator();
                while (it.hasNext()) {
                    ((PerformanceCounter) it.next()).reset();
                }
                Unit unit = Unit.INSTANCE;
            }
        }

        public static /* synthetic */ PerformanceCounter create$default(Companion companion, String str, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            return companion.create(str, z);
        }

        public final PerformanceCounter create(String name2, boolean reenterable) {
            Intrinsics.checkNotNullParameter(name2, "name");
            if (reenterable) {
                return new ReenterableCounter(name2);
            }
            return new SimpleCounter(name2);
        }

        public final PerformanceCounter create(String name2, PerformanceCounter... excluded) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(excluded, "excluded");
            return new CounterWithExclude(name2, (PerformanceCounter[]) Arrays.copyOf(excluded, excluded.length));
        }

        public final <T> T getOrPut$util_runtime(ThreadLocal<T> threadLocal, Function0<? extends T> function0) {
            Intrinsics.checkNotNullParameter(threadLocal, "threadLocal");
            Intrinsics.checkNotNullParameter(function0, "default");
            T t = threadLocal.get();
            if (t != null) {
                return t;
            }
            T tInvoke = function0.invoke();
            threadLocal.set(tInvoke);
            return tInvoke;
        }
    }

    protected PerformanceCounter(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.excludedFrom = new ArrayList();
        ArrayList<PerformanceCounter> arrayList = allCounters;
        synchronized (arrayList) {
            arrayList.add(this);
        }
    }

    public final String getName() {
        return this.name;
    }

    public final List<CounterWithExclude> getExcludedFrom$util_runtime() {
        return this.excludedFrom;
    }

    public final void increment() {
        this.count++;
    }

    public final <T> T time(Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        this.count++;
        if (!enabled) {
            return block.invoke();
        }
        Iterator<T> it = this.excludedFrom.iterator();
        while (it.hasNext()) {
            ((CounterWithExclude) it.next()).enterExcludedMethod();
        }
        try {
            return (T) countTime(block);
        } finally {
            Iterator<T> it2 = this.excludedFrom.iterator();
            while (it2.hasNext()) {
                ((CounterWithExclude) it2.next()).exitExcludedMethod();
            }
        }
    }

    public final void reset() {
        this.count = 0;
        this.totalTimeNanos = 0L;
    }

    protected final void incrementTime(long delta) {
        this.totalTimeNanos += delta;
    }

    public final void report(Function1<? super String, Unit> consumer) {
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        if (this.totalTimeNanos == 0) {
            consumer.invoke(this.name + " performed " + this.count + " times");
        } else {
            consumer.invoke(this.name + " performed " + this.count + " times, total time " + TimeUnit.NANOSECONDS.toMillis(this.totalTimeNanos) + " ms");
        }
    }

    public final void report(Function3<? super String, ? super Integer, ? super Long, Unit> consumer) {
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        consumer.invoke(this.name, Integer.valueOf(this.count), Long.valueOf(this.totalTimeNanos));
    }
}
