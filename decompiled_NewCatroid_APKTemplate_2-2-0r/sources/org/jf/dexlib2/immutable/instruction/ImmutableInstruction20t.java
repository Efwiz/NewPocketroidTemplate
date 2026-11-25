package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction20t;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction20t extends ImmutableInstruction implements Instruction20t {
    public static final Format FORMAT = Format.Format20t;
    protected final int codeOffset;

    public ImmutableInstruction20t(Opcode opcode, int i) {
        super(opcode);
        this.codeOffset = Preconditions.checkShortCodeOffset(i);
    }

    public static ImmutableInstruction20t of(Instruction20t instruction20t) {
        if (instruction20t instanceof ImmutableInstruction20t) {
            return (ImmutableInstruction20t) instruction20t;
        }
        return new ImmutableInstruction20t(instruction20t.getOpcode(), instruction20t.getCodeOffset());
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
