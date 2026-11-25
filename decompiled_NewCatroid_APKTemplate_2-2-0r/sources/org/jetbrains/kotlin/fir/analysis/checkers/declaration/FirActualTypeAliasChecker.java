package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: FirActualTypeAliasChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirActualTypeAliasChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirTypeAliasChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirActualTypeAliasChecker extends FirDeclarationChecker<FirTypeAlias> {
    public static final FirActualTypeAliasChecker INSTANCE = new FirActualTypeAliasChecker();

    private FirActualTypeAliasChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirTypeAlias declaration, CheckerContext context, DiagnosticReporter reporter) {
        FirTypeRef expandedTypeRef;
        FirClassLikeSymbol<?> classLikeSymbol;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getStatus().isActual() && (classLikeSymbol = FirHelpersKt.toClassLikeSymbol((expandedTypeRef = declaration.getExpandedTypeRef()), context.getSession())) != null) {
            if (classLikeSymbol instanceof FirTypeAliasSymbol) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getACTUAL_TYPE_ALIAS_NOT_TO_CLASS(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
            Iterator<FirTypeParameterSymbol> it = classLikeSymbol.getTypeParameterSymbols().iterator();
            while (it.hasNext()) {
                if (it.next().getVariance() != Variance.INVARIANT) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getACTUAL_TYPE_ALIAS_TO_CLASS_WITH_DECLARATION_SITE_VARIANCE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    break;
                }
            }
            for (ConeTypeProjection coneTypeProjection : FirTypeUtilsKt.getConeType(expandedTypeRef).getTypeArguments()) {
                if (coneTypeProjection.getKind() != ProjectionKind.INVARIANT) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getACTUAL_TYPE_ALIAS_WITH_USE_SITE_VARIANCE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                    break;
                }
            }
            if (declaration.getTypeParameters().size() == FirTypeUtilsKt.getConeType(expandedTypeRef).getTypeArguments().length) {
                int size = declaration.getTypeParameters().size();
                for (int i = 0; i < size; i++) {
                    ConeTypeProjection coneTypeProjection2 = FirTypeUtilsKt.getConeType(expandedTypeRef).getTypeArguments()[i];
                    if (coneTypeProjection2 instanceof ConeTypeParameterType) {
                        if (!Intrinsics.areEqual(declaration.getTypeParameters().get(i).getSymbol(), ((ConeTypeParameterType) coneTypeProjection2).getLookupTag().getTypeParameterSymbol())) {
                            KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getACTUAL_TYPE_ALIAS_WITH_COMPLEX_SUBSTITUTION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                            break;
                        }
                    } else {
                        if (coneTypeProjection2 instanceof ConeKotlinType) {
                            if (!(((ConeKotlinType) coneTypeProjection2).getTypeArguments().length == 0)) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getACTUAL_TYPE_ALIAS_WITH_COMPLEX_SUBSTITUTION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            } else {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getACTUAL_TYPE_ALIAS_WITH_COMPLEX_SUBSTITUTION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                break;
            }
            if (context.get$languageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions)) {
                if (FirTypeUtilsKt.isNothing(expandedTypeRef)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getACTUAL_TYPE_ALIAS_TO_NOTHING(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
                if (Intrinsics.areEqual((Object) FirTypeUtilsKt.isMarkedNullable(expandedTypeRef), (Object) true)) {
                    KtDiagnosticReportHelpersKt.reportOn$default(reporter, declaration.getSource(), FirErrors.INSTANCE.getACTUAL_TYPE_ALIAS_TO_NULLABLE_TYPE(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                }
            }
        }
    }
}
