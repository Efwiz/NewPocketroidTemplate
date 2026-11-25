package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirErrorFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirErrorPropertyBuilder;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponents;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirIntegerConstantOperatorScopeKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirErrorFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirErrorPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: CandidateFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 (2\u00020\u0001:\u0001(B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\\\u0010\f\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u00052\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u0019J\u0016\u0010\u001b\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u001dJ\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010 \u001a\u00020!2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0016\u0010\"\u001a\u00020\u0019*\u0004\u0018\u00010\u00152\u0006\u0010#\u001a\u00020$H\u0002J\u0018\u0010%\u001a\u00020\u0019*\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010&\u001a\u00020$H\u0002J\u001c\u0010'\u001a\u0006\u0012\u0002\b\u00030\u000f*\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory;", "", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;)V", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;)V", "getContext", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "createCandidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "givenExtensionReceiverOptions", "", "objectsByName", "", "isFromOriginalTypeInPresenceOfSmartCast", "createErrorCandidate", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "createErrorFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirErrorFunctionSymbol;", "createErrorPropertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirErrorPropertySymbol;", "isCandidateFromCompanionObjectTypeScope", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "isRegularClassWithoutCompanion", "session", "unwrapIntegerOperatorSymbolIfNeeded", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CandidateFactory {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ConstraintStorage baseSystem;
    private final ResolutionContext context;

    /* JADX INFO: compiled from: CandidateFactory.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ExplicitReceiverKind.values().length];
            try {
                iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ExplicitReceiverKind.NO_EXPLICIT_RECEIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ExplicitReceiverKind.BOTH_RECEIVERS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private CandidateFactory(ResolutionContext resolutionContext, ConstraintStorage constraintStorage) {
        this.context = resolutionContext;
        this.baseSystem = constraintStorage;
    }

    public final ResolutionContext getContext() {
        return this.context;
    }

    /* JADX INFO: compiled from: CandidateFactory.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateFactory$Companion;", "", "()V", "buildBaseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ConstraintStorage buildBaseSystem(ResolutionContext context, CallInfo callInfo) {
            NewConstraintSystemImpl newConstraintSystemImplCreateConstraintSystem = context.getInferenceComponents().createConstraintSystem();
            Iterator<T> it = callInfo.getArguments().iterator();
            while (it.hasNext()) {
                CandidateFactoryKt.addSubsystemFromExpression(newConstraintSystemImplCreateConstraintSystem, (FirExpression) it.next());
            }
            newConstraintSystemImplCreateConstraintSystem.addOtherSystem(context.getBodyResolveContext().getInferenceSession().getCurrentConstraintStorage());
            return newConstraintSystemImplCreateConstraintSystem.asReadOnlyStorage();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CandidateFactory(ResolutionContext context, CallInfo callInfo) {
        this(context, INSTANCE.buildBaseSystem(context, callInfo));
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Candidate createCandidate(CallInfo callInfo, FirBasedSymbol<?> symbol, ExplicitReceiverKind explicitReceiverKind, FirScope scope, FirExpression dispatchReceiver, List<? extends FirExpression> givenExtensionReceiverOptions, boolean objectsByName, boolean isFromOriginalTypeInPresenceOfSmartCast) {
        boolean zIsCandidateFromCompanionObjectTypeScope;
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(explicitReceiverKind, "explicitReceiverKind");
        Intrinsics.checkNotNullParameter(givenExtensionReceiverOptions, "givenExtensionReceiverOptions");
        FirBasedSymbol<?> firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded = unwrapIntegerOperatorSymbolIfNeeded(symbol, callInfo);
        InferenceComponents.ConstraintSystemFactory constraintSystemFactory = this.context.getInferenceComponents().getConstraintSystemFactory();
        ConstraintStorage constraintStorage = this.baseSystem;
        int i = WhenMappings.$EnumSwitchMapping$0[explicitReceiverKind.ordinal()];
        if (i == 1) {
            zIsCandidateFromCompanionObjectTypeScope = isCandidateFromCompanionObjectTypeScope((FirExpression) CollectionsKt.singleOrNull((List) givenExtensionReceiverOptions), callInfo.getSession());
        } else if (i == 2) {
            zIsCandidateFromCompanionObjectTypeScope = isCandidateFromCompanionObjectTypeScope(dispatchReceiver, callInfo.getSession());
        } else {
            if (i != 3 && i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            zIsCandidateFromCompanionObjectTypeScope = false;
        }
        Candidate candidate = new Candidate(firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded, dispatchReceiver, givenExtensionReceiverOptions, explicitReceiverKind, constraintSystemFactory, constraintStorage, callInfo, scope, zIsCandidateFromCompanionObjectTypeScope, isFromOriginalTypeInPresenceOfSmartCast);
        FirElement callSite = callInfo.getCallSite();
        if (callSite instanceof FirCallableReferenceAccess) {
            if ((firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded instanceof FirValueParameterSymbol) || (((firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded instanceof FirPropertySymbol) && ((FirPropertySymbol) firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded).isLocal()) || (firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded instanceof FirBackingFieldSymbol))) {
                candidate.addDiagnostic(new Unsupported("References to variables aren't supported yet", ((FirCallableReferenceAccess) callSite).getCalleeReference().getSource()));
            } else if (firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded instanceof FirEnumEntrySymbol) {
                candidate.addDiagnostic(new Unsupported("References to enum entries aren't supported", ((FirCallableReferenceAccess) callSite).getCalleeReference().getSource()));
            }
        } else if (objectsByName && isRegularClassWithoutCompanion(firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded, callInfo.getSession())) {
            candidate.addDiagnostic(NoCompanionObject.INSTANCE);
        }
        if (callInfo.getOrigin() == FirFunctionCallOrigin.Operator && (firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded instanceof FirPropertySymbol)) {
            candidate.addDiagnostic(PropertyAsOperator.INSTANCE);
        }
        if ((firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded instanceof FirPropertySymbol) && !FirLanguageSettingsComponentKt.getLanguageVersionSettings(this.context.getSession()).supportsFeature(LanguageFeature.PrioritizedEnumEntries)) {
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableSymbol<?>) firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded);
            FirRegularClass firRegularClass = coneClassLikeLookupTagContainingClassLookupTag != null ? LookupTagUtilsKt.toFirRegularClass(coneClassLikeLookupTagContainingClassLookupTag, this.context.getSession()) : null;
            if (firRegularClass != null && UtilsKt.isEnumEntries((FirVariable) ((FirPropertySymbol) firBasedSymbolUnwrapIntegerOperatorSymbolIfNeeded).getFir(), firRegularClass)) {
                candidate.addDiagnostic(LowerPriorityToPreserveCompatibilityDiagnostic.INSTANCE);
            }
        }
        return candidate;
    }

    private final boolean isRegularClassWithoutCompanion(FirBasedSymbol<?> firBasedSymbol, FirSession firSession) {
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass;
        FirClassLikeSymbol firClassLikeSymbol = firBasedSymbol instanceof FirClassLikeSymbol ? (FirClassLikeSymbol) firBasedSymbol : null;
        return (firClassLikeSymbol == null || (firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(firClassLikeSymbol, firSession)) == null || firRegularClassSymbolFullyExpandedClass.getClassKind() == ClassKind.OBJECT || firRegularClassSymbolFullyExpandedClass.getCompanionObjectSymbol() != null) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirBasedSymbol<?> unwrapIntegerOperatorSymbolIfNeeded(FirBasedSymbol<?> firBasedSymbol, CallInfo callInfo) {
        FirNamedFunctionSymbol originalForWrappedIntegerOperator;
        if (!(firBasedSymbol instanceof FirNamedFunctionSymbol)) {
            return firBasedSymbol;
        }
        FirNamedFunctionSymbol firNamedFunctionSymbol = (FirNamedFunctionSymbol) firBasedSymbol;
        return (((FirSimpleFunction) firNamedFunctionSymbol.getFir()).getValueParameters().isEmpty() || (originalForWrappedIntegerOperator = FirIntegerConstantOperatorScopeKt.getOriginalForWrappedIntegerOperator((FirSimpleFunction) firNamedFunctionSymbol.getFir())) == null || ResolveUtilsKt.isIntegerLiteralOrOperatorCall((FirExpression) CollectionsKt.first((List) callInfo.getArguments()))) ? firBasedSymbol : originalForWrappedIntegerOperator;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isCandidateFromCompanionObjectTypeScope(FirExpression firExpression, FirSession firSession) {
        ClassId classId;
        FirRegularClassSymbol firRegularClassSymbolFullyExpandedClass;
        FirRegularClass firRegularClass;
        FirResolvedQualifier firResolvedQualifier = firExpression instanceof FirResolvedQualifier ? (FirResolvedQualifier) firExpression : null;
        if (firResolvedQualifier == null || (classId = ConeTypeUtilsKt.getClassId(FirTypeUtilsKt.getConeType(firExpression.getTypeRef()))) == null) {
            return false;
        }
        FirClassLikeSymbol<?> symbol = firResolvedQualifier.getSymbol();
        FirRegularClassSymbol companionObjectSymbol = (symbol == null || (firRegularClassSymbolFullyExpandedClass = DeclarationUtilsKt.fullyExpandedClass(symbol, firSession)) == null || (firRegularClass = (FirRegularClass) firRegularClassSymbolFullyExpandedClass.getFir()) == null) ? null : firRegularClass.getCompanionObjectSymbol();
        return Intrinsics.areEqual(companionObjectSymbol != null ? companionObjectSymbol.getClassId() : null, classId);
    }

    public final Candidate createErrorCandidate(CallInfo callInfo, ConeDiagnostic diagnostic) {
        FirErrorFunctionSymbol firErrorFunctionSymbolCreateErrorFunctionSymbol;
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        CallKind callKind = callInfo.getCallKind();
        if (callKind instanceof CallKind.VariableAccess) {
            firErrorFunctionSymbolCreateErrorFunctionSymbol = createErrorPropertySymbol(diagnostic);
        } else {
            if (!(callKind instanceof CallKind.Function ? true : callKind instanceof CallKind.DelegatingConstructorCall ? true : callKind instanceof CallKind.CallableReference)) {
                if (callKind instanceof CallKind.SyntheticSelect) {
                    throw new IllegalStateException();
                }
                if (callKind instanceof CallKind.SyntheticIdForCallableReferencesResolution) {
                    throw new IllegalStateException();
                }
                if (callKind instanceof CallKind.CustomForIde) {
                    throw new IllegalStateException();
                }
                throw new NoWhenBranchMatchedException();
            }
            firErrorFunctionSymbolCreateErrorFunctionSymbol = createErrorFunctionSymbol(diagnostic);
        }
        return new Candidate(firErrorFunctionSymbolCreateErrorFunctionSymbol, null, CollectionsKt.emptyList(), ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, this.context.getInferenceComponents().getConstraintSystemFactory(), this.baseSystem, callInfo, null, false, false, 768, null);
    }

    private final FirErrorFunctionSymbol createErrorFunctionSymbol(ConeDiagnostic diagnostic) {
        FirErrorFunctionSymbol firErrorFunctionSymbol = new FirErrorFunctionSymbol();
        FirErrorFunctionBuilder firErrorFunctionBuilder = new FirErrorFunctionBuilder();
        firErrorFunctionBuilder.setModuleData(FirModuleDataKt.getModuleData(this.context.getSession()));
        firErrorFunctionBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        firErrorFunctionBuilder.setOrigin(FirDeclarationOrigin.Synthetic.Error.INSTANCE);
        firErrorFunctionBuilder.setDiagnostic(diagnostic);
        firErrorFunctionBuilder.setSymbol(firErrorFunctionSymbol);
        firErrorFunctionBuilder.build();
        return firErrorFunctionSymbol;
    }

    private final FirErrorPropertySymbol createErrorPropertySymbol(ConeDiagnostic diagnostic) {
        FirErrorPropertySymbol firErrorPropertySymbol = new FirErrorPropertySymbol(diagnostic);
        FirErrorPropertyBuilder firErrorPropertyBuilder = new FirErrorPropertyBuilder();
        firErrorPropertyBuilder.setModuleData(FirModuleDataKt.getModuleData(this.context.getSession()));
        firErrorPropertyBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        firErrorPropertyBuilder.setOrigin(FirDeclarationOrigin.Synthetic.Error.INSTANCE);
        firErrorPropertyBuilder.setName(FirErrorPropertySymbol.INSTANCE.getNAME());
        firErrorPropertyBuilder.setDiagnostic(diagnostic);
        firErrorPropertyBuilder.setSymbol(firErrorPropertySymbol);
        firErrorPropertyBuilder.build();
        return firErrorPropertySymbol;
    }
}
