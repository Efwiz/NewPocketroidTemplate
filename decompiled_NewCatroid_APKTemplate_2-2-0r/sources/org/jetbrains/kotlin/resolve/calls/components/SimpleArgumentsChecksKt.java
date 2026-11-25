package org.jetbrains.kotlin.resolve.calls.components;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderKt;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ReceiverConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.model.ArgumentNullabilityErrorDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedExpressionAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedSubCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SubKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.UnsafeCallError;
import org.jetbrains.kotlin.resolve.calls.model.UnstableSmartCast;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.NotNullTypeParameter;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.NewCapturedTypeKt;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeCheckerKt;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: SimpleArgumentsChecks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001\u001aF\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u00012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002\u001aN\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0003\u001a\u0004\u0018\u00010\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u000e\u001a\u0004\u0018\u00010\u00012\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u001a<\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0002¨\u0006\u001b"}, d2 = {"captureFromTypeParameterUpperBoundIfNeeded", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "argumentType", "expectedType", "checkExpressionArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "expressionArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/ExpressionKotlinCallArgument;", "diagnosticsHolder", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "isReceiver", "", "convertedType", "selectorCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "checkSimpleArgument", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "receiverInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/ReceiverInfo;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "checkSubCallArgument", "subCallArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/SubKotlinCallArgument;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SimpleArgumentsChecksKt {
    public static final ResolvedAtom checkSimpleArgument(ConstraintSystemBuilder csBuilder, SimpleKotlinCallArgument argument, UnwrappedType unwrappedType, KotlinDiagnosticsHolder diagnosticsHolder, ReceiverInfo receiverInfo, UnwrappedType unwrappedType2, InferenceSession inferenceSession, KotlinCall kotlinCall) {
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(diagnosticsHolder, "diagnosticsHolder");
        Intrinsics.checkNotNullParameter(receiverInfo, "receiverInfo");
        if (argument instanceof ExpressionKotlinCallArgument) {
            return checkExpressionArgument(csBuilder, (ExpressionKotlinCallArgument) argument, unwrappedType, diagnosticsHolder, receiverInfo.getIsReceiver(), unwrappedType2, kotlinCall);
        }
        if (argument instanceof SubKotlinCallArgument) {
            return checkSubCallArgument(csBuilder, (SubKotlinCallArgument) argument, unwrappedType, diagnosticsHolder, receiverInfo, inferenceSession);
        }
        ArgumentsUtilsKt.unexpectedArgument(argument);
        throw null;
    }

    private static final ResolvedAtom checkExpressionArgument(ConstraintSystemBuilder constraintSystemBuilder, ExpressionKotlinCallArgument expressionKotlinCallArgument, UnwrappedType unwrappedType, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, boolean z, UnwrappedType unwrappedType2, KotlinCall kotlinCall) {
        ConstraintPosition argumentConstraintPositionImpl;
        ResolvedExpressionAtom resolvedExpressionAtom = new ResolvedExpressionAtom(expressionKotlinCallArgument);
        if (unwrappedType == null) {
            return resolvedExpressionAtom;
        }
        if (unwrappedType2 == null) {
            unwrappedType2 = captureFromTypeParameterUpperBoundIfNeeded(ArgumentsUtilsKt.getStableType(expressionKotlinCallArgument.getReceiver()), unwrappedType);
        }
        UnwrappedType unwrappedType3 = unwrappedType2;
        if (z) {
            argumentConstraintPositionImpl = new ReceiverConstraintPositionImpl(expressionKotlinCallArgument, kotlinCall);
        } else {
            argumentConstraintPositionImpl = new ArgumentConstraintPositionImpl(expressionKotlinCallArgument);
        }
        ConstraintPosition constraintPosition = argumentConstraintPositionImpl;
        if ((unwrappedType instanceof NotNullTypeParameter) && unwrappedType3.getIsMarkedNullable()) {
            kotlinDiagnosticsHolder.addDiagnostic(new ArgumentNullabilityErrorDiagnostic(unwrappedType, unwrappedType3, expressionKotlinCallArgument));
        }
        if (expressionKotlinCallArgument.getIsSafeCall()) {
            UnwrappedType unwrappedTypeMakeNullableAsSpecified = unwrappedType.makeNullableAsSpecified(true);
            if (!ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, unwrappedType3, unwrappedTypeMakeNullableAsSpecified, constraintPosition)) {
                ResolvedCallAtomsKt.addDiagnosticIfNotNull(kotlinDiagnosticsHolder, checkExpressionArgument$unstableSmartCastOrSubtypeError(constraintSystemBuilder, expressionKotlinCallArgument, z, unwrappedType3, ArgumentsUtilsKt.getUnstableType(expressionKotlinCallArgument.getReceiver()), unwrappedTypeMakeNullableAsSpecified, constraintPosition));
            }
            return resolvedExpressionAtom;
        }
        UnwrappedType unwrappedType4 = unwrappedType3;
        UnwrappedType unwrappedType5 = unwrappedType;
        if (!ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, unwrappedType4, unwrappedType5, constraintPosition)) {
            if (!z) {
                ResolvedCallAtomsKt.addDiagnosticIfNotNull(kotlinDiagnosticsHolder, checkExpressionArgument$unstableSmartCastOrSubtypeError(constraintSystemBuilder, expressionKotlinCallArgument, z, unwrappedType3, ArgumentsUtilsKt.getUnstableType(expressionKotlinCallArgument.getReceiver()), unwrappedType, constraintPosition));
                return resolvedExpressionAtom;
            }
            UnwrappedType unstableType = ArgumentsUtilsKt.getUnstableType(expressionKotlinCallArgument.getReceiver());
            UnwrappedType unwrappedTypeMakeNullableAsSpecified2 = unwrappedType.makeNullableAsSpecified(true);
            if (unstableType != null && ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, unstableType, unwrappedType5, constraintPosition)) {
                kotlinDiagnosticsHolder.addDiagnostic(UnstableSmartCast.INSTANCE.invoke(expressionKotlinCallArgument, unstableType, z));
            } else if (ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, unwrappedType4, unwrappedTypeMakeNullableAsSpecified2, constraintPosition)) {
                kotlinDiagnosticsHolder.addDiagnostic(new UnsafeCallError(expressionKotlinCallArgument, false, 2, null));
            } else {
                constraintSystemBuilder.addSubtypeConstraint(unwrappedType4, unwrappedType5, constraintPosition);
            }
        }
        return resolvedExpressionAtom;
    }

    private static final KotlinCallDiagnostic checkExpressionArgument$unstableSmartCastOrSubtypeError(ConstraintSystemBuilder constraintSystemBuilder, ExpressionKotlinCallArgument expressionKotlinCallArgument, boolean z, UnwrappedType unwrappedType, UnwrappedType unwrappedType2, UnwrappedType unwrappedType3, ConstraintPosition constraintPosition) {
        if (unwrappedType2 != null && ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, unwrappedType2, unwrappedType3, constraintPosition)) {
            return UnstableSmartCast.INSTANCE.invoke(expressionKotlinCallArgument, unwrappedType2, z);
        }
        if (unwrappedType.getIsMarkedNullable()) {
            UnwrappedType unwrappedType4 = unwrappedType3;
            if (ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, unwrappedType, unwrappedType4, constraintPosition)) {
                return null;
            }
            if (ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, TypeUtilsKt.makeNotNullable(unwrappedType), unwrappedType4, constraintPosition)) {
                return new ArgumentNullabilityErrorDiagnostic(unwrappedType3, unwrappedType, expressionKotlinCallArgument);
            }
        }
        constraintSystemBuilder.addSubtypeConstraint(unwrappedType, unwrappedType3, constraintPosition);
        return null;
    }

    public static final UnwrappedType captureFromTypeParameterUpperBoundIfNeeded(UnwrappedType argumentType, UnwrappedType expectedType) {
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        TypeConstructor constructor = FlexibleTypesKt.upperIfFlexible(expectedType).getConstructor();
        UnwrappedType unwrappedType = argumentType;
        if (!(FlexibleTypesKt.lowerIfFlexible(unwrappedType).getConstructor().getThis$0() instanceof TypeParameterDescriptor)) {
            return argumentType;
        }
        Iterator<T> it = TypeUtilsKt.supertypes(FlexibleTypesKt.lowerIfFlexible(unwrappedType)).iterator();
        boolean z = false;
        Object obj = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            Object next = it.next();
            KotlinType kotlinType = (KotlinType) next;
            if ((kotlinType.getConstructor().getThis$0() instanceof ClassifierDescriptorWithTypeParameters) && NewKotlinTypeCheckerKt.hasSupertypeWithGivenTypeConstructor(kotlinType.unwrap(), constructor)) {
                if (!z) {
                    obj = next;
                    z = true;
                }
            }
            obj = null;
            break;
        }
        KotlinType kotlinType2 = (KotlinType) obj;
        if (kotlinType2 == null) {
            return argumentType;
        }
        UnwrappedType unwrappedTypeCaptureFromExpression = NewCapturedTypeKt.captureFromExpression(kotlinType2.unwrap());
        if (unwrappedTypeCaptureFromExpression == null || !SpecialTypesKt.isDefinitelyNotNullType(unwrappedType)) {
            return unwrappedTypeCaptureFromExpression == null ? argumentType : unwrappedTypeCaptureFromExpression;
        }
        return SpecialTypesKt.makeDefinitelyNotNullOrNotNull$default(unwrappedTypeCaptureFromExpression, false, 1, null);
    }

    private static final ResolvedAtom checkSubCallArgument(ConstraintSystemBuilder constraintSystemBuilder, SubKotlinCallArgument subKotlinCallArgument, UnwrappedType unwrappedType, KotlinDiagnosticsHolder kotlinDiagnosticsHolder, ReceiverInfo receiverInfo, InferenceSession inferenceSession) {
        ResolvedSubCallArgument resolvedSubCallArgument = new ResolvedSubCallArgument(subKotlinCallArgument, receiverInfo.getIsReceiver() && inferenceSession != null && inferenceSession.getResolveReceiverIndependently());
        if (unwrappedType == null) {
            return resolvedSubCallArgument;
        }
        UnwrappedType unwrappedTypeMakeNullableAsSpecified = unwrappedType.makeNullableAsSpecified(true);
        ConstraintPosition receiverConstraintPositionImpl = receiverInfo.getIsReceiver() ? new ReceiverConstraintPositionImpl(subKotlinCallArgument, subKotlinCallArgument.getCallResult().getResultCallAtom().getAtom()) : new ArgumentConstraintPositionImpl(subKotlinCallArgument);
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = constraintSystemBuilder.buildCurrentSubstitutor();
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor");
        UnwrappedType unwrappedTypeSafeSubstitute = ((NewTypeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor).safeSubstitute(subKotlinCallArgument.getReceiver().getReceiverValue().getType().unwrap());
        if (subKotlinCallArgument.getIsSafeCall()) {
            constraintSystemBuilder.addSubtypeConstraint(unwrappedTypeSafeSubstitute, unwrappedTypeMakeNullableAsSpecified, receiverConstraintPositionImpl);
            return resolvedSubCallArgument;
        }
        if (receiverInfo.getIsReceiver()) {
            UnwrappedType unwrappedType2 = unwrappedTypeSafeSubstitute;
            if (!ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, unwrappedType2, unwrappedType, receiverConstraintPositionImpl) && ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, unwrappedType2, unwrappedTypeMakeNullableAsSpecified, receiverConstraintPositionImpl)) {
                if (receiverInfo.getShouldReportUnsafeCall()) {
                    kotlinDiagnosticsHolder.addDiagnostic(new UnsafeCallError(subKotlinCallArgument, receiverInfo.getReportUnsafeCallAsUnsafeImplicitInvoke()));
                }
                return resolvedSubCallArgument;
            }
        }
        constraintSystemBuilder.addSubtypeConstraint(unwrappedTypeSafeSubstitute, unwrappedType, receiverConstraintPositionImpl);
        return resolvedSubCallArgument;
    }
}
