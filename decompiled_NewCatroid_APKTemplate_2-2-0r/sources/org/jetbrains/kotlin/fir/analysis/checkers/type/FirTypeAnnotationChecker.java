package org.jetbrains.kotlin.fir.analysis.checkers.type;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirTypeAnnotationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeAnnotationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "()V", "check", "", "typeRef", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeAnnotationChecker extends FirTypeChecker<FirTypeRef> {
    public static final FirTypeAnnotationChecker INSTANCE = new FirTypeAnnotationChecker();

    private FirTypeAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker
    public void check(FirTypeRef typeRef, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (typeRef instanceof FirResolvedTypeRef) {
            for (FirAnnotation firAnnotation : typeRef.getAnnotations()) {
                if (firAnnotation.getSource() != null) {
                    AnnotationUseSiteTarget useSiteTarget = firAnnotation.getUseSiteTarget();
                    Set<KotlinTarget> allowedAnnotationTargets = FirAnnotationHelpersKt.getAllowedAnnotationTargets(firAnnotation, context.getSession());
                    if (useSiteTarget != null) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getWRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET(), "type usage", useSiteTarget.getRenderName(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                    } else if (!allowedAnnotationTargets.contains(KotlinTarget.TYPE)) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getWRONG_ANNOTATION_TARGET(), "type usage", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                    }
                    if (Intrinsics.areEqual(FirAnnotationUtilsKt.toAnnotationClassId(firAnnotation, context.getSession()), StandardClassIds.Annotations.INSTANCE.getExtensionFunctionType())) {
                        FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) typeRef;
                        if (!FunctionalTypeUtilsKt.isSomeFunctionType(firResolvedTypeRef.getType(), context.getSession())) {
                            if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.ForbidExtensionFunctionTypeOnNonFunctionTypes)) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getWRONG_EXTENSION_FUNCTION_TYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            } else {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getWRONG_EXTENSION_FUNCTION_TYPE_WARNING(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            }
                        } else if (firResolvedTypeRef.getType().getTypeArguments().length <= 1) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firAnnotation.getSource(), FirErrors.INSTANCE.getWRONG_EXTENSION_FUNCTION_TYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                    }
                }
            }
        }
    }
}
