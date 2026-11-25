package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyBackingField;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbolKt;

/* JADX INFO: compiled from: FirExplicitBackingFieldForbiddenChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirExplicitBackingFieldForbiddenChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBackingFieldChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "getProperDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExplicitBackingFieldForbiddenChecker extends FirDeclarationChecker<FirBackingField> {
    public static final FirExplicitBackingFieldForbiddenChecker INSTANCE = new FirExplicitBackingFieldForbiddenChecker();

    private FirExplicitBackingFieldForbiddenChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirBackingField declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirDefaultPropertyBackingField) {
            return;
        }
        if (declaration.getPropertySymbol().getResolvedStatus().getModality() == Modality.ABSTRACT) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), getProperDiagnostic(context), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        if (FirCallableSymbolKt.isExtension(declaration.getPropertySymbol())) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getEXPLICIT_BACKING_FIELD_IN_EXTENSION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    private final KtDiagnosticFactory0 getProperDiagnostic(CheckerContext context) {
        FirClass firClassFindClosestClassOrObject = FirHelpersKt.findClosestClassOrObject(context);
        if ((firClassFindClosestClassOrObject != null ? firClassFindClosestClassOrObject.getClassKind() : null) == ClassKind.INTERFACE) {
            return FirErrors.INSTANCE.getEXPLICIT_BACKING_FIELD_IN_INTERFACE();
        }
        return FirErrors.INSTANCE.getEXPLICIT_BACKING_FIELD_IN_ABSTRACT_PROPERTY();
    }
}
