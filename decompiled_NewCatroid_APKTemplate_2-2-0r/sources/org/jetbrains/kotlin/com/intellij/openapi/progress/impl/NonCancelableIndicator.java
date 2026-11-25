package org.jetbrains.kotlin.com.intellij.openapi.progress.impl;

import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.progress.StandardProgressIndicator;

/* JADX INFO: loaded from: classes3.dex */
class NonCancelableIndicator implements StandardProgressIndicator {
    static final NonCancelableIndicator INSTANCE = new NonCancelableIndicator() { // from class: org.jetbrains.kotlin.com.intellij.openapi.progress.impl.NonCancelableIndicator.1
        public int hashCode() {
            return 0;
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/openapi/progress/impl/NonCancelableIndicator", "getModalityState"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final void cancel() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public double getFraction() {
        return 0.0d;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public String getText() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public String getText2() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final boolean isCanceled() {
        return false;
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
    public boolean isRunning() {
        return true;
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

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void start() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void stop() {
    }

    protected NonCancelableIndicator() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public final void checkCanceled() {
        CoreProgressManager.runCheckCanceledHooks(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public ModalityState getModalityState() {
        ModalityState modalityState = ModalityState.NON_MODAL;
        if (modalityState == null) {
            $$$reportNull$$$0(0);
        }
        return modalityState;
    }
}
