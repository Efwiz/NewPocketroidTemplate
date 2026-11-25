package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticContext;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.DelegateFieldsMapKt;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.symbols.SymbolInternals;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirDelegateFieldTypeMismatchChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0017¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDelegateFieldTypeMismatchChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegateFieldTypeMismatchChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirDelegateFieldTypeMismatchChecker INSTANCE = new FirDelegateFieldTypeMismatchChecker();

    private FirDelegateFieldTypeMismatchChecker() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    @SymbolInternals
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirFieldSymbol firFieldSymbol;
        FirField firField;
        FirExpression initializer;
        FirReference calleeReference;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        int size = declaration.getSuperTypeRefs().size();
        for (int i = 0; i < size; i++) {
            FirTypeRef firTypeRef = declaration.getSuperTypeRefs().get(i);
            Map<Integer, FirFieldSymbol> delegateFieldsMap = DelegateFieldsMapKt.getDelegateFieldsMap(declaration);
            if (delegateFieldsMap != null && (firFieldSymbol = delegateFieldsMap.get(Integer.valueOf(i))) != null && (firField = (FirField) firFieldSymbol.getFir()) != null && (initializer = firField.getInitializer()) != null && ((!(initializer instanceof FirCall) || (calleeReference = FirExpressionUtilKt.getCalleeReference(initializer)) == null || !FirReferenceUtilsKt.isError(calleeReference)) && !TypeUtilsKt.isSubtypeOf(FirTypeUtilsKt.getConeType(initializer.getTypeRef()), FirTypeUtilsKt.getConeType(firTypeRef), context.getSession(), true))) {
                KtDiagnosticReportHelpersKt.reportOn(reporter, (AbstractKtSourceElement) initializer.getSource(), (KtDiagnosticFactory3<ConeKotlinType, ConeKotlinType, boolean>) ((KtDiagnosticFactory3<Object, Object, Object>) FirErrors.INSTANCE.getTYPE_MISMATCH()), FirTypeUtilsKt.getConeType(firField.getReturnTypeRef()), FirTypeUtilsKt.getConeType(initializer.getTypeRef()), false, (DiagnosticContext) context, (64 & 64) != 0 ? null : null);
            }
        }
    }
}
