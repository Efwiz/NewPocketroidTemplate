package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;

/* JADX INFO: compiled from: FirExpectConsistencyChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirExpectConsistencyChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExpectConsistencyChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirExpectConsistencyChecker INSTANCE = new FirExpectConsistencyChecker();

    private FirExpectConsistencyChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = declaration.getSource();
        if (source == null || (source.getKind() instanceof KtFakeSourceElementKind)) {
            return;
        }
        boolean z = context.getContainingDeclarations().size() == 1;
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
        FirClass firClass = objLastOrNull instanceof FirClass ? (FirClass) objLastOrNull : null;
        boolean z2 = firClass != null;
        if (!(declaration instanceof FirAnonymousInitializer)) {
            if (declaration instanceof FirMemberDeclaration) {
                if (z || z2) {
                    FirMemberDeclaration firMemberDeclaration = (FirMemberDeclaration) declaration;
                    if (firMemberDeclaration.getStatus().isExpect()) {
                        if (declaration instanceof FirConstructor) {
                            FirConstructor firConstructor = (FirConstructor) declaration;
                            if (!firConstructor.getIsPrimary()) {
                                FirDelegatedConstructorCall delegatedConstructor = firConstructor.getDelegatedConstructor();
                                KtSourceElement source2 = delegatedConstructor != null ? delegatedConstructor.getSource() : null;
                                if (!((source2 != null ? source2.getKind() : null) instanceof KtFakeSourceElementKind)) {
                                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, source2, FirErrors.INSTANCE.getEXPECTED_CLASS_CONSTRUCTOR_DELEGATION_CALL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                }
                            }
                        } else if (Visibilities.INSTANCE.isPrivate(firMemberDeclaration.getStatus().getVisibility())) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getEXPECTED_PRIVATE_DECLARATION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                        if (!(declaration instanceof FirFunction) || ((FirFunction) declaration).getBody() == null) {
                            return;
                        }
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getEXPECTED_DECLARATION_WITH_BODY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        if (firClass == null || !firClass.getStatus().isExpect()) {
            return;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getEXPECTED_DECLARATION_WITH_BODY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
    }
}
