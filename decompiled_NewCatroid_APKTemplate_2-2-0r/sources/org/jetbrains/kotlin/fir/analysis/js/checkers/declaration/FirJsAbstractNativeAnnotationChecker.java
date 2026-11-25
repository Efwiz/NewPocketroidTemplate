package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.analysis.js.checkers.FirJsHelpersKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirJsNativeAnnotationCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\b \u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\n\u001a\u00020\u000bH\u0004R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsAbstractNativeAnnotationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSimpleFunctionChecker;", "requiredAnnotation", "Lorg/jetbrains/kotlin/name/ClassId;", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "hasRequiredAnnotation", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirJsAbstractNativeAnnotationChecker extends FirDeclarationChecker<FirSimpleFunction> {
    private final ClassId requiredAnnotation;

    public FirJsAbstractNativeAnnotationChecker(ClassId requiredAnnotation) {
        Intrinsics.checkNotNullParameter(requiredAnnotation, "requiredAnnotation");
        this.requiredAnnotation = requiredAnnotation;
    }

    protected final boolean hasRequiredAnnotation(FirFunction firFunction, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return FirAnnotationUtilsKt.hasAnnotation((FirDeclaration) firFunction, this.requiredAnnotation, context.getSession());
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirSimpleFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(declaration, this.requiredAnnotation, context.getSession());
        if (annotationByClassId == null) {
            return;
        }
        boolean z = (FirHelpersKt.isTopLevel(context) || Intrinsics.areEqual(declaration.getStatus().getVisibility(), Visibilities.Local.INSTANCE)) ? false : true;
        boolean zIsExtension = FirDeclarationUtilKt.isExtension(declaration);
        if ((!z || (!zIsExtension && FirJsHelpersKt.isNativeObject(declaration.getSymbol(), context))) && (z || zIsExtension)) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJsErrors.INSTANCE.getNATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN(), FirTypeUtilsKt.getConeType(annotationByClassId.getTypeRef()), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
