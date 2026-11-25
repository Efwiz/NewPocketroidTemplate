package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirThisReceiverExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirExpressionStub;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponents;
import org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Candidate.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ô\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001Bk\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\u0010\n\u001a\u00060\u000bR\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0014¢\u0006\u0002\u0010\u0016J\u0010\u0010\u0089\u0001\u001a\u00020[2\u0007\u0010\u008a\u0001\u001a\u00020\u0019J\u0007\u0010\u008b\u0001\u001a\u00020\u0005J\f\u00104\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007J\u0007\u0010\u008c\u0001\u001a\u00020\u0005J\u0016\u0010\u008d\u0001\u001a\u00020\u00142\n\u0010\u008e\u0001\u001a\u0005\u0018\u00010\u008f\u0001H\u0096\u0002J\t\u0010\u0090\u0001\u001a\u00020QH\u0016J\n\u0010\u0091\u0001\u001a\u00030\u0092\u0001H\u0016J\u0007\u0010\u0093\u0001\u001a\u00020[J\r\u0010\u0094\u0001\u001a\u00020\u0005*\u00020\u0005H\u0002R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR:\u0010\u001e\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020 \u0018\u00010\u001fj\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020 \u0018\u0001`!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R(\u0010*\u001a\u0004\u0018\u00010)2\b\u0010(\u001a\u0004\u0018\u00010)@@X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001c\u0010/\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u0012\u0010\n\u001a\u00060\u000bR\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u00104\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001e\u0010:\u001a\u00020\u001b2\u0006\u00109\u001a\u00020\u001b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b;\u0010\u001dR\u001a\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00190\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u00106R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u00101\"\u0004\b?\u00103R\u001a\u0010@\u001a\b\u0012\u0004\u0012\u00020A0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u00106R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bC\u0010DR \u0010E\u001a\b\u0012\u0004\u0012\u00020F0\u0007X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bG\u00106\"\u0004\bH\u00108R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\bI\u00106R\u001a\u0010J\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010L\"\u0004\bM\u0010NR\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010LR\u0011\u0010\u0015\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010LR\u0011\u0010O\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\bO\u0010LR\u001a\u0010P\u001a\u00020QX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bR\u0010S\"\u0004\bT\u0010UR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\b\n\u0000\u001a\u0004\bV\u0010WR-\u0010X\u001a\u0015\u0012\u0004\u0012\u00020Z\u0012\u0004\u0012\u00020[\u0018\u00010Y¢\u0006\u0002\b\\X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010^\"\u0004\b_\u0010`R\u001a\u0010a\u001a\u00020QX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bb\u0010S\"\u0004\bc\u0010UR\u0017\u0010d\u001a\b\u0012\u0004\u0012\u00020e0\u0018¢\u0006\b\n\u0000\u001a\u0004\bf\u00106R\u001c\u0010g\u001a\u0004\u0018\u00010hX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010j\"\u0004\bk\u0010lR\u000e\u0010m\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010n\u001a\u00020oX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bp\u0010q\"\u0004\br\u0010sR\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bt\u0010uR\u001b\u0010v\u001a\u00020w8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bz\u0010{\u001a\u0004\bx\u0010yR\u000e\u0010|\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010}\u001a\u00020~X\u0086.¢\u0006\u0011\n\u0000\u001a\u0005\b\u007f\u0010\u0080\u0001\"\u0006\b\u0081\u0001\u0010\u0082\u0001R\u001d\u0010\u0083\u0001\u001a\u00020\u0014X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0084\u0001\u0010L\"\u0005\b\u0085\u0001\u0010NR\u001d\u0010\u0086\u0001\u001a\u00020\u0014X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0087\u0001\u0010L\"\u0005\b\u0088\u0001\u0010N¨\u0006\u0095\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "givenExtensionReceiverOptions", "", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "constraintSystemFactory", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents$ConstraintSystemFactory;", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "originScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "isFromCompanionObjectTypeScope", "", "isFromOriginalTypeInPresenceOfSmartCast", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents$ConstraintSystemFactory;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/scopes/FirScope;ZZ)V", "_diagnostics", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "getApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "argumentMapping", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lkotlin/collections/LinkedHashMap;", "getArgumentMapping", "()Ljava/util/LinkedHashMap;", "setArgumentMapping", "(Ljava/util/LinkedHashMap;)V", "getCallInfo", "()Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "value", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceAdaptation;", "callableReferenceAdaptation", "getCallableReferenceAdaptation$resolve", "()Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceAdaptation;", "setCallableReferenceAdaptation$resolve", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallableReferenceAdaptation;)V", "chosenExtensionReceiver", "getChosenExtensionReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "setChosenExtensionReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "contextReceiverArguments", "getContextReceiverArguments", "()Ljava/util/List;", "setContextReceiverArguments", "(Ljava/util/List;)V", "<set-?>", "currentApplicability", "getCurrentApplicability", "diagnostics", "getDiagnostics", "getDispatchReceiver", "setDispatchReceiver", "errors", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getErrors", "getExplicitReceiverKind", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "freshVariables", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "getFreshVariables", "setFreshVariables", "getGivenExtensionReceiverOptions", "hasVisibleBackingField", "getHasVisibleBackingField", "()Z", "setHasVisibleBackingField", "(Z)V", "isSuccessful", "numDefaults", "", "getNumDefaults", "()I", "setNumDefaults", "(I)V", "getOriginScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "outerConstraintBuilderEffect", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemOperation;", "", "Lkotlin/ExtensionFunctionType;", "getOuterConstraintBuilderEffect", "()Lkotlin/jvm/functions/Function1;", "setOuterConstraintBuilderEffect", "(Lkotlin/jvm/functions/Function1;)V", "passedStages", "getPassedStages", "setPassedStages", "postponedAtoms", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedResolvedAtom;", "getPostponedAtoms", "resultingTypeForCallableReference", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getResultingTypeForCallableReference", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "setResultingTypeForCallableReference", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "sourcesWereUpdated", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "getSubstitutor", "()Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "setSubstitutor", "(Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;)V", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "system", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "getSystem", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "system$delegate", "Lkotlin/Lazy;", "systemInitialized", "typeArgumentMapping", "Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping;", "getTypeArgumentMapping", "()Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping;", "setTypeArgumentMapping", "(Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping;)V", "usesFunctionConversion", "getUsesFunctionConversion", "setUsesFunctionConversion", "usesSAM", "getUsesSAM", "setUsesSAM", "addDiagnostic", "diagnostic", "chosenExtensionReceiverExpression", "dispatchReceiverExpression", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "toString", "", "updateSourcesOfReceivers", "tryToSetSourceForImplicitReceiver", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Candidate extends AbstractCandidate {
    private final List<ResolutionDiagnostic> _diagnostics;
    private LinkedHashMap<FirExpression, FirValueParameter> argumentMapping;
    private final ConstraintStorage baseSystem;
    private final CallInfo callInfo;
    private CallableReferenceAdaptation callableReferenceAdaptation;
    private FirExpression chosenExtensionReceiver;
    private final InferenceComponents.ConstraintSystemFactory constraintSystemFactory;
    private List<? extends FirExpression> contextReceiverArguments;
    private CandidateApplicability currentApplicability;
    private FirExpression dispatchReceiver;
    private final ExplicitReceiverKind explicitReceiverKind;
    public List<? extends ConeTypeVariable> freshVariables;
    private final List<FirExpression> givenExtensionReceiverOptions;
    private boolean hasVisibleBackingField;
    private final boolean isFromCompanionObjectTypeScope;
    private final boolean isFromOriginalTypeInPresenceOfSmartCast;
    private int numDefaults;
    private final FirScope originScope;
    private Function1<? super ConstraintSystemOperation, Unit> outerConstraintBuilderEffect;
    private int passedStages;
    private final List<PostponedResolvedAtom> postponedAtoms;
    private ConeKotlinType resultingTypeForCallableReference;
    private boolean sourcesWereUpdated;
    public ConeSubstitutor substitutor;
    private final FirBasedSymbol<?> symbol;

    /* JADX INFO: renamed from: system$delegate, reason: from kotlin metadata */
    private final Lazy system;
    private boolean systemInitialized;
    public TypeArgumentMapping typeArgumentMapping;
    private boolean usesFunctionConversion;
    private boolean usesSAM;

    public /* synthetic */ Candidate(FirBasedSymbol firBasedSymbol, FirExpression firExpression, List list, ExplicitReceiverKind explicitReceiverKind, InferenceComponents.ConstraintSystemFactory constraintSystemFactory, ConstraintStorage constraintStorage, CallInfo callInfo, FirScope firScope, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firBasedSymbol, firExpression, list, explicitReceiverKind, constraintSystemFactory, constraintStorage, callInfo, firScope, (i & 256) != 0 ? false : z, (i & 512) != 0 ? false : z2);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
    public FirBasedSymbol<?> getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
    public FirExpression getDispatchReceiver() {
        return this.dispatchReceiver;
    }

    public void setDispatchReceiver(FirExpression firExpression) {
        this.dispatchReceiver = firExpression;
    }

    public final List<FirExpression> getGivenExtensionReceiverOptions() {
        return this.givenExtensionReceiverOptions;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
    public ExplicitReceiverKind getExplicitReceiverKind() {
        return this.explicitReceiverKind;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
    public CallInfo getCallInfo() {
        return this.callInfo;
    }

    public final FirScope getOriginScope() {
        return this.originScope;
    }

    /* JADX INFO: renamed from: isFromCompanionObjectTypeScope, reason: from getter */
    public final boolean getIsFromCompanionObjectTypeScope() {
        return this.isFromCompanionObjectTypeScope;
    }

    /* JADX INFO: renamed from: isFromOriginalTypeInPresenceOfSmartCast, reason: from getter */
    public final boolean getIsFromOriginalTypeInPresenceOfSmartCast() {
        return this.isFromOriginalTypeInPresenceOfSmartCast;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Candidate(FirBasedSymbol<?> symbol, FirExpression firExpression, List<? extends FirExpression> givenExtensionReceiverOptions, ExplicitReceiverKind explicitReceiverKind, InferenceComponents.ConstraintSystemFactory constraintSystemFactory, ConstraintStorage baseSystem, CallInfo callInfo, FirScope firScope, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(givenExtensionReceiverOptions, "givenExtensionReceiverOptions");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        Intrinsics.checkNotNullParameter(constraintSystemFactory, "constraintSystemFactory");
        Intrinsics.checkNotNullParameter(baseSystem, "baseSystem");
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        this.symbol = symbol;
        this.dispatchReceiver = firExpression;
        this.givenExtensionReceiverOptions = givenExtensionReceiverOptions;
        this.explicitReceiverKind = explicitReceiverKind;
        this.constraintSystemFactory = constraintSystemFactory;
        this.baseSystem = baseSystem;
        this.callInfo = callInfo;
        this.originScope = firScope;
        this.isFromCompanionObjectTypeScope = z;
        this.isFromOriginalTypeInPresenceOfSmartCast = z2;
        this.system = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<NewConstraintSystemImpl>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.Candidate$system$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final NewConstraintSystemImpl invoke() {
                NewConstraintSystemImpl newConstraintSystemImplCreateConstraintSystem = this.this$0.constraintSystemFactory.createConstraintSystem();
                newConstraintSystemImplCreateConstraintSystem.addOtherSystem(this.this$0.baseSystem);
                this.this$0.systemInitialized = true;
                return newConstraintSystemImplCreateConstraintSystem;
            }
        });
        this.postponedAtoms = new ArrayList();
        this.currentApplicability = CandidateApplicability.RESOLVED;
        this.chosenExtensionReceiver = (FirExpression) CollectionsKt.singleOrNull((List) givenExtensionReceiverOptions);
        this._diagnostics = new ArrayList();
    }

    public final NewConstraintSystemImpl getSystem() {
        return (NewConstraintSystemImpl) this.system.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
    public List<ConstraintSystemError> getErrors() {
        return getSystem().getErrors();
    }

    public final ConeSubstitutor getSubstitutor() {
        ConeSubstitutor coneSubstitutor = this.substitutor;
        if (coneSubstitutor != null) {
            return coneSubstitutor;
        }
        Intrinsics.throwUninitializedPropertyAccessException("substitutor");
        return null;
    }

    public final void setSubstitutor(ConeSubstitutor coneSubstitutor) {
        Intrinsics.checkNotNullParameter(coneSubstitutor, "<set-?>");
        this.substitutor = coneSubstitutor;
    }

    public final List<ConeTypeVariable> getFreshVariables() {
        List list = this.freshVariables;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("freshVariables");
        return null;
    }

    public final void setFreshVariables(List<? extends ConeTypeVariable> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.freshVariables = list;
    }

    public final ConeKotlinType getResultingTypeForCallableReference() {
        return this.resultingTypeForCallableReference;
    }

    public final void setResultingTypeForCallableReference(ConeKotlinType coneKotlinType) {
        this.resultingTypeForCallableReference = coneKotlinType;
    }

    public final Function1<ConstraintSystemOperation, Unit> getOuterConstraintBuilderEffect() {
        return this.outerConstraintBuilderEffect;
    }

    public final void setOuterConstraintBuilderEffect(Function1<? super ConstraintSystemOperation, Unit> function1) {
        this.outerConstraintBuilderEffect = function1;
    }

    public final boolean getUsesSAM() {
        return this.usesSAM;
    }

    public final void setUsesSAM(boolean z) {
        this.usesSAM = z;
    }

    /* JADX INFO: renamed from: getCallableReferenceAdaptation$resolve, reason: from getter */
    public final CallableReferenceAdaptation getCallableReferenceAdaptation() {
        return this.callableReferenceAdaptation;
    }

    public final void setCallableReferenceAdaptation$resolve(CallableReferenceAdaptation callableReferenceAdaptation) {
        this.callableReferenceAdaptation = callableReferenceAdaptation;
        this.usesFunctionConversion = (callableReferenceAdaptation != null ? callableReferenceAdaptation.getSuspendConversionStrategy() : null) instanceof CallableReferenceConversionStrategy.CustomConversion;
        if (callableReferenceAdaptation != null) {
            this.numDefaults = callableReferenceAdaptation.getDefaults();
        }
    }

    public final boolean getUsesFunctionConversion() {
        return this.usesFunctionConversion;
    }

    public final void setUsesFunctionConversion(boolean z) {
        this.usesFunctionConversion = z;
    }

    public final LinkedHashMap<FirExpression, FirValueParameter> getArgumentMapping() {
        return this.argumentMapping;
    }

    public final void setArgumentMapping(LinkedHashMap<FirExpression, FirValueParameter> linkedHashMap) {
        this.argumentMapping = linkedHashMap;
    }

    public final int getNumDefaults() {
        return this.numDefaults;
    }

    public final void setNumDefaults(int i) {
        this.numDefaults = i;
    }

    public final TypeArgumentMapping getTypeArgumentMapping() {
        TypeArgumentMapping typeArgumentMapping = this.typeArgumentMapping;
        if (typeArgumentMapping != null) {
            return typeArgumentMapping;
        }
        Intrinsics.throwUninitializedPropertyAccessException("typeArgumentMapping");
        return null;
    }

    public final void setTypeArgumentMapping(TypeArgumentMapping typeArgumentMapping) {
        Intrinsics.checkNotNullParameter(typeArgumentMapping, "<set-?>");
        this.typeArgumentMapping = typeArgumentMapping;
    }

    public final List<PostponedResolvedAtom> getPostponedAtoms() {
        return this.postponedAtoms;
    }

    public final CandidateApplicability getCurrentApplicability() {
        return this.currentApplicability;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
    public FirExpression getChosenExtensionReceiver() {
        return this.chosenExtensionReceiver;
    }

    public void setChosenExtensionReceiver(FirExpression firExpression) {
        this.chosenExtensionReceiver = firExpression;
    }

    public final List<FirExpression> getContextReceiverArguments() {
        return this.contextReceiverArguments;
    }

    public final void setContextReceiverArguments(List<? extends FirExpression> list) {
        this.contextReceiverArguments = list;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
    /* JADX INFO: renamed from: getApplicability, reason: from getter */
    public CandidateApplicability getCurrentApplicability() {
        return this.currentApplicability;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
    public List<ResolutionDiagnostic> getDiagnostics() {
        return this._diagnostics;
    }

    public final void addDiagnostic(ResolutionDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this._diagnostics.add(diagnostic);
        if (diagnostic.getApplicability().compareTo(this.currentApplicability) < 0) {
            this.currentApplicability = diagnostic.getApplicability();
        }
    }

    public final boolean isSuccessful() {
        return CandidateApplicabilityKt.isSuccess(this.currentApplicability) && !(this.systemInitialized && getSystem().getHasContradiction());
    }

    public final int getPassedStages() {
        return this.passedStages;
    }

    public final void setPassedStages(int i) {
        this.passedStages = i;
    }

    public final FirExpression dispatchReceiverExpression() {
        FirExpression dispatchReceiver = getDispatchReceiver();
        if (dispatchReceiver != null) {
            if (dispatchReceiver instanceof FirExpressionStub) {
                dispatchReceiver = null;
            }
            if (dispatchReceiver != null) {
                return dispatchReceiver;
            }
        }
        return FirNoReceiverExpression.INSTANCE;
    }

    public final FirExpression chosenExtensionReceiverExpression() {
        FirExpression chosenExtensionReceiver = getChosenExtensionReceiver();
        if (chosenExtensionReceiver != null) {
            if (chosenExtensionReceiver instanceof FirExpressionStub) {
                chosenExtensionReceiver = null;
            }
            if (chosenExtensionReceiver != null) {
                return chosenExtensionReceiver;
            }
        }
        return FirNoReceiverExpression.INSTANCE;
    }

    public final List<FirExpression> contextReceiverArguments() {
        List list = this.contextReceiverArguments;
        return list == null ? CollectionsKt.emptyList() : list;
    }

    public final void updateSourcesOfReceivers() {
        if (this.sourcesWereUpdated) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        this.sourcesWereUpdated = true;
        FirExpression dispatchReceiver = getDispatchReceiver();
        ArrayList arrayList = null;
        setDispatchReceiver(dispatchReceiver != null ? tryToSetSourceForImplicitReceiver(dispatchReceiver) : null);
        FirExpression chosenExtensionReceiver = getChosenExtensionReceiver();
        setChosenExtensionReceiver(chosenExtensionReceiver != null ? tryToSetSourceForImplicitReceiver(chosenExtensionReceiver) : null);
        List<? extends FirExpression> list = this.contextReceiverArguments;
        if (list != null) {
            List<? extends FirExpression> list2 = list;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                arrayList2.add(tryToSetSourceForImplicitReceiver((FirExpression) it.next()));
            }
            arrayList = arrayList2;
        }
        this.contextReceiverArguments = arrayList;
    }

    private final FirExpression tryToSetSourceForImplicitReceiver(FirExpression firExpression) {
        if (firExpression instanceof FirSmartCastExpression) {
            FirSmartCastExpression firSmartCastExpression = (FirSmartCastExpression) firExpression;
            firSmartCastExpression.replaceOriginalExpression(tryToSetSourceForImplicitReceiver(firSmartCastExpression.getOriginalExpression()));
            return firExpression;
        }
        if (!(firExpression instanceof FirThisReceiverExpression)) {
            return firExpression;
        }
        FirThisReceiverExpression firThisReceiverExpression = (FirThisReceiverExpression) firExpression;
        if (!firThisReceiverExpression.isImplicit()) {
            return firExpression;
        }
        FirThisReceiverExpressionBuilder firThisReceiverExpressionBuilder = new FirThisReceiverExpressionBuilder();
        firThisReceiverExpressionBuilder.setTypeRef(firThisReceiverExpression.getTypeRef());
        firThisReceiverExpressionBuilder.getAnnotations().addAll(firThisReceiverExpression.getAnnotations());
        firThisReceiverExpressionBuilder.getContextReceiverArguments().addAll(firThisReceiverExpression.getContextReceiverArguments());
        firThisReceiverExpressionBuilder.getTypeArguments().addAll(firThisReceiverExpression.getTypeArguments());
        firThisReceiverExpressionBuilder.setSource(firThisReceiverExpression.getSource());
        firThisReceiverExpressionBuilder.setCalleeReference(firThisReceiverExpression.getCalleeReference());
        firThisReceiverExpressionBuilder.setImplicit(firThisReceiverExpression.isImplicit());
        KtSourceElement source = getCallInfo().getCallSite().getSource();
        firThisReceiverExpressionBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitReceiver.INSTANCE, 0, 0, 6, null) : null);
        return firThisReceiverExpressionBuilder.build();
    }

    public final boolean getHasVisibleBackingField() {
        return this.hasVisibleBackingField;
    }

    public final void setHasVisibleBackingField(boolean z) {
        this.hasVisibleBackingField = z;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.calls.Candidate");
        return Intrinsics.areEqual(getSymbol(), ((Candidate) other).getSymbol());
    }

    public int hashCode() {
        return getSymbol().hashCode();
    }

    public String toString() {
        return (CandidateApplicabilityKt.isSuccess(getCurrentApplicability()) ? "OK" : "FAIL") + '(' + new StringBuilder().append(this.passedStages).append('/').append(getCallInfo().getCallKind().getResolutionSequence().size()).toString() + "): " + getSymbol();
    }
}
