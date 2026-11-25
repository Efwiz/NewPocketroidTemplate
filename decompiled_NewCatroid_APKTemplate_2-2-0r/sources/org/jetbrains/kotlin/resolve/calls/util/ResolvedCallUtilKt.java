package org.jetbrains.kotlin.resolve.calls.util;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.context.CallResolutionContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.results.ResolutionStatus;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.SmartCastUtilKt;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.resolve.calls.tower.NewAbstractResolvedCall;
import org.jetbrains.kotlin.resolve.constants.IntegerLiteralTypeConstructor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassValueReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.DefinitelyNotNullType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.StubTypeForBuilderInference;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.NewCapturedType;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: resolvedCallUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0010\u0010\b\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\n\u001a\u0010\u0010\u000b\u001a\u0004\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\n\u001a\u0010\u0010\f\u001a\u0004\u0018\u00010\r*\u0006\u0012\u0002\b\u00030\n\u001a\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f*\u0006\u0012\u0002\b\u00030\n\u001a\u000e\u0010\u0010\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030\n\u001a\u000e\u0010\u0012\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030\n\u001a\u001c\u0010\u0013\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030\n2\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0002\u001a\u0016\u0010\u0015\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u000e\u0010\u0016\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030\n\u001a\n\u0010\u0017\u001a\u00020\u0011*\u00020\u0018\u001a&\u0010\u0019\u001a\u0004\u0018\u00010\u001a*\u0006\u0012\u0002\b\u00030\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0014\u001a\n\u0010\u001c\u001a\u00020\u001d*\u00020\u001e¨\u0006\u001f"}, d2 = {"getArgumentByParameterIndex", "", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "Lorg/jetbrains/kotlin/psi/KtCallElement;", "index", "", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getDispatchReceiverWithSmartCast", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getExplicitReceiverValue", "getImplicitReceiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ImplicitReceiver;", "getImplicitReceivers", "", "hasBothReceivers", "", "hasInferredReturnType", "hasSafeNullableReceiver", "Lorg/jetbrains/kotlin/resolve/calls/context/CallResolutionContext;", "hasThisOrNoDispatchReceiver", "isNewNotCompleted", "isNotSimpleCall", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "makeNullableTypeIfSafeReceiver", "Lorg/jetbrains/kotlin/types/KotlinType;", ModuleXmlParser.TYPE, "toResolutionStatus", "Lorg/jetbrains/kotlin/resolve/calls/results/ResolutionStatus;", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolvedCallUtilKt {

    /* JADX INFO: compiled from: resolvedCallUtil.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ExplicitReceiverKind.values().length];
            try {
                iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ExplicitReceiverKind.BOTH_RECEIVERS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ExplicitReceiverKind.NO_EXPLICIT_RECEIVER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[CandidateApplicability.values().length];
            try {
                iArr2[CandidateApplicability.RESOLVED.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[CandidateApplicability.RESOLVED_LOW_PRIORITY.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[CandidateApplicability.RESOLVED_WITH_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[CandidateApplicability.RESOLVED_NEED_PRESERVE_COMPATIBILITY.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[CandidateApplicability.INAPPLICABLE_WRONG_RECEIVER.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[CandidateApplicability.UNSAFE_CALL.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x005a  */
    public static final boolean hasThisOrNoDispatchReceiver(ResolvedCall<?> resolvedCall, BindingContext context) {
        ClassifierDescriptorWithTypeParameters descriptor;
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
        if (resolvedCall.getResultingDescriptor().mo6753getDispatchReceiverParameter() == null || receiverValueMo7304getDispatchReceiver == null) {
            return true;
        }
        if (receiverValueMo7304getDispatchReceiver instanceof ImplicitReceiver) {
            descriptor = ((ImplicitReceiver) receiverValueMo7304getDispatchReceiver).getDeclarationDescriptor();
        } else if (receiverValueMo7304getDispatchReceiver instanceof ClassValueReceiver) {
            descriptor = ((ClassValueReceiver) receiverValueMo7304getDispatchReceiver).getClassQualifier().getDescriptor();
        } else if (receiverValueMo7304getDispatchReceiver instanceof ExpressionReceiver) {
            KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(((ExpressionReceiver) receiverValueMo7304getDispatchReceiver).getExpression());
            if (ktExpressionDeparenthesize instanceof KtThisExpression) {
                descriptor = (DeclarationDescriptor) context.get(BindingContext.REFERENCE_TARGET, ((KtThisExpression) ktExpressionDeparenthesize).getInstanceReference());
            } else {
                descriptor = null;
            }
        } else {
            descriptor = null;
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        Intrinsics.checkNotNullExpressionValue(resultingDescriptor, "getResultingDescriptor(...)");
        return Intrinsics.areEqual(descriptor, DescriptorUtilsKt.getOwnerForEffectiveDispatchReceiverParameter(resultingDescriptor));
    }

    public static final ReceiverValue getExplicitReceiverValue(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[resolvedCall.getExplicitReceiverKind().ordinal()];
        if (i == 1) {
            ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
            Intrinsics.checkNotNull(receiverValueMo7304getDispatchReceiver);
            return receiverValueMo7304getDispatchReceiver;
        }
        if (i != 2 && i != 3) {
            return null;
        }
        ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.getExtensionReceiver();
        Intrinsics.checkNotNull(receiverValueMo7305getExtensionReceiver);
        return receiverValueMo7305getExtensionReceiver;
    }

    public static final ImplicitReceiver getImplicitReceiverValue(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        Object objFirstOrNull = CollectionsKt.firstOrNull(getImplicitReceivers(resolvedCall));
        if (objFirstOrNull instanceof ImplicitReceiver) {
            return (ImplicitReceiver) objFirstOrNull;
        }
        return null;
    }

    public static final Collection<ReceiverValue> getImplicitReceivers(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$0[resolvedCall.getExplicitReceiverKind().ordinal()];
        if (i == 1) {
            return CollectionsKt.listOfNotNull(resolvedCall.getExtensionReceiver());
        }
        if (i == 2) {
            return CollectionsKt.listOfNotNull(resolvedCall.getDispatchReceiver());
        }
        if (i == 3) {
            return CollectionsKt.emptyList();
        }
        if (i == 4) {
            return CollectionsKt.listOfNotNull((Object[]) new ReceiverValue[]{resolvedCall.getExtensionReceiver(), resolvedCall.getDispatchReceiver()});
        }
        throw new NoWhenBranchMatchedException();
    }

    private static final boolean hasSafeNullableReceiver(ResolvedCall<?> resolvedCall, CallResolutionContext<?> callResolutionContext) {
        ReceiverValue explicitReceiverValue;
        DataFlowValue dataFlowValueCreateDataFlowValue;
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        if (!CallUtilKt.isSafeCall(call) || (explicitReceiverValue = getExplicitReceiverValue(resolvedCall)) == null || (dataFlowValueCreateDataFlowValue = callResolutionContext.dataFlowValueFactory.createDataFlowValue(explicitReceiverValue, callResolutionContext)) == null) {
            return false;
        }
        return callResolutionContext.dataFlowInfo.getStableNullability(dataFlowValueCreateDataFlowValue).canBeNull();
    }

    public static final KotlinType makeNullableTypeIfSafeReceiver(ResolvedCall<?> resolvedCall, KotlinType kotlinType, CallResolutionContext<?> context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (kotlinType != null) {
            return TypeUtils.makeNullableIfNeeded(kotlinType, hasSafeNullableReceiver(resolvedCall, context));
        }
        return null;
    }

    public static final boolean hasBothReceivers(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        return (resolvedCall.getDispatchReceiver() == null || resolvedCall.getExtensionReceiver() == null) ? false : true;
    }

    public static final ReceiverValue getDispatchReceiverWithSmartCast(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        return SmartCastUtilKt.getReceiverValueWithSmartCast(resolvedCall.getDispatchReceiver(), resolvedCall.getSmartCastDispatchReceiverType());
    }

    public static final List<ValueArgument> getArgumentByParameterIndex(KtCallElement ktCallElement, int i, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktCallElement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktCallElement, context);
        if (resolvedCall == null) {
            return CollectionsKt.emptyList();
        }
        List<ValueParameterDescriptor> valueParameters = resolvedCall.getResultingDescriptor().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.getOrNull(valueParameters, i);
        if (valueParameterDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        ResolvedValueArgument resolvedValueArgument = resolvedCall.getValueArguments().get(valueParameterDescriptor);
        List<ValueArgument> arguments = resolvedValueArgument != null ? resolvedValueArgument.getArguments() : null;
        return arguments == null ? CollectionsKt.emptyList() : arguments;
    }

    public static final boolean isNotSimpleCall(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        List<TypeParameterDescriptor> typeParameters = callableDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        if (typeParameters.isEmpty()) {
            KotlinType returnType = callableDescriptor.getReturnType();
            if (!(returnType != null ? TypeUtilsKt.contains(returnType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt$isNotSimpleCall$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf((it instanceof NewCapturedType) || (it.getConstructor() instanceof IntegerLiteralTypeConstructor) || (it instanceof DefinitelyNotNullType) || (it instanceof StubTypeForBuilderInference));
                }
            }) : false)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean isNewNotCompleted(ResolvedCall<?> resolvedCall) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        return (resolvedCall instanceof NewAbstractResolvedCall) && !((NewAbstractResolvedCall) resolvedCall).getIsCompleted();
    }

    public static final boolean hasInferredReturnType(ResolvedCall<?> resolvedCall) {
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        if (isNewNotCompleted(resolvedCall) || (returnType = resolvedCall.getResultingDescriptor().getReturnType()) == null) {
            return false;
        }
        return !TypeUtilsKt.contains(returnType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.util.ResolvedCallUtilKt.hasInferredReturnType.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(ErrorUtils.isUninferredTypeVariable(it));
            }
        });
    }

    public static final ResolutionStatus toResolutionStatus(CandidateApplicability candidateApplicability) {
        Intrinsics.checkNotNullParameter(candidateApplicability, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$1[candidateApplicability.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                return ResolutionStatus.SUCCESS;
            case 5:
                return ResolutionStatus.RECEIVER_TYPE_ERROR;
            case 6:
                return ResolutionStatus.UNSAFE_CALL_ERROR;
            default:
                return ResolutionStatus.OTHER_ERROR;
        }
    }
}
