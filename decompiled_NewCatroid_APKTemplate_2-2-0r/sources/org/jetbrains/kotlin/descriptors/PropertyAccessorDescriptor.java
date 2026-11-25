package org.jetbrains.kotlin.descriptors;

import java.util.Collection;

/* JADX INFO: loaded from: classes4.dex */
public interface PropertyAccessorDescriptor extends VariableAccessorDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    PropertyAccessorDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, boolean z);

    PropertyDescriptor getCorrespondingProperty();

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    PropertyAccessorDescriptor getOriginal();

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    Collection<? extends PropertyAccessorDescriptor> getOverriddenDescriptors();

    boolean isDefault();
}
