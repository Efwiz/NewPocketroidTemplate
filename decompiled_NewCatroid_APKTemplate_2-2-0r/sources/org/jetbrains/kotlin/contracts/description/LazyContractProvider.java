package org.jetbrains.kotlin.contracts.description;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: LazyContractProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¢\u0006\u0002\u0010\u0007J\n\u0010\f\u001a\u0004\u0018\u00010\tH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\b\u0010\b\u001a\u0004\u0018\u00010\tR\u0016\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/contracts/description/LazyContractProvider;", "Lorg/jetbrains/kotlin/contracts/description/AbstractContractProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "computation", "Lkotlin/Function0;", "", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lkotlin/jvm/functions/Function0;)V", "contractDescription", "Lorg/jetbrains/kotlin/contracts/description/ContractDescription;", "isComputed", "", "getContractDescription", "setContractDescription", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyContractProvider extends AbstractContractProvider {
    private final Function0<Object> computation;
    private ContractDescription contractDescription;
    private volatile boolean isComputed;
    private final StorageManager storageManager;

    public LazyContractProvider(StorageManager storageManager, Function0<? extends Object> computation) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(computation, "computation");
        this.storageManager = storageManager;
        this.computation = computation;
    }

    @Override // org.jetbrains.kotlin.contracts.description.AbstractContractProvider
    public ContractDescription getContractDescription() {
        if (!this.isComputed) {
            this.storageManager.compute(this.computation);
        }
        return this.contractDescription;
    }

    public final void setContractDescription(ContractDescription contractDescription) {
        this.contractDescription = contractDescription;
        this.isComputed = true;
    }
}
