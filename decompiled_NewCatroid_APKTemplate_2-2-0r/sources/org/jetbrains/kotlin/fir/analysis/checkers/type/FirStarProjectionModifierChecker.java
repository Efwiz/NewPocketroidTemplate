package org.jetbrains.kotlin.fir.analysis.checkers.type;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtRealSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifier;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifierList;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirStarProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;

/* JADX INFO: compiled from: FirStarProjectionModifierChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirStarProjectionModifierChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "()V", "check", "", "typeRef", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirStarProjectionModifierChecker extends FirTypeChecker<FirTypeRef> {
    public static final FirStarProjectionModifierChecker INSTANCE = new FirStarProjectionModifierChecker();

    private FirStarProjectionModifierChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker
    public void check(FirTypeRef typeRef, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        FirModifierList modifierList;
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (typeRef instanceof FirResolvedTypeRef) {
            FirTypeRef delegatedTypeRef = ((FirResolvedTypeRef) typeRef).getDelegatedTypeRef();
            FirUserTypeRef firUserTypeRef = delegatedTypeRef instanceof FirUserTypeRef ? (FirUserTypeRef) delegatedTypeRef : null;
            if (firUserTypeRef == null) {
                return;
            }
            Iterator<FirQualifierPart> it = firUserTypeRef.getQualifier().iterator();
            while (it.hasNext()) {
                for (FirTypeProjection firTypeProjection : it.next().getTypeArgumentList().getTypeArguments()) {
                    if ((firTypeProjection instanceof FirStarProjection) && (source = firTypeProjection.getSource()) != null) {
                        if (!(source.getKind() instanceof KtRealSourceElementKind)) {
                            source = null;
                        }
                        if (source != null && (modifierList = FirKeywordUtilsKt.getModifierList(source)) != null) {
                            for (FirModifier<?> firModifier : modifierList.getModifiers()) {
                                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firModifier.getSource(), FirErrors.INSTANCE.getWRONG_MODIFIER_TARGET(), firModifier.getToken(), KotlinTarget.STAR_PROJECTION.getDescription(), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
                            }
                        }
                    }
                }
            }
        }
    }
}
