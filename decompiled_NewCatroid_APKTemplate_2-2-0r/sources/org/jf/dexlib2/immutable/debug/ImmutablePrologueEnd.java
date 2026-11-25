package org.jf.dexlib2.immutable.debug;

import org.jf.dexlib2.iface.debug.PrologueEnd;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutablePrologueEnd extends ImmutableDebugItem implements PrologueEnd {
    @Override // org.jf.dexlib2.iface.debug.DebugItem
    public int getDebugItemType() {
        return 7;
    }

    public ImmutablePrologueEnd(int i) {
        super(i);
    }

    public static ImmutablePrologueEnd of(PrologueEnd prologueEnd) {
        if (prologueEnd instanceof ImmutablePrologueEnd) {
            return (ImmutablePrologueEnd) prologueEnd;
        }
        return new ImmutablePrologueEnd(prologueEnd.getCodeAddress());
    }
}
