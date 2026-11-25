package org.jetbrains.kotlin.ir.interpreter.stack;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Variable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000b\u0010\b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0015\u0010\t\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/stack/Variable;", "", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "(Lorg/jetbrains/kotlin/ir/interpreter/state/State;)V", "getState", "()Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "setState", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Variable {
    private State state;

    public static /* synthetic */ Variable copy$default(Variable variable, State state, int i, Object obj) {
        if ((i & 1) != 0) {
            state = variable.state;
        }
        return variable.copy(state);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final State getState() {
        return this.state;
    }

    public final Variable copy(State state) {
        return new Variable(state);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof Variable) && Intrinsics.areEqual(this.state, ((Variable) other).state);
    }

    public int hashCode() {
        State state = this.state;
        if (state == null) {
            return 0;
        }
        return state.hashCode();
    }

    public String toString() {
        return "Variable(state=" + this.state + ')';
    }

    public Variable(State state) {
        this.state = state;
    }

    public final State getState() {
        return this.state;
    }

    public final void setState(State state) {
        this.state = state;
    }
}
