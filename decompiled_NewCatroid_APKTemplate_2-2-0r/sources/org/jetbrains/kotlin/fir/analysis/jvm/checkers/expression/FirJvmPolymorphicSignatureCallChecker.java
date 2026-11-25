package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirSpreadArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirJvmPolymorphicSignatureCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirJvmPolymorphicSignatureCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "polymorphicSignatureClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmPolymorphicSignatureCallChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirJvmPolymorphicSignatureCallChecker INSTANCE = new FirJvmPolymorphicSignatureCallChecker();
    private static final ClassId polymorphicSignatureClassId;

    private FirJvmPolymorphicSignatureCallChecker() {
    }

    static {
        ClassId classIdFromString = ClassId.fromString("java/lang/invoke/MethodHandle.PolymorphicSignature");
        Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
        polymorphicSignatureClassId = classIdFromString;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirCallableSymbol resolvedCallableSymbol$default;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.PolymorphicSignature) || (resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(expression.getCalleeReference(), false, 1, null)) == null || FirAnnotationUtilsKt.getAnnotationByClassId(resolvedCallableSymbol$default, polymorphicSignatureClassId, context.getSession()) == null) {
            return;
        }
        for (FirExpression firExpression : expression.getArgumentList().getArguments()) {
            if (firExpression instanceof FirVarargArgumentsExpression) {
                for (FirExpression firExpression2 : ((FirVarargArgumentsExpression) firExpression).getArguments()) {
                    if (firExpression2 instanceof FirSpreadArgumentExpression) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firExpression2.getSource(), FirJvmErrors.INSTANCE.getSPREAD_ON_SIGNATURE_POLYMORPHIC_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    }
                }
            }
        }
    }
}
