package org.jetbrains.kotlin.resolve.calls.components;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderKt;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemTransaction;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintKind;
import org.jetbrains.kotlin.resolve.calls.inference.model.ExplicitTypeParameterConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.ReceiverConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.UnsafeCallError;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.constants.IntegerValueTypeConstant;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: ResolutionParts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0014\u0010\t\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\nH\u0002\u001a&\u0010\f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a\u0014\u0010\u0012\u001a\u00020\u0013*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\nH\u0002¨\u0006\u0014"}, d2 = {"checkUnsafeImplicitInvokeAfterSafeCall", "Lorg/jetbrains/kotlin/resolve/calls/components/ImplicitInvokeCheckStatus;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "getReceiverArgumentWithConstraintIfCompatible", "Lorg/jetbrains/kotlin/resolve/calls/components/ApplicableContextReceiverArgumentWithConstraint;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "prepareExpectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "expectedType", "resolveKotlinArgument", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "candidateParameter", "receiverInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/ReceiverInfo;", "shouldRunConversionForConstants", "", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolutionPartsKt {

    /* JADX INFO: compiled from: ResolutionParts.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

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
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:68:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:73:0x010b  */
    /* JADX WARN: Code duplicated, block: B:83:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void resolveKotlinArgument(ResolutionCandidate resolutionCandidate, KotlinCallArgument kotlinCallArgument, ParameterDescriptor parameterDescriptor, ReceiverInfo receiverInfo) {
        UnwrappedType convertedType;
        IntegerValueTypeConstant integerValueTypeConstant;
        SimpleType unknownIntegerType;
        UnwrappedType unwrappedTypeUnwrap;
        SimpleType unknownIntegerType2;
        SimpleType unknownIntegerType3;
        ConstraintSystemBuilder builder = resolutionCandidate.getSystem().getBuilder();
        UnwrappedType expectedType = parameterDescriptor != null ? ArgumentsUtilsKt.getExpectedType(kotlinCallArgument, parameterDescriptor, resolutionCandidate.getCallComponents().getLanguageVersionSettings()) : null;
        boolean isReceiver = receiverInfo.getIsReceiver();
        TypeConversions.ConversionData conversionDataPerformCompositeConversionBeforeSubtyping = (isReceiver || parameterDescriptor == null || expectedType == null) ? null : TypeConversions.INSTANCE.performCompositeConversionBeforeSubtyping(resolutionCandidate, kotlinCallArgument, parameterDescriptor, expectedType);
        UnwrappedType convertedType2 = conversionDataPerformCompositeConversionBeforeSubtyping != null ? conversionDataPerformCompositeConversionBeforeSubtyping.getConvertedType() : null;
        if (conversionDataPerformCompositeConversionBeforeSubtyping == null || (convertedType = conversionDataPerformCompositeConversionBeforeSubtyping.getConvertedType()) == null) {
            convertedType = expectedType;
        }
        UnwrappedType unwrappedTypePrepareExpectedType = convertedType != null ? prepareExpectedType(resolutionCandidate, convertedType) : null;
        if (unwrappedTypePrepareExpectedType == null || isReceiver || !shouldRunConversionForConstants(resolutionCandidate, unwrappedTypePrepareExpectedType)) {
            integerValueTypeConstant = null;
        } else {
            IntegerValueTypeConstant integerValueTypeConstantConvertSignedConstantToUnsigned = resolutionCandidate.getResolutionCallbacks().convertSignedConstantToUnsigned(kotlinCallArgument);
            if (integerValueTypeConstantConvertSignedConstantToUnsigned != null) {
                resolutionCandidate.getResolvedCall().registerArgumentWithConstantConversion(kotlinCallArgument, integerValueTypeConstantConvertSignedConstantToUnsigned);
            }
            integerValueTypeConstant = integerValueTypeConstantConvertSignedConstantToUnsigned;
        }
        InferenceSession inferenceSession = resolutionCandidate.getResolutionCallbacks().getInferenceSession();
        if (expectedType == null || convertedType2 != null || isReceiver || ((conversionDataPerformCompositeConversionBeforeSubtyping != null && conversionDataPerformCompositeConversionBeforeSubtyping.getWasConversion()) || ((conversionDataPerformCompositeConversionBeforeSubtyping != null && conversionDataPerformCompositeConversionBeforeSubtyping.getConversionDefinitelyNotNeeded()) || builder.getHasContradiction()))) {
            resolutionCandidate.addResolvedKtPrimitive(PostponeArgumentsChecksKt.resolveKtPrimitive(builder, kotlinCallArgument, unwrappedTypePrepareExpectedType, resolutionCandidate, receiverInfo, (integerValueTypeConstant == null || (unknownIntegerType = integerValueTypeConstant.getUnknownIntegerType()) == null) ? null : unknownIntegerType.unwrap(), inferenceSession, receiverInfo.getSelectorCall()));
            return;
        }
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        ConstraintSystemTransaction constraintSystemTransactionPrepareTransaction = builder.prepareTransaction();
        ConstraintSystemBuilder constraintSystemBuilder = builder;
        ResolutionCandidate resolutionCandidate2 = resolutionCandidate;
        ResolvedAtom resolvedAtomResolveKtPrimitive = PostponeArgumentsChecksKt.resolveKtPrimitive(builder, kotlinCallArgument, unwrappedTypePrepareExpectedType, resolutionCandidate2, receiverInfo, (integerValueTypeConstant == null || (unknownIntegerType3 = integerValueTypeConstant.getUnknownIntegerType()) == null) ? null : unknownIntegerType3.unwrap(), inferenceSession, (128 & 128) != 0 ? null : null);
        if (!constraintSystemBuilder.getHasContradiction()) {
            resolutionCandidate.addResolvedKtPrimitive(resolvedAtomResolveKtPrimitive);
        } else {
            UnwrappedType unwrappedTypePerformCompositeConversionAfterSubtyping = TypeConversions.INSTANCE.performCompositeConversionAfterSubtyping(resolutionCandidate, kotlinCallArgument, parameterDescriptor, expectedType);
            objectRef.element = unwrappedTypePerformCompositeConversionAfterSubtyping != null ? prepareExpectedType(resolutionCandidate, unwrappedTypePerformCompositeConversionAfterSubtyping) : 0;
            if (objectRef.element == 0) {
                resolutionCandidate.addResolvedKtPrimitive(resolvedAtomResolveKtPrimitive);
            } else {
                constraintSystemTransactionPrepareTransaction.rollbackTransaction();
            }
            if (objectRef.element != 0) {
                UnwrappedType unwrappedType = (UnwrappedType) objectRef.element;
                if (integerValueTypeConstant != null || (unknownIntegerType2 = integerValueTypeConstant.getUnknownIntegerType()) == null) {
                    unwrappedTypeUnwrap = null;
                } else {
                    unwrappedTypeUnwrap = unknownIntegerType2.unwrap();
                }
                resolutionCandidate.addResolvedKtPrimitive(PostponeArgumentsChecksKt.resolveKtPrimitive(builder, kotlinCallArgument, unwrappedType, resolutionCandidate2, receiverInfo, unwrappedTypeUnwrap, inferenceSession, (128 & 128) != 0 ? null : null));
            }
        }
        constraintSystemTransactionPrepareTransaction.closeTransaction();
        if (objectRef.element != 0) {
            UnwrappedType unwrappedType2 = (UnwrappedType) objectRef.element;
            if (integerValueTypeConstant != null) {
                unwrappedTypeUnwrap = null;
            } else {
                unwrappedTypeUnwrap = null;
            }
            resolutionCandidate.addResolvedKtPrimitive(PostponeArgumentsChecksKt.resolveKtPrimitive(builder, kotlinCallArgument, unwrappedType2, resolutionCandidate2, receiverInfo, unwrappedTypeUnwrap, inferenceSession, (128 & 128) != 0 ? null : null));
        }
    }

    private static final boolean shouldRunConversionForConstants(ResolutionCandidate resolutionCandidate, UnwrappedType unwrappedType) {
        VariableWithConstraints variableWithConstraints;
        if (UnsignedTypes.isUnsignedType(unwrappedType)) {
            return true;
        }
        ConstraintSystemBuilder builder = resolutionCandidate.getSystem().getBuilder();
        if (!builder.isTypeVariable(unwrappedType) || (variableWithConstraints = builder.currentStorage().getNotFixedTypeVariables().get(unwrappedType.getConstructor())) == null) {
            return false;
        }
        List<Constraint> constraints = variableWithConstraints.getConstraints();
        if (!(constraints instanceof Collection) || !constraints.isEmpty()) {
            for (Constraint constraint : constraints) {
                if (constraint.getKind() == ConstraintKind.EQUALITY && (constraint.getPosition().getFrom() instanceof ExplicitTypeParameterConstraintPositionImpl)) {
                    KotlinTypeMarker type = constraint.getType();
                    Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.types.UnwrappedType");
                    if (UnsignedTypes.isUnsignedType((UnwrappedType) type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ImplicitInvokeCheckStatus checkUnsafeImplicitInvokeAfterSafeCall(ResolutionCandidate resolutionCandidate, SimpleKotlinCallArgument simpleKotlinCallArgument) {
        SimpleKotlinCallArgument dispatchReceiverArgument;
        ResolutionCandidate variableCandidateIfInvoke = resolutionCandidate.getVariableCandidateIfInvoke();
        if (variableCandidateIfInvoke == null) {
            return ImplicitInvokeCheckStatus.NO_INVOKE;
        }
        MutableResolvedCallAtom resolvedCall = variableCandidateIfInvoke.getResolvedCall();
        int i = WhenMappings.$EnumSwitchMapping$0[resolvedCall.getExplicitReceiverKind().ordinal()];
        if (i == 1) {
            dispatchReceiverArgument = resolvedCall.getDispatchReceiverArgument();
        } else {
            if (i != 2 && i != 3) {
                if (i == 4) {
                    return ImplicitInvokeCheckStatus.INVOKE_ON_NOT_NULL_VARIABLE;
                }
                throw new NoWhenBranchMatchedException();
            }
            dispatchReceiverArgument = resolvedCall.getExtensionReceiverArgument();
        }
        if (dispatchReceiverArgument == null) {
            throw new IllegalStateException("Receiver kind does not match receiver argument".toString());
        }
        if (dispatchReceiverArgument.getIsSafeCall() && KotlinTypeKt.isNullable(ArgumentsUtilsKt.getStableType(dispatchReceiverArgument.getReceiver())) && resolutionCandidate.getResolvedCall().get_candidateDescriptor().getTypeParameters().isEmpty()) {
            resolutionCandidate.addDiagnostic(new UnsafeCallError(simpleKotlinCallArgument, true));
            return ImplicitInvokeCheckStatus.UNSAFE_INVOKE_REPORTED;
        }
        return ImplicitInvokeCheckStatus.INVOKE_ON_NOT_NULL_VARIABLE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final UnwrappedType prepareExpectedType(ResolutionCandidate resolutionCandidate, UnwrappedType unwrappedType) {
        return resolutionCandidate.getResolvedCall().getKnownParametersSubstitutor().safeSubstitute(resolutionCandidate.getResolvedCall().getFreshVariablesSubstitutor().safeSubstitute(unwrappedType));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ApplicableContextReceiverArgumentWithConstraint getReceiverArgumentWithConstraintIfCompatible(ResolutionCandidate resolutionCandidate, SimpleKotlinCallArgument simpleKotlinCallArgument, ParameterDescriptor parameterDescriptor) {
        ConstraintSystemBuilder builder = resolutionCandidate.getSystem().getBuilder();
        SimpleKotlinCallArgument simpleKotlinCallArgument2 = simpleKotlinCallArgument;
        UnwrappedType unwrappedTypePrepareExpectedType = prepareExpectedType(resolutionCandidate, ArgumentsUtilsKt.getExpectedType(simpleKotlinCallArgument2, parameterDescriptor, resolutionCandidate.getCallComponents().getLanguageVersionSettings()));
        UnwrappedType unwrappedTypeCaptureFromTypeParameterUpperBoundIfNeeded = SimpleArgumentsChecksKt.captureFromTypeParameterUpperBoundIfNeeded(ArgumentsUtilsKt.getStableType(simpleKotlinCallArgument.getReceiver()), unwrappedTypePrepareExpectedType);
        ReceiverConstraintPositionImpl receiverConstraintPositionImpl = new ReceiverConstraintPositionImpl(simpleKotlinCallArgument2, resolutionCandidate.getResolvedCall().getAtom());
        if (ConstraintSystemBuilderKt.isSubtypeConstraintCompatible(builder, unwrappedTypeCaptureFromTypeParameterUpperBoundIfNeeded, unwrappedTypePrepareExpectedType, receiverConstraintPositionImpl)) {
            return new ApplicableContextReceiverArgumentWithConstraint(simpleKotlinCallArgument, unwrappedTypeCaptureFromTypeParameterUpperBoundIfNeeded, unwrappedTypePrepareExpectedType, receiverConstraintPositionImpl);
        }
        return null;
    }
}
