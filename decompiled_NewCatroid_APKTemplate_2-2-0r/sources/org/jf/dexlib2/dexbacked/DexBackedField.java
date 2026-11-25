package org.jf.dexlib2.dexbacked;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import org.jf.dexlib2.HiddenApiRestriction;
import org.jf.dexlib2.base.reference.BaseFieldReference;
import org.jf.dexlib2.dexbacked.reference.DexBackedFieldReference;
import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory;
import org.jf.dexlib2.dexbacked.util.EncodedArrayItemIterator;
import org.jf.dexlib2.dexbacked.value.DexBackedEncodedValue;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.util.collection.EmptySet;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedField extends BaseFieldReference implements Field {
    public final int accessFlags;
    public final int annotationSetOffset;
    public final ClassDef classDef;
    public final DexBackedDexFile dexFile;
    private int fieldIdItemOffset;
    public final int fieldIndex;
    private final int hiddenApiRestrictions;
    public final EncodedValue initialValue;
    private final int initialValueOffset;
    private final int startOffset;

    public DexBackedField(DexBackedDexFile dexBackedDexFile, DexReader<?> dexReader, DexBackedClassDef dexBackedClassDef, int i, EncodedArrayItemIterator encodedArrayItemIterator, AnnotationsDirectory.AnnotationIterator annotationIterator, int i2) {
        this.dexFile = dexBackedDexFile;
        this.classDef = dexBackedClassDef;
        this.startOffset = dexReader.getOffset();
        int largeUleb128 = dexReader.readLargeUleb128() + i;
        this.fieldIndex = largeUleb128;
        this.accessFlags = dexReader.readSmallUleb128();
        this.annotationSetOffset = annotationIterator.seekTo(largeUleb128);
        this.initialValueOffset = encodedArrayItemIterator.getReaderOffset();
        this.initialValue = encodedArrayItemIterator.getNextOrNull();
        this.hiddenApiRestrictions = i2;
    }

    public DexBackedField(DexBackedDexFile dexBackedDexFile, DexReader<?> dexReader, DexBackedClassDef dexBackedClassDef, int i, AnnotationsDirectory.AnnotationIterator annotationIterator, int i2) {
        this.dexFile = dexBackedDexFile;
        this.classDef = dexBackedClassDef;
        this.startOffset = dexReader.getOffset();
        int largeUleb128 = dexReader.readLargeUleb128() + i;
        this.fieldIndex = largeUleb128;
        this.accessFlags = dexReader.readSmallUleb128();
        this.annotationSetOffset = annotationIterator.seekTo(largeUleb128);
        this.initialValueOffset = 0;
        this.initialValue = null;
        this.hiddenApiRestrictions = i2;
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public String getName() {
        return this.dexFile.getStringSection().get(this.dexFile.getBuffer().readSmallUint(getFieldIdItemOffset() + 4));
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    public String getType() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readUshort(getFieldIdItemOffset() + 2));
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public String getDefiningClass() {
        return this.classDef.getType();
    }

    @Override // org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public int getAccessFlags() {
        return this.accessFlags;
    }

    @Override // org.jf.dexlib2.iface.Field
    public EncodedValue getInitialValue() {
        return this.initialValue;
    }

    @Override // org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Annotatable
    public Set<? extends DexBackedAnnotation> getAnnotations() {
        return AnnotationsDirectory.getAnnotations(this.dexFile, this.annotationSetOffset);
    }

    @Override // org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public Set<HiddenApiRestriction> getHiddenApiRestrictions() {
        int i = this.hiddenApiRestrictions;
        if (i == 7) {
            return EmptySet.of();
        }
        return EnumSet.copyOf((Collection) HiddenApiRestriction.getAllFlags(i));
    }

    public static void skipFields(DexReader<?> dexReader, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            dexReader.skipUleb128();
            dexReader.skipUleb128();
        }
    }

    private int getFieldIdItemOffset() {
        if (this.fieldIdItemOffset == 0) {
            this.fieldIdItemOffset = this.dexFile.getFieldSection().getOffset(this.fieldIndex);
        }
        return this.fieldIdItemOffset;
    }

    public int getSize() {
        DexReader<? extends DexBuffer> dexReader = this.dexFile.getBuffer().readerAt(this.startOffset);
        dexReader.readLargeUleb128();
        dexReader.readSmallUleb128();
        int offset = dexReader.getOffset() - this.startOffset;
        if (!getAnnotations().isEmpty()) {
            offset += 8;
        }
        int i = this.initialValueOffset;
        if (i > 0) {
            dexReader.setOffset(i);
            if (this.initialValue != null) {
                DexBackedEncodedValue.skipFrom(dexReader);
                offset += dexReader.getOffset() - this.initialValueOffset;
            }
        }
        return offset + new DexBackedFieldReference(this.dexFile, this.fieldIndex).getSize();
    }
}
