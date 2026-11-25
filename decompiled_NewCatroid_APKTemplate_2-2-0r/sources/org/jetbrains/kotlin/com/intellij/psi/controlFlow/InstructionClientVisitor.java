package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

/* JADX INFO: loaded from: classes3.dex */
abstract class InstructionClientVisitor<T> extends ControlFlowInstructionVisitor {
    boolean[] processedInstructions;

    public abstract T getResult();

    public void procedureEntered(int i, int i2) {
    }

    InstructionClientVisitor() {
    }

    protected final boolean isLeaf(int i) {
        return i == this.processedInstructions.length;
    }
}
