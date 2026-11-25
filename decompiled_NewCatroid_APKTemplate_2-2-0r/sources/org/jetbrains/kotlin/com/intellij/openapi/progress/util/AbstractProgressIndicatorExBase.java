package org.jetbrains.kotlin.com.intellij.openapi.progress.util;

import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.TaskInfo;
import org.jetbrains.kotlin.com.intellij.openapi.wm.ex.ProgressIndicatorEx;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.WeakList;

/* JADX INFO: loaded from: classes3.dex */
public class AbstractProgressIndicatorExBase extends AbstractProgressIndicatorBase implements ProgressIndicatorEx {
    private volatile WeakList<TaskInfo> myFinished;
    private TaskInfo myOwnerTask;
    private final boolean myReusable;
    private volatile ProgressIndicatorEx[] myStateDelegates;
    private volatile boolean myWasStarted;

    @FunctionalInterface
    protected interface IndicatorAction {
        void execute(ProgressIndicatorEx progressIndicatorEx);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 2:
            case 3:
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "action";
                break;
            case 8:
                objArr[0] = "indicator";
                break;
            default:
                objArr[0] = "task";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/util/AbstractProgressIndicatorExBase";
        switch (i) {
            case 1:
                objArr[2] = "isFinished";
                break;
            case 2:
                objArr[2] = "addStateDelegate";
                break;
            case 3:
                objArr[2] = "removeStateDelegate";
                break;
            case 4:
                objArr[2] = "delegateProgressChange";
                break;
            case 5:
                objArr[2] = "doDelegateRunningChange";
                break;
            case 6:
                objArr[2] = "delegateRunningChange";
                break;
            case 7:
                objArr[2] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                break;
            case 8:
                objArr[2] = "initStateFrom";
                break;
            default:
                objArr[2] = "finish";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Deprecated
    protected void delegateRunningChange(IndicatorAction indicatorAction) {
        if (indicatorAction == null) {
            $$$reportNull$$$0(6);
        }
    }

    protected void onProgressChange() {
    }

    protected void onRunningChange() {
    }

    public AbstractProgressIndicatorExBase(boolean z) {
        this.myReusable = z;
    }

    public AbstractProgressIndicatorExBase() {
        this(false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void start() {
        synchronized (getLock()) {
            super.start();
            doDelegateRunningChange(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
                public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                    progressIndicatorEx.start();
                }
            });
            this.myWasStarted = true;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void stop() {
        super.stop();
        doDelegateRunningChange(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda8
            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
            public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                progressIndicatorEx.stop();
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void cancel() {
        super.cancel();
        doDelegateRunningChange(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda9
            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
            public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                progressIndicatorEx.cancel();
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.wm.ex.ProgressIndicatorEx
    public void finish(final TaskInfo taskInfo) {
        if (taskInfo == null) {
            $$$reportNull$$$0(0);
        }
        WeakList<TaskInfo> weakList = this.myFinished;
        if (weakList == null) {
            synchronized (getLock()) {
                weakList = this.myFinished;
                if (weakList == null) {
                    weakList = new WeakList<>();
                    this.myFinished = weakList;
                }
            }
        }
        if (weakList.addIfAbsent(taskInfo)) {
            doDelegateRunningChange(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
                public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                    progressIndicatorEx.finish(taskInfo);
                }
            });
        }
    }

    public boolean isFinished(TaskInfo taskInfo) {
        if (taskInfo == null) {
            $$$reportNull$$$0(1);
        }
        WeakList<TaskInfo> weakList = this.myFinished;
        return weakList != null && weakList.contains(taskInfo);
    }

    protected void setOwnerTask(TaskInfo taskInfo) {
        this.myOwnerTask = taskInfo;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.wm.ex.ProgressIndicatorEx
    public void processFinish() {
        TaskInfo taskInfo = this.myOwnerTask;
        if (taskInfo != null) {
            finish(taskInfo);
            this.myOwnerTask = null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final void checkCanceled() {
        super.checkCanceled();
        delegate(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
            public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                progressIndicatorEx.checkCanceled();
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setText(final String str) {
        super.setText(str);
        delegateProgressChange(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
            public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                progressIndicatorEx.setText(str);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setText2(final String str) {
        super.setText2(str);
        delegateProgressChange(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
            public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                progressIndicatorEx.setText2(str);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setFraction(final double d) {
        super.setFraction(d);
        delegateProgressChange(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
            public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                progressIndicatorEx.setFraction(d);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void pushState() {
        synchronized (getLock()) {
            super.pushState();
            delegateProgressChange(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda11
                @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
                public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                    progressIndicatorEx.pushState();
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void popState() {
        synchronized (getLock()) {
            super.popState();
            delegateProgressChange(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda4
                @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
                public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                    progressIndicatorEx.popState();
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase
    protected boolean isReuseable() {
        return this.myReusable;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setIndeterminate(final boolean z) {
        super.setIndeterminate(z);
        delegateProgressChange(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda10
            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
            public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                progressIndicatorEx.setIndeterminate(z);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.wm.ex.ProgressIndicatorEx
    public final void addStateDelegate(ProgressIndicatorEx progressIndicatorEx) {
        if (progressIndicatorEx == null) {
            $$$reportNull$$$0(2);
        }
        synchronized (getLock()) {
            progressIndicatorEx.initStateFrom(this);
            ProgressIndicatorEx[] progressIndicatorExArr = this.myStateDelegates;
            if (progressIndicatorExArr == null) {
                this.myStateDelegates = new ProgressIndicatorEx[]{progressIndicatorEx};
            } else {
                if (ArrayUtil.contains(progressIndicatorEx, progressIndicatorExArr)) {
                    throw new IllegalArgumentException("Already registered: " + progressIndicatorEx);
                }
                this.myStateDelegates = (ProgressIndicatorEx[]) ArrayUtil.append(progressIndicatorExArr, progressIndicatorEx, (Class<ProgressIndicatorEx>) ProgressIndicatorEx.class);
            }
        }
        onProgressChange();
    }

    public final void removeStateDelegate(ProgressIndicatorEx progressIndicatorEx) {
        if (progressIndicatorEx == null) {
            $$$reportNull$$$0(3);
        }
        synchronized (getLock()) {
            ProgressIndicatorEx[] progressIndicatorExArr = this.myStateDelegates;
            if (progressIndicatorExArr == null) {
                return;
            }
            this.myStateDelegates = (ProgressIndicatorEx[]) ArrayUtil.remove(progressIndicatorExArr, progressIndicatorEx);
            onProgressChange();
        }
    }

    protected final void removeAllStateDelegates() {
        synchronized (getLock()) {
            this.myStateDelegates = null;
        }
    }

    private void delegateProgressChange(IndicatorAction indicatorAction) {
        if (indicatorAction == null) {
            $$$reportNull$$$0(4);
        }
        delegate(indicatorAction);
        onProgressChange();
    }

    private void doDelegateRunningChange(IndicatorAction indicatorAction) {
        if (indicatorAction == null) {
            $$$reportNull$$$0(5);
        }
        delegate(indicatorAction);
        onRunningChange();
    }

    private void delegate(IndicatorAction indicatorAction) {
        if (indicatorAction == null) {
            $$$reportNull$$$0(7);
        }
        ProgressIndicatorEx[] progressIndicatorExArr = this.myStateDelegates;
        if (progressIndicatorExArr != null) {
            for (ProgressIndicatorEx progressIndicatorEx : progressIndicatorExArr) {
                indicatorAction.execute(progressIndicatorEx);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.wm.ex.ProgressIndicatorEx
    public void initStateFrom(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(8);
        }
        super.initStateFrom(progressIndicator);
        delegate(new IndicatorAction() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase$$ExternalSyntheticLambda7
            @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase.IndicatorAction
            public final void execute(ProgressIndicatorEx progressIndicatorEx) {
                this.f$0.m6498xfc5d639(progressIndicatorEx);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$initStateFrom$5$org-jetbrains-kotlin-com-intellij-openapi-progress-util-AbstractProgressIndicatorExBase, reason: not valid java name */
    /* synthetic */ void m6498xfc5d639(ProgressIndicatorEx progressIndicatorEx) {
        progressIndicatorEx.initStateFrom(this);
    }

    public boolean wasStarted() {
        return this.myWasStarted;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "ProgressIndicatorEx " + System.identityHashCode(this) + ": running=" + isRunning() + "; canceled=" + isCanceled() + (isReuseable() ? "; reusable=true" : "");
    }
}
