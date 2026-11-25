package org.jf.dexlib2.dexbacked.raw;

import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.dexlib2.util.AnnotatedBytes;

/* JADX INFO: loaded from: classes6.dex */
public class FieldIdItem {
    public static final int CLASS_OFFSET = 0;
    public static final int ITEM_SIZE = 8;
    public static final int NAME_OFFSET = 4;
    public static final int TYPE_OFFSET = 2;

    public static SectionAnnotator makeAnnotator(DexAnnotator dexAnnotator, MapItem mapItem) {
        return new SectionAnnotator(dexAnnotator, mapItem) { // from class: org.jf.dexlib2.dexbacked.raw.FieldIdItem.1
            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public String getItemName() {
                return "field_id_item";
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            protected void annotateItem(AnnotatedBytes annotatedBytes, int i, String str) {
                annotatedBytes.annotate(2, "class_idx = %s", TypeIdItem.getReferenceAnnotation(this.dexFile, this.dexFile.getBuffer().readUshort(annotatedBytes.getCursor())));
                annotatedBytes.annotate(2, "return_type_idx = %s", TypeIdItem.getReferenceAnnotation(this.dexFile, this.dexFile.getBuffer().readUshort(annotatedBytes.getCursor())));
                annotatedBytes.annotate(4, "name_idx = %s", StringIdItem.getReferenceAnnotation(this.dexFile, this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor())));
            }
        };
    }

    public static String asString(DexBackedDexFile dexBackedDexFile, int i) {
        int offset = dexBackedDexFile.getFieldSection().getOffset(i);
        return String.format("%s->%s:%s", dexBackedDexFile.getTypeSection().get(dexBackedDexFile.getBuffer().readUshort(offset)), dexBackedDexFile.getStringSection().get(dexBackedDexFile.getBuffer().readSmallUint(offset + 4)), dexBackedDexFile.getTypeSection().get(dexBackedDexFile.getBuffer().readUshort(offset + 2)));
    }

    public static String getReferenceAnnotation(DexBackedDexFile dexBackedDexFile, int i) {
        try {
            return String.format("field_id_item[%d]: %s", Integer.valueOf(i), asString(dexBackedDexFile, i));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return String.format("field_id_item[%d]", Integer.valueOf(i));
        }
    }

    public static String[] getFields(DexBackedDexFile dexBackedDexFile) {
        MapItem mapItemForSection = dexBackedDexFile.getMapItemForSection(4);
        if (mapItemForSection == null) {
            return new String[0];
        }
        int itemCount = mapItemForSection.getItemCount();
        String[] strArr = new String[itemCount];
        for (int i = 0; i < itemCount; i++) {
            strArr[i] = asString(dexBackedDexFile, i);
        }
        return strArr;
    }
}
