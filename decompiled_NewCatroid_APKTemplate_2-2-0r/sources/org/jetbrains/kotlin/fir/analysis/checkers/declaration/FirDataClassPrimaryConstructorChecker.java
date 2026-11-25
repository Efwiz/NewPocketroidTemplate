package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.LightTreePositioningStrategiesKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;

/* JADX INFO: compiled from: FirDataClassPrimaryConstructorChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDataClassPrimaryConstructorChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDataClassPrimaryConstructorChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirDataClassPrimaryConstructorChecker INSTANCE = new FirDataClassPrimaryConstructorChecker();

    private FirDataClassPrimaryConstructorChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getClassKind() == ClassKind.CLASS && declaration.getStatus().isData()) {
            FirConstructorSymbol firConstructorSymbolPrimaryConstructorIfAny = org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt.primaryConstructorIfAny(declaration, context.getSession());
            if (firConstructorSymbolPrimaryConstructorIfAny == null || (source = firConstructorSymbolPrimaryConstructorIfAny.getSource()) == null || (source.getKind() instanceof KtFakeSourceElementKind)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getPRIMARY_CONSTRUCTOR_REQUIRED_FOR_DATA_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            List<FirValueParameterSymbol> valueParameterSymbols = firConstructorSymbolPrimaryConstructorIfAny.getValueParameterSymbols();
            if (valueParameterSymbols.isEmpty()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firConstructorSymbolPrimaryConstructorIfAny.getSource(), FirErrors.INSTANCE.getDATA_CLASS_WITHOUT_PARAMETERS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            for (FirValueParameterSymbol firValueParameterSymbol : valueParameterSymbols) {
                if (firValueParameterSymbol.isVararg()) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameterSymbol.getSource(), FirErrors.INSTANCE.getDATA_CLASS_VARARG_PARAMETER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
                KtSourceElement source2 = firValueParameterSymbol.getSource();
                if (source2 == null || !LightTreePositioningStrategiesKt.hasValOrVar(source2)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameterSymbol.getSource(), FirErrors.INSTANCE.getDATA_CLASS_NOT_PROPERTY_PARAMETER(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }
}
