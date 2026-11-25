package org.jetbrains.kotlin.descriptors;

import org.jetbrains.kotlin.mpp.ClassifierSymbolMarker;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: loaded from: classes4.dex */
public interface ClassifierDescriptor extends DeclarationDescriptorNonRoot, ClassifierSymbolMarker {
    SimpleType getDefaultType();

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    ClassifierDescriptor getOriginal();

    TypeConstructor getTypeConstructor();
}
