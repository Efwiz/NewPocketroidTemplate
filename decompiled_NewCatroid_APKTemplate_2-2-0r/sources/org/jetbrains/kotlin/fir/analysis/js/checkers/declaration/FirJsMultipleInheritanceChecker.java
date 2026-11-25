package org.jetbrains.kotlin.fir.analysis.js.checkers.declaration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.js.FirJsErrors;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirJsMultipleInheritanceChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J,\u0010\f\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/js/checkers/declaration/FirJsMultipleInheritanceChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkFunctionIfSubtypeOf", "functionToCheck", "Lorg/jetbrains/kotlin/name/Name;", "supertype", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsMultipleInheritanceChecker extends FirDeclarationChecker<FirClass> {
    public static final FirJsMultipleInheritanceChecker INSTANCE = new FirJsMultipleInheritanceChecker();

    private FirJsMultipleInheritanceChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkFunctionIfSubtypeOf(declaration, OperatorNameConventions.GET, context.getSession().getBuiltinTypes().getCharSequenceType().getType(), context, reporter);
        checkFunctionIfSubtypeOf(declaration, StandardNames.NEXT_CHAR, context.getSession().getBuiltinTypes().getCharIteratorType().getType(), context, reporter);
    }

    private final void checkFunctionIfSubtypeOf(FirClass firClass, Name name2, ConeKotlinType coneKotlinType, CheckerContext checkerContext, DiagnosticReporter diagnosticReporter) {
        if (TypeUtilsKt.isSubtypeOf$default(ScopeUtilsKt.defaultType(firClass), coneKotlinType, checkerContext.getSession(), false, 4, null)) {
            for (FirNamedFunctionSymbol firNamedFunctionSymbol : FirScopeKt.getFunctions(FirHelpersKt.unsubstitutedScope(firClass, checkerContext), name2)) {
                List<FirFunctionSymbol<?>> listOverriddenFunctions = FirHelpersKt.overriddenFunctions(firNamedFunctionSymbol, firClass.getSymbol(), checkerContext);
                if (listOverriddenFunctions.size() > 1) {
                    List<FirFunctionSymbol<?>> list = listOverriddenFunctions;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            if (Intrinsics.areEqual(((FirFunctionSymbol) it.next()).getCallableId().getClassId(), ConeTypeUtilsKt.getClassId(coneKotlinType))) {
                                KtDiagnosticReportHelpersKt.reportOn$default(diagnosticReporter, firClass.getSource(), FirJsErrors.INSTANCE.getWRONG_MULTIPLE_INHERITANCE(), firNamedFunctionSymbol, checkerContext, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
