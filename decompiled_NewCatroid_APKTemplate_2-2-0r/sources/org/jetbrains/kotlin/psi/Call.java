package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: loaded from: classes6.dex */
public interface Call {

    public enum CallType {
        DEFAULT,
        ARRAY_GET_METHOD,
        ARRAY_SET_METHOD,
        INVOKE,
        CONTAINS
    }

    KtElement getCallElement();

    ASTNode getCallOperationNode();

    CallType getCallType();

    KtExpression getCalleeExpression();

    ReceiverValue getDispatchReceiver();

    Receiver getExplicitReceiver();

    List<? extends LambdaArgument> getFunctionLiteralArguments();

    KtTypeArgumentList getTypeArgumentList();

    List<KtTypeProjection> getTypeArguments();

    KtValueArgumentList getValueArgumentList();

    List<? extends ValueArgument> getValueArguments();

    default boolean isSemanticallyEquivalentToSafeCall() {
        return getCallOperationNode() != null && getCallOperationNode().getElementType() == KtTokens.SAFE_ACCESS;
    }
}
