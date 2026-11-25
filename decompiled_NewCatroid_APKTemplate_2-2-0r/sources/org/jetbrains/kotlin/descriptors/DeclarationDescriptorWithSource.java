package org.jetbrains.kotlin.descriptors;

/* JADX INFO: loaded from: classes4.dex */
public interface DeclarationDescriptorWithSource extends DeclarationDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    DeclarationDescriptorWithSource getOriginal();

    SourceElement getSource();
}
