package org.jetbrains.kotlin.resolve.calls.model;

import androidx.core.app.NotificationCompat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tasks.OldResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.tasks.TracingStrategy;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.CastImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: loaded from: classes6.dex */
public class ResolvedCallImpl<D extends CallableDescriptor> implements MutableResolvedCall<D> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) ResolvedCallImpl.class);
    private final Map<ValueArgument, ArgumentMatchImpl> argumentToParameterMap;
    private final Call call;
    private final D candidateDescriptor;
    private boolean completed;
    private ConstraintSystem constraintSystem;
    private final MutableDataFlowInfoForArguments dataFlowInfoForArguments;
    private ReceiverValue dispatchReceiver;
    private final ExplicitReceiverKind explicitReceiverKind;
    private ReceiverValue extensionReceiver;
    private Boolean hasInferredReturnType;
    private final TypeSubstitutor knownTypeParametersSubstitutor;
    private Queue<Function0<Unit>> remainingTasks;
    private D resultingDescriptor;
    private KotlinType smartCastDispatchReceiverType;
    private ResolutionStatus status;
    private DelegatingBindingTrace trace;
    private TracingStrategy tracing;
    private Map<TypeParameterDescriptor, KotlinType> typeArguments;
    private final Map<ValueParameterDescriptor, ResolvedValueArgument> valueArguments;

    /* JADX WARN: Code duplicated, block: B:8:0x0010 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 14) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 33:
                                case 34:
                                case 35:
                                case 36:
                                    break;
                                default:
                                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                    break;
                            }
                        case 27:
                        case 28:
                        case 29:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 14) {
            switch (i) {
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 27:
                        case 28:
                        case 29:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 33:
                                case 34:
                                case 35:
                                case 36:
                                    i2 = 2;
                                    break;
                                default:
                                    i2 = 3;
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 5:
            case 11:
                objArr[0] = "trace";
                break;
            case 2:
            case 6:
            case 12:
                objArr[0] = "tracing";
                break;
            case 3:
            case 7:
            case 13:
                objArr[0] = "dataFlowInfoForArguments";
                break;
            case 4:
            default:
                objArr[0] = "candidate";
                break;
            case 8:
                objArr[0] = "call";
                break;
            case 9:
                objArr[0] = "candidateDescriptor";
                break;
            case 10:
                objArr[0] = "explicitReceiverKind";
                break;
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 27:
            case 28:
            case 29:
            case 33:
            case 34:
            case 35:
            case 36:
                objArr[0] = "org/jetbrains/kotlin/resolve/calls/model/ResolvedCallImpl";
                break;
            case 15:
                objArr[0] = NotificationCompat.CATEGORY_STATUS;
                break;
            case 21:
            case 22:
            case 23:
                objArr[0] = "substitutor";
                break;
            case 24:
                objArr[0] = "constraintSystem";
                break;
            case 25:
                objArr[0] = "valueParameter";
                break;
            case 26:
            case 30:
            case 32:
                objArr[0] = "valueArgument";
                break;
            case 31:
                objArr[0] = "matchStatus";
                break;
            case 37:
                objArr[0] = "smartCastDispatchReceiverType";
                break;
            case 38:
                objArr[0] = "smartCastExtensionReceiverType";
                break;
        }
        if (i != 14) {
            switch (i) {
                case 16:
                    objArr[1] = "getTrace";
                    break;
                case 17:
                    objArr[1] = "getTracingStrategy";
                    break;
                case 18:
                    objArr[1] = "getCall";
                    break;
                case 19:
                    objArr[1] = "getCandidateDescriptor";
                    break;
                case 20:
                    objArr[1] = "getResultingDescriptor";
                    break;
                default:
                    switch (i) {
                        case 27:
                            objArr[1] = "getContextReceivers";
                            break;
                        case 28:
                            objArr[1] = "getExplicitReceiverKind";
                            break;
                        case 29:
                            objArr[1] = "getValueArguments";
                            break;
                        default:
                            switch (i) {
                                case 33:
                                case 34:
                                    objArr[1] = "getArgumentMapping";
                                    break;
                                case 35:
                                    objArr[1] = "getTypeArguments";
                                    break;
                                case 36:
                                    objArr[1] = "getDataFlowInfoForArguments";
                                    break;
                                default:
                                    objArr[1] = "org/jetbrains/kotlin/resolve/calls/model/ResolvedCallImpl";
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "getStatus";
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "<init>";
                break;
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 27:
            case 28:
            case 29:
            case 33:
            case 34:
            case 35:
            case 36:
                break;
            case 15:
                objArr[2] = "addStatus";
                break;
            case 21:
                objArr[2] = "setResultingSubstitutor";
                break;
            case 22:
                objArr[2] = "setResolvedCallSubstitutor";
                break;
            case 23:
                objArr[2] = "setSubstitutor";
                break;
            case 24:
                objArr[2] = "setConstraintSystem";
                break;
            case 25:
            case 26:
                objArr[2] = "recordValueArgument";
                break;
            case 30:
            case 31:
                objArr[2] = "recordArgumentMatchStatus";
                break;
            case 32:
                objArr[2] = "getArgumentMapping";
                break;
            case 37:
                objArr[2] = "setSmartCastDispatchReceiverType";
                break;
            case 38:
                objArr[2] = "updateExtensionReceiverWithSmartCastIfNeeded";
                break;
            default:
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 14) {
            switch (i) {
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                    break;
                default:
                    switch (i) {
                        case 27:
                        case 28:
                        case 29:
                            break;
                        default:
                            switch (i) {
                                case 33:
                                case 34:
                                case 35:
                                case 36:
                                    break;
                                default:
                                    throw new IllegalArgumentException(str2);
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    private void assertNotCompleted(String str) {
    }

    public static <D extends CallableDescriptor> ResolvedCallImpl<D> create(OldResolutionCandidate<D> oldResolutionCandidate, DelegatingBindingTrace delegatingBindingTrace, TracingStrategy tracingStrategy, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments) {
        if (oldResolutionCandidate == null) {
            $$$reportNull$$$0(0);
        }
        if (delegatingBindingTrace == null) {
            $$$reportNull$$$0(1);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(2);
        }
        if (mutableDataFlowInfoForArguments == null) {
            $$$reportNull$$$0(3);
        }
        return new ResolvedCallImpl<>(oldResolutionCandidate, delegatingBindingTrace, tracingStrategy, mutableDataFlowInfoForArguments);
    }

    private ResolvedCallImpl(OldResolutionCandidate<D> oldResolutionCandidate, DelegatingBindingTrace delegatingBindingTrace, TracingStrategy tracingStrategy, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments) {
        if (oldResolutionCandidate == null) {
            $$$reportNull$$$0(4);
        }
        if (delegatingBindingTrace == null) {
            $$$reportNull$$$0(5);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(6);
        }
        if (mutableDataFlowInfoForArguments == null) {
            $$$reportNull$$$0(7);
        }
        this.status = ResolutionStatus.UNKNOWN_STATUS;
        this.constraintSystem = null;
        this.hasInferredReturnType = null;
        this.completed = false;
        this.smartCastDispatchReceiverType = null;
        this.remainingTasks = null;
        this.call = oldResolutionCandidate.getCall();
        D d = (D) oldResolutionCandidate.getDescriptor();
        this.candidateDescriptor = d;
        this.dispatchReceiver = oldResolutionCandidate.getDispatchReceiver();
        this.extensionReceiver = null;
        this.explicitReceiverKind = oldResolutionCandidate.getExplicitReceiverKind();
        this.knownTypeParametersSubstitutor = oldResolutionCandidate.getKnownTypeParametersResultingSubstitutor();
        this.trace = delegatingBindingTrace;
        this.tracing = tracingStrategy;
        this.dataFlowInfoForArguments = mutableDataFlowInfoForArguments;
        this.typeArguments = createTypeArgumentsMap(d);
        this.valueArguments = createValueArgumentsMap(d);
        this.argumentToParameterMap = createArgumentsToParameterMap(d);
    }

    public ResolvedCallImpl(Call call, D d, ReceiverValue receiverValue, ReceiverValue receiverValue2, ExplicitReceiverKind explicitReceiverKind, TypeSubstitutor typeSubstitutor, DelegatingBindingTrace delegatingBindingTrace, TracingStrategy tracingStrategy, MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments) {
        if (call == null) {
            $$$reportNull$$$0(8);
        }
        if (d == null) {
            $$$reportNull$$$0(9);
        }
        if (explicitReceiverKind == null) {
            $$$reportNull$$$0(10);
        }
        if (delegatingBindingTrace == null) {
            $$$reportNull$$$0(11);
        }
        if (tracingStrategy == null) {
            $$$reportNull$$$0(12);
        }
        if (mutableDataFlowInfoForArguments == null) {
            $$$reportNull$$$0(13);
        }
        this.status = ResolutionStatus.UNKNOWN_STATUS;
        this.constraintSystem = null;
        this.hasInferredReturnType = null;
        this.completed = false;
        this.smartCastDispatchReceiverType = null;
        this.remainingTasks = null;
        this.call = call;
        this.candidateDescriptor = d;
        this.dispatchReceiver = receiverValue;
        this.extensionReceiver = receiverValue2;
        this.explicitReceiverKind = explicitReceiverKind;
        this.knownTypeParametersSubstitutor = typeSubstitutor;
        this.trace = delegatingBindingTrace;
        this.tracing = tracingStrategy;
        this.dataFlowInfoForArguments = mutableDataFlowInfoForArguments;
        this.typeArguments = createTypeArgumentsMap(d);
        this.valueArguments = createValueArgumentsMap(d);
        this.argumentToParameterMap = createArgumentsToParameterMap(d);
    }

    private static Map<ValueParameterDescriptor, ResolvedValueArgument> createValueArgumentsMap(CallableDescriptor callableDescriptor) {
        return callableDescriptor.getValueParameters().isEmpty() ? Collections.emptyMap() : new LinkedHashMap();
    }

    private static Map<ValueArgument, ArgumentMatchImpl> createArgumentsToParameterMap(CallableDescriptor callableDescriptor) {
        return callableDescriptor.getValueParameters().isEmpty() ? Collections.emptyMap() : new HashMap();
    }

    private static Map<TypeParameterDescriptor, KotlinType> createTypeArgumentsMap(CallableDescriptor callableDescriptor) {
        return callableDescriptor.getTypeParameters().isEmpty() ? Collections.emptyMap() : new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ResolutionStatus getStatus() {
        ResolutionStatus resolutionStatus = this.status;
        if (resolutionStatus == null) {
            $$$reportNull$$$0(14);
        }
        return resolutionStatus;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void addStatus(ResolutionStatus resolutionStatus) {
        if (resolutionStatus == null) {
            $$$reportNull$$$0(15);
        }
        this.status = this.status.combine(resolutionStatus);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void setStatusToSuccess() {
        this.status = ResolutionStatus.SUCCESS;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public DelegatingBindingTrace getTrace() {
        assertNotCompleted("Trace");
        DelegatingBindingTrace delegatingBindingTrace = this.trace;
        if (delegatingBindingTrace == null) {
            $$$reportNull$$$0(16);
        }
        return delegatingBindingTrace;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public TracingStrategy getTracingStrategy() {
        assertNotCompleted("TracingStrategy");
        TracingStrategy tracingStrategy = this.tracing;
        if (tracingStrategy == null) {
            $$$reportNull$$$0(17);
        }
        return tracingStrategy;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Call getCall() {
        Call call = this.call;
        if (call == null) {
            $$$reportNull$$$0(18);
        }
        return call;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    /* JADX INFO: renamed from: getCandidateDescriptor */
    public D get$suspendPropertyDescriptor() {
        D d = this.candidateDescriptor;
        if (d == null) {
            $$$reportNull$$$0(19);
        }
        return d;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public D getResultingDescriptor() {
        D d = this.resultingDescriptor;
        if (d == null) {
            d = this.candidateDescriptor;
        }
        if (d == null) {
            $$$reportNull$$$0(20);
        }
        return d;
    }

    public void setResultingSubstitutor(TypeSubstitutor typeSubstitutor) {
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(21);
        }
        D d = this.resultingDescriptor;
        this.resultingDescriptor = (D) ((d == null || !DescriptorUtilsKt.shouldBeSubstituteWithStubTypes(d)) ? this.candidateDescriptor : this.resultingDescriptor).substitute(typeSubstitutor);
    }

    public void setResolvedCallSubstitutor(final TypeSubstitutor typeSubstitutor) {
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(22);
        }
        for (TypeParameterDescriptor typeParameterDescriptor : this.candidateDescriptor.getTypeParameters()) {
            TypeProjection typeProjectionMo7475get = typeSubstitutor.getSubstitution().mo7475get(typeParameterDescriptor.getDefaultType());
            if (typeProjectionMo7475get != null) {
                this.typeArguments.put(typeParameterDescriptor, typeProjectionMo7475get.getNullableAnyType());
            }
        }
        this.typeArguments = (Map) this.typeArguments.entrySet().stream().collect(Collectors.toMap(new Function() { // from class: org.jetbrains.kotlin.resolve.calls.model.ResolvedCallImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (TypeParameterDescriptor) ((Map.Entry) obj).getKey();
            }
        }, new Function() { // from class: org.jetbrains.kotlin.resolve.calls.model.ResolvedCallImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                return typeSubstitutor.safeSubstitute((KotlinType) entry.getValue(), ((TypeParameterDescriptor) entry.getKey()).getVariance());
            }
        }));
        ReceiverValue receiverValue = this.dispatchReceiver;
        if (receiverValue instanceof ExpressionReceiver) {
            this.dispatchReceiver = receiverValue.mo7466replaceType(typeSubstitutor.safeSubstitute(receiverValue.getType(), Variance.IN_VARIANCE));
        }
        ReceiverValue receiverValue2 = this.extensionReceiver;
        if (receiverValue2 instanceof ExtensionReceiver) {
            this.extensionReceiver = receiverValue2.mo7466replaceType(typeSubstitutor.safeSubstitute(receiverValue2.getType(), Variance.IN_VARIANCE));
        }
        if (this.candidateDescriptor.getValueParameters().isEmpty()) {
            return;
        }
        List<ValueParameterDescriptor> valueParameters = this.resultingDescriptor.getValueParameters();
        SmartList<Map.Entry> smartList = new SmartList((Collection) this.valueArguments.entrySet());
        this.valueArguments.clear();
        for (Map.Entry entry : smartList) {
            this.valueArguments.put(valueParameters.get(((ValueParameterDescriptor) entry.getKey()).getIndex()), (ResolvedValueArgument) entry.getValue());
        }
        SmartList<Map.Entry> smartList2 = new SmartList((Collection) this.argumentToParameterMap.entrySet());
        this.argumentToParameterMap.clear();
        for (Map.Entry entry2 : smartList2) {
            ArgumentMatchImpl argumentMatchImpl = (ArgumentMatchImpl) entry2.getValue();
            ValueParameterDescriptor valueParameterDescriptor = valueParameters.get(argumentMatchImpl.getValueParameter().getIndex());
            this.argumentToParameterMap.put((ValueArgument) entry2.getKey(), argumentMatchImpl.replaceValueParameter(valueParameterDescriptor));
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void setSubstitutor(TypeSubstitutor typeSubstitutor) {
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(23);
        }
        setResultingSubstitutor(typeSubstitutor);
        setResolvedCallSubstitutor(typeSubstitutor);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void setConstraintSystem(ConstraintSystem constraintSystem) {
        if (constraintSystem == null) {
            $$$reportNull$$$0(24);
        }
        this.constraintSystem = constraintSystem;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public ConstraintSystem getConstraintSystem() {
        assertNotCompleted("ConstraintSystem");
        return this.constraintSystem;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void recordValueArgument(ValueParameterDescriptor valueParameterDescriptor, ResolvedValueArgument resolvedValueArgument) {
        if (valueParameterDescriptor == null) {
            $$$reportNull$$$0(25);
        }
        if (resolvedValueArgument == null) {
            $$$reportNull$$$0(26);
        }
        this.valueArguments.put(valueParameterDescriptor, resolvedValueArgument);
        Iterator<ValueArgument> it = resolvedValueArgument.getArguments().iterator();
        while (it.hasNext()) {
            this.argumentToParameterMap.put(it.next(), new ArgumentMatchImpl(valueParameterDescriptor));
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ReceiverValue getExtensionReceiver() {
        return this.extensionReceiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ReceiverValue getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public List<ReceiverValue> getContextReceivers() {
        List<ReceiverValue> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(27);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ExplicitReceiverKind getExplicitReceiverKind() {
        ExplicitReceiverKind explicitReceiverKind = this.explicitReceiverKind;
        if (explicitReceiverKind == null) {
            $$$reportNull$$$0(28);
        }
        return explicitReceiverKind;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Map<ValueParameterDescriptor, ResolvedValueArgument> getValueArguments() {
        Map<ValueParameterDescriptor, ResolvedValueArgument> map = this.valueArguments;
        if (map == null) {
            $$$reportNull$$$0(29);
        }
        return map;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public List<ResolvedValueArgument> getValueArgumentsByIndex() {
        ArrayList arrayList = new ArrayList(this.candidateDescriptor.getValueParameters().size());
        for (int i = 0; i < this.candidateDescriptor.getValueParameters().size(); i++) {
            arrayList.add(null);
        }
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : this.valueArguments.entrySet()) {
            if (((ResolvedValueArgument) arrayList.set(entry.getKey().getIndex(), entry.getValue())) != null) {
                return null;
            }
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (arrayList.get(i2) == null) {
                return null;
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void recordArgumentMatchStatus(ValueArgument valueArgument, ArgumentMatchStatus argumentMatchStatus) {
        if (valueArgument == null) {
            $$$reportNull$$$0(30);
        }
        if (argumentMatchStatus == null) {
            $$$reportNull$$$0(31);
        }
        this.argumentToParameterMap.get(valueArgument).recordMatchStatus(argumentMatchStatus);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ArgumentMapping getArgumentMapping(ValueArgument valueArgument) {
        if (valueArgument == null) {
            $$$reportNull$$$0(32);
        }
        ArgumentMatchImpl argumentMatchImpl = this.argumentToParameterMap.get(valueArgument);
        if (argumentMatchImpl != null) {
            if (argumentMatchImpl == null) {
                $$$reportNull$$$0(34);
            }
            return argumentMatchImpl;
        }
        if (ArgumentMappingKt.isReallySuccess(this)) {
            LOG.error("ArgumentUnmapped for " + valueArgument + " in successfully resolved call: " + this.call.getCallElement().getText());
        }
        ArgumentUnmapped argumentUnmapped = ArgumentUnmapped.INSTANCE;
        if (argumentUnmapped == null) {
            $$$reportNull$$$0(33);
        }
        return argumentUnmapped;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Map<TypeParameterDescriptor, KotlinType> getTypeArguments() {
        Map<TypeParameterDescriptor, KotlinType> map = this.typeArguments;
        if (map == null) {
            $$$reportNull$$$0(35);
        }
        return map;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public MutableDataFlowInfoForArguments getDataFlowInfoForArguments() {
        MutableDataFlowInfoForArguments mutableDataFlowInfoForArguments = this.dataFlowInfoForArguments;
        if (mutableDataFlowInfoForArguments == null) {
            $$$reportNull$$$0(36);
        }
        return mutableDataFlowInfoForArguments;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public boolean hasInferredReturnType() {
        if (!this.completed) {
            ConstraintSystem constraintSystem = this.constraintSystem;
            this.hasInferredReturnType = Boolean.valueOf(constraintSystem == null || CallResolverUtilKt.hasInferredReturnType(this.candidateDescriptor, constraintSystem));
        }
        return this.hasInferredReturnType.booleanValue();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void markCallAsCompleted() {
        if (!this.completed) {
            hasInferredReturnType();
        }
        this.trace = null;
        this.constraintSystem = null;
        this.tracing = null;
        this.completed = true;
        this.remainingTasks = null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void addRemainingTasks(Function0<Unit> function0) {
        if (this.remainingTasks == null) {
            this.remainingTasks = new ArrayDeque();
        }
        this.remainingTasks.add(function0);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void performRemainingTasks() {
        if (this.remainingTasks == null) {
            return;
        }
        while (!this.remainingTasks.isEmpty()) {
            this.remainingTasks.poll().invoke();
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public boolean isCompleted() {
        return this.completed;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public TypeSubstitutor getKnownTypeParametersSubstitutor() {
        return this.knownTypeParametersSubstitutor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void setSmartCastDispatchReceiverType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(37);
        }
        this.smartCastDispatchReceiverType = kotlinType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public KotlinType getSmartCastDispatchReceiverType() {
        return this.smartCastDispatchReceiverType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCall
    public void updateExtensionReceiverWithSmartCastIfNeeded(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(38);
        }
        if (this.extensionReceiver instanceof ImplicitClassReceiver) {
            this.extensionReceiver = new CastImplicitClassReceiver(((ImplicitClassReceiver) this.extensionReceiver).getClassDescriptor(), kotlinType);
        }
    }
}
