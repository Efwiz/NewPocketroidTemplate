package com.reandroid.dex.debug;

import com.reandroid.dex.smali.model.SmaliDebugElement;
import com.reandroid.dex.smali.model.SmaliDebugPrologue;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class DebugPrologue extends DebugElement {
    public DebugPrologue() {
        super(DebugElementType.PROLOGUE);
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public void fromSmali(SmaliDebugElement smaliDebugElement) throws IOException {
        super.fromSmali(smaliDebugElement);
        if (!(smaliDebugElement instanceof SmaliDebugPrologue)) {
            throw new IOException("Mismatch class: " + smaliDebugElement.getClass() + ", expecting: " + SmaliDebugPrologue.class);
        }
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public DebugElementType<DebugPrologue> getElementType() {
        return DebugElementType.PROLOGUE;
    }
}
