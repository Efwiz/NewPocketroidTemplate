package org.jetbrains.kotlin.com.intellij.openapi.progress.impl;

import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public final class ProgressResult<T> {
    private final boolean myIsCanceled;
    private final T myResult;
    private final Throwable myThrowable;

    public ProgressResult(T t, boolean z, Throwable th) {
        this.myResult = t;
        this.myIsCanceled = z;
        this.myThrowable = th;
    }

    public boolean isCanceled() {
        return this.myIsCanceled;
    }

    public Throwable getThrowable() {
        return this.myThrowable;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ProgressResult progressResult = (ProgressResult) obj;
        return this.myIsCanceled == progressResult.myIsCanceled && Objects.equals(this.myResult, progressResult.myResult) && Objects.equals(this.myThrowable, progressResult.myThrowable);
    }

    public int hashCode() {
        return Objects.hash(this.myResult, Boolean.valueOf(this.myIsCanceled), this.myThrowable);
    }
}
