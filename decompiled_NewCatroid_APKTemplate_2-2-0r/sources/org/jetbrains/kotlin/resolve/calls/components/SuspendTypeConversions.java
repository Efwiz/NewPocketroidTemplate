package org.jetbrains.kotlin.resolve.calls.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: SuspendConversionUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u0004H\u0016J(\u0010\u000e\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/SuspendTypeConversions;", "Lorg/jetbrains/kotlin/resolve/calls/components/ParameterTypeConversion;", "()V", "conversionDefinitelyNotNeeded", "", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "expectedParameterType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "conversionIsNeededAfterSubtypingCheck", "conversionIsNeededBeforeSubtypingCheck", "areSuspendOnlySamConversionsSupported", "convertParameterType", "parameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuspendTypeConversions implements ParameterTypeConversion {
    public static final SuspendTypeConversions INSTANCE = new SuspendTypeConversions();

    private SuspendTypeConversions() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public boolean conversionDefinitelyNotNeeded(ResolutionCandidate candidate, KotlinCallArgument argument, UnwrappedType expectedParameterType) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(expectedParameterType, "expectedParameterType");
        return ((argument instanceof SimpleKotlinCallArgument) && !FunctionTypesKt.isSuspendFunctionType(ArgumentsUtilsKt.getStableType(((SimpleKotlinCallArgument) argument).getReceiver())) && FunctionTypesKt.isSuspendFunctionType(expectedParameterType)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public boolean conversionIsNeededBeforeSubtypingCheck(KotlinCallArgument argument, boolean areSuspendOnlySamConversionsSupported) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        if (argument instanceof SimpleKotlinCallArgument) {
            SimpleKotlinCallArgument simpleKotlinCallArgument = (SimpleKotlinCallArgument) argument;
            if (FunctionTypesKt.isFunctionType(ArgumentsUtilsKt.getStableType(simpleKotlinCallArgument.getReceiver())) || FunctionTypesKt.isKFunctionType(ArgumentsUtilsKt.getStableType(simpleKotlinCallArgument.getReceiver()))) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public boolean conversionIsNeededAfterSubtypingCheck(KotlinCallArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        return (argument instanceof SimpleKotlinCallArgument) && FunctionTypesKt.isFunctionTypeOrSubtype(ArgumentsUtilsKt.getStableType(((SimpleKotlinCallArgument) argument).getReceiver()));
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
        SimpleType simpleTypeCreateFunctionType = FunctionTypesKt.createFunctionType(builtIns, annotations, receiverTypeFromFunctionType, contextReceiverTypesFromFunctionType, arrayList, null, FunctionTypesKt.getReturnTypeFromFunctionType(unwrappedType), false);
        candidate.getResolvedCall().registerArgumentWithSuspendConversion(argument, simpleTypeCreateFunctionType);
        ResolvedCallAtomsKt.markCandidateForCompatibilityResolve$default(candidate, false, 1, null);
        return simpleTypeCreateFunctionType;
    }
}
