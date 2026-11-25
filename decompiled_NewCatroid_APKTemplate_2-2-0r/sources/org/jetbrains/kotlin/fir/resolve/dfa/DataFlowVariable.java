package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: DfaVariables.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0001\u0002\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "", "variableIndexForDebug", "", "(I)V", "toString", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/SyntheticVariable;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DataFlowVariable {
    private final int variableIndexForDebug;

    public /* synthetic */ DataFlowVariable(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    private DataFlowVariable(int i) {
        this.variableIndexForDebug = i;
    }

    public final String toString() {
        return "d" + this.variableIndexForDebug;
    }
}
