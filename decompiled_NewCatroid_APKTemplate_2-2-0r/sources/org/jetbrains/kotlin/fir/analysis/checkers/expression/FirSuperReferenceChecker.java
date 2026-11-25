package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeArgumentList;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;

/* JADX INFO: compiled from: FirSuperReferenceChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirSuperReferenceChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSuperReferenceChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirSuperReferenceChecker INSTANCE = new FirSuperReferenceChecker();

    private FirSuperReferenceChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirQualifierPart firQualifierPart;
        FirTypeArgumentList typeArgumentList;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirReference calleeReference = expression.getCalleeReference();
        FirSuperReference firSuperReference = calleeReference instanceof FirSuperReference ? (FirSuperReference) calleeReference : null;
        if (firSuperReference != null) {
            if (!ReferenceUtilsKt.hadExplicitTypeInSource(firSuperReference)) {
                firSuperReference = null;
            }
            if (firSuperReference == null) {
                return;
            }
            FirTypeRef superTypeRef = firSuperReference.getSuperTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = superTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) superTypeRef : null;
            FirTypeRef delegatedTypeRef = firResolvedTypeRef != null ? firResolvedTypeRef.getDelegatedTypeRef() : null;
            FirUserTypeRef firUserTypeRef = delegatedTypeRef instanceof FirUserTypeRef ? (FirUserTypeRef) delegatedTypeRef : null;
            if (firUserTypeRef == null || (firQualifierPart = (FirQualifierPart) CollectionsKt.firstOrNull((List) firUserTypeRef.getQualifier())) == null || (typeArgumentList = firQualifierPart.getTypeArgumentList()) == null) {
                return;
            }
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(superTypeRef);
            if ((coneType instanceof ConeErrorType) || typeArgumentList.getTypeArguments().isEmpty()) {
                return;
            }
            for (ConeTypeProjection coneTypeProjection : coneType.getTypeArguments()) {
                if (coneTypeProjection instanceof ConeErrorType) {
                    return;
                }
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, typeArgumentList.getSource(), FirErrors.INSTANCE.getTYPE_ARGUMENTS_REDUNDANT_IN_SUPER_QUALIFIER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
