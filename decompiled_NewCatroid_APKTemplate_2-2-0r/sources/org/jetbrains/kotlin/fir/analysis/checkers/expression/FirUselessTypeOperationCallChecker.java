package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirCastDiagnosticsHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirUselessTypeOperationCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirUselessTypeOperationCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirTypeOperatorCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUselessTypeOperationCallChecker extends FirExpressionChecker<FirTypeOperatorCall> {
    public static final FirUselessTypeOperationCallChecker INSTANCE = new FirUselessTypeOperationCallChecker();

    /* JADX INFO: compiled from: FirUselessTypeOperationCallChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FirOperation.values().length];
            try {
                iArr[FirOperation.IS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirOperation.NOT_IS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FirOperation.AS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FirOperation.SAFE_AS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirUselessTypeOperationCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirTypeOperatorCall expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (FirOperation.INSTANCE.getTYPES().contains(expression.getOperation())) {
            FirExpression firExpression = (FirExpression) CollectionsKt.first((List) expression.getArgumentList().getArguments());
            ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(ConeTypeUtilsKt.upperBoundIfFlexible(FirTypeUtilsKt.getConeType(firExpression.getTypeRef())), context.getSession());
            if (coneSimpleKotlinTypeFullyExpandedType instanceof ConeErrorType) {
                return;
            }
            ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(expression.getConversionTypeRef()), context.getSession());
            if (coneKotlinTypeFullyExpandedType instanceof ConeErrorType) {
                return;
            }
            if (expression.getOperation() == FirOperation.SAFE_AS) {
                coneKotlinTypeFullyExpandedType = TypeUtilsKt.withNullability$default(coneKotlinTypeFullyExpandedType, ConeNullability.NULLABLE, TypeComponentsKt.getTypeContext(context.getSession()), null, false, 12, null);
            }
            if (FirCastDiagnosticsHelpersKt.isRefinementUseless(context, coneSimpleKotlinTypeFullyExpandedType, coneKotlinTypeFullyExpandedType, FirCastDiagnosticsHelpersKt.shouldCheckForExactType(expression, context), firExpression)) {
                int i = WhenMappings.$EnumSwitchMapping$0[expression.getOperation().ordinal()];
                if (i == 1) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, (AbstractKtSourceElement) expression.getSource(), (KtDiagnosticFactory1) FirErrors.INSTANCE.getUSELESS_IS_CHECK(), (Object) true, (DiagnosticContext) context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    return;
                }
                if (i == 2) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, (AbstractKtSourceElement) expression.getSource(), (KtDiagnosticFactory1) FirErrors.INSTANCE.getUSELESS_IS_CHECK(), (Object) false, (DiagnosticContext) context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    return;
                }
                if (i == 3 || i == 4) {
                    FirTypeRef typeRef = firExpression.getTypeRef();
                    FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
                    if (firResolvedTypeRef == null || !firResolvedTypeRef.getIsFromStubType()) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getUSELESS_CAST(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        return;
                    }
                    return;
                }
                throw new AssertionError("Should not be here: " + expression.getOperation());
            }
        }
    }
}
