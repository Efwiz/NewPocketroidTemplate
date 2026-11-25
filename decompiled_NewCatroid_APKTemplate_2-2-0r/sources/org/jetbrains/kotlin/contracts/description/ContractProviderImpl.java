package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LazyContractProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/ContractProviderImpl;", "Lorg/jetbrains/kotlin/contracts/description/AbstractContractProvider;", "contractDescription", "Lorg/jetbrains/kotlin/contracts/description/ContractDescription;", "(Lorg/jetbrains/kotlin/contracts/description/ContractDescription;)V", "getContractDescription", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ContractProviderImpl extends AbstractContractProvider {
    private final ContractDescription contractDescription;

    public ContractProviderImpl(ContractDescription contractDescription) {
        Intrinsics.checkNotNullParameter(contractDescription, "contractDescription");
        this.contractDescription = contractDescription;
    }

    @Override // org.jetbrains.kotlin.contracts.description.AbstractContractProvider
    public ContractDescription getContractDescription() {
        return this.contractDescription;
    }
}
