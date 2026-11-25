package org.jf.dexlib2.builder;

import java.util.List;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.builder.instruction.BuilderSwitchElement;
import org.jf.dexlib2.iface.instruction.SwitchPayload;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BuilderSwitchPayload extends BuilderInstruction implements SwitchPayload {
    MethodLocation referrer;

    @Override // org.jf.dexlib2.iface.instruction.SwitchPayload
    public abstract List<? extends BuilderSwitchElement> getSwitchElements();

    protected BuilderSwitchPayload(Opcode opcode) {
        super(opcode);
    }

    public MethodLocation getReferrer() {
        MethodLocation methodLocation = this.referrer;
        if (methodLocation != null) {
            return methodLocation;
        }
        throw new IllegalStateException("The referrer has not been set yet");
    }
}
