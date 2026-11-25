package org.jetbrains.kotlin.ir.interpreter.stack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArrayDeque;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.interpreter.Instruction;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Frame.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\fJ\b\u0010\u0013\u001a\u0004\u0018\u00010\tJ\u0018\u0010\u0014\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u00160\u0015J\u0006\u0010\u0017\u001a\u00020\u0011J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0012\u001a\u00020\fJ\b\u0010\u001a\u001a\u0004\u0018\u00010\u0019J\u0006\u0010\u001b\u001a\u00020\tJ\u0006\u0010\u001c\u001a\u00020\u0019J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\tJ\u000e\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0019J\u0016\u0010\"\u001a\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010#\u001a\u00020\u0019J\u0016\u0010$\u001a\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010%\u001a\u00020\rJ\u0018\u0010$\u001a\u00020\u001e2\u0006\u0010\u0012\u001a\u00020\f2\b\u0010!\u001a\u0004\u0018\u00010\u0019R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/stack/SubFrame;", "", "owner", "Lorg/jetbrains/kotlin/ir/IrElement;", "(Lorg/jetbrains/kotlin/ir/IrElement;)V", "dataStack", "Lorg/jetbrains/kotlin/ir/interpreter/stack/DataStack;", "instructions", "Lkotlin/collections/ArrayDeque;", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "memory", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Variable;", "getOwner", "()Lorg/jetbrains/kotlin/ir/IrElement;", "containsStateInMemory", "", NativeSymbol.TYPE_NAME, "dropInstructions", "getAll", "", "Lkotlin/Pair;", "isEmpty", "loadState", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "peekState", "popInstruction", "popState", "pushInstruction", "", "instruction", "pushState", "state", "rewriteState", "newState", "storeState", "variable", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SubFrame {
    private final DataStack dataStack;
    private final ArrayDeque<Instruction> instructions;
    private final Map<IrSymbol, Variable> memory;
    private final IrElement owner;

    public SubFrame(IrElement owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.owner = owner;
        this.instructions = new ArrayDeque<>();
        this.dataStack = new DataStack();
        this.memory = new LinkedHashMap();
    }

    public final IrElement getOwner() {
        return this.owner;
    }

    public final boolean isEmpty() {
        return this.instructions.isEmpty();
    }

    public final void pushInstruction(Instruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        this.instructions.addFirst(instruction);
    }

    public final Instruction popInstruction() {
        return this.instructions.removeFirst();
    }

    public final Instruction dropInstructions() {
        Instruction instructionLastOrNull = this.instructions.lastOrNull();
        if (instructionLastOrNull == null) {
            return null;
        }
        this.instructions.clear();
        return instructionLastOrNull;
    }

    public final void pushState(State state) {
        Intrinsics.checkNotNullParameter(state, "state");
        this.dataStack.push(state);
    }

    public final State popState() {
        return this.dataStack.pop();
    }

    public final State peekState() {
        return this.dataStack.peek();
    }

    public final void storeState(IrSymbol symbol, Variable variable) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(variable, "variable");
        this.memory.put(symbol, variable);
    }

    public final void storeState(IrSymbol symbol, State state) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.memory.put(symbol, new Variable(state));
    }

    public final boolean containsStateInMemory(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this.memory.get(symbol) != null;
    }

    public final State loadState(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Variable variable = this.memory.get(symbol);
        if (variable != null) {
            return variable.getState();
        }
        return null;
    }

    public final void rewriteState(IrSymbol symbol, State newState) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(newState, "newState");
        Variable variable = this.memory.get(symbol);
        if (variable == null) {
            return;
        }
        variable.setState(newState);
    }

    public final List<Pair<IrSymbol, Variable>> getAll() {
        return MapsKt.toList(this.memory);
    }
}
