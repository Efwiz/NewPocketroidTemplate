package org.jetbrains.kotlin.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: compiled from: OffsetsOnlyPositioningStrategy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/OffsetsOnlyPositioningStrategy;", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "()V", "isValid", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "mark", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "startOffset", "", "endOffset", "markDiagnostic", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "markKtDiagnostic", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class OffsetsOnlyPositioningStrategy extends AbstractSourceElementPositioningStrategy {
    @Override // org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy
    public boolean isValid(AbstractKtSourceElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return true;
    }

    public List<TextRange> markKtDiagnostic(AbstractKtSourceElement element, KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return mark(element.getStartOffset(), element.getEndOffset());
    }

    public List<TextRange> mark(int startOffset, int endOffset) {
        return OffsetsOnlyPositioningStrategyKt.markElement(startOffset, endOffset);
    }

    @Override // org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy
    public List<TextRange> markDiagnostic(KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return markKtDiagnostic(diagnostic.getElement(), diagnostic);
    }
}
