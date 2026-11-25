package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypesKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirTopLevelTypeAliasChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTopLevelTypeAliasChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeAliasChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTopLevelTypeAliasChecker extends FirDeclarationChecker<FirTypeAlias> {
    public static final FirTopLevelTypeAliasChecker INSTANCE = new FirTopLevelTypeAliasChecker();

    private FirTopLevelTypeAliasChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirTypeAlias declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (!FirHelpersKt.isTopLevel(context)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getTOPLEVEL_TYPEALIASES_ONLY(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
        FirTypeRef expandedTypeRef = declaration.getExpandedTypeRef();
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(expandedTypeRef), context.getSession());
        if (check$containsTypeParameter(context, coneKotlinTypeFullyExpandedType) || (coneKotlinTypeFullyExpandedType instanceof ConeDynamicType)) {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getExpandedTypeRef().getSource(), FirErrors.INSTANCE.getTYPEALIAS_SHOULD_EXPAND_TO_CLASS(), FirTypeUtilsKt.getConeType(expandedTypeRef), context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
        }
    }

    private static final boolean check$containsTypeParameter(CheckerContext checkerContext, ConeKotlinType coneKotlinType) {
        ConeSimpleKotlinType coneSimpleKotlinTypeUnwrapDefinitelyNotNull = ConeTypesKt.unwrapDefinitelyNotNull(ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType));
        if (coneSimpleKotlinTypeUnwrapDefinitelyNotNull instanceof ConeTypeParameterType) {
            return true;
        }
        if ((coneSimpleKotlinTypeUnwrapDefinitelyNotNull instanceof ConeClassLikeType) && (LookupTagUtilsKt.toSymbol(((ConeClassLikeType) coneSimpleKotlinTypeUnwrapDefinitelyNotNull).getLookupTag(), checkerContext.getSession()) instanceof FirTypeAliasSymbol)) {
            for (ConeTypeProjection coneTypeProjection : coneSimpleKotlinTypeUnwrapDefinitelyNotNull.getTypeArguments()) {
                ConeKotlinType type = null;
                ConeKotlinType coneKotlinType2 = coneTypeProjection instanceof ConeKotlinType ? (ConeKotlinType) coneTypeProjection : null;
                if (coneKotlinType2 == null) {
                    ConeKotlinTypeProjection coneKotlinTypeProjection = coneTypeProjection instanceof ConeKotlinTypeProjection ? (ConeKotlinTypeProjection) coneTypeProjection : null;
                    if (coneKotlinTypeProjection != null) {
                        type = coneKotlinTypeProjection.getType();
                    }
                } else {
                    type = coneKotlinType2;
                }
                if (type != null && check$containsTypeParameter(checkerContext, type)) {
                    return true;
                }
            }
        }
        return false;
    }
}
