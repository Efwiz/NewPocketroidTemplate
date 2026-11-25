package org.jetbrains.kotlin.fir.analysis.jvm.checkers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirInlineCheckerPlatformSpecificComponent;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;

/* JADX INFO: compiled from: FirJvmInlineCheckerComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0014\u0010\u000b\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u000e0\r0\fH\u0016J \u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/FirJvmInlineCheckerComponent;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/FirInlineCheckerPlatformSpecificComponent;", "()V", "checkFunctionalParametersWithInheritedDefaultValues", "", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "overriddenSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "checkSuspendFunctionalParameterWithDefaultValue", "param", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "isGenerallyOk", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmInlineCheckerComponent extends FirInlineCheckerPlatformSpecificComponent {
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.FirInlineCheckerPlatformSpecificComponent
    public boolean isGenerallyOk(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!DeclarationUtilsKt.isLocalMember(declaration) || (CollectionsKt.lastOrNull((List) context.getContainingDeclarations()) instanceof FirScript)) {
            return true;
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getNOT_YET_SUPPORTED_IN_INLINE(), "Local inline functions", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.FirInlineCheckerPlatformSpecificComponent
    public void checkSuspendFunctionalParameterWithDefaultValue(FirValueParameter param, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(param, "param");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, param.getSource(), FirErrors.INSTANCE.getNOT_YET_SUPPORTED_IN_INLINE(), "Suspend functional parameters with default values", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.FirInlineCheckerPlatformSpecificComponent
    public void checkFunctionalParametersWithInheritedDefaultValues(FirSimpleFunction function, CheckerContext context, DiagnosticReporter reporter, List<? extends FirCallableSymbol<? extends FirCallableDeclaration>> overriddenSymbols) {
        int i;
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        Intrinsics.checkNotNullParameter(overriddenSymbols, "overriddenSymbols");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = overriddenSymbols.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            FirCallableSymbol firCallableSymbol = (FirCallableSymbol) it.next();
            if (firCallableSymbol instanceof FirFunctionSymbol) {
                List<FirValueParameterSymbol> valueParameterSymbols = ((FirFunctionSymbol) firCallableSymbol).getValueParameterSymbols();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : valueParameterSymbols) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    Integer numValueOf = Integer.valueOf(i);
                    numValueOf.intValue();
                    if (!((FirValueParameterSymbol) obj).getHasDefaultValue()) {
                        numValueOf = null;
                    }
                    if (numValueOf != null) {
                        arrayList2.add(numValueOf);
                    }
                    i = i2;
                }
                arrayListEmptyList = arrayList2;
            } else {
                arrayListEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList, arrayListEmptyList);
        }
        Set set = CollectionsKt.toSet(arrayList);
        for (Object obj2 : function.getValueParameters()) {
            int i3 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            FirValueParameter firValueParameter = (FirValueParameter) obj2;
            if (firValueParameter.getDefaultValue() == null && set.contains(Integer.valueOf(i))) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firValueParameter.getSource(), FirErrors.INSTANCE.getNOT_YET_SUPPORTED_IN_INLINE(), "Functional parameters with inherited default values", context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
            i = i3;
        }
    }
}
