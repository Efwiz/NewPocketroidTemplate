package org.jetbrains.kotlin.descriptors;

import org.jetbrains.kotlin.descriptors.annotations.Annotated;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;

/* JADX INFO: loaded from: classes4.dex */
public interface DeclarationDescriptor extends Named, ValidateableDescriptor, Annotated, DeclarationSymbolMarker {
    <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d);

    /* JADX INFO: renamed from: acceptVoid */
    void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> declarationDescriptorVisitor);

    DeclarationDescriptor getContainingDeclaration();

    DeclarationDescriptor getOriginal();
}
