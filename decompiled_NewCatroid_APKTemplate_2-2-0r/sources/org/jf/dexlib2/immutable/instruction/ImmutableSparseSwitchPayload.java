package org.jf.dexlib2.immutable.instruction;

import java.util.List;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.SwitchElement;
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableSparseSwitchPayload extends ImmutableInstruction implements SparseSwitchPayload {
    public static final Opcode OPCODE = Opcode.SPARSE_SWITCH_PAYLOAD;
    protected final List<? extends ImmutableSwitchElement> switchElements;

    public ImmutableSparseSwitchPayload(List<? extends SwitchElement> list) {
        super(OPCODE);
        this.switchElements = ImmutableSwitchElement.immutableListOf(list);
    }

    public static ImmutableSparseSwitchPayload of(SparseSwitchPayload sparseSwitchPayload) {
        if (sparseSwitchPayload instanceof ImmutableSparseSwitchPayload) {
            return (ImmutableSparseSwitchPayload) sparseSwitchPayload;
        }
        return new ImmutableSparseSwitchPayload(sparseSwitchPayload.getSwitchElements());
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchPayload
    public List<? extends SwitchElement> getSwitchElements() {
        return this.switchElements;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction, org.jf.dexlib2.iface.instruction.Instruction
    public int getCodeUnits() {
        return (this.switchElements.size() * 4) + 2;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return OPCODE.format;
    }
}
