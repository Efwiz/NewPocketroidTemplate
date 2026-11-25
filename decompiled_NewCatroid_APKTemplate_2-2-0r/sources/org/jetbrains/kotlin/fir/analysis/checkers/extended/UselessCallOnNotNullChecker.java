package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: UselessCallOnNotNullChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u000e\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u0002H\u0002J\f\u0010\u0011\u001a\u00020\u0012*\u00020\u0013H\u0002J\f\u0010\u0014\u001a\u00020\u0007*\u00020\u0013H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/UselessCallOnNotNullChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "triggerOn", "", "", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "getCallableId", "Lorg/jetbrains/kotlin/name/CallableId;", "getNullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getPackage", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UselessCallOnNotNullChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final UselessCallOnNotNullChecker INSTANCE = new UselessCallOnNotNullChecker();
    private static final Set<String> triggerOn = SetsKt.setOf((Object[]) new String[]{"kotlin.collections.orEmpty", "kotlin.sequences.orEmpty", "kotlin.text.orEmpty", "kotlin.text.isNullOrEmpty", "kotlin.text.isNullOrEmpty", "kotlin.text.isNullOrBlank", "kotlin.isNullOrBlank", "kotlin.isNullOrEmpty", "kotlin.orEmpty"});

    private UselessCallOnNotNullChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirExpression explicitReceiver;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        CallableId callableId = getCallableId(expression);
        if (callableId == null || (explicitReceiver = expression.getExplicitReceiver()) == null) {
            return;
        }
        String str = getPackage(explicitReceiver);
        String strAsString = callableId.getCallableName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (triggerOn.contains(str + '.' + strAsString) && getNullability(explicitReceiver) == ConeNullability.NOT_NULL) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getUSELESS_CALL_ON_NOT_NULL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final CallableId getCallableId(FirQualifiedAccessExpression firQualifiedAccessExpression) {
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(firQualifiedAccessExpression.getCalleeReference(), false, 1, null);
        if (resolvedNamedFunctionSymbol$default != null) {
            return resolvedNamedFunctionSymbol$default.getCallableId();
        }
        return null;
    }

    private final String getPackage(FirExpression firExpression) {
        ClassId classId = ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firExpression.getTypeRef()));
        return String.valueOf(classId != null ? classId.getPackageFqName() : null);
    }

    private final ConeNullability getNullability(FirExpression firExpression) {
        return FirTypeUtilsKt.getConeType(firExpression.getTypeRef()).getNullability();
    }
}
