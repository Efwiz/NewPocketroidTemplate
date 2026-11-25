package org.jetbrains.kotlin.fir.analysis.checkers.type;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.syntax.FirSyntaxChecker;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.psi.KtParameter;

/* JADX INFO: compiled from: FirFunctionalTypeParameterSyntaxChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u00032\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004B\u0005¢\u0006\u0002\u0010\u0007J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirFunctionalTypeParameterSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/syntax/FirSyntaxChecker;", "Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", "Lorg/jetbrains/kotlin/psi/KtParameter;", "()V", "check", "", "typeRef", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirFunctionalTypeParameterSyntaxChecker extends FirTypeChecker<FirTypeRef> implements FirSyntaxChecker<FirFunctionTypeParameter, KtParameter> {
    @Override // org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker
    public void check(FirTypeRef typeRef, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (typeRef instanceof FirFunctionTypeRef) {
            Iterator<FirFunctionTypeParameter> it = ((FirFunctionTypeRef) typeRef).getParameters().iterator();
            while (it.hasNext()) {
                checkSyntax(it.next(), context, reporter);
            }
        }
    }
}
