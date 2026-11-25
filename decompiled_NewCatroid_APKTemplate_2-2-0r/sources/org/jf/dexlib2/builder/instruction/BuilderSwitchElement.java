package org.jf.dexlib2.builder.instruction;

import org.jf.dexlib2.builder.BuilderSwitchPayload;
import org.jf.dexlib2.builder.Label;
import org.jf.dexlib2.iface.instruction.SwitchElement;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderSwitchElement implements SwitchElement {
    private final int key;
    BuilderSwitchPayload parent;
    private final Label target;

    public BuilderSwitchElement(BuilderSwitchPayload builderSwitchPayload, int i, Label label) {
        this.parent = builderSwitchPayload;
        this.key = i;
        this.target = label;
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
    public int getKey() {
        return this.key;
    }

    @Override // org.jf.dexlib2.iface.instruction.SwitchElement
    public int getOffset() {
        return this.target.getCodeAddress() - this.parent.getReferrer().getCodeAddress();
    }

    public Label getTarget() {
        return this.target;
    }
}
