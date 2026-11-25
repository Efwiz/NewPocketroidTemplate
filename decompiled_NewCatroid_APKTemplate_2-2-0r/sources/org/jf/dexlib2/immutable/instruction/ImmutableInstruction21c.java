package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction21c;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.immutable.reference.ImmutableReference;
import org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction21c extends ImmutableInstruction implements Instruction21c {
    public static final Format FORMAT = Format.Format21c;
    protected final ImmutableReference reference;
    protected final int registerA;

    public ImmutableInstruction21c(Opcode opcode, int i, Reference reference) {
        super(opcode);
        this.registerA = Preconditions.checkByteRegister(i);
        this.reference = ImmutableReferenceFactory.of(opcode.referenceType, reference);
    }

    public static ImmutableInstruction21c of(Instruction21c instruction21c) {
        if (instruction21c instanceof ImmutableInstruction21c) {
            return (ImmutableInstruction21c) instruction21c;
        }
        return new ImmutableInstruction21c(instruction21c.getOpcode(), instruction21c.getRegisterA(), instruction21c.getReference());
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    public ImmutableReference getReference() {
        return this.reference;
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    public int getReferenceType() {
        return this.opcode.referenceType;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
