package org.jetbrains.kotlin.contracts;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.contracts.model.structure.ESVariable;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;

/* JADX INFO: compiled from: ESDataFlowValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/contracts/ESVariableWithDataFlowValue;", "Lorg/jetbrains/kotlin/contracts/model/structure/ESVariable;", "Lorg/jetbrains/kotlin/contracts/ESDataFlowValue;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;", "dataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "(Lorg/jetbrains/kotlin/descriptors/ValueDescriptor;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;)V", "getDataFlowValue", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ESVariableWithDataFlowValue extends ESVariable implements ESDataFlowValue {
    private final DataFlowValue dataFlowValue;

    @Override // org.jetbrains.kotlin.contracts.ESDataFlowValue
    public DataFlowValue getDataFlowValue() {
        return this.dataFlowValue;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ESVariableWithDataFlowValue(ValueDescriptor descriptor, DataFlowValue dataFlowValue) {
        super(descriptor);
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(dataFlowValue, "dataFlowValue");
        this.dataFlowValue = dataFlowValue;
    }

    @Override // org.jetbrains.kotlin.contracts.model.structure.ESVariable
    public boolean equals(Object other) {
        return dataFlowEquals(other);
    }

    @Override // org.jetbrains.kotlin.contracts.model.structure.ESVariable
    public int hashCode() {
        return getDataFlowValue().hashCode();
    }
}
