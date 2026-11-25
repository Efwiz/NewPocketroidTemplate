package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;

/* JADX INFO: compiled from: FirThrowableSubclassChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\r*\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0002J\u0014\u0010\u000e\u001a\u00020\r*\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirThrowableSubclassChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "hasGenericOuterDeclaration", "", "hasThrowableSupertype", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirThrowableSubclassChecker extends FirDeclarationChecker<FirClass> {
    public static final FirThrowableSubclassChecker INSTANCE = new FirThrowableSubclassChecker();

    private FirThrowableSubclassChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (hasThrowableSupertype(declaration, context)) {
            if (!declaration.getTypeParameters().isEmpty()) {
                FirTypeParameterRef firTypeParameterRef = (FirTypeParameterRef) CollectionsKt.firstOrNull((List) declaration.getTypeParameters());
                if (firTypeParameterRef != null && (source = firTypeParameterRef.getSource()) != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getGENERIC_THROWABLE_SUBCLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
                if (!(declaration instanceof FirRegularClass)) {
                    if (!(declaration instanceof FirAnonymousObject)) {
                        return;
                    }
                } else if (!declaration.getStatus().isInner() && !declaration.getSymbol().getClassId().isLocal()) {
                    return;
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getINNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            if (hasGenericOuterDeclaration(declaration, context)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getINNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final boolean hasThrowableSupertype(FirClass firClass, CheckerContext checkerContext) {
        List<ConeClassLikeType> superConeTypes = FirDeclarationUtilKt.getSuperConeTypes(firClass);
        if ((superConeTypes instanceof Collection) && superConeTypes.isEmpty()) {
            return false;
        }
        for (ConeClassLikeType coneClassLikeType : superConeTypes) {
            if (!(coneClassLikeType instanceof ConeErrorType) && FirHelpersKt.isSubtypeOfThrowable(coneClassLikeType, checkerContext.getSession())) {
                return true;
            }
        }
        return false;
    }

    private final boolean hasGenericOuterDeclaration(FirClass firClass, CheckerContext checkerContext) {
        if (!FirDeclarationUtilKt.getClassId(firClass).isLocal()) {
            return false;
        }
        for (FirAnnotationContainer firAnnotationContainer : CollectionsKt.asReversed(checkerContext.getContainingDeclarations())) {
            if ((firAnnotationContainer instanceof FirTypeParameterRefsOwner) && !((FirTypeParameterRefsOwner) firAnnotationContainer).getTypeParameters().isEmpty()) {
                return true;
            }
            if ((firAnnotationContainer instanceof FirRegularClass) && !((FirClassLikeDeclaration) firAnnotationContainer).getSymbol().getClassId().isLocal() && !((FirMemberDeclaration) firAnnotationContainer).getStatus().isInner()) {
                break;
            }
        }
        return false;
    }
}
