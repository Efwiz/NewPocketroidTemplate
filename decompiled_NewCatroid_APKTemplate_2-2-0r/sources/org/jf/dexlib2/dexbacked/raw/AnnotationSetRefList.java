package org.jf.dexlib2.dexbacked.raw;

import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.dexlib2.util.AnnotatedBytes;

/* JADX INFO: loaded from: classes6.dex */
public class AnnotationSetRefList {
    public static final int LIST_OFFSET = 4;
    public static final int SIZE_OFFSET = 0;

    public static SectionAnnotator makeAnnotator(DexAnnotator dexAnnotator, MapItem mapItem) {
        return new SectionAnnotator(dexAnnotator, mapItem) { // from class: org.jf.dexlib2.dexbacked.raw.AnnotationSetRefList.1
            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public int getItemAlignment() {
                return 4;
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public String getItemName() {
                return "annotation_set_ref_list";
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            protected void annotateItem(AnnotatedBytes annotatedBytes, int i, String str) {
                int smallUint = this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor());
                annotatedBytes.annotate(4, "size = %d", Integer.valueOf(smallUint));
                for (int i2 = 0; i2 < smallUint; i2++) {
                    annotatedBytes.annotate(4, "annotation_set_item[0x%x]", Integer.valueOf(this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor())));
                }
            }
        };
    }

    public static String getReferenceAnnotation(DexBackedDexFile dexBackedDexFile, int i) {
        if (i == 0) {
            return "annotation_set_ref_list[NO_OFFSET]";
        }
        return String.format("annotation_set_ref_list[0x%x]", Integer.valueOf(i));
    }
}
