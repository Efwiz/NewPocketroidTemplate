package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticMarker;
import org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters1;
import org.jetbrains.kotlin.diagnostics.PositioningStrategy;

/* JADX INFO: compiled from: JsCodePositioningStrategy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCodePositioningStrategy;", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "()V", "markDiagnostic", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticMarker;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCodePositioningStrategy extends PositioningStrategy<PsiElement> {
    public static final JsCodePositioningStrategy INSTANCE = new JsCodePositioningStrategy();

    private JsCodePositioningStrategy() {
    }

    @Override // org.jetbrains.kotlin.diagnostics.PositioningStrategy
    public List<TextRange> markDiagnostic(DiagnosticMarker diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return CollectionsKt.listOf(((JsCallData) ((DiagnosticWithParameters1) diagnostic).getA()).getReportRange());
    }
}
