package org.jetbrains.kotlin.descriptors;

import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes4.dex */
public interface ValueDescriptor extends CallableDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    DeclarationDescriptor getContainingDeclaration();

    KotlinType getType();
}
