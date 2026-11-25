package org.jetbrains.kotlin.com.intellij.psi.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class CachedValueProfiler {
    private static final Overhead ourFrameOverhead;
    private static final Overhead ourTrackerOverhead;
    private static final Logger LOG = Logger.getInstance((Class<?>) CachedValueProfiler.class);
    private static volatile GlobalContext ourGlobalContext = new GlobalContext(null, 0);
    private static final ThreadLocal<ThreadContext> ourContext = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProfiler$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final Object get() {
            return CachedValueProfiler.lambda$static$0();
        }
    });
    private static final AtomicLong ourFrameId = new AtomicLong();

    public interface EventConsumer {
        void onFrameEnter(long j, EventPlace eventPlace, long j2, long j3);

        void onFrameExit(long j, long j2, long j3, long j4);

        void onValueComputed(long j, EventPlace eventPlace, long j2, long j3);

        void onValueInvalidated(long j, EventPlace eventPlace, long j2, long j3);

        void onValueRejected(long j, EventPlace eventPlace, long j2, long j3, long j4);

        void onValueUsed(long j, EventPlace eventPlace, long j2, long j3);
    }

    public interface EventPlace {
        StackTraceElement getStackFrame();

        StackTraceElement[] getStackTrace();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = TypedValues.AttributesType.S_FRAME;
        } else if (i != 3) {
            objArr[0] = "result";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/CachedValueProfiler";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/CachedValueProfiler";
        } else {
            objArr[1] = "findCallerPlace";
        }
        if (i == 1 || i == 2) {
            objArr[2] = "onResultReturned";
        } else if (i != 3) {
            objArr[2] = "onResultCreated";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    static {
        ourFrameOverhead = new Overhead();
        ourTrackerOverhead = new Overhead();
    }

    static /* synthetic */ ThreadContext lambda$static$0() {
        return new ThreadContext(ourGlobalContext);
    }

    public static boolean isProfiling() {
        return ourGlobalContext.consumer != null;
    }

    public static EventConsumer setEventConsumer(EventConsumer eventConsumer) {
        GlobalContext globalContext = ourGlobalContext;
        if (globalContext.consumer == null && eventConsumer == null) {
            return null;
        }
        ourGlobalContext = new GlobalContext(eventConsumer, globalContext.epoch + 1);
        if (globalContext.consumer != null) {
            Logger logger = LOG;
            logger.info(ourFrameOverhead.resetAndReport("doCompute()"));
            logger.info(ourTrackerOverhead.resetAndReport("getValue()"));
        }
        return globalContext.consumer;
    }

    public static final class Frame implements AutoCloseable {

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        final long f718id;
        final Frame parent;
        private final Map<CachedValueProvider.Result<?>, EventPlace> places;
        final long start;
        private long timeComputed;
        private long timeConfigured;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "result", "org/jetbrains/kotlin/com/intellij/psi/util/CachedValueProfiler$Frame", "newValueTracker"));
        }

        Frame() {
            long jCurrentTime = CachedValueProfiler.currentTime();
            this.start = jCurrentTime;
            long jIncrementAndGet = CachedValueProfiler.ourFrameId.incrementAndGet();
            this.f718id = jIncrementAndGet;
            this.places = new HashMap();
            ThreadContext threadContext = (ThreadContext) CachedValueProfiler.ourContext.get();
            Frame frame = threadContext.topFrame;
            this.parent = frame;
            threadContext.topFrame = this;
            if (threadContext.consumer == null || threadContext.epoch != CachedValueProfiler.ourGlobalContext.epoch) {
                return;
            }
            threadContext.consumer.onFrameEnter(jIncrementAndGet, CachedValueProfiler.place(new CachedValueProfiler$$ExternalSyntheticLambda1()), frame == null ? 0L : frame.f718id, jCurrentTime);
            this.timeConfigured = CachedValueProfiler.currentTime();
            CachedValueProfiler.ourFrameOverhead.count.incrementAndGet();
            CachedValueProfiler.ourFrameOverhead.overhead.addAndGet(this.timeConfigured - jCurrentTime);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            ThreadContext threadContext = (ThreadContext) CachedValueProfiler.ourContext.get();
            this.places.clear();
            if (threadContext.topFrame != this) {
                CachedValueProfiler.LOG.warn("unexpected frame: " + (threadContext.topFrame == null ? "null" : Long.valueOf(threadContext.topFrame.f718id)) + ", expected: " + this.f718id, new Throwable());
            }
            threadContext.topFrame = this.parent;
            if (this.parent == null) {
                CachedValueProfiler.ourContext.remove();
            }
            if (threadContext.consumer == null || threadContext.epoch != CachedValueProfiler.ourGlobalContext.epoch) {
                return;
            }
            threadContext.consumer.onFrameExit(this.f718id, this.start, this.timeComputed, CachedValueProfiler.currentTime());
            long jCurrentTime = CachedValueProfiler.currentTime();
            CachedValueProfiler.ourFrameOverhead.total.addAndGet(jCurrentTime - this.start);
            if (this.timeComputed != 0) {
                CachedValueProfiler.ourFrameOverhead.overhead.addAndGet(jCurrentTime - this.timeComputed);
            }
        }

        public ValueTracker newValueTracker(CachedValueProvider.Result<?> result) {
            if (result == null) {
                $$$reportNull$$$0(0);
            }
            this.timeComputed = CachedValueProfiler.currentTime();
            return CachedValueProfiler.onResultReturned(this, result);
        }
    }

    public static Frame newFrame() {
        return new Frame();
    }

    static void onResultCreated(CachedValueProvider.Result<?> result, Object obj) {
        Frame frame;
        EventPlace eventPlacePlace;
        if (result == null) {
            $$$reportNull$$$0(0);
        }
        long jCurrentTime = currentTime();
        ThreadContext threadContext = ourContext.get();
        if (threadContext.consumer == null || threadContext.epoch != ourGlobalContext.epoch || (frame = threadContext.topFrame) == null) {
            return;
        }
        if (obj == null) {
            eventPlacePlace = place(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProfiler$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj2) {
                    return CachedValueProfiler.findComputationPlace((Throwable) obj2);
                }
            });
        } else if (obj instanceof CachedValueProvider.Result) {
            eventPlacePlace = (EventPlace) frame.places.get(obj);
        } else {
            eventPlacePlace = obj instanceof Function ? place(new CachedValueProfiler$$ExternalSyntheticLambda1()) : null;
        }
        if (eventPlacePlace == null) {
            return;
        }
        frame.places.put(result, eventPlacePlace);
        ourFrameOverhead.overhead.addAndGet(currentTime() - jCurrentTime);
    }

    static ValueTracker onResultReturned(Frame frame, CachedValueProvider.Result<?> result) {
        if (frame == null) {
            $$$reportNull$$$0(1);
        }
        if (result == null) {
            $$$reportNull$$$0(2);
        }
        long jCurrentTime = currentTime();
        ThreadContext threadContext = ourContext.get();
        if (threadContext.consumer == null) {
            return null;
        }
        EventPlace eventPlacePlace = (EventPlace) frame.places.get(result);
        if (eventPlacePlace == null) {
            eventPlacePlace = place(new CachedValueProfiler$$ExternalSyntheticLambda1());
        }
        threadContext.consumer.onValueComputed(frame.f718id, eventPlacePlace, frame.timeConfigured, jCurrentTime);
        return new ValueTracker(eventPlacePlace, frame.timeConfigured, jCurrentTime, threadContext.epoch);
    }

    static EventPlace place(final Function<? super Throwable, ? extends StackTraceElement> function) {
        final Throwable th = new Throwable();
        return new EventPlace() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProfiler.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProfiler.EventPlace
            public StackTraceElement getStackFrame() {
                return (StackTraceElement) function.apply(th);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProfiler.EventPlace
            public StackTraceElement[] getStackTrace() {
                return th.getStackTrace();
            }
        };
    }

    static StackTraceElement findComputationPlace(Throwable th) {
        StackTraceElement[] stackTrace = th.getStackTrace();
        int length = stackTrace.length;
        int i = 2;
        while (i < length) {
            String methodName = stackTrace[i].getMethodName();
            String className = stackTrace[i].getClassName();
            if ("doCompute".equals(methodName) && ((className.endsWith("CachedValueImpl") || className.endsWith("CachedValue")) && (className.startsWith("org.jetbrains.kotlin.com.intellij.util.") || className.startsWith("org.jetbrains.kotlin.com.intellij.psi.")))) {
                break;
            }
            i++;
        }
        if (i >= length) {
            return null;
        }
        while (true) {
            i--;
            if (i <= 0) {
                break;
            }
            String className2 = stackTrace[i].getClassName();
            if (!className2.startsWith("org.jetbrains.kotlin.com.intellij.util.CachedValue") && !className2.startsWith("org.jetbrains.kotlin.com.intellij.psi.util.CachedValue") && !className2.startsWith("org.jetbrains.kotlin.com.intellij.psi.impl.PsiCachedValue") && !className2.startsWith("org.jetbrains.kotlin.com.intellij.openapi.util.Recursion")) {
                break;
            }
        }
        if (i > 0) {
            return stackTrace[i];
        }
        return null;
    }

    static StackTraceElement findCallerPlace(Throwable th) {
        StackTraceElement[] stackTrace = th.getStackTrace();
        int length = stackTrace.length;
        for (int i = 2; i < length; i++) {
            String className = stackTrace[i].getClassName();
            if (!className.startsWith("org.jetbrains.kotlin.com.intellij.util.CachedValue") && !className.startsWith("org.jetbrains.kotlin.com.intellij.psi.util.CachedValue") && !className.startsWith("org.jetbrains.kotlin.com.intellij.psi.impl.PsiCachedValue") && !className.startsWith("org.jetbrains.kotlin.com.intellij.openapi.util.Recursion") && !className.startsWith("org.jetbrains.kotlin.com.intellij.psi.impl.PsiParameterizedCachedValue")) {
                StackTraceElement stackTraceElement = stackTrace[i];
                if (stackTraceElement == null) {
                    $$$reportNull$$$0(3);
                }
                return stackTraceElement;
            }
        }
        return new StackTraceElement("unknown", "unknown", "", -1);
    }

    static long currentTime() {
        return System.nanoTime();
    }

    public static final class ValueTracker {
        final long computed;
        final int epoch;
        final EventPlace place;
        final long start;
        volatile long used;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "place", "org/jetbrains/kotlin/com/intellij/psi/util/CachedValueProfiler$ValueTracker", "<init>"));
        }

        ValueTracker(EventPlace eventPlace, long j, long j2, int i) {
            if (eventPlace == null) {
                $$$reportNull$$$0(0);
            }
            this.place = eventPlace;
            this.start = j;
            this.computed = j2;
            this.epoch = i;
        }

        public void onValueInvalidated() {
            long jCurrentTime = CachedValueProfiler.currentTime();
            ThreadContext threadContext = (ThreadContext) CachedValueProfiler.ourContext.get();
            if (threadContext.consumer != null && threadContext.epoch == CachedValueProfiler.ourGlobalContext.epoch && this.epoch == threadContext.epoch) {
                threadContext.consumer.onValueInvalidated(threadContext.topFrame == null ? 0L : threadContext.topFrame.f718id, this.place, this.used, jCurrentTime);
                CachedValueProfiler.ourTrackerOverhead.overhead.addAndGet(CachedValueProfiler.currentTime() - jCurrentTime);
            }
        }

        public void onValueUsed() {
            long jCurrentTime = CachedValueProfiler.currentTime();
            this.used = jCurrentTime;
            ThreadContext threadContext = (ThreadContext) CachedValueProfiler.ourContext.get();
            if (threadContext.consumer != null && threadContext.epoch == CachedValueProfiler.ourGlobalContext.epoch && this.epoch == threadContext.epoch) {
                threadContext.consumer.onValueUsed(threadContext.topFrame == null ? 0L : threadContext.topFrame.f718id, this.place, this.computed, jCurrentTime);
                CachedValueProfiler.ourTrackerOverhead.count.incrementAndGet();
                CachedValueProfiler.ourTrackerOverhead.overhead.addAndGet(CachedValueProfiler.currentTime() - jCurrentTime);
            }
        }

        public void onValueRejected() {
            long jCurrentTime = CachedValueProfiler.currentTime();
            ThreadContext threadContext = (ThreadContext) CachedValueProfiler.ourContext.get();
            if (threadContext.consumer != null && threadContext.epoch == CachedValueProfiler.ourGlobalContext.epoch && this.epoch == threadContext.epoch) {
                threadContext.consumer.onValueRejected(threadContext.topFrame == null ? 0L : threadContext.topFrame.f718id, this.place, this.start, this.computed, jCurrentTime);
                CachedValueProfiler.ourTrackerOverhead.overhead.addAndGet(CachedValueProfiler.currentTime() - jCurrentTime);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ThreadContext {
        final EventConsumer consumer;
        final int epoch;
        Frame topFrame;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "globalContext", "org/jetbrains/kotlin/com/intellij/psi/util/CachedValueProfiler$ThreadContext", "<init>"));
        }

        ThreadContext(GlobalContext globalContext) {
            if (globalContext == null) {
                $$$reportNull$$$0(0);
            }
            this.consumer = globalContext.consumer;
            this.epoch = globalContext.epoch;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GlobalContext {
        final EventConsumer consumer;
        final int epoch;

        GlobalContext(EventConsumer eventConsumer, int i) {
            this.consumer = eventConsumer;
            this.epoch = i;
        }
    }

    private static class Overhead {
        final AtomicLong count;
        final AtomicLong overhead;
        final AtomicLong total;

        private Overhead() {
            this.total = new AtomicLong();
            this.overhead = new AtomicLong();
            this.count = new AtomicLong();
        }

        String resetAndReport(String str) {
            String string;
            long andSet = this.total.getAndSet(0L);
            long andSet2 = this.overhead.getAndSet(0L);
            long andSet3 = this.count.getAndSet(0L);
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            StringBuilder sbAppend = new StringBuilder().append(numberFormat.format(andSet3)).append(" ").append(str).append(" calls, ").append(numberFormat.format(andSet2)).append(" overhead ns");
            if (andSet3 == 0 && andSet == 0) {
                string = "";
            } else {
                StringBuilder sb = new StringBuilder(" (");
                String[] strArr = new String[2];
                strArr[0] = andSet3 == 0 ? null : numberFormat.format(andSet2 / andSet3) + " ns/call";
                strArr[1] = andSet != 0 ? String.format("%.2f", Double.valueOf(andSet2 / (andSet / 100))) + " %" : null;
                string = sb.append(StringUtil.join((Collection<String>) Arrays.asList(strArr), ", ")).append(Constant.AFTER_QUTO).toString();
            }
            return sbAppend.append(string).toString();
        }
    }
}
