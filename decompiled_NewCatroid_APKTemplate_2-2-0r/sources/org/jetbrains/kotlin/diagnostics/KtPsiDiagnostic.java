package org.jetbrains.kotlin.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: compiled from: KtDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0012\u0010\u0012\u001a\u00020\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtPsiDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticMarker;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/KtPsiSourceElement;", "getElement", "()Lorg/jetbrains/kotlin/KtPsiSourceElement;", "factory", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getPsiElement", "()Lcom/intellij/psi/PsiElement;", "psiFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getPsiFile", "()Lcom/intellij/psi/PsiFile;", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "textRanges", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTextRanges", "()Ljava/util/List;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtPsiDiagnostic extends DiagnosticMarker {
    KtPsiSourceElement getElement();

    AbstractKtDiagnosticFactory getFactory();

    Severity getSeverity();

    List<TextRange> getTextRanges();

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticMarker
    default PsiElement getPsiElement() {
        return getElement().getPsi();
    }

    default PsiFile getPsiFile() {
        PsiFile containingFile = getPsiElement().getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile;
    }
}
