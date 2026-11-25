package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0002\u001a\u00020\u0003*\u00020\u0004H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"CHECK_PSI_CONSISTENCY_IN_DIAGNOSTICS", "", "checkPsiTypeConsistency", "", "Lorg/jetbrains/kotlin/diagnostics/KtPsiDiagnostic;", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkPsiTypeConsistency(KtPsiDiagnostic ktPsiDiagnostic) {
        if (!ktPsiDiagnostic.getFactory().getPsiType().isInstance(ktPsiDiagnostic.getElement().getPsi())) {
            throw new IllegalArgumentException((Reflection.getOrCreateKotlinClass(ktPsiDiagnostic.getElement().getPsi().getClass()) + " is not a subtype of " + ktPsiDiagnostic.getFactory().getPsiType() + " for factory " + ktPsiDiagnostic.getFactory()).toString());
        }
    }
}
