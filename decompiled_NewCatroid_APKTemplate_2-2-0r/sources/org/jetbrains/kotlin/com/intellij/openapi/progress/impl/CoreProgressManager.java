package org.jetbrains.kotlin.com.intellij.openapi.progress.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.codeWithMe.ClientId;
import org.jetbrains.kotlin.com.intellij.diagnostic.ThreadDumper;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationEx;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationUtil;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.Cancellation;
import org.jetbrains.kotlin.com.intellij.openapi.progress.EmptyProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.PerformInBackgroundOption;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.StandardProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.Task;
import org.jetbrains.kotlin.com.intellij.openapi.progress.WrappedProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.wm.ex.ProgressIndicatorEx;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.concurrency.AppExecutorUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ConcurrentLongObjectMap;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class CoreProgressManager extends ProgressManager implements Disposable {
    private static CheckCanceledHook ourCheckCanceledHook;
    private ScheduledFuture<?> myCheckCancelledFuture;
    private int myDeprioritizations;
    private volatile long myPrioritizingStarted;
    private static final Logger LOG = Logger.getInstance((Class<?>) CoreProgressManager.class);
    public static final boolean ENABLED = !CompilerOptions.DISABLED.equals(System.getProperty("idea.ProcessCanceledException"));
    private static final Map<ProgressIndicator, Set<Thread>> threadsUnderIndicator = new HashMap();
    private static final ConcurrentLongObjectMap<ProgressIndicator> currentIndicators = ContainerUtil.createConcurrentLongObjectMap();
    private static final ConcurrentLongObjectMap<ProgressIndicator> threadTopLevelIndicators = ContainerUtil.createConcurrentLongObjectMap();
    static final Set<Thread> threadsUnderCanceledIndicator = new HashSet();
    private static volatile CheckCanceledBehavior ourCheckCanceledBehavior = CheckCanceledBehavior.NONE;
    private static final Map<ProgressIndicator, AtomicInteger> nonStandardIndicators = new ConcurrentHashMap();
    private static final ThreadLocal<Boolean> isInNonCancelableSection = new ThreadLocal<>();
    private static final long MAX_PRIORITIZATION_NANOS = TimeUnit.SECONDS.toNanos(12);
    private static final Thread[] EMPTY_THREAD_ARRAY = new Thread[0];
    private final AtomicInteger myUnsafeProgressCount = new AtomicInteger(0);
    private final Set<Thread> myPrioritizedThreads = ContainerUtil.newConcurrentSet();
    private volatile Thread[] myEffectivePrioritizedThreads = EMPTY_THREAD_ARRAY;
    private final Object myPrioritizationLock = ObjectUtils.sentinel("myPrioritizationLock");

    private enum CheckCanceledBehavior {
        NONE,
        ONLY_HOOKS,
        INDICATOR_PLUS_HOOKS
    }

    @FunctionalInterface
    interface CheckCanceledHook {
        boolean runHook(ProgressIndicator progressIndicator);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 50:
            case 51:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 18:
            case 21:
            case 27:
            case 30:
            case 49:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 50:
            case 51:
                i2 = 3;
                break;
            case 18:
            case 21:
            case 27:
            case 30:
            case 49:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 5:
            case 7:
            case 9:
            case 13:
            case 39:
            case 41:
            case 44:
                objArr[0] = "process";
                break;
            case 2:
                objArr[0] = "progress";
                break;
            case 3:
                objArr[0] = "runnable";
                break;
            case 4:
            case 48:
                objArr[0] = "computable";
                break;
            case 6:
            case 8:
            case 10:
            case 12:
                objArr[0] = "progressTitle";
                break;
            case 11:
                objArr[0] = "project";
                break;
            case 14:
                objArr[0] = "option";
                break;
            case 15:
            case 16:
            case 17:
            case 19:
            case 22:
            case 24:
            case 28:
            case 31:
            case 32:
            case 33:
            case 36:
            case 37:
                objArr[0] = "task";
                break;
            case 18:
            case 21:
            case 27:
            case 30:
            case 49:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/impl/CoreProgressManager";
                break;
            case 20:
            case 25:
            case 29:
            case 34:
            case 38:
                objArr[0] = "progressIndicator";
                break;
            case 23:
            case 42:
            case 46:
            case 51:
                objArr[0] = "indicator";
                break;
            case 26:
            case 35:
                objArr[0] = "modalityState";
                break;
            case 40:
                objArr[0] = "action";
                break;
            case 43:
                objArr[0] = "currentThread";
                break;
            case 45:
            case 47:
            case 50:
                objArr[0] = "thread";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 50:
            case 51:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/impl/CoreProgressManager";
                break;
            case 18:
            case 21:
            case 27:
                objArr[1] = "runProcessWithProgressAsynchronously";
                break;
            case 30:
                objArr[1] = "runProcessWithProgressAsync";
                break;
            case 49:
                objArr[1] = "getCurrentThreadProgressModality";
                break;
            default:
                objArr[1] = "getCurrentIndicators";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "runProcess";
                break;
            case 2:
                objArr[2] = "assertNoOtherThreadUnder";
                break;
            case 3:
                objArr[2] = "executeNonCancelableSection";
                break;
            case 4:
                objArr[2] = "computeInNonCancelableSection";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 32:
                objArr[2] = "runProcessWithProgressSynchronously";
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
            case 19:
            case 20:
            case 24:
            case 25:
            case 26:
            case 37:
            case 38:
                objArr[2] = "runProcessWithProgressAsynchronously";
                break;
            case 15:
                objArr[2] = "run";
                break;
            case 16:
                objArr[2] = "runAsynchronously";
                break;
            case 22:
            case 23:
                objArr[2] = "startTask";
                break;
            case 28:
            case 29:
                objArr[2] = "runProcessWithProgressAsync";
                break;
            case 31:
                objArr[2] = "notifyTaskFinished";
                break;
            case 33:
            case 34:
            case 35:
                objArr[2] = "runProcessWithProgressInCurrentThread";
                break;
            case 36:
                objArr[2] = "finishTask";
                break;
            case 39:
                objArr[2] = "executeProcessUnderProgress";
                break;
            case 40:
                objArr[2] = "runInReadActionWithWriteActionPriority";
                break;
            case 41:
                objArr[2] = "computeUnderProgress";
                break;
            case 42:
            case 43:
            case 44:
                objArr[2] = "registerIndicatorAndRun";
                break;
            case 45:
                objArr[2] = "updateThreadUnderCanceledIndicator";
                break;
            case 46:
                objArr[2] = "indicatorCanceled";
                break;
            case 47:
                objArr[2] = "isCanceledThread";
                break;
            case 48:
                objArr[2] = "computePrioritized";
                break;
            case 50:
                objArr[2] = "getCurrentIndicator";
                break;
            case 51:
                objArr[2] = "assertUnderProgress";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 28:
            case 29:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 50:
            case 51:
                throw new IllegalArgumentException(str2);
            case 18:
            case 21:
            case 27:
            case 30:
            case 49:
            default:
                throw new IllegalStateException(str2);
        }
    }

    protected CheckCanceledHook createCheckCanceledHook() {
        return null;
    }

    void notifyTaskFinished(Task.Backgroundable backgroundable, long j) {
        if (backgroundable == null) {
            $$$reportNull$$$0(31);
        }
    }

    protected void prioritizingFinished() {
    }

    protected void prioritizingStarted() {
    }

    private void startBackgroundNonStandardIndicatorsPing() {
        if (this.myCheckCancelledFuture != null) {
            return;
        }
        this.myCheckCancelledFuture = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6495x5dcc00e();
            }
        }, 0L, 10L, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: renamed from: lambda$startBackgroundNonStandardIndicatorsPing$0$org-jetbrains-kotlin-com-intellij-openapi-progress-impl-CoreProgressManager, reason: not valid java name */
    /* synthetic */ void m6495x5dcc00e() {
        for (ProgressIndicator progressIndicator : nonStandardIndicators.keySet()) {
            try {
                progressIndicator.checkCanceled();
            } catch (ProcessCanceledException unused) {
                indicatorCanceled(progressIndicator);
            }
        }
    }

    private void stopBackgroundNonStandardIndicatorsPing() {
        ScheduledFuture<?> scheduledFuture = this.myCheckCancelledFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            this.myCheckCancelledFuture = null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        synchronized (threadsUnderIndicator) {
            stopBackgroundNonStandardIndicatorsPing();
        }
    }

    public static boolean runCheckCanceledHooks(ProgressIndicator progressIndicator) {
        CheckCanceledHook checkCanceledHook = ourCheckCanceledHook;
        return checkCanceledHook != null && checkCanceledHook.runHook(progressIndicator);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider
    protected void doCheckCanceled() throws ProcessCanceledException {
        if (!isInNonCancelableSection()) {
            Cancellation.checkCancelled();
        }
        CheckCanceledBehavior checkCanceledBehavior = ourCheckCanceledBehavior;
        if (checkCanceledBehavior == CheckCanceledBehavior.NONE) {
            return;
        }
        if (checkCanceledBehavior == CheckCanceledBehavior.INDICATOR_PLUS_HOOKS) {
            ProgressIndicator progressIndicator = getProgressIndicator();
            if (progressIndicator != null) {
                progressIndicator.checkCanceled();
                return;
            }
            return;
        }
        runCheckCanceledHooks(null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public boolean hasProgressIndicator() {
        return getProgressIndicator() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public boolean hasUnsafeProgressIndicator() {
        return this.myUnsafeProgressCount.get() > 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public boolean hasModalProgressIndicator() {
        boolean zOr;
        Map<ProgressIndicator, Set<Thread>> map = threadsUnderIndicator;
        synchronized (map) {
            zOr = ContainerUtil.or(map.keySet(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda10
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return ((ProgressIndicator) obj).isModal();
                }
            });
        }
        return zOr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public void runProcess(final Runnable runnable, final ProgressIndicator progressIndicator) throws Throwable {
        if (runnable == null) {
            $$$reportNull$$$0(1);
        }
        if (progressIndicator != null) {
            assertNoOtherThreadUnder(progressIndicator);
        }
        executeProcessUnderProgress(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                CoreProgressManager.lambda$runProcess$2(progressIndicator, runnable);
            }
        }, progressIndicator);
    }

    static /* synthetic */ void lambda$runProcess$2(ProgressIndicator progressIndicator, Runnable runnable) {
        try {
            if (progressIndicator != null) {
                try {
                    if (!progressIndicator.isRunning()) {
                        progressIndicator.start();
                    }
                } catch (RuntimeException e) {
                    throw e;
                } catch (Throwable th) {
                    throw new RuntimeException(th);
                }
            }
            runnable.run();
            if (progressIndicator == null || !progressIndicator.isRunning()) {
                return;
            }
            progressIndicator.stop();
            if (progressIndicator instanceof ProgressIndicatorEx) {
                ((ProgressIndicatorEx) progressIndicator).processFinish();
            }
        } catch (Throwable th2) {
            if (progressIndicator != null && progressIndicator.isRunning()) {
                progressIndicator.stop();
                if (progressIndicator instanceof ProgressIndicatorEx) {
                    ((ProgressIndicatorEx) progressIndicator).processFinish();
                }
            }
            throw th2;
        }
    }

    private static void assertNoOtherThreadUnder(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(2);
        }
        Map<ProgressIndicator, Set<Thread>> map = threadsUnderIndicator;
        synchronized (map) {
            Set<Thread> set = map.get(progressIndicator);
            Thread next = (set == null || set.isEmpty()) ? null : set.iterator().next();
            if (next != null) {
                if (next == Thread.currentThread()) {
                    LOG.error("This thread is already running under this indicator, starting/stopping it here might be a data race");
                } else {
                    StringWriter stringWriter = new StringWriter();
                    ThreadDumper.dumpCallStack(next, stringWriter, next.getStackTrace());
                    LOG.error("Other thread is already running under this indicator, starting/stopping it here might be a data race. Its thread dump:\n" + stringWriter);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public void executeNonCancelableSection(final Runnable runnable) throws Exception {
        if (runnable == null) {
            $$$reportNull$$$0(3);
        }
        computeInNonCancelableSection(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return CoreProgressManager.lambda$executeNonCancelableSection$3(runnable);
            }
        });
    }

    static /* synthetic */ Object lambda$executeNonCancelableSection$3(Runnable runnable) throws RuntimeException {
        runnable.run();
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public <T, E extends Exception> T computeInNonCancelableSection(ThrowableComputable<T, E> throwableComputable) throws Exception {
        if (throwableComputable == null) {
            $$$reportNull$$$0(4);
        }
        try {
            if (isInNonCancelableSection()) {
                return throwableComputable.compute();
            }
            try {
                isInNonCancelableSection.set(Boolean.TRUE);
                return (T) computeUnderProgress(throwableComputable, NonCancelableIndicator.INSTANCE);
            } finally {
                isInNonCancelableSection.remove();
            }
        } catch (ProcessCanceledException e) {
            throw new RuntimeException("PCE is not expected in non-cancellable section execution", e);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public boolean runProcessWithProgressSynchronously(Runnable runnable, String str, boolean z, Project project) {
        if (runnable == null) {
            $$$reportNull$$$0(5);
        }
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return runProcessWithProgressSynchronously(runnable, str, z, project, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public <T, E extends Exception> T runProcessWithProgressSynchronously(final ThrowableComputable<T, E> throwableComputable, String str, boolean z, Project project) throws Exception {
        if (throwableComputable == null) {
            $$$reportNull$$$0(7);
        }
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        final AtomicReference atomicReference = new AtomicReference();
        final AtomicReference atomicReference2 = new AtomicReference();
        runProcessWithProgressSynchronously(new Task.Modal(project, str, z) { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "indicator", "org/jetbrains/kotlin/com/intellij/openapi/progress/impl/CoreProgressManager$1", "run"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.Progressive
            public void run(ProgressIndicator progressIndicator) {
                if (progressIndicator == null) {
                    $$$reportNull$$$0(0);
                }
                try {
                    atomicReference.set(throwableComputable.compute());
                } catch (Throwable th) {
                    atomicReference2.set(th);
                }
            }
        });
        Throwable th = (Throwable) atomicReference2.get();
        if (th != null) {
            ExceptionUtil.rethrowUnchecked(th);
            throw ((Exception) th);
        }
        return (T) atomicReference.get();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public boolean runProcessWithProgressSynchronously(final Runnable runnable, String str, boolean z, Project project, JComponent jComponent) {
        if (runnable == null) {
            $$$reportNull$$$0(9);
        }
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        return runProcessWithProgressSynchronously(new Task.Modal(project, jComponent, str, z) { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "indicator", "org/jetbrains/kotlin/com/intellij/openapi/progress/impl/CoreProgressManager$2", "run"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.Progressive
            public void run(ProgressIndicator progressIndicator) {
                if (progressIndicator == null) {
                    $$$reportNull$$$0(0);
                }
                runnable.run();
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public void runProcessWithProgressAsynchronously(Project project, String str, final Runnable runnable, final Runnable runnable2, final Runnable runnable3, PerformInBackgroundOption performInBackgroundOption) {
        if (project == null) {
            $$$reportNull$$$0(11);
        }
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (runnable == null) {
            $$$reportNull$$$0(13);
        }
        if (performInBackgroundOption == null) {
            $$$reportNull$$$0(14);
        }
        runProcessWithProgressAsynchronously(new Task.Backgroundable(project, str, true, performInBackgroundOption) { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "indicator", "org/jetbrains/kotlin/com/intellij/openapi/progress/impl/CoreProgressManager$3", "run"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.Progressive
            public void run(ProgressIndicator progressIndicator) {
                if (progressIndicator == null) {
                    $$$reportNull$$$0(0);
                }
                runnable.run();
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.Task
            public void onCancel() {
                Runnable runnable4 = runnable3;
                if (runnable4 != null) {
                    runnable4.run();
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.Task
            public void onSuccess() {
                Runnable runnable4 = runnable2;
                if (runnable4 != null) {
                    runnable4.run();
                }
            }
        });
    }

    public static boolean shouldKeepTasksAsynchronousInHeadlessMode() {
        return SystemProperties.getBooleanProperty("intellij.progress.task.ignoreHeadless", false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public void run(Task task) {
        if (task == null) {
            $$$reportNull$$$0(15);
        }
        if (task.isHeadless() && !shouldKeepTasksAsynchronousInHeadlessMode()) {
            if (SwingUtilities.isEventDispatchThread()) {
                runProcessWithProgressSynchronously(task);
                return;
            } else {
                runProcessWithProgressInCurrentThread(task, new EmptyProgressIndicator(), ModalityState.defaultModalityState());
                return;
            }
        }
        if (task.isModal()) {
            runProcessWithProgressSynchronously(task.asModal());
            return;
        }
        Task.Backgroundable backgroundableAsBackgroundable = task.asBackgroundable();
        if (backgroundableAsBackgroundable.isConditionalModal() && !backgroundableAsBackgroundable.shouldStartInBackground()) {
            runProcessWithProgressSynchronously(backgroundableAsBackgroundable);
        } else {
            runAsynchronously(backgroundableAsBackgroundable);
        }
    }

    private void runAsynchronously(final Task.Backgroundable backgroundable) {
        if (backgroundable == null) {
            $$$reportNull$$$0(16);
        }
        if (ApplicationManager.getApplication().isDispatchThread()) {
            runProcessWithProgressAsynchronously(backgroundable);
        } else {
            ApplicationManager.getApplication().invokeLater(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6487xea99a528(backgroundable);
                }
            }, ModalityState.defaultModalityState());
        }
    }

    /* JADX INFO: renamed from: lambda$runAsynchronously$4$org-jetbrains-kotlin-com-intellij-openapi-progress-impl-CoreProgressManager, reason: not valid java name */
    /* synthetic */ void m6487xea99a528(Task.Backgroundable backgroundable) {
        Project project = backgroundable.getProject();
        if (project != null && project.isDisposed()) {
            LOG.info("Task canceled because of project disposal: " + backgroundable);
            m6492xec6dbd2f(backgroundable, true, null);
        } else {
            runProcessWithProgressAsynchronously(backgroundable);
        }
    }

    public Future<?> runProcessWithProgressAsynchronously(Task.Backgroundable backgroundable) {
        if (backgroundable == null) {
            $$$reportNull$$$0(17);
        }
        Future<?> futureRunProcessWithProgressAsynchronously = runProcessWithProgressAsynchronously(backgroundable, new EmptyProgressIndicator(), null);
        if (futureRunProcessWithProgressAsynchronously == null) {
            $$$reportNull$$$0(18);
        }
        return futureRunProcessWithProgressAsynchronously;
    }

    public Future<?> runProcessWithProgressAsynchronously(Task.Backgroundable backgroundable, ProgressIndicator progressIndicator, Runnable runnable) {
        if (backgroundable == null) {
            $$$reportNull$$$0(19);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(20);
        }
        Future<?> futureRunProcessWithProgressAsynchronously = runProcessWithProgressAsynchronously(backgroundable, progressIndicator, runnable, progressIndicator.getModalityState());
        if (futureRunProcessWithProgressAsynchronously == null) {
            $$$reportNull$$$0(21);
        }
        return futureRunProcessWithProgressAsynchronously;
    }

    @Deprecated
    protected void startTask(Task task, ProgressIndicator progressIndicator, Runnable runnable) {
        if (task == null) {
            $$$reportNull$$$0(22);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(23);
        }
        try {
            task.run(progressIndicator);
            try {
                if (progressIndicator instanceof ProgressIndicatorEx) {
                    ((ProgressIndicatorEx) progressIndicator).finish(task);
                }
            } finally {
                if (runnable != null) {
                    runnable.run();
                }
            }
        } catch (Throwable th) {
            try {
                if (progressIndicator instanceof ProgressIndicatorEx) {
                    ((ProgressIndicatorEx) progressIndicator).finish(task);
                }
                throw th;
            } finally {
                if (runnable != null) {
                    runnable.run();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IndicatorDisposable implements Disposable {
        private final ProgressIndicator myIndicator;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "indicator", "org/jetbrains/kotlin/com/intellij/openapi/progress/impl/CoreProgressManager$IndicatorDisposable", "<init>"));
        }

        IndicatorDisposable(ProgressIndicator progressIndicator) {
            if (progressIndicator == null) {
                $$$reportNull$$$0(0);
            }
            this.myIndicator = progressIndicator;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
        public void dispose() {
            Disposer.dispose((Disposable) this.myIndicator, false);
        }
    }

    public Future<?> runProcessWithProgressAsynchronously(Task.Backgroundable backgroundable, ProgressIndicator progressIndicator, Runnable runnable, ModalityState modalityState) {
        IndicatorDisposable indicatorDisposable;
        if (backgroundable == null) {
            $$$reportNull$$$0(24);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(25);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(26);
        }
        if (progressIndicator instanceof Disposable) {
            indicatorDisposable = new IndicatorDisposable(progressIndicator);
            Disposer.register(ApplicationManager.getApplication(), indicatorDisposable);
        } else {
            indicatorDisposable = null;
        }
        Future<?> futureRunProcessWithProgressAsync = runProcessWithProgressAsync(backgroundable, CompletableFuture.completedFuture(progressIndicator), runnable, indicatorDisposable, modalityState);
        if (futureRunProcessWithProgressAsync == null) {
            $$$reportNull$$$0(27);
        }
        return futureRunProcessWithProgressAsync;
    }

    protected Future<?> runProcessWithProgressAsync(final Task.Backgroundable backgroundable, final CompletableFuture<? extends ProgressIndicator> completableFuture, final Runnable runnable, final IndicatorDisposable indicatorDisposable, final ModalityState modalityState) {
        if (backgroundable == null) {
            $$$reportNull$$$0(28);
        }
        if (completableFuture == null) {
            $$$reportNull$$$0(29);
        }
        final AtomicLong atomicLong = new AtomicLong();
        CompletableFuture completableFutureWhenComplete = new ProgressRunner(new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6488x156e300e(backgroundable, runnable, atomicLong, (ProgressIndicator) obj);
            }
        }).onThread(ProgressRunner.ThreadToUse.POOLED).withProgress(completableFuture).submit().whenComplete(ClientId.decorateBiConsumer(new BiConsumer() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.m6490x170b2d10(backgroundable, atomicLong, modalityState, completableFuture, indicatorDisposable, (ProgressResult) obj, (Throwable) obj2);
            }
        }));
        if (completableFutureWhenComplete == null) {
            $$$reportNull$$$0(30);
        }
        return completableFutureWhenComplete;
    }

    /* JADX INFO: renamed from: lambda$runProcessWithProgressAsync$5$org-jetbrains-kotlin-com-intellij-openapi-progress-impl-CoreProgressManager, reason: not valid java name */
    /* synthetic */ Object m6488x156e300e(Task.Backgroundable backgroundable, Runnable runnable, AtomicLong atomicLong, ProgressIndicator progressIndicator) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            startTask(backgroundable, progressIndicator, runnable);
            return null;
        } finally {
            atomicLong.set(System.currentTimeMillis() - jCurrentTimeMillis);
        }
    }

    /* JADX INFO: renamed from: lambda$runProcessWithProgressAsync$7$org-jetbrains-kotlin-com-intellij-openapi-progress-impl-CoreProgressManager, reason: not valid java name */
    /* synthetic */ void m6490x170b2d10(final Task.Backgroundable backgroundable, AtomicLong atomicLong, ModalityState modalityState, CompletableFuture completableFuture, final IndicatorDisposable indicatorDisposable, final ProgressResult progressResult, Throwable th) {
        if (!progressResult.isCanceled()) {
            notifyTaskFinished(backgroundable, atomicLong.get());
        }
        if (modalityState == null) {
            try {
                modalityState = ((ProgressIndicator) completableFuture.get()).getModalityState();
            } catch (Throwable unused) {
                modalityState = ModalityState.NON_MODAL;
            }
        }
        ApplicationUtil.invokeLaterSomewhere(backgroundable.whereToRunCallbacks(), modalityState, new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6489x163cae8f(backgroundable, progressResult, indicatorDisposable);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$runProcessWithProgressAsync$6$org-jetbrains-kotlin-com-intellij-openapi-progress-impl-CoreProgressManager, reason: not valid java name */
    /* synthetic */ void m6489x163cae8f(Task.Backgroundable backgroundable, ProgressResult progressResult, IndicatorDisposable indicatorDisposable) {
        m6492xec6dbd2f(backgroundable, progressResult.isCanceled(), progressResult.getThrowable() instanceof ProcessCanceledException ? null : progressResult.getThrowable());
        if (indicatorDisposable != null) {
            Disposer.dispose(indicatorDisposable);
        }
    }

    protected boolean runProcessWithProgressSynchronously(final Task task) {
        if (task == null) {
            $$$reportNull$$$0(32);
        }
        final Ref ref = new Ref();
        Runnable runnable = new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6493x7fe3322b(task, ref);
            }
        };
        ApplicationEx applicationEx = ApplicationManagerEx.getApplicationEx();
        final boolean zRunProcessWithProgressSynchronously = applicationEx.runProcessWithProgressSynchronously(runnable, task.getTitle(), task.isCancellable(), task.isModal(), task.getProject(), task.getParentComponent(), task.getCancelText());
        ApplicationUtil.invokeAndWaitSomewhere(task.whereToRunCallbacks(), applicationEx.getDefaultModalityState(), new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6494x80b1b0ac(task, zRunProcessWithProgressSynchronously, ref);
            }
        });
        return zRunProcessWithProgressSynchronously;
    }

    /* JADX INFO: renamed from: lambda$runProcessWithProgressSynchronously$8$org-jetbrains-kotlin-com-intellij-openapi-progress-impl-CoreProgressManager, reason: not valid java name */
    /* synthetic */ void m6493x7fe3322b(Task task, Ref ref) {
        try {
            startTask(task, getProgressIndicator(), null);
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            ref.set(th);
        }
    }

    /* JADX INFO: renamed from: lambda$runProcessWithProgressSynchronously$9$org-jetbrains-kotlin-com-intellij-openapi-progress-impl-CoreProgressManager, reason: not valid java name */
    /* synthetic */ void m6494x80b1b0ac(Task task, boolean z, Ref ref) {
        m6492xec6dbd2f(task, !z, (Throwable) ref.get());
    }

    public void runProcessWithProgressInCurrentThread(final Task task, final ProgressIndicator progressIndicator, ModalityState modalityState) {
        boolean z;
        if (task == null) {
            $$$reportNull$$$0(33);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(34);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(35);
        }
        if (progressIndicator instanceof Disposable) {
            Disposer.register(ApplicationManager.getApplication(), (Disposable) progressIndicator);
        }
        final boolean z2 = true;
        final Throwable th = null;
        try {
            runProcess(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6491xeb9f3eae(task, progressIndicator);
                }
            }, progressIndicator);
        } catch (ProcessCanceledException unused) {
            z = true;
        } catch (Throwable th2) {
            th = th2;
        }
        z = false;
        if (!z && !progressIndicator.isCanceled()) {
            z2 = false;
        }
        ApplicationUtil.invokeAndWaitSomewhere(task.whereToRunCallbacks(), modalityState, new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6492xec6dbd2f(task, z2, th);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$runProcessWithProgressInCurrentThread$10$org-jetbrains-kotlin-com-intellij-openapi-progress-impl-CoreProgressManager, reason: not valid java name */
    /* synthetic */ void m6491xeb9f3eae(Task task, ProgressIndicator progressIndicator) {
        startTask(task, progressIndicator, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: renamed from: finishTask, reason: merged with bridge method [inline-methods] */
    public void m6492xec6dbd2f(Task task, boolean z, Throwable th) {
        if (task == null) {
            $$$reportNull$$$0(36);
        }
        try {
            if (th != null) {
                task.onThrowable(th);
            } else if (z) {
                task.onCancel();
            } else {
                task.onSuccess();
            }
            task.onFinished();
        } catch (Throwable th2) {
            task.onFinished();
            throw th2;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public void runProcessWithProgressAsynchronously(Task.Backgroundable backgroundable, ProgressIndicator progressIndicator) {
        if (backgroundable == null) {
            $$$reportNull$$$0(37);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(38);
        }
        runProcessWithProgressAsynchronously(backgroundable, progressIndicator, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider
    public ProgressIndicator getProgressIndicator() {
        return getCurrentIndicator(Thread.currentThread());
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public void executeProcessUnderProgress(final Runnable runnable, ProgressIndicator progressIndicator) throws Throwable {
        if (runnable == null) {
            $$$reportNull$$$0(39);
        }
        computeUnderProgress(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda14
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return CoreProgressManager.lambda$executeProcessUnderProgress$12(runnable);
            }
        }, progressIndicator);
    }

    static /* synthetic */ Object lambda$executeProcessUnderProgress$12(Runnable runnable) throws RuntimeException {
        runnable.run();
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public boolean runInReadActionWithWriteActionPriority(Runnable runnable, ProgressIndicator progressIndicator) {
        if (runnable == null) {
            $$$reportNull$$$0(40);
        }
        ApplicationManager.getApplication().runReadAction(runnable);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x001e A[Catch: all -> 0x0018, TRY_LEAVE, TryCatch #1 {all -> 0x0018, blocks: (B:7:0x0010, B:15:0x001e, B:17:0x002d, B:22:0x0039, B:23:0x003c, B:24:0x003d, B:16:0x0029), top: B:33:0x0010, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:19:0x0032  */
    /* JADX WARN: Code duplicated, block: B:24:0x003d A[Catch: all -> 0x0018, TRY_LEAVE, TryCatch #1 {all -> 0x0018, blocks: (B:7:0x0010, B:15:0x001e, B:17:0x002d, B:22:0x0039, B:23:0x003c, B:24:0x003d, B:16:0x0029), top: B:33:0x0010, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:26:0x0043  */
    private <V, E extends Throwable> V computeUnderProgress(ThrowableComputable<V, E> throwableComputable, ProgressIndicator progressIndicator) throws Throwable {
        ProgressIndicator progressIndicator2;
        boolean z;
        long id2;
        if (throwableComputable == null) {
            $$$reportNull$$$0(41);
        }
        if (progressIndicator == null) {
            this.myUnsafeProgressCount.incrementAndGet();
        }
        if (progressIndicator != null) {
            try {
                progressIndicator2 = getProgressIndicator();
                z = progressIndicator != progressIndicator2;
                if (z) {
                    Thread threadCurrentThread = Thread.currentThread();
                    id2 = threadCurrentThread.getId();
                    setCurrentIndicator(id2, progressIndicator);
                    try {
                        V v = (V) registerIndicatorAndRun(progressIndicator, threadCurrentThread, progressIndicator2, throwableComputable);
                        setCurrentIndicator(id2, progressIndicator2);
                        if (progressIndicator == null) {
                            this.myUnsafeProgressCount.decrementAndGet();
                        }
                        return v;
                    } catch (Throwable th) {
                        setCurrentIndicator(id2, progressIndicator2);
                        throw th;
                    }
                }
                V vCompute = throwableComputable.compute();
                if (progressIndicator == null) {
                    this.myUnsafeProgressCount.decrementAndGet();
                }
                return vCompute;
            } catch (Throwable th2) {
                if (progressIndicator == null) {
                    this.myUnsafeProgressCount.decrementAndGet();
                }
                throw th2;
            }
        }
        progressIndicator2 = null;
        if (z) {
            Thread threadCurrentThread2 = Thread.currentThread();
            id2 = threadCurrentThread2.getId();
            setCurrentIndicator(id2, progressIndicator);
            V v2 = (V) registerIndicatorAndRun(progressIndicator, threadCurrentThread2, progressIndicator2, throwableComputable);
            setCurrentIndicator(id2, progressIndicator2);
            if (progressIndicator == null) {
                this.myUnsafeProgressCount.decrementAndGet();
            }
            return v2;
        }
        V vCompute2 = throwableComputable.compute();
        if (progressIndicator == null) {
            this.myUnsafeProgressCount.decrementAndGet();
        }
        return vCompute2;
    }

    private <V, E extends Throwable> V registerIndicatorAndRun(ProgressIndicator progressIndicator, Thread thread, ProgressIndicator progressIndicator2, ThrowableComputable<V, E> throwableComputable) throws Throwable {
        boolean z;
        ProgressIndicator originalProgressIndicator;
        if (progressIndicator == null) {
            $$$reportNull$$$0(42);
        }
        if (thread == null) {
            $$$reportNull$$$0(43);
        }
        if (throwableComputable == null) {
            $$$reportNull$$$0(44);
        }
        ArrayList arrayList = new ArrayList();
        synchronized (threadsUnderIndicator) {
            z = false;
            ProgressIndicator originalProgressIndicator2 = progressIndicator;
            boolean zIsCanceled = false;
            while (true) {
                originalProgressIndicator = null;
                if (originalProgressIndicator2 == null) {
                    break;
                }
                Set<Thread> setComputeIfAbsent = threadsUnderIndicator.computeIfAbsent(originalProgressIndicator2, new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda11
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return CoreProgressManager.lambda$registerIndicatorAndRun$13((ProgressIndicator) obj);
                    }
                });
                if (!setComputeIfAbsent.add(thread)) {
                    setComputeIfAbsent = null;
                }
                arrayList.add(setComputeIfAbsent);
                if (!(originalProgressIndicator2 instanceof StandardProgressIndicator)) {
                    nonStandardIndicators.compute(originalProgressIndicator2, new BiFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda12
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            return CoreProgressManager.lambda$registerIndicatorAndRun$14((ProgressIndicator) obj, (AtomicInteger) obj2);
                        }
                    });
                    startBackgroundNonStandardIndicatorsPing();
                }
                zIsCanceled |= originalProgressIndicator2.isCanceled();
                originalProgressIndicator2 = originalProgressIndicator2 instanceof WrappedProgressIndicator ? ((WrappedProgressIndicator) originalProgressIndicator2).getOriginalProgressIndicator() : null;
                throw th;
            }
            updateThreadUnderCanceledIndicator(thread, zIsCanceled);
        }
        try {
            V vCompute = throwableComputable.compute();
            synchronized (threadsUnderIndicator) {
                int i = 0;
                while (i < arrayList.size()) {
                    originalProgressIndicator = i == 0 ? progressIndicator : ((WrappedProgressIndicator) originalProgressIndicator).getOriginalProgressIndicator();
                    Set set = (Set) arrayList.get(i);
                    if (set != null && set.remove(thread) && set.isEmpty()) {
                        threadsUnderIndicator.remove(originalProgressIndicator);
                    }
                    if (!(originalProgressIndicator instanceof StandardProgressIndicator) && nonStandardIndicators.compute(originalProgressIndicator, new BiFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda13
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            return CoreProgressManager.lambda$registerIndicatorAndRun$15((ProgressIndicator) obj, (AtomicInteger) obj2);
                        }
                    }) == null) {
                        stopBackgroundNonStandardIndicatorsPing();
                    }
                    i++;
                }
                if (progressIndicator2 != null && progressIndicator2.isCanceled()) {
                    z = true;
                }
                updateThreadUnderCanceledIndicator(thread, z);
            }
            return vCompute;
        } catch (Throwable th) {
            synchronized (threadsUnderIndicator) {
                int i2 = 0;
                while (i2 < arrayList.size()) {
                    originalProgressIndicator = i2 == 0 ? progressIndicator : ((WrappedProgressIndicator) originalProgressIndicator).getOriginalProgressIndicator();
                    Set set2 = (Set) arrayList.get(i2);
                    if (set2 != null && set2.remove(thread) && set2.isEmpty()) {
                        threadsUnderIndicator.remove(originalProgressIndicator);
                    }
                    if (!(originalProgressIndicator instanceof StandardProgressIndicator) && nonStandardIndicators.compute(originalProgressIndicator, new BiFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager$$ExternalSyntheticLambda13
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            return CoreProgressManager.lambda$registerIndicatorAndRun$15((ProgressIndicator) obj, (AtomicInteger) obj2);
                        }
                    }) == null) {
                        stopBackgroundNonStandardIndicatorsPing();
                    }
                    i2++;
                }
                if (progressIndicator2 != null && progressIndicator2.isCanceled()) {
                    z = true;
                }
                updateThreadUnderCanceledIndicator(thread, z);
                throw th;
            }
        }
    }

    static /* synthetic */ Set lambda$registerIndicatorAndRun$13(ProgressIndicator progressIndicator) {
        return new HashSet();
    }

    static /* synthetic */ AtomicInteger lambda$registerIndicatorAndRun$14(ProgressIndicator progressIndicator, AtomicInteger atomicInteger) {
        if (atomicInteger == null) {
            return new AtomicInteger(1);
        }
        atomicInteger.incrementAndGet();
        return atomicInteger;
    }

    static /* synthetic */ AtomicInteger lambda$registerIndicatorAndRun$15(ProgressIndicator progressIndicator, AtomicInteger atomicInteger) {
        if (atomicInteger.decrementAndGet() == 0) {
            return null;
        }
        return atomicInteger;
    }

    private void updateThreadUnderCanceledIndicator(Thread thread, boolean z) {
        if (thread == null) {
            $$$reportNull$$$0(45);
        }
        if (z ? threadsUnderCanceledIndicator.add(thread) : threadsUnderCanceledIndicator.remove(thread)) {
            updateShouldCheckCanceled();
        }
    }

    final void updateShouldCheckCanceled() {
        CheckCanceledBehavior checkCanceledBehavior;
        synchronized (threadsUnderIndicator) {
            CheckCanceledHook checkCanceledHookCreateCheckCanceledHook = createCheckCanceledHook();
            boolean zIsEmpty = threadsUnderCanceledIndicator.isEmpty();
            ourCheckCanceledHook = checkCanceledHookCreateCheckCanceledHook;
            if (checkCanceledHookCreateCheckCanceledHook == null && zIsEmpty) {
                checkCanceledBehavior = CheckCanceledBehavior.NONE;
            } else {
                checkCanceledBehavior = (zIsEmpty || !ENABLED) ? CheckCanceledBehavior.ONLY_HOOKS : CheckCanceledBehavior.INDICATOR_PLUS_HOOKS;
            }
            ourCheckCanceledBehavior = checkCanceledBehavior;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    protected void indicatorCanceled(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(46);
        }
        Map<ProgressIndicator, Set<Thread>> map = threadsUnderIndicator;
        synchronized (map) {
            Set<Thread> set = map.get(progressIndicator);
            if (set != null) {
                for (Thread thread : set) {
                    ProgressIndicator currentIndicator = getCurrentIndicator(thread);
                    while (currentIndicator != null) {
                        if (currentIndicator != progressIndicator) {
                            currentIndicator = currentIndicator instanceof WrappedProgressIndicator ? ((WrappedProgressIndicator) currentIndicator).getOriginalProgressIndicator() : null;
                        } else {
                            threadsUnderCanceledIndicator.add(thread);
                            updateShouldCheckCanceled();
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public boolean isInNonCancelableSection() {
        return isInNonCancelableSection.get() != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager
    public <T, E extends Throwable> T computePrioritized(ThrowableComputable<T, E> throwableComputable) throws Throwable {
        boolean z;
        if (throwableComputable == null) {
            $$$reportNull$$$0(48);
        }
        Thread threadCurrentThread = Thread.currentThread();
        synchronized (this.myPrioritizationLock) {
            if (isCurrentThreadPrioritized()) {
                z = false;
            } else {
                if (this.myPrioritizedThreads.isEmpty()) {
                    this.myPrioritizingStarted = System.nanoTime();
                }
                this.myPrioritizedThreads.add(threadCurrentThread);
                updateEffectivePrioritized();
                z = true;
            }
        }
        try {
            T tCompute = throwableComputable.compute();
            if (z) {
                synchronized (this.myPrioritizationLock) {
                    this.myPrioritizedThreads.remove(threadCurrentThread);
                    updateEffectivePrioritized();
                }
            }
            return tCompute;
        } catch (Throwable th) {
            if (z) {
                synchronized (this.myPrioritizationLock) {
                    this.myPrioritizedThreads.remove(threadCurrentThread);
                    updateEffectivePrioritized();
                }
            }
            throw th;
        }
    }

    private void updateEffectivePrioritized() {
        Thread[] threadArr;
        Thread[] threadArr2 = this.myEffectivePrioritizedThreads;
        if (this.myDeprioritizations > 0 || this.myPrioritizedThreads.isEmpty()) {
            threadArr = EMPTY_THREAD_ARRAY;
        } else {
            threadArr = (Thread[]) this.myPrioritizedThreads.toArray(EMPTY_THREAD_ARRAY);
        }
        this.myEffectivePrioritizedThreads = threadArr;
        if (threadArr2.length == 0 && threadArr.length > 0) {
            prioritizingStarted();
        } else {
            if (threadArr2.length <= 0 || threadArr.length != 0) {
                return;
            }
            prioritizingFinished();
        }
    }

    public boolean isCurrentThreadPrioritized() {
        return this.myPrioritizedThreads.contains(Thread.currentThread());
    }

    private static void setCurrentIndicator(long j, ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            currentIndicators.remove(j);
            threadTopLevelIndicators.remove(j);
        } else {
            currentIndicators.put(j, progressIndicator);
            threadTopLevelIndicators.putIfAbsent(j, progressIndicator);
        }
    }

    private static ProgressIndicator getCurrentIndicator(Thread thread) {
        if (thread == null) {
            $$$reportNull$$$0(50);
        }
        return currentIndicators.get(thread.getId());
    }
}
