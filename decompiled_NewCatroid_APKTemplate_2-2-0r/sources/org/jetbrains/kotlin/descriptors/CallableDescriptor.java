package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.mpp.CallableSymbolMarker;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes4.dex */
public interface CallableDescriptor extends DeclarationDescriptorNonRoot, DeclarationDescriptorWithVisibility, Substitutable<CallableDescriptor>, CallableSymbolMarker {

    public interface UserDataKey<V> {
    }

    List<ReceiverParameterDescriptor> getContextReceiverParameters();

    /* JADX INFO: renamed from: getDispatchReceiverParameter */
    ReceiverParameterDescriptor mo6753getDispatchReceiverParameter();

    ReceiverParameterDescriptor getExtensionReceiverParameter();

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    CallableDescriptor getOriginal();

    Collection<? extends CallableDescriptor> getOverriddenDescriptors();

    KotlinType getReturnType();

    List<TypeParameterDescriptor> getTypeParameters();

    <V> V getUserData(UserDataKey<V> userDataKey);

    List<ValueParameterDescriptor> getValueParameters();

    boolean hasStableParameterNames();

    boolean hasSynthesizedParameterNames();
}
