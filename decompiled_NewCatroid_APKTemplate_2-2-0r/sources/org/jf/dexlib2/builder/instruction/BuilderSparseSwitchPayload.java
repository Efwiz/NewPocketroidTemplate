package org.jf.dexlib2.builder.instruction;

import java.util.List;
import java.util.function.Function;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.builder.BuilderSwitchPayload;
import org.jf.dexlib2.builder.SwitchLabelElement;
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
import org.jf.util.collection.EmptyList;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderSparseSwitchPayload extends BuilderSwitchPayload implements SparseSwitchPayload {
    public static final Opcode OPCODE = Opcode.SPARSE_SWITCH_PAYLOAD;
    protected final List<BuilderSwitchElement> switchElements;

    public BuilderSparseSwitchPayload(List<? extends SwitchLabelElement> list) {
        super(OPCODE);
        if (list == null) {
            this.switchElements = EmptyList.of();
        } else {
            this.switchElements = ListUtil.transform(list, new Function<SwitchLabelElement, BuilderSwitchElement>() { // from class: org.jf.dexlib2.builder.instruction.BuilderSparseSwitchPayload.1
                static final /* synthetic */ boolean $assertionsDisabled = false;

                @Override // java.util.function.Function
                public BuilderSwitchElement apply(SwitchLabelElement switchLabelElement) {
                    return new BuilderSwitchElement(BuilderSparseSwitchPayload.this, switchLabelElement.key, switchLabelElement.target);
                }
            });
        }
    }

    @Override // org.jf.dexlib2.builder.BuilderSwitchPayload, org.jf.dexlib2.iface.instruction.SwitchPayload
    public List<BuilderSwitchElement> getSwitchElements() {
        return this.switchElements;
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction, org.jf.dexlib2.iface.instruction.Instruction
    public int getCodeUnits() {
        return (this.switchElements.size() * 4) + 2;
    }

    @Override // org.jf.dexlib2.builder.BuilderInstruction
    public Format getFormat() {
        return OPCODE.format;
    }
}
