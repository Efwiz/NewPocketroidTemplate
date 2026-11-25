package org.jf.dexlib2.dexbacked.instruction;

import java.util.List;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.util.FixedSizeList;
import org.jf.dexlib2.iface.instruction.SwitchElement;
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedSparseSwitchPayload extends DexBackedInstruction implements SparseSwitchPayload {
    private static final int ELEMENT_COUNT_OFFSET = 2;
    private static final int KEYS_OFFSET = 4;
    public final int elementCount;

    public DexBackedSparseSwitchPayload(DexBackedDexFile dexBackedDexFile, int i) {
        super(dexBackedDexFile, Opcode.SPARSE_SWITCH_PAYLOAD, i);
        this.elementCount = dexBackedDexFile.getDataBuffer().readUshort(i + 2);
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchPayload
    public List<? extends SwitchElement> getSwitchElements() {
        return new FixedSizeList<SwitchElement>() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedSparseSwitchPayload.1
            @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
            public SwitchElement readItem(final int i) {
                return new SwitchElement() { // from class: org.jf.dexlib2.dexbacked.instruction.DexBackedSparseSwitchPayload.1.1
                    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
                    public int getKey() {
                        return DexBackedSparseSwitchPayload.this.dexFile.getDataBuffer().readInt(DexBackedSparseSwitchPayload.this.instructionStart + 4 + (i * 4));
                    }

                    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
                    public int getOffset() {
                        return DexBackedSparseSwitchPayload.this.dexFile.getDataBuffer().readInt(DexBackedSparseSwitchPayload.this.instructionStart + 4 + (DexBackedSparseSwitchPayload.this.elementCount * 4) + (i * 4));
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return DexBackedSparseSwitchPayload.this.elementCount;
            }
        };
    }

    @Override // org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction, org.jf.dexlib2.iface.instruction.Instruction
    public int getCodeUnits() {
        return (this.elementCount * 4) + 2;
    }
}
