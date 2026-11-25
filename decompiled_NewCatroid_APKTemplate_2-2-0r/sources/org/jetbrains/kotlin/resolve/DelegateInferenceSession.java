package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.components.InferenceSession;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzer;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.components.KotlinConstraintSystemCompleter;
import org.jetbrains.kotlin.resolve.calls.inference.model.DelegatedPropertyConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedLambdaAtom;
import org.jetbrains.kotlin.resolve.calls.model.SingleCallResolutionResult;
import org.jetbrains.kotlin.resolve.calls.tower.PSICallResolver;
import org.jetbrains.kotlin.resolve.calls.tower.PSIPartialCallInfo;
import org.jetbrains.kotlin.resolve.calls.tower.StubTypesBasedInferenceSession;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: DelegateInferenceSession.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BI\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0013J\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bJ4\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00060\u001e2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0016J\u0010\u0010(\u001a\u00020)2\u0006\u0010 \u001a\u00020!H\u0016J\u001e\u0010*\u001a\u00020)2\u0006\u0010+\u001a\u00020,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u001bH\u0016J\u0010\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u0002002\u0006\u00104\u001a\u000205H\u0016J\u001c\u00106\u001a\u00020)*\u0002022\u0006\u00107\u001a\u0002082\u0006\u0010+\u001a\u00020#H\u0002J\u0014\u00109\u001a\u00020)*\u0002022\u0006\u0010+\u001a\u00020#H\u0002J\u0014\u0010:\u001a\u00020)*\u0002022\u0006\u0010+\u001a\u00020#H\u0002R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DelegateInferenceSession;", "Lorg/jetbrains/kotlin/resolve/calls/tower/StubTypesBasedInferenceSession;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "psiCallResolver", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;", "postponedArgumentsAnalyzer", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;", "kotlinConstraintSystemCompleter", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "parentSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/resolve/calls/tower/PSICallResolver;Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzer;Lorg/jetbrains/kotlin/resolve/calls/inference/components/KotlinConstraintSystemCompleter;Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;)V", "getExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getParentSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getVariableDescriptor", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "getNestedBuilderInferenceSessions", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceSession;", "inferPostponedVariables", "", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "lambda", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedLambdaAtom;", "constraintSystemBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "initializeLambda", "", "prepareForCompletion", "commonSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "resolvedCallsInfo", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIPartialCallInfo;", "shouldCompleteResolvedSubAtomsOf", "", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "writeOnlyStubs", "callInfo", "Lorg/jetbrains/kotlin/resolve/calls/model/SingleCallResolutionResult;", "addConstraintForThis", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "addConstraintsForGetValueMethod", "addConstraintsForSetValueMethod", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegateInferenceSession extends StubTypesBasedInferenceSession<FunctionDescriptor> {
    private final UnwrappedType expectedType;
    private final InferenceSession parentSession;
    private final VariableDescriptorWithAccessors variableDescriptor;

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public void initializeLambda(ResolvedLambdaAtom lambda) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.StubTypesBasedInferenceSession, org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean shouldCompleteResolvedSubAtomsOf(ResolvedCallAtom resolvedCallAtom) {
        Intrinsics.checkNotNullParameter(resolvedCallAtom, "resolvedCallAtom");
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public boolean writeOnlyStubs(SingleCallResolutionResult callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        return false;
    }

    public final VariableDescriptorWithAccessors getVariableDescriptor() {
        return this.variableDescriptor;
    }

    public final UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public InferenceSession getParentSession() {
        return this.parentSession;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DelegateInferenceSession(VariableDescriptorWithAccessors variableDescriptor, UnwrappedType unwrappedType, PSICallResolver psiCallResolver, PostponedArgumentsAnalyzer postponedArgumentsAnalyzer, KotlinConstraintSystemCompleter kotlinConstraintSystemCompleter, KotlinCallComponents callComponents, KotlinBuiltIns builtIns, InferenceSession inferenceSession) {
        super(psiCallResolver, postponedArgumentsAnalyzer, kotlinConstraintSystemCompleter, callComponents, builtIns);
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(psiCallResolver, "psiCallResolver");
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzer, "postponedArgumentsAnalyzer");
        Intrinsics.checkNotNullParameter(kotlinConstraintSystemCompleter, "kotlinConstraintSystemCompleter");
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.variableDescriptor = variableDescriptor;
        this.expectedType = unwrappedType;
        this.parentSession = inferenceSession;
        if (getParentSession() instanceof StubTypesBasedInferenceSession) {
            ((StubTypesBasedInferenceSession) getParentSession()).addNestedInferenceSession(this);
        }
    }

    public final List<BuilderInferenceSession> getNestedBuilderInferenceSessions() {
        HashSet<StubTypesBasedInferenceSession<?>> nestedInferenceSessions = getNestedInferenceSessions();
        ArrayList arrayList = new ArrayList();
        for (Object obj : nestedInferenceSessions) {
            if (obj instanceof BuilderInferenceSession) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        HashSet<StubTypesBasedInferenceSession<?>> nestedInferenceSessions2 = getNestedInferenceSessions();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : nestedInferenceSessions2) {
            if (obj2 instanceof DelegateInferenceSession) {
                arrayList3.add(obj2);
            }
        }
        ArrayList arrayList4 = arrayList2;
        ArrayList arrayList5 = arrayList3;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it = arrayList5.iterator();
        while (it.hasNext()) {
            arrayList6.add(((DelegateInferenceSession) it.next()).getNestedBuilderInferenceSessions());
        }
        return CollectionsKt.plus((Collection) arrayList4, (Iterable) CollectionsKt.flatten(arrayList6));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.StubTypesBasedInferenceSession
    public void prepareForCompletion(NewConstraintSystem commonSystem, List<PSIPartialCallInfo> resolvedCallsInfo) {
        Intrinsics.checkNotNullParameter(commonSystem, "commonSystem");
        Intrinsics.checkNotNullParameter(resolvedCallsInfo, "resolvedCallsInfo");
        ConstraintSystemBuilder builder = commonSystem.getBuilder();
        Iterator<PSIPartialCallInfo> it = resolvedCallsInfo.iterator();
        while (it.hasNext()) {
            ResolvedCallAtom resultCallAtom = it.next().getCallResolutionResult().getResultCallAtom();
            Name name2 = resultCallAtom.get_candidateDescriptor().getShortName();
            if (Intrinsics.areEqual(name2, OperatorNameConventions.GET_VALUE)) {
                addConstraintsForGetValueMethod(resultCallAtom, builder);
            } else if (Intrinsics.areEqual(name2, OperatorNameConventions.SET_VALUE)) {
                addConstraintsForSetValueMethod(resultCallAtom, builder);
            }
        }
    }

    private final void addConstraintForThis(ResolvedCallAtom resolvedCallAtom, CallableDescriptor callableDescriptor, ConstraintSystemBuilder constraintSystemBuilder) {
        SimpleType type;
        ReceiverParameterDescriptor extensionReceiverParameter = this.variableDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter == null || (type = extensionReceiverParameter.getType()) == null) {
            ReceiverParameterDescriptor dispatchReceiverParameter = this.variableDescriptor.mo6753getDispatchReceiverParameter();
            type = dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null;
            if (type == null) {
                type = getBuiltIns().getNullableNothingType();
            }
        }
        Intrinsics.checkNotNull(type);
        List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.getOrNull(valueParameters, 0);
        if (valueParameterDescriptor == null) {
            return;
        }
        constraintSystemBuilder.addSubtypeConstraint(type.unwrap(), resolvedCallAtom.getFreshVariablesSubstitutor().safeSubstitute(valueParameterDescriptor.getType().unwrap()), new DelegatedPropertyConstraintPositionImpl(resolvedCallAtom.getAtom()));
    }

    private final void addConstraintsForGetValueMethod(ResolvedCallAtom resolvedCallAtom, ConstraintSystemBuilder constraintSystemBuilder) {
        UnwrappedType unwrappedTypeUnwrap;
        if (this.expectedType != null) {
            KotlinType returnType = resolvedCallAtom.get_candidateDescriptor().getReturnType();
            if (returnType == null || (unwrappedTypeUnwrap = returnType.unwrap()) == null) {
                return;
            } else {
                constraintSystemBuilder.addSubtypeConstraint(resolvedCallAtom.getFreshVariablesSubstitutor().safeSubstitute(unwrappedTypeUnwrap), this.expectedType, new DelegatedPropertyConstraintPositionImpl(resolvedCallAtom.getAtom()));
            }
        }
        addConstraintForThis(resolvedCallAtom, resolvedCallAtom.get_candidateDescriptor(), constraintSystemBuilder);
    }

    private final void addConstraintsForSetValueMethod(ResolvedCallAtom resolvedCallAtom, ConstraintSystemBuilder constraintSystemBuilder) {
        KotlinType type;
        UnwrappedType unwrappedTypeUnwrap;
        if (this.expectedType != null) {
            List<ValueParameterDescriptor> valueParameters = resolvedCallAtom.get_candidateDescriptor().getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.getOrNull(valueParameters, 2);
            if (valueParameterDescriptor == null || (type = valueParameterDescriptor.getType()) == null || (unwrappedTypeUnwrap = type.unwrap()) == null) {
                return;
            } else {
                constraintSystemBuilder.addSubtypeConstraint(this.expectedType, resolvedCallAtom.getFreshVariablesSubstitutor().safeSubstitute(unwrappedTypeUnwrap), new DelegatedPropertyConstraintPositionImpl(resolvedCallAtom.getAtom()));
            }
        }
        addConstraintForThis(resolvedCallAtom, resolvedCallAtom.get_candidateDescriptor(), constraintSystemBuilder);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.InferenceSession
    public Map<TypeConstructor, UnwrappedType> inferPostponedVariables(ResolvedLambdaAtom lambda, ConstraintSystemBuilder constraintSystemBuilder, ConstraintSystemCompletionMode completionMode, KotlinDiagnosticsHolder diagnosticsHolder) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "constraintSystemBuilder");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        return MapsKt.emptyMap();
    }
}
