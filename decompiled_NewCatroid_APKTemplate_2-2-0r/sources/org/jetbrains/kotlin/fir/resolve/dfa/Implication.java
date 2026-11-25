package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: statements.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/Implication;", "", "condition", "Lorg/jetbrains/kotlin/fir/resolve/dfa/OperationStatement;", "effect", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Statement;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/OperationStatement;Lorg/jetbrains/kotlin/fir/resolve/dfa/Statement;)V", "getCondition", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/OperationStatement;", "getEffect", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/Statement;", "toString", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Implication {
    private final OperationStatement condition;
    private final Statement effect;

    public Implication(OperationStatement condition, Statement effect) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(effect, "effect");
        this.condition = condition;
        this.effect = effect;
    }

    public final OperationStatement getCondition() {
        return this.condition;
    }

    public final Statement getEffect() {
        return this.effect;
    }

    public String toString() {
        return this.condition + " -> " + this.effect;
    }
}
