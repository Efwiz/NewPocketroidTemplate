package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirImplicitNothingReturnTypeChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirImplicitNothingReturnTypeChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCallableDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirImplicitNothingReturnTypeChecker extends FirDeclarationChecker<FirCallableDeclaration> {
    public static final FirImplicitNothingReturnTypeChecker INSTANCE = new FirImplicitNothingReturnTypeChecker();

    private FirImplicitNothingReturnTypeChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirCallableDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtDiagnosticFactory0 implicit_nothing_property_type;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        boolean z = declaration instanceof FirSimpleFunction;
        if (z || (declaration instanceof FirProperty)) {
            boolean z2 = declaration instanceof FirProperty;
            if ((z2 && ((FirProperty) declaration).getIsLocal()) || declaration.getStatus().isOverride() || DeclarationUtilsKt.getHasExplicitReturnType(declaration.getSymbol()) || Intrinsics.areEqual(declaration.getOrigin(), FirDeclarationOrigin.ScriptCustomization.ResultProperty.INSTANCE) || !ConeBuiltinTypeUtilsKt.isNothing(FirTypeUtilsKt.getConeType(declaration.getReturnTypeRef()))) {
                return;
            }
            if (z) {
                implicit_nothing_property_type = FirErrors.INSTANCE.getIMPLICIT_NOTHING_RETURN_TYPE();
            } else if (z2) {
                implicit_nothing_property_type = FirErrors.INSTANCE.getIMPLICIT_NOTHING_PROPERTY_TYPE();
            } else {
                throw new IllegalStateException("Should not be here".toString());
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), implicit_nothing_property_type, context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
