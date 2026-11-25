package org.jf.dexlib2.immutable.instruction;

import java.util.List;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.SwitchElement;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.writer.InstructionFactory;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstructionFactory implements InstructionFactory<Reference> {
    public static final ImmutableInstructionFactory INSTANCE = new ImmutableInstructionFactory();

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public /* bridge */ /* synthetic */ Instruction makeArrayPayload(int i, List list) {
        return makeArrayPayload(i, (List<Number>) list);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public /* bridge */ /* synthetic */ Instruction makePackedSwitchPayload(List list) {
        return makePackedSwitchPayload((List<? extends SwitchElement>) list);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public /* bridge */ /* synthetic */ Instruction makeSparseSwitchPayload(List list) {
        return makeSparseSwitchPayload((List<? extends SwitchElement>) list);
    }

    private ImmutableInstructionFactory() {
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction10t makeInstruction10t(Opcode opcode, int i) {
        return new ImmutableInstruction10t(opcode, i);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction10x makeInstruction10x(Opcode opcode) {
        return new ImmutableInstruction10x(opcode);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction11n makeInstruction11n(Opcode opcode, int i, int i2) {
        return new ImmutableInstruction11n(opcode, i, i2);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction11x makeInstruction11x(Opcode opcode, int i) {
        return new ImmutableInstruction11x(opcode, i);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction12x makeInstruction12x(Opcode opcode, int i, int i2) {
        return new ImmutableInstruction12x(opcode, i, i2);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction20bc makeInstruction20bc(Opcode opcode, int i, Reference reference) {
        return new ImmutableInstruction20bc(opcode, i, reference);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction20t makeInstruction20t(Opcode opcode, int i) {
        return new ImmutableInstruction20t(opcode, i);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction21c makeInstruction21c(Opcode opcode, int i, Reference reference) {
        return new ImmutableInstruction21c(opcode, i, reference);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction21ih makeInstruction21ih(Opcode opcode, int i, int i2) {
        return new ImmutableInstruction21ih(opcode, i, i2);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction21lh makeInstruction21lh(Opcode opcode, int i, long j) {
        return new ImmutableInstruction21lh(opcode, i, j);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction21s makeInstruction21s(Opcode opcode, int i, int i2) {
        return new ImmutableInstruction21s(opcode, i, i2);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction21t makeInstruction21t(Opcode opcode, int i, int i2) {
        return new ImmutableInstruction21t(opcode, i, i2);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction22b makeInstruction22b(Opcode opcode, int i, int i2, int i3) {
        return new ImmutableInstruction22b(opcode, i, i2, i3);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction22c makeInstruction22c(Opcode opcode, int i, int i2, Reference reference) {
        return new ImmutableInstruction22c(opcode, i, i2, reference);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction22s makeInstruction22s(Opcode opcode, int i, int i2, int i3) {
        return new ImmutableInstruction22s(opcode, i, i2, i3);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction22t makeInstruction22t(Opcode opcode, int i, int i2, int i3) {
        return new ImmutableInstruction22t(opcode, i, i2, i3);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction22x makeInstruction22x(Opcode opcode, int i, int i2) {
        return new ImmutableInstruction22x(opcode, i, i2);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction23x makeInstruction23x(Opcode opcode, int i, int i2, int i3) {
        return new ImmutableInstruction23x(opcode, i, i2, i3);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction30t makeInstruction30t(Opcode opcode, int i) {
        return new ImmutableInstruction30t(opcode, i);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction31c makeInstruction31c(Opcode opcode, int i, Reference reference) {
        return new ImmutableInstruction31c(opcode, i, reference);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction31i makeInstruction31i(Opcode opcode, int i, int i2) {
        return new ImmutableInstruction31i(opcode, i, i2);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction31t makeInstruction31t(Opcode opcode, int i, int i2) {
        return new ImmutableInstruction31t(opcode, i, i2);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction32x makeInstruction32x(Opcode opcode, int i, int i2) {
        return new ImmutableInstruction32x(opcode, i, i2);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction35c makeInstruction35c(Opcode opcode, int i, int i2, int i3, int i4, int i5, int i6, Reference reference) {
        return new ImmutableInstruction35c(opcode, i, i2, i3, i4, i5, i6, reference);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction3rc makeInstruction3rc(Opcode opcode, int i, int i2, Reference reference) {
        return new ImmutableInstruction3rc(opcode, i, i2, reference);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableInstruction51l makeInstruction51l(Opcode opcode, int i, long j) {
        return new ImmutableInstruction51l(opcode, i, j);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableSparseSwitchPayload makeSparseSwitchPayload(List<? extends SwitchElement> list) {
        return new ImmutableSparseSwitchPayload(list);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutablePackedSwitchPayload makePackedSwitchPayload(List<? extends SwitchElement> list) {
        return new ImmutablePackedSwitchPayload(list);
    }

    @Override // org.jf.dexlib2.writer.InstructionFactory
    public ImmutableArrayPayload makeArrayPayload(int i, List<Number> list) {
        return new ImmutableArrayPayload(i, list);
    }
}
