package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.jvm.FirJvmErrors;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbolKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirJvmFunctionDelegateMemberNameClashChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirJvmFunctionDelegateMemberNameClashChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "functionDelegateName", "Lorg/jetbrains/kotlin/name/Name;", "getFunctionDelegateName", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmFunctionDelegateMemberNameClashChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJvmFunctionDelegateMemberNameClashChecker INSTANCE = new FirJvmFunctionDelegateMemberNameClashChecker();
    private static final Name functionDelegateName;
    private static final Name getFunctionDelegateName;

    private FirJvmFunctionDelegateMemberNameClashChecker() {
    }

    static {
        Name nameIdentifier = Name.identifier("functionDelegate");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        functionDelegateName = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("getFunctionDelegate");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        getFunctionDelegateName = nameIdentifier2;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        List<FirValueParameter> valueParameters;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration instanceof FirCallableDeclaration) {
            FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(declaration, context.getSession());
            FirRegularClassSymbol firRegularClassSymbol = containingClassSymbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) containingClassSymbol : null;
            if (firRegularClassSymbol == null || !firRegularClassSymbol.getRawStatus().isFun() || FirCallableSymbolKt.isExtension(((FirCallableDeclaration) declaration).getSymbol())) {
                return;
            }
            FirFunction firFunction = declaration instanceof FirFunction ? (FirFunction) declaration : null;
            if (firFunction == null || (valueParameters = firFunction.getValueParameters()) == null || !(!valueParameters.isEmpty())) {
                if (((declaration instanceof FirSimpleFunction) && Intrinsics.areEqual(((FirSimpleFunction) declaration).getName(), getFunctionDelegateName)) || ((declaration instanceof FirProperty) && Intrinsics.areEqual(((FirProperty) declaration).getName(), functionDelegateName))) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirJvmErrors.INSTANCE.getFUNCTION_DELEGATE_MEMBER_NAME_CLASH(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }
}
