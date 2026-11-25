package org.jf.dexlib2.builder;

import org.jf.dexlib2.iface.debug.DebugItem;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BuilderDebugItem extends ItemWithLocation implements DebugItem {
    @Override // org.jf.dexlib2.iface.debug.DebugItem
    public int getCodeAddress() {
        if (this.location == null) {
            throw new IllegalStateException("Cannot get the address of a BuilderDebugItem that isn't associated with a method.");
        }
        return this.location.getCodeAddress();
    }
}
