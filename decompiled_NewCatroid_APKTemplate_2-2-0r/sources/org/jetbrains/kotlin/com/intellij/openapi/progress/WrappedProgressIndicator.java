package org.jetbrains.kotlin.com.intellij.openapi.progress;

/* JADX INFO: loaded from: classes3.dex */
public interface WrappedProgressIndicator extends ProgressIndicator {
    ProgressIndicator getOriginalProgressIndicator();
}
