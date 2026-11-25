package org.jetbrains.kotlin.progress;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;

/* JADX INFO: compiled from: CancelationStatus.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\u0004H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/progress/ProgressIndicatorAndCompilationCanceledStatus;", "", "()V", "canceledStatus", "Lorg/jetbrains/kotlin/progress/CompilationCanceledStatus;", "checkCanceled", "", "setCompilationCanceledStatus", "newCanceledStatus", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProgressIndicatorAndCompilationCanceledStatus {
    public static final ProgressIndicatorAndCompilationCanceledStatus INSTANCE = new ProgressIndicatorAndCompilationCanceledStatus();
    private static CompilationCanceledStatus canceledStatus;

    private ProgressIndicatorAndCompilationCanceledStatus() {
    }

    @JvmStatic
    public static final synchronized void setCompilationCanceledStatus(CompilationCanceledStatus newCanceledStatus) {
        canceledStatus = newCanceledStatus;
    }

    @JvmStatic
    public static final void checkCanceled() {
        ProgressIndicatorProvider.checkCanceled();
        CompilationCanceledStatus compilationCanceledStatus = canceledStatus;
        if (compilationCanceledStatus != null) {
            compilationCanceledStatus.checkCanceled();
        }
    }
}
