package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: FirOptInUsageAccessChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirOptInUsageAccessChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirBasicExpressionChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOptInUsageAccessChecker extends FirExpressionChecker<FirStatement> {
    public static final FirOptInUsageAccessChecker INSTANCE = new FirOptInUsageAccessChecker();

    private FirOptInUsageAccessChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirStatement expression, CheckerContext context, DiagnosticReporter reporter) {
        FirReference calleeReference;
        FirBasedSymbol<?> resolvedBaseSymbol$default;
        KtSourceElement source;
        FirDeclaration firDeclarationPrevious;
        FirExpression delegate;
        KtSourceElement source2;
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        KtSourceElement source3 = expression.getSource();
        KtSourceElementKind kind = source3 != null ? source3.getKind() : null;
        if ((kind instanceof KtFakeSourceElementKind.DataClassGeneratedMembers) || (kind instanceof KtFakeSourceElementKind.PropertyFromParameter)) {
            return;
        }
        FirStatement firStatement = expression;
        if (FirHelpersKt.isLhsOfAssignment(firStatement, context) || (calleeReference = FirExpressionUtilKt.getCalleeReference(firStatement)) == null || (resolvedBaseSymbol$default = FirReferenceUtilsKt.toResolvedBaseSymbol$default(calleeReference, false, 1, null)) == null) {
            return;
        }
        FirOptInUsageBaseChecker firOptInUsageBaseChecker = FirOptInUsageBaseChecker.INSTANCE;
        if (expression instanceof FirVariableAssignment) {
            FirOptInUsageBaseChecker.reportNotAcceptedExperimentalities$default(firOptInUsageBaseChecker, SetsKt.plus((Set) firOptInUsageBaseChecker.loadExperimentalities(resolvedBaseSymbol$default, context, true, null), (Iterable) firOptInUsageBaseChecker.loadExperimentalitiesFromTypeArguments(context, CollectionsKt.emptyList())), ((FirVariableAssignment) expression).getLValue(), context, reporter, null, 16, null);
            return;
        }
        if (expression instanceof FirQualifiedAccessExpression) {
            FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) expression;
            FirExpression dispatchReceiver = firQualifiedAccessExpression.getDispatchReceiver();
            if (dispatchReceiver instanceof FirNoReceiverExpression) {
                dispatchReceiver = null;
            }
            Set setPlus = SetsKt.plus((Set) firOptInUsageBaseChecker.loadExperimentalities(resolvedBaseSymbol$default, context, false, (dispatchReceiver == null || (typeRef = dispatchReceiver.getTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(typeRef)) == null) ? null : TypeExpansionUtilsKt.fullyExpandedType(coneType, context.getSession())), (Iterable) firOptInUsageBaseChecker.loadExperimentalitiesFromTypeArguments(context, firQualifiedAccessExpression.getTypeArguments()));
            KtSourceElement source4 = expression.getSource();
            if (Intrinsics.areEqual(source4 != null ? source4.getKind() : null, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE)) {
                List<FirDeclaration> containingDeclarations = context.getContainingDeclarations();
                ListIterator<FirDeclaration> listIterator = containingDeclarations.listIterator(containingDeclarations.size());
                do {
                    if (!listIterator.hasPrevious()) {
                        firDeclarationPrevious = null;
                        break;
                    }
                    firDeclarationPrevious = listIterator.previous();
                } while (!(firDeclarationPrevious instanceof FirProperty));
                FirProperty firProperty = firDeclarationPrevious instanceof FirProperty ? (FirProperty) firDeclarationPrevious : null;
                if (firProperty == null || (delegate = firProperty.getDelegate()) == null || (source2 = delegate.getSource()) == null || (source = KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.DelegatedPropertyAccessor.INSTANCE, 0, 0, 6, null)) == null) {
                    return;
                }
            } else {
                source = expression.getSource();
            }
            firOptInUsageBaseChecker.reportNotAcceptedExperimentalities(setPlus, firStatement, context, reporter, source);
        }
    }
}
