package org.jf.dexlib2.immutable.debug;

import org.jf.dexlib2.iface.debug.EpilogueBegin;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableEpilogueBegin extends ImmutableDebugItem implements EpilogueBegin {
    @Override // org.jf.dexlib2.iface.debug.DebugItem
    public int getDebugItemType() {
        return 8;
    }

    public ImmutableEpilogueBegin(int i) {
        super(i);
    }

    public static ImmutableEpilogueBegin of(EpilogueBegin epilogueBegin) {
        if (epilogueBegin instanceof ImmutableEpilogueBegin) {
            return (ImmutableEpilogueBegin) epilogueBegin;
        }
        return new ImmutableEpilogueBegin(epilogueBegin.getCodeAddress());
    }
}
