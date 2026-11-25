package org.jetbrains.kotlin.diagnostics;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: PositioningStrategy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\bJ\u001b\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0007\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "()V", "isValid", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lcom/intellij/psi/PsiElement;)Z", "mark", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "(Lcom/intellij/psi/PsiElement;)Ljava/util/List;", "markDiagnostic", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticMarker;", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class PositioningStrategy<E extends PsiElement> {
    public List<TextRange> markDiagnostic(DiagnosticMarker diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        PsiElement psiElement = diagnostic.getPsiElement();
        Intrinsics.checkNotNull(psiElement, "null cannot be cast to non-null type E of org.jetbrains.kotlin.diagnostics.PositioningStrategy");
        return mark(psiElement);
    }

    public List<TextRange> mark(E element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return PositioningStrategyKt.markElement(element);
    }

    public boolean isValid(E element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return !PositioningStrategyKt.hasSyntaxErrors(element);
    }
}
