package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX INFO: compiled from: FirJsNativeAnnotationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsNativeSetterChecker;", "Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsAbstractNativeIndexerChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsNativeSetterChecker extends FirJsAbstractNativeIndexerChecker {
    public static final FirJsNativeSetterChecker INSTANCE = new FirJsNativeSetterChecker();

    private FirJsNativeSetterChecker() {
        super(JsStandardClassIds.Annotations.JsNativeSetter, "setter", 2);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsAbstractNativeIndexerChecker, org.jetbrains.kotlin.fir.analysis.js.checkers.declaration.FirJsAbstractNativeAnnotationChecker, org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirSimpleFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (hasRequiredAnnotation(declaration, context)) {
            super.check(declaration, context, reporter);
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef());
            if (ConeBuiltinTypeUtilsKt.isUnit(coneType) || declaration.getValueParameters().size() < 2 || TypeUtilsKt.isSubtypeOf$default(FirTypeUtilsKt.getConeType(declaration.getValueParameters().get(1).getReturnTypeRef()), coneType, context.getSession(), false, 4, null)) {
                return;
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNATIVE_SETTER_WRONG_RETURN_TYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
