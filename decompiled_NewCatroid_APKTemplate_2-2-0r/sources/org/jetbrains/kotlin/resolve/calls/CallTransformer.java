package org.jetbrains.kotlin.resolve.calls;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.LambdaArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.DelegatingCall;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: loaded from: classes6.dex */
public class CallTransformer {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "call";
        } else {
            objArr[0] = "variableCall";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/CallTransformer";
        if (i != 1) {
            objArr[2] = "stripCallArguments";
        } else {
            objArr[2] = "stripReceiver";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private CallTransformer() {
    }

    public static Call stripCallArguments(Call call) {
        if (call == null) {
            $$$reportNull$$$0(0);
        }
        return new DelegatingCall(call) { // from class: org.jetbrains.kotlin.resolve.calls.CallTransformer.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/CallTransformer$1";
                if (i == 1) {
                    objArr[1] = "getFunctionLiteralArguments";
                } else if (i == 2) {
                    objArr[1] = "getTypeArguments";
                } else if (i != 3) {
                    objArr[1] = "getValueArguments";
                } else {
                    objArr[1] = "getCallElement";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
            public KtTypeArgumentList getTypeArgumentList() {
                return null;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
            public KtValueArgumentList getValueArgumentList() {
                return null;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
            public List<? extends ValueArgument> getValueArguments() {
                List<? extends ValueArgument> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(0);
                }
                return listEmptyList;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
            public List<LambdaArgument> getFunctionLiteralArguments() {
                List<LambdaArgument> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(1);
                }
                return listEmptyList;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
            public List<KtTypeProjection> getTypeArguments() {
                List<KtTypeProjection> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(2);
                }
                return listEmptyList;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
            public KtElement getCallElement() {
                KtExpression calleeExpression = getCalleeExpression();
                if (calleeExpression == null) {
                    $$$reportNull$$$0(3);
                }
                return calleeExpression;
            }
        };
    }

    public static Call stripReceiver(Call call) {
        if (call == null) {
            $$$reportNull$$$0(1);
        }
        return new DelegatingCall(call) { // from class: org.jetbrains.kotlin.resolve.calls.CallTransformer.2
            @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
            public ASTNode getCallOperationNode() {
                return null;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
            public ReceiverValue getExplicitReceiver() {
                return null;
            }
        };
    }

    public static class CallForImplicitInvoke extends DelegatingCall {
        private final ExpressionReceiver calleeExpressionAsDispatchReceiver;
        private final Receiver explicitExtensionReceiver;
        private final KtSimpleNameExpression fakeInvokeExpression;
        public final boolean itIsVariableAsFunctionCall;
        private final Call outerCall;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
            if (i == 1) {
                objArr[0] = "call";
            } else if (i == 2 || i == 3 || i == 4) {
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/CallTransformer$CallForImplicitInvoke";
            } else {
                objArr[0] = "calleeExpressionAsDispatchReceiver";
            }
            if (i == 2) {
                objArr[1] = "getDispatchReceiver";
            } else if (i == 3) {
                objArr[1] = "getCallType";
            } else if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/resolve/calls/CallTransformer$CallForImplicitInvoke";
            } else {
                objArr[1] = "getOuterCall";
            }
            if (i != 2 && i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 2 && i != 3 && i != 4) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CallForImplicitInvoke(Receiver receiver, ExpressionReceiver expressionReceiver, Call call, boolean z) {
            super(call);
            if (expressionReceiver == null) {
                $$$reportNull$$$0(0);
            }
            if (call == null) {
                $$$reportNull$$$0(1);
            }
            this.outerCall = call;
            this.explicitExtensionReceiver = receiver;
            this.calleeExpressionAsDispatchReceiver = expressionReceiver;
            this.fakeInvokeExpression = (KtSimpleNameExpression) new KtPsiFactory(call.getCallElement().getProject(), false).createExpression(OperatorNameConventions.INVOKE.asString());
            this.itIsVariableAsFunctionCall = z;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
        public ASTNode getCallOperationNode() {
            if (this.explicitExtensionReceiver != null) {
                return super.getCallOperationNode();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
        public Receiver getExplicitReceiver() {
            return this.explicitExtensionReceiver;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
        public ExpressionReceiver getDispatchReceiver() {
            ExpressionReceiver expressionReceiver = this.calleeExpressionAsDispatchReceiver;
            if (expressionReceiver == null) {
                $$$reportNull$$$0(2);
            }
            return expressionReceiver;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
        public KtExpression getCalleeExpression() {
            return this.fakeInvokeExpression;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.util.DelegatingCall, org.jetbrains.kotlin.psi.Call
        public Call.CallType getCallType() {
            Call.CallType callType = Call.CallType.INVOKE;
            if (callType == null) {
                $$$reportNull$$$0(3);
            }
            return callType;
        }

        public Call getOuterCall() {
            Call call = this.outerCall;
            if (call == null) {
                $$$reportNull$$$0(4);
            }
            return call;
        }
    }
}
