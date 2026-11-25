package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitution;

/* JADX INFO: loaded from: classes4.dex */
public interface CallableMemberDescriptor extends CallableDescriptor, MemberDescriptor {

    public interface CopyBuilder<D extends CallableMemberDescriptor> {
        D build();

        CopyBuilder<D> setCopyOverrides(boolean z);

        CopyBuilder<D> setDispatchReceiverParameter(ReceiverParameterDescriptor receiverParameterDescriptor);

        CopyBuilder<D> setKind(Kind kind);

        CopyBuilder<D> setModality(Modality modality);

        CopyBuilder<D> setName(Name name2);

        CopyBuilder<D> setOriginal(CallableMemberDescriptor callableMemberDescriptor);

        CopyBuilder<D> setOwner(DeclarationDescriptor declarationDescriptor);

        CopyBuilder<D> setPreserveSourceElement();

        CopyBuilder<D> setReturnType(KotlinType kotlinType);

        CopyBuilder<D> setSubstitution(TypeSubstitution typeSubstitution);

        CopyBuilder<D> setTypeParameters(List<TypeParameterDescriptor> list);

        CopyBuilder<D> setVisibility(DescriptorVisibility descriptorVisibility);
    }

    CallableMemberDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, Kind kind, boolean z);

    Kind getKind();

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    CallableMemberDescriptor getOriginal();

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    Collection<? extends CallableMemberDescriptor> getOverriddenDescriptors();

    CopyBuilder<? extends CallableMemberDescriptor> newCopyBuilder();

    /* JADX INFO: renamed from: setOverriddenDescriptors */
    void mo7205setOverriddenDescriptors(Collection<? extends CallableMemberDescriptor> collection);

    public enum Kind {
        DECLARATION,
        FAKE_OVERRIDE,
        DELEGATION,
        SYNTHESIZED;

        public boolean isReal() {
            return this != FAKE_OVERRIDE;
        }
    }
}
