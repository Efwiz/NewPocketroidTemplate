package org.jetbrains.kotlin.utils;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: loaded from: classes6.dex */
public class Profiler {
    private long cumulative;
    private String formatString;
    private final Logger log;
    private boolean mute;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f990name;
    private boolean paused;
    private StackTraceElement[] stackTrace;
    private long start;
    private static final ThreadLocal<Stack<Profiler>> PROFILERS = new ThreadLocal<Stack<Profiler>>() { // from class: org.jetbrains.kotlin.utils.Profiler.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public Stack<Profiler> initialValue() {
            return new Stack<>();
        }
    };
    private static final ReentrantLock OUT_LOCK = new ReentrantLock();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        if (i == 2) {
            objArr[0] = "out";
        } else if (i == 7 || i == 4) {
            objArr[0] = K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG;
        } else if (i != 5) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/utils/Profiler";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/utils/Profiler";
        } else {
            objArr[1] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        }
        if (i != 5) {
            if (i == 6 || i == 7) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    public static Profiler create(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return create(str, System.out);
    }

    public static Profiler create(String str, PrintStream printStream) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (printStream == null) {
            $$$reportNull$$$0(2);
        }
        return create(str, new PrintingLogger(printStream));
    }

    public static Profiler create(String str, Logger logger) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (logger == null) {
            $$$reportNull$$$0(4);
        }
        Profiler profiler = new Profiler(str, logger);
        PROFILERS.get().push(profiler);
        return profiler;
    }

    public static Profiler getFromContext() {
        Stack<Profiler> stack = PROFILERS.get();
        if (stack.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return stack.peek();
    }

    private Profiler(String str, Logger logger) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (logger == null) {
            $$$reportNull$$$0(7);
        }
        this.start = Long.MAX_VALUE;
        this.cumulative = 0L;
        this.paused = true;
        this.f990name = str;
        this.log = logger;
        setPrintAccuracy(3);
    }

    public Profiler recordStackTrace(int i) {
        return recordStackTrace(1, i);
    }

    public Profiler recordStackTrace(int i, int i2) {
        int iMin;
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int i3 = i + 1;
        if (i3 >= stackTrace.length) {
            return this;
        }
        if (i2 == -1) {
            iMin = stackTrace.length;
        } else {
            iMin = Math.min(i + i2 + 1, stackTrace.length);
        }
        this.stackTrace = (StackTraceElement[]) Arrays.copyOfRange(stackTrace, i3, iMin);
        return this;
    }

    public Profiler resetStackTrace() {
        this.stackTrace = null;
        return this;
    }

    public Profiler printStackTrace() {
        if (this.stackTrace != null && this.log.isDebugEnabled()) {
            OUT_LOCK.lock();
            try {
                for (StackTraceElement stackTraceElement : this.stackTrace) {
                    println("\tat ", stackTraceElement);
                }
                OUT_LOCK.unlock();
            } catch (Throwable th) {
                OUT_LOCK.unlock();
                throw th;
            }
        }
        return this;
    }

    public Profiler printEntering() {
        println("Entering ", this.f990name);
        return this;
    }

    public Profiler printThreadName() {
        println(Thread.currentThread().getName() + " ", this.f990name);
        return this;
    }

    public Profiler start() {
        if (this.paused) {
            this.start = System.nanoTime();
            this.paused = false;
        }
        return this;
    }

    public Profiler end() {
        long jNanoTime = this.cumulative;
        if (!this.paused) {
            jNanoTime += System.nanoTime() - this.start;
        }
        this.paused = true;
        this.cumulative = 0L;
        if (!this.mute && this.log.isDebugEnabled()) {
            OUT_LOCK.lock();
            try {
                println(this.f990name, " took ", format(jNanoTime));
                printStackTrace();
            } finally {
                OUT_LOCK.unlock();
            }
        }
        return this;
    }

    public Profiler pause() {
        if (!this.paused) {
            this.cumulative += System.nanoTime() - this.start;
            this.paused = true;
        }
        return this;
    }

    public long getCumulative() {
        return this.cumulative;
    }

    public Profiler mute() {
        this.mute = true;
        return this;
    }

    public Profiler unmute() {
        this.mute = false;
        return this;
    }

    public Profiler println(Object obj) {
        if (!this.mute && this.log.isDebugEnabled()) {
            this.log.debug(String.valueOf(obj));
        }
        return this;
    }

    public Profiler println(Object obj, Object obj2) {
        if (!this.mute && this.log.isDebugEnabled()) {
            OUT_LOCK.lock();
            try {
                this.log.debug(String.valueOf(obj) + obj2);
            } finally {
                OUT_LOCK.unlock();
            }
        }
        return this;
    }

    public Profiler println(Object obj, Object obj2, Object obj3) {
        if (!this.mute && this.log.isDebugEnabled()) {
            OUT_LOCK.lock();
            try {
                this.log.debug(String.valueOf(obj) + obj2 + obj3);
            } finally {
                OUT_LOCK.unlock();
            }
        }
        return this;
    }

    public Profiler println(Object obj, Object obj2, Object obj3, Object... objArr) {
        if (!this.mute && this.log.isDebugEnabled()) {
            OUT_LOCK.lock();
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(obj);
                sb.append(obj2);
                sb.append(obj3);
                for (Object obj4 : objArr) {
                    sb.append(obj4);
                }
                this.log.debug(sb.toString());
            } finally {
                OUT_LOCK.unlock();
            }
        }
        return this;
    }

    public Profiler setPrintAccuracy(int i) {
        this.formatString = "%." + i + "fs";
        return this;
    }

    private String format(long j) {
        return String.format(this.formatString, Double.valueOf(j / 1.0E9d));
    }
}
