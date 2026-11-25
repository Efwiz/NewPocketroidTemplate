package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: DynamicCalls.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\u0010\u0010\u0003\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\u0005H\u0000\u001a\u0010\u0010\u0006\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\u0005H\u0000\u001a\u0010\u0010\u0007\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\u0005H\u0000¨\u0006\b"}, d2 = {"getDynamicOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "Lorg/jetbrains/kotlin/psi/KtElement;", "isImplicitGet", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "isImplicitInvoke", "isImplicitSet", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DynamicCallsKt {
    public static final boolean isImplicitInvoke(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        if (!Intrinsics.areEqual(resolvedCall.getResultingDescriptor().getShortName(), OperatorNameConventions.INVOKE)) {
            return false;
        }
        KtElement callElement = resolvedCall.getCall().getCallElement();
        KtCallExpression ktCallExpression = callElement instanceof KtCallExpression ? (KtCallExpression) callElement : null;
        if (ktCallExpression == null) {
            return false;
        }
        KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
        KtSimpleNameExpression ktSimpleNameExpression = calleeExpression instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) calleeExpression : null;
        if (ktSimpleNameExpression == null) {
            return true;
        }
        return true ^ Intrinsics.areEqual(ktSimpleNameExpression.getReferencedName(), OperatorNameConventions.INVOKE.asString());
    }

    public static final boolean isImplicitGet(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        return Intrinsics.areEqual(resolvedCall.getResultingDescriptor().getShortName(), OperatorNameConventions.GET) && (resolvedCall.getCall().getCallElement() instanceof KtArrayAccessExpression);
    }

    public static final boolean isImplicitSet(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        return Intrinsics.areEqual(resolvedCall.getResultingDescriptor().getShortName(), OperatorNameConventions.SET) && (resolvedCall.getCall().getCallElement() instanceof KtArrayAccessExpression);
    }

    public static final IrDynamicOperator getDynamicOperator(KtElement ktElement) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        if (ktElement instanceof KtUnaryExpression) {
            KtUnaryExpression ktUnaryExpression = (KtUnaryExpression) ktElement;
            IElementType operationToken = ktUnaryExpression.getOperationToken();
            if (Intrinsics.areEqual(operationToken, KtTokens.PLUS)) {
                return IrDynamicOperator.UNARY_PLUS;
            }
            if (Intrinsics.areEqual(operationToken, KtTokens.MINUS)) {
                return IrDynamicOperator.UNARY_MINUS;
            }
            if (Intrinsics.areEqual(operationToken, KtTokens.EXCL)) {
                return IrDynamicOperator.EXCL;
            }
            throw new AssertionError("Unexpected unary operator expression: " + ktUnaryExpression.getText());
        }
        if (ktElement instanceof KtBinaryExpression) {
            KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) ktElement;
            IElementType operationToken2 = ktBinaryExpression.getOperationToken();
            if (Intrinsics.areEqual(operationToken2, KtTokens.PLUS)) {
                return IrDynamicOperator.BINARY_PLUS;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.MINUS)) {
                return IrDynamicOperator.BINARY_MINUS;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.MUL)) {
                return IrDynamicOperator.MUL;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.DIV)) {
                return IrDynamicOperator.DIV;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.PERC)) {
                return IrDynamicOperator.MOD;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.LT)) {
                return IrDynamicOperator.LT;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.LTEQ)) {
                return IrDynamicOperator.LE;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.GT)) {
                return IrDynamicOperator.GT;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.GTEQ)) {
                return IrDynamicOperator.GE;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.ANDAND)) {
                return IrDynamicOperator.ANDAND;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.OROR)) {
                return IrDynamicOperator.OROR;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.EQEQ)) {
                return IrDynamicOperator.EQEQ;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.EQEQEQ)) {
                return IrDynamicOperator.EQEQEQ;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.EXCLEQ)) {
                return IrDynamicOperator.EXCLEQ;
            }
            if (Intrinsics.areEqual(operationToken2, KtTokens.EXCLEQEQEQ)) {
                return IrDynamicOperator.EXCLEQEQ;
            }
            throw new AssertionError("Unexpected binary operator expression: " + ktBinaryExpression.getText());
        }
        throw new AssertionError("Unexpected operator expression: " + ktElement.getText());
    }
}
