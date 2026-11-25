package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.FirUpperBoundViolatedHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: FirUpperBoundViolatedDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirUpperBoundViolatedDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirUpperBoundViolatedDeclarationChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirUpperBoundViolatedDeclarationChecker INSTANCE = new FirUpperBoundViolatedDeclarationChecker();

    private FirUpperBoundViolatedDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirClass) {
            FirClass firClass = (FirClass) declaration;
            for (FirTypeParameterRef firTypeParameterRef : firClass.getTypeParameters()) {
                if (firTypeParameterRef instanceof FirTypeParameter) {
                    Iterator<FirTypeRef> it = ((FirTypeParameter) firTypeParameterRef).getBounds().iterator();
                    while (it.hasNext()) {
                        FirUpperBoundViolatedHelpersKt.checkUpperBoundViolated$default(it.next(), context, reporter, false, 8, null);
                    }
                }
            }
            Iterator<FirTypeRef> it2 = firClass.getSuperTypeRefs().iterator();
            while (it2.hasNext()) {
                FirUpperBoundViolatedHelpersKt.checkUpperBoundViolated$default(it2.next(), context, reporter, false, 8, null);
            }
            return;
        }
        if (declaration instanceof FirTypeAlias) {
            FirUpperBoundViolatedHelpersKt.checkUpperBoundViolated(((FirTypeAlias) declaration).getExpandedTypeRef(), context, reporter, true);
            return;
        }
        if (declaration instanceof FirCallableDeclaration) {
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) declaration;
            KtSourceElement source = firCallableDeclaration.getReturnTypeRef().getSource();
            if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) {
                return;
            }
            FirUpperBoundViolatedHelpersKt.checkUpperBoundViolated(firCallableDeclaration.getReturnTypeRef(), context, reporter, CollectionsKt.lastOrNull((List) context.getContainingDeclarations()) instanceof FirTypeAlias);
        }
    }
}
