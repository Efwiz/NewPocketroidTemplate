package org.jetbrains.kotlin.fir.analysis.jvm.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.ImportedFromObjectOrStaticData;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;

/* JADX INFO: compiled from: FirJavaSamInterfaceConstructorReferenceChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/expression/FirJavaSamInterfaceConstructorReferenceChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaSamInterfaceConstructorReferenceChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirJavaSamInterfaceConstructorReferenceChecker INSTANCE = new FirJavaSamInterfaceConstructorReferenceChecker();

    private FirJavaSamInterfaceConstructorReferenceChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirRegularClassSymbol regularClassSymbol;
        ImportedFromObjectOrStaticData importedFromObjectOrStaticData;
        FirCallableDeclaration original;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (expression instanceof FirCallableReferenceAccess) {
            FirNamedReference calleeReference = ((FirCallableReferenceAccess) expression).getCalleeReference();
            FirResolvedNamedReference firResolvedNamedReference = calleeReference instanceof FirResolvedNamedReference ? (FirResolvedNamedReference) calleeReference : null;
            if (firResolvedNamedReference == null) {
                return;
            }
            FirBasedSymbol<?> resolvedSymbol = firResolvedNamedReference.getResolvedSymbol();
            if ((resolvedSymbol instanceof FirNamedFunctionSymbol) && Intrinsics.areEqual(resolvedSymbol.getOrigin(), FirDeclarationOrigin.SamConstructor.INSTANCE) && (regularClassSymbol = FirHelpersKt.toRegularClassSymbol(((FirNamedFunctionSymbol) resolvedSymbol).getResolvedReturnTypeRef(), context.getSession())) != null && regularClassSymbol.getRawStatus().isFun()) {
                FirRegularClassSymbol firRegularClassSymbol = regularClassSymbol;
                FirDeclarationOrigin origin = firRegularClassSymbol.getOrigin();
                if (!(origin instanceof FirDeclarationOrigin.Java) && !Intrinsics.areEqual(origin, FirDeclarationOrigin.Enhancement.INSTANCE)) {
                    E fir = firRegularClassSymbol.getFir();
                    FirCallableDeclaration firCallableDeclaration = fir instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir : null;
                    if (firCallableDeclaration == null || (importedFromObjectOrStaticData = FirAbstractImportingScopeKt.getImportedFromObjectOrStaticData(firCallableDeclaration)) == null || (original = importedFromObjectOrStaticData.getOriginal()) == null || !DeclarationUtilsKt.isJavaOrEnhancement(original)) {
                        return;
                    }
                }
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firResolvedNamedReference.getSource(), FirJvmErrors.INSTANCE.getJAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }
}
