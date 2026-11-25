package org.jetbrains.kotlin.diagnostics.rendering;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters1;
import org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters2;
import org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters3;
import org.jetbrains.kotlin.diagnostics.DiagnosticWithParameters4;
import org.jetbrains.kotlin.diagnostics.ParametrizedDiagnostic;
import org.jetbrains.kotlin.diagnostics.SimpleDiagnostic;

/* JADX INFO: compiled from: RenderingContextUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\b"}, d2 = {"fromDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext$Companion;", "d", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "parameters", "", "", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RenderingContextUtilsKt {
    public static final List<Object> parameters(RenderingContext.Companion companion, Diagnostic d) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(d, "d");
        if (d instanceof SimpleDiagnostic) {
            return CollectionsKt.emptyList();
        }
        if (d instanceof DiagnosticWithParameters1) {
            return CollectionsKt.listOf(((DiagnosticWithParameters1) d).getA());
        }
        if (d instanceof DiagnosticWithParameters2) {
            DiagnosticWithParameters2 diagnosticWithParameters2 = (DiagnosticWithParameters2) d;
            return CollectionsKt.listOf(diagnosticWithParameters2.getA(), diagnosticWithParameters2.getB());
        }
        if (d instanceof DiagnosticWithParameters3) {
            DiagnosticWithParameters3 diagnosticWithParameters3 = (DiagnosticWithParameters3) d;
            return CollectionsKt.listOf(diagnosticWithParameters3.getA(), diagnosticWithParameters3.getB(), diagnosticWithParameters3.getC());
        }
        if (d instanceof DiagnosticWithParameters4) {
            DiagnosticWithParameters4 diagnosticWithParameters4 = (DiagnosticWithParameters4) d;
            return CollectionsKt.listOf(diagnosticWithParameters4.getA(), diagnosticWithParameters4.getB(), diagnosticWithParameters4.getC(), diagnosticWithParameters4.getD());
        }
        if (d instanceof ParametrizedDiagnostic) {
            throw new IllegalStateException(("Unexpected diagnostic: " + d.getClass()).toString());
        }
        return CollectionsKt.emptyList();
    }

    public static final RenderingContext fromDiagnostic(RenderingContext.Companion companion, Diagnostic d) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        Intrinsics.checkNotNullParameter(d, "d");
        return new RenderingContext.Impl(parameters(companion, d));
    }
}
