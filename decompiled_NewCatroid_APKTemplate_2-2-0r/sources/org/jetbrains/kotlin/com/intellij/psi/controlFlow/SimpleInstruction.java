package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: loaded from: classes3.dex */
public abstract class SimpleInstruction extends InstructionBase {
    private static final Logger LOG = Logger.getInstance((Class<?>) SimpleInstruction.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/SimpleInstruction", "accept"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public int nNext() {
        return 1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public int getNext(int i, int i2) {
        LOG.assertTrue(i2 == 0);
        return i + 1;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public void accept(ControlFlowInstructionVisitor controlFlowInstructionVisitor, int i, int i2) {
        if (controlFlowInstructionVisitor == null) {
            $$$reportNull$$$0(0);
        }
        controlFlowInstructionVisitor.visitSimpleInstruction(this, i, i2);
    }
}
