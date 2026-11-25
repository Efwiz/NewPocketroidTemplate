package org.jetbrains.kotlin.resolve.calls.tasks;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtConstructorDelegationCall;
import org.jetbrains.kotlin.psi.KtConstructorDelegationReferenceExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceErrorData;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: TracingStrategyForImplicitConstructorDelegationCall.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J2\u0010\u0011\u001a\u00020\u000e\"\n\b\u0000\u0010\u0012*\u0004\u0018\u00010\u00132\u0006\u0010\u000f\u001a\u00020\u00102\u0014\u0010\u0014\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00160\u0015H\u0016J\u0018\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J(\u0010\u0018\u001a\u00020\u000e\"\b\b\u0000\u0010\u0012*\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0016H\u0016J(\u0010\u001a\u001a\u00020\u000e\"\b\b\u0000\u0010\u0012*\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0016H\u0016J2\u0010\u001b\u001a\u00020\u000e\"\n\b\u0000\u0010\u0012*\u0004\u0018\u00010\u00132\u0006\u0010\u000f\u001a\u00020\u00102\u0014\u0010\u001c\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00160\u0015H\u0016J\u0010\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020 H\u0016J\u0018\u0010!\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020#H\u0016J \u0010$\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0016J\u0010\u0010)\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010*\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010+\u001a\u00020,H\u0016J2\u0010-\u001a\u00020\u000e\"\n\b\u0000\u0010\u0012*\u0004\u0018\u00010\u00132\u0006\u0010\u000f\u001a\u00020\u00102\u0014\u0010\u001c\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00160\u0015H\u0016J\u0010\u0010.\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001c\u0010/\u001a\u00020\u000e2\n\u00100\u001a\u0006\u0012\u0002\b\u0003012\u0006\u00102\u001a\u000203H\u0016J\u0010\u00104\u001a\u00020\u000e2\u0006\u00105\u001a\u000206H\u0002J\u0010\u00107\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J.\u00108\u001a\u00020\u000e\"\b\b\u0000\u0010\u0012*\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00160:H\u0016J \u0010;\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u00105\u001a\u00020<2\u0006\u0010=\u001a\u00020>H\u0016J \u0010?\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010@\u001a\u00020A2\u0006\u0010\u001f\u001a\u00020\u0013H\u0016J,\u0010B\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010C\u001a\u00020#2\u0006\u0010D\u001a\u00020E2\n\u0010F\u001a\u0006\u0012\u0002\b\u000301H\u0016R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tasks/TracingStrategyForImplicitConstructorDelegationCall;", "Lorg/jetbrains/kotlin/resolve/calls/tasks/AbstractTracingStrategy;", "delegationCall", "Lorg/jetbrains/kotlin/psi/KtConstructorDelegationCall;", "call", "Lorg/jetbrains/kotlin/psi/Call;", "(Lorg/jetbrains/kotlin/psi/KtConstructorDelegationCall;Lorg/jetbrains/kotlin/psi/Call;)V", "calleeExpression", "Lorg/jetbrains/kotlin/psi/KtConstructorDelegationReferenceExpression;", "getCalleeExpression", "()Lorg/jetbrains/kotlin/psi/KtConstructorDelegationReferenceExpression;", "getDelegationCall", "()Lorg/jetbrains/kotlin/psi/KtConstructorDelegationCall;", "abstractSuperCall", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "ambiguity", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "resolvedCalls", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "bindCall", "bindReference", "resolvedCall", "bindResolvedCall", "cannotCompleteResolve", "descriptors", "instantiationOfAbstractClass", "invisibleMember", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithVisibility;", "missingReceiver", "expectedReceiver", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "nestedClassAccessViaInstanceReference", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "noReceiverAllowed", "noValueForParameter", "valueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "noneApplicable", "reportError", "typeInferenceFailed", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "data", "Lorg/jetbrains/kotlin/resolve/calls/inference/InferenceErrorData;", "unexpectedError", ModuleXmlParser.TYPE, "", "unresolvedReference", "unresolvedReferenceWrongReceiver", "candidates", "", "unsafeCall", "Lorg/jetbrains/kotlin/types/KotlinType;", "isCallForImplicitInvoke", "", "wrongNumberOfTypeArguments", "expectedTypeArgumentCount", "", "wrongReceiverType", "receiverParameter", "receiverArgument", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "c", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TracingStrategyForImplicitConstructorDelegationCall extends AbstractTracingStrategy {
    private final KtConstructorDelegationReferenceExpression calleeExpression;
    private final KtConstructorDelegationCall delegationCall;

    public final KtConstructorDelegationCall getDelegationCall() {
        return this.delegationCall;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TracingStrategyForImplicitConstructorDelegationCall(KtConstructorDelegationCall delegationCall, Call call) {
        Intrinsics.checkNotNullParameter(delegationCall, "delegationCall");
        Intrinsics.checkNotNullParameter(call, "call");
        KtConstructorDelegationReferenceExpression calleeExpression = delegationCall.getCalleeExpression();
        Intrinsics.checkNotNull(calleeExpression);
        super(calleeExpression, call);
        this.delegationCall = delegationCall;
        this.calleeExpression = delegationCall.getCalleeExpression();
    }

    public final KtConstructorDelegationReferenceExpression getCalleeExpression() {
        return this.calleeExpression;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void bindCall(BindingTrace trace, Call call) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(call, "call");
        trace.record(BindingContext.CALL, call.getCalleeExpression(), call);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void bindReference(BindingTrace trace, ResolvedCall<D> resolvedCall) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        Intrinsics.checkNotNullExpressionValue(callableDescriptor, "getCandidateDescriptor(...)");
        if (((DeclarationDescriptor) trace.get(BindingContext.REFERENCE_TARGET, this.calleeExpression)) == null || !ErrorUtils.isError(callableDescriptor)) {
            trace.record(BindingContext.REFERENCE_TARGET, this.calleeExpression, callableDescriptor);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void bindResolvedCall(BindingTrace trace, ResolvedCall<D> resolvedCall) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        trace.record(BindingContext.RESOLVED_CALL, this.call, resolvedCall);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void unresolvedReference(BindingTrace trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        DiagnosticFactory1<KtReferenceExpression, KtReferenceExpression> diagnosticFactory1 = Errors.UNRESOLVED_REFERENCE;
        KtConstructorDelegationReferenceExpression ktConstructorDelegationReferenceExpression = this.calleeExpression;
        Intrinsics.checkNotNull(ktConstructorDelegationReferenceExpression);
        trace.report(diagnosticFactory1.on(ktConstructorDelegationReferenceExpression, this.calleeExpression));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void unresolvedReferenceWrongReceiver(BindingTrace trace, Collection<? extends ResolvedCall<D>> candidates) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(candidates, "candidates");
        trace.report(Errors.UNRESOLVED_REFERENCE_WRONG_RECEIVER.on(this.reference, candidates));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void noValueForParameter(BindingTrace trace, ValueParameterDescriptor valueParameter) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        reportError(trace);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void ambiguity(BindingTrace trace, Collection<? extends ResolvedCall<D>> resolvedCalls) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(resolvedCalls, "resolvedCalls");
        reportError(trace);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void noneApplicable(BindingTrace trace, Collection<? extends ResolvedCall<D>> descriptors) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        reportError(trace);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void invisibleMember(BindingTrace trace, DeclarationDescriptorWithVisibility descriptor) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        reportError(trace);
    }

    private final void reportError(BindingTrace trace) {
        PsiElement psiElementReportOnElement = CallResolverUtilKt.reportOnElement(this.delegationCall);
        Collection<Diagnostic> collectionForElement = trace.getContext().getDiagnostics().forElement(psiElementReportOnElement);
        if (!(collectionForElement instanceof Collection) || !collectionForElement.isEmpty()) {
            Iterator<T> it = collectionForElement.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((Diagnostic) it.next()).getFactory(), Errors.EXPLICIT_DELEGATION_CALL_REQUIRED)) {
                    return;
                }
            }
        }
        trace.report(Errors.EXPLICIT_DELEGATION_CALL_REQUIRED.on(psiElementReportOnElement));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public <D extends CallableDescriptor> void cannotCompleteResolve(BindingTrace trace, Collection<? extends ResolvedCall<D>> descriptors) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
        unexpectedError("cannotCompleteResolve");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void instantiationOfAbstractClass(BindingTrace trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        unexpectedError("instantiationOfAbstractClass");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void abstractSuperCall(BindingTrace trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        unexpectedError("abstractSuperCall");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void nestedClassAccessViaInstanceReference(BindingTrace trace, ClassDescriptor classDescriptor, ExplicitReceiverKind explicitReceiverKind) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        unexpectedError("nestedClassAccessViaInstanceReference");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void unsafeCall(BindingTrace trace, KotlinType type, boolean isCallForImplicitInvoke) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(type, "type");
        unexpectedError("unsafeCall");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void missingReceiver(BindingTrace trace, ReceiverParameterDescriptor expectedReceiver) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(expectedReceiver, "expectedReceiver");
        unexpectedError("missingReceiver");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void wrongReceiverType(BindingTrace trace, ReceiverParameterDescriptor receiverParameter, ReceiverValue receiverArgument, ResolutionContext<?> c) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        Intrinsics.checkNotNullParameter(receiverArgument, "receiverArgument");
        Intrinsics.checkNotNullParameter(c, "c");
        unexpectedError("wrongReceiverType");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void noReceiverAllowed(BindingTrace trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        unexpectedError("noReceiverAllowed");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void wrongNumberOfTypeArguments(BindingTrace trace, int expectedTypeArgumentCount, CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        unexpectedError("wrongNumberOfTypeArguments");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tasks.AbstractTracingStrategy, org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy
    public void typeInferenceFailed(ResolutionContext<?> context, InferenceErrorData data) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedError("typeInferenceFailed");
    }

    private final void unexpectedError(String type) {
        throw new AssertionError("Unexpected error type: " + type);
    }
}
