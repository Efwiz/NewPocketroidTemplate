package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.SyntheticMemberDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.DescriptorRelatedInferenceUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutorByConstructorMap;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatchImpl;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentUnmapped;
import org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleTypeArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: NewAbstractResolvedCall.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Ê\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0007\b\u0004¢\u0006\u0002\u0010\u0004J0\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u00062\u0006\u00104\u001a\u00020\u00022\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u0002030\u0006H&J\b\u00105\u001a\u00020\u0018H&J\u0014\u00106\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u0002030\u0006H\u0002J\u0010\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\fH\u0016J\b\u0010:\u001a\u00020;H\u0016J\b\u0010<\u001a\u00020=H\u0016J\"\u0010>\u001a\u0004\u0018\u00010?2\b\u0010@\u001a\u0004\u0018\u00010?2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020C0BH\u0002J\u0014\u0010D\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u0002030\u0006H\u0016J\u0010\u0010E\u001a\n\u0012\u0004\u0012\u000203\u0018\u00010BH\u0016J\u0006\u0010\u0017\u001a\u00020\u0018J\u0012\u0010F\u001a\u00020G2\b\u0010H\u001a\u0004\u0018\u00010?H&J\u0010\u0010I\u001a\u00020G2\b\u0010H\u001a\u0004\u0018\u00010?J\u0012\u0010J\u001a\u00020\u00022\b\u0010H\u001a\u0004\u0018\u00010?H\u0004J\u001e\u0010K\u001a\u00020G2\u0014\u0010L\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0018\u00010\u0006H\u0004J\u0016\u0010M\u001a\u00020G2\f\u0010N\u001a\b\u0012\u0004\u0012\u00020O0BH&J\u0010\u0010P\u001a\u00020G2\u0006\u0010Q\u001a\u00020OH&J\u0010\u0010R\u001a\u00020G2\u0006\u0010Q\u001a\u00020OH&J\u0015\u0010S\u001a\u00020G2\u0006\u0010T\u001a\u00020\"H\u0000¢\u0006\u0002\bUJ\u001c\u0010V\u001a\u00020G2\u0014\u0010W\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u000203\u0018\u00010\u0006J \u0010X\u001a\u00020\u0002*\u00020\u00022\b\u0010H\u001a\u0004\u0018\u00010?2\b\b\u0002\u0010Y\u001a\u00020\u0018H\u0002J\u0014\u0010Z\u001a\u00020[*\u00020O2\u0006\u0010\\\u001a\u00020CH\u0002R\u001e\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0010\u0010!\u001a\u0004\u0018\u00010\"X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\u00020\u0018X\u0094D¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0012\u0010&\u001a\u00020'X¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u0004\u0018\u00010+X¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0012\u0010.\u001a\u00020/X¦\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u001c\u00102\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u000203\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0001\u0003]^_¨\u0006`"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "()V", "argumentMappingByOriginal", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "getArgumentMappingByOriginal", "()Ljava/util/Map;", "argumentToParameterMap", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchImpl;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "getDiagnostics", "()Ljava/util/Collection;", "freshSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "getFreshSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "isCompleted", "", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "nonTrivialUpdatedResultInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "positionDependentApproximation", "getPositionDependentApproximation", "()Z", "psiKotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "getPsiKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "getResolvedCallAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/TypeApproximator;", "valueArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "resultingDescriptor", "containsOnlyOnlyInputTypesErrors", "createValueArguments", "getArgumentMapping", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMapping;", "valueArgument", "getCall", "Lorg/jetbrains/kotlin/psi/Call;", "getDataFlowInfoForArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/DataFlowInfoForArguments;", "getSubstitutorWithoutFlexibleTypes", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "currentSubstitutor", "explicitTypeArguments", "", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleTypeArgument;", "getValueArguments", "getValueArgumentsByIndex", "setResultingSubstitutor", "", "substitutor", "substituteReceivers", "substitutedResultingDescriptor", "updateArgumentsMapping", "newMapping", "updateContextReceiverTypes", "newTypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "updateDispatchReceiverType", "newType", "updateExtensionReceiverType", "updateResultingDataFlowInfo", "dataFlowInfo", "updateResultingDataFlowInfo$frontend", "updateValueArguments", "newValueArguments", "substituteInferredVariablesAndApproximate", "shouldApproximate", "withNullabilityFromExplicitTypeArgument", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "typeArgument", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewCallableReferenceResolvedCall;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolvedCallImpl;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewVariableAsFunctionResolvedCallImpl;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class NewAbstractResolvedCall<D extends CallableDescriptor> implements ResolvedCall<D> {
    private Map<ValueArgument, ArgumentMatchImpl> argumentToParameterMap;
    private boolean isCompleted;
    private DataFlowInfo nonTrivialUpdatedResultInfo;
    private final boolean positionDependentApproximation;
    private Map<ValueParameterDescriptor, ? extends ResolvedValueArgument> valueArguments;

    public /* synthetic */ NewAbstractResolvedCall(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Map<ValueArgument, ArgumentMatchImpl> argumentToParameterMap(CallableDescriptor resultingDescriptor, Map<ValueParameterDescriptor, ? extends ResolvedValueArgument> valueArguments);

    public abstract boolean containsOnlyOnlyInputTypesErrors();

    public abstract Map<ValueParameterDescriptor, ResolvedCallArgument> getArgumentMappingByOriginal();

    public abstract Collection<KotlinCallDiagnostic> getDiagnostics();

    public abstract FreshVariableNewTypeSubstitutor getFreshSubstitutor();

    public abstract KotlinCall getKotlinCall();

    public abstract LanguageVersionSettings getLanguageVersionSettings();

    public abstract PSIKotlinCall getPsiKotlinCall();

    public abstract ResolvedCallAtom getResolvedCallAtom();

    public abstract TypeApproximator getTypeApproximator();

    public abstract void setResultingSubstitutor(NewTypeSubstitutor substitutor);

    public abstract void updateContextReceiverTypes(List<? extends KotlinType> newTypes);

    public abstract void updateDispatchReceiverType(KotlinType newType);

    public abstract void updateExtensionReceiverType(KotlinType newType);

    private NewAbstractResolvedCall() {
    }

    protected boolean getPositionDependentApproximation() {
        return this.positionDependentApproximation;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Call getCall() {
        return getPsiKotlinCall().getPsiCall();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Map<ValueParameterDescriptor, ResolvedValueArgument> getValueArguments() {
        if (this.valueArguments == null) {
            this.valueArguments = createValueArguments();
        }
        Map map = this.valueArguments;
        Intrinsics.checkNotNull(map);
        return map;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public List<ResolvedValueArgument> getValueArgumentsByIndex() {
        ArrayList arrayList = new ArrayList(get$suspendPropertyDescriptor().getValueParameters().size());
        int size = get$suspendPropertyDescriptor().getValueParameters().size();
        for (int i = 0; i < size; i++) {
            arrayList.add(null);
        }
        for (Map.Entry<ValueParameterDescriptor, ResolvedValueArgument> entry : getValueArguments().entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            if (((ResolvedValueArgument) arrayList.set(key.getIndex(), entry.getValue())) != null) {
                return null;
            }
        }
        ArrayList arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                if (((ResolvedValueArgument) it.next()) == null) {
                    return null;
                }
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ArgumentMapping getArgumentMapping(ValueArgument valueArgument) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        if (this.argumentToParameterMap == null) {
            D resultingDescriptor = getResultingDescriptor();
            Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
            updateArgumentsMapping(argumentToParameterMap(resultingDescriptor, getValueArguments()));
        }
        Map<ValueArgument, ArgumentMatchImpl> map = this.argumentToParameterMap;
        Intrinsics.checkNotNull(map);
        ArgumentMatchImpl argumentMatchImpl = map.get(valueArgument);
        if (argumentMatchImpl == null) {
            argumentMatchImpl = ArgumentUnmapped.INSTANCE;
        }
        return argumentMatchImpl;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public DataFlowInfoForArguments getDataFlowInfoForArguments() {
        return new DataFlowInfoForArguments(this) { // from class: org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall.getDataFlowInfoForArguments.1
            final /* synthetic */ NewAbstractResolvedCall<D> this$0;

            {
                this.this$0 = this;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments
            public DataFlowInfo getResultInfo() {
                DataFlowInfo dataFlowInfo = ((NewAbstractResolvedCall) this.this$0).nonTrivialUpdatedResultInfo;
                return dataFlowInfo == null ? this.this$0.getPsiKotlinCall().getResultDataFlowInfo() : dataFlowInfo;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DataFlowInfoForArguments
            public DataFlowInfo getInfo(ValueArgument valueArgument) {
                KotlinCallArgument externalArgument;
                Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
                KotlinCall kotlinCall = this.this$0.getKotlinCall();
                PSIKotlinCallArgument psiCallArgument = (kotlinCall == null || (externalArgument = kotlinCall.getExternalArgument()) == null) ? null : NewCallArgumentsKt.getPsiCallArgument(externalArgument);
                if (Intrinsics.areEqual(psiCallArgument != null ? psiCallArgument.getValueArgument() : null, valueArgument)) {
                    return psiCallArgument.getDataFlowInfoAfterThisArgument();
                }
                DataFlowInfo info = this.this$0.getPsiKotlinCall().getDataFlowInfoForArguments().getInfo(valueArgument);
                Intrinsics.checkNotNullExpressionValue(info, "getInfo(...)");
                return info;
            }
        };
    }

    public final void updateValueArguments(Map<ValueParameterDescriptor, ? extends ResolvedValueArgument> newValueArguments) {
        this.valueArguments = newValueArguments;
    }

    public final void substituteReceivers(NewTypeSubstitutor substitutor) {
        KotlinType type;
        KotlinType type2;
        if (substitutor != null) {
            this.isCompleted = true;
            ReceiverValue dispatchReceiver = mo7304getDispatchReceiver();
            if (dispatchReceiver != null && (type2 = dispatchReceiver.getType()) != null) {
                updateDispatchReceiverType(substitutor.safeSubstitute(type2.unwrap()));
            }
            ReceiverValue extensionReceiver = mo7305getExtensionReceiver();
            if (extensionReceiver == null || (type = extensionReceiver.getType()) == null) {
                return;
            }
            updateExtensionReceiverType(substitutor.safeSubstitute(type.unwrap()));
        }
    }

    /* JADX INFO: renamed from: isCompleted, reason: from getter */
    public final boolean getIsCompleted() {
        return this.isCompleted;
    }

    public final void updateResultingDataFlowInfo$frontend(DataFlowInfo dataFlowInfo) {
        Intrinsics.checkNotNullParameter(dataFlowInfo, "dataFlowInfo");
        if (Intrinsics.areEqual(dataFlowInfo, DataFlowInfo.INSTANCE.getEMPTY())) {
            return;
        }
        this.nonTrivialUpdatedResultInfo = dataFlowInfo.and(getPsiKotlinCall().getResultDataFlowInfo());
    }

    protected final void updateArgumentsMapping(Map<ValueArgument, ArgumentMatchImpl> newMapping) {
        this.argumentToParameterMap = newMapping;
    }

    protected final CallableDescriptor substitutedResultingDescriptor(NewTypeSubstitutor substitutor) {
        ArrayList arrayListEmptyList;
        KotlinCall atom;
        List<TypeArgument> typeArguments;
        D candidateDescriptor = get$suspendPropertyDescriptor();
        Intrinsics.checkNotNullExpressionValue(candidateDescriptor, "getCandidateDescriptor(...)");
        if (candidateDescriptor instanceof ClassConstructorDescriptor ? true : candidateDescriptor instanceof SyntheticMemberDescriptor) {
            ResolvedCallAtom resolvedCallAtom = getResolvedCallAtom();
            if (resolvedCallAtom == null || (atom = resolvedCallAtom.getAtom()) == null || (typeArguments = atom.getTypeArguments()) == null) {
                arrayListEmptyList = CollectionsKt.emptyList();
            } else {
                ArrayList arrayList = new ArrayList();
                for (Object obj : typeArguments) {
                    if (obj instanceof SimpleTypeArgument) {
                        arrayList.add(obj);
                    }
                }
                arrayListEmptyList = arrayList;
            }
            return substituteInferredVariablesAndApproximate$default(this, candidateDescriptor, getSubstitutorWithoutFlexibleTypes(substitutor, arrayListEmptyList), false, 2, null);
        }
        if (candidateDescriptor instanceof FunctionDescriptor) {
            return substituteInferredVariablesAndApproximate(candidateDescriptor, substitutor, ResolvedCallUtilKt.isNotSimpleCall(candidateDescriptor));
        }
        return ((candidateDescriptor instanceof PropertyDescriptor) && ResolvedCallUtilKt.isNotSimpleCall(candidateDescriptor)) ? substituteInferredVariablesAndApproximate$default(this, candidateDescriptor, substitutor, false, 2, null) : candidateDescriptor;
    }

    static /* synthetic */ CallableDescriptor substituteInferredVariablesAndApproximate$default(NewAbstractResolvedCall newAbstractResolvedCall, CallableDescriptor callableDescriptor, NewTypeSubstitutor newTypeSubstitutor, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: substituteInferredVariablesAndApproximate");
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return newAbstractResolvedCall.substituteInferredVariablesAndApproximate(callableDescriptor, newTypeSubstitutor, z);
    }

    private final CallableDescriptor substituteInferredVariablesAndApproximate(CallableDescriptor callableDescriptor, NewTypeSubstitutor newTypeSubstitutor, boolean z) {
        NewTypeSubstitutor knownParametersSubstitutor;
        CallableDescriptor callableDescriptorSubstitute;
        CallableDescriptor callableDescriptorSubstitute2;
        if (newTypeSubstitutor == null) {
            newTypeSubstitutor = FreshVariableNewTypeSubstitutor.INSTANCE.getEmpty();
        }
        FreshVariableNewTypeSubstitutor freshSubstitutor = getFreshSubstitutor();
        if (freshSubstitutor != null && (callableDescriptorSubstitute2 = DescriptorRelatedInferenceUtilsKt.substitute(callableDescriptor, freshSubstitutor)) != null) {
            callableDescriptor = callableDescriptorSubstitute2;
        }
        ResolvedCallAtom resolvedCallAtom = getResolvedCallAtom();
        if (resolvedCallAtom != null && (knownParametersSubstitutor = resolvedCallAtom.getKnownParametersSubstitutor()) != null && (callableDescriptorSubstitute = DescriptorRelatedInferenceUtilsKt.substitute(callableDescriptor, knownParametersSubstitutor)) != null) {
            callableDescriptor = callableDescriptorSubstitute;
        }
        return DescriptorRelatedInferenceUtilsKt.substituteAndApproximateTypes(callableDescriptor, newTypeSubstitutor, z ? getTypeApproximator() : null, getPositionDependentApproximation());
    }

    private final NewTypeSubstitutor getSubstitutorWithoutFlexibleTypes(NewTypeSubstitutor currentSubstitutor, List<? extends SimpleTypeArgument> explicitTypeArguments) {
        List<TypeVariableFromCallableDescriptor> freshVariables;
        TypeVariableTypeConstructor freshTypeConstructor;
        UnwrappedType unwrappedType;
        UnwrappedType unwrappedTypeWithNullabilityFromExplicitTypeArgument;
        if (!(currentSubstitutor instanceof NewTypeSubstitutorByConstructorMap) || explicitTypeArguments.isEmpty()) {
            return currentSubstitutor;
        }
        NewTypeSubstitutorByConstructorMap newTypeSubstitutorByConstructorMap = (NewTypeSubstitutorByConstructorMap) currentSubstitutor;
        Map<TypeConstructor, UnwrappedType> map = newTypeSubstitutorByConstructorMap.getMap();
        if (map.isEmpty()) {
            return currentSubstitutor;
        }
        Iterator<Map.Entry<TypeConstructor, UnwrappedType>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            if (FlexibleTypesKt.isFlexible(it.next().getValue())) {
                FreshVariableNewTypeSubstitutor freshSubstitutor = getFreshSubstitutor();
                if (freshSubstitutor == null || (freshVariables = freshSubstitutor.getFreshVariables()) == null) {
                    return null;
                }
                Map mutableMap = MapsKt.toMutableMap(newTypeSubstitutorByConstructorMap.getMap());
                int i = 0;
                for (Object obj : explicitTypeArguments) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    SimpleTypeArgument simpleTypeArgument = (SimpleTypeArgument) obj;
                    TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor = (TypeVariableFromCallableDescriptor) CollectionsKt.getOrNull(freshVariables, i);
                    if (typeVariableFromCallableDescriptor != null && (freshTypeConstructor = typeVariableFromCallableDescriptor.getFreshTypeConstructor()) != null && (unwrappedType = (UnwrappedType) mutableMap.get(freshTypeConstructor)) != null && (unwrappedTypeWithNullabilityFromExplicitTypeArgument = withNullabilityFromExplicitTypeArgument(unwrappedType, simpleTypeArgument)) != null) {
                        mutableMap.put(freshTypeConstructor, unwrappedTypeWithNullabilityFromExplicitTypeArgument);
                    }
                    i = i2;
                }
                return new NewTypeSubstitutorByConstructorMap(mutableMap);
            }
        }
        return currentSubstitutor;
    }

    private final UnwrappedType withNullabilityFromExplicitTypeArgument(KotlinType kotlinType, SimpleTypeArgument simpleTypeArgument) {
        return (simpleTypeArgument.getType().getIsMarkedNullable() ? TypeUtilsKt.makeNullable(kotlinType) : TypeUtilsKt.makeNotNullable(kotlinType)).unwrap();
    }

    private final Map<ValueParameterDescriptor, ResolvedValueArgument> createValueArguments() {
        ResolvedValueArgument expressionValueArgument;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        boolean zSupportsFeature = getLanguageVersionSettings().supportsFeature(LanguageFeature.UseCorrectExecutionOrderForVarargArguments);
        SmartList<Pair> smartList = null;
        for (Map.Entry<ValueParameterDescriptor, ResolvedCallArgument> entry : getArgumentMappingByOriginal().entrySet()) {
            ValueParameterDescriptor key = entry.getKey();
            ResolvedCallArgument value = entry.getValue();
            ValueParameterDescriptor valueParameterDescriptor = getResultingDescriptor().getValueParameters().get(key.getIndex());
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            Intrinsics.checkNotNull(valueParameterDescriptor);
            if (Intrinsics.areEqual(value, ResolvedCallArgument.DefaultArgument.INSTANCE)) {
                expressionValueArgument = DefaultValueArgument.DEFAULT;
            } else if (value instanceof ResolvedCallArgument.SimpleArgument) {
                ValueArgument valueArgument = NewCallArgumentsKt.getPsiCallArgument(((ResolvedCallArgument.SimpleArgument) value).getCallArgument()).getValueArgument();
                if (!ArgumentsUtilsKt.isVararg(valueParameterDescriptor)) {
                    expressionValueArgument = new ExpressionValueArgument(valueArgument);
                } else if (zSupportsFeature) {
                    VarargValueArgument varargValueArgument = new VarargValueArgument();
                    varargValueArgument.addArgument(valueArgument);
                    expressionValueArgument = varargValueArgument;
                } else {
                    VarargValueArgument varargValueArgument2 = new VarargValueArgument();
                    varargValueArgument2.addArgument(valueArgument);
                    if (smartList == null) {
                        smartList = new SmartList();
                    }
                    smartList.add(TuplesKt.to(valueParameterDescriptor, varargValueArgument2));
                }
            } else if (value instanceof ResolvedCallArgument.VarargArgument) {
                VarargValueArgument varargValueArgument3 = new VarargValueArgument();
                List<KotlinCallArgument> arguments = value.getArguments();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
                Iterator<T> it = arguments.iterator();
                while (it.hasNext()) {
                    arrayList.add(NewCallArgumentsKt.getPsiCallArgument((KotlinCallArgument) it.next()).getValueArgument());
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    varargValueArgument3.addArgument((ValueArgument) it2.next());
                }
                expressionValueArgument = varargValueArgument3;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            ResolvedValueArgument resolvedValueArgument = expressionValueArgument;
            Intrinsics.checkNotNull(resolvedValueArgument);
            linkedHashMap2.put(valueParameterDescriptor, resolvedValueArgument);
        }
        if (smartList != null && !zSupportsFeature) {
            for (Pair pair : smartList) {
                linkedHashMap.put((ValueParameterDescriptor) pair.component1(), (VarargValueArgument) pair.component2());
            }
        }
        return AddToStdlibKt.compactIfPossible(linkedHashMap);
    }
}
