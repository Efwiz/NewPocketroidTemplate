package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirKeywordUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirModifier;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirSuspendLimitationsChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\r*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirSuspendLimitationsChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirDeclarationChecker;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/declaration/FirFunctionChecker;", "()V", "check", "", "declaration", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isKotlinTestAnnotation", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSuspendLimitationsChecker extends FirDeclarationChecker<FirFunction> {
    public static final FirSuspendLimitationsChecker INSTANCE = new FirSuspendLimitationsChecker();

    private FirSuspendLimitationsChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
    public void check(FirFunction declaration, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        if (declaration.getStatus().isSuspend()) {
            List<FirAnnotation> annotations = declaration.getAnnotations();
            if ((annotations instanceof Collection) && annotations.isEmpty()) {
                return;
            }
            Iterator<T> it = annotations.iterator();
            while (it.hasNext()) {
                if (INSTANCE.isKotlinTestAnnotation((FirAnnotation) it.next(), context.getSession())) {
                    KtModifierKeywordToken SUSPEND_KEYWORD = KtTokens.SUSPEND_KEYWORD;
                    Intrinsics.checkNotNullExpressionValue(SUSPEND_KEYWORD, "SUSPEND_KEYWORD");
                    FirModifier<?> modifier = FirKeywordUtilsKt.getModifier(declaration, SUSPEND_KEYWORD);
                    if (modifier != null) {
                        KtDiagnosticReportHelpersKt.reportOn$default(reporter, modifier.getSource(), FirErrors.INSTANCE.getUNSUPPORTED_SUSPEND_TEST(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                        return;
                    }
                    return;
                }
            }
        }
    }

    private final boolean isKotlinTestAnnotation(FirAnnotation firAnnotation, FirSession firSession) {
        ConeClassLikeLookupTag lookupTag;
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firAnnotation.getAnnotationTypeRef());
        ClassId classId = null;
        ConeClassLikeType coneClassLikeType = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
        if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
            classId = lookupTag.getClassId();
        }
        return Intrinsics.areEqual(classId, StandardClassIds.Annotations.INSTANCE.getTest()) || Intrinsics.areEqual(FirAnnotationUtilsKt.toAnnotationClassId(firAnnotation, firSession), StandardClassIds.Annotations.INSTANCE.getTest());
    }
}
