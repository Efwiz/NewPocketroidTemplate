package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirLateinitIntrinsicApplicabilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0016\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirLateinitIntrinsicApplicabilityChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirQualifiedAccessExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "containingClassOrFile", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLateinitIntrinsicApplicabilityChecker extends FirExpressionChecker<FirQualifiedAccessExpression> {
    public static final FirLateinitIntrinsicApplicabilityChecker INSTANCE = new FirLateinitIntrinsicApplicabilityChecker();

    private FirLateinitIntrinsicApplicabilityChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirQualifiedAccessExpression expression, CheckerContext context, DiagnosticReporter reporter) {
        FirReceiverParameter receiverParameter;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirPropertySymbol resolvedPropertySymbol$default = FirReferenceUtilsKt.toResolvedPropertySymbol$default(expression.getCalleeReference(), false, 1, null);
        if (resolvedPropertySymbol$default != null && Intrinsics.areEqual(resolvedPropertySymbol$default.getName().getIdentifierOrNullIfSpecial(), "isInitialized") && (receiverParameter = resolvedPropertySymbol$default.getReceiverParameter()) != null && FirAnnotationUtilsKt.hasAnnotation(receiverParameter, StandardClassIds.Annotations.INSTANCE.getAccessibleLateinitPropertyLiteral(), context.getSession())) {
            KtSourceElement source = expression.getCalleeReference().getSource();
            FirExpression extensionReceiver = expression.getExtensionReceiver();
            if (!(extensionReceiver instanceof FirCallableReferenceAccess)) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getLATEINIT_INTRINSIC_CALL_ON_NON_LITERAL(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            FirPropertySymbol resolvedPropertySymbol$default2 = FirReferenceUtilsKt.toResolvedPropertySymbol$default(((FirCallableReferenceAccess) extensionReceiver).getCalleeReference(), false, 1, null);
            if (resolvedPropertySymbol$default2 == null) {
                return;
            }
            if (!resolvedPropertySymbol$default2.getRawStatus().isLateInit()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getLATEINIT_INTRINSIC_CALL_ON_NON_LATEINIT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
                return;
            }
            if (!CollectionsKt.contains(context.getContainingDeclarations(), containingClassOrFile(resolvedPropertySymbol$default2, context))) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getLATEINIT_INTRINSIC_CALL_ON_NON_ACCESSIBLE_PROPERTY(), resolvedPropertySymbol$default2, context, (AbstractSourceElementPositioningStrategy) null, 16, (Object) null);
                return;
            }
            FirDeclaration firDeclaration = (FirDeclaration) CollectionsKt.lastOrNull((List) context.getContainingDeclarations());
            if ((firDeclaration instanceof FirFunction) && ((FirMemberDeclaration) firDeclaration).getStatus().isInline()) {
                KtDiagnosticReportHelpersKt.reportOn$default(reporter, source, FirErrors.INSTANCE.getLATEINIT_INTRINSIC_CALL_IN_INLINE_FUNCTION(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirDeclaration containingClassOrFile(FirPropertySymbol firPropertySymbol, CheckerContext checkerContext) {
        FirClassLikeDeclaration firClassLikeDeclaration;
        FirClassLikeSymbol<?> containingClassSymbol = FirHelpersKt.getContainingClassSymbol(firPropertySymbol, checkerContext.getSession());
        return (containingClassSymbol == null || (firClassLikeDeclaration = (FirClassLikeDeclaration) containingClassSymbol.getFir()) == null) ? FirProviderKt.getFirProvider(checkerContext.getSession()).getFirCallableContainerFile(firPropertySymbol) : firClassLikeDeclaration;
    }
}
