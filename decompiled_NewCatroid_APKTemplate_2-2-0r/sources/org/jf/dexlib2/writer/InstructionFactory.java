package org.jf.dexlib2.writer;

import java.util.List;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.SwitchElement;
import org.jf.dexlib2.iface.reference.Reference;

/* JADX INFO: loaded from: classes6.dex */
public interface InstructionFactory<Ref extends Reference> {
    Instruction makeArrayPayload(int i, List<Number> list);

    Instruction makeInstruction10t(Opcode opcode, int i);

    Instruction makeInstruction10x(Opcode opcode);

    Instruction makeInstruction11n(Opcode opcode, int i, int i2);

    Instruction makeInstruction11x(Opcode opcode, int i);

    Instruction makeInstruction12x(Opcode opcode, int i, int i2);

    Instruction makeInstruction20bc(Opcode opcode, int i, Ref ref);

    Instruction makeInstruction20t(Opcode opcode, int i);

    Instruction makeInstruction21c(Opcode opcode, int i, Ref ref);

    Instruction makeInstruction21ih(Opcode opcode, int i, int i2);

    Instruction makeInstruction21lh(Opcode opcode, int i, long j);

    Instruction makeInstruction21s(Opcode opcode, int i, int i2);

    Instruction makeInstruction21t(Opcode opcode, int i, int i2);

    Instruction makeInstruction22b(Opcode opcode, int i, int i2, int i3);

    Instruction makeInstruction22c(Opcode opcode, int i, int i2, Ref ref);

    Instruction makeInstruction22s(Opcode opcode, int i, int i2, int i3);

    Instruction makeInstruction22t(Opcode opcode, int i, int i2, int i3);

    Instruction makeInstruction22x(Opcode opcode, int i, int i2);

    Instruction makeInstruction23x(Opcode opcode, int i, int i2, int i3);

    Instruction makeInstruction30t(Opcode opcode, int i);

    Instruction makeInstruction31c(Opcode opcode, int i, Ref ref);

    Instruction makeInstruction31i(Opcode opcode, int i, int i2);

    Instruction makeInstruction31t(Opcode opcode, int i, int i2);

    Instruction makeInstruction32x(Opcode opcode, int i, int i2);

    Instruction makeInstruction35c(Opcode opcode, int i, int i2, int i3, int i4, int i5, int i6, Ref ref);

    Instruction makeInstruction3rc(Opcode opcode, int i, int i2, Ref ref);

    Instruction makeInstruction51l(Opcode opcode, int i, long j);

    Instruction makePackedSwitchPayload(List<? extends SwitchElement> list);

    Instruction makeSparseSwitchPayload(List<? extends SwitchElement> list);
}
