package org.jf.dexlib2.dexbacked;

import java.util.Set;
import org.jf.dexlib2.base.BaseAnnotation;
import org.jf.dexlib2.dexbacked.util.VariableSizeSet;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedAnnotation extends BaseAnnotation {
    public final DexBackedDexFile dexFile;
    private final int elementsOffset;
    public final int typeIndex;
    public final int visibility;

    public DexBackedAnnotation(DexBackedDexFile dexBackedDexFile, int i) {
        this.dexFile = dexBackedDexFile;
        DexReader<? extends DexBuffer> dexReader = dexBackedDexFile.getDataBuffer().readerAt(i);
        this.visibility = dexReader.readUbyte();
        this.typeIndex = dexReader.readSmallUleb128();
        this.elementsOffset = dexReader.getOffset();
    }

    @Override // org.jf.dexlib2.iface.Annotation
    public int getVisibility() {
        return this.visibility;
    }

    @Override // org.jf.dexlib2.iface.Annotation, org.jf.dexlib2.iface.BasicAnnotation
    public String getType() {
        return this.dexFile.getTypeSection().get(this.typeIndex);
    }

    @Override // org.jf.dexlib2.iface.Annotation, org.jf.dexlib2.iface.BasicAnnotation
    public Set<? extends DexBackedAnnotationElement> getElements() {
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getDataBuffer().readerAt(this.elementsOffset);
        return new VariableSizeSet<DexBackedAnnotationElement>(this.dexFile.getDataBuffer(), dexReader.getOffset(), dexReader.readSmallUleb128()) { // from class: org.jf.dexlib2.dexbacked.DexBackedAnnotation.1
            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeSet
            protected /* bridge */ /* synthetic */ DexBackedAnnotationElement readNextItem(DexReader dexReader2, int i) {
                return readNextItem2((DexReader<?>) dexReader2, i);
            }

            @Override // org.jf.dexlib2.dexbacked.util.VariableSizeSet
            /* JADX INFO: renamed from: readNextItem, reason: avoid collision after fix types in other method */
            protected DexBackedAnnotationElement readNextItem2(DexReader<?> dexReader2, int i) {
                return new DexBackedAnnotationElement(DexBackedAnnotation.this.dexFile, dexReader2);
            }
        };
    }
}
