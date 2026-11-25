package org.jetbrains.kotlin.fir.analysis.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;

/* JADX INFO: compiled from: FirInlineCheckerPlatformSpecificComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0014\u0010\u000b\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u000e0\r0\fH\u0016J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/FirInlineCheckerPlatformSpecificComponent;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "checkFunctionalParametersWithInheritedDefaultValues", "", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "overriddenSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "checkSuspendFunctionalParameterWithDefaultValue", "param", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "isGenerallyOk", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirInlineCheckerPlatformSpecificComponent implements FirSessionComponent {
    public void checkFunctionalParametersWithInheritedDefaultValues(FirSimpleFunction function, CheckerContext context, DiagnosticReporter reporter, List<? extends FirCallableSymbol<? extends FirCallableDeclaration>> overriddenSymbols) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(overriddenSymbols, "overriddenSymbols");
    }

    public void checkSuspendFunctionalParameterWithDefaultValue(FirValueParameter param, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(param, "param");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
    }

    public boolean isGenerallyOk(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        return true;
    }
}
