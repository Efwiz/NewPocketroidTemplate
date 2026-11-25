package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

/* JADX INFO: loaded from: classes3.dex */
public interface Instruction extends Cloneable {
    void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2);

    /* JADX INFO: renamed from: clone */
    Instruction mo6541clone();

    int getNext(int i, int i2);

    int nNext();
}
