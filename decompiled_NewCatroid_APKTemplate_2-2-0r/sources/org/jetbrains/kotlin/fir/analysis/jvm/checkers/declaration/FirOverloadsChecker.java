package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.ExpectActualAttributesKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphBuilderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.name.JvmNames;

/* JADX INFO: compiled from: FirOverloadsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirOverloadsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOverloadsChecker extends FirDeclarationChecker<FirFunction> {
    public static final FirOverloadsChecker INSTANCE = new FirOverloadsChecker();

    private FirOverloadsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirSession session = context.getSession();
        FirAnnotation annotationByClassId = FirAnnotationUtilsKt.getAnnotationByClassId(declaration, JvmNames.INSTANCE.getJVM_OVERLOADS_CLASS_ID(), session);
        if (annotationByClassId == null) {
            return;
        }
        FirFunctionSymbol<?> symbol = declaration.getSymbol();
        if (symbol.getRawStatus().isActual()) {
            symbol = null;
        }
        if (symbol == null && (symbol = ExpectActualAttributesKt.getSingleExpectForActualOrNull((FirFunctionSymbol<?>) declaration.getSymbol())) == null) {
            return;
        }
        FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(declaration, session);
        if ((containingClassSymbol != null ? FirHelpersKt.getClassKind(containingClassSymbol) : null) == ClassKind.INTERFACE) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), FirJvmErrors.INSTANCE.getOVERLOADS_INTERFACE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        FirFunction firFunction = declaration;
        if (firFunction.getStatus().getModality() == Modality.ABSTRACT) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), FirJvmErrors.INSTANCE.getOVERLOADS_ABSTRACT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        if (!(declaration instanceof FirSimpleFunction) || !Intrinsics.areEqual(((FirSimpleFunction) declaration).getStatus().getVisibility(), Visibilities.Local.INSTANCE)) {
            List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
            if (!(containingDeclarations instanceof Collection) || !containingDeclarations.isEmpty()) {
                Iterator<T> it = containingDeclarations.iterator();
                while (it.hasNext()) {
                    if (ControlFlowGraphBuilderKt.isLocalClassOrAnonymousObject((FirDeclaration) it.next())) {
                    }
                }
            }
            if (declaration instanceof FirConstructor) {
                if ((containingClassSymbol != null ? FirHelpersKt.getClassKind(containingClassSymbol) : null) == ClassKind.ANNOTATION_CLASS) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), FirJvmErrors.INSTANCE.getOVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    return;
                }
            }
            if (!firFunction.getStatus().getVisibility().getIsPublicAPI() && !Intrinsics.areEqual(firFunction.getStatus().getVisibility(), Visibilities.Internal.INSTANCE)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), FirJvmErrors.INSTANCE.getOVERLOADS_PRIVATE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            List<FirValueParameterSymbol> valueParameterSymbols = symbol.getValueParameterSymbols();
            if (!(valueParameterSymbols instanceof Collection) || !valueParameterSymbols.isEmpty()) {
                Iterator<T> it2 = valueParameterSymbols.iterator();
                while (it2.hasNext()) {
                    if (((FirValueParameterSymbol) it2.next()).getHasDefaultValue()) {
                        return;
                    }
                }
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), FirJvmErrors.INSTANCE.getOVERLOADS_WITHOUT_DEFAULT_ARGUMENTS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, annotationByClassId.getSource(), FirJvmErrors.INSTANCE.getOVERLOADS_LOCAL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
