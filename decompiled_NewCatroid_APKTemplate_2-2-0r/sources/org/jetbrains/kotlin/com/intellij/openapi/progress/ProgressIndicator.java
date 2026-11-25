package org.jetbrains.kotlin.com.intellij.openapi.progress;

import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;

/* JADX INFO: loaded from: classes3.dex */
public interface ProgressIndicator {
    void cancel();

    void checkCanceled() throws ProcessCanceledException;

    double getFraction();

    ModalityState getModalityState();

    String getText();

    String getText2();

    boolean isCanceled();

    boolean isIndeterminate();

    boolean isModal();

    boolean isRunning();

    void popState();

    void pushState();

    void setFraction(double d);

    void setIndeterminate(boolean z);

    void setText(String str);

    void setText2(String str);

    void start();

    void stop();
}
