package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirReifiedChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J8\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\f\u0010\u0014\u001a\u00020\u0012*\u00020\u0015H\u0002¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirReifiedChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkArgumentAndReport", "typeArgument", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "isExplicit", "", "isArray", "isReifiedTypeParameterOrFromKotlinArray", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirReifiedChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirReifiedChecker INSTANCE = new FirReifiedChecker();

    private FirReifiedChecker() {
    }

    /* JADX WARN: Code duplicated, block: B:16:0x005a  */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        List<FirTypeParameterSymbol> typeParameterSymbols;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirReference calleeReference = expression.getCalleeReference();
        List<FirTypeProjection> typeArguments = expression.getTypeArguments();
        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null);
        if (resolvedCallableSymbol$default == null || (typeParameterSymbols = resolvedCallableSymbol$default.getTypeParameterSymbols()) == null) {
            return;
        }
        int iMin = Math.min(typeArguments.size(), typeParameterSymbols.size());
        for (int i = 0; i < iMin; i++) {
            FirTypeProjection firTypeProjection = typeArguments.get(i);
            KtSourceElement source = firTypeProjection.getSource();
            if (source != null || (source = calleeReference.getSource()) != null) {
                KtSourceElement ktSourceElement = source;
                ConeKotlinType type = ConeTypeProjectionKt.getType(FirTypeUtilsKt.toConeTypeProjection(firTypeProjection));
                if (type != null) {
                    if (isReifiedTypeParameterOrFromKotlinArray(typeParameterSymbols.get(i))) {
                        KtSourceElement source2 = firTypeProjection.getSource();
                        checkArgumentAndReport(type, ktSourceElement, Intrinsics.areEqual(source2 != null ? source2.getKind() : null, KtRealSourceElementKind.INSTANCE), false, context, reporter);
                    }
                }
            }
        }
    }

    private final boolean isReifiedTypeParameterOrFromKotlinArray(FirTypeParameterSymbol firTypeParameterSymbol) {
        FirBasedSymbol<?> containingDeclarationSymbol = firTypeParameterSymbol.getContainingDeclarationSymbol();
        return firTypeParameterSymbol.isReified() || ((containingDeclarationSymbol instanceof FirRegularClassSymbol) && Intrinsics.areEqual(((FirRegularClassSymbol) containingDeclarationSymbol).getClassId(), StandardClassIds.INSTANCE.getArray()));
    }

    private final void checkArgumentAndReport(ConeKotlinType typeArgument, KtSourceElement source, boolean isExplicit, boolean isArray, CheckerContext context, DiagnosticReporter reporter) {
        if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(typeArgument), StandardClassIds.INSTANCE.getArray())) {
            ConeKotlinType type = ConeTypeProjectionKt.getType(typeArgument.getTypeArguments()[0]);
            if (type == null) {
                return;
            }
            checkArgumentAndReport(type, source, isExplicit, true, context, reporter);
            return;
        }
        if (typeArgument instanceof ConeTypeParameterType) {
            FirTypeParameterSymbol typeParameterSymbol = ((ConeTypeParameterType) typeArgument).getLookupTag().getTypeParameterSymbol();
            if (typeParameterSymbol.isReified()) {
                return;
            }
            KtSourceElement ktSourceElement = source;
            FirErrors firErrors = FirErrors.INSTANCE;
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, ktSourceElement, isArray ? (KtDiagnosticFactory1) KtDiagnosticReportHelpersKt.chooseFactory(firErrors.getTYPE_PARAMETER_AS_REIFIED_ARRAY(), context) : firErrors.getTYPE_PARAMETER_AS_REIFIED(), typeParameterSymbol, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            return;
        }
        if ((typeArgument instanceof ConeDefinitelyNotNullType) && isExplicit) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getDEFINITELY_NON_NULLABLE_AS_REIFIED(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        } else if (ConeBuiltinTypeUtilsKt.isNothing(typeArgument) || ConeBuiltinTypeUtilsKt.isNullableNothing(typeArgument) || (typeArgument instanceof ConeCapturedType)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getREIFIED_TYPE_FORBIDDEN_SUBSTITUTION(), typeArgument, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
