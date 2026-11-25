package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;

/* JADX INFO: loaded from: classes3.dex */
public final class ShutDownTracker implements Runnable {
    private final ConcurrentLinkedDeque<Runnable> myShutdownTasks;
    private final Thread myThread;
    private final List<Thread> myThreads;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 3:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                i2 = 3;
                break;
            case 3:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "unit";
                break;
            case 2:
            case 4:
            case 5:
                objArr[0] = "thread";
                break;
            case 3:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/ShutDownTracker";
                break;
            case 6:
                objArr[0] = "runnable";
                break;
            case 7:
            case 9:
            case 10:
                objArr[0] = "task";
                break;
            case 8:
                objArr[0] = "parentDisposable";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/ShutDownTracker";
                break;
            case 3:
                objArr[1] = "getStopperThreads";
                break;
            default:
                objArr[1] = "getInstance";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "waitFor";
                break;
            case 2:
                objArr[2] = "isRegistered";
                break;
            case 4:
                objArr[2] = "unregisterStopperThread";
                break;
            case 5:
            case 6:
                objArr[2] = "executeWithStopperThread";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "registerShutdownTask";
                break;
            case 10:
                objArr[2] = "unregisterShutdownTask";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                throw new IllegalArgumentException(str2);
            case 3:
            default:
                throw new IllegalStateException(str2);
        }
    }

    private ShutDownTracker() {
        this.myThreads = Collections.synchronizedList(new ArrayList());
        this.myShutdownTasks = new ConcurrentLinkedDeque<>();
        Thread thread = new Thread(this, "Shutdown tracker");
        this.myThread = thread;
        Runtime.getRuntime().addShutdownHook(thread);
    }

    private static class ShutDownTrackerHolder {
        private static final ShutDownTracker ourInstance = new ShutDownTracker();
    }

    public static ShutDownTracker getInstance() {
        ShutDownTracker shutDownTracker = ShutDownTrackerHolder.ourInstance;
        if (shutDownTracker == null) {
            $$$reportNull$$$0(0);
        }
        return shutDownTracker;
    }

    public static boolean isShutdownHookRunning() {
        return getInstance().myThread.isAlive();
    }

    @Override // java.lang.Runnable
    public void run() {
        ensureStopperThreadsFinished();
        while (true) {
            Runnable runnablePollLast = this.myShutdownTasks.pollLast();
            if (runnablePollLast == null) {
                return;
            }
            try {
                runnablePollLast.run();
            } catch (Throwable th) {
                try {
                    Logger.getInstance((Class<?>) ShutDownTracker.class).error(th);
                } catch (AssertionError unused) {
                }
            }
        }
    }

    public void ensureStopperThreadsFinished() {
        Thread[] stopperThreads = getStopperThreads();
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (stopperThreads.length > 0) {
            Thread thread = stopperThreads[0];
            if (!thread.isAlive()) {
                if (isRegistered(thread)) {
                    Logger.getInstance((Class<?>) ShutDownTracker.class).error("Thread '" + thread.getName() + "' did not unregister itself from ShutDownTracker");
                    unregisterStopperThread(thread);
                }
            } else {
                if (System.currentTimeMillis() - jCurrentTimeMillis > DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS) {
                    thread.interrupt();
                }
                try {
                    thread.join(100L);
                } catch (InterruptedException unused) {
                }
            }
            stopperThreads = getStopperThreads();
        }
    }

    private boolean isRegistered(Thread thread) {
        if (thread == null) {
            $$$reportNull$$$0(2);
        }
        return this.myThreads.contains(thread);
    }

    private Thread[] getStopperThreads() {
        Thread[] threadArr = (Thread[]) this.myThreads.toArray(new Thread[0]);
        if (threadArr == null) {
            $$$reportNull$$$0(3);
        }
        return threadArr;
    }

    private void unregisterStopperThread(Thread thread) {
        if (thread == null) {
            $$$reportNull$$$0(4);
        }
        this.myThreads.remove(thread);
    }
}
