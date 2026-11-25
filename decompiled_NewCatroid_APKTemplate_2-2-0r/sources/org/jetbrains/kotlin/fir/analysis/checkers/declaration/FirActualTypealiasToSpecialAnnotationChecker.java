package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.calls.mpp.ActualTypealiasToSpecialAnnotationUtils;

/* JADX INFO: compiled from: FirActualTypealiasToSpecialAnnotationChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirActualTypealiasToSpecialAnnotationChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeAliasChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirActualTypealiasToSpecialAnnotationChecker extends FirDeclarationChecker<FirTypeAlias> {
    public static final FirActualTypealiasToSpecialAnnotationChecker INSTANCE = new FirActualTypealiasToSpecialAnnotationChecker();

    private FirActualTypealiasToSpecialAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirTypeAlias declaration, CheckerContext context, DiagnosticReporter reporter) {
        ConeClassLikeType expandedConeType;
        FirClassLikeSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions) && declaration.getStatus().isActual() && (expandedConeType = FirDeclarationUtilKt.getExpandedConeType(declaration)) != null && (symbol = TypeUtilsKt.toSymbol(expandedConeType, context.getSession())) != null && FirHelpersKt.getClassKind(symbol) == ClassKind.ANNOTATION_CLASS) {
            ClassId classId = symbol.getClassId();
            if (ActualTypealiasToSpecialAnnotationUtils.INSTANCE.isAnnotationProhibitedInActualTypeAlias(classId)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getACTUAL_TYPEALIAS_TO_SPECIAL_ANNOTATION(), classId, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
            }
        }
    }
}
