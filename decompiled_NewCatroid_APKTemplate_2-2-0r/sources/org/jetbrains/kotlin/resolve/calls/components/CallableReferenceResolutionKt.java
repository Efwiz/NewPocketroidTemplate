package org.jetbrains.kotlin.resolve.calls.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation;
import org.jetbrains.kotlin.resolve.calls.inference.components.FreshVariableNewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.model.ArgumentConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.CallableReferenceConstraintPositionImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCall;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceResolutionAtom;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferencesCandidateFactory;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.LHSResult;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.tower.PrioritizedCompositeScopeTowerProcessor;
import org.jetbrains.kotlin.resolve.calls.tower.SamePriorityCompositeScopeTowerProcessor;
import org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerProcessor;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.DetailedReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.NewCapturedTypeKt;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.model.TypeSystemContextKt;
import org.jetbrains.kotlin.types.model.TypeVariance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: CallableReferenceResolution.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u001a\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\bH\u0002\u001a\"\u0010\u000b\u001a\u00020\f*\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012\u001a$\u0010\u0013\u001a\u00020\f*\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002\u001a0\u0010\u0018\u001a\u00020\f*\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002¨\u0006\u001e"}, d2 = {"createCallableReferenceProcessor", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerProcessor;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "factory", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferencesCandidateFactory;", "extractInputOutputTypesFromCallableReferenceExpectedType", "Lorg/jetbrains/kotlin/resolve/calls/components/InputOutputTypes;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "extractInputOutputTypesFromFunctionType", "functionType", "addConstraints", "", "constraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemOperation;", "substitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/FreshVariableNewTypeSubstitutor;", "callableReference", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceResolutionAtom;", "addLhsTypeConstraint", "lhsType", "Lorg/jetbrains/kotlin/types/KotlinType;", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "addReceiverConstraint", "toFreshSubstitutor", "receiverArgument", "Lorg/jetbrains/kotlin/resolve/calls/components/CallableReceiver;", "receiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceResolutionKt {

    /* JADX INFO: compiled from: CallableReferenceResolution.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            try {
                iArr[TypeVariance.INV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariance.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariance.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ScopeTowerProcessor<CallableReferenceResolutionCandidate> createCallableReferenceProcessor(CallableReferencesCandidateFactory factory) {
        ReceiverValueWithSmartCastInfo classValueReceiverWithSmartCastInfo;
        SimpleKotlinCallArgument lshCallArgument;
        Intrinsics.checkNotNullParameter(factory, "factory");
        LHSResult lhsResult = factory.getKotlinCall().getLhsResult();
        Object objCreateCallableProcessor = null;
        if (Intrinsics.areEqual(lhsResult, LHSResult.Empty.INSTANCE) ? true : Intrinsics.areEqual(lhsResult, LHSResult.Error.INSTANCE) ? true : lhsResult instanceof LHSResult.Expression) {
            LHSResult.Expression expression = lhsResult instanceof LHSResult.Expression ? (LHSResult.Expression) lhsResult : null;
            if (expression != null && (lshCallArgument = expression.getLshCallArgument()) != null) {
                objCreateCallableProcessor = lshCallArgument.getReceiver();
            }
            return factory.createCallableProcessor((DetailedReceiver) objCreateCallableProcessor);
        }
        if (lhsResult instanceof LHSResult.Type) {
            LHSResult.Type type = (LHSResult.Type) lhsResult;
            QualifierReceiver qualifier = type.getQualifier();
            objCreateCallableProcessor = qualifier != null ? factory.createCallableProcessor(qualifier) : null;
            SamePriorityCompositeScopeTowerProcessor samePriorityCompositeScopeTowerProcessorCreateCallableProcessor = factory.createCallableProcessor(type.getUnboundDetailedReceiver());
            if (objCreateCallableProcessor != null) {
                samePriorityCompositeScopeTowerProcessorCreateCallableProcessor = new SamePriorityCompositeScopeTowerProcessor(objCreateCallableProcessor, samePriorityCompositeScopeTowerProcessorCreateCallableProcessor);
            }
            QualifierReceiver qualifier2 = type.getQualifier();
            if (qualifier2 == null || (classValueReceiverWithSmartCastInfo = qualifier2.getClassValueReceiverWithSmartCastInfo()) == null) {
                return samePriorityCompositeScopeTowerProcessorCreateCallableProcessor;
            }
            return new PrioritizedCompositeScopeTowerProcessor(samePriorityCompositeScopeTowerProcessorCreateCallableProcessor, factory.createCallableProcessor(classValueReceiverWithSmartCastInfo));
        }
        if (!(lhsResult instanceof LHSResult.Object)) {
            throw new NoWhenBranchMatchedException();
        }
        LHSResult.Object object = (LHSResult.Object) lhsResult;
        return new SamePriorityCompositeScopeTowerProcessor(factory.createCallableProcessor(object.getQualifier()), factory.createCallableProcessor(object.getObjectValueReceiver()));
    }

    public static final void addConstraints(CallableReferenceResolutionCandidate callableReferenceResolutionCandidate, ConstraintSystemOperation constraintSystem, FreshVariableNewTypeSubstitutor substitutor, CallableReferenceResolutionAtom callableReference) {
        ConstraintPosition callableReferenceConstraintPositionImpl;
        Intrinsics.checkNotNullParameter(callableReferenceResolutionCandidate, "<this>");
        Intrinsics.checkNotNullParameter(constraintSystem, "constraintSystem");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(callableReference, "callableReference");
        LHSResult lhsResult = callableReference.getLhsResult();
        if (callableReference instanceof CallableReferenceKotlinCallArgument) {
            callableReferenceConstraintPositionImpl = new ArgumentConstraintPositionImpl((KotlinCallArgument) callableReference);
        } else {
            if (!(callableReference instanceof CallableReferenceKotlinCall)) {
                throw new NoWhenBranchMatchedException();
            }
            callableReferenceConstraintPositionImpl = new CallableReferenceConstraintPositionImpl((CallableReferenceKotlinCall) callableReference);
        }
        if ((lhsResult instanceof LHSResult.Type) && callableReferenceResolutionCandidate.getExpectedType() != null && !TypeUtils.noExpectedType(callableReferenceResolutionCandidate.getExpectedType()) && !DescriptorUtils.isStaticDeclaration(callableReferenceResolutionCandidate.getCandidate())) {
            DeclarationDescriptor containingDeclaration = callableReferenceResolutionCandidate.getCandidate().getContainingModule();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (!DescriptorUtilsKt.isCompanionObject(containingDeclaration)) {
                addLhsTypeConstraint(constraintSystem, ArgumentsUtilsKt.getStableType(((LHSResult.Type) lhsResult).getUnboundDetailedReceiver()), callableReferenceResolutionCandidate.getExpectedType(), callableReferenceConstraintPositionImpl);
            }
        }
        if (!ErrorUtils.isError(callableReferenceResolutionCandidate.getCandidate())) {
            addReceiverConstraint(constraintSystem, substitutor, callableReferenceResolutionCandidate.getDispatchReceiver(), callableReferenceResolutionCandidate.getCandidate().mo6753getDispatchReceiverParameter(), callableReferenceConstraintPositionImpl);
            addReceiverConstraint(constraintSystem, substitutor, callableReferenceResolutionCandidate.getExtensionReceiver(), callableReferenceResolutionCandidate.getCandidate().getExtensionReceiverParameter(), callableReferenceConstraintPositionImpl);
        }
        if (callableReferenceResolutionCandidate.getExpectedType() == null || TypeUtils.noExpectedType(callableReferenceResolutionCandidate.getExpectedType()) || constraintSystem.getHasContradiction()) {
            return;
        }
        constraintSystem.addSubtypeConstraint(substitutor.safeSubstitute(callableReferenceResolutionCandidate.getReflectionCandidateType()), callableReferenceResolutionCandidate.getExpectedType(), callableReferenceConstraintPositionImpl);
    }

    private static final void addLhsTypeConstraint(ConstraintSystemOperation constraintSystemOperation, KotlinType kotlinType, UnwrappedType unwrappedType, ConstraintPosition constraintPosition) {
        if (ReflectionTypes.INSTANCE.isNumberedTypeWithOneOrMoreNumber(unwrappedType)) {
            TypeProjection typeProjection = (TypeProjection) CollectionsKt.first((List) unwrappedType.getArguments());
            KotlinType type = typeProjection.getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            Variance projectionKind = typeProjection.getProjectionKind();
            Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
            TypeVariance typeVarianceConvertVariance = TypeSystemContextKt.convertVariance(projectionKind);
            AbstractTypeChecker abstractTypeChecker = AbstractTypeChecker.INSTANCE;
            List<TypeParameterDescriptor> parameters = unwrappedType.getConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            Variance variance = ((TypeParameterDescriptor) CollectionsKt.first((List) parameters)).getVariance();
            Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
            TypeVariance typeVarianceEffectiveVariance = abstractTypeChecker.effectiveVariance(TypeSystemContextKt.convertVariance(variance), typeVarianceConvertVariance);
            if (typeVarianceEffectiveVariance != null) {
                typeVarianceConvertVariance = typeVarianceEffectiveVariance;
            }
            int i = WhenMappings.$EnumSwitchMapping$0[typeVarianceConvertVariance.ordinal()];
            if (i == 1) {
                constraintSystemOperation.addEqualityConstraint(kotlinType, type, constraintPosition);
            } else if (i == 2) {
                constraintSystemOperation.addSubtypeConstraint(type, kotlinType, constraintPosition);
            } else {
                if (i != 3) {
                    return;
                }
                constraintSystemOperation.addSubtypeConstraint(kotlinType, type, constraintPosition);
            }
        }
    }

    private static final void addReceiverConstraint(ConstraintSystemOperation constraintSystemOperation, FreshVariableNewTypeSubstitutor freshVariableNewTypeSubstitutor, CallableReceiver callableReceiver, ReceiverParameterDescriptor receiverParameterDescriptor, ConstraintPosition constraintPosition) {
        if (callableReceiver == null || receiverParameterDescriptor == null) {
            return;
        }
        UnwrappedType unwrappedTypeSafeSubstitute = freshVariableNewTypeSubstitutor.safeSubstitute(receiverParameterDescriptor.getValue().getType().unwrap());
        UnwrappedType stableType = ArgumentsUtilsKt.getStableType(callableReceiver.getReceiver());
        UnwrappedType unwrappedTypeCaptureFromExpression = NewCapturedTypeKt.captureFromExpression(stableType);
        if (unwrappedTypeCaptureFromExpression != null) {
            stableType = unwrappedTypeCaptureFromExpression;
        }
        constraintSystemOperation.addSubtypeConstraint(stableType, unwrappedTypeSafeSubstitute, constraintPosition);
    }

    public static final InputOutputTypes extractInputOutputTypesFromCallableReferenceExpectedType(UnwrappedType unwrappedType) {
        if (unwrappedType == null) {
            return null;
        }
        UnwrappedType unwrappedType2 = unwrappedType;
        if (FunctionTypesKt.isFunctionType(unwrappedType2) || FunctionTypesKt.isSuspendFunctionType(unwrappedType2)) {
            return extractInputOutputTypesFromFunctionType(unwrappedType);
        }
        if (ReflectionTypes.INSTANCE.isBaseTypeForNumberedReferenceTypes(unwrappedType2)) {
            return new InputOutputTypes(CollectionsKt.emptyList(), ((TypeProjection) CollectionsKt.single((List) unwrappedType.getArguments())).getNullableAnyType().unwrap());
        }
        if (!ReflectionTypes.INSTANCE.isNumberedKFunction(unwrappedType2)) {
            if (!ReflectionTypes.INSTANCE.isNumberedKSuspendFunction(unwrappedType2)) {
                if (!ReflectionTypes.INSTANCE.isNumberedKPropertyOrKMutablePropertyType(unwrappedType2)) {
                    return null;
                }
                for (KotlinType kotlinType : TypeUtilsKt.supertypes(unwrappedType2)) {
                    if (FunctionTypesKt.isFunctionType(kotlinType)) {
                        return extractInputOutputTypesFromFunctionType(kotlinType.unwrap());
                    }
                }
                throw new NoSuchElementException("Collection contains no element matching the predicate.");
            }
            for (KotlinType kotlinType2 : TypeUtilsKt.immediateSupertypes(unwrappedType2)) {
                if (FunctionTypesKt.isSuspendFunctionType(kotlinType2)) {
                    return extractInputOutputTypesFromFunctionType(kotlinType2.unwrap());
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        for (KotlinType kotlinType3 : TypeUtilsKt.immediateSupertypes(unwrappedType2)) {
            if (FunctionTypesKt.isFunctionType(kotlinType3)) {
                return extractInputOutputTypesFromFunctionType(kotlinType3.unwrap());
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private static final InputOutputTypes extractInputOutputTypesFromFunctionType(UnwrappedType unwrappedType) {
        UnwrappedType unwrappedType2 = unwrappedType;
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(unwrappedType2);
        UnwrappedType unwrappedTypeUnwrap = receiverTypeFromFunctionType != null ? receiverTypeFromFunctionType.unwrap() : null;
        List<TypeProjection> valueParameterTypesFromFunctionType = FunctionTypesKt.getValueParameterTypesFromFunctionType(unwrappedType2);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameterTypesFromFunctionType, 10));
        Iterator<T> it = valueParameterTypesFromFunctionType.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeProjection) it.next()).getNullableAnyType().unwrap());
        }
        return new InputOutputTypes(CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(unwrappedTypeUnwrap), (Iterable) arrayList), FunctionTypesKt.getReturnTypeFromFunctionType(unwrappedType2).unwrap());
    }
}
