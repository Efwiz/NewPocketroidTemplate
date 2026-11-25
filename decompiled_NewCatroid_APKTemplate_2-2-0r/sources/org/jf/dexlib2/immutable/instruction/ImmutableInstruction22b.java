package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction22b;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction22b extends ImmutableInstruction implements Instruction22b {
    public static final Format FORMAT = Format.Format22b;
    protected final int literal;
    protected final int registerA;
    protected final int registerB;

    public ImmutableInstruction22b(Opcode opcode, int i, int i2, int i3) {
        super(opcode);
        this.registerA = Preconditions.checkByteRegister(i);
        this.registerB = Preconditions.checkByteRegister(i2);
        this.literal = Preconditions.checkByteLiteral(i3);
    }

    public static ImmutableInstruction22b of(Instruction22b instruction22b) {
        if (instruction22b instanceof ImmutableInstruction22b) {
            return (ImmutableInstruction22b) instruction22b;
        }
        return new ImmutableInstruction22b(instruction22b.getOpcode(), instruction22b.getRegisterA(), instruction22b.getRegisterB(), instruction22b.getNarrowLiteral());
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    public int getRegisterB() {
        return this.registerB;
    }

    @Override // org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
    public int getNarrowLiteral() {
        return this.literal;
    }

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    public long getWideLiteral() {
        return this.literal;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
