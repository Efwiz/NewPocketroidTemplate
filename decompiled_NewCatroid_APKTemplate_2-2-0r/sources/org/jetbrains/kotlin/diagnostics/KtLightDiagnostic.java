package org.jetbrains.kotlin.diagnostics;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\u00020\u00078VX\u0097\u0004¢\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtLightDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticMarker;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "getElement", "()Lorg/jetbrains/kotlin/KtLightSourceElement;", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getPsiElement$annotations", "()V", "getPsiElement", "()Lcom/intellij/psi/PsiElement;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtLightDiagnostic extends DiagnosticMarker {
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Should not be called")
    static /* synthetic */ void getPsiElement$annotations() {
    }

    KtLightSourceElement getElement();

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticMarker
    /* synthetic */ default PsiElement getPsiElement() {
        throw new IllegalStateException("psiElement should not be called on KtLightDiagnostic".toString());
    }
}
