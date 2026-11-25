package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.scopes.FirPlatformClassMapperKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: PlatformClassMappedToKotlinTypeRefChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/PlatformClassMappedToKotlinTypeRefChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "()V", "check", "", "typeRef", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlatformClassMappedToKotlinTypeRefChecker extends FirTypeChecker<FirTypeRef> {
    public static final PlatformClassMappedToKotlinTypeRefChecker INSTANCE = new PlatformClassMappedToKotlinTypeRefChecker();

    private PlatformClassMappedToKotlinTypeRefChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker
    public void check(FirTypeRef typeRef, CheckerContext context, DiagnosticReporter reporter) {
        ClassId correspondingKotlinClass;
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!(typeRef instanceof FirResolvedTypeRef) || typeRef.getSource() == null || (correspondingKotlinClass = FirPlatformClassMapperKt.getPlatformClassMapper(context.getSession()).getCorrespondingKotlinClass(ConeTypeUtilsKt.getClassId(((FirResolvedTypeRef) typeRef).getType()))) == null) {
            return;
        }
        KtSourceElement source = typeRef.getSource();
        KtDiagnosticFactory1<FqName> platform_class_mapped_to_kotlin = FirErrors.INSTANCE.getPLATFORM_CLASS_MAPPED_TO_KOTLIN();
        FqName fqNameAsSingleFqName = correspondingKotlinClass.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, platform_class_mapped_to_kotlin, fqNameAsSingleFqName, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
    }
}
