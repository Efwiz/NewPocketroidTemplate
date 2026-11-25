package org.jf.dexlib2.builder.instruction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.builder.BuilderSwitchPayload;
import org.jf.dexlib2.builder.Label;
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
import org.jf.util.collection.EmptyList;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderPackedSwitchPayload extends BuilderSwitchPayload implements PackedSwitchPayload {
    public static final Opcode OPCODE = Opcode.PACKED_SWITCH_PAYLOAD;
    protected final List<BuilderSwitchElement> switchElements;

    public BuilderPackedSwitchPayload(int i, List<? extends Label> list) {
        super(OPCODE);
        if (list == null) {
            this.switchElements = EmptyList.of();
            return;
        }
        this.switchElements = new ArrayList(list.size());
        Iterator<? extends Label> it = list.iterator();
        while (it.hasNext()) {
            this.switchElements.add(new BuilderSwitchElement(this, i, it.next()));
            i++;
        }
    }

    @Override // org.jf.dexlib2.builder.BuilderSwitchPayload, org.jf.dexlib2.iface.instruction.SwitchPayload
    public List<BuilderSwitchElement> getSwitchElements() {
        return this.switchElements;
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction, org.jf.dexlib2.iface.instruction.Instruction
    public int getCodeUnits() {
        return (this.switchElements.size() * 2) + 4;
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    public Format getFormat() {
        return OPCODE.format;
    }
}
