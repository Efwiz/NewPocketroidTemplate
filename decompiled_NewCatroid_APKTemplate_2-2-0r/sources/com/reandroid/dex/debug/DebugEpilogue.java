package com.reandroid.dex.debug;

import com.reandroid.dex.smali.model.SmaliDebugElement;
import com.reandroid.dex.smali.model.SmaliDebugEpilogue;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class DebugEpilogue extends DebugElement {
    public DebugEpilogue() {
        super(DebugElementType.EPILOGUE);
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public void fromSmali(SmaliDebugElement smaliDebugElement) throws IOException {
        super.fromSmali(smaliDebugElement);
        if (!(smaliDebugElement instanceof SmaliDebugEpilogue)) {
            throw new IOException("Mismatch class: " + smaliDebugElement.getClass() + ", expecting: " + SmaliDebugEpilogue.class);
        }
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public DebugElementType<DebugEpilogue> getElementType() {
        return DebugElementType.EPILOGUE;
    }
}
