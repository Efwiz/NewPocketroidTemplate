package org.jetbrains.kotlin.fir.analysis.p003native.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.p002native.FirNativeErrors;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;

/* JADX INFO: compiled from: FirNativeObjCRefinementAnnotationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/native/checkers/FirNativeObjCRefinementAnnotationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "hidesFromObjCSupportedTargets", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "[Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "refinesInSwiftSupportedTargets", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNativeObjCRefinementAnnotationChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirNativeObjCRefinementAnnotationChecker INSTANCE = new FirNativeObjCRefinementAnnotationChecker();
    private static final KotlinTarget[] hidesFromObjCSupportedTargets = {KotlinTarget.FUNCTION, KotlinTarget.PROPERTY, KotlinTarget.CLASS};
    private static final KotlinTarget[] refinesInSwiftSupportedTargets = {KotlinTarget.FUNCTION, KotlinTarget.PROPERTY};

    private FirNativeObjCRefinementAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getClassKind() != ClassKind.ANNOTATION_CLASS) {
            return;
        }
        FirSession session = context.getSession();
        ObjCExportMetaAnnotations objCExportMetaAnnotationsFindMetaAnnotations = FirNativeObjCRefinementAnnotationCheckerKt.findMetaAnnotations(declaration.getAnnotations(), session);
        FirAnnotation hidesFromObjCAnnotation = objCExportMetaAnnotationsFindMetaAnnotations.getHidesFromObjCAnnotation();
        FirAnnotation refinesInSwiftAnnotation = objCExportMetaAnnotationsFindMetaAnnotations.getRefinesInSwiftAnnotation();
        if (hidesFromObjCAnnotation == null && refinesInSwiftAnnotation == null) {
            return;
        }
        if (hidesFromObjCAnnotation != null && refinesInSwiftAnnotation != null) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, refinesInSwiftAnnotation.getSource(), FirNativeErrors.INSTANCE.getREDUNDANT_SWIFT_REFINEMENT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        Set<KotlinTarget> allowedAnnotationTargets = FirAnnotationHelpersKt.getAllowedAnnotationTargets(declaration, session);
        if (hidesFromObjCAnnotation != null && !SetsKt.minus((Set) allowedAnnotationTargets, (Object[]) hidesFromObjCSupportedTargets).isEmpty()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, hidesFromObjCAnnotation.getSource(), FirNativeErrors.INSTANCE.getINVALID_OBJC_HIDES_TARGETS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (refinesInSwiftAnnotation == null || SetsKt.minus((Set) allowedAnnotationTargets, (Object[]) refinesInSwiftSupportedTargets).isEmpty()) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, refinesInSwiftAnnotation.getSource(), FirNativeErrors.INSTANCE.getINVALID_REFINES_IN_SWIFT_TARGETS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
