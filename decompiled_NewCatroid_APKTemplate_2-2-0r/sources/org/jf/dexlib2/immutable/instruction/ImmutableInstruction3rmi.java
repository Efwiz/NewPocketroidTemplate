package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction3rmi;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction3rmi extends ImmutableInstruction implements Instruction3rmi {
    public static final Format FORMAT = Format.Format3rmi;
    protected final int inlineIndex;
    protected final int registerCount;
    protected final int startRegister;

    public ImmutableInstruction3rmi(Opcode opcode, int i, int i2, int i3) {
        super(opcode);
        this.startRegister = Preconditions.checkShortRegister(i);
        this.registerCount = Preconditions.checkRegisterRangeCount(i2);
        this.inlineIndex = Preconditions.checkInlineIndex(i3);
    }

    public static ImmutableInstruction3rmi of(Instruction3rmi instruction3rmi) {
        if (instruction3rmi instanceof ImmutableInstruction3rmi) {
            return (ImmutableInstruction3rmi) instruction3rmi;
        }
        return new ImmutableInstruction3rmi(instruction3rmi.getOpcode(), instruction3rmi.getStartRegister(), instruction3rmi.getRegisterCount(), instruction3rmi.getInlineIndex());
    }

    @Override // org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
    public int getStartRegister() {
        return this.startRegister;
    }

    @Override // org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
    public int getRegisterCount() {
        return this.registerCount;
    }

    @Override // org.jf.dexlib2.iface.instruction.InlineIndexInstruction
    public int getInlineIndex() {
        return this.inlineIndex;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
