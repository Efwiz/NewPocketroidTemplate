package org.jetbrains.kotlin.checkers.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.checkers.diagnostics.factories.SyntaxErrorDiagnosticFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;

/* JADX INFO: compiled from: DiagnosticForTests.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/SyntaxErrorDiagnostic;", "Lorg/jetbrains/kotlin/checkers/diagnostics/AbstractDiagnosticForTests;", "errorElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiErrorElement;", "(Lcom/intellij/psi/PsiErrorElement;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntaxErrorDiagnostic extends AbstractDiagnosticForTests {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SyntaxErrorDiagnostic(PsiErrorElement errorElement) {
        super(errorElement, SyntaxErrorDiagnosticFactory.INSTANCE.getINSTANCE());
        Intrinsics.checkNotNullParameter(errorElement, "errorElement");
    }
}
