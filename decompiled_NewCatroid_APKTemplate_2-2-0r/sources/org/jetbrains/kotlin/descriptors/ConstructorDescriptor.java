package org.jetbrains.kotlin.descriptors;

import java.util.List;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: loaded from: classes4.dex */
public interface ConstructorDescriptor extends FunctionDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
    ConstructorDescriptor copy(DeclarationDescriptor declarationDescriptor, Modality modality, DescriptorVisibility descriptorVisibility, CallableMemberDescriptor.Kind kind, boolean z);

    ClassDescriptor getConstructedClass();

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    /* JADX INFO: renamed from: getContainingDeclaration */
    ClassifierDescriptorWithTypeParameters getContainingModule();

    @Override // org.jetbrains.kotlin.descriptors.Named
    /* JADX INFO: renamed from: getName */
    Name getShortName();

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.CallableMemberDescriptor, org.jetbrains.kotlin.descriptors.CallableDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    ConstructorDescriptor getOriginal();

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    KotlinType getReturnType();

    @Override // org.jetbrains.kotlin.descriptors.CallableDescriptor
    List<TypeParameterDescriptor> getTypeParameters();

    boolean isPrimary();

    @Override // org.jetbrains.kotlin.descriptors.FunctionDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    ConstructorDescriptor substitute(TypeSubstitutor typeSubstitutor);
}
