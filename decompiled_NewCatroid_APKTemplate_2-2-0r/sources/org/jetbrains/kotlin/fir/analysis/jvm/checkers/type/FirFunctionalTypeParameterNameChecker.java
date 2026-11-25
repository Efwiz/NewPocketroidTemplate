package org.jetbrains.kotlin.fir.analysis.jvm.checkers.type;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker;
import org.jetbrains.kotlin.fir.analysis.jvm.FirJvmNamesChecker;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirFunctionalTypeParameterNameChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/type/FirFunctionalTypeParameterNameChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "()V", "check", "", "typeRef", "Lorg/jetbrains/kotlin/fir/FirFunctionTypeParameter;", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFunctionalTypeParameterNameChecker extends FirTypeChecker<FirTypeRef> {
    public static final FirFunctionalTypeParameterNameChecker INSTANCE = new FirFunctionalTypeParameterNameChecker();

    private FirFunctionalTypeParameterNameChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker
    public void check(FirTypeRef typeRef, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (typeRef instanceof FirFunctionTypeRef) {
            Iterator<FirFunctionTypeParameter> it = ((FirFunctionTypeRef) typeRef).getParameters().iterator();
            while (it.hasNext()) {
                check(it.next(), context, reporter);
            }
        }
    }

    private final void check(FirFunctionTypeParameter typeRef, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        Name name2 = typeRef.getName();
        if (name2 == null || (source = typeRef.getSource()) == null) {
            return;
        }
        FirJvmNamesChecker.INSTANCE.checkNameAndReport(name2, source, context, reporter);
    }
}
