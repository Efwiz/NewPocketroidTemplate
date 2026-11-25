package org.jetbrains.kotlin.fir.analysis.checkers.syntax;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;

/* JADX INFO: compiled from: FirSyntaxChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0006B\u0005¢\u0006\u0002\u0010\u0007J#\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirDeclarationSyntaxChecker;", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "P", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirSyntaxChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;)V", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDeclarationSyntaxChecker<D extends FirDeclaration, P extends PsiElement> extends FirDeclarationChecker<D> implements FirSyntaxChecker<D, P> {
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public final void check(D declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        checkSyntax(declaration, context, reporter);
    }
}
