package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.mpp.PropertySymbolMarker;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
public interface PropertyDescriptor extends CallableMemberDescriptor, VariableDescriptorWithAccessors, PropertySymbolMarker {
    List<PropertyAccessorDescriptor> getAccessors();

    FieldDescriptor getBackingField();

    FieldDescriptor getDelegateField();

    PropertyGetterDescriptor getGetter();

    KotlinType getInType();

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    PropertyDescriptor getOriginal();

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor
    Collection<? extends PropertyDescriptor> getOverriddenDescriptors();

    PropertySetterDescriptor getSetter();

    boolean isSetterProjectedOut();

    @Override // org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    CallableMemberDescriptor.CopyBuilder<? extends PropertyDescriptor> newCopyBuilder();

    @Override // org.jetbrains.kotlin.descriptors.Substitutable
    PropertyDescriptor substitute(TypeSubstitutor typeSubstitutor);
}
