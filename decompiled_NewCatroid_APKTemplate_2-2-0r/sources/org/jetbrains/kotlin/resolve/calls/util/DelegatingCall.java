package org.jetbrains.kotlin.resolve.calls.util;

import java.util.List;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.LambdaArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: loaded from: classes6.dex */
public class DelegatingCall implements Call {
    private final Call delegate;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/resolve/calls/util/DelegatingCall";
        } else {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        }
        if (i == 1) {
            objArr[1] = "getValueArguments";
        } else if (i == 2) {
            objArr[1] = "getFunctionLiteralArguments";
        } else if (i == 3) {
            objArr[1] = "getTypeArguments";
        } else if (i == 4) {
            objArr[1] = "getCallElement";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/resolve/calls/util/DelegatingCall";
        } else {
            objArr[1] = "getCallType";
        }
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public DelegatingCall(Call call) {
        if (call == null) {
            $$$reportNull$$$0(0);
        }
        this.delegate = call;
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public ASTNode getCallOperationNode() {
        return this.delegate.getCallOperationNode();
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public Receiver getExplicitReceiver() {
        return this.delegate.getExplicitReceiver();
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public ReceiverValue getDispatchReceiver() {
        return this.delegate.getDispatchReceiver();
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public KtExpression getCalleeExpression() {
        return this.delegate.getCalleeExpression();
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public KtValueArgumentList getValueArgumentList() {
        return this.delegate.getValueArgumentList();
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public List<? extends ValueArgument> getValueArguments() {
        List<? extends ValueArgument> valueArguments = this.delegate.getValueArguments();
        if (valueArguments == null) {
            $$$reportNull$$$0(1);
        }
        return valueArguments;
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public List<? extends LambdaArgument> getFunctionLiteralArguments() {
        List<? extends LambdaArgument> functionLiteralArguments = this.delegate.getFunctionLiteralArguments();
        if (functionLiteralArguments == null) {
            $$$reportNull$$$0(2);
        }
        return functionLiteralArguments;
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public List<KtTypeProjection> getTypeArguments() {
        List<KtTypeProjection> typeArguments = this.delegate.getTypeArguments();
        if (typeArguments == null) {
            $$$reportNull$$$0(3);
        }
        return typeArguments;
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public KtTypeArgumentList getTypeArgumentList() {
        return this.delegate.getTypeArgumentList();
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public KtElement getCallElement() {
        KtElement callElement = this.delegate.getCallElement();
        if (callElement == null) {
            $$$reportNull$$$0(4);
        }
        return callElement;
    }

    @Override // org.jetbrains.kotlin.psi.Call
    public Call.CallType getCallType() {
        Call.CallType callType = this.delegate.getCallType();
        if (callType == null) {
            $$$reportNull$$$0(5);
        }
        return callType;
    }

    public String toString() {
        return "*" + this.delegate.toString();
    }
}
