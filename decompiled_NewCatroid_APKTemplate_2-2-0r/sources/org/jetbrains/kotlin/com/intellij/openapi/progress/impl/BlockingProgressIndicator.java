package org.jetbrains.kotlin.com.intellij.openapi.progress.impl;

import java.util.concurrent.CompletableFuture;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;

/* JADX INFO: loaded from: classes3.dex */
public interface BlockingProgressIndicator extends ProgressIndicator {
    @Deprecated
    void startBlocking(Runnable runnable, CompletableFuture<?> completableFuture);
}
