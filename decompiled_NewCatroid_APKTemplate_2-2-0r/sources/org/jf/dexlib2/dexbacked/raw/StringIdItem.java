package org.jf.dexlib2.dexbacked.raw;

import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.dexlib2.util.AnnotatedBytes;
import org.jf.util.StringUtils;

/* JADX INFO: loaded from: classes6.dex */
public class StringIdItem {
    public static final int ITEM_SIZE = 4;

    public static SectionAnnotator makeAnnotator(DexAnnotator dexAnnotator, MapItem mapItem) {
        return new SectionAnnotator(dexAnnotator, mapItem) { // from class: org.jf.dexlib2.dexbacked.raw.StringIdItem.1
            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public String getItemName() {
                return "string_id_item";
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public void annotateItem(AnnotatedBytes annotatedBytes, int i, String str) {
                int smallUint = this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor());
                try {
                    annotatedBytes.annotate(4, "string_data_item[0x%x]: \"%s\"", Integer.valueOf(smallUint), StringUtils.escapeString(this.dexFile.getStringSection().get(i)));
                } catch (Exception e) {
                    System.err.print("Error while resolving string value at index: ");
                    System.err.print(i);
                    e.printStackTrace(System.err);
                    annotatedBytes.annotate(4, "string_id_item[0x%x]", Integer.valueOf(smallUint));
                }
            }
        };
    }

    public static String getReferenceAnnotation(DexBackedDexFile dexBackedDexFile, int i) {
        return getReferenceAnnotation(dexBackedDexFile, i, false);
    }

    public static String getReferenceAnnotation(DexBackedDexFile dexBackedDexFile, int i, boolean z) {
        try {
            String str = dexBackedDexFile.getStringSection().get(i);
            if (z) {
                str = String.format("\"%s\"", StringUtils.escapeString(str));
            }
            return String.format("string_id_item[%d]: %s", Integer.valueOf(i), str);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return String.format("string_id_item[%d]", Integer.valueOf(i));
        }
    }

    public static String getOptionalReferenceAnnotation(DexBackedDexFile dexBackedDexFile, int i) {
        return getOptionalReferenceAnnotation(dexBackedDexFile, i, false);
    }

    public static String getOptionalReferenceAnnotation(DexBackedDexFile dexBackedDexFile, int i, boolean z) {
        if (i == -1) {
            return "string_id_item[NO_INDEX]";
        }
        return getReferenceAnnotation(dexBackedDexFile, i, z);
    }
}
