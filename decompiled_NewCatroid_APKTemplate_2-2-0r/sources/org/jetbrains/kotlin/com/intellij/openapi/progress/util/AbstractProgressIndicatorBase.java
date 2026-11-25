package org.jetbrains.kotlin.com.intellij.openapi.progress.util;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuardImpl;
import org.jetbrains.kotlin.com.intellij.openapi.application.impl.ModalityStateEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.ui.CoreAwareIconManager;
import org.jetbrains.kotlin.com.intellij.ui.IconManager;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;

/* JADX INFO: loaded from: classes3.dex */
public class AbstractProgressIndicatorBase extends UserDataHolderBase implements ProgressIndicator {
    private static final Logger LOG = Logger.getInstance((Class<?>) AbstractProgressIndicatorBase.class);
    private final Object lock;
    private volatile boolean myCanceled;
    private volatile double myFraction;
    private volatile boolean myIndeterminate = Boolean.parseBoolean(System.getProperty("ide.progress.indeterminate.by.default", "true"));
    private volatile Runnable myMacActivity;
    private volatile ProgressIndicator myModalityProgress;
    private volatile ModalityState myModalityState;
    private volatile int myNonCancelableSectionCount;
    private volatile boolean myRunning;
    private volatile boolean myShouldStartActivity;
    private Stack<State> myStateStack;
    private volatile boolean myStopped;
    private volatile String myText;
    private volatile String myText2;

    /* JADX WARN: Code duplicated, block: B:22:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "state";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/util/AbstractProgressIndicatorBase";
        } else if (i == 3) {
            objArr[0] = "indicator";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/util/AbstractProgressIndicatorBase";
        } else {
            objArr[0] = CrashHianalyticsData.MESSAGE;
        }
        if (i == 2) {
            objArr[1] = "getModalityState";
        } else if (i == 4) {
            objArr[1] = "getStateStack";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/util/AbstractProgressIndicatorBase";
        } else {
            objArr[1] = "getLock";
        }
        if (i == 1) {
            objArr[2] = "restoreFrom";
        } else if (i != 2) {
            if (i == 3) {
                objArr[2] = "initStateFrom";
            } else if (i != 4 && i != 5) {
                objArr[2] = "throwInvalidState";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public boolean isPopupWasShown() {
        return true;
    }

    protected boolean isReuseable() {
        return false;
    }

    public AbstractProgressIndicatorBase() {
        this.myShouldStartActivity = SystemInfoRt.isMac && Boolean.parseBoolean(System.getProperty("idea.mac.prevent.app.nap", "true"));
        this.myModalityState = ModalityState.NON_MODAL;
        this.lock = ObjectUtils.sentinel("APIB lock");
    }

    private static class State {
        private final double myFraction;
        private final boolean myIndeterminate;
        private final String myText;
        private final String myText2;

        private State(String str, String str2, double d, boolean z) {
            this.myText = str;
            this.myText2 = str2;
            this.myFraction = d;
            this.myIndeterminate = z;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void start() {
        synchronized (getLock()) {
            if (isRunning()) {
                throwInvalidState("Attempt to start ProgressIndicator which is already running");
            }
            if (this.myStopped) {
                if (this.myCanceled && !isReuseable()) {
                    throwInvalidState("Attempt to start ProgressIndicator which is cancelled and already stopped");
                }
                this.myCanceled = false;
                this.myStopped = false;
            }
            this.myText = "";
            this.myFraction = 0.0d;
            this.myText2 = "";
            if (this.myShouldStartActivity) {
                IconManager iconManager = IconManager.getInstance();
                if (iconManager instanceof CoreAwareIconManager) {
                    this.myMacActivity = ((CoreAwareIconManager) iconManager).wakeUpNeo(this);
                }
            } else {
                this.myMacActivity = null;
            }
            this.myRunning = true;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void stop() {
        synchronized (getLock()) {
            if (this.myStopped) {
                throwInvalidState("Attempt to stop ProgressIndicator which is already stopped");
            }
            if (!this.myRunning) {
                throwInvalidState("stop() should be called only if start() called before");
            }
            this.myRunning = false;
            this.myStopped = true;
            stopSystemActivity();
        }
    }

    private void throwInvalidState(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        LOG.error(str + ": " + this + "," + getClass(), new IllegalStateException());
    }

    void stopSystemActivity() {
        Runnable runnable = this.myMacActivity;
        if (runnable != null) {
            runnable.run();
            this.myMacActivity = null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public boolean isRunning() {
        return this.myRunning;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void cancel() {
        this.myCanceled = true;
        stopSystemActivity();
        if (ApplicationManager.getApplication() != null) {
            ProgressManager.canceled(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public boolean isCanceled() {
        return this.myCanceled;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void checkCanceled() {
        throwIfCanceled();
        if (CoreProgressManager.runCheckCanceledHooks(this)) {
            throwIfCanceled();
        }
    }

    private void throwIfCanceled() {
        if (isCanceled() && isCancelable()) {
            Throwable cancellationTrace = getCancellationTrace();
            if (!(cancellationTrace instanceof ProcessCanceledException)) {
                throw new ProcessCanceledException(cancellationTrace);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected Throwable getCancellationTrace() {
        if (this instanceof Disposable) {
            return Disposer.getDisposalTrace((Disposable) this);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setText(String str) {
        this.myText = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public String getText() {
        return this.myText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setText2(String str) {
        this.myText2 = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public String getText2() {
        return this.myText2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public double getFraction() {
        return this.myFraction;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setFraction(double d) {
        synchronized (getLock()) {
            if (isIndeterminate()) {
                LOG.warn("This progress indicator is indeterminate, this may lead to visual inconsistency. Please call setIndeterminate(false) before you start progress. " + getClass(), new IllegalStateException());
                setIndeterminate(false);
            }
            this.myFraction = d;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void pushState() {
        synchronized (getLock()) {
            getStateStack().push(getState());
        }
    }

    private State getState() {
        return new State(getText(), getText2(), getFraction(), isIndeterminate());
    }

    private void restoreFrom(State state) {
        if (state == null) {
            $$$reportNull$$$0(1);
        }
        setText(state.myText);
        setText2(state.myText2);
        setIndeterminate(state.myIndeterminate);
        if (isIndeterminate()) {
            return;
        }
        setFraction(state.myFraction);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void popState() {
        synchronized (getLock()) {
            restoreFrom(this.myStateStack.pop());
        }
    }

    public void startNonCancelableSection() {
        PluginException.reportDeprecatedUsage("ProgressIndicator#startNonCancelableSection", "Use `ProgressManager.executeNonCancelableSection()` instead");
        this.myNonCancelableSectionCount++;
    }

    public void finishNonCancelableSection() {
        this.myNonCancelableSectionCount--;
    }

    protected boolean isCancelable() {
        return this.myNonCancelableSectionCount == 0 && !ProgressManager.getInstance().isInNonCancelableSection();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final boolean isModal() {
        return this.myModalityProgress != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public ModalityState getModalityState() {
        ModalityState modalityState = this.myModalityState;
        if (modalityState == null) {
            $$$reportNull$$$0(2);
        }
        return modalityState;
    }

    public void setModalityProgress(ProgressIndicator progressIndicator) {
        if (isRunning()) {
            throwInvalidState("setModalityProgress() must not be called on already running indicator");
        }
        this.myModalityProgress = progressIndicator;
        setModalityState(progressIndicator);
    }

    private void setModalityState(ProgressIndicator progressIndicator) {
        ModalityState modalityStateDefaultModalityState = ModalityState.defaultModalityState();
        if (progressIndicator != null) {
            modalityStateDefaultModalityState = ((ModalityStateEx) modalityStateDefaultModalityState).appendProgress(progressIndicator);
            ((TransactionGuardImpl) TransactionGuard.getInstance()).enteredModality(modalityStateDefaultModalityState);
        }
        this.myModalityState = modalityStateDefaultModalityState;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public boolean isIndeterminate() {
        return this.myIndeterminate;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setIndeterminate(boolean z) {
        synchronized (getLock()) {
            this.myIndeterminate = z;
            if (z && getFraction() != 0.0d) {
                this.myFraction = 0.0d;
            }
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "ProgressIndicator " + System.identityHashCode(this) + ": running=" + isRunning() + "; canceled=" + isCanceled();
    }

    public boolean isShowing() {
        return isModal();
    }

    public void initStateFrom(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(3);
        }
        synchronized (getLock()) {
            this.myRunning = progressIndicator.isRunning();
            this.myCanceled = progressIndicator.isCanceled();
            boolean zIsIndeterminate = progressIndicator.isIndeterminate();
            setIndeterminate(zIsIndeterminate);
            if (!zIsIndeterminate || progressIndicator.getFraction() != 0.0d) {
                setFraction(progressIndicator.getFraction());
            }
            setText(progressIndicator.getText());
            setText2(progressIndicator.getText2());
            if (progressIndicator instanceof AbstractProgressIndicatorBase) {
                AbstractProgressIndicatorBase abstractProgressIndicatorBase = (AbstractProgressIndicatorBase) progressIndicator;
                this.myStateStack = abstractProgressIndicatorBase.myStateStack == null ? null : new Stack<>(abstractProgressIndicatorBase.getStateStack());
            }
            dontStartActivity();
        }
    }

    protected void dontStartActivity() {
        this.myShouldStartActivity = false;
    }

    private Stack<State> getStateStack() {
        Stack<State> stack = this.myStateStack;
        if (stack == null) {
            stack = new Stack<>(2);
            this.myStateStack = stack;
        }
        if (stack == null) {
            $$$reportNull$$$0(4);
        }
        return stack;
    }

    protected Object getLock() {
        Object obj = this.lock;
        if (obj == null) {
            $$$reportNull$$$0(5);
        }
        return obj;
    }
}
