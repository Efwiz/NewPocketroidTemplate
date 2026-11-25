package org.jetbrains.kotlin.contracts;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;

/* JADX INFO: compiled from: ESDataFlowValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0001H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/contracts/ESDataFlowValue;", "", "dataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "getDataFlowValue", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "dataFlowEquals", "", "other", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ESDataFlowValue {
    DataFlowValue getDataFlowValue();

    default boolean dataFlowEquals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof ESDataFlowValue) {
            return Intrinsics.areEqual(getDataFlowValue(), ((ESDataFlowValue) other).getDataFlowValue());
        }
        return false;
    }
}
