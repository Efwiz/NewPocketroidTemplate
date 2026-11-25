package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.CallableIdUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirJvmSerializableLambdaChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirJvmSerializableLambdaChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirAnnotationChecker;", "()V", "JVM_SERIALIZABLE_LAMBDA_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmSerializableLambdaChecker extends FirExpressionChecker<FirAnnotation> {
    public static final FirJvmSerializableLambdaChecker INSTANCE = new FirJvmSerializableLambdaChecker();
    private static final FqName JVM_SERIALIZABLE_LAMBDA_ANNOTATION_FQ_NAME = new FqName("kotlin.jvm.JvmSerializableLambda");

    private FirJvmSerializableLambdaChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirAnnotation expression, CheckerContext context, DiagnosticReporter reporter) {
        String description;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (Intrinsics.areEqual(CallableIdUtilsKt.fqName(expression, context.getSession()), JVM_SERIALIZABLE_LAMBDA_ANNOTATION_FQ_NAME)) {
            FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.last((List) context.getContainingDeclarations());
            if (firDeclaration instanceof FirAnonymousFunction) {
                return;
            }
            KotlinTarget kotlinTarget = (KotlinTarget) CollectionsKt.firstOrNull((List) FirHelpersKt.getActualTargetList(firDeclaration).getDefaultTargets());
            if (kotlinTarget == null || (description = kotlinTarget.getDescription()) == null) {
                description = "unidentified target";
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getWRONG_ANNOTATION_TARGET(), description, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }
}
