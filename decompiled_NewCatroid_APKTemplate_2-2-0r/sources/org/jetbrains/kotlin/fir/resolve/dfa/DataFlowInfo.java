package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FirControlFlowGraphReferenceImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowInfo;", "", "variableStorage", "Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorage;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorage;)V", "getVariableStorage", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/VariableStorage;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataFlowInfo {
    private final VariableStorage variableStorage;

    public DataFlowInfo(VariableStorage variableStorage) {
        Intrinsics.checkNotNullParameter(variableStorage, "variableStorage");
        this.variableStorage = variableStorage;
    }

    public final VariableStorage getVariableStorage() {
        return this.variableStorage;
    }
}
