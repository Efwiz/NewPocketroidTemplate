package org.jf.dexlib2.immutable.debug;

import org.jf.dexlib2.base.reference.BaseStringReference;
import org.jf.dexlib2.iface.debug.SetSourceFile;
import org.jf.dexlib2.iface.reference.StringReference;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableSetSourceFile extends ImmutableDebugItem implements SetSourceFile {
    protected final String sourceFile;

    @Override // org.jf.dexlib2.iface.debug.DebugItem
    public int getDebugItemType() {
        return 9;
    }

    public ImmutableSetSourceFile(int i, String str) {
        super(i);
        this.sourceFile = str;
    }

    public static ImmutableSetSourceFile of(SetSourceFile setSourceFile) {
        if (setSourceFile instanceof ImmutableSetSourceFile) {
            return (ImmutableSetSourceFile) setSourceFile;
        }
        return new ImmutableSetSourceFile(setSourceFile.getCodeAddress(), setSourceFile.getSourceFile());
    }

    @Override // org.jf.dexlib2.iface.debug.SetSourceFile
    public String getSourceFile() {
        return this.sourceFile;
    }

    @Override // org.jf.dexlib2.iface.debug.SetSourceFile
    public StringReference getSourceFileReference() {
        if (this.sourceFile == null) {
            return null;
        }
        return new BaseStringReference() { // from class: org.jf.dexlib2.immutable.debug.ImmutableSetSourceFile.1
            @Override // org.jf.dexlib2.iface.reference.StringReference
            public String getString() {
                return ImmutableSetSourceFile.this.sourceFile;
            }
        };
    }
}
