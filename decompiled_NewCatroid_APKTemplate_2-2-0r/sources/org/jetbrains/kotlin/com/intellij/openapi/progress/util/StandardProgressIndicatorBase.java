package org.jetbrains.kotlin.com.intellij.openapi.progress.util;

import org.jetbrains.kotlin.com.intellij.openapi.progress.StandardProgressIndicator;

/* JADX INFO: loaded from: classes3.dex */
public final class StandardProgressIndicatorBase extends AbstractProgressIndicatorBase implements StandardProgressIndicator {
    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public void cancel() {
        super.cancel();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
    public boolean isCanceled() {
        return super.isCanceled();
    }
}
