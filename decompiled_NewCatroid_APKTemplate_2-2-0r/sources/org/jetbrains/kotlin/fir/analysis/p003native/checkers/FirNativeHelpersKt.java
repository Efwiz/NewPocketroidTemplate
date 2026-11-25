package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.NativeForwardDeclarationKind;

/* JADX INFO: compiled from: FirNativeHelpers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002\u001a*\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r¨\u0006\u000e"}, d2 = {"forwardDeclarationKindOrNull", "Lorg/jetbrains/kotlin/name/NativeForwardDeclarationKind;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "reportIfHasAnnotation", "", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "annotationClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "error", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "checkers.native"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirNativeHelpersKt {
    public static final void reportIfHasAnnotation(DiagnosticReporter diagnosticReporter, FirDeclaration declaration, ClassId annotationClassId, KtDiagnosticFactory0 error, CheckerContext context) {
        Intrinsics.checkNotNullParameter(diagnosticReporter, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(annotationClassId, "annotationClassId");
        Intrinsics.checkNotNullParameter(error, "error");
        Intrinsics.checkNotNullParameter(context, "context");
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(declaration, annotationClassId, context.getSession());
        if (annotationByClassId != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, annotationByClassId.getSource(), error, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    public static final NativeForwardDeclarationKind forwardDeclarationKindOrNull(FirRegularClassSymbol firRegularClassSymbol) {
        Intrinsics.checkNotNullParameter(firRegularClassSymbol, "<this>");
        return NativeForwardDeclarationKind.INSTANCE.getPackageFqNameToKind().get(firRegularClassSymbol.getClassId().getPackageFqName());
    }
}
