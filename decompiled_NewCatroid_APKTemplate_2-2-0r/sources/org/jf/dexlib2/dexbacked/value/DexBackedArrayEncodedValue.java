package org.jf.dexlib2.dexbacked.value;

import java.util.List;
import org.jf.dexlib2.base.value.BaseArrayEncodedValue;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexReader;
import org.jf.dexlib2.dexbacked.util.VariableSizeList;
import org.jf.dexlib2.iface.value.ArrayEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedArrayEncodedValue extends BaseArrayEncodedValue implements ArrayEncodedValue {
    public final DexBackedDexFile dexFile;
    private final int elementCount;
    private final int encodedArrayOffset;

    public DexBackedArrayEncodedValue(DexBackedDexFile dexBackedDexFile, DexReader dexReader) {
        this.dexFile = dexBackedDexFile;
        int smallUleb128 = dexReader.readSmallUleb128();
        this.elementCount = smallUleb128;
        this.encodedArrayOffset = dexReader.getOffset();
        skipElementsFrom(dexReader, smallUleb128);
    }

    public static void skipFrom(DexReader dexReader) {
        skipElementsFrom(dexReader, dexReader.readSmallUleb128());
    }

    private static void skipElementsFrom(DexReader dexReader, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            DexBackedEncodedValue.skipFrom(dexReader);
        }
    }

    @Override // org.jf.dexlib2.iface.value.ArrayEncodedValue
    public List<? extends EncodedValue> getValue() {
        return new VariableSizeList<EncodedValue>(this.dexFile.getDataBuffer(), this.encodedArrayOffset, this.elementCount) { // from class: org.jf.dexlib2.dexbacked.value.DexBackedArrayEncodedValue.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeList
            public EncodedValue readNextItem(DexReader dexReader, int i) {
                return DexBackedEncodedValue.readFrom(DexBackedArrayEncodedValue.this.dexFile, dexReader);
            }
        };
    }
}
