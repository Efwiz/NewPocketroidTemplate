package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.ExpectActualAttributesKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ExpectActualUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: FirActualCallableDeclarationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J \u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirActualCallableDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirCallableDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkReturnTypes", "callableDeclaration", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirActualCallableDeclarationChecker extends FirDeclarationChecker<FirCallableDeclaration> {
    public static final FirActualCallableDeclarationChecker INSTANCE = new FirActualCallableDeclarationChecker();

    private FirActualCallableDeclarationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirCallableDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getStatus().isActual()) {
            checkReturnTypes(declaration, context, reporter);
        }
    }

    private final void checkReturnTypes(FirCallableDeclaration callableDeclaration, CheckerContext context, DiagnosticReporter reporter) {
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = callableDeclaration.getSymbol();
        FirCallableSymbol<? extends FirCallableDeclaration> firCallableSymbol = symbol;
        FirBasedSymbol<?> singleExpectForActualOrNull = ExpectActualAttributesKt.getSingleExpectForActualOrNull(firCallableSymbol);
        FirCallableSymbol firCallableSymbol2 = singleExpectForActualOrNull instanceof FirCallableSymbol ? (FirCallableSymbol) singleExpectForActualOrNull : null;
        if (firCallableSymbol2 == null) {
            return;
        }
        FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firCallableSymbol2, firCallableSymbol2.getModuleData().getSession());
        List<FirTypeParameterSymbol> typeParameterSymbols = containingClassSymbol != null ? containingClassSymbol.getTypeParameterSymbols() : null;
        if (typeParameterSymbols == null) {
            typeParameterSymbols = CollectionsKt.emptyList();
        }
        List<FirTypeParameterSymbol> list = typeParameterSymbols;
        FirClassLikeSymbol<?> containingClassSymbol2 = FirHelpersKt.getContainingClassSymbol(firCallableSymbol, context.getSession());
        List<FirTypeParameterSymbol> typeParameterSymbols2 = containingClassSymbol2 != null ? containingClassSymbol2.getTypeParameterSymbols() : null;
        if (ExpectActualUtilsKt.areCompatibleExpectActualTypes(ExpectActualUtilsKt.createExpectActualTypeParameterSubstitutor(firCallableSymbol2.getTypeParameterSymbols(), symbol.getTypeParameterSymbols(), context.getSession(), ExpectActualUtilsKt.createExpectActualTypeParameterSubstitutor$default(list, typeParameterSymbols2 == null ? CollectionsKt.emptyList() : typeParameterSymbols2, context.getSession(), null, 8, null)).substituteOrSelf(firCallableSymbol2.getResolvedReturnType().getType()), symbol.getResolvedReturnType().getType(), context.getSession())) {
            return;
        }
        KtSourceElement source = callableDeclaration.getSource();
        KtDiagnosticFactory2<FirBasedSymbol<?>, Map<ExpectActualCompatibility<FirBasedSymbol<?>>, Collection<FirBasedSymbol<?>>>> actual_without_expect = FirErrors.INSTANCE.getACTUAL_WITHOUT_EXPECT();
        Map<ExpectActualCompatibility<FirBasedSymbol<?>>, List<FirBasedSymbol<?>>> expectForActual = ExpectActualAttributesKt.getExpectForActual(firCallableSymbol);
        Intrinsics.checkNotNull(expectForActual, "null cannot be cast to non-null type kotlin.collections.Map<org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility<org.jetbrains.kotlin.fir.symbols.FirBasedSymbol<*>>, kotlin.collections.Collection<org.jetbrains.kotlin.fir.symbols.FirBasedSymbol<*>>>");
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, actual_without_expect, symbol, expectForActual, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }
}
