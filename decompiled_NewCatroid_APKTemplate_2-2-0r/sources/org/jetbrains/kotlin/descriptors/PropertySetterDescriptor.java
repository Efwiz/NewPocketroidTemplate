package org.jetbrains.kotlin.descriptors;

import java.util.Collection;

/* JADX INFO: loaded from: classes4.dex */
public interface PropertySetterDescriptor extends PropertyAccessorDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    PropertySetterDescriptor getOriginal();

    @Override // org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor, org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    Collection<? extends PropertySetterDescriptor> getOverriddenDescriptors();
}
