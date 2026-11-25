package org.jetbrains.kotlin.ir.interpreter.stack;

import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.interpreter.state.State;

/* JADX INFO: compiled from: Frame.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005J\u0006\u0010\u0007\u001a\u00020\u0005J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/stack/DataStack;", "", "()V", "stack", "Lkotlin/collections/ArrayDeque;", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "peek", "pop", "push", "", "state", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DataStack {
    private final ArrayDeque<State> stack = new ArrayDeque<>();

    public final void push(State state) {
        Intrinsics.checkNotNullParameter(state, "state");
        this.stack.add(state);
    }

    public final State pop() {
        return this.stack.removeLast();
    }

    public final State peek() {
        return this.stack.lastOrNull();
    }
}
