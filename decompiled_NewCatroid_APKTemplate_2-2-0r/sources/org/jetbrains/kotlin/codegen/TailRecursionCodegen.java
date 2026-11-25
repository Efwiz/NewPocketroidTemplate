package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cfg.TailRecursionKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: TailRecursionCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ&\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0002J\u0012\u0010\u0014\u001a\u00020\f2\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0012\u0010\u001b\u001a\u00020\u001c2\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0016J\u0018\u0010\u001d\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/codegen/TailRecursionCodegen;", "", "context", "Lorg/jetbrains/kotlin/codegen/context/MethodContext;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/context/MethodContext;Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "assignParameterValues", "", "fd", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "callableMethod", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", "valueArguments", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "generateTailRecursion", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getParameterVariableIndex", "", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "isTailRecursion", "", "store", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TailRecursionCodegen {
    private final ExpressionCodegen codegen;
    private final MethodContext context;
    private final GenerationState state;
    private final InstructionAdapter v;

    public TailRecursionCodegen(MethodContext context, ExpressionCodegen codegen, InstructionAdapter v, GenerationState state) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(state, "state");
        this.context = context;
        this.codegen = codegen;
        this.v = v;
        this.state = state;
    }

    public final boolean isTailRecursion(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        TailRecursionKind tailRecursionKind = (TailRecursionKind) this.state.getBindingContext().get(BindingContext.TAIL_RECURSION_CALL, resolvedCall.getCall());
        return tailRecursionKind != null && tailRecursionKind.isDoGenerateTailRecursion();
    }

    public final void generateTailRecursion(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        CallableDescriptor callableDescriptorUnwrapInitialDescriptorForSuspendFunction = CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(resolvedCall.getResultingDescriptor());
        FunctionDescriptor functionDescriptor = callableDescriptorUnwrapInitialDescriptorForSuspendFunction instanceof FunctionDescriptor ? (FunctionDescriptor) callableDescriptorUnwrapInitialDescriptorForSuspendFunction : null;
        if (functionDescriptor == null) {
            throw new IllegalStateException(("Resolved call doesn't refer to the function descriptor: " + callableDescriptorUnwrapInitialDescriptorForSuspendFunction).toString());
        }
        Callable callableResolveToCallable = this.codegen.resolveToCallable(functionDescriptor, false, resolvedCall);
        Intrinsics.checkNotNull(callableResolveToCallable, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.CallableMethod");
        CallableMethod callableMethod = (CallableMethod) callableResolveToCallable;
        List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
        if (valueArgumentsByIndex == null) {
            throw new IllegalStateException("Failed to arrange value arguments by index: " + functionDescriptor);
        }
        if (functionDescriptor.isSuspend()) {
            AsmUtil.pop(this.v, ((JvmMethodParameterSignature) CollectionsKt.last((List) callableMethod.getValueParameters())).getAsmType());
        }
        assignParameterValues(functionDescriptor, callableMethod, valueArgumentsByIndex);
        if (callableMethod.getExtensionReceiverType() != null) {
            ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
            ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            if (!Intrinsics.areEqual(extensionReceiver, extensionReceiverParameter.getValue())) {
                this.context.getReceiverExpression(this.codegen.typeMapper).store(StackValue.onStack(callableMethod.getExtensionReceiverType()), this.v, true);
            } else {
                AsmUtil.pop(this.v, callableMethod.getExtensionReceiverType());
            }
        }
        if (callableMethod.getDispatchReceiverType() != null) {
            AsmUtil.pop(this.v, callableMethod.getDispatchReceiverType());
        }
        this.v.goTo(this.context.getMethodStartLabel());
    }

    private final void assignParameterValues(CallableDescriptor fd, CallableMethod callableMethod, List<? extends ResolvedValueArgument> valueArguments) {
        boolean zSupportsFeature = this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperComputationOrderOfTailrecDefaultParameters);
        List<Type> valueParameterTypes = callableMethod.getValueParameterTypes();
        List<ValueParameterDescriptor> valueParameters = fd.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        for (ValueParameterDescriptor valueParameterDescriptor : CollectionsKt.asReversedMutable(valueParameters)) {
            ResolvedValueArgument resolvedValueArgument = valueArguments.get(valueParameterDescriptor.getIndex());
            Type type = valueParameterTypes.get(valueParameterDescriptor.getIndex());
            if (resolvedValueArgument instanceof ExpressionValueArgument) {
                ValueArgument valueArgument = ((ExpressionValueArgument) resolvedValueArgument).getValueArgument();
                KtExpression argumentExpression = valueArgument != null ? valueArgument.getArgumentExpression() : null;
                if (argumentExpression instanceof KtSimpleNameExpression) {
                    ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(argumentExpression, this.state.getBindingContext());
                    if (Intrinsics.areEqual(resolvedCall != null ? resolvedCall.getResultingDescriptor() : null, valueParameterDescriptor.getOriginal())) {
                        AsmUtil.pop(this.v, type);
                    }
                }
                Intrinsics.checkNotNull(valueParameterDescriptor);
                store(valueParameterDescriptor, type);
            } else {
                if (resolvedValueArgument instanceof DefaultValueArgument) {
                    AsmUtil.pop(this.v, type);
                    if (!zSupportsFeature) {
                        DefaultParameterValueLoader.DEFAULT.genValue(valueParameterDescriptor, this.codegen).put(type, this.v);
                    }
                } else if (!(resolvedValueArgument instanceof VarargValueArgument)) {
                    throw new UnsupportedOperationException("Unknown argument type: " + resolvedValueArgument + " in " + fd);
                }
                Intrinsics.checkNotNull(valueParameterDescriptor);
                store(valueParameterDescriptor, type);
            }
        }
        if (zSupportsFeature) {
            for (ValueParameterDescriptor valueParameterDescriptor2 : fd.getValueParameters()) {
                ResolvedValueArgument resolvedValueArgument2 = valueArguments.get(valueParameterDescriptor2.getIndex());
                Type type2 = valueParameterTypes.get(valueParameterDescriptor2.getIndex());
                if (resolvedValueArgument2 instanceof DefaultValueArgument) {
                    DefaultParameterValueLoader.DEFAULT.genValue(valueParameterDescriptor2, this.codegen).put(type2, this.v);
                    Intrinsics.checkNotNull(valueParameterDescriptor2);
                    store(valueParameterDescriptor2, type2);
                }
            }
        }
    }

    private final void store(ValueParameterDescriptor parameterDescriptor, Type type) {
        this.v.store(getParameterVariableIndex(parameterDescriptor), type);
    }

    private final int getParameterVariableIndex(ValueParameterDescriptor parameterDescriptor) {
        int iLookupLocalIndex = this.codegen.lookupLocalIndex(parameterDescriptor);
        if (iLookupLocalIndex == -1) {
            iLookupLocalIndex = this.codegen.lookupLocalIndex(parameterDescriptor.getOriginal());
        }
        if (iLookupLocalIndex != -1) {
            return iLookupLocalIndex;
        }
        throw new IllegalStateException("Failed to obtain parameter index: " + parameterDescriptor);
    }
}
