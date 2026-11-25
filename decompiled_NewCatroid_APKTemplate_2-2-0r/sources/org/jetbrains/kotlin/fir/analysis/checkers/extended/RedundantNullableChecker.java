package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.SourceNavigator;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: RedundantNullableChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/RedundantNullableChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "()V", "check", "", "typeRef", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantNullableChecker extends FirTypeChecker<FirTypeRef> {
    public static final RedundantNullableChecker INSTANCE = new RedundantNullableChecker();

    private RedundantNullableChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker
    public void check(FirTypeRef typeRef, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if ((typeRef instanceof FirResolvedTypeRef) && Intrinsics.areEqual((Object) FirTypeUtilsKt.isMarkedNullable(typeRef), (Object) true)) {
            FirClassLikeSymbol<?> classLikeSymbol = FirHelpersKt.toClassLikeSymbol(typeRef, context.getSession());
            if (classLikeSymbol instanceof FirTypeAliasSymbol) {
                while (classLikeSymbol instanceof FirTypeAliasSymbol) {
                    FirResolvedTypeRef resolvedExpandedTypeRef = ((FirTypeAliasSymbol) classLikeSymbol).getResolvedExpandedTypeRef();
                    if (ConeTypeUtilsKt.isMarkedNullable(resolvedExpandedTypeRef.getType())) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, typeRef.getSource(), FirErrors.INSTANCE.getREDUNDANT_NULLABLE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        return;
                    }
                    classLikeSymbol = FirHelpersKt.toClassLikeSymbol(resolvedExpandedTypeRef, context.getSession());
                }
                return;
            }
            if (SourceNavigator.INSTANCE.forElement(typeRef).isRedundantNullable(typeRef)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, typeRef.getSource(), FirErrors.INSTANCE.getREDUNDANT_NULLABLE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }
}
