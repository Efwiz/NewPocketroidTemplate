package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

/* JADX INFO: loaded from: classes3.dex */
public abstract class InstructionBase implements Instruction, Cloneable {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/InstructionBase", "clone"));
    }

    public abstract String toString();

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.Instruction
    public Instruction clone() {
        try {
            Instruction instruction = (Instruction) super.clone();
            if (instruction == null) {
                $$$reportNull$$$0(0);
            }
            return instruction;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
