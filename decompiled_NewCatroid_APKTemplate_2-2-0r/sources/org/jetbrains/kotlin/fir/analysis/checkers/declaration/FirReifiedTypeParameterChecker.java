package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;

/* JADX INFO: compiled from: FirReifiedTypeParameterChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\f\u0010\f\u001a\u00020\r*\u00020\u000eH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirReifiedTypeParameterChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeParameterChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "areAccessorsInline", "", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirReifiedTypeParameterChecker extends FirDeclarationChecker<FirTypeParameter> {
    public static final FirReifiedTypeParameterChecker INSTANCE = new FirReifiedTypeParameterChecker();

    private FirReifiedTypeParameterChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirTypeParameter declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirDeclaration firDeclaration;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getIsReified() && (firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) context.getContainingDeclarations())) != null) {
            if ((firDeclaration instanceof FirRegularClass) || (((firDeclaration instanceof FirSimpleFunction) && !((FirMemberDeclaration) firDeclaration).getStatus().isInline()) || ((firDeclaration instanceof FirProperty) && !areAccessorsInline((FirProperty) firDeclaration)))) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getREIFIED_TYPE_PARAMETER_NO_INLINE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final boolean areAccessorsInline(FirProperty firProperty) {
        FirPropertyAccessor setter;
        FirPropertyAccessor getter = firProperty.getGetter();
        if (getter == null || !getter.getStatus().isInline()) {
            return false;
        }
        return !firProperty.getIsVar() || ((setter = firProperty.getSetter()) != null && setter.getStatus().isInline());
    }
}
