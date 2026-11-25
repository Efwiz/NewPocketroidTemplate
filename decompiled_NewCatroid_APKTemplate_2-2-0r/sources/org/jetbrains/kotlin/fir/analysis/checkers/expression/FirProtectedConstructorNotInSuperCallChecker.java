package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirProtectedConstructorNotInSuperCallChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirProtectedConstructorNotInSuperCallChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirFunctionCallChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "shouldAllowSuchCallNonetheless", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirProtectedConstructorNotInSuperCallChecker extends FirExpressionChecker<FirFunctionCall> {
    public static final FirProtectedConstructorNotInSuperCallChecker INSTANCE = new FirProtectedConstructorNotInSuperCallChecker();

    private FirProtectedConstructorNotInSuperCallChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirFunctionCall expression, CheckerContext context, DiagnosticReporter reporter) {
        FirResolvedNamedReference firResolvedNamedReference;
        FirConstructorSymbol resolvedConstructorSymbol$default;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirResolvedNamedReference resolved = FirReferenceUtilsKt.getResolved(expression.getCalleeReference());
        if (resolved == null || (resolvedConstructorSymbol$default = FirReferenceUtilsKt.toResolvedConstructorSymbol$default((firResolvedNamedReference = resolved), false, 1, null)) == null) {
            return;
        }
        FirRegularClassSymbol constructedClass = DeclarationUtilsKt.getConstructedClass(resolvedConstructorSymbol$default, context.getSession());
        if (shouldAllowSuchCallNonetheless(resolvedConstructorSymbol$default, context) || !Intrinsics.areEqual(resolvedConstructorSymbol$default.getResolvedStatus().getVisibility().normalize(), Visibilities.Protected.INSTANCE) || FirReferenceUtilsKt.isError(firResolvedNamedReference)) {
            return;
        }
        List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
        if (!(containingDeclarations instanceof Collection) || !containingDeclarations.isEmpty()) {
            Iterator<T> it = containingDeclarations.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((FirDeclaration) it.next()).getSymbol(), constructedClass)) {
                    return;
                }
            }
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getCalleeReference().getSource(), FirErrors.INSTANCE.getPROTECTED_CONSTRUCTOR_NOT_IN_SUPER_CALL(), resolvedConstructorSymbol$default, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean shouldAllowSuchCallNonetheless(FirConstructorSymbol symbol, CheckerContext context) {
        FirFile containingFile = context.getContainingFile();
        if (containingFile == null) {
            return false;
        }
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) symbol.getFir();
        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
        if (originalForSubstitutionOverrideAttr == null) {
            originalForSubstitutionOverrideAttr = ClassMembersKt.isIntersectionOverride(firCallableDeclaration) ? ClassMembersKt.getOriginalForIntersectionOverrideAttr(firCallableDeclaration) : null;
        }
        FirConstructorSymbol firConstructorSymbol = (FirConstructorSymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null);
        if (firConstructorSymbol != null) {
            symbol = firConstructorSymbol;
        }
        FirDeclarationOrigin origin = symbol.getOrigin();
        return ((origin instanceof FirDeclarationOrigin.Java) || Intrinsics.areEqual(origin, FirDeclarationOrigin.Enhancement.INSTANCE)) && Intrinsics.areEqual(symbol.getCallableId().getPackageName(), UtilsKt.getPackageFqName(containingFile));
    }
}
