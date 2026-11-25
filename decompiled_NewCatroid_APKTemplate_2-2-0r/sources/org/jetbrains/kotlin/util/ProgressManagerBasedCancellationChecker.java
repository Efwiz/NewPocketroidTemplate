package org.jetbrains.kotlin.util;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;

/* JADX INFO: compiled from: ProgressManagerBasedCancellationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/util/ProgressManagerBasedCancellationChecker;", "Lorg/jetbrains/kotlin/util/CancellationChecker;", "()V", "check", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProgressManagerBasedCancellationChecker implements CancellationChecker {
    public static final ProgressManagerBasedCancellationChecker INSTANCE = new ProgressManagerBasedCancellationChecker();

    private ProgressManagerBasedCancellationChecker() {
    }

    @Override // org.jetbrains.kotlin.util.CancellationChecker
    public void check() {
        ProgressManager.checkCanceled();
    }
}
