package org.jetbrains.kotlin.descriptors;

import java.util.List;
import org.jetbrains.kotlin.mpp.TypeParameterSymbolMarker;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;

/* JADX INFO: loaded from: classes4.dex */
public interface TypeParameterDescriptor extends ClassifierDescriptor, TypeParameterSymbolMarker, TypeParameterMarker {
    int getIndex();

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    TypeParameterDescriptor getOriginal();

    StorageManager getStorageManager();

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor
    TypeConstructor getTypeConstructor();

    List<KotlinType> getUpperBounds();

    Variance getVariance();

    boolean isCapturedFromOuterDeclaration();

    boolean isReified();
}
