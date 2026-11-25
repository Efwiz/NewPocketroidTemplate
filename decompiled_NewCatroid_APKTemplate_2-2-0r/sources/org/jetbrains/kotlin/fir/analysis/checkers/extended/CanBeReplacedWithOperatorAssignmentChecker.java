package org.jetbrains.kotlin.fir.analysis.checkers.extended;

import com.beust.jcommander.Parameters;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirErrors;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.util.LightTreeUtilsKt;
import org.slf4j.Marker;

/* JADX INFO: compiled from: CanBeReplacedWithOperatorAssignmentChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\f\u0012\u0004\u0012\u00020\u00020\u0001j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001a\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012H\u0002J\u001c\u0010\u0014\u001a\u00020\r2\b\u0010\u0015\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0016\u001a\u0004\u0018\u00010\u000fH\u0002J,\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u000fH\u0002J\f\u0010\u001c\u001a\u00020\r*\u00020\u000fH\u0002J\f\u0010\u001c\u001a\u00020\r*\u00020\u001dH\u0002J\f\u0010\u001e\u001a\u00020\r*\u00020\u001dH\u0002J\u0014\u0010\u001f\u001a\u00020\r*\u00020\u001d2\u0006\u0010\u0018\u001a\u00020 H\u0002¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/extended/CanBeReplacedWithOperatorAssignmentChecker;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirExpressionChecker;", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/FirVariableAssignmentChecker;", "()V", "check", "", "expression", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "isCommutativeOperator", "", "operator", "Lorg/jetbrains/kotlin/com/intellij/lang/LighterASTNode;", "isHierarchicallyTrue", "currentOperation", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "nextOperation", "isLightNodesHierarchicallyTrue", "first", "second", "lightTreeMatcher", "variable", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtLightSourceElement;", "prevOperator", "canBeAugmented", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "isCommutative", "matcher", "Lorg/jetbrains/kotlin/psi/KtNameReferenceExpression;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CanBeReplacedWithOperatorAssignmentChecker extends FirExpressionChecker<FirVariableAssignment> {
    public static final CanBeReplacedWithOperatorAssignmentChecker INSTANCE = new CanBeReplacedWithOperatorAssignmentChecker();

    private CanBeReplacedWithOperatorAssignmentChecker() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.expression.FirExpressionChecker
    public void check(FirVariableAssignment expression, CheckerContext context, DiagnosticReporter reporter) {
        FirExpression explicitReceiver;
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        FirCallableSymbol<?> resolvedCallableSymbol;
        ConeClassLikeType coneClassLikeTypeDispatchReceiverClassTypeOrNull;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(reporter, "reporter");
        FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(expression);
        if (calleeReference instanceof FirResolvedNamedReference) {
            KtSourceElement source = expression.getSource();
            if ((source != null ? source.getKind() : null) instanceof KtFakeSourceElementKind) {
                return;
            }
            FirExpression rValue = expression.getRValue();
            FirFunctionCall firFunctionCall = rValue instanceof FirFunctionCall ? (FirFunctionCall) rValue : null;
            if (firFunctionCall == null) {
                return;
            }
            KtSourceElement source2 = firFunctionCall.getSource();
            if (((source2 != null ? source2.getKind() : null) instanceof KtFakeSourceElementKind) || (explicitReceiver = firFunctionCall.getExplicitReceiver()) == null || (typeRef = explicitReceiver.getTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(typeRef)) == null || !ConeBuiltinTypeUtilsKt.isPrimitive(coneType) || (resolvedCallableSymbol = FirExpressionUtilKt.toResolvedCallableSymbol(firFunctionCall)) == null || (coneClassLikeTypeDispatchReceiverClassTypeOrNull = ClassMembersKt.dispatchReceiverClassTypeOrNull(resolvedCallableSymbol)) == null || !ConeBuiltinTypeUtilsKt.isPrimitive(coneClassLikeTypeDispatchReceiverClassTypeOrNull)) {
                return;
            }
            KtSourceElement source3 = expression.getSource();
            if (source3 instanceof KtPsiSourceElement) {
                PsiElement psi = UtilsKt.getPsi(calleeReference);
                KtNameReferenceExpression ktNameReferenceExpression = psi instanceof KtNameReferenceExpression ? (KtNameReferenceExpression) psi : null;
                if (ktNameReferenceExpression == null) {
                    return;
                }
                PsiElement psi2 = UtilsKt.getPsi(firFunctionCall);
                KtBinaryExpression ktBinaryExpression = psi2 instanceof KtBinaryExpression ? (KtBinaryExpression) psi2 : null;
                if (ktBinaryExpression == null || !matcher(ktBinaryExpression, ktNameReferenceExpression)) {
                    return;
                }
            } else {
                if (!(source3 instanceof KtLightSourceElement)) {
                    return;
                }
                KtSourceElement source4 = ((FirResolvedNamedReference) calleeReference).getSource();
                Intrinsics.checkNotNull(source4);
                LighterASTNode lighterASTNode = source4.getLighterASTNode();
                KtSourceElement source5 = firFunctionCall.getSource();
                Intrinsics.checkNotNull(source5);
                if (!lightTreeMatcher$default(this, lighterASTNode, source5.getLighterASTNode(), (KtLightSourceElement) source3, null, 8, null)) {
                    return;
                }
            }
            KtDiagnosticReportHelpersKt.reportOn$default(reporter, expression.getSource(), FirErrors.INSTANCE.getCAN_BE_REPLACED_WITH_OPERATOR_ASSIGNMENT(), context, (AbstractSourceElementPositioningStrategy) null, 8, (Object) null);
        }
    }

    static /* synthetic */ boolean lightTreeMatcher$default(CanBeReplacedWithOperatorAssignmentChecker canBeReplacedWithOperatorAssignmentChecker, LighterASTNode lighterASTNode, LighterASTNode lighterASTNode2, KtLightSourceElement ktLightSourceElement, LighterASTNode lighterASTNode3, int i, Object obj) {
        if ((i & 8) != 0) {
            lighterASTNode3 = null;
        }
        return canBeReplacedWithOperatorAssignmentChecker.lightTreeMatcher(lighterASTNode, lighterASTNode2, ktLightSourceElement, lighterASTNode3);
    }

    private final boolean lightTreeMatcher(LighterASTNode variable, LighterASTNode expression, KtLightSourceElement source, LighterASTNode prevOperator) {
        Object next;
        List<LighterASTNode> children = LightTreeUtilsKt.getChildren(expression, source.getTreeStructure());
        Iterator<T> it = children.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((LighterASTNode) next).getTokenType(), KtNodeTypes.OPERATION_REFERENCE));
        LighterASTNode lighterASTNode = (LighterASTNode) next;
        if (prevOperator != null && !isLightNodesHierarchicallyTrue(prevOperator, lighterASTNode)) {
            return false;
        }
        if (lighterASTNode != null && !canBeAugmented(lighterASTNode)) {
            return false;
        }
        boolean z = lighterASTNode != null && isCommutativeOperator(lighterASTNode);
        boolean z2 = false;
        for (LighterASTNode lighterASTNode2 : children) {
            IElementType tokenType = lighterASTNode2.getTokenType();
            if (!Intrinsics.areEqual(tokenType, KtNodeTypes.REFERENCE_EXPRESSION)) {
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.BINARY_EXPRESSION)) {
                    return INSTANCE.lightTreeMatcher(variable, lighterASTNode2, source, lighterASTNode);
                }
                if (Intrinsics.areEqual(tokenType, KtNodeTypes.OPERATION_REFERENCE)) {
                    z2 = true;
                }
            } else if (z || !z2) {
                if (Intrinsics.areEqual(lighterASTNode2.toString(), variable.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean matcher(KtBinaryExpression ktBinaryExpression, KtNameReferenceExpression ktNameReferenceExpression) {
        if (!canBeAugmented(ktBinaryExpression)) {
            return false;
        }
        KtExpression left = ktBinaryExpression.getLeft();
        KtNameReferenceExpression ktNameReferenceExpression2 = left instanceof KtNameReferenceExpression ? (KtNameReferenceExpression) left : null;
        if (Intrinsics.areEqual(ktNameReferenceExpression2 != null ? ktNameReferenceExpression2.getReferencedName() : null, ktNameReferenceExpression.getReferencedName())) {
            return true;
        }
        KtExpression right = ktBinaryExpression.getRight();
        KtNameReferenceExpression ktNameReferenceExpression3 = right instanceof KtNameReferenceExpression ? (KtNameReferenceExpression) right : null;
        if (Intrinsics.areEqual(ktNameReferenceExpression3 != null ? ktNameReferenceExpression3.getReferencedName() : null, ktNameReferenceExpression.getReferencedName()) && isCommutative(ktBinaryExpression)) {
            return true;
        }
        if (isCommutative(ktBinaryExpression)) {
            KtExpression left2 = ktBinaryExpression.getLeft();
            KtBinaryExpression ktBinaryExpression2 = left2 instanceof KtBinaryExpression ? (KtBinaryExpression) left2 : null;
            KtExpression right2 = ktBinaryExpression.getRight();
            KtBinaryExpression ktBinaryExpression3 = right2 instanceof KtBinaryExpression ? (KtBinaryExpression) right2 : null;
            IElementType operationToken = ktBinaryExpression.getOperationToken();
            Intrinsics.checkNotNullExpressionValue(operationToken, "getOperationToken(...)");
            if (isHierarchicallyTrue(operationToken, ktBinaryExpression2 != null ? ktBinaryExpression2.getOperationToken() : null) && ktBinaryExpression2 != null && matcher(ktBinaryExpression2, ktNameReferenceExpression)) {
                return true;
            }
            IElementType operationToken2 = ktBinaryExpression.getOperationToken();
            Intrinsics.checkNotNullExpressionValue(operationToken2, "getOperationToken(...)");
            return isHierarchicallyTrue(operationToken2, ktBinaryExpression3 != null ? ktBinaryExpression3.getOperationToken() : null) && ktBinaryExpression3 != null && matcher(ktBinaryExpression3, ktNameReferenceExpression);
        }
        KtExpression left3 = ktBinaryExpression.getLeft();
        KtBinaryExpression ktBinaryExpression4 = left3 instanceof KtBinaryExpression ? (KtBinaryExpression) left3 : null;
        IElementType operationToken3 = ktBinaryExpression.getOperationToken();
        Intrinsics.checkNotNullExpressionValue(operationToken3, "getOperationToken(...)");
        return isHierarchicallyTrue(operationToken3, ktBinaryExpression4 != null ? ktBinaryExpression4.getOperationToken() : null) && ktBinaryExpression4 != null && matcher(ktBinaryExpression4, ktNameReferenceExpression);
    }

    private final boolean isCommutative(KtBinaryExpression ktBinaryExpression) {
        return Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.PLUS) || Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.MUL);
    }

    private final boolean canBeAugmented(KtBinaryExpression ktBinaryExpression) {
        return Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.PLUS) || Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.MUL) || Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.MINUS) || Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.DIV) || Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.PERC);
    }

    private final boolean isHierarchicallyTrue(IElementType currentOperation, IElementType nextOperation) {
        return Intrinsics.areEqual(currentOperation, nextOperation);
    }

    private final boolean isCommutativeOperator(LighterASTNode operator) {
        String string = operator.toString();
        return Intrinsics.areEqual(string, Marker.ANY_NON_NULL_MARKER) || Intrinsics.areEqual(string, "*");
    }

    private final boolean isLightNodesHierarchicallyTrue(LighterASTNode first, LighterASTNode second) {
        return Intrinsics.areEqual(String.valueOf(first), String.valueOf(second));
    }

    private final boolean canBeAugmented(LighterASTNode lighterASTNode) {
        String string = lighterASTNode.toString();
        return Intrinsics.areEqual(string, Marker.ANY_NON_NULL_MARKER) || Intrinsics.areEqual(string, "*") || Intrinsics.areEqual(string, Parameters.DEFAULT_OPTION_PREFIXES) || Intrinsics.areEqual(string, "/") || Intrinsics.areEqual(string, "%");
    }
}
