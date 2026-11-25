package org.jetbrains.kotlin.resolve.calls.tasks;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: loaded from: classes6.dex */
public class TracingStrategyImpl extends AbstractTracingStrategy {
    private final KtReferenceExpression reference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
            case 5:
                objArr[0] = "call";
                break;
            case 2:
            default:
                objArr[0] = "reference";
                break;
            case 4:
            case 6:
            case 8:
            case 10:
            case 11:
                objArr[0] = "trace";
                break;
            case 7:
            case 9:
                objArr[0] = "resolvedCall";
                break;
            case 12:
            case 13:
                objArr[0] = "candidates";
                break;
            case 14:
                objArr[0] = "candidate";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/calls/tasks/TracingStrategyImpl";
        switch (i) {
            case 2:
            case 3:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
            case 4:
            case 5:
                objArr[2] = "bindCall";
                break;
            case 6:
            case 7:
                objArr[2] = "bindReference";
                break;
            case 8:
            case 9:
                objArr[2] = "bindResolvedCall";
                break;
            case 10:
                objArr[2] = "unresolvedReference";
                break;
            case 11:
            case 12:
                objArr[2] = "unresolvedReferenceWrongReceiver";
                break;
            case 13:
                objArr[2] = "isFunctionExpectedError";
                break;
            case 14:
                objArr[2] = "variableIfFunctionExpectedError";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private TracingStrategyImpl(KtReferenceExpression ktReferenceExpression, Call call) {
        super(ktReferenceExpression, call);
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (call == null) {
            $$$reportNull$$$0(1);
        }
        this.reference = ktReferenceExpression;
    }

    public static TracingStrategy create(KtReferenceExpression ktReferenceExpression, Call call) {
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (call == null) {
            $$$reportNull$$$0(3);
        }
        return new TracingStrategyImpl(ktReferenceExpression, call);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void bindCall(BindingTrace bindingTrace, Call call) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(4);
        }
        if (call == null) {
            $$$reportNull$$$0(5);
        }
        bindingTrace.record(BindingContext.CALL, call.getCalleeExpression(), call);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void bindReference(BindingTrace bindingTrace, ResolvedCall<D> resolvedCall) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(6);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(7);
        }
        DeclarationDescriptor declarationDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            declarationDescriptor = ((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall().get$suspendPropertyDescriptor();
        }
        if (declarationDescriptor instanceof FakeCallableDescriptorForObject) {
            FakeCallableDescriptorForObject fakeCallableDescriptorForObject = (FakeCallableDescriptorForObject) declarationDescriptor;
            ClassifierDescriptorWithTypeParameters referencedDescriptor = fakeCallableDescriptorForObject.getReferencedDescriptor();
            if (fakeCallableDescriptorForObject.getClassDescriptor().mo6346getCompanionObjectDescriptor() != null) {
                bindingTrace.record(BindingContext.SHORT_REFERENCE_TO_COMPANION_OBJECT, this.reference, fakeCallableDescriptorForObject.getClassDescriptor());
            }
            declarationDescriptor = referencedDescriptor;
        }
        if (((DeclarationDescriptor) bindingTrace.get(BindingContext.REFERENCE_TARGET, this.reference)) == null || !ErrorUtils.isError(declarationDescriptor)) {
            bindingTrace.record(BindingContext.REFERENCE_TARGET, this.reference, declarationDescriptor);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void bindResolvedCall(BindingTrace bindingTrace, ResolvedCall<D> resolvedCall) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(8);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(9);
        }
        bindingTrace.record(BindingContext.RESOLVED_CALL, this.call, resolvedCall);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void unresolvedReference(BindingTrace bindingTrace) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(10);
        }
        DiagnosticFactory1<KtReferenceExpression, KtReferenceExpression> diagnosticFactory1 = Errors.UNRESOLVED_REFERENCE;
        KtReferenceExpression ktReferenceExpression = this.reference;
        bindingTrace.report(diagnosticFactory1.on(ktReferenceExpression, ktReferenceExpression));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void unresolvedReferenceWrongReceiver(BindingTrace bindingTrace, Collection<? extends ResolvedCall<D>> collection) {
        if (bindingTrace == null) {
            $$$reportNull$$$0(11);
        }
        if (collection == null) {
            $$$reportNull$$$0(12);
        }
        VariableDescriptor variableDescriptorIsFunctionExpectedError = isFunctionExpectedError(collection);
        if (variableDescriptorIsFunctionExpectedError != null) {
            DiagnosticFactory2<KtExpression, KtExpression, KotlinType> diagnosticFactory2 = Errors.FUNCTION_EXPECTED;
            KtReferenceExpression ktReferenceExpression = this.reference;
            bindingTrace.report(diagnosticFactory2.on(ktReferenceExpression, ktReferenceExpression, variableDescriptorIsFunctionExpectedError.getType()));
            return;
        }
        bindingTrace.report(Errors.UNRESOLVED_REFERENCE_WRONG_RECEIVER.on(this.reference, collection));
    }

    private static <D extends CallableDescriptor> VariableDescriptor isFunctionExpectedError(Collection<? extends ResolvedCall<D>> collection) {
        if (collection == null) {
            $$$reportNull$$$0(13);
        }
        return (VariableDescriptor) CollectionsKt.singleOrNull(CollectionsKt.distinct(CollectionsKt.map(collection, new Function1() { // from class: org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategyImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return TracingStrategyImpl.variableIfFunctionExpectedError((ResolvedCall) obj);
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <D extends CallableDescriptor> VariableDescriptor variableIfFunctionExpectedError(ResolvedCall<D> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(14);
        }
        if (!(resolvedCall instanceof VariableAsFunctionResolvedCall)) {
            return null;
        }
        VariableAsFunctionResolvedCall variableAsFunctionResolvedCall = (VariableAsFunctionResolvedCall) resolvedCall;
        ResolvedCall<VariableDescriptor> variableCall = variableAsFunctionResolvedCall.getVariableCall();
        ResolvedCall<FunctionDescriptor> functionCall = variableAsFunctionResolvedCall.getFunctionCall();
        boolean z = variableCall.getStatus().isSuccess() && !FunctionTypesKt.isFunctionType(((VariableDescriptor) variableCall.get$suspendPropertyDescriptor()).getType());
        Call call = functionCall.getCall();
        if (z && CallResolverUtilKt.isInvokeCallOnVariable(call) && call.getValueArguments().isEmpty()) {
            return (VariableDescriptor) variableCall.get$suspendPropertyDescriptor();
        }
        return null;
    }
}
