package org.jetbrains.kotlin.com.intellij.util.io;

import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;

/* JADX INFO: loaded from: classes3.dex */
public interface IOCancellationCallback {
    void checkCancelled() throws ProcessCanceledException;

    void interactWithUI();
}
