package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirEnumClassSimpleChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirEnumClassSimpleChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirRegularClassChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEnumClassSimpleChecker extends FirDeclarationChecker<FirRegularClass> {
    public static final FirEnumClassSimpleChecker INSTANCE = new FirEnumClassSimpleChecker();

    private FirEnumClassSimpleChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirRegularClass declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirRegularClass firRegularClass = declaration;
        if (firRegularClass.getClassKind() == ClassKind.ENUM_CLASS) {
            FirTypeRef firTypeRefFindNonInterfaceSupertype = FirHelpersKt.findNonInterfaceSupertype(firRegularClass, context);
            if (firTypeRefFindNonInterfaceSupertype != null) {
                if (Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(firTypeRefFindNonInterfaceSupertype), context.getSession())), StandardClassIds.INSTANCE.getEnum())) {
                    firTypeRefFindNonInterfaceSupertype = null;
                }
                if (firTypeRefFindNonInterfaceSupertype != null) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeRefFindNonInterfaceSupertype.getSource(), FirErrors.INSTANCE.getCLASS_IN_SUPERTYPE_FOR_ENUM(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
            if (declaration.getTypeParameters().isEmpty()) {
                return;
            }
            FirTypeParameterRef firTypeParameterRef = (FirTypeParameterRef) CollectionsKt.firstOrNull((List) declaration.getTypeParameters());
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, firTypeParameterRef != null ? firTypeParameterRef.getSource() : null, FirErrors.INSTANCE.getTYPE_PARAMETERS_IN_ENUM(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }
}
