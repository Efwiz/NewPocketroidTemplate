package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.UnknownInstruction;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableUnknownInstruction extends ImmutableInstruction implements UnknownInstruction {
    public static final Format FORMAT = Format.Format10x;
    protected final int originalOpcode;

    public ImmutableUnknownInstruction(int i) {
        super(Opcode.NOP);
        this.originalOpcode = i;
    }

    public static ImmutableUnknownInstruction of(UnknownInstruction unknownInstruction) {
        if (unknownInstruction instanceof ImmutableUnknownInstruction) {
            return (ImmutableUnknownInstruction) unknownInstruction;
        }
        return new ImmutableUnknownInstruction(unknownInstruction.getOriginalOpcode());
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return FORMAT;
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.UnknownInstruction
    public int getOriginalOpcode() {
        return this.originalOpcode;
    }
}
