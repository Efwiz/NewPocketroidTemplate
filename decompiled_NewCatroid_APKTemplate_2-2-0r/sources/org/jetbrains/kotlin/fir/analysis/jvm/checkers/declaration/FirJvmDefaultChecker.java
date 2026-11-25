package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.java.JvmDefaultModeContainerKt;
import org.jetbrains.kotlin.name.JvmNames;

/* JADX INFO: compiled from: FirJvmDefaultChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirJvmDefaultChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmDefaultChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJvmDefaultChecker INSTANCE = new FirJvmDefaultChecker();

    private FirJvmDefaultChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        JvmDefaultMode jvmDefaultModeState = JvmDefaultModeContainerKt.getJvmDefaultModeState(context.getSession());
        FirSession session = context.getSession();
        FirDeclaration firDeclaration = declaration;
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(firDeclaration, JvmNames.INSTANCE.getJVM_DEFAULT_NO_COMPATIBILITY_CLASS_ID(), session);
        if (annotationByClassId != null) {
            KtSourceElement source = annotationByClassId.getSource();
            if (!jvmDefaultModeState.isEnabled()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirJvmErrors.INSTANCE.getJVM_DEFAULT_IN_DECLARATION(), "JvmDefaultWithoutCompatibility", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                return;
            }
        }
        FirAnnotation annotationByClassId2 = FirAnnotationUtilsKt.getAnnotationByClassId(firDeclaration, JvmNames.INSTANCE.getJVM_DEFAULT_WITH_COMPATIBILITY_CLASS_ID(), session);
        if (annotationByClassId2 != null) {
            KtSourceElement source2 = annotationByClassId2.getSource();
            if (jvmDefaultModeState != JvmDefaultMode.ALL_INCOMPATIBLE) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirJvmErrors.INSTANCE.getJVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            } else {
                if ((declaration instanceof FirRegularClass) && ((FirClass) declaration).getClassKind() == ClassKind.INTERFACE) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirJvmErrors.INSTANCE.getJVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }
}
