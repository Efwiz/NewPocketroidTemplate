package org.jf.dexlib2.dexbacked.value;

import java.util.Set;
import org.jf.dexlib2.base.value.BaseAnnotationEncodedValue;
import org.jf.dexlib2.dexbacked.DexBackedAnnotationElement;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexReader;
import org.jf.dexlib2.dexbacked.model.DexTypeString;
import org.jf.dexlib2.dexbacked.util.VariableSizeSet;
import org.jf.dexlib2.iface.value.AnnotationEncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedAnnotationEncodedValue extends BaseAnnotationEncodedValue implements AnnotationEncodedValue {
    public final DexBackedDexFile dexFile;
    private final DexTypeString dexTypeString;
    private final int elementCount;
    private final int elementsOffset;

    public DexBackedAnnotationEncodedValue(DexBackedDexFile dexBackedDexFile, DexReader<?> dexReader) {
        this.dexFile = dexBackedDexFile;
        this.dexTypeString = dexBackedDexFile.getTypeStringSection().get(dexReader.readSmallUleb128());
        int smallUleb128 = dexReader.readSmallUleb128();
        this.elementCount = smallUleb128;
        this.elementsOffset = dexReader.getOffset();
        skipElements(dexReader, smallUleb128);
    }

    public static void skipFrom(DexReader<?> dexReader) {
        dexReader.skipUleb128();
        skipElements(dexReader, dexReader.readSmallUleb128());
    }

    private static void skipElements(DexReader<?> dexReader, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            dexReader.skipUleb128();
            DexBackedEncodedValue.skipFrom(dexReader);
        }
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue, org.jf.dexlib2.iface.BasicAnnotation
    public String getType() {
        return getDexTypeString().getValue();
    }

    public DexTypeString getDexTypeString() {
        return this.dexTypeString;
    }

    @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue, org.jf.dexlib2.iface.BasicAnnotation
    public Set<? extends DexBackedAnnotationElement> getElements() {
        return new VariableSizeSet<DexBackedAnnotationElement>(this.dexFile.getDataBuffer(), this.elementsOffset, this.elementCount) { // from class: org.jf.dexlib2.dexbacked.value.DexBackedAnnotationEncodedValue.1
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeSet
            protected /* bridge */ /* synthetic */ DexBackedAnnotationElement readNextItem(DexReader dexReader, int i) {
                return readNextItem2((DexReader<?>) dexReader, i);
            }

            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeSet
            /* JADX INFO: renamed from: readNextItem, reason: avoid collision after fix types in other method */
            protected DexBackedAnnotationElement readNextItem2(DexReader<?> dexReader, int i) {
                return new DexBackedAnnotationElement(DexBackedAnnotationEncodedValue.this.dexFile, dexReader);
            }
        };
    }
}
