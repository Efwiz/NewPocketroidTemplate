package org.jf.dexlib2.builder.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.builder.BuilderInstruction;
import org.jf.dexlib2.iface.instruction.formats.Instruction31i;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderInstruction31i extends BuilderInstruction implements Instruction31i {
    public static final Format FORMAT = Format.Format31i;
    protected final int literal;
    protected final int registerA;

    public BuilderInstruction31i(Opcode opcode, int i, int i2) {
        super(opcode);
        this.registerA = Preconditions.checkByteRegister(i);
        this.literal = i2;
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
    }

    @Override // org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
    public int getNarrowLiteral() {
        return this.literal;
    }

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    public long getWideLiteral() {
        return this.literal;
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
