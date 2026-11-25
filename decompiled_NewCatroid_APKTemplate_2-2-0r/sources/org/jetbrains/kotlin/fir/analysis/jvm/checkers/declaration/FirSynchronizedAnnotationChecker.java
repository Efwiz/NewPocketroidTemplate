package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.name.JvmNames;

/* JADX INFO: compiled from: FirSynchronizedAnnotationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirSynchronizedAnnotationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSynchronizedAnnotationChecker extends FirDeclarationChecker<FirFunction> {
    public static final FirSynchronizedAnnotationChecker INSTANCE = new FirSynchronizedAnnotationChecker();

    private FirSynchronizedAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirSession session = context.getSession();
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(declaration, JvmNames.SYNCHRONIZED_ANNOTATION_CLASS_ID, session);
        if (annotationByClassId == null) {
            return;
        }
        FirFunction firFunction = declaration;
        if (firFunction.getStatus().isInline()) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), FirJvmErrors.INSTANCE.getSYNCHRONIZED_ON_INLINE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if (!firFunction.getStatus().isSuspend()) {
            FirAnonymousFunction firAnonymousFunction = declaration instanceof FirAnonymousFunction ? (FirAnonymousFunction) declaration : null;
            if (firAnonymousFunction == null || (typeRef = firAnonymousFunction.getTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(typeRef)) == null || !FunctionalTypeUtilsKt.isSuspendOrKSuspendFunctionType(coneType, session)) {
                FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(declaration, session);
                if (containingClassSymbol == null) {
                    return;
                }
                if (FirHelpersKt.getClassKind(containingClassSymbol) == ClassKind.INTERFACE) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), FirJvmErrors.INSTANCE.getSYNCHRONIZED_IN_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                } else {
                    if (firFunction.getStatus().getModality() == Modality.ABSTRACT) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), FirJvmErrors.INSTANCE.getSYNCHRONIZED_ON_ABSTRACT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        return;
                    }
                    return;
                }
            }
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), FirJvmErrors.INSTANCE.getSYNCHRONIZED_ON_SUSPEND(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
