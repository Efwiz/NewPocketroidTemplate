package org.jf.dexlib2.dexbacked.value;

import org.jf.dexlib2.base.value.BaseStringEncodedValue;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexReader;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedStringEncodedValue extends BaseStringEncodedValue {
    public final DexBackedDexFile dexFile;
    private final int stringIndex;

    public DexBackedStringEncodedValue(DexBackedDexFile dexBackedDexFile, DexReader dexReader, int i) {
        this.dexFile = dexBackedDexFile;
        this.stringIndex = dexReader.readSizedSmallUint(i + 1);
    }

    @Override // org.jf.dexlib2.iface.value.StringEncodedValue
    public String getValue() {
        return this.dexFile.getStringSection().get(this.stringIndex);
    }
}
