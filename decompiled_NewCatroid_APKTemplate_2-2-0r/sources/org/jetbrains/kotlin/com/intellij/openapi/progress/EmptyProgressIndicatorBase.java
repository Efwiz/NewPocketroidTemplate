package org.jetbrains.kotlin.com.intellij.openapi.progress;

import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;

/* JADX INFO: loaded from: classes3.dex */
public abstract class EmptyProgressIndicatorBase implements ProgressIndicator {
    private final ModalityState myModalityState;
    private volatile int myNonCancelableSectionCount;
    private volatile RunState myRunState;

    private enum RunState {
        VIRGIN,
        STARTED,
        STOPPED
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "modalityState";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/progress/EmptyProgressIndicatorBase";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/progress/EmptyProgressIndicatorBase";
        } else {
            objArr[1] = "getModalityState";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public double getFraction() {
        return 1.0d;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public boolean isIndeterminate() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public boolean isModal() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void popState() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void pushState() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setFraction(double d) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setIndeterminate(boolean z) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setText(String str) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void setText2(String str) {
    }

    public EmptyProgressIndicatorBase() {
        this(ModalityState.defaultModalityState());
    }

    public EmptyProgressIndicatorBase(ModalityState modalityState) {
        if (modalityState == null) {
            $$$reportNull$$$0(0);
        }
        this.myRunState = RunState.VIRGIN;
        this.myModalityState = modalityState;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void start() {
        if (this.myRunState == RunState.STARTED) {
            throw new IllegalStateException("Indicator already started");
        }
        this.myRunState = RunState.STARTED;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.openapi.progress.EmptyProgressIndicatorBase$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$openapi$progress$EmptyProgressIndicatorBase$RunState;

        static {
            int[] iArr = new int[RunState.values().length];
            $SwitchMap$com$intellij$openapi$progress$EmptyProgressIndicatorBase$RunState = iArr;
            try {
                iArr[RunState.VIRGIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$openapi$progress$EmptyProgressIndicatorBase$RunState[RunState.STARTED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$openapi$progress$EmptyProgressIndicatorBase$RunState[RunState.STOPPED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void stop() {
        int i = AnonymousClass1.$SwitchMap$com$intellij$openapi$progress$EmptyProgressIndicatorBase$RunState[this.myRunState.ordinal()];
        if (i == 1) {
            throw new IllegalStateException("Indicator can't be stopped because it wasn't started");
        }
        if (i == 2) {
            this.myRunState = RunState.STOPPED;
        } else if (i == 3) {
            throw new IllegalStateException("Indicator already stopped");
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public boolean isRunning() {
        return this.myRunState == RunState.STARTED;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final void checkCanceled() {
        if (isCanceled() && this.myNonCancelableSectionCount == 0) {
            throw new ProcessCanceledException();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public String getText() {
        return "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public String getText2() {
        return "";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public ModalityState getModalityState() {
        ModalityState modalityState = this.myModalityState;
        if (modalityState == null) {
            $$$reportNull$$$0(1);
        }
        return modalityState;
    }
}
