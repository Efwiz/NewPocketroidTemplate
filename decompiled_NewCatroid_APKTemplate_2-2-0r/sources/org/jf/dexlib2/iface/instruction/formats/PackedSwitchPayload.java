package org.jf.dexlib2.iface.instruction.formats;

import java.util.List;
import org.jf.dexlib2.iface.instruction.SwitchElement;
import org.jf.dexlib2.iface.instruction.SwitchPayload;

/* JADX INFO: loaded from: classes6.dex */
public interface PackedSwitchPayload extends SwitchPayload {
    @Override // org.jf.dexlib2.iface.instruction.SwitchPayload
    List<? extends SwitchElement> getSwitchElements();
}
