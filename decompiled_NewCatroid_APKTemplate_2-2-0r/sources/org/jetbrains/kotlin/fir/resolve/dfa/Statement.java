package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: statements.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/Statement;", "", "()V", "variable", "Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "getVariable", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/DataFlowVariable;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/OperationStatement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Statement {
    public /* synthetic */ Statement(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract DataFlowVariable getVariable();

    private Statement() {
    }
}
