package org.jetbrains.kotlin.fir.analysis.js.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirJsNativeRttiChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J \u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/expression/FirJsNativeRttiChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkGetClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "checkTypeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsNativeRttiChecker extends FirExpressionChecker<FirStatement> {
    public static final FirJsNativeRttiChecker INSTANCE = new FirJsNativeRttiChecker();

    /* JADX INFO: compiled from: FirJsNativeRttiChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FirOperation.values().length];
            try {
                iArr[FirOperation.AS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirOperation.SAFE_AS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FirOperation.IS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FirOperation.NOT_IS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private FirJsNativeRttiChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirStatement expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (expression instanceof FirGetClassCall) {
            checkGetClassCall((FirGetClassCall) expression, context, reporter);
        } else if (expression instanceof FirTypeOperatorCall) {
            checkTypeOperatorCall((FirTypeOperatorCall) expression, context, reporter);
        }
    }

    private final void checkGetClassCall(FirGetClassCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirRegularClassSymbol regularClassSymbol = FirHelpersKt.toRegularClassSymbol(expression.getArgument().getTypeRef(), context.getSession());
        if (regularClassSymbol != null && (CollectionsKt.firstOrNull((List) expression.getArgumentList().getArguments()) instanceof FirResolvedQualifier) && FirJsHelpersKt.isNativeInterface(regularClassSymbol, context)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJsErrors.INSTANCE.getEXTERNAL_INTERFACE_AS_CLASS_LITERAL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final void checkTypeOperatorCall(FirTypeOperatorCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirTypeRef conversionTypeRef = expression.getConversionTypeRef();
        FirRegularClassSymbol regularClassSymbol = FirHelpersKt.toRegularClassSymbol(conversionTypeRef, context.getSession());
        if (regularClassSymbol != null && FirJsHelpersKt.isNativeInterface(regularClassSymbol, context)) {
            int i = WhenMappings.$EnumSwitchMapping$0[expression.getOperation().ordinal()];
            if (i == 1 || i == 2) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJsErrors.INSTANCE.getUNCHECKED_CAST_TO_EXTERNAL_INTERFACE(), FirTypeUtilsKt.getConeType(((FirExpression) CollectionsKt.first((List) expression.getArgumentList().getArguments())).getTypeRef()), FirTypeUtilsKt.getConeType(conversionTypeRef), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
            } else if (i == 3 || i == 4) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirJsErrors.INSTANCE.getCANNOT_CHECK_FOR_EXTERNAL_INTERFACE(), FirTypeUtilsKt.getConeType(conversionTypeRef), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }
}
