package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;

/* JADX INFO: compiled from: LazyContractProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/ContractProviderKey;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "Lorg/jetbrains/kotlin/contracts/description/AbstractContractProvider;", "()V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContractProviderKey implements CallableDescriptor.UserDataKey<AbstractContractProvider> {
    public static final ContractProviderKey INSTANCE = new ContractProviderKey();

    private ContractProviderKey() {
    }
}
