package org.jetbrains.kotlin.com.intellij.openapi.application.impl;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.awt.Dialog;
import java.awt.Window;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import javax.swing.SwingUtilities;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.diagnostic.LoadingState;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityStateListener;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuardImpl;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.util.EventDispatcher;
import org.jetbrains.kotlin.com.intellij.util.concurrency.AppExecutorUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.com.intellij.util.ui.EDT;

/* JADX INFO: loaded from: classes3.dex */
public final class LaterInvocator {
    private static final Logger LOG = Logger.getInstance((Class<?>) LaterInvocator.class);
    private static final List<Object> ourModalEntities = ContainerUtil.createLockFreeCopyOnWriteList();
    private static final Map<Project, List<Dialog>> projectToModalEntities = ContainerUtil.createWeakMap();
    private static final Map<Project, Stack<ModalityState>> projectToModalEntitiesStack = ContainerUtil.createWeakMap();
    private static final Stack<ModalityStateEx> ourModalityStack = new Stack<>((ModalityStateEx) ModalityState.NON_MODAL);
    private static final EventDispatcher<ModalityStateListener> ourModalityStateMulticaster = EventDispatcher.create(ModalityStateListener.class);
    private static final Executor ourWriteThreadExecutor = AppExecutorUtil.createBoundedApplicationPoolExecutor("Write Thread", 1);
    private static final FlushQueue ourEdtQueue = new FlushQueue(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.impl.LaterInvocator$$ExternalSyntheticLambda1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            SwingUtilities.invokeLater((Runnable) obj);
        }
    });
    private static final FlushQueue ourWtQueue = new FlushQueue(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.impl.LaterInvocator$$ExternalSyntheticLambda2
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            LaterInvocator.ourWriteThreadExecutor.execute(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.impl.LaterInvocator$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    ApplicationManagerEx.getApplicationEx().runIntendedWriteActionOnCurrentThread(runnable);
                }
            });
        }
    });
    private static final ConcurrentMap<Window, ModalityStateEx> ourWindowModalities = CollectionFactory.createConcurrentWeakMap();
    static final AtomicBoolean FLUSHER_SCHEDULED = new AtomicBoolean(false);
    private static final AtomicLong SUBMITTED_COUNT = new AtomicLong(0);
    private static final AtomicInteger THREAD_TO_FLUSH = new AtomicInteger(0);

    /* JADX WARN: Code duplicated, block: B:7:0x000d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 12) {
            switch (i) {
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 12) {
            switch (i) {
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "parentDisposable";
                break;
            case 2:
            case 4:
                objArr[0] = ConfigConstants.CONFIG_KEY_WINDOW;
                break;
            case 3:
            case 12:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/application/impl/LaterInvocator";
                break;
            case 5:
            case 7:
            case 9:
            case 13:
            case 16:
                objArr[0] = "runnable";
                break;
            case 6:
            case 11:
            case 15:
                objArr[0] = "expired";
                break;
            case 8:
            case 10:
            case 14:
            case 17:
                objArr[0] = "modalityState";
                break;
            case 18:
            case 19:
            case 24:
            case 25:
                objArr[0] = "modalEntity";
                break;
            case 20:
                objArr[0] = "appendedState";
                break;
            case 21:
            case 23:
                objArr[0] = "dialog";
                break;
            case 22:
                objArr[0] = "state";
                break;
            default:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
        }
        if (i == 3) {
            objArr[1] = "modalityStateForWindow";
        } else if (i != 12) {
            switch (i) {
                case 26:
                    objArr[1] = "getCurrentModalEntities";
                    break;
                case 27:
                    objArr[1] = "getCurrentModalityState";
                    break;
                case 28:
                    objArr[1] = "getRunnableQueue";
                    break;
                case 29:
                    objArr[1] = "getLaterInvocatorEdtQueue";
                    break;
                case 30:
                    objArr[1] = "getLaterInvocatorWtQueue";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/application/impl/LaterInvocator";
                    break;
            }
        } else {
            objArr[1] = "invokeLater";
        }
        switch (i) {
            case 2:
                objArr[2] = "modalityStateForWindow";
                break;
            case 3:
            case 12:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                break;
            case 4:
                objArr[2] = "isModalDialog";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "invokeLater";
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "invokeLaterWithCallback";
                break;
            case 16:
            case 17:
                objArr[2] = "invokeAndWait";
                break;
            case 18:
            case 19:
            case 20:
            case 21:
                objArr[2] = "enterModal";
                break;
            case 22:
                objArr[2] = "markTransparent";
                break;
            case 23:
            case 25:
                objArr[2] = "leaveModal";
                break;
            case 24:
                objArr[2] = "removeModality";
                break;
            default:
                objArr[2] = "addModalityStateListener";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 12) {
            switch (i) {
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ int lambda$requestFlush$4(int i) {
        return i ^ 1;
    }

    private LaterInvocator() {
    }

    public static void enterModal(Object obj, ModalityStateEx modalityStateEx) {
        if (obj == null) {
            $$$reportNull$$$0(19);
        }
        if (modalityStateEx == null) {
            $$$reportNull$$$0(20);
        }
        Logger logger = LOG;
        logger.assertTrue(isWriteThread(), "enterModal() should be invoked in write thread");
        if (logger.isDebugEnabled()) {
            logger.debug("enterModal:" + obj);
        }
        ((ModalityStateListener) ourModalityStateMulticaster.getMulticaster()).beforeModalityStateChanged(true, obj);
        ourModalEntities.add(obj);
        Stack<ModalityStateEx> stack = ourModalityStack;
        synchronized (stack) {
            stack.push(modalityStateEx);
        }
        TransactionGuardImpl transactionGuardImpl = LoadingState.COMPONENTS_LOADED.isOccurred() ? (TransactionGuardImpl) TransactionGuard.getInstance() : null;
        if (transactionGuardImpl != null) {
            transactionGuardImpl.enteredModality(modalityStateEx);
        }
        reincludeSkippedItemsAndRequestFlush();
    }

    private static void removeModality(Object obj, int i) {
        if (obj == null) {
            $$$reportNull$$$0(24);
        }
        ourModalEntities.remove(i);
        Stack<ModalityStateEx> stack = ourModalityStack;
        synchronized (stack) {
            int i2 = 1;
            stack.remove(i + 1);
            while (true) {
                Stack<ModalityStateEx> stack2 = ourModalityStack;
                if (i2 < stack2.size()) {
                    stack2.get(i2).removeModality(obj);
                    i2++;
                }
            }
        }
        ModalityStateEx.unmarkTransparent(obj);
    }

    public static void leaveModal(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(25);
        }
        Logger logger = LOG;
        logger.assertTrue(isWriteThread(), "leaveModal() should be invoked in write thread");
        if (logger.isDebugEnabled()) {
            logger.debug("leaveModal:" + obj);
        }
        ((ModalityStateListener) ourModalityStateMulticaster.getMulticaster()).beforeModalityStateChanged(false, obj);
        int iIndexOf = ourModalEntities.indexOf(obj);
        logger.assertTrue(iIndexOf >= 0);
        removeModality(obj, iIndexOf);
        reincludeSkippedItemsAndRequestFlush();
    }

    public static ModalityStateEx getCurrentModalityState() {
        ModalityStateEx modalityStateExPeek;
        if (!EDT.isCurrentThreadEdt()) {
            ApplicationManager.getApplication().assertIsWriteThread();
        }
        Stack<ModalityStateEx> stack = ourModalityStack;
        synchronized (stack) {
            modalityStateExPeek = stack.peek();
        }
        if (modalityStateExPeek == null) {
            $$$reportNull$$$0(27);
        }
        return modalityStateExPeek;
    }

    private static boolean isWriteThread() {
        return ApplicationManager.getApplication().isWriteThread();
    }

    private static FlushQueue getRunnableQueue(boolean z) {
        FlushQueue flushQueue = z ? ourEdtQueue : ourWtQueue;
        if (flushQueue == null) {
            $$$reportNull$$$0(28);
        }
        return flushQueue;
    }

    static void requestFlush() {
        AtomicLong atomicLong;
        long j;
        SUBMITTED_COUNT.incrementAndGet();
        do {
            AtomicBoolean atomicBoolean = FLUSHER_SCHEDULED;
            if (!atomicBoolean.compareAndSet(false, true)) {
                return;
            }
            int andUpdate = THREAD_TO_FLUSH.getAndUpdate(new IntUnaryOperator() { // from class: org.jetbrains.kotlin.com.intellij.openapi.application.impl.LaterInvocator$$ExternalSyntheticLambda0
                @Override // java.util.function.IntUnaryOperator
                public final int applyAsInt(int i) {
                    return LaterInvocator.lambda$requestFlush$4(i);
                }
            });
            atomicLong = SUBMITTED_COUNT;
            j = atomicLong.get();
            FlushQueue runnableQueue = getRunnableQueue(andUpdate == 0);
            if (runnableQueue.mayHaveItems()) {
                runnableQueue.scheduleFlush();
                return;
            }
            FlushQueue runnableQueue2 = getRunnableQueue(andUpdate != 0);
            if (runnableQueue2.mayHaveItems()) {
                runnableQueue2.scheduleFlush();
                return;
            }
            atomicBoolean.set(false);
        } while (j != atomicLong.get());
    }

    public static void pollWriteThreadEventsOnce() {
        Logger logger = LOG;
        logger.assertTrue(!SwingUtilities.isEventDispatchThread());
        logger.assertTrue(ApplicationManager.getApplication().isWriteThread());
        ourWtQueue.flushNow();
    }

    private static void reincludeSkippedItemsAndRequestFlush() {
        ourEdtQueue.reincludeSkippedItems();
        ourWtQueue.reincludeSkippedItems();
        requestFlush();
    }
}
