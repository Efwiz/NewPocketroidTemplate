package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.CastingType;
import org.jetbrains.kotlin.fir.analysis.checkers.FirCastDiagnosticsHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirCastOperatorsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCastOperatorsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTypeOperatorCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCastOperatorsChecker extends FirExpressionChecker<FirTypeOperatorCall> {
    public static final FirCastOperatorsChecker INSTANCE = new FirCastOperatorsChecker();

    private FirCastOperatorsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirTypeOperatorCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirSession session = context.getSession();
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(FirExpressionUtilKt.unwrapSmartcastExpression(expression.getArgumentList().getArguments().get(0)).getTypeRef()), session);
        FirTypeRef conversionTypeRef = expression.getConversionTypeRef();
        ConeKotlinType coneKotlinTypeFullyExpandedType2 = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(conversionTypeRef), session);
        if (FirOperation.INSTANCE.getTYPES().contains(expression.getOperation()) && (coneKotlinTypeFullyExpandedType2 instanceof ConeDynamicType)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, conversionTypeRef.getSource(), FirErrors.INSTANCE.getDYNAMIC_NOT_ALLOWED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        boolean z = expression.getOperation() == FirOperation.SAFE_AS;
        if (expression.getOperation() == FirOperation.AS || z) {
            CastingType castingTypeCheckCasting = FirCastDiagnosticsHelpersKt.checkCasting(coneKotlinTypeFullyExpandedType, coneKotlinTypeFullyExpandedType2, z, context);
            if (castingTypeCheckCasting == CastingType.Impossible) {
                if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.EnableDfaWarningsInK2)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getCAST_NEVER_SUCCEEDS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                return;
            } else if (castingTypeCheckCasting == CastingType.Always) {
                if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.EnableDfaWarningsInK2)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getUSELESS_CAST(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
                return;
            } else {
                if (FirCastDiagnosticsHelpersKt.isCastErased(coneKotlinTypeFullyExpandedType, coneKotlinTypeFullyExpandedType2, context)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getUNCHECKED_CAST(), coneKotlinTypeFullyExpandedType, coneKotlinTypeFullyExpandedType2, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                    return;
                }
                return;
            }
        }
        if (expression.getOperation() == FirOperation.IS && !context.getIsContractBody() && FirCastDiagnosticsHelpersKt.isCastErased(coneKotlinTypeFullyExpandedType, coneKotlinTypeFullyExpandedType2, context)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, conversionTypeRef.getSource(), FirErrors.INSTANCE.getCANNOT_CHECK_FOR_ERASED(), coneKotlinTypeFullyExpandedType2, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
