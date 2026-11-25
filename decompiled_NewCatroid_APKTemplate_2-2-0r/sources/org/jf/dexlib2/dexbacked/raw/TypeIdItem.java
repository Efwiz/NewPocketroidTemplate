package org.jf.dexlib2.dexbacked.raw;

import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.dexlib2.util.AnnotatedBytes;

/* JADX INFO: loaded from: classes6.dex */
public class TypeIdItem {
    public static final int ITEM_SIZE = 4;

    public static SectionAnnotator makeAnnotator(DexAnnotator dexAnnotator, MapItem mapItem) {
        return new SectionAnnotator(dexAnnotator, mapItem) { // from class: org.jf.dexlib2.dexbacked.raw.TypeIdItem.1
            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public String getItemName() {
                return "type_id_item";
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            protected void annotateItem(AnnotatedBytes annotatedBytes, int i, String str) {
                annotatedBytes.annotate(4, StringIdItem.getReferenceAnnotation(this.dexFile, this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor())), new Object[0]);
            }
        };
    }

    public static String getReferenceAnnotation(DexBackedDexFile dexBackedDexFile, int i) {
        try {
            return String.format("type_id_item[%d]: %s", Integer.valueOf(i), dexBackedDexFile.getTypeSection().get(i));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return String.format("type_id_item[%d]", Integer.valueOf(i));
        }
    }

    public static String getOptionalReferenceAnnotation(DexBackedDexFile dexBackedDexFile, int i) {
        if (i == -1) {
            return "type_id_item[NO_INDEX]";
        }
        return getReferenceAnnotation(dexBackedDexFile, i);
    }
}
