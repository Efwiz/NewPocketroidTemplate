package org.jetbrains.kotlin.com.intellij.openapi.progress.impl;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.codeWithMe.ClientId;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.application.impl.LaterInvocator;
import org.jetbrains.kotlin.com.intellij.openapi.application.impl.ModalityStateEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.EmptyProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.util.concurrency.AppExecutorUtil;
import org.jetbrains.kotlin.com.intellij.util.concurrency.Semaphore;
import org.jetbrains.kotlin.com.intellij.util.ui.EDT;

/* JADX INFO: loaded from: classes3.dex */
public final class ProgressRunner<R> {
    private static final Logger LOG = Logger.getInstance((Class<?>) ProgressRunner.class);
    private final boolean isModal;
    private final boolean isSync;
    private final Function<? super ProgressIndicator, ? extends R> myComputation;
    private final CompletableFuture<? extends ProgressIndicator> myProgressIndicatorFuture;
    private final ThreadToUse myThreadToUse;

    public enum ThreadToUse {
        WRITE,
        POOLED,
        FJ
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 9 || i == 10 || i == 14 || i == 18 || i == 25) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 9 || i == 10 || i == 14 || i == 18 || i == 25) ? 2 : 3];
        if (i != 1) {
            switch (i) {
                case 4:
                    objArr[0] = "use";
                    break;
                case 5:
                case 8:
                case 24:
                    objArr[0] = "progressIndicatorFuture";
                    break;
                case 6:
                    objArr[0] = "thread";
                    break;
                case 7:
                    objArr[0] = "progressIndicator";
                    break;
                case 9:
                case 10:
                case 14:
                case 18:
                case 25:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/impl/ProgressRunner";
                    break;
                case 11:
                case 15:
                case 22:
                    objArr[0] = "progressFuture";
                    break;
                case 12:
                case 16:
                    objArr[0] = "modalityEntered";
                    break;
                case 13:
                case 17:
                    objArr[0] = "onThreadCallable";
                    break;
                case 19:
                case 20:
                    objArr[0] = "resultFuture";
                    break;
                case 21:
                    objArr[0] = "pollAction";
                    break;
                case 23:
                    objArr[0] = "callable";
                    break;
                default:
                    objArr[0] = "computation";
                    break;
            }
        } else {
            objArr[0] = "task";
        }
        if (i == 9) {
            objArr[1] = "submitAndGet";
        } else if (i == 10) {
            objArr[1] = "submit";
        } else if (i == 14) {
            objArr[1] = "execFromEDT";
        } else if (i == 18) {
            objArr[1] = "normalExec";
        } else if (i != 25) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/impl/ProgressRunner";
        } else {
            objArr[1] = "launchTask";
        }
        switch (i) {
            case 6:
                objArr[2] = "onThread";
                break;
            case 7:
            case 8:
                objArr[2] = "withProgress";
                break;
            case 9:
            case 10:
            case 14:
            case 18:
            case 25:
                break;
            case 11:
            case 12:
            case 13:
                objArr[2] = "execFromEDT";
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "normalExec";
                break;
            case 19:
                objArr[2] = "waitForFutureUnlockingThread";
                break;
            case 20:
            case 21:
                objArr[2] = "pollLaterInvocatorActively";
                break;
            case 22:
                objArr[2] = "isCanceled";
                break;
            case 23:
            case 24:
                objArr[2] = "launchTask";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 9 && i != 10 && i != 14 && i != 18 && i != 25) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ Object lambda$execFromEDT$8(Object obj, Void r1) {
        return obj;
    }

    static /* synthetic */ Object lambda$normalExec$11(Object obj, Throwable th) {
        return obj;
    }

    static /* synthetic */ Object lambda$normalExec$14(Object obj, Void r1) {
        return obj;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ProgressRunner(Function<? super ProgressIndicator, ? extends R> function) {
        this(function, false, false, ThreadToUse.POOLED, CompletableFuture.completedFuture(new EmptyProgressIndicator()));
        if (function == null) {
            $$$reportNull$$$0(2);
        }
    }

    private ProgressRunner(Function<? super ProgressIndicator, ? extends R> function, boolean z, boolean z2, ThreadToUse threadToUse, CompletableFuture<? extends ProgressIndicator> completableFuture) {
        if (function == null) {
            $$$reportNull$$$0(3);
        }
        if (threadToUse == null) {
            $$$reportNull$$$0(4);
        }
        if (completableFuture == null) {
            $$$reportNull$$$0(5);
        }
        this.myComputation = ClientId.decorateFunction(function);
        this.isSync = z;
        this.isModal = z2;
        this.myThreadToUse = threadToUse;
        this.myProgressIndicatorFuture = completableFuture;
    }

    public ProgressRunner<R> onThread(ThreadToUse threadToUse) {
        if (threadToUse == null) {
            $$$reportNull$$$0(6);
        }
        return threadToUse == this.myThreadToUse ? this : new ProgressRunner<>(this.myComputation, this.isSync, this.isModal, threadToUse, this.myProgressIndicatorFuture);
    }

    public ProgressRunner<R> withProgress(CompletableFuture<? extends ProgressIndicator> completableFuture) {
        if (completableFuture == null) {
            $$$reportNull$$$0(8);
        }
        return this.myProgressIndicatorFuture == completableFuture ? this : new ProgressRunner<>(this.myComputation, this.isSync, this.isModal, this.myThreadToUse, completableFuture);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CompletableFuture<ProgressResult<R>> submit() {
        CompletableFuture<R> completableFutureExecFromEDT;
        boolean zCheckIfForceDirectExecNeeded = checkIfForceDirectExecNeeded();
        final CompletableFuture<? extends ProgressIndicator> completableFutureThenApply = this.myProgressIndicatorFuture.thenApply(new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ProgressRunner.lambda$submit$2((ProgressIndicator) obj);
            }
        });
        final Semaphore semaphore = new Semaphore(!zCheckIfForceDirectExecNeeded ? 1 : 0);
        Supplier<R> supplier = new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6497xa1e819a4(semaphore, completableFutureThenApply);
            }
        };
        if (zCheckIfForceDirectExecNeeded) {
            completableFutureExecFromEDT = new CompletableFuture<>();
            try {
                completableFutureExecFromEDT.complete(supplier.get());
            } catch (Throwable th) {
                completableFutureExecFromEDT.completeExceptionally(th);
            }
        } else {
            completableFutureExecFromEDT = ApplicationManager.getApplication().isDispatchThread() ? execFromEDT(completableFutureThenApply, semaphore, supplier) : normalExec(completableFutureThenApply, semaphore, supplier);
        }
        CompletableFuture<ProgressResult<R>> completableFuture = (CompletableFuture<ProgressResult<R>>) completableFutureExecFromEDT.handle(new BiFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda8
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ProgressRunner.lambda$submit$5(completableFutureThenApply, obj, (Throwable) obj2);
            }
        });
        if (completableFuture == null) {
            $$$reportNull$$$0(10);
        }
        return completableFuture;
    }

    static /* synthetic */ ProgressIndicator lambda$submit$2(ProgressIndicator progressIndicator) {
        if (progressIndicator instanceof Disposable) {
            Disposer.register(ApplicationManager.getApplication(), (Disposable) progressIndicator);
        }
        return progressIndicator;
    }

    /* JADX INFO: renamed from: lambda$submit$4$org-jetbrains-kotlin-com-intellij-openapi-progress-impl-ProgressRunner, reason: not valid java name */
    /* synthetic */ Object m6497xa1e819a4(Semaphore semaphore, CompletableFuture completableFuture) {
        final Ref refCreate = Ref.create();
        if (this.isModal) {
            semaphore.waitFor();
        }
        try {
            final ProgressIndicator progressIndicator = (ProgressIndicator) completableFuture.join();
            if (progressIndicator == null) {
                throw new IllegalStateException("Expected not-null progress indicator but got null from " + this.myProgressIndicatorFuture);
            }
            ProgressManager.getInstance().runProcess(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6496x1f9d64c5(refCreate, progressIndicator);
                }
            }, progressIndicator);
            return refCreate.get();
        } catch (Throwable th) {
            throw new RuntimeException("Can't get progress", th);
        }
    }

    /* JADX INFO: renamed from: lambda$submit$3$org-jetbrains-kotlin-com-intellij-openapi-progress-impl-ProgressRunner, reason: not valid java name */
    /* synthetic */ void m6496x1f9d64c5(Ref ref, ProgressIndicator progressIndicator) {
        ref.set(this.myComputation.apply(progressIndicator));
    }

    static /* synthetic */ ProgressResult lambda$submit$5(CompletableFuture completableFuture, Object obj, Throwable th) {
        Throwable thUnwrap = unwrap(th);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            if (thUnwrap != null) {
                logger.debug("ProgressRunner: task completed with throwable", thUnwrap);
            }
            if (isCanceled(completableFuture)) {
                logger.debug("ProgressRunner: task cancelled");
            }
        }
        return new ProgressResult(obj, (thUnwrap instanceof ProcessCanceledException) || isCanceled(completableFuture), thUnwrap);
    }

    private boolean checkIfForceDirectExecNeeded() {
        if (this.isSync && EDT.isCurrentThreadEdt() && !ApplicationManager.getApplication().isWriteThread()) {
            throw new IllegalStateException("Running sync tasks on pure EDT (w/o IW lock) is dangerous for several reasons.");
        }
        if (!this.isSync && this.isModal && EDT.isCurrentThreadEdt()) {
            throw new IllegalStateException("Running async modal tasks from EDT is impossible: modal implies sync dialog show + polling events");
        }
        boolean z = this.isSync && ApplicationManager.getApplication().isDispatchThread() && (ApplicationManager.getApplication().isWriteAccessAllowed() || !this.isModal);
        if (z) {
            String str = ApplicationManager.getApplication().isWriteAccessAllowed() ? "inside Write Action" : "not modal execution";
            String str2 = this.isModal ? "Use Modal execution; " : "";
            if (this.myThreadToUse == ThreadToUse.POOLED || this.myThreadToUse == ThreadToUse.FJ) {
                str2 = str2 + "Use pooled thread; ";
            }
            if (str2.isEmpty()) {
                str2 = "none";
            }
            Logger.getInstance((Class<?>) ProgressRunner.class).warn("Forced to sync exec on EDT. Reason: " + str + ". Failed constraints: " + str2, new Throwable());
        }
        return z;
    }

    private CompletableFuture<R> execFromEDT(CompletableFuture<? extends ProgressIndicator> completableFuture, final Semaphore semaphore, Supplier<R> supplier) {
        if (completableFuture == null) {
            $$$reportNull$$$0(11);
        }
        if (semaphore == null) {
            $$$reportNull$$$0(12);
        }
        if (supplier == null) {
            $$$reportNull$$$0(13);
        }
        final CompletableFuture<R> completableFutureLaunchTask = launchTask(supplier, completableFuture);
        if (this.isModal) {
            completableFutureLaunchTask = completableFutureLaunchTask.thenCombine((CompletionStage) completableFuture.thenAccept(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ProgressRunner.lambda$execFromEDT$6(semaphore, completableFutureLaunchTask, (ProgressIndicator) obj);
                }
            }).exceptionally(new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda12
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ProgressRunner.lambda$execFromEDT$7(completableFutureLaunchTask, (Throwable) obj);
                }
            }), new BiFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda13
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ProgressRunner.lambda$execFromEDT$8(obj, (Void) obj2);
                }
            });
        }
        if (this.isSync) {
            try {
                completableFutureLaunchTask.get();
            } catch (Throwable unused) {
            }
        }
        if (completableFutureLaunchTask == null) {
            $$$reportNull$$$0(14);
        }
        return completableFutureLaunchTask;
    }

    static /* synthetic */ void lambda$execFromEDT$6(final Semaphore semaphore, CompletableFuture completableFuture, ProgressIndicator progressIndicator) {
        if (progressIndicator instanceof BlockingProgressIndicator) {
            Objects.requireNonNull(semaphore);
            ((BlockingProgressIndicator) progressIndicator).startBlocking(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    semaphore.up();
                }
            }, completableFuture);
        } else {
            Logger.getInstance((Class<?>) ProgressRunner.class).warn("Can't go modal without BlockingProgressIndicator");
            semaphore.up();
        }
    }

    static /* synthetic */ Void lambda$execFromEDT$7(CompletableFuture completableFuture, Throwable th) {
        completableFuture.completeExceptionally(th);
        return null;
    }

    private CompletableFuture<R> normalExec(CompletableFuture<? extends ProgressIndicator> completableFuture, final Semaphore semaphore, Supplier<R> supplier) throws Throwable {
        if (completableFuture == null) {
            $$$reportNull$$$0(15);
        }
        if (semaphore == null) {
            $$$reportNull$$$0(16);
        }
        if (supplier == null) {
            $$$reportNull$$$0(17);
        }
        if (this.isModal) {
            completableFuture = completableFuture.thenApplyAsync(new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda15
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ProgressRunner.lambda$normalExec$9(semaphore, (ProgressIndicator) obj);
                }
            }, new Executor() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda16
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    ProgressRunner.lambda$normalExec$10(runnable);
                }
            });
        }
        CompletableFuture<R> completableFutureLaunchTask = launchTask(supplier, completableFuture);
        if (this.isModal) {
            completableFutureLaunchTask = completableFutureLaunchTask.thenCombine((CompletionStage) completableFutureLaunchTask.handle(new BiFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda17
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ProgressRunner.lambda$normalExec$11(obj, (Throwable) obj2);
                }
            }).thenAcceptBoth((CompletionStage) completableFuture, (BiConsumer<? super U, ? super U>) new BiConsumer() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ProgressRunner.lambda$normalExec$13(obj, (ProgressIndicator) obj2);
                }
            }), new BiFunction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda2
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ProgressRunner.lambda$normalExec$14(obj, (Void) obj2);
                }
            });
        }
        if (this.isSync) {
            waitForFutureUnlockingThread(completableFutureLaunchTask);
        }
        if (completableFutureLaunchTask == null) {
            $$$reportNull$$$0(18);
        }
        return completableFutureLaunchTask;
    }

    static /* synthetic */ ProgressIndicator lambda$normalExec$9(Semaphore semaphore, ProgressIndicator progressIndicator) {
        LaterInvocator.enterModal(progressIndicator, (ModalityStateEx) progressIndicator.getModalityState());
        semaphore.up();
        return progressIndicator;
    }

    static /* synthetic */ void lambda$normalExec$10(Runnable runnable) {
        if (ApplicationManager.getApplication().isWriteThread()) {
            runnable.run();
        } else {
            ApplicationManager.getApplication().invokeLaterOnWriteThread(runnable);
        }
    }

    static /* synthetic */ void lambda$normalExec$13(Object obj, final ProgressIndicator progressIndicator) {
        if (ApplicationManager.getApplication().isWriteThread()) {
            LaterInvocator.leaveModal(progressIndicator);
        } else {
            ApplicationManager.getApplication().invokeLaterOnWriteThread(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    LaterInvocator.leaveModal(progressIndicator);
                }
            }, progressIndicator.getModalityState());
        }
    }

    private static void waitForFutureUnlockingThread(CompletableFuture<?> completableFuture) throws Throwable {
        if (completableFuture == null) {
            $$$reportNull$$$0(19);
        }
        if (ApplicationManager.getApplication().isWriteThread()) {
            pollLaterInvocatorActively(completableFuture, new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    LaterInvocator.pollWriteThreadEventsOnce();
                }
            });
        } else {
            if (EDT.isCurrentThreadEdt()) {
                throw new UnsupportedOperationException("Sync waiting from EDT is dangerous.");
            }
            try {
                completableFuture.get();
            } catch (Throwable unused) {
            }
        }
    }

    private static void pollLaterInvocatorActively(final CompletableFuture<?> completableFuture, final Runnable runnable) throws Throwable {
        if (completableFuture == null) {
            $$$reportNull$$$0(20);
        }
        if (runnable == null) {
            $$$reportNull$$$0(21);
        }
        ApplicationManagerEx.getApplicationEx().runUnlockingIntendedWrite(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return ProgressRunner.lambda$pollLaterInvocatorActively$15(completableFuture, runnable);
            }
        });
    }

    static /* synthetic */ Object lambda$pollLaterInvocatorActively$15(CompletableFuture completableFuture, Runnable runnable) throws RuntimeException {
        while (true) {
            try {
                completableFuture.get(10L, TimeUnit.MILLISECONDS);
                return null;
            } catch (TimeoutException unused) {
                ApplicationManagerEx.getApplicationEx().runIntendedWriteActionOnCurrentThread(runnable);
            } catch (Throwable unused2) {
                return null;
            }
        }
    }

    public static boolean isCanceled(Future<? extends ProgressIndicator> future) {
        if (future == null) {
            $$$reportNull$$$0(22);
        }
        try {
            return future.get().isCanceled();
        } catch (Throwable unused) {
            return false;
        }
    }

    public static Throwable unwrap(Throwable th) {
        return ((th instanceof CompletionException) || (th instanceof ExecutionException)) ? th.getCause() : th;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$openapi$progress$impl$ProgressRunner$ThreadToUse;

        static {
            int[] iArr = new int[ThreadToUse.values().length];
            $SwitchMap$com$intellij$openapi$progress$impl$ProgressRunner$ThreadToUse = iArr;
            try {
                iArr[ThreadToUse.POOLED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$openapi$progress$impl$ProgressRunner$ThreadToUse[ThreadToUse.FJ.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$openapi$progress$impl$ProgressRunner$ThreadToUse[ThreadToUse.WRITE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private CompletableFuture<R> launchTask(final Supplier<R> supplier, CompletableFuture<? extends ProgressIndicator> completableFuture) {
        final CompletableFuture<R> completableFutureSupplyAsync;
        if (supplier == null) {
            $$$reportNull$$$0(23);
        }
        if (completableFuture == null) {
            $$$reportNull$$$0(24);
        }
        int i = AnonymousClass1.$SwitchMap$com$intellij$openapi$progress$impl$ProgressRunner$ThreadToUse[this.myThreadToUse.ordinal()];
        if (i == 1) {
            completableFutureSupplyAsync = CompletableFuture.supplyAsync(supplier, AppExecutorUtil.getAppExecutorService());
        } else if (i == 2) {
            completableFutureSupplyAsync = CompletableFuture.supplyAsync(supplier, ForkJoinPool.commonPool());
        } else if (i == 3) {
            completableFutureSupplyAsync = new CompletableFuture<>();
            final Runnable runnable = new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ProgressRunner.lambda$launchTask$16(completableFutureSupplyAsync, supplier);
                }
            };
            completableFuture.whenComplete(new BiConsumer() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.ProgressRunner$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ProgressRunner.lambda$launchTask$17(completableFutureSupplyAsync, runnable, (ProgressIndicator) obj, (Throwable) obj2);
                }
            });
        } else {
            throw new IllegalStateException("Unexpected value: " + this.myThreadToUse);
        }
        if (completableFutureSupplyAsync == null) {
            $$$reportNull$$$0(25);
        }
        return completableFutureSupplyAsync;
    }

    static /* synthetic */ void lambda$launchTask$16(CompletableFuture completableFuture, Supplier supplier) {
        try {
            completableFuture.complete(supplier.get());
        } catch (Throwable th) {
            completableFuture.completeExceptionally(th);
        }
    }

    static /* synthetic */ void lambda$launchTask$17(CompletableFuture completableFuture, Runnable runnable, ProgressIndicator progressIndicator, Throwable th) {
        if (th != null) {
            completableFuture.completeExceptionally(th);
        } else {
            ApplicationManager.getApplication().invokeLaterOnWriteThread(runnable, progressIndicator.getModalityState());
        }
    }
}
