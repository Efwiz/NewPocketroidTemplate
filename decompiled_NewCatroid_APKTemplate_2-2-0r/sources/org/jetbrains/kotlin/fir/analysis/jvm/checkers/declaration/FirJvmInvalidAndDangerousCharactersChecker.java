package org.jetbrains.kotlin.fir.analysis.jvm.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.FirJvmNamesChecker;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;

/* JADX INFO: compiled from: FirJvmInvalidAndDangerousCharactersChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/declaration/FirJvmInvalidAndDangerousCharactersChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirBasicDeclarationChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmInvalidAndDangerousCharactersChecker extends FirDeclarationChecker<FirDeclaration> {
    public static final FirJvmInvalidAndDangerousCharactersChecker INSTANCE = new FirJvmInvalidAndDangerousCharactersChecker();

    private FirJvmInvalidAndDangerousCharactersChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirDeclaration declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source = declaration.getSource();
        if (declaration instanceof FirRegularClass) {
            FirJvmNamesChecker.INSTANCE.checkNameAndReport(((FirRegularClass) declaration).getName(), source, context, reporter);
            return;
        }
        if (declaration instanceof FirSimpleFunction) {
            FirJvmNamesChecker.INSTANCE.checkNameAndReport(((FirSimpleFunction) declaration).getName(), source, context, reporter);
            return;
        }
        if (declaration instanceof FirTypeParameter) {
            FirJvmNamesChecker.INSTANCE.checkNameAndReport(((FirTypeParameter) declaration).getName(), source, context, reporter);
            return;
        }
        if (declaration instanceof FirProperty) {
            FirJvmNamesChecker.INSTANCE.checkNameAndReport(((FirProperty) declaration).getName(), source, context, reporter);
        } else if (declaration instanceof FirTypeAlias) {
            FirJvmNamesChecker.INSTANCE.checkNameAndReport(((FirTypeAlias) declaration).getName(), source, context, reporter);
        } else if (declaration instanceof FirValueParameter) {
            FirJvmNamesChecker.INSTANCE.checkNameAndReport(((FirValueParameter) declaration).getName(), source, context, reporter);
        }
    }
}
