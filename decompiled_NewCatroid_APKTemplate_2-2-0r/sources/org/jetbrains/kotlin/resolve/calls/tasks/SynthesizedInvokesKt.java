package org.jetbrains.kotlin.resolve.calls.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: synthesizedInvokes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u001a\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004\u001a\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\b¨\u0006\t"}, d2 = {"createSynthesizedFunctionWithFirstParameterAsReceiver", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptor", "createSynthesizedInvokes", "", "functions", "isSynthesizedInvoke", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SynthesizedInvokesKt {
    public static final Collection<FunctionDescriptor> createSynthesizedInvokes(Collection<? extends FunctionDescriptor> functions) {
        FunctionDescriptor functionDescriptorCreateSynthesizedFunctionWithFirstParameterAsReceiver;
        Intrinsics.checkNotNullParameter(functions, "functions");
        ArrayList arrayList = new ArrayList(1);
        for (FunctionDescriptor functionDescriptor : functions) {
            if (Intrinsics.areEqual(functionDescriptor.getShortName(), OperatorNameConventions.INVOKE) && !functionDescriptor.getValueParameters().isEmpty()) {
                DeclarationDescriptor containingDeclaration = functionDescriptor.getDeclarationDescriptor();
                Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) containingDeclaration);
                if (classId != null && FunctionTypesKt.isBuiltinFunctionClass(classId)) {
                    functionDescriptorCreateSynthesizedFunctionWithFirstParameterAsReceiver = createSynthesizedFunctionWithFirstParameterAsReceiver(functionDescriptor);
                } else {
                    Collection<? extends FunctionDescriptor> overriddenDescriptors = functionDescriptor.getOverriddenDescriptors();
                    Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                    FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) CollectionsKt.singleOrNull(overriddenDescriptors);
                    if (functionDescriptor2 == null) {
                        throw new IllegalStateException(("No single overridden invoke for " + functionDescriptor + ": " + functionDescriptor.getOverriddenDescriptors()).toString());
                    }
                    FunctionDescriptor functionDescriptorCreateSynthesizedFunctionWithFirstParameterAsReceiver2 = createSynthesizedFunctionWithFirstParameterAsReceiver(functionDescriptor2);
                    FunctionDescriptor functionDescriptorCopy = functionDescriptorCreateSynthesizedFunctionWithFirstParameterAsReceiver2.copy(functionDescriptor.getDeclarationDescriptor(), functionDescriptorCreateSynthesizedFunctionWithFirstParameterAsReceiver2.getModality(), functionDescriptorCreateSynthesizedFunctionWithFirstParameterAsReceiver2.getVisibilityImpl(), CallableMemberDescriptor.Kind.FAKE_OVERRIDE, false);
                    Intrinsics.checkNotNullExpressionValue(functionDescriptorCopy, "copy(...)");
                    DescriptorUtilsKt.setSingleOverridden(functionDescriptorCopy, functionDescriptorCreateSynthesizedFunctionWithFirstParameterAsReceiver2);
                    functionDescriptorCreateSynthesizedFunctionWithFirstParameterAsReceiver = functionDescriptorCopy;
                }
                ReceiverParameterDescriptor dispatchReceiverParameter = functionDescriptor.mo6753getDispatchReceiverParameter();
                Intrinsics.checkNotNull(dispatchReceiverParameter);
                FunctionDescriptor functionDescriptorSubstitute = functionDescriptorCreateSynthesizedFunctionWithFirstParameterAsReceiver.substitute(TypeSubstitutor.create(dispatchReceiverParameter.getType()));
                if (functionDescriptorSubstitute != null) {
                    arrayList.add(functionDescriptorSubstitute);
                }
            }
        }
        return arrayList;
    }

    private static final FunctionDescriptor createSynthesizedFunctionWithFirstParameterAsReceiver(FunctionDescriptor functionDescriptor) {
        FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> copyBuilderNewCopyBuilder = functionDescriptor.getOriginal().newCopyBuilder();
        FunctionDescriptor original = functionDescriptor.getOriginal();
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getOriginal().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        copyBuilderNewCopyBuilder.setExtensionReceiverParameter(DescriptorFactory.createExtensionReceiverParameterForCallable(original, ((ValueParameterDescriptor) CollectionsKt.first((List) valueParameters)).getType(), Annotations.INSTANCE.getEMPTY()));
        List<ValueParameterDescriptor> valueParameters2 = functionDescriptor.getOriginal().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        List<ValueParameterDescriptor> listDrop = CollectionsKt.drop(valueParameters2, 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDrop, 10));
        for (ValueParameterDescriptor valueParameterDescriptor : listDrop) {
            FunctionDescriptor original2 = functionDescriptor.getOriginal();
            Intrinsics.checkNotNullExpressionValue(original2, "getOriginal(...)");
            Name nameIdentifier = Name.identifier("p" + (valueParameterDescriptor.getIndex() + 1));
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            arrayList.add(valueParameterDescriptor.mo7204copy(original2, nameIdentifier, valueParameterDescriptor.getIndex() - 1));
        }
        copyBuilderNewCopyBuilder.setValueParameters(arrayList);
        FunctionDescriptor functionDescriptorBuild = copyBuilderNewCopyBuilder.build();
        Intrinsics.checkNotNull(functionDescriptorBuild);
        return functionDescriptorBuild;
    }

    public static final boolean isSynthesizedInvoke(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!Intrinsics.areEqual(descriptor.getShortName(), OperatorNameConventions.INVOKE) || !(descriptor instanceof FunctionDescriptor)) {
            return false;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
        while (!functionDescriptor.getKind().isReal()) {
            Collection<? extends FunctionDescriptor> overriddenDescriptors = functionDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            functionDescriptor = (FunctionDescriptor) CollectionsKt.singleOrNull(overriddenDescriptors);
            if (functionDescriptor == null) {
                return false;
            }
        }
        if (functionDescriptor.getKind() != CallableMemberDescriptor.Kind.SYNTHESIZED) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = functionDescriptor.getDeclarationDescriptor();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        return FunctionTypesKt.getFunctionTypeKind(containingDeclaration) != null;
    }
}
