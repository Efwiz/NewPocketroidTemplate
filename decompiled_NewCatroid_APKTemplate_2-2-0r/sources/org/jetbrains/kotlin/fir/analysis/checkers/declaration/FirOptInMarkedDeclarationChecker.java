package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;

/* JADX INFO: compiled from: FirOptInMarkedDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirOptInMarkedDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOptInMarkedDeclarationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirOptInMarkedDeclarationChecker INSTANCE = new FirOptInMarkedDeclarationChecker();

    private FirOptInMarkedDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirReceiverParameter receiverParameter;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        for (FirAnnotation firAnnotation : declaration.getAnnotations()) {
            FirRegularClassSymbol annotationClassForOptInMarker = FirAnnotationHelpersKt.getAnnotationClassForOptInMarker(firAnnotation, context.getSession());
            if (annotationClassForOptInMarker != null) {
                AnnotationUseSiteTarget useSiteTarget = firAnnotation.getUseSiteTarget();
                if (((declaration instanceof FirPropertyAccessor) && ((FirPropertyAccessor) declaration).getIsGetter()) || useSiteTarget == AnnotationUseSiteTarget.PROPERTY_GETTER) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getOPT_IN_MARKER_ON_WRONG_TARGET(), "getter", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
                if (useSiteTarget == AnnotationUseSiteTarget.SETTER_PARAMETER || (useSiteTarget != AnnotationUseSiteTarget.PROPERTY && useSiteTarget != AnnotationUseSiteTarget.PROPERTY_SETTER && (declaration instanceof FirValueParameter) && FirAnnotationHelpersKt.getAllowedAnnotationTargets(annotationClassForOptInMarker, context.getSession()).contains(KotlinTarget.VALUE_PARAMETER))) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getOPT_IN_MARKER_ON_WRONG_TARGET(), "parameter", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
                if ((declaration instanceof FirProperty) && ((FirProperty) declaration).getIsLocal()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getOPT_IN_MARKER_ON_WRONG_TARGET(), "variable", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
                if (useSiteTarget == AnnotationUseSiteTarget.FIELD || useSiteTarget == AnnotationUseSiteTarget.PROPERTY_DELEGATE_FIELD) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getOPT_IN_MARKER_ON_WRONG_TARGET(), "field", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        }
        if ((declaration instanceof FirCallableDeclaration) && (receiverParameter = ((FirCallableDeclaration) declaration).getReceiverParameter()) != null) {
            for (FirAnnotation firAnnotation2 : receiverParameter.getAnnotations()) {
                if (FirAnnotationHelpersKt.getAnnotationClassForOptInMarker(firAnnotation2, context.getSession()) != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation2.getSource(), FirErrors.INSTANCE.getOPT_IN_MARKER_ON_WRONG_TARGET(), "parameter", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                }
            }
        }
    }
}
