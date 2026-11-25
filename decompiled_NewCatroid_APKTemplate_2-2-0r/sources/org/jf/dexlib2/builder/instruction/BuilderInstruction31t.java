package org.jf.dexlib2.builder.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.builder.BuilderOffsetInstruction;
import org.jf.dexlib2.builder.Label;
import org.jf.dexlib2.iface.instruction.formats.Instruction31t;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderInstruction31t extends BuilderOffsetInstruction implements Instruction31t {
    public static final Format FORMAT = Format.Format31t;
    protected final int registerA;

    public BuilderInstruction31t(Opcode opcode, int i, Label label) {
        super(opcode, label);
        this.registerA = Preconditions.checkByteRegister(i);
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
