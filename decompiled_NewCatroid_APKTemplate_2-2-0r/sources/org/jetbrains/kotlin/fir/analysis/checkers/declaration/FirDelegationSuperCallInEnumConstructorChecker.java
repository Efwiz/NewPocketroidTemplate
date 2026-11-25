package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;

/* JADX INFO: compiled from: FirDelegationSuperCallInEnumConstructorChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDelegationSuperCallInEnumConstructorChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegationSuperCallInEnumConstructorChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirDelegationSuperCallInEnumConstructorChecker INSTANCE = new FirDelegationSuperCallInEnumConstructorChecker();

    private FirDelegationSuperCallInEnumConstructorChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirDelegatedConstructorCall delegatedConstructor;
        KtSourceElement source;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getClassKind() == ClassKind.ENUM_CLASS) {
            for (FirDeclaration firDeclaration : declaration.getDeclarations()) {
                if (firDeclaration instanceof FirConstructor) {
                    FirConstructor firConstructor = (FirConstructor) firDeclaration;
                    if (!firConstructor.getIsPrimary() && (delegatedConstructor = firConstructor.getDelegatedConstructor()) != null && !delegatedConstructor.getIsThis()) {
                        FirDelegatedConstructorCall delegatedConstructor2 = firConstructor.getDelegatedConstructor();
                        if (!(((delegatedConstructor2 == null || (source = delegatedConstructor2.getSource()) == null) ? null : source.getKind()) instanceof KtFakeSourceElementKind)) {
                            FirDelegatedConstructorCall delegatedConstructor3 = firConstructor.getDelegatedConstructor();
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, delegatedConstructor3 != null ? delegatedConstructor3.getSource() : null, FirErrors.INSTANCE.getDELEGATION_SUPER_CALL_IN_ENUM_CONSTRUCTOR(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        }
                    }
                }
            }
        }
    }
}
