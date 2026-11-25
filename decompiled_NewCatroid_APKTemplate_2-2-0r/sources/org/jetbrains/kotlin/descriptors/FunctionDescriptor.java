package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.mpp.FunctionSymbolMarker;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
public interface FunctionDescriptor extends CallableMemberDescriptor, FunctionSymbolMarker {
    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    FunctionDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, boolean z);

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    DeclarationDescriptor getContainingDeclaration();

    FunctionDescriptor getInitialSignatureDescriptor();

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    FunctionDescriptor getOriginal();

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    Collection<? extends FunctionDescriptor> getOverriddenDescriptors();

    boolean isHiddenForResolutionEverywhereBesideSupercalls();

    boolean isHiddenToOvercomeSignatureClash();

    boolean isInfix();

    boolean isInline();

    boolean isOperator();

    boolean isSuspend();

    boolean isTailrec();

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    CopyBuilder<? extends FunctionDescriptor> newCopyBuilder();

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    FunctionDescriptor substitute(TypeSubstitutor typeSubstitutor);

    public interface CopyBuilder<D extends FunctionDescriptor> extends CallableMemberDescriptor.CopyBuilder<D> {
        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        D build();

        <V> CopyBuilder<D> putUserData(CallableDescriptor.UserDataKey<V> userDataKey, V v);

        CopyBuilder<D> setAdditionalAnnotations(Annotations annotations);

        CopyBuilder<D> setContextReceiverParameters(List<ReceiverParameterDescriptor> list);

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setCopyOverrides(boolean z);

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setDispatchReceiverParameter(ReceiverParameterDescriptor receiverParameterDescriptor);

        CopyBuilder<D> setDropOriginalInContainingParts();

        CopyBuilder<D> setExtensionReceiverParameter(ReceiverParameterDescriptor receiverParameterDescriptor);

        CopyBuilder<D> setHiddenForResolutionEverywhereBesideSupercalls();

        CopyBuilder<D> setHiddenToOvercomeSignatureClash();

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setKind(CallableMemberDescriptor.Kind kind);

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setModality(Modality modality);

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setName(Name name2);

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setOriginal(CallableMemberDescriptor callableMemberDescriptor);

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setOwner(DeclarationDescriptor declarationDescriptor);

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setPreserveSourceElement();

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setReturnType(KotlinType kotlinType);

        CopyBuilder<D> setSignatureChange();

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setSubstitution(TypeSubstitution typeSubstitution);

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setTypeParameters(List<TypeParameterDescriptor> list);

        CopyBuilder<D> setValueParameters(List<ValueParameterDescriptor> list);

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        CopyBuilder<D> setVisibility(DescriptorVisibility descriptorVisibility);

        @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor.CopyBuilder
        /* bridge */ /* synthetic */ default CallableMemberDescriptor.CopyBuilder setTypeParameters(List list) {
            return setTypeParameters((List<TypeParameterDescriptor>) list);
        }
    }
}
