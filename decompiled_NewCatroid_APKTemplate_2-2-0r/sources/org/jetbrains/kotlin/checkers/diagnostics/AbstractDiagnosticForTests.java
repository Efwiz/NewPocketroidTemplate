package org.jetbrains.kotlin.checkers.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory;
import org.jetbrains.kotlin.diagnostics.Severity;

/* JADX INFO: compiled from: DiagnosticForTests.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0018\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/AbstractDiagnosticForTests;", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "factory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "(Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;)V", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory;", "isValid", "", "()Z", "getPsiElement", "()Lcom/intellij/psi/PsiElement;", "psiFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getPsiFile", "()Lcom/intellij/psi/PsiFile;", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "textRanges", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTextRanges", "()Ljava/util/List;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AbstractDiagnosticForTests implements Diagnostic {
    private final DiagnosticFactory<?> factory;
    private final PsiElement psiElement;

    @Override // org.jetbrains.kotlin.diagnostics.UnboundDiagnostic
    /* JADX INFO: renamed from: isValid */
    public boolean getIsValid() {
        return true;
    }

    public AbstractDiagnosticForTests(PsiElement psiElement, DiagnosticFactory<?> factory) {
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.psiElement = psiElement;
        this.factory = factory;
    }

    @Override // org.jetbrains.kotlin.diagnostics.UnboundDiagnostic
    public DiagnosticFactory<?> getFactory() {
        return this.factory;
    }

    @Override // org.jetbrains.kotlin.diagnostics.Diagnostic, org.jetbrains.kotlin.diagnostics.DiagnosticMarker
    public PsiElement getPsiElement() {
        return this.psiElement;
    }

    @Override // org.jetbrains.kotlin.diagnostics.UnboundDiagnostic
    public Severity getSeverity() {
        return Severity.ERROR;
    }

    @Override // org.jetbrains.kotlin.diagnostics.UnboundDiagnostic
    public List<TextRange> getTextRanges() {
        return CollectionsKt.listOf(getPsiElement().getTextRange());
    }

    @Override // org.jetbrains.kotlin.diagnostics.Diagnostic
    public PsiFile getPsiFile() {
        PsiFile containingFile = getPsiElement().getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile;
    }
}
