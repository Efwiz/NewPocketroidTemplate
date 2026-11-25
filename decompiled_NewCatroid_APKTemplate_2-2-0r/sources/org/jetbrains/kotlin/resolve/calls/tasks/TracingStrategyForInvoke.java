package org.jetbrains.kotlin.resolve.calls.tasks;

import java.util.Collection;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public class TracingStrategyForInvoke extends AbstractTracingStrategy {
    private final KotlinType calleeType;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "call";
                break;
            case 2:
                objArr[0] = "calleeType";
                break;
            case 3:
            case 5:
            case 7:
            case 9:
            case 10:
                objArr[0] = "trace";
                break;
            case 6:
            case 8:
                objArr[0] = "resolvedCall";
                break;
            case 11:
                objArr[0] = "candidates";
                break;
            default:
                objArr[0] = "reference";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/tasks/TracingStrategyForInvoke";
        switch (i) {
            case 3:
            case 4:
                objArr[2] = "bindCall";
                break;
            case 5:
            case 6:
                objArr[2] = "bindReference";
                break;
            case 7:
            case 8:
                objArr[2] = "bindResolvedCall";
                break;
            case 9:
                objArr[2] = "unresolvedReference";
                break;
            case 10:
            case 11:
                objArr[2] = "unresolvedReferenceWrongReceiver";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TracingStrategyForInvoke(KtExpression ktExpression, Call call, KotlinType kotlinType) {
        super(ktExpression, call);
        if (ktExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (call == null) {
            $$$reportNull$$$0(1);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(2);
        }
        this.calleeType = kotlinType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void bindCall(BindingTrace bindingTrace, Call call) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(3);
        }
        if (call == null) {
            $$$reportNull$$$0(4);
        }
        if (this.reference instanceof KtSimpleNameExpression) {
            return;
        }
        bindingTrace.record(BindingContext.CALL, this.reference, call);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void bindReference(BindingTrace bindingTrace, ResolvedCall<D> resolvedCall) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(5);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(6);
        }
        KtElement callElement = this.call.getCallElement();
        if (callElement instanceof KtReferenceExpression) {
            bindingTrace.record(BindingContext.REFERENCE_TARGET, (KtReferenceExpression) callElement, resolvedCall.get$suspendPropertyDescriptor());
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void bindResolvedCall(BindingTrace bindingTrace, ResolvedCall<D> resolvedCall) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(7);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(8);
        }
        if (this.reference instanceof KtSimpleNameExpression) {
            return;
        }
        bindingTrace.record(BindingContext.RESOLVED_CALL, this.call, resolvedCall);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void unresolvedReference(BindingTrace bindingTrace) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(9);
        }
        functionExpectedOrNoReceiverAllowed(bindingTrace);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void unresolvedReferenceWrongReceiver(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(10);
        }
        if (collection == null) {
            $$$reportNull$$$0(11);
        }
        functionExpectedOrNoReceiverAllowed(bindingTrace);
    }

    private void functionExpectedOrNoReceiverAllowed(BindingTrace bindingTrace) {
        if (FunctionTypesKt.isNonExtensionFunctionType(this.calleeType)) {
            bindingTrace.report(Errors.NO_RECEIVER_ALLOWED.on(this.reference));
        } else {
            bindingTrace.report(Errors.FUNCTION_EXPECTED.on(this.reference, this.reference, this.calleeType));
        }
    }
}
