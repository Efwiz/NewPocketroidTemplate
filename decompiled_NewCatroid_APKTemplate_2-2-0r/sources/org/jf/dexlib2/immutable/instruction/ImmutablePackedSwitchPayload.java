package org.jf.dexlib2.immutable.instruction;

import java.util.List;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.SwitchElement;
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutablePackedSwitchPayload extends ImmutableInstruction implements PackedSwitchPayload {
    public static final Opcode OPCODE = Opcode.PACKED_SWITCH_PAYLOAD;
    protected final List<? extends ImmutableSwitchElement> switchElements;

    public ImmutablePackedSwitchPayload(List<? extends SwitchElement> list) {
        super(OPCODE);
        this.switchElements = (List) Preconditions.checkSequentialOrderedKeys(ImmutableSwitchElement.immutableListOf(list));
    }

    public static ImmutablePackedSwitchPayload of(PackedSwitchPayload packedSwitchPayload) {
        if (packedSwitchPayload instanceof ImmutablePackedSwitchPayload) {
            return (ImmutablePackedSwitchPayload) packedSwitchPayload;
        }
        return new ImmutablePackedSwitchPayload(packedSwitchPayload.getSwitchElements());
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload, org.jf.dexlib2.iface.instruction.SwitchPayload
    public List<? extends SwitchElement> getSwitchElements() {
        return this.switchElements;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction, org.jf.dexlib2.iface.instruction.Instruction
    public int getCodeUnits() {
        return (this.switchElements.size() * 2) + 4;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return OPCODE.format;
    }
}
