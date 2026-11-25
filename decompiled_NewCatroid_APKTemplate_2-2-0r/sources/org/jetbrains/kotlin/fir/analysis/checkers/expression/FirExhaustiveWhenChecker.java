package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatus;
import org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatusKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenBranch;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirElseIfTrueCondition;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: FirExhaustiveWhenChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003:\u0001\u001dB\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J \u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J2\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u00022\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J \u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\bR\u0018\u0010\t\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\bR\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExhaustiveWhenChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirWhenExpressionChecker;", "()V", "isIfExpression", "", "Lorg/jetbrains/kotlin/KtSourceElement;", "(Lorg/jetbrains/kotlin/KtSourceElement;)Z", "isWhenExpression", "missingCases", "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "getMissingCases", "(Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;)Ljava/util/List;", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "reportElseMisplaced", "reportNoElseInWhen", FirebaseAnalytics.Param.SOURCE, "whenExpression", "subjectClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "reportNotExhaustive", "AlgebraicTypeKind", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExhaustiveWhenChecker extends FirExpressionChecker<FirWhenExpression> {
    public static final FirExhaustiveWhenChecker INSTANCE = new FirExhaustiveWhenChecker();

    private FirExhaustiveWhenChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirWhenExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        reportNotExhaustive(expression, context, reporter);
        reportElseMisplaced(expression, reporter, context);
    }

    private final void reportNotExhaustive(FirWhenExpression whenExpression, CheckerContext context, DiagnosticReporter reporter) {
        KtSourceElement source;
        AlgebraicTypeKind algebraicTypeKind;
        ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType;
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        if (ExhaustivenessStatusKt.isExhaustive(whenExpression) || (source = whenExpression.getSource()) == null) {
            return;
        }
        FirExpression subject = whenExpression.getSubject();
        FirRegularClassSymbol regularClassSymbol = null;
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = (subject == null || (typeRef = subject.getTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(typeRef)) == null) ? null : ConeTypeUtilsKt.lowerBoundIfFlexible(coneType);
        if (coneSimpleKotlinTypeLowerBoundIfFlexible != null && (coneSimpleKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneSimpleKotlinTypeLowerBoundIfFlexible, context.getSession())) != null) {
            regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(coneSimpleKotlinTypeFullyExpandedType, context.getSession());
        }
        FirRegularClassSymbol firRegularClassSymbol = regularClassSymbol;
        if (whenExpression.getUsedAsExpression()) {
            if (isIfExpression(source)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getINVALID_IF_AS_EXPRESSION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            } else {
                if (isWhenExpression(source)) {
                    reportNoElseInWhen(reporter, source, whenExpression, firRegularClassSymbol, context);
                    return;
                }
                return;
            }
        }
        if (firRegularClassSymbol == null) {
            return;
        }
        if (firRegularClassSymbol.getResolvedStatus().getModality() == Modality.SEALED) {
            algebraicTypeKind = AlgebraicTypeKind.Sealed;
        } else if (firRegularClassSymbol.getClassKind() == ClassKind.ENUM_CLASS) {
            algebraicTypeKind = AlgebraicTypeKind.Enum;
        } else if (!ConeBuiltinTypeUtilsKt.isBooleanOrNullableBoolean(coneSimpleKotlinTypeLowerBoundIfFlexible)) {
            return;
        } else {
            algebraicTypeKind = AlgebraicTypeKind.Boolean;
        }
        if (FirLanguageSettingsComponentKt.getLanguageVersionSettings(context.getSession()).supportsFeature(LanguageFeature.ProhibitNonExhaustiveWhenOnAlgebraicTypes)) {
            reportNoElseInWhen(reporter, source, whenExpression, firRegularClassSymbol, context);
        } else {
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNON_EXHAUSTIVE_WHEN_STATEMENT(), algebraicTypeKind.getDisplayName(), getMissingCases(whenExpression), context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
        }
    }

    private final void reportNoElseInWhen(DiagnosticReporter reporter, KtSourceElement source, FirWhenExpression whenExpression, FirRegularClassSymbol subjectClassSymbol, CheckerContext context) {
        String str;
        if (Intrinsics.areEqual((Object) (subjectClassSymbol != null ? Boolean.valueOf(subjectClassSymbol.getRawStatus().isExpect()) : null), (Object) true)) {
            str = " ('when' with expect " + (subjectClassSymbol.getClassKind() == ClassKind.ENUM_CLASS ? PsiKeyword.ENUM : "sealed") + " subject cannot be exhaustive without else branch)";
        } else {
            str = "";
        }
        KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getNO_ELSE_IN_WHEN(), getMissingCases(whenExpression), str, context, (AbstractSourceElementPositioningStrategy) null, 32, (Object) null);
    }

    private final List<WhenMissingCase> getMissingCases(FirWhenExpression firWhenExpression) {
        ExhaustivenessStatus exhaustivenessStatus = firWhenExpression.getExhaustivenessStatus();
        Intrinsics.checkNotNull(exhaustivenessStatus, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.ExhaustivenessStatus.NotExhaustive");
        return ((ExhaustivenessStatus.NotExhaustive) exhaustivenessStatus).getReasons();
    }

    /* JADX INFO: compiled from: FirExhaustiveWhenChecker.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExhaustiveWhenChecker$AlgebraicTypeKind;", "", "displayName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDisplayName", "()Ljava/lang/String;", "Sealed", "Enum", "Boolean", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum AlgebraicTypeKind {
        Sealed("sealed class/interface"),
        Enum(PsiKeyword.ENUM),
        Boolean("Boolean");

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final String displayName;

        public static EnumEntries<AlgebraicTypeKind> getEntries() {
            return $ENTRIES;
        }

        AlgebraicTypeKind(String str) {
            this.displayName = str;
        }

        public final String getDisplayName() {
            return this.displayName;
        }
    }

    private final void reportElseMisplaced(FirWhenExpression expression, DiagnosticReporter reporter, CheckerContext context) {
        int size = expression.getBranches().size();
        for (IndexedValue indexedValue : CollectionsKt.withIndex(expression.getBranches())) {
            FirWhenBranch firWhenBranch = (FirWhenBranch) indexedValue.getValue();
            if ((firWhenBranch.getCondition() instanceof FirElseIfTrueCondition) && indexedValue.getIndex() < size - 1) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, firWhenBranch.getSource(), FirErrors.INSTANCE.getELSE_MISPLACED_IN_WHEN(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    private final boolean isIfExpression(KtSourceElement ktSourceElement) {
        return Intrinsics.areEqual(ktSourceElement.getElementType(), KtNodeTypes.IF);
    }

    private final boolean isWhenExpression(KtSourceElement ktSourceElement) {
        return Intrinsics.areEqual(ktSourceElement.getElementType(), KtNodeTypes.WHEN);
    }
}
