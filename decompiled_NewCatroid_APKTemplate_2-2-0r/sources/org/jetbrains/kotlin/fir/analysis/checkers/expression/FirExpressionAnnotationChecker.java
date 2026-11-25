package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirExpressionAnnotationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionAnnotationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExpressionAnnotationChecker extends FirExpressionChecker<FirStatement> {
    public static final FirExpressionAnnotationChecker INSTANCE = new FirExpressionAnnotationChecker();

    private FirExpressionAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirStatement expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if ((expression instanceof FirDeclaration) || !(expression instanceof FirExpression) || (expression instanceof FirErrorExpression)) {
            return;
        }
        if (expression instanceof FirBlock) {
            KtSourceElement source = expression.getSource();
            if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtRealSourceElementKind.INSTANCE)) {
                return;
            }
            KtSourceElement source2 = expression.getSource();
            if (Intrinsics.areEqual(source2 != null ? source2.getKind() : null, KtFakeSourceElementKind.DesugaredForLoop.INSTANCE)) {
                return;
            }
        }
        List<FirAnnotation> annotations = expression.getAnnotations();
        if (annotations.isEmpty()) {
            return;
        }
        HashMap map = new HashMap();
        for (FirAnnotation firAnnotation : annotations) {
            AnnotationUseSiteTarget useSiteTarget = firAnnotation.getUseSiteTarget();
            if (useSiteTarget == null) {
                useSiteTarget = FirAnnotationHelpersKt.getDefaultUseSiteTarget(expression, firAnnotation, context);
            }
            AnnotationUseSiteTarget annotationUseSiteTarget = useSiteTarget;
            HashMap map2 = map;
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef());
            Object obj = map2.get(coneType);
            if (obj == null) {
                obj = (List) new ArrayList();
                map2.put(coneType, obj);
            }
            List list = (List) obj;
            if (!FirAnnotationHelpersKt.getAllowedAnnotationTargets(firAnnotation, context.getSession()).contains(KotlinTarget.EXPRESSION)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getWRONG_ANNOTATION_TARGET(), "expression", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
            FirAnnotationHelpersKt.checkRepeatedAnnotation(annotationUseSiteTarget, list, firAnnotation, context, reporter);
            list.add(annotationUseSiteTarget);
        }
    }
}
