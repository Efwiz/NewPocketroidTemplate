package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction30t;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction30t extends ImmutableInstruction implements Instruction30t {
    public static final Format FORMAT = Format.Format30t;
    protected final int codeOffset;

    public ImmutableInstruction30t(Opcode opcode, int i) {
        super(opcode);
        this.codeOffset = i;
    }

    public static ImmutableInstruction30t of(Instruction30t instruction30t) {
        if (instruction30t instanceof ImmutableInstruction30t) {
            return (ImmutableInstruction30t) instruction30t;
        }
        return new ImmutableInstruction30t(instruction30t.getOpcode(), instruction30t.getCodeOffset());
    }

    @Override // org.jf.dexlib2.iface.instruction.OffsetInstruction
    public int getCodeOffset() {
        return this.codeOffset;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
