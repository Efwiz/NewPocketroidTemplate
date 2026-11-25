package org.jf.dexlib2.builder.debug;

import org.jf.dexlib2.builder.BuilderDebugItem;
import org.jf.dexlib2.iface.debug.LineNumber;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderLineNumber extends BuilderDebugItem implements LineNumber {
    private final int lineNumber;

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    public int getDebugItemType() {
        return 10;
    }

    public BuilderLineNumber(int i) {
        this.lineNumber = i;
    }

    @Override // org.jf.dexlib2.iface.debug.LineNumber
    public int getLineNumber() {
        return this.lineNumber;
    }
}
