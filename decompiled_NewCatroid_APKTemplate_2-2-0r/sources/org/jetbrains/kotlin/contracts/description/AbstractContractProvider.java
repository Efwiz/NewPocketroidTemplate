package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ContractProvider;

/* JADX INFO: compiled from: LazyContractProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u0004\u0018\u00010\u0004H&¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/AbstractContractProvider;", "Lorg/jetbrains/kotlin/descriptors/ContractProvider;", "()V", "getContractDescription", "Lorg/jetbrains/kotlin/contracts/description/ContractDescription;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractContractProvider implements ContractProvider {
    public abstract ContractDescription getContractDescription();
}
