package org.jf.dexlib2.iface.instruction;

import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public interface SwitchPayload extends PayloadInstruction {
    List<? extends SwitchElement> getSwitchElements();
}
