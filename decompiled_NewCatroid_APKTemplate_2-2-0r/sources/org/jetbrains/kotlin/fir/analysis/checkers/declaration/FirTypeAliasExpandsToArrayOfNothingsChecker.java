package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirTypeAliasExpandsToArrayOfNothingsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\r*\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0014\u0010\u0011\u001a\u00020\r*\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeAliasExpandsToArrayOfNothingsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeAliasChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "containsMalformedArgument", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "expandedClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "isMalformed", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeAliasExpandsToArrayOfNothingsChecker extends FirDeclarationChecker<FirTypeAlias> {
    public static final FirTypeAliasExpandsToArrayOfNothingsChecker INSTANCE = new FirTypeAliasExpandsToArrayOfNothingsChecker();

    private FirTypeAliasExpandsToArrayOfNothingsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirTypeAlias declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        ConeClassLikeType expandedConeType = FirDeclarationUtilKt.getExpandedConeType(declaration);
        if (expandedConeType != null && isMalformed(expandedConeType, context)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getExpandedTypeRef().getSource(), FirErrors.INSTANCE.getTYPEALIAS_EXPANDS_TO_ARRAY_OF_NOTHINGS(), expandedConeType, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0034  */
    /* JADX WARN: Code duplicated, block: B:15:0x003c A[ORIG_RETURN, RETURN] */
    private final boolean isMalformed(ConeKotlinType coneKotlinType, CheckerContext checkerContext) {
        ConeKotlinType type;
        if (Intrinsics.areEqual(expandedClassId(coneKotlinType, checkerContext), StandardClassIds.INSTANCE.getArray())) {
            ConeTypeProjection coneTypeProjection = (ConeTypeProjection) ArraysKt.singleOrNull(coneKotlinType.getTypeArguments());
            if (!Intrinsics.areEqual((coneTypeProjection == null || (type = ConeTypeProjectionKt.getType(coneTypeProjection)) == null) ? null : expandedClassId(type, checkerContext), StandardClassIds.INSTANCE.getNothing())) {
                if (containsMalformedArgument(coneKotlinType, checkerContext)) {
                    return false;
                }
            }
        } else if (containsMalformedArgument(coneKotlinType, checkerContext)) {
            return false;
        }
        return true;
    }

    private final boolean containsMalformedArgument(ConeKotlinType coneKotlinType, CheckerContext checkerContext) {
        for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
            ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
            if (type != null && INSTANCE.isMalformed(type, checkerContext)) {
                return true;
            }
        }
        return false;
    }

    private final ClassId expandedClassId(ConeKotlinType coneKotlinType, CheckerContext checkerContext) {
        return ConeTypeUtilsKt.getClassId(TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType, checkerContext.getSession()));
    }
}
