package org.jetbrains.kotlin.fir.analysis.checkers.type;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.types.FirIntersectionTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirDefinitelyNotNullableChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirDefinitelyNotNullableChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "()V", "check", "", "typeRef", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDefinitelyNotNullableChecker extends FirTypeChecker<FirTypeRef> {
    public static final FirDefinitelyNotNullableChecker INSTANCE = new FirDefinitelyNotNullableChecker();

    private FirDefinitelyNotNullableChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker
    public void check(FirTypeRef typeRef, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        FirTypeRef delegatedTypeRef = firResolvedTypeRef != null ? firResolvedTypeRef.getDelegatedTypeRef() : null;
        FirIntersectionTypeRef firIntersectionTypeRef = delegatedTypeRef instanceof FirIntersectionTypeRef ? (FirIntersectionTypeRef) delegatedTypeRef : null;
        if (firIntersectionTypeRef == null) {
            return;
        }
        if (firIntersectionTypeRef.getIsMarkedNullable()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firIntersectionTypeRef.getSource(), FirErrors.INSTANCE.getNULLABLE_ON_DEFINITELY_NOT_NULLABLE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (!FirTypeUtilsKt.isLeftValidForDefinitelyNotNullable(firIntersectionTypeRef)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firIntersectionTypeRef.getLeftType().getSource(), FirErrors.INSTANCE.getINCORRECT_LEFT_COMPONENT_OF_INTERSECTION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (FirTypeUtilsKt.isRightValidForDefinitelyNotNullable(firIntersectionTypeRef)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firIntersectionTypeRef.getRightType().getSource(), FirErrors.INSTANCE.getINCORRECT_RIGHT_COMPONENT_OF_INTERSECTION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
