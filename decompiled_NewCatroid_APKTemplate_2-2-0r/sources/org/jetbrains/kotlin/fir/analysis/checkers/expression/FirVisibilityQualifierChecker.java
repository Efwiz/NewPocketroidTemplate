package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.ConeDiagnosticToFirDiagnosticKt;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirErrorResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeVisibilityError;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirVisibilityQualifierChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J,\u0010\f\u001a\u00020\u00062\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirVisibilityQualifierChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirResolvedQualifierChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkClassLikeSymbol", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirVisibilityQualifierChecker extends FirExpressionChecker<FirResolvedQualifier> {
    public static final FirVisibilityQualifierChecker INSTANCE = new FirVisibilityQualifierChecker();

    private FirVisibilityQualifierChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirResolvedQualifier expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirClassLikeSymbol<?> symbol = expression.getSymbol();
        if (symbol == null) {
            return;
        }
        checkClassLikeSymbol(symbol, expression, context, reporter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void checkClassLikeSymbol(FirClassLikeSymbol<?> symbol, FirResolvedQualifier expression, CheckerContext context, DiagnosticReporter reporter) {
        FirClassLikeSymbol<?> symbol2;
        ConeClassLikeType expandedConeType;
        FirClassLikeSymbol<?> symbol3;
        FirFile containingFile = context.getContainingFile();
        if (containingFile == null) {
            return;
        }
        FirClassLikeDeclaration firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir();
        KtSourceElement source = expression.getSource();
        if (!Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.ImplicitReceiver.INSTANCE) && !FirVisibilityCheckerKt.getVisibilityChecker(context.getSession()).isClassLikeVisible(firClassLikeDeclaration, context.getSession(), containingFile, context.getContainingDeclarations())) {
            if ((expression instanceof FirErrorResolvedQualifier) && (((FirErrorResolvedQualifier) expression).getDiagnostic() instanceof ConeVisibilityError)) {
                return;
            }
            reporter.report(ConeDiagnosticToFirDiagnosticKt.toInvisibleReferenceDiagnostic(symbol, expression.getSource()), context);
            return;
        }
        if ((firClassLikeDeclaration instanceof FirTypeAlias) && (expandedConeType = FirDeclarationUtilKt.getExpandedConeType((FirTypeAlias) firClassLikeDeclaration)) != null && (symbol3 = TypeUtilsKt.toSymbol(expandedConeType, context.getSession())) != null) {
            INSTANCE.checkClassLikeSymbol(symbol3, expression, context, reporter);
        }
        ConeClassLikeLookupTag ownerLookupTag = FirVisibilityCheckerKt.getOwnerLookupTag(symbol);
        if (ownerLookupTag == null || (symbol2 = LookupTagUtilsKt.toSymbol(ownerLookupTag, context.getSession())) == null) {
            return;
        }
        INSTANCE.checkClassLikeSymbol(symbol2, expression, context, reporter);
    }
}
