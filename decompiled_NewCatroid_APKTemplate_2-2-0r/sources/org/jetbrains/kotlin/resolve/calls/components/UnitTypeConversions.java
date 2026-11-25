package org.jetbrains.kotlin.resolve.calls.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: UnitTypeConversions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u0004H\u0016J(\u0010\u000e\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\nH\u0016J\u0014\u0010\u0011\u001a\u00020\u0004*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\f\u0010\u0015\u001a\u00020\u0004*\u00020\u0012H\u0002J\u0014\u0010\u0016\u001a\u00020\u0004*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/UnitTypeConversions;", "Lorg/jetbrains/kotlin/resolve/calls/components/ParameterTypeConversion;", "()V", "conversionDefinitelyNotNeeded", "", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "expectedParameterType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "conversionIsNeededAfterSubtypingCheck", "conversionIsNeededBeforeSubtypingCheck", "areSuspendOnlySamConversionsSupported", "convertParameterType", "parameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "hasUnitOrSubtypeReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "c", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemOperation;", "isUnitOrSubtype", "isUnitOrSubtypeOrVariable", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnitTypeConversions implements ParameterTypeConversion {
    public static final UnitTypeConversions INSTANCE = new UnitTypeConversions();

    private UnitTypeConversions() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public boolean conversionDefinitelyNotNeeded(ResolutionCandidate candidate, KotlinCallArgument argument, UnwrappedType expectedParameterType) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(expectedParameterType, "expectedParameterType");
        if (!(argument instanceof SimpleKotlinCallArgument)) {
            return true;
        }
        ReceiverValueWithSmartCastInfo receiver = ((SimpleKotlinCallArgument) argument).getReceiver();
        ConstraintSystemBuilder builder = candidate.getSystem().getBuilder();
        KotlinType type = receiver.getReceiverValue().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        ConstraintSystemBuilder constraintSystemBuilder = builder;
        if (hasUnitOrSubtypeReturnType(type, constraintSystemBuilder)) {
            return true;
        }
        Set<KotlinType> typesFromSmartCasts = receiver.getTypesFromSmartCasts();
        if (!(typesFromSmartCasts instanceof Collection) || !typesFromSmartCasts.isEmpty()) {
            Iterator<T> it = typesFromSmartCasts.iterator();
            while (it.hasNext()) {
                if (INSTANCE.hasUnitOrSubtypeReturnType((KotlinType) it.next(), constraintSystemBuilder)) {
                    return true;
                }
            }
        }
        UnwrappedType unwrappedType = expectedParameterType;
        return (FunctionTypesKt.isBuiltinFunctionalType(unwrappedType) && TypeUtilsKt.isUnit(FunctionTypesKt.getReturnTypeFromFunctionType(unwrappedType))) ? false : true;
    }

    private final boolean hasUnitOrSubtypeReturnType(KotlinType kotlinType, ConstraintSystemOperation constraintSystemOperation) {
        if (FunctionTypesKt.isFunctionOrKFunctionTypeWithAnySuspendability(kotlinType)) {
            KotlinType nullableAnyType = ((TypeProjection) CollectionsKt.last((List) kotlinType.getArguments())).getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
            if (isUnitOrSubtypeOrVariable(nullableAnyType, constraintSystemOperation)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isUnitOrSubtypeOrVariable(KotlinType kotlinType, ConstraintSystemOperation constraintSystemOperation) {
        return isUnitOrSubtype(kotlinType) || constraintSystemOperation.isTypeVariable(kotlinType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isUnitOrSubtype(KotlinType kotlinType) {
        return TypeUtilsKt.isUnit(kotlinType) || DynamicTypesKt.isDynamic(kotlinType) || TypeUtilsKt.isNothing(kotlinType);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public boolean conversionIsNeededBeforeSubtypingCheck(KotlinCallArgument argument, boolean areSuspendOnlySamConversionsSupported) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        return (argument instanceof SimpleKotlinCallArgument) && FunctionTypesKt.isFunctionType(ArgumentsUtilsKt.getStableType(((SimpleKotlinCallArgument) argument).getReceiver()));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public boolean conversionIsNeededAfterSubtypingCheck(KotlinCallArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        if (!(argument instanceof SimpleKotlinCallArgument)) {
            return false;
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        boolean zIsFunctionTypeOrSubtype = FunctionTypesKt.isFunctionTypeOrSubtype(ArgumentsUtilsKt.getStableType(((SimpleKotlinCallArgument) argument).getReceiver()), new Function1<KotlinType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.components.UnitTypeConversions$conversionIsNeededAfterSubtypingCheck$hasReturnTypeInSubtypes$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                booleanRef.element = true;
                return Boolean.valueOf(UnitTypeConversions.INSTANCE.isUnitOrSubtype(FunctionTypesKt.getReturnTypeFromFunctionType(it)));
            }
        });
        if (booleanRef.element) {
            return !zIsFunctionTypeOrSubtype;
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public UnwrappedType convertParameterType(ResolutionCandidate candidate, KotlinCallArgument argument, ParameterDescriptor parameter, UnwrappedType expectedParameterType) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(expectedParameterType, "expectedParameterType");
        KotlinBuiltIns builtIns = candidate.getCallComponents().getBuiltIns();
        Annotations annotations = expectedParameterType.getAnnotations();
        UnwrappedType unwrappedType = expectedParameterType;
        KotlinType receiverTypeFromFunctionType = FunctionTypesKt.getReceiverTypeFromFunctionType(unwrappedType);
        List<KotlinType> contextReceiverTypesFromFunctionType = FunctionTypesKt.getContextReceiverTypesFromFunctionType(unwrappedType);
        List<TypeProjection> valueParameterTypesFromFunctionType = FunctionTypesKt.getValueParameterTypesFromFunctionType(unwrappedType);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameterTypesFromFunctionType, 10));
        Iterator<T> it = valueParameterTypesFromFunctionType.iterator();
        while (it.hasNext()) {
            KotlinType nullableAnyType = ((TypeProjection) it.next()).getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
            arrayList.add(nullableAnyType);
        }
        SimpleType nullableAnyType2 = candidate.getCallComponents().getBuiltIns().getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType2, "getNullableAnyType(...)");
        SimpleType simpleTypeCreateFunctionType = FunctionTypesKt.createFunctionType(builtIns, annotations, receiverTypeFromFunctionType, contextReceiverTypesFromFunctionType, arrayList, null, nullableAnyType2, FunctionTypesKt.isSuspendFunctionType(unwrappedType));
        candidate.getResolvedCall().registerArgumentWithUnitConversion(argument, simpleTypeCreateFunctionType);
        ResolvedCallAtomsKt.markCandidateForCompatibilityResolve$default(candidate, false, 1, null);
        return simpleTypeCreateFunctionType;
    }
}
