package org.jf.dexlib2.dexbacked;

import org.jf.dexlib2.base.BaseAnnotationElement;
import org.jf.dexlib2.dexbacked.model.DexString;
import org.jf.dexlib2.dexbacked.value.DexBackedEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedAnnotationElement extends BaseAnnotationElement {
    private final DexBackedDexFile dexFile;
    public final int nameIndex;
    public final EncodedValue value;

    public DexBackedAnnotationElement(DexBackedDexFile dexBackedDexFile, DexReader<?> dexReader) {
        this.dexFile = dexBackedDexFile;
        this.nameIndex = dexReader.readSmallUleb128();
        this.value = DexBackedEncodedValue.readFrom(dexBackedDexFile, dexReader);
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    public String getName() {
        return getNameDexString().getValue();
    }

    public DexString getNameDexString() {
        return this.dexFile.getDexStringSection().get(this.nameIndex);
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    public EncodedValue getValue() {
        return this.value;
    }
}
