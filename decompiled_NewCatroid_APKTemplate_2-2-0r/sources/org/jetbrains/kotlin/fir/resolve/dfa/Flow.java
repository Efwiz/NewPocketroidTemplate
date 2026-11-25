package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.Set;
import kotlin.Metadata;

/* JADX INFO: compiled from: Flow.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u0005H&J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005H&R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/Flow;", "", "()V", "knownVariables", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "getKnownVariables", "()Ljava/util/Set;", "getTypeStatement", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "variable", "unwrapVariable", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Flow {
    public abstract Set<RealVariable> getKnownVariables();

    public abstract TypeStatement getTypeStatement(RealVariable variable);

    public abstract RealVariable unwrapVariable(RealVariable variable);
}
