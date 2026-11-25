package org.jetbrains.kotlin.fir.analysis.js.checkers.expression;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.JsStandardClassIds;

/* JADX INFO: compiled from: FirJsExternalArgumentCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/expression/FirJsExternalArgumentCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsExternalArgumentCallChecker extends FirExpressionChecker<FirCall> {
    public static final FirJsExternalArgumentCallChecker INSTANCE = new FirJsExternalArgumentCallChecker();

    private FirJsExternalArgumentCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirRegularClassSymbol regularClassSymbol;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirArgumentList argumentList = expression.getArgumentList();
        LinkedHashMap<FirExpression, FirValueParameter> mapping = argumentList instanceof FirResolvedArgumentList ? ((FirResolvedArgumentList) argumentList).getMapping() : null;
        if (mapping == null) {
            return;
        }
        for (Map.Entry<FirExpression, FirValueParameter> entry : mapping.entrySet()) {
            FirExpression key = entry.getKey();
            if (FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) entry.getValue(), JsStandardClassIds.Annotations.JsExternalArgument, context.getSession())) {
                FirExpression firExpressionUnwrapArgument = FirExpressionUtilKt.unwrapArgument(key);
                ConeKotlinType coneTypeOrNull = FirTypeUtilsKt.getConeTypeOrNull(firExpressionUnwrapArgument.getTypeRef());
                if (coneTypeOrNull != null && (((regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(coneTypeOrNull, context.getSession())) != null && !FirJsHelpersKt.isEffectivelyExternal(regularClassSymbol, context.getSession())) || (coneTypeOrNull instanceof ConeDynamicType))) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firExpressionUnwrapArgument.getSource(), FirJsErrors.INSTANCE.getJS_EXTERNAL_ARGUMENT(), coneTypeOrNull, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        }
    }
}
