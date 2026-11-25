package org.jf.dexlib2.dexbacked.instruction;

import java.util.List;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.util.FixedSizeList;
import org.jf.dexlib2.iface.instruction.SwitchElement;
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedPackedSwitchPayload extends DexBackedInstruction implements PackedSwitchPayload {
    private static final int ELEMENT_COUNT_OFFSET = 2;
    private static final int FIRST_KEY_OFFSET = 4;
    private static final int TARGETS_OFFSET = 8;
    public final int elementCount;

    public DexBackedPackedSwitchPayload(DexBackedDexFile dexBackedDexFile, int i) {
        super(dexBackedDexFile, Opcode.PACKED_SWITCH_PAYLOAD, i);
        this.elementCount = dexBackedDexFile.getDataBuffer().readUshort(i + 2);
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload, org.jf.dexlib2.iface.instruction.SwitchPayload
    public List<? extends SwitchElement> getSwitchElements() {
        final int i = this.dexFile.getDataBuffer().readInt(this.instructionStart + 4);
        return new FixedSizeList<SwitchElement>() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedPackedSwitchPayload.1
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            public SwitchElement readItem(final int i2) {
                return new SwitchElement() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedPackedSwitchPayload.1.1
                    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
                    public int getKey() {
                        return i + i2;
                    }

                    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
                    public int getOffset() {
                        return DexBackedPackedSwitchPayload.this.dexFile.getDataBuffer().readInt(DexBackedPackedSwitchPayload.this.instructionStart + 8 + (i2 * 4));
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedPackedSwitchPayload.this.elementCount;
            }
        };
    }

    @Override // org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction, org.jf.dexlib2.iface.instruction.Instruction
    public int getCodeUnits() {
        return (this.elementCount * 2) + 4;
    }
}
