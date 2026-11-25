package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.CapturedTypeFromSubtyping;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstrainingTypeIsError;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.IncorporationConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintError;
import org.jetbrains.kotlin.resolve.calls.inference.model.OnlyInputTypesDiagnostic;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatchImpl;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentMatchStatus;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnosticsKt;
import org.jetbrains.kotlin.resolve.calls.model.KotlinConstraintSystemDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.SamConversionDescription;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.resolve.scopes.receivers.CastImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: NewResolvedCallImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B5\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ0\u0010=\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020>0\u00112\u0006\u00106\u001a\u00020\u00022\u0012\u0010?\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020@0\u0011H\u0016J\b\u0010A\u001a\u00020BH\u0002J4\u0010C\u001a\u0010\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\"\u0018\u00010\u00112\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00020E\u0012\u0004\u0012\u00020\"0\u00112\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010F\u001a\u00020B2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010G\u001a\u00020B2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u0012\u0010H\u001a\u00020B2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\u001a\u0010I\u001a\u0014\u0012\u0004\u0012\u00020!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u001a0\u0011H\u0002J\b\u0010J\u001a\u00020KH\u0016J\u0010\u0010L\u001a\u0004\u0018\u00010\u00182\u0006\u0010M\u001a\u00020!J\r\u0010N\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010OJ\u000e\u0010P\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0016J\n\u0010Q\u001a\u0004\u0018\u00010\u001bH\u0016J\u0010\u0010R\u001a\u0004\u0018\u00010\"2\u0006\u0010M\u001a\u00020!J\u0010\u0010S\u001a\u0004\u0018\u00010\"2\u0006\u0010M\u001a\u00020!J\u0010\u0010T\u001a\u0004\u0018\u00010\"2\u0006\u0010M\u001a\u00020!J\b\u0010U\u001a\u00020VH\u0016J\n\u0010W\u001a\u0004\u0018\u00010\u001bH\u0016J\r\u0010X\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010OJ\n\u0010Y\u001a\u0004\u0018\u000109H\u0016J\b\u0010Z\u001a\u00020[H\u0016J\u0014\u0010\\\u001a\u000e\u0012\u0004\u0012\u00020]\u0012\u0004\u0012\u0002090\u0011H\u0016J\u0012\u0010^\u001a\u00020B2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u000e\u0010_\u001a\u00020B2\u0006\u00108\u001a\u000209J\u0016\u0010`\u001a\u00020B2\f\u0010a\u001a\b\u0012\u0004\u0012\u0002090\u001aH\u0016J\u0014\u0010b\u001a\u00020B2\f\u0010c\u001a\b\u0012\u0004\u0012\u00020\n0\tJ\u0010\u0010d\u001a\u00020B2\u0006\u0010e\u001a\u000209H\u0016J\u0010\u0010f\u001a\u00020B2\u0006\u0010e\u001a\u000209H\u0016J\u000e\u0010g\u001a\u00020B2\u0006\u0010h\u001a\u000209R \u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\t@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010 \u001a\u0010\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\"\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010#\u001a\u0010\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\"\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010$\u001a\u0010\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\"\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\u00020'8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020+X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0014\u00100\u001a\u000201X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u0010\u00106\u001a\u00028\u0000X\u0082.¢\u0006\u0004\n\u0002\u00107R\u0010\u00108\u001a\u0004\u0018\u000109X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u0010;R\u0014\u0010<\u001a\b\u0012\u0004\u0012\u00020\"0\u001aX\u0082.¢\u0006\u0002\n\u0000¨\u0006i"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NewResolvedCallImpl;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/tower/NewAbstractResolvedCall;", "resolvedCallAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "substitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;Ljava/util/Collection;Lorg/jetbrains/kotlin/types/TypeApproximator;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "argumentMappingByOriginal", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallArgument;", "getArgumentMappingByOriginal", "()Ljava/util/Map;", "argumentTypeForConstantConvertedMap", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/kotlin/resolve/constants/IntegerValueTypeConstant;", "contextReceivers", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "<set-?>", "getDiagnostics", "()Ljava/util/Collection;", "dispatchReceiver", "expectedTypeForSamConvertedArgumentMap", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "expectedTypeForSuspendConvertedArgumentMap", "expectedTypeForUnitConvertedArgumentMap", "extensionReceiver", "freshSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "getFreshSubstitutor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "psiKotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "getPsiKotlinCall", "()Lorg/jetbrains/kotlin/resolve/calls/tower/PSIKotlinCall;", "getResolvedCallAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallAtom;", "resultingDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "smartCastDispatchReceiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getTypeApproximator", "()Lorg/jetbrains/kotlin/types/TypeApproximator;", "typeArguments", "argumentToParameterMap", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchImpl;", "valueArguments", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedValueArgument;", "calculateExpectedTypeForConstantConvertedArgumentMap", "", "calculateExpectedTypeForConvertedArguments", "arguments", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "calculateExpectedTypeForSamConvertedArgumentMap", "calculateExpectedTypeForSuspendConvertedArgumentMap", "calculateExpectedTypeForUnitConvertedArgumentMap", "collectErrorPositions", "containsOnlyOnlyInputTypesErrors", "", "getArgumentTypeForConstantConvertedArgument", "valueArgument", "getCandidateDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getContextReceivers", "getDispatchReceiver", "getExpectedTypeForSamConvertedArgument", "getExpectedTypeForSuspendConvertedArgument", "getExpectedTypeForUnitConvertedArgument", "getExplicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getExtensionReceiver", "getResultingDescriptor", "getSmartCastDispatchReceiverType", "getStatus", "Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;", "getTypeArguments", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "setResultingSubstitutor", "setSmartCastDispatchReceiverType", "updateContextReceiverTypes", "newTypes", "updateDiagnostics", "completedDiagnostics", "updateDispatchReceiverType", "newType", "updateExtensionReceiverType", "updateExtensionReceiverWithSmartCastIfNeeded", "smartCastExtensionReceiverType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewResolvedCallImpl<D extends CallableDescriptor> extends NewAbstractResolvedCall<D> {
    private Map<KtExpression, IntegerValueTypeConstant> argumentTypeForConstantConvertedMap;
    private List<? extends ReceiverValue> contextReceivers;
    private Collection<? extends KotlinCallDiagnostic> diagnostics;
    private ReceiverValue dispatchReceiver;
    private Map<ValueArgument, ? extends UnwrappedType> expectedTypeForSamConvertedArgumentMap;
    private Map<ValueArgument, ? extends UnwrappedType> expectedTypeForSuspendConvertedArgumentMap;
    private Map<ValueArgument, ? extends UnwrappedType> expectedTypeForUnitConvertedArgumentMap;
    private ReceiverValue extensionReceiver;
    private final KotlinCall kotlinCall;
    private final LanguageVersionSettings languageVersionSettings;
    private final PSIKotlinCall psiKotlinCall;
    private final ResolvedCallAtom resolvedCallAtom;
    private D resultingDescriptor;
    private KotlinType smartCastDispatchReceiverType;
    private final TypeApproximator typeApproximator;
    private List<? extends UnwrappedType> typeArguments;

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public ResolvedCallAtom getResolvedCallAtom() {
        return this.resolvedCallAtom;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public TypeApproximator getTypeApproximator() {
        return this.typeApproximator;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NewResolvedCallImpl(ResolvedCallAtom resolvedCallAtom, NewTypeSubstitutor newTypeSubstitutor, Collection<? extends KotlinCallDiagnostic> diagnostics, TypeApproximator typeApproximator, LanguageVersionSettings languageVersionSettings) {
        ReceiverValueWithSmartCastInfo receiver;
        ReceiverValueWithSmartCastInfo receiver2;
        super(null);
        Intrinsics.checkNotNullParameter(resolvedCallAtom, "resolvedCallAtom");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        ReceiverValue receiverValue = null;
        this.resolvedCallAtom = resolvedCallAtom;
        this.typeApproximator = typeApproximator;
        this.languageVersionSettings = languageVersionSettings;
        this.psiKotlinCall = PSIKotlinCallsKt.getPsiKotlinCall(getResolvedCallAtom().getAtom());
        this.kotlinCall = getResolvedCallAtom().getAtom();
        this.diagnostics = diagnostics;
        SimpleKotlinCallArgument extensionReceiverArgument = getResolvedCallAtom().getExtensionReceiverArgument();
        this.extensionReceiver = (extensionReceiverArgument == null || (receiver2 = extensionReceiverArgument.getReceiver()) == null) ? null : receiver2.getReceiverValue();
        SimpleKotlinCallArgument dispatchReceiverArgument = getResolvedCallAtom().getDispatchReceiverArgument();
        if (dispatchReceiverArgument != null && (receiver = dispatchReceiverArgument.getReceiver()) != null) {
            receiverValue = receiver.getReceiverValue();
        }
        this.dispatchReceiver = receiverValue;
        List<SimpleKotlinCallArgument> contextReceiversArguments = getResolvedCallAtom().getContextReceiversArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceiversArguments, 10));
        Iterator<T> it = contextReceiversArguments.iterator();
        while (it.hasNext()) {
            arrayList.add(((SimpleKotlinCallArgument) it.next()).getReceiver().getReceiverValue());
        }
        this.contextReceivers = arrayList;
        setResultingSubstitutor(newTypeSubstitutor);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public PSIKotlinCall getPsiKotlinCall() {
        return this.psiKotlinCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public KotlinCall getKotlinCall() {
        return this.kotlinCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public FreshVariableNewTypeSubstitutor getFreshSubstitutor() {
        return getResolvedCallAtom().getFreshVariablesSubstitutor();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public Map<ValueParameterDescriptor, ResolvedCallArgument> getArgumentMappingByOriginal() {
        return getResolvedCallAtom().getArgumentMappingByOriginal();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public Collection<KotlinCallDiagnostic> getDiagnostics() {
        return this.diagnostics;
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
        return this.contextReceivers;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    /* JADX INFO: renamed from: getCandidateDescriptor */
    public D get$suspendPropertyDescriptor() {
        D d = (D) getResolvedCallAtom().get_candidateDescriptor();
        Intrinsics.checkNotNull(d, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl");
        return d;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public D getResultingDescriptor() {
        D d = this.resultingDescriptor;
        if (d != null) {
            return d;
        }
        Intrinsics.throwUninitializedPropertyAccessException("resultingDescriptor");
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ExplicitReceiverKind getExplicitReceiverKind() {
        return getResolvedCallAtom().getExplicitReceiverKind();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void updateDispatchReceiverType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        ReceiverValue receiverValue = this.dispatchReceiver;
        if (Intrinsics.areEqual(receiverValue != null ? receiverValue.getType() : null, newType)) {
            return;
        }
        ReceiverValue receiverValue2 = this.dispatchReceiver;
        this.dispatchReceiver = receiverValue2 != null ? receiverValue2.mo7466replaceType(newType) : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void updateExtensionReceiverType(KotlinType newType) {
        Intrinsics.checkNotNullParameter(newType, "newType");
        ReceiverValue receiverValue = this.extensionReceiver;
        if (Intrinsics.areEqual(receiverValue != null ? receiverValue.getType() : null, newType)) {
            return;
        }
        ReceiverValue receiverValue2 = this.extensionReceiver;
        this.extensionReceiver = receiverValue2 != null ? receiverValue2.mo7466replaceType(newType) : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void updateContextReceiverTypes(List<? extends KotlinType> newTypes) {
        Intrinsics.checkNotNullParameter(newTypes, "newTypes");
        if (this.contextReceivers.size() != newTypes.size()) {
            return;
        }
        List<Pair> listZip = CollectionsKt.zip(this.contextReceivers, newTypes);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listZip, 10));
        for (Pair pair : listZip) {
            arrayList.add(((ReceiverValue) pair.component1()).mo7466replaceType((KotlinType) pair.component2()));
        }
        this.contextReceivers = arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public ResolutionStatus getStatus() {
        return ResolvedCallUtilKt.toResolutionStatus(ImplicitScopeTowerKt.getResultApplicabilityForCallDiagnostics(getDiagnostics()));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public Map<TypeParameterDescriptor, KotlinType> getTypeArguments() {
        List<TypeParameterDescriptor> typeParameters = get$suspendPropertyDescriptor().getTypeParameters();
        Intrinsics.checkNotNull(typeParameters);
        List<? extends UnwrappedType> list = null;
        if (typeParameters.isEmpty()) {
            typeParameters = null;
        }
        if (typeParameters == null) {
            return MapsKt.emptyMap();
        }
        List<TypeParameterDescriptor> list2 = typeParameters;
        List<? extends UnwrappedType> list3 = this.typeArguments;
        if (list3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("typeArguments");
        } else {
            list = list3;
        }
        return MapsKt.toMap(CollectionsKt.zip(list2, list));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public boolean containsOnlyOnlyInputTypesErrors() {
        Collection<KotlinCallDiagnostic> diagnostics = getDiagnostics();
        if ((diagnostics instanceof Collection) && diagnostics.isEmpty()) {
            return true;
        }
        for (KotlinCallDiagnostic kotlinCallDiagnostic : diagnostics) {
            if (!(kotlinCallDiagnostic instanceof KotlinConstraintSystemDiagnostic) || !(((KotlinConstraintSystemDiagnostic) kotlinCallDiagnostic).getError() instanceof OnlyInputTypesDiagnostic)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
    public KotlinType getSmartCastDispatchReceiverType() {
        return this.smartCastDispatchReceiverType;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public void setResultingSubstitutor(NewTypeSubstitutor substitutor) {
        updateArgumentsMapping(null);
        updateValueArguments(null);
        substituteReceivers(substitutor);
        D d = (D) substitutedResultingDescriptor(substitutor);
        Intrinsics.checkNotNull(d, "null cannot be cast to non-null type D of org.jetbrains.kotlin.resolve.calls.tower.NewResolvedCallImpl");
        this.resultingDescriptor = d;
        List<TypeVariableFromCallableDescriptor> freshVariables = getFreshSubstitutor().getFreshVariables();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(freshVariables, 10));
        Iterator<T> it = freshVariables.iterator();
        while (it.hasNext()) {
            UnwrappedType unwrappedTypeSafeSubstitute = (substitutor == null ? FreshVariableNewTypeSubstitutor.INSTANCE.getEmpty() : substitutor).safeSubstitute(((TypeVariableFromCallableDescriptor) it.next()).getDefaultType());
            UnwrappedType unwrappedTypeApproximateToSuperType = getTypeApproximator().approximateToSuperType(unwrappedTypeSafeSubstitute, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.IntegerLiteralsTypesApproximation.INSTANCE);
            if (unwrappedTypeApproximateToSuperType != null) {
                unwrappedTypeSafeSubstitute = unwrappedTypeApproximateToSuperType;
            }
            arrayList.add(unwrappedTypeSafeSubstitute);
        }
        this.typeArguments = arrayList;
        calculateExpectedTypeForSamConvertedArgumentMap(substitutor);
        calculateExpectedTypeForSuspendConvertedArgumentMap(substitutor);
        calculateExpectedTypeForUnitConvertedArgumentMap(substitutor);
        calculateExpectedTypeForConstantConvertedArgumentMap();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall
    public Map<ValueArgument, ArgumentMatchImpl> argumentToParameterMap(CallableDescriptor resultingDescriptor, Map<ValueParameterDescriptor, ? extends ResolvedValueArgument> valueArguments) {
        ArgumentMatchStatus argumentMatchStatus;
        Intrinsics.checkNotNullParameter(resultingDescriptor, "resultingDescriptor");
        Intrinsics.checkNotNullParameter(valueArguments, "valueArguments");
        Map<ValueArgument, List<KotlinCallDiagnostic>> mapCollectErrorPositions = collectErrorPositions();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (ValueParameterDescriptor valueParameterDescriptor : resultingDescriptor.getValueParameters()) {
            ResolvedValueArgument resolvedValueArgument = valueArguments.get(valueParameterDescriptor);
            if (resolvedValueArgument != null) {
                for (ValueArgument valueArgument : resolvedValueArgument.getArguments()) {
                    if (mapCollectErrorPositions.get(valueArgument) == null || (argumentMatchStatus = ArgumentMatchStatus.TYPE_MISMATCH) == null) {
                        argumentMatchStatus = ArgumentMatchStatus.SUCCESS;
                    }
                    Intrinsics.checkNotNull(valueArgument);
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    ArgumentMatchImpl argumentMatchImpl = new ArgumentMatchImpl(valueParameterDescriptor);
                    argumentMatchImpl.recordMatchStatus(argumentMatchStatus);
                    linkedHashMap.put(valueArgument, argumentMatchImpl);
                }
            }
        }
        return linkedHashMap;
    }

    public final void updateExtensionReceiverWithSmartCastIfNeeded(KotlinType smartCastExtensionReceiverType) {
        Intrinsics.checkNotNullParameter(smartCastExtensionReceiverType, "smartCastExtensionReceiverType");
        if (this.extensionReceiver instanceof ImplicitClassReceiver) {
            ReceiverValue receiverValue = this.extensionReceiver;
            Intrinsics.checkNotNull(receiverValue, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver");
            this.extensionReceiver = new CastImplicitClassReceiver(((ImplicitClassReceiver) receiverValue).getClassDescriptor(), smartCastExtensionReceiverType);
        }
    }

    public final void setSmartCastDispatchReceiverType(KotlinType smartCastDispatchReceiverType) {
        Intrinsics.checkNotNullParameter(smartCastDispatchReceiverType, "smartCastDispatchReceiverType");
        this.smartCastDispatchReceiverType = smartCastDispatchReceiverType;
    }

    public final void updateDiagnostics(Collection<? extends KotlinCallDiagnostic> completedDiagnostics) {
        Intrinsics.checkNotNullParameter(completedDiagnostics, "completedDiagnostics");
        this.diagnostics = completedDiagnostics;
    }

    public final IntegerValueTypeConstant getArgumentTypeForConstantConvertedArgument(ValueArgument valueArgument) {
        Map<KtExpression, IntegerValueTypeConstant> map;
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        KtExpression argumentExpression = valueArgument.getArgumentExpression();
        if (argumentExpression == null || (map = this.argumentTypeForConstantConvertedMap) == null) {
            return null;
        }
        return map.get(argumentExpression);
    }

    public final UnwrappedType getExpectedTypeForSamConvertedArgument(ValueArgument valueArgument) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Map<ValueArgument, ? extends UnwrappedType> map = this.expectedTypeForSamConvertedArgumentMap;
        if (map != null) {
            return map.get(valueArgument);
        }
        return null;
    }

    public final UnwrappedType getExpectedTypeForSuspendConvertedArgument(ValueArgument valueArgument) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Map<ValueArgument, ? extends UnwrappedType> map = this.expectedTypeForSuspendConvertedArgumentMap;
        if (map != null) {
            return map.get(valueArgument);
        }
        return null;
    }

    public final UnwrappedType getExpectedTypeForUnitConvertedArgument(ValueArgument valueArgument) {
        Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
        Map<ValueArgument, ? extends UnwrappedType> map = this.expectedTypeForUnitConvertedArgumentMap;
        if (map != null) {
            return map.get(valueArgument);
        }
        return null;
    }

    private final Map<ValueArgument, UnwrappedType> calculateExpectedTypeForConvertedArguments(Map<KotlinCallArgument, ? extends UnwrappedType> arguments, NewTypeSubstitutor substitutor) {
        UnwrappedType unwrappedTypeSafeSubstitute;
        if (arguments.isEmpty()) {
            return null;
        }
        HashMap map = new HashMap();
        for (Map.Entry<KotlinCallArgument, ? extends UnwrappedType> entry : arguments.entrySet()) {
            KotlinCallArgument key = entry.getKey();
            UnwrappedType unwrappedTypeSafeSubstitute2 = getResolvedCallAtom().getFreshVariablesSubstitutor().safeSubstitute(entry.getValue());
            if (substitutor != null && (unwrappedTypeSafeSubstitute = substitutor.safeSubstitute(unwrappedTypeSafeSubstitute2)) != null) {
                unwrappedTypeSafeSubstitute2 = unwrappedTypeSafeSubstitute;
            }
            map.put(NewCallArgumentsKt.getPsiCallArgument(key).getValueArgument(), unwrappedTypeSafeSubstitute2);
        }
        return map;
    }

    private final void calculateExpectedTypeForConstantConvertedArgumentMap() {
        if (getResolvedCallAtom().getArgumentsWithConstantConversion().isEmpty()) {
            return;
        }
        HashMap map = new HashMap();
        for (Map.Entry<KotlinCallArgument, IntegerValueTypeConstant> entry : getResolvedCallAtom().getArgumentsWithConstantConversion().entrySet()) {
            KotlinCallArgument key = entry.getKey();
            IntegerValueTypeConstant value = entry.getValue();
            KtExpression psiExpression = NewCallArgumentsKt.getPsiExpression(key);
            if (psiExpression != null) {
                map.put(psiExpression, value);
            }
        }
        this.argumentTypeForConstantConvertedMap = map;
    }

    private final void calculateExpectedTypeForSamConvertedArgumentMap(NewTypeSubstitutor substitutor) {
        Map<KotlinCallArgument, SamConversionDescription> argumentsWithConversion = getResolvedCallAtom().getArgumentsWithConversion();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(argumentsWithConversion.size()));
        Iterator<T> it = argumentsWithConversion.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), ((SamConversionDescription) entry.getValue()).getConvertedTypeByCandidateParameter());
        }
        this.expectedTypeForSamConvertedArgumentMap = calculateExpectedTypeForConvertedArguments(linkedHashMap, substitutor);
    }

    private final void calculateExpectedTypeForSuspendConvertedArgumentMap(NewTypeSubstitutor substitutor) {
        this.expectedTypeForSuspendConvertedArgumentMap = calculateExpectedTypeForConvertedArguments(getResolvedCallAtom().getArgumentsWithSuspendConversion(), substitutor);
    }

    private final void calculateExpectedTypeForUnitConvertedArgumentMap(NewTypeSubstitutor substitutor) {
        this.expectedTypeForUnitConvertedArgumentMap = calculateExpectedTypeForConvertedArguments(getResolvedCallAtom().getArgumentsWithUnitConversion(), substitutor);
    }

    private final Map<ValueArgument, List<KotlinCallDiagnostic>> collectErrorPositions() {
        ConstraintPosition constraintPositionCollectErrorPositions$originalPosition;
        ValueArgument valueArgument;
        ArrayList<Pair> arrayList = new ArrayList();
        for (KotlinCallDiagnostic kotlinCallDiagnostic : getDiagnostics()) {
            ConstraintSystemError constraintSystemError = KotlinCallDiagnosticsKt.getConstraintSystemError(kotlinCallDiagnostic);
            if (constraintSystemError instanceof NewConstraintError) {
                constraintPositionCollectErrorPositions$originalPosition = collectErrorPositions$originalPosition(((NewConstraintError) constraintSystemError).getPosition());
            } else if (constraintSystemError instanceof CapturedTypeFromSubtyping) {
                constraintPositionCollectErrorPositions$originalPosition = collectErrorPositions$originalPosition(((CapturedTypeFromSubtyping) constraintSystemError).getPosition());
            } else {
                constraintPositionCollectErrorPositions$originalPosition = constraintSystemError instanceof ConstrainingTypeIsError ? collectErrorPositions$originalPosition(((ConstrainingTypeIsError) constraintSystemError).getPosition()) : null;
            }
            ArgumentConstraintPositionImpl argumentConstraintPositionImpl = constraintPositionCollectErrorPositions$originalPosition instanceof ArgumentConstraintPositionImpl ? (ArgumentConstraintPositionImpl) constraintPositionCollectErrorPositions$originalPosition : null;
            if (argumentConstraintPositionImpl != null) {
                KotlinCallArgument argument = argumentConstraintPositionImpl.getArgument();
                PSIKotlinCallArgument pSIKotlinCallArgument = argument instanceof PSIKotlinCallArgument ? (PSIKotlinCallArgument) argument : null;
                if (pSIKotlinCallArgument != null && (valueArgument = pSIKotlinCallArgument.getValueArgument()) != null) {
                    arrayList.add(TuplesKt.to(valueArgument, kotlinCallDiagnostic));
                }
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Pair pair : arrayList) {
            ValueArgument valueArgument2 = (ValueArgument) pair.getFirst();
            Object obj = linkedHashMap.get(valueArgument2);
            if (obj == null) {
                obj = (List) new ArrayList();
                linkedHashMap.put(valueArgument2, obj);
            }
            ((List) obj).add((KotlinCallDiagnostic) pair.getSecond());
        }
        return linkedHashMap;
    }

    private static final ConstraintPosition collectErrorPositions$originalPosition(ConstraintPosition constraintPosition) {
        return constraintPosition instanceof IncorporationConstraintPosition ? collectErrorPositions$originalPosition(((IncorporationConstraintPosition) constraintPosition).getFrom()) : constraintPosition;
    }
}
