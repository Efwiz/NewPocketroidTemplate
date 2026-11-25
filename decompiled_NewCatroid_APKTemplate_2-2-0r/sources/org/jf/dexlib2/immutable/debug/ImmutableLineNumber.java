package org.jf.dexlib2.immutable.debug;

import org.jf.dexlib2.iface.debug.LineNumber;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableLineNumber extends ImmutableDebugItem implements LineNumber {
    protected final int lineNumber;

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    public int getDebugItemType() {
        return 10;
    }

    public ImmutableLineNumber(int i, int i2) {
        super(i);
        this.lineNumber = i2;
    }

    public static ImmutableLineNumber of(LineNumber lineNumber) {
        if (lineNumber instanceof ImmutableLineNumber) {
            return (ImmutableLineNumber) lineNumber;
        }
        return new ImmutableLineNumber(lineNumber.getCodeAddress(), lineNumber.getLineNumber());
    }

    @Override // org.jf.dexlib2.iface.debug.LineNumber
    public int getLineNumber() {
        return this.lineNumber;
    }
}
