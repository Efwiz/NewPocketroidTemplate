package org.jetbrains.kotlin.load.java;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.load.java.lazy.types.RawSubstitution;
import org.jetbrains.kotlin.load.java.lazy.types.RawTypeImpl;
import org.jetbrains.kotlin.resolve.ExternalOverridabilityCondition;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ErasedOverridabilityCondition.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/ErasedOverridabilityCondition;", "Lorg/jetbrains/kotlin/resolve/ExternalOverridabilityCondition;", "()V", "getContract", "Lorg/jetbrains/kotlin/resolve/ExternalOverridabilityCondition$Contract;", "isOverridable", "Lorg/jetbrains/kotlin/resolve/ExternalOverridabilityCondition$Result;", "superDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "subDescriptor", "subClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErasedOverridabilityCondition implements ExternalOverridabilityCondition {

    /* JADX INFO: compiled from: ErasedOverridabilityCondition.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[OverridingUtil.OverrideCompatibilityInfo.Result.values().length];
            try {
                iArr[OverridingUtil.OverrideCompatibilityInfo.Result.OVERRIDABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.ExternalOverridabilityCondition
    public ExternalOverridabilityCondition.Result isOverridable(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor, ClassDescriptor subClassDescriptor) {
        Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
        Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
        if (subDescriptor instanceof JavaMethodDescriptor) {
            JavaMethodDescriptor javaMethodDescriptor = (JavaMethodDescriptor) subDescriptor;
            List<TypeParameterDescriptor> typeParameters = javaMethodDescriptor.getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            if (typeParameters.isEmpty()) {
                OverridingUtil.OverrideCompatibilityInfo basicOverridabilityProblem = OverridingUtil.getBasicOverridabilityProblem(superDescriptor, subDescriptor);
                if ((basicOverridabilityProblem != null ? basicOverridabilityProblem.getResult() : null) != null) {
                    return ExternalOverridabilityCondition.Result.UNKNOWN;
                }
                List<ValueParameterDescriptor> valueParameters = javaMethodDescriptor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                Sequence map = SequencesKt.map(CollectionsKt.asSequence(valueParameters), new Function1<ValueParameterDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.load.java.ErasedOverridabilityCondition$isOverridable$signatureTypes$1
                    @Override // kotlin.jvm.functions.Function1
                    public final KotlinType invoke(ValueParameterDescriptor valueParameterDescriptor) {
                        return valueParameterDescriptor.getType();
                    }
                });
                KotlinType returnType = javaMethodDescriptor.getReturnType();
                Intrinsics.checkNotNull(returnType);
                Sequence sequencePlus = SequencesKt.plus((Sequence<? extends KotlinType>) map, returnType);
                ReceiverParameterDescriptor extensionReceiverParameter = javaMethodDescriptor.getExtensionReceiverParameter();
                for (KotlinType kotlinType : SequencesKt.plus(sequencePlus, (Iterable) CollectionsKt.listOfNotNull(extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null))) {
                    if (!kotlinType.getArguments().isEmpty() && !(kotlinType.unwrap() instanceof RawTypeImpl)) {
                        return ExternalOverridabilityCondition.Result.UNKNOWN;
                    }
                }
                FunctionDescriptor functionDescriptorSubstitute = superDescriptor.substitute(new RawSubstitution(null, 1, null).buildSubstitutor());
                if (functionDescriptorSubstitute == null) {
                    return ExternalOverridabilityCondition.Result.UNKNOWN;
                }
                if (functionDescriptorSubstitute instanceof SimpleFunctionDescriptor) {
                    SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) functionDescriptorSubstitute;
                    List<TypeParameterDescriptor> typeParameters2 = simpleFunctionDescriptor.getTypeParameters();
                    Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
                    if (!typeParameters2.isEmpty()) {
                        FunctionDescriptor functionDescriptorBuild = simpleFunctionDescriptor.newCopyBuilder().setTypeParameters(CollectionsKt.emptyList()).build();
                        Intrinsics.checkNotNull(functionDescriptorBuild);
                        functionDescriptorSubstitute = functionDescriptorBuild;
                    }
                }
                OverridingUtil.OverrideCompatibilityInfo.Result result = OverridingUtil.DEFAULT.isOverridableByWithoutExternalConditions(functionDescriptorSubstitute, subDescriptor, false).getResult();
                Intrinsics.checkNotNullExpressionValue(result, "getResult(...)");
                if (WhenMappings.$EnumSwitchMapping$0[result.ordinal()] == 1) {
                    return ExternalOverridabilityCondition.Result.OVERRIDABLE;
                }
                return ExternalOverridabilityCondition.Result.UNKNOWN;
            }
        }
        return ExternalOverridabilityCondition.Result.UNKNOWN;
    }

    @Override // org.jetbrains.kotlin.resolve.ExternalOverridabilityCondition
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.SUCCESS_ONLY;
    }
}
