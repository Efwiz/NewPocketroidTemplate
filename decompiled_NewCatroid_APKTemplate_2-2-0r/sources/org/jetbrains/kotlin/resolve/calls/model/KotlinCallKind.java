package org.jetbrains.kotlin.resolve.calls.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilityUtils;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.CheckIncompatibleTypeVariableUpperBounds;
import org.jetbrains.kotlin.resolve.calls.components.CollectionTypeVariableUsagesInfo;
import org.jetbrains.kotlin.resolve.calls.components.CreateFreshVariablesSubstitutor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.AbstractFakeOverrideSuperCall;
import org.jetbrains.kotlin.resolve.calls.model.AbstractSuperCall;
import org.jetbrains.kotlin.resolve.calls.model.EagerCallableReferenceAtom;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallKind;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.MultipleArgumentsApplicableForContextReceiver;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.NoContextReceiver;
import org.jetbrains.kotlin.resolve.calls.model.ReceiverExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleTypeArgument;
import org.jetbrains.kotlin.resolve.calls.model.SmartCastDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.StubBuilderInferenceReceiver;
import org.jetbrains.kotlin.resolve.calls.model.SuperAsExtensionReceiver;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastUtilKt;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.ContextReceiverAmbiguity;
import org.jetbrains.kotlin.resolve.calls.tower.InfixCallNoInfixModifier;
import org.jetbrains.kotlin.resolve.calls.tower.InvokeConventionCallNoOperatorModifier;
import org.jetbrains.kotlin.resolve.calls.tower.NoMatchingContextReceiver;
import org.jetbrains.kotlin.resolve.calls.tower.UnsupportedContextualDeclarationCall;
import org.jetbrains.kotlin.resolve.calls.tower.VisibilityError;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'INVOKE' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:160)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: KotlinCallKind.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001b\b\u0002\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallKind;", "", "resolutionPart", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionPart;", "(Ljava/lang/String;I[Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionPart;)V", "resolutionSequence", "", "getResolutionSequence", "()Ljava/util/List;", "VARIABLE", "FUNCTION", "INVOKE", "CALLABLE_REFERENCE", "UNSUPPORTED", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinCallKind {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ KotlinCallKind[] $VALUES;
    public static final KotlinCallKind CALLABLE_REFERENCE;
    public static final KotlinCallKind FUNCTION;
    public static final KotlinCallKind INVOKE;
    public static final KotlinCallKind UNSUPPORTED;
    public static final KotlinCallKind VARIABLE = new KotlinCallKind("VARIABLE", 0, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckVisibility
        @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
        public void process(ResolutionCandidate resolutionCandidate, int i) {
            ReceiverValue receiverValue;
            ReceiverValueWithSmartCastInfo receiver;
            Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
            DeclarationDescriptor ownerDescriptor = resolutionCandidate.getScopeTower().getLexicalScope().getOwnerDescriptor();
            SimpleKotlinCallArgument dispatchReceiverArgument = resolutionCandidate.getResolvedCall().getDispatchReceiverArgument();
            if (dispatchReceiverArgument == null || (receiver = dispatchReceiverArgument.getReceiver()) == null || (receiverValue = receiver.getReceiverValue()) == null) {
                receiverValue = DescriptorVisibilities.ALWAYS_SUITABLE_RECEIVER;
            }
            DeclarationDescriptorWithVisibility declarationDescriptorWithVisibilityFindInvisibleMember = DescriptorVisibilityUtils.findInvisibleMember(receiverValue, resolutionCandidate.getResolvedCall().get_candidateDescriptor(), ownerDescriptor, resolutionCandidate.getCallComponents().getLanguageVersionSettings());
            if (declarationDescriptorWithVisibilityFindInvisibleMember == null) {
                return;
            }
            if (dispatchReceiverArgument instanceof ExpressionKotlinCallArgument) {
                ExpressionKotlinCallArgument expressionKotlinCallArgument = (ExpressionKotlinCallArgument) dispatchReceiverArgument;
                if (DescriptorVisibilityUtils.findInvisibleMember(SmartCastUtilKt.getReceiverValueWithSmartCast(receiverValue, ArgumentsUtilsKt.getStableType(expressionKotlinCallArgument.getReceiver())), resolutionCandidate.getResolvedCall().get_candidateDescriptor(), ownerDescriptor, resolutionCandidate.getCallComponents().getLanguageVersionSettings()) == null) {
                    resolutionCandidate.addDiagnostic(new SmartCastDiagnostic(expressionKotlinCallArgument, ArgumentsUtilsKt.getStableType(expressionKotlinCallArgument.getReceiver()), resolutionCandidate.getResolvedCall().getAtom()));
                    return;
                }
            }
            resolutionCandidate.addDiagnostic(new VisibilityError(declarationDescriptorWithVisibilityFindInvisibleMember));
        }
    }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckSuperExpressionCallPart
        @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
        public void process(ResolutionCandidate resolutionCandidate, int i) {
            Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
            CallableDescriptor callableDescriptor = resolutionCandidate.getResolvedCall().get_candidateDescriptor();
            if (resolutionCandidate.getCallComponents().getStatelessCallbacks().isSuperExpression(resolutionCandidate.getResolvedCall().getDispatchReceiverArgument()) && (callableDescriptor instanceof CallableMemberDescriptor)) {
                checkSuperCandidateDescriptor(resolutionCandidate, (CallableMemberDescriptor) callableDescriptor);
            }
            SimpleKotlinCallArgument extensionReceiverArgument = resolutionCandidate.getResolvedCall().getExtensionReceiverArgument();
            if (extensionReceiverArgument == null || !resolutionCandidate.getCallComponents().getStatelessCallbacks().isSuperExpression(extensionReceiverArgument)) {
                return;
            }
            resolutionCandidate.addDiagnostic(new SuperAsExtensionReceiver(extensionReceiverArgument));
        }

        private final void checkSuperCandidateDescriptor(ResolutionCandidate resolutionCandidate, CallableMemberDescriptor callableMemberDescriptor) {
            Object next;
            CallableMemberDescriptor callableMemberDescriptor2;
            if (callableMemberDescriptor.getModality() == Modality.ABSTRACT) {
                SimpleKotlinCallArgument dispatchReceiverArgument = resolutionCandidate.getResolvedCall().getDispatchReceiverArgument();
                Intrinsics.checkNotNull(dispatchReceiverArgument);
                resolutionCandidate.addDiagnostic(new AbstractSuperCall(dispatchReceiverArgument));
                return;
            }
            if (callableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                while (callableMemberDescriptor.getOverriddenDescriptors().size() == 1) {
                    Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
                    Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                    Object objFirst = CollectionsKt.first(overriddenDescriptors);
                    Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
                    callableMemberDescriptor = (CallableMemberDescriptor) objFirst;
                    if (callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                        return;
                    }
                }
                if (callableMemberDescriptor.getOverriddenDescriptors().size() > 1) {
                    Collection<? extends CallableMemberDescriptor> overriddenDescriptors2 = callableMemberDescriptor.getOverriddenDescriptors();
                    Intrinsics.checkNotNullExpressionValue(overriddenDescriptors2, "getOverriddenDescriptors(...)");
                    Iterator<T> it = overriddenDescriptors2.iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        } else {
                            next = it.next();
                            callableMemberDescriptor2 = (CallableMemberDescriptor) next;
                            Intrinsics.checkNotNull(callableMemberDescriptor2);
                        }
                    } while (DescriptorUtilsKt.isInsideInterface(callableMemberDescriptor2));
                    CallableMemberDescriptor callableMemberDescriptor3 = (CallableMemberDescriptor) next;
                    if ((callableMemberDescriptor3 != null ? callableMemberDescriptor3.getModality() : null) == Modality.ABSTRACT) {
                        resolutionCandidate.addDiagnostic(AbstractFakeOverrideSuperCall.INSTANCE);
                    }
                }
            }
        }
    }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.NoTypeArguments
        @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
        public void process(ResolutionCandidate resolutionCandidate, int i) {
            Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
            resolutionCandidate.getResolvedCall().getAtom().getTypeArguments().isEmpty();
            resolutionCandidate.getResolvedCall().setTypeArgumentMappingByOriginal(TypeArgumentsToParametersMapper.TypeArgumentsMapping.NoExplicitArguments.INSTANCE);
        }
    }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.NoArguments
        @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
        public void process(ResolutionCandidate resolutionCandidate, int i) {
            Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
            resolutionCandidate.getResolvedCall().getAtom().getArgumentsInParenthesis().isEmpty();
            resolutionCandidate.getResolvedCall().getAtom().getExternalArgument();
            resolutionCandidate.getResolvedCall().setArgumentMappingByOriginal(MapsKt.emptyMap());
            resolutionCandidate.getResolvedCall().setArgumentToCandidateParameter(MapsKt.emptyMap());
        }
    }, CreateFreshVariablesSubstitutor.INSTANCE, CollectionTypeVariableUsagesInfo.INSTANCE, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckExplicitReceiverKindConsistency

        /* JADX INFO: compiled from: ResolutionParts.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ExplicitReceiverKind.values().length];
                try {
                    iArr[ExplicitReceiverKind.NO_EXPLICIT_RECEIVER.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[ExplicitReceiverKind.BOTH_RECEIVERS.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        private final Void hasError(ResolutionCandidate resolutionCandidate) {
            throw new IllegalStateException(("Inconsistent call: " + resolutionCandidate.getResolvedCall().getAtom() + ". \nCandidate: " + resolutionCandidate.getResolvedCall().get_candidateDescriptor() + ", explicitReceiverKind: " + resolutionCandidate.getResolvedCall().getExplicitReceiverKind() + ".\nExplicit receiver: " + resolutionCandidate.getResolvedCall().getAtom().getExplicitReceiver() + ", dispatchReceiverForInvokeExtension: " + resolutionCandidate.getResolvedCall().getAtom().getDispatchReceiverForInvokeExtension()).toString());
        }

        @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
        public void process(ResolutionCandidate resolutionCandidate, int i) {
            Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
            int i2 = WhenMappings.$EnumSwitchMapping$0[resolutionCandidate.getResolvedCall().getExplicitReceiverKind().ordinal()];
            if (i2 == 1) {
                if ((resolutionCandidate.getResolvedCall().getAtom().getExplicitReceiver() instanceof SimpleKotlinCallArgument) || resolutionCandidate.getResolvedCall().getAtom().getDispatchReceiverForInvokeExtension() != null) {
                    hasError(resolutionCandidate);
                    throw null;
                }
                return;
            }
            if (i2 != 2 && i2 != 3) {
                if (i2 != 4) {
                    return;
                }
                if (resolutionCandidate.getResolvedCall().getAtom().getExplicitReceiver() == null || resolutionCandidate.getResolvedCall().getAtom().getDispatchReceiverForInvokeExtension() == null) {
                    hasError(resolutionCandidate);
                    throw null;
                }
                return;
            }
            if (resolutionCandidate.getResolvedCall().getAtom().getCallKind() != KotlinCallKind.INVOKE || resolutionCandidate.getResolvedCall().getAtom().getDispatchReceiverForInvokeExtension() != null) {
                if (resolutionCandidate.getResolvedCall().getAtom().getCallKind() == KotlinCallKind.INVOKE) {
                    return;
                }
                if (resolutionCandidate.getResolvedCall().getAtom().getExplicitReceiver() != null && resolutionCandidate.getResolvedCall().getAtom().getDispatchReceiverForInvokeExtension() == null) {
                    return;
                }
            }
            hasError(resolutionCandidate);
            throw null;
        }
    }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckReceivers
        @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
        public int workCount(ResolutionCandidate resolutionCandidate) {
            Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
            return 2;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
        public void process(ResolutionCandidate resolutionCandidate, int i) {
            Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
            if (i == 0) {
                checkReceiver(resolutionCandidate, resolutionCandidate.getResolvedCall().getDispatchReceiverArgument(), resolutionCandidate.getResolvedCall().get_candidateDescriptor().mo6753getDispatchReceiverParameter(), true);
                return;
            }
            if (i != 1) {
                return;
            }
            SimpleKotlinCallArgument extensionReceiverArgument = resolutionCandidate.getResolvedCall().getExtensionReceiverArgument();
            if (extensionReceiverArgument == null) {
                extensionReceiverArgument = chooseExtensionReceiverCandidate(resolutionCandidate);
                if (extensionReceiverArgument == null) {
                    return;
                } else {
                    resolutionCandidate.getResolvedCall().setExtensionReceiverArgument(extensionReceiverArgument);
                }
            }
            if (!resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.NoBuilderInferenceWithoutAnnotationRestriction) && (extensionReceiverArgument.getReceiver().getReceiverValue().getType() instanceof StubTypeForBuilderInference)) {
                ReceiverParameterDescriptor extensionReceiverParameter = resolutionCandidate.getResolvedCall().get_candidateDescriptor().getExtensionReceiverParameter();
                Intrinsics.checkNotNull(extensionReceiverParameter);
                resolutionCandidate.addDiagnostic(new StubBuilderInferenceReceiver(extensionReceiverArgument, extensionReceiverParameter));
            }
            checkReceiver(resolutionCandidate, resolutionCandidate.getResolvedCall().getExtensionReceiverArgument(), resolutionCandidate.getResolvedCall().get_candidateDescriptor().getExtensionReceiverParameter(), false);
        }

        private final SimpleKotlinCallArgument chooseExtensionReceiverCandidate(ResolutionCandidate resolutionCandidate) {
            List<SimpleKotlinCallArgument> extensionReceiverArgumentCandidates = resolutionCandidate.getResolvedCall().getExtensionReceiverArgumentCandidates();
            List<SimpleKotlinCallArgument> list = extensionReceiverArgumentCandidates;
            if (list == null || list.isEmpty()) {
                return null;
            }
            if (extensionReceiverArgumentCandidates.size() == 1) {
                return (SimpleKotlinCallArgument) CollectionsKt.single((List) extensionReceiverArgumentCandidates);
            }
            ReceiverParameterDescriptor extensionReceiverParameter = resolutionCandidate.getResolvedCall().get_candidateDescriptor().getExtensionReceiverParameter();
            if (extensionReceiverParameter == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = extensionReceiverArgumentCandidates.iterator();
            while (it.hasNext()) {
                ApplicableContextReceiverArgumentWithConstraint receiverArgumentWithConstraintIfCompatible = ResolutionPartsKt.getReceiverArgumentWithConstraintIfCompatible(resolutionCandidate, (SimpleKotlinCallArgument) it.next(), extensionReceiverParameter);
                if (receiverArgumentWithConstraintIfCompatible != null) {
                    arrayList.add(receiverArgumentWithConstraintIfCompatible);
                }
            }
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            if (size == 0) {
                resolutionCandidate.addDiagnostic(new NoMatchingContextReceiver());
                return null;
            }
            if (size == 1) {
                return ((ApplicableContextReceiverArgumentWithConstraint) CollectionsKt.single((List) arrayList2)).getArgument();
            }
            resolutionCandidate.addDiagnostic(new ContextReceiverAmbiguity());
            return null;
        }

        private final void checkReceiver(ResolutionCandidate resolutionCandidate, SimpleKotlinCallArgument simpleKotlinCallArgument, ReceiverParameterDescriptor receiverParameterDescriptor, boolean z) {
            ImplicitInvokeCheckStatus implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall;
            if (!(resolutionCandidate instanceof CallableReferenceResolutionCandidate)) {
                if ((simpleKotlinCallArgument == null) != (receiverParameterDescriptor == null)) {
                    throw new IllegalStateException(("Inconsistency receiver state for call " + resolutionCandidate.getResolvedCall().getAtom() + " and candidate descriptor: " + resolutionCandidate.getResolvedCall().get_candidateDescriptor()).toString());
                }
            }
            if (simpleKotlinCallArgument == null || receiverParameterDescriptor == null) {
                return;
            }
            if (z) {
                implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall = ResolutionPartsKt.checkUnsafeImplicitInvokeAfterSafeCall(resolutionCandidate, simpleKotlinCallArgument);
            } else {
                implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall = ImplicitInvokeCheckStatus.NO_INVOKE;
            }
            ResolutionPartsKt.resolveKotlinArgument(resolutionCandidate, simpleKotlinCallArgument, receiverParameterDescriptor, new ReceiverInfo(true, implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall != ImplicitInvokeCheckStatus.UNSAFE_INVOKE_REPORTED, implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall == ImplicitInvokeCheckStatus.INVOKE_ON_NOT_NULL_VARIABLE, resolutionCandidate.getResolvedCall().getAtom()));
        }
    }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.PostponedVariablesInitializerResolutionPart
        @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
        public void process(ResolutionCandidate resolutionCandidate, int i) {
            Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
            ConstraintSystemBuilder builder = resolutionCandidate.getSystem().getBuilder();
            for (Map.Entry<KotlinCallArgument, ValueParameterDescriptor> entry : resolutionCandidate.getResolvedCall().getArgumentToCandidateParameter().entrySet()) {
                KotlinCallArgument key = entry.getKey();
                ValueParameterDescriptor value = entry.getValue();
                if (resolutionCandidate.getCallComponents().getStatelessCallbacks().isBuilderInferenceCall(key, value)) {
                    KotlinType type = value.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(type);
                    if (receiverTypeFromFunctionType != null) {
                        boolean zSupportsFeature = resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.UseBuilderInferenceOnlyIfNeeded);
                        if (key instanceof LambdaKotlinCallArgument) {
                            LambdaKotlinCallArgument lambdaKotlinCallArgument = (LambdaKotlinCallArgument) key;
                            if (!lambdaKotlinCallArgument.getHasBuilderInferenceAnnotation()) {
                                lambdaKotlinCallArgument.setHasBuilderInferenceAnnotation(true);
                            }
                        }
                        if (!zSupportsFeature) {
                            for (final TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor : resolutionCandidate.getResolvedCall().getFreshVariablesSubstitutor().getFreshVariables()) {
                                if (!(resolutionCandidate.getResolvedCall().getTypeArgumentMappingByOriginal().getTypeArgument(typeVariableFromCallableDescriptor.getOriginalTypeParameter()) instanceof SimpleTypeArgument)) {
                                    TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor2 = typeVariableFromCallableDescriptor;
                                    if (!builder.isPostponedTypeVariable(typeVariableFromCallableDescriptor2) && TypeUtilsKt.contains(receiverTypeFromFunctionType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.PostponedVariablesInitializerResolutionPart.process.1
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        public final Boolean invoke(UnwrappedType it) {
                                            Intrinsics.checkNotNullParameter(it, "it");
                                            return Boolean.valueOf(Intrinsics.areEqual(it.getConstructor(), typeVariableFromCallableDescriptor.getOriginalTypeParameter().getTypeConstructor()));
                                        }
                                    })) {
                                        builder.markPostponedVariable(typeVariableFromCallableDescriptor2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckContextReceiversResolutionPart
        @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
        public void process(ResolutionCandidate resolutionCandidate, int i) {
            Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
            if (resolutionCandidate.getResolvedCall().get_candidateDescriptor().getContextReceiverParameters().isEmpty()) {
                return;
            }
            if (!resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.ContextReceivers)) {
                resolutionCandidate.addDiagnostic(new UnsupportedContextualDeclarationCall());
                return;
            }
            Sequence<LexicalScope> sequenceFilter = SequencesKt.filter(ScopeUtilsKt.getParentsWithSelf(resolutionCandidate.getScopeTower().getLexicalScope()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckContextReceiversResolutionPart$process$$inlined$filterIsInstance$1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof LexicalScope);
                }
            });
            Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
            ArrayList arrayList = new ArrayList();
            for (LexicalScope lexicalScope : sequenceFilter) {
                ReceiverValueWithSmartCastInfo implicitReceiver = resolutionCandidate.getScopeTower().getImplicitReceiver(lexicalScope);
                if (implicitReceiver != null) {
                    arrayList.add(CollectionsKt.listOf(implicitReceiver));
                }
                List<ReceiverValueWithSmartCastInfo> contextReceivers = resolutionCandidate.getScopeTower().getContextReceivers(lexicalScope);
                if (!contextReceivers.isEmpty()) {
                    arrayList.add(contextReceivers);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (ReceiverParameterDescriptor receiverParameterDescriptor : resolutionCandidate.getResolvedCall().get_candidateDescriptor().getContextReceiverParameters()) {
                Intrinsics.checkNotNull(receiverParameterDescriptor);
                SimpleKotlinCallArgument simpleKotlinCallArgumentFindContextReceiver = findContextReceiver(resolutionCandidate, arrayList, receiverParameterDescriptor);
                if (simpleKotlinCallArgumentFindContextReceiver == null) {
                    return;
                } else {
                    arrayList2.add(simpleKotlinCallArgumentFindContextReceiver);
                }
            }
            resolutionCandidate.getResolvedCall().setContextReceiversArguments(arrayList2);
        }

        private final SimpleKotlinCallArgument findContextReceiver(ResolutionCandidate resolutionCandidate, List<? extends List<ReceiverValueWithSmartCastInfo>> list, ReceiverParameterDescriptor receiverParameterDescriptor) {
            ConstraintSystemBuilder builder = resolutionCandidate.getSystem().getBuilder();
            for (List<ReceiverValueWithSmartCastInfo> list2 : list) {
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    ApplicableContextReceiverArgumentWithConstraint receiverArgumentWithConstraintIfCompatible = ResolutionPartsKt.getReceiverArgumentWithConstraintIfCompatible(resolutionCandidate, ReceiverExpressionKotlinCallArgument.Companion.invoke$default(ReceiverExpressionKotlinCallArgument.INSTANCE, (ReceiverValueWithSmartCastInfo) it.next(), false, false, 6, null), receiverParameterDescriptor);
                    if (receiverArgumentWithConstraintIfCompatible != null) {
                        arrayList.add(receiverArgumentWithConstraintIfCompatible);
                    }
                }
                List list3 = CollectionsKt.toList(arrayList);
                if (list3.size() == 1) {
                    ApplicableContextReceiverArgumentWithConstraint applicableContextReceiverArgumentWithConstraint = (ApplicableContextReceiverArgumentWithConstraint) CollectionsKt.single(list3);
                    SimpleKotlinCallArgument argument = applicableContextReceiverArgumentWithConstraint.getArgument();
                    UnwrappedType argumentType = applicableContextReceiverArgumentWithConstraint.getArgumentType();
                    builder.addSubtypeConstraint(argumentType, applicableContextReceiverArgumentWithConstraint.getExpectedType(), applicableContextReceiverArgumentWithConstraint.getPosition());
                    return argument;
                }
                if (list3.size() > 1) {
                    resolutionCandidate.addDiagnostic(new MultipleArgumentsApplicableForContextReceiver(receiverParameterDescriptor));
                    return null;
                }
            }
            resolutionCandidate.addDiagnostic(new NoContextReceiver(receiverParameterDescriptor));
            return null;
        }
    }, CheckIncompatibleTypeVariableUpperBounds.INSTANCE);
    private final List<ResolutionPart> resolutionSequence;

    private static final /* synthetic */ KotlinCallKind[] $values() {
        return new KotlinCallKind[]{VARIABLE, FUNCTION, INVOKE, CALLABLE_REFERENCE, UNSUPPORTED};
    }

    public static EnumEntries<KotlinCallKind> getEntries() {
        return $ENTRIES;
    }

    public static KotlinCallKind valueOf(String str) {
        return (KotlinCallKind) Enum.valueOf(KotlinCallKind.class, str);
    }

    public static KotlinCallKind[] values() {
        return (KotlinCallKind[]) $VALUES.clone();
    }

    private KotlinCallKind(String str, int i, ResolutionPart... resolutionPartArr) {
        super(str, i);
        this.resolutionSequence = ArraysKt.asList(resolutionPartArr);
    }

    static {
        KotlinCallKind kotlinCallKind = new KotlinCallKind("FUNCTION", 1, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckVisibility
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                ReceiverValue receiverValue;
                ReceiverValueWithSmartCastInfo receiver;
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                DeclarationDescriptor ownerDescriptor = resolutionCandidate.getScopeTower().getLexicalScope().getOwnerDescriptor();
                SimpleKotlinCallArgument dispatchReceiverArgument = resolutionCandidate.getResolvedCall().getDispatchReceiverArgument();
                if (dispatchReceiverArgument == null || (receiver = dispatchReceiverArgument.getReceiver()) == null || (receiverValue = receiver.getReceiverValue()) == null) {
                    receiverValue = DescriptorVisibilities.ALWAYS_SUITABLE_RECEIVER;
                }
                DeclarationDescriptorWithVisibility declarationDescriptorWithVisibilityFindInvisibleMember = DescriptorVisibilityUtils.findInvisibleMember(receiverValue, resolutionCandidate.getResolvedCall().get_candidateDescriptor(), ownerDescriptor, resolutionCandidate.getCallComponents().getLanguageVersionSettings());
                if (declarationDescriptorWithVisibilityFindInvisibleMember == null) {
                    return;
                }
                if (dispatchReceiverArgument instanceof ExpressionKotlinCallArgument) {
                    ExpressionKotlinCallArgument expressionKotlinCallArgument = (ExpressionKotlinCallArgument) dispatchReceiverArgument;
                    if (DescriptorVisibilityUtils.findInvisibleMember(SmartCastUtilKt.getReceiverValueWithSmartCast(receiverValue, ArgumentsUtilsKt.getStableType(expressionKotlinCallArgument.getReceiver())), resolutionCandidate.getResolvedCall().get_candidateDescriptor(), ownerDescriptor, resolutionCandidate.getCallComponents().getLanguageVersionSettings()) == null) {
                        resolutionCandidate.addDiagnostic(new SmartCastDiagnostic(expressionKotlinCallArgument, ArgumentsUtilsKt.getStableType(expressionKotlinCallArgument.getReceiver()), resolutionCandidate.getResolvedCall().getAtom()));
                        return;
                    }
                }
                resolutionCandidate.addDiagnostic(new VisibilityError(declarationDescriptorWithVisibilityFindInvisibleMember));
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckInfixResolutionPart
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                CallableDescriptor callableDescriptor = resolutionCandidate.getResolvedCall().get_candidateDescriptor();
                if ((callableDescriptor instanceof FunctionDescriptor) && !((FunctionDescriptor) callableDescriptor).isInfix() && resolutionCandidate.getCallComponents().getStatelessCallbacks().isInfixCall(resolutionCandidate.getResolvedCall().getAtom())) {
                    resolutionCandidate.addDiagnostic(InfixCallNoInfixModifier.INSTANCE);
                }
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckOperatorResolutionPart
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                CallableDescriptor callableDescriptor = resolutionCandidate.getResolvedCall().get_candidateDescriptor();
                if ((callableDescriptor instanceof FunctionDescriptor) && !((FunctionDescriptor) callableDescriptor).isOperator() && resolutionCandidate.getCallComponents().getStatelessCallbacks().isOperatorCall(resolutionCandidate.getResolvedCall().getAtom())) {
                    resolutionCandidate.addDiagnostic(InvokeConventionCallNoOperatorModifier.INSTANCE);
                }
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckSuperExpressionCallPart
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                CallableDescriptor callableDescriptor = resolutionCandidate.getResolvedCall().get_candidateDescriptor();
                if (resolutionCandidate.getCallComponents().getStatelessCallbacks().isSuperExpression(resolutionCandidate.getResolvedCall().getDispatchReceiverArgument()) && (callableDescriptor instanceof CallableMemberDescriptor)) {
                    checkSuperCandidateDescriptor(resolutionCandidate, (CallableMemberDescriptor) callableDescriptor);
                }
                SimpleKotlinCallArgument extensionReceiverArgument = resolutionCandidate.getResolvedCall().getExtensionReceiverArgument();
                if (extensionReceiverArgument == null || !resolutionCandidate.getCallComponents().getStatelessCallbacks().isSuperExpression(extensionReceiverArgument)) {
                    return;
                }
                resolutionCandidate.addDiagnostic(new SuperAsExtensionReceiver(extensionReceiverArgument));
            }

            private final void checkSuperCandidateDescriptor(ResolutionCandidate resolutionCandidate, CallableMemberDescriptor callableMemberDescriptor) {
                Object next;
                CallableMemberDescriptor callableMemberDescriptor2;
                if (callableMemberDescriptor.getModality() == Modality.ABSTRACT) {
                    SimpleKotlinCallArgument dispatchReceiverArgument = resolutionCandidate.getResolvedCall().getDispatchReceiverArgument();
                    Intrinsics.checkNotNull(dispatchReceiverArgument);
                    resolutionCandidate.addDiagnostic(new AbstractSuperCall(dispatchReceiverArgument));
                    return;
                }
                if (callableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                    while (callableMemberDescriptor.getOverriddenDescriptors().size() == 1) {
                        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
                        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                        Object objFirst = CollectionsKt.first(overriddenDescriptors);
                        Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
                        callableMemberDescriptor = (CallableMemberDescriptor) objFirst;
                        if (callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                            return;
                        }
                    }
                    if (callableMemberDescriptor.getOverriddenDescriptors().size() > 1) {
                        Collection<? extends CallableMemberDescriptor> overriddenDescriptors2 = callableMemberDescriptor.getOverriddenDescriptors();
                        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors2, "getOverriddenDescriptors(...)");
                        Iterator<T> it = overriddenDescriptors2.iterator();
                        do {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            } else {
                                next = it.next();
                                callableMemberDescriptor2 = (CallableMemberDescriptor) next;
                                Intrinsics.checkNotNull(callableMemberDescriptor2);
                            }
                        } while (DescriptorUtilsKt.isInsideInterface(callableMemberDescriptor2));
                        CallableMemberDescriptor callableMemberDescriptor3 = (CallableMemberDescriptor) next;
                        if ((callableMemberDescriptor3 != null ? callableMemberDescriptor3.getModality() : null) == Modality.ABSTRACT) {
                            resolutionCandidate.addDiagnostic(AbstractFakeOverrideSuperCall.INSTANCE);
                        }
                    }
                }
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.MapTypeArguments
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                MutableResolvedCallAtom resolvedCall = resolutionCandidate.getResolvedCall();
                TypeArgumentsToParametersMapper typeArgumentsToParametersMapper = resolutionCandidate.getCallComponents().getTypeArgumentsToParametersMapper();
                KotlinCall atom = resolutionCandidate.getResolvedCall().getAtom();
                CallableDescriptor original = resolutionCandidate.getResolvedCall().get_candidateDescriptor().getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                TypeArgumentsToParametersMapper.TypeArgumentsMapping typeArgumentsMappingMapTypeArguments = typeArgumentsToParametersMapper.mapTypeArguments(atom, original);
                Iterator<T> it = typeArgumentsMappingMapTypeArguments.getDiagnostics().iterator();
                while (it.hasNext()) {
                    resolutionCandidate.addDiagnostic((KotlinCallDiagnostic) it.next());
                }
                resolvedCall.setTypeArgumentMappingByOriginal(typeArgumentsMappingMapTypeArguments);
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.MapArguments
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                ArgumentsToParametersMapper.ArgumentMapping argumentMappingMapArguments = resolutionCandidate.getCallComponents().getArgumentsToParametersMapper().mapArguments(resolutionCandidate.getResolvedCall().getAtom(), resolutionCandidate.getResolvedCall().get_candidateDescriptor());
                Iterator<T> it = argumentMappingMapArguments.getDiagnostics().iterator();
                while (it.hasNext()) {
                    resolutionCandidate.addDiagnostic((KotlinCallDiagnostic) it.next());
                }
                resolutionCandidate.getResolvedCall().setArgumentMappingByOriginal(argumentMappingMapArguments.getParameterToCallArgumentMap());
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.ArgumentsToCandidateParameterDescriptor
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                HashMap map = new HashMap();
                for (Map.Entry<ValueParameterDescriptor, ResolvedCallArgument> entry : resolutionCandidate.getResolvedCall().getArgumentMappingByOriginal().entrySet()) {
                    ValueParameterDescriptor key = entry.getKey();
                    ResolvedCallArgument value = entry.getValue();
                    List<ValueParameterDescriptor> valueParameters = resolutionCandidate.getResolvedCall().get_candidateDescriptor().getValueParameters();
                    Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.getOrNull(valueParameters, key.getIndex());
                    if (valueParameterDescriptor != null) {
                        Iterator<KotlinCallArgument> it = value.getArguments().iterator();
                        while (it.hasNext()) {
                            map.put(it.next(), valueParameterDescriptor);
                        }
                    }
                }
                resolutionCandidate.getResolvedCall().setArgumentToCandidateParameter(AddToStdlibKt.compactIfPossible(map));
            }
        }, CreateFreshVariablesSubstitutor.INSTANCE, CollectionTypeVariableUsagesInfo.INSTANCE, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckExplicitReceiverKindConsistency

            /* JADX INFO: compiled from: ResolutionParts.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[ExplicitReceiverKind.values().length];
                    try {
                        iArr[ExplicitReceiverKind.NO_EXPLICIT_RECEIVER.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[ExplicitReceiverKind.BOTH_RECEIVERS.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            private final Void hasError(ResolutionCandidate resolutionCandidate) {
                throw new IllegalStateException(("Inconsistent call: " + resolutionCandidate.getResolvedCall().getAtom() + ". \nCandidate: " + resolutionCandidate.getResolvedCall().get_candidateDescriptor() + ", explicitReceiverKind: " + resolutionCandidate.getResolvedCall().getExplicitReceiverKind() + ".\nExplicit receiver: " + resolutionCandidate.getResolvedCall().getAtom().getExplicitReceiver() + ", dispatchReceiverForInvokeExtension: " + resolutionCandidate.getResolvedCall().getAtom().getDispatchReceiverForInvokeExtension()).toString());
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                int i2 = WhenMappings.$EnumSwitchMapping$0[resolutionCandidate.getResolvedCall().getExplicitReceiverKind().ordinal()];
                if (i2 == 1) {
                    if ((resolutionCandidate.getResolvedCall().getAtom().getExplicitReceiver() instanceof SimpleKotlinCallArgument) || resolutionCandidate.getResolvedCall().getAtom().getDispatchReceiverForInvokeExtension() != null) {
                        hasError(resolutionCandidate);
                        throw null;
                    }
                    return;
                }
                if (i2 != 2 && i2 != 3) {
                    if (i2 != 4) {
                        return;
                    }
                    if (resolutionCandidate.getResolvedCall().getAtom().getExplicitReceiver() == null || resolutionCandidate.getResolvedCall().getAtom().getDispatchReceiverForInvokeExtension() == null) {
                        hasError(resolutionCandidate);
                        throw null;
                    }
                    return;
                }
                if (resolutionCandidate.getResolvedCall().getAtom().getCallKind() != KotlinCallKind.INVOKE || resolutionCandidate.getResolvedCall().getAtom().getDispatchReceiverForInvokeExtension() != null) {
                    if (resolutionCandidate.getResolvedCall().getAtom().getCallKind() == KotlinCallKind.INVOKE) {
                        return;
                    }
                    if (resolutionCandidate.getResolvedCall().getAtom().getExplicitReceiver() != null && resolutionCandidate.getResolvedCall().getAtom().getDispatchReceiverForInvokeExtension() == null) {
                        return;
                    }
                }
                hasError(resolutionCandidate);
                throw null;
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckReceivers
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public int workCount(ResolutionCandidate resolutionCandidate) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                return 2;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                if (i == 0) {
                    checkReceiver(resolutionCandidate, resolutionCandidate.getResolvedCall().getDispatchReceiverArgument(), resolutionCandidate.getResolvedCall().get_candidateDescriptor().mo6753getDispatchReceiverParameter(), true);
                    return;
                }
                if (i != 1) {
                    return;
                }
                SimpleKotlinCallArgument extensionReceiverArgument = resolutionCandidate.getResolvedCall().getExtensionReceiverArgument();
                if (extensionReceiverArgument == null) {
                    extensionReceiverArgument = chooseExtensionReceiverCandidate(resolutionCandidate);
                    if (extensionReceiverArgument == null) {
                        return;
                    } else {
                        resolutionCandidate.getResolvedCall().setExtensionReceiverArgument(extensionReceiverArgument);
                    }
                }
                if (!resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.NoBuilderInferenceWithoutAnnotationRestriction) && (extensionReceiverArgument.getReceiver().getReceiverValue().getType() instanceof StubTypeForBuilderInference)) {
                    ReceiverParameterDescriptor extensionReceiverParameter = resolutionCandidate.getResolvedCall().get_candidateDescriptor().getExtensionReceiverParameter();
                    Intrinsics.checkNotNull(extensionReceiverParameter);
                    resolutionCandidate.addDiagnostic(new StubBuilderInferenceReceiver(extensionReceiverArgument, extensionReceiverParameter));
                }
                checkReceiver(resolutionCandidate, resolutionCandidate.getResolvedCall().getExtensionReceiverArgument(), resolutionCandidate.getResolvedCall().get_candidateDescriptor().getExtensionReceiverParameter(), false);
            }

            private final SimpleKotlinCallArgument chooseExtensionReceiverCandidate(ResolutionCandidate resolutionCandidate) {
                List<SimpleKotlinCallArgument> extensionReceiverArgumentCandidates = resolutionCandidate.getResolvedCall().getExtensionReceiverArgumentCandidates();
                List<SimpleKotlinCallArgument> list = extensionReceiverArgumentCandidates;
                if (list == null || list.isEmpty()) {
                    return null;
                }
                if (extensionReceiverArgumentCandidates.size() == 1) {
                    return (SimpleKotlinCallArgument) CollectionsKt.single((List) extensionReceiverArgumentCandidates);
                }
                ReceiverParameterDescriptor extensionReceiverParameter = resolutionCandidate.getResolvedCall().get_candidateDescriptor().getExtensionReceiverParameter();
                if (extensionReceiverParameter == null) {
                    return null;
                }
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = extensionReceiverArgumentCandidates.iterator();
                while (it.hasNext()) {
                    ApplicableContextReceiverArgumentWithConstraint receiverArgumentWithConstraintIfCompatible = ResolutionPartsKt.getReceiverArgumentWithConstraintIfCompatible(resolutionCandidate, (SimpleKotlinCallArgument) it.next(), extensionReceiverParameter);
                    if (receiverArgumentWithConstraintIfCompatible != null) {
                        arrayList.add(receiverArgumentWithConstraintIfCompatible);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                if (size == 0) {
                    resolutionCandidate.addDiagnostic(new NoMatchingContextReceiver());
                    return null;
                }
                if (size == 1) {
                    return ((ApplicableContextReceiverArgumentWithConstraint) CollectionsKt.single((List) arrayList2)).getArgument();
                }
                resolutionCandidate.addDiagnostic(new ContextReceiverAmbiguity());
                return null;
            }

            private final void checkReceiver(ResolutionCandidate resolutionCandidate, SimpleKotlinCallArgument simpleKotlinCallArgument, ReceiverParameterDescriptor receiverParameterDescriptor, boolean z) {
                ImplicitInvokeCheckStatus implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall;
                if (!(resolutionCandidate instanceof CallableReferenceResolutionCandidate)) {
                    if ((simpleKotlinCallArgument == null) != (receiverParameterDescriptor == null)) {
                        throw new IllegalStateException(("Inconsistency receiver state for call " + resolutionCandidate.getResolvedCall().getAtom() + " and candidate descriptor: " + resolutionCandidate.getResolvedCall().get_candidateDescriptor()).toString());
                    }
                }
                if (simpleKotlinCallArgument == null || receiverParameterDescriptor == null) {
                    return;
                }
                if (z) {
                    implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall = ResolutionPartsKt.checkUnsafeImplicitInvokeAfterSafeCall(resolutionCandidate, simpleKotlinCallArgument);
                } else {
                    implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall = ImplicitInvokeCheckStatus.NO_INVOKE;
                }
                ResolutionPartsKt.resolveKotlinArgument(resolutionCandidate, simpleKotlinCallArgument, receiverParameterDescriptor, new ReceiverInfo(true, implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall != ImplicitInvokeCheckStatus.UNSAFE_INVOKE_REPORTED, implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall == ImplicitInvokeCheckStatus.INVOKE_ON_NOT_NULL_VARIABLE, resolutionCandidate.getResolvedCall().getAtom()));
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckArgumentsInParenthesis
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                KotlinCallArgument kotlinCallArgument = resolutionCandidate.getResolvedCall().getAtom().getArgumentsInParenthesis().get(i);
                ResolutionPartsKt.resolveKotlinArgument(resolutionCandidate, kotlinCallArgument, resolutionCandidate.getResolvedCall().getArgumentToCandidateParameter().get(kotlinCallArgument), ReceiverInfo.INSTANCE.getNotReceiver());
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public int workCount(ResolutionCandidate resolutionCandidate) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                return resolutionCandidate.getResolvedCall().getAtom().getArgumentsInParenthesis().size();
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckExternalArgument
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                KotlinCallArgument externalArgument = resolutionCandidate.getResolvedCall().getAtom().getExternalArgument();
                if (externalArgument == null) {
                    return;
                }
                ResolutionPartsKt.resolveKotlinArgument(resolutionCandidate, externalArgument, resolutionCandidate.getResolvedCall().getArgumentToCandidateParameter().get(externalArgument), ReceiverInfo.INSTANCE.getNotReceiver());
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.EagerResolveOfCallableReferences
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                List<ResolvedAtom> subResolvedAtoms = resolutionCandidate.getSubResolvedAtoms();
                ArrayList arrayList = new ArrayList();
                for (Object obj : subResolvedAtoms) {
                    if (obj instanceof EagerCallableReferenceAtom) {
                        arrayList.add(obj);
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    resolutionCandidate.getCallComponents().getCallableReferenceArgumentResolver().processCallableReferenceArgument(resolutionCandidate.getSystem().getBuilder(), (EagerCallableReferenceAtom) it.next(), resolutionCandidate, resolutionCandidate.getResolutionCallbacks());
                }
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CompatibilityOfPartiallyApplicableSamConversion
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                ValueParameterDescriptor valueParameterDescriptor;
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                if (resolutionCandidate.getResolvedCall().getArgumentsWithConversion().isEmpty() || resolutionCandidate.getResolvedCall().getArgumentsWithConversion().size() == resolutionCandidate.getResolvedCall().get_candidateDescriptor().getValueParameters().size()) {
                    return;
                }
                for (KotlinCallArgument kotlinCallArgument : resolutionCandidate.getResolvedCall().getAtom().getArgumentsInParenthesis()) {
                    if (resolutionCandidate.getResolvedCall().getArgumentsWithConversion().get(kotlinCallArgument) == null && (valueParameterDescriptor = resolutionCandidate.getResolvedCall().getArgumentToCandidateParameter().get(kotlinCallArgument)) != null) {
                        if (SamTypeConversions.INSTANCE.isJavaParameterCanBeConverted(resolutionCandidate, ArgumentsUtilsKt.getExpectedType(kotlinCallArgument, valueParameterDescriptor, resolutionCandidate.getCallComponents().getLanguageVersionSettings()))) {
                            ResolvedCallAtomsKt.markCandidateForCompatibilityResolve$default(resolutionCandidate, false, 1, null);
                            return;
                        }
                    }
                }
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.PostponedVariablesInitializerResolutionPart
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                ConstraintSystemBuilder builder = resolutionCandidate.getSystem().getBuilder();
                for (Map.Entry<KotlinCallArgument, ValueParameterDescriptor> entry : resolutionCandidate.getResolvedCall().getArgumentToCandidateParameter().entrySet()) {
                    KotlinCallArgument key = entry.getKey();
                    ValueParameterDescriptor value = entry.getValue();
                    if (resolutionCandidate.getCallComponents().getStatelessCallbacks().isBuilderInferenceCall(key, value)) {
                        KotlinType type = value.getType();
                        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(type);
                        if (receiverTypeFromFunctionType != null) {
                            boolean zSupportsFeature = resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.UseBuilderInferenceOnlyIfNeeded);
                            if (key instanceof LambdaKotlinCallArgument) {
                                LambdaKotlinCallArgument lambdaKotlinCallArgument = (LambdaKotlinCallArgument) key;
                                if (!lambdaKotlinCallArgument.getHasBuilderInferenceAnnotation()) {
                                    lambdaKotlinCallArgument.setHasBuilderInferenceAnnotation(true);
                                }
                            }
                            if (!zSupportsFeature) {
                                for (final TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor : resolutionCandidate.getResolvedCall().getFreshVariablesSubstitutor().getFreshVariables()) {
                                    if (!(resolutionCandidate.getResolvedCall().getTypeArgumentMappingByOriginal().getTypeArgument(typeVariableFromCallableDescriptor.getOriginalTypeParameter()) instanceof SimpleTypeArgument)) {
                                        TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor2 = typeVariableFromCallableDescriptor;
                                        if (!builder.isPostponedTypeVariable(typeVariableFromCallableDescriptor2) && TypeUtilsKt.contains(receiverTypeFromFunctionType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.PostponedVariablesInitializerResolutionPart.process.1
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public final Boolean invoke(UnwrappedType it) {
                                                Intrinsics.checkNotNullParameter(it, "it");
                                                return Boolean.valueOf(Intrinsics.areEqual(it.getConstructor(), typeVariableFromCallableDescriptor.getOriginalTypeParameter().getTypeConstructor()));
                                            }
                                        })) {
                                            builder.markPostponedVariable(typeVariableFromCallableDescriptor2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckContextReceiversResolutionPart
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                if (resolutionCandidate.getResolvedCall().get_candidateDescriptor().getContextReceiverParameters().isEmpty()) {
                    return;
                }
                if (!resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.ContextReceivers)) {
                    resolutionCandidate.addDiagnostic(new UnsupportedContextualDeclarationCall());
                    return;
                }
                Sequence<LexicalScope> sequenceFilter = SequencesKt.filter(ScopeUtilsKt.getParentsWithSelf(resolutionCandidate.getScopeTower().getLexicalScope()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckContextReceiversResolutionPart$process$$inlined$filterIsInstance$1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(Object obj) {
                        return Boolean.valueOf(obj instanceof LexicalScope);
                    }
                });
                Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
                ArrayList arrayList = new ArrayList();
                for (LexicalScope lexicalScope : sequenceFilter) {
                    ReceiverValueWithSmartCastInfo implicitReceiver = resolutionCandidate.getScopeTower().getImplicitReceiver(lexicalScope);
                    if (implicitReceiver != null) {
                        arrayList.add(CollectionsKt.listOf(implicitReceiver));
                    }
                    List<ReceiverValueWithSmartCastInfo> contextReceivers = resolutionCandidate.getScopeTower().getContextReceivers(lexicalScope);
                    if (!contextReceivers.isEmpty()) {
                        arrayList.add(contextReceivers);
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                for (ReceiverParameterDescriptor receiverParameterDescriptor : resolutionCandidate.getResolvedCall().get_candidateDescriptor().getContextReceiverParameters()) {
                    Intrinsics.checkNotNull(receiverParameterDescriptor);
                    SimpleKotlinCallArgument simpleKotlinCallArgumentFindContextReceiver = findContextReceiver(resolutionCandidate, arrayList, receiverParameterDescriptor);
                    if (simpleKotlinCallArgumentFindContextReceiver == null) {
                        return;
                    } else {
                        arrayList2.add(simpleKotlinCallArgumentFindContextReceiver);
                    }
                }
                resolutionCandidate.getResolvedCall().setContextReceiversArguments(arrayList2);
            }

            private final SimpleKotlinCallArgument findContextReceiver(ResolutionCandidate resolutionCandidate, List<? extends List<ReceiverValueWithSmartCastInfo>> list, ReceiverParameterDescriptor receiverParameterDescriptor) {
                ConstraintSystemBuilder builder = resolutionCandidate.getSystem().getBuilder();
                for (List<ReceiverValueWithSmartCastInfo> list2 : list) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<T> it = list2.iterator();
                    while (it.hasNext()) {
                        ApplicableContextReceiverArgumentWithConstraint receiverArgumentWithConstraintIfCompatible = ResolutionPartsKt.getReceiverArgumentWithConstraintIfCompatible(resolutionCandidate, ReceiverExpressionKotlinCallArgument.Companion.invoke$default(ReceiverExpressionKotlinCallArgument.INSTANCE, (ReceiverValueWithSmartCastInfo) it.next(), false, false, 6, null), receiverParameterDescriptor);
                        if (receiverArgumentWithConstraintIfCompatible != null) {
                            arrayList.add(receiverArgumentWithConstraintIfCompatible);
                        }
                    }
                    List list3 = CollectionsKt.toList(arrayList);
                    if (list3.size() == 1) {
                        ApplicableContextReceiverArgumentWithConstraint applicableContextReceiverArgumentWithConstraint = (ApplicableContextReceiverArgumentWithConstraint) CollectionsKt.single(list3);
                        SimpleKotlinCallArgument argument = applicableContextReceiverArgumentWithConstraint.getArgument();
                        UnwrappedType argumentType = applicableContextReceiverArgumentWithConstraint.getArgumentType();
                        builder.addSubtypeConstraint(argumentType, applicableContextReceiverArgumentWithConstraint.getExpectedType(), applicableContextReceiverArgumentWithConstraint.getPosition());
                        return argument;
                    }
                    if (list3.size() > 1) {
                        resolutionCandidate.addDiagnostic(new MultipleArgumentsApplicableForContextReceiver(receiverParameterDescriptor));
                        return null;
                    }
                }
                resolutionCandidate.addDiagnostic(new NoContextReceiver(receiverParameterDescriptor));
                return null;
            }
        }, CheckIncompatibleTypeVariableUpperBounds.INSTANCE);
        FUNCTION = kotlinCallKind;
        ResolutionPart[] resolutionPartArr = (ResolutionPart[]) kotlinCallKind.resolutionSequence.toArray(new ResolutionPart[0]);
        INVOKE = new KotlinCallKind("INVOKE", 2, (ResolutionPart[]) Arrays.copyOf(resolutionPartArr, resolutionPartArr.length));
        CALLABLE_REFERENCE = new KotlinCallKind("CALLABLE_REFERENCE", 3, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckVisibility
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                ReceiverValue receiverValue;
                ReceiverValueWithSmartCastInfo receiver;
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                DeclarationDescriptor ownerDescriptor = resolutionCandidate.getScopeTower().getLexicalScope().getOwnerDescriptor();
                SimpleKotlinCallArgument dispatchReceiverArgument = resolutionCandidate.getResolvedCall().getDispatchReceiverArgument();
                if (dispatchReceiverArgument == null || (receiver = dispatchReceiverArgument.getReceiver()) == null || (receiverValue = receiver.getReceiverValue()) == null) {
                    receiverValue = DescriptorVisibilities.ALWAYS_SUITABLE_RECEIVER;
                }
                DeclarationDescriptorWithVisibility declarationDescriptorWithVisibilityFindInvisibleMember = DescriptorVisibilityUtils.findInvisibleMember(receiverValue, resolutionCandidate.getResolvedCall().get_candidateDescriptor(), ownerDescriptor, resolutionCandidate.getCallComponents().getLanguageVersionSettings());
                if (declarationDescriptorWithVisibilityFindInvisibleMember == null) {
                    return;
                }
                if (dispatchReceiverArgument instanceof ExpressionKotlinCallArgument) {
                    ExpressionKotlinCallArgument expressionKotlinCallArgument = (ExpressionKotlinCallArgument) dispatchReceiverArgument;
                    if (DescriptorVisibilityUtils.findInvisibleMember(SmartCastUtilKt.getReceiverValueWithSmartCast(receiverValue, ArgumentsUtilsKt.getStableType(expressionKotlinCallArgument.getReceiver())), resolutionCandidate.getResolvedCall().get_candidateDescriptor(), ownerDescriptor, resolutionCandidate.getCallComponents().getLanguageVersionSettings()) == null) {
                        resolutionCandidate.addDiagnostic(new SmartCastDiagnostic(expressionKotlinCallArgument, ArgumentsUtilsKt.getStableType(expressionKotlinCallArgument.getReceiver()), resolutionCandidate.getResolvedCall().getAtom()));
                        return;
                    }
                }
                resolutionCandidate.addDiagnostic(new VisibilityError(declarationDescriptorWithVisibilityFindInvisibleMember));
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.NoTypeArguments
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                resolutionCandidate.getResolvedCall().getAtom().getTypeArguments().isEmpty();
                resolutionCandidate.getResolvedCall().setTypeArgumentMappingByOriginal(TypeArgumentsToParametersMapper.TypeArgumentsMapping.NoExplicitArguments.INSTANCE);
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.NoArguments
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                resolutionCandidate.getResolvedCall().getAtom().getArgumentsInParenthesis().isEmpty();
                resolutionCandidate.getResolvedCall().getAtom().getExternalArgument();
                resolutionCandidate.getResolvedCall().setArgumentMappingByOriginal(MapsKt.emptyMap());
                resolutionCandidate.getResolvedCall().setArgumentToCandidateParameter(MapsKt.emptyMap());
            }
        }, CreateFreshVariablesSubstitutor.INSTANCE, CollectionTypeVariableUsagesInfo.INSTANCE, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckReceivers
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public int workCount(ResolutionCandidate resolutionCandidate) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                return 2;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                if (i == 0) {
                    checkReceiver(resolutionCandidate, resolutionCandidate.getResolvedCall().getDispatchReceiverArgument(), resolutionCandidate.getResolvedCall().get_candidateDescriptor().mo6753getDispatchReceiverParameter(), true);
                    return;
                }
                if (i != 1) {
                    return;
                }
                SimpleKotlinCallArgument extensionReceiverArgument = resolutionCandidate.getResolvedCall().getExtensionReceiverArgument();
                if (extensionReceiverArgument == null) {
                    extensionReceiverArgument = chooseExtensionReceiverCandidate(resolutionCandidate);
                    if (extensionReceiverArgument == null) {
                        return;
                    } else {
                        resolutionCandidate.getResolvedCall().setExtensionReceiverArgument(extensionReceiverArgument);
                    }
                }
                if (!resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.NoBuilderInferenceWithoutAnnotationRestriction) && (extensionReceiverArgument.getReceiver().getReceiverValue().getType() instanceof StubTypeForBuilderInference)) {
                    ReceiverParameterDescriptor extensionReceiverParameter = resolutionCandidate.getResolvedCall().get_candidateDescriptor().getExtensionReceiverParameter();
                    Intrinsics.checkNotNull(extensionReceiverParameter);
                    resolutionCandidate.addDiagnostic(new StubBuilderInferenceReceiver(extensionReceiverArgument, extensionReceiverParameter));
                }
                checkReceiver(resolutionCandidate, resolutionCandidate.getResolvedCall().getExtensionReceiverArgument(), resolutionCandidate.getResolvedCall().get_candidateDescriptor().getExtensionReceiverParameter(), false);
            }

            private final SimpleKotlinCallArgument chooseExtensionReceiverCandidate(ResolutionCandidate resolutionCandidate) {
                List<SimpleKotlinCallArgument> extensionReceiverArgumentCandidates = resolutionCandidate.getResolvedCall().getExtensionReceiverArgumentCandidates();
                List<SimpleKotlinCallArgument> list = extensionReceiverArgumentCandidates;
                if (list == null || list.isEmpty()) {
                    return null;
                }
                if (extensionReceiverArgumentCandidates.size() == 1) {
                    return (SimpleKotlinCallArgument) CollectionsKt.single((List) extensionReceiverArgumentCandidates);
                }
                ReceiverParameterDescriptor extensionReceiverParameter = resolutionCandidate.getResolvedCall().get_candidateDescriptor().getExtensionReceiverParameter();
                if (extensionReceiverParameter == null) {
                    return null;
                }
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = extensionReceiverArgumentCandidates.iterator();
                while (it.hasNext()) {
                    ApplicableContextReceiverArgumentWithConstraint receiverArgumentWithConstraintIfCompatible = ResolutionPartsKt.getReceiverArgumentWithConstraintIfCompatible(resolutionCandidate, (SimpleKotlinCallArgument) it.next(), extensionReceiverParameter);
                    if (receiverArgumentWithConstraintIfCompatible != null) {
                        arrayList.add(receiverArgumentWithConstraintIfCompatible);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                if (size == 0) {
                    resolutionCandidate.addDiagnostic(new NoMatchingContextReceiver());
                    return null;
                }
                if (size == 1) {
                    return ((ApplicableContextReceiverArgumentWithConstraint) CollectionsKt.single((List) arrayList2)).getArgument();
                }
                resolutionCandidate.addDiagnostic(new ContextReceiverAmbiguity());
                return null;
            }

            private final void checkReceiver(ResolutionCandidate resolutionCandidate, SimpleKotlinCallArgument simpleKotlinCallArgument, ReceiverParameterDescriptor receiverParameterDescriptor, boolean z) {
                ImplicitInvokeCheckStatus implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall;
                if (!(resolutionCandidate instanceof CallableReferenceResolutionCandidate)) {
                    if ((simpleKotlinCallArgument == null) != (receiverParameterDescriptor == null)) {
                        throw new IllegalStateException(("Inconsistency receiver state for call " + resolutionCandidate.getResolvedCall().getAtom() + " and candidate descriptor: " + resolutionCandidate.getResolvedCall().get_candidateDescriptor()).toString());
                    }
                }
                if (simpleKotlinCallArgument == null || receiverParameterDescriptor == null) {
                    return;
                }
                if (z) {
                    implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall = ResolutionPartsKt.checkUnsafeImplicitInvokeAfterSafeCall(resolutionCandidate, simpleKotlinCallArgument);
                } else {
                    implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall = ImplicitInvokeCheckStatus.NO_INVOKE;
                }
                ResolutionPartsKt.resolveKotlinArgument(resolutionCandidate, simpleKotlinCallArgument, receiverParameterDescriptor, new ReceiverInfo(true, implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall != ImplicitInvokeCheckStatus.UNSAFE_INVOKE_REPORTED, implicitInvokeCheckStatusCheckUnsafeImplicitInvokeAfterSafeCall == ImplicitInvokeCheckStatus.INVOKE_ON_NOT_NULL_VARIABLE, resolutionCandidate.getResolvedCall().getAtom()));
            }
        }, new ResolutionPart() { // from class: org.jetbrains.kotlin.resolve.calls.components.CheckCallableReference
            @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
            public void process(ResolutionCandidate resolutionCandidate, int i) {
                Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
                if (!(resolutionCandidate instanceof CallableReferenceResolutionCandidate)) {
                    throw new IllegalStateException("`CheckCallableReferences` resolution part is applicable only to callable reference calls".toString());
                }
                NewConstraintSystem system = resolutionCandidate.getSystem();
                if (system.getHasContradiction()) {
                    system = null;
                }
                if (system == null) {
                    return;
                }
                CallableReferenceResolutionCandidate callableReferenceResolutionCandidate = (CallableReferenceResolutionCandidate) resolutionCandidate;
                CallableReferenceResolutionKt.addConstraints(callableReferenceResolutionCandidate, system.getBuilder(), callableReferenceResolutionCandidate.getResolvedCall().getFreshVariablesSubstitutor(), callableReferenceResolutionCandidate.getKotlinCall());
            }
        }, CheckIncompatibleTypeVariableUpperBounds.INSTANCE);
        UNSUPPORTED = new KotlinCallKind("UNSUPPORTED", 4, new ResolutionPart[0]);
        KotlinCallKind[] kotlinCallKindArr$values = $values();
        $VALUES = kotlinCallKindArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(kotlinCallKindArr$values);
    }

    public final List<ResolutionPart> getResolutionSequence() {
        return this.resolutionSequence;
    }
}
