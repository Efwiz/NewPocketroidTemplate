package org.jetbrains.kotlin.psi2ir.generators;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: DescriptorStorageForContextReceivers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0000J\u000e\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0005J\u0016\u0010\f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u0006R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/DescriptorStorageForContextReceivers;", "", "()V", "syntheticFieldStorage", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "addAllFrom", "", "storage", "getSyntheticField", "receiverValue", "put", "irField", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorStorageForContextReceivers {
    private final Map<ReceiverValue, IrField> syntheticFieldStorage = new LinkedHashMap();

    public final IrField getSyntheticField(ReceiverValue receiverValue) {
        Intrinsics.checkNotNullParameter(receiverValue, "receiverValue");
        IrField irField = this.syntheticFieldStorage.get(receiverValue);
        if (irField != null) {
            return irField;
        }
        throw new IllegalStateException(("No synthetic field for receiver value " + receiverValue).toString());
    }

    public final void put(ReceiverValue receiverValue, IrField irField) {
        Intrinsics.checkNotNullParameter(receiverValue, "receiverValue");
        Intrinsics.checkNotNullParameter(irField, "irField");
        this.syntheticFieldStorage.put(receiverValue, irField);
    }

    public final void addAllFrom(DescriptorStorageForContextReceivers storage) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        this.syntheticFieldStorage.putAll(storage.syntheticFieldStorage);
    }
}
