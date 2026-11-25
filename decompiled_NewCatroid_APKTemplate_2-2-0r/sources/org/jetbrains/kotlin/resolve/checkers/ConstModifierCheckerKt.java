package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import org.jetbrains.kotlin.diagnostics.Diagnostic;

/* JADX INFO: compiled from: ConstModifierChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002¨\u0006\u0003"}, d2 = {"nonApplicable", "Lorg/jetbrains/kotlin/resolve/checkers/ConstApplicability$NonApplicable;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstModifierCheckerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ConstApplicability.NonApplicable nonApplicable(Diagnostic diagnostic) {
        return new ConstApplicability.NonApplicable(diagnostic);
    }
}
