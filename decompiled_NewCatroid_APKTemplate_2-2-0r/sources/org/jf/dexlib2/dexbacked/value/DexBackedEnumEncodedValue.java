package org.jf.dexlib2.dexbacked.value;

import org.jf.dexlib2.base.value.BaseEnumEncodedValue;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexReader;
import org.jf.dexlib2.dexbacked.reference.DexBackedFieldReference;
import org.jf.dexlib2.iface.reference.FieldReference;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedEnumEncodedValue extends BaseEnumEncodedValue {
    public final DexBackedDexFile dexFile;
    private final int fieldIndex;

    public DexBackedEnumEncodedValue(DexBackedDexFile dexBackedDexFile, DexReader dexReader, int i) {
        this.dexFile = dexBackedDexFile;
        this.fieldIndex = dexReader.readSizedSmallUint(i + 1);
    }

    @Override // org.jf.dexlib2.iface.value.EnumEncodedValue
    public FieldReference getValue() {
        return new DexBackedFieldReference(this.dexFile, this.fieldIndex);
    }
}
