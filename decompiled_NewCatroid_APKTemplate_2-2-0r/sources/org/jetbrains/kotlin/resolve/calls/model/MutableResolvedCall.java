package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: loaded from: classes6.dex */
public interface MutableResolvedCall<D extends CallableDescriptor> extends ResolvedCall<D> {
    void addRemainingTasks(Function0<Unit> function0);

    void addStatus(ResolutionStatus resolutionStatus);

    ConstraintSystem getConstraintSystem();

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    MutableDataFlowInfoForArguments getDataFlowInfoForArguments();

    TypeSubstitutor getKnownTypeParametersSubstitutor();

    DelegatingBindingTrace getTrace();

    TracingStrategy getTracingStrategy();

    boolean hasInferredReturnType();

    boolean isCompleted();

    void markCallAsCompleted();

    void performRemainingTasks();

    void recordArgumentMatchStatus(ValueArgument valueArgument, ArgumentMatchStatus argumentMatchStatus);

    void recordValueArgument(ValueParameterDescriptor valueParameterDescriptor, ResolvedValueArgument resolvedValueArgument);

    void setConstraintSystem(ConstraintSystem constraintSystem);

    void setSmartCastDispatchReceiverType(KotlinType kotlinType);

    void setStatusToSuccess();

    void setSubstitutor(TypeSubstitutor typeSubstitutor);

    void updateExtensionReceiverWithSmartCastIfNeeded(KotlinType kotlinType);
}
