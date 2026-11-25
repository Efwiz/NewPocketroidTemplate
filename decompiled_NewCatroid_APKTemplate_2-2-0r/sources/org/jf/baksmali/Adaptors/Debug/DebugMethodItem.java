package org.jf.baksmali.Adaptors.Debug;

import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.Adaptors.MethodItem;
import org.jf.baksmali.Adaptors.RegisterFormatter;
import org.jf.dexlib2.iface.debug.DebugItem;
import org.jf.dexlib2.iface.debug.EndLocal;
import org.jf.dexlib2.iface.debug.LineNumber;
import org.jf.dexlib2.iface.debug.RestartLocal;
import org.jf.dexlib2.iface.debug.SetSourceFile;
import org.jf.dexlib2.iface.debug.StartLocal;
import org.jf.util.ExceptionWithContext;

/* JADX INFO: loaded from: classes6.dex */
public abstract class DebugMethodItem extends MethodItem {
    private final int sortOrder;

    protected DebugMethodItem(int i, int i2) {
        super(i);
        this.sortOrder = i2;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public double getSortOrder() {
        return this.sortOrder;
    }

    public static DebugMethodItem build(ClassDefinition classDefinition, RegisterFormatter registerFormatter, DebugItem debugItem) {
        int codeAddress = debugItem.getCodeAddress();
        switch (debugItem.getDebugItemType()) {
            case 3:
                return new StartLocalMethodItem(classDefinition, codeAddress, -1, registerFormatter, (StartLocal) debugItem);
            case 4:
            default:
                throw new ExceptionWithContext("Invalid debug item type: %d", Integer.valueOf(debugItem.getDebugItemType()));
            case 5:
                return new EndLocalMethodItem(codeAddress, -1, registerFormatter, (EndLocal) debugItem);
            case 6:
                return new RestartLocalMethodItem(classDefinition, codeAddress, -1, registerFormatter, (RestartLocal) debugItem);
            case 7:
                return new EndPrologueMethodItem(codeAddress, -4);
            case 8:
                return new BeginEpilogueMethodItem(codeAddress, -4);
            case 9:
                return new SetSourceFileMethodItem(codeAddress, -3, (SetSourceFile) debugItem);
            case 10:
                return new LineNumberMethodItem(codeAddress, -2, (LineNumber) debugItem);
        }
    }
}
