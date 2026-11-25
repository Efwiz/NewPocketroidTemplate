package org.jf.dexlib2.dexbacked.raw;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.dexlib2.util.AnnotatedBytes;

/* JADX INFO: loaded from: classes6.dex */
public class ProtoIdItem {
    public static final int ITEM_SIZE = 12;
    public static final int PARAMETERS_OFFSET = 8;
    public static final int RETURN_TYPE_OFFSET = 4;
    public static final int SHORTY_OFFSET = 0;

    public static SectionAnnotator makeAnnotator(DexAnnotator dexAnnotator, MapItem mapItem) {
        return new SectionAnnotator(dexAnnotator, mapItem) { // from class: org.jf.dexlib2.dexbacked.raw.ProtoIdItem.1
            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public String getItemName() {
                return "proto_id_item";
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            protected void annotateItem(AnnotatedBytes annotatedBytes, int i, String str) {
                annotatedBytes.annotate(4, "shorty_idx = %s", StringIdItem.getReferenceAnnotation(this.dexFile, this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor())));
                annotatedBytes.annotate(4, "return_type_idx = %s", TypeIdItem.getReferenceAnnotation(this.dexFile, this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor())));
                annotatedBytes.annotate(4, "parameters_off = %s", TypeListItem.getReferenceAnnotation(this.dexFile, this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor())));
            }
        };
    }

    public static String getReferenceAnnotation(DexBackedDexFile dexBackedDexFile, int i) {
        try {
            return String.format("proto_id_item[%d]: %s", Integer.valueOf(i), asString(dexBackedDexFile, i));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return String.format("proto_id_item[%d]", Integer.valueOf(i));
        }
    }

    public static String asString(DexBackedDexFile dexBackedDexFile, int i) {
        int offset = dexBackedDexFile.getProtoSection().getOffset(i);
        return "(" + TypeListItem.asString(dexBackedDexFile, dexBackedDexFile.getBuffer().readSmallUint(offset + 8)) + Constant.AFTER_QUTO + dexBackedDexFile.getTypeSection().get(dexBackedDexFile.getBuffer().readSmallUint(offset + 4));
    }

    public static String[] getProtos(DexBackedDexFile dexBackedDexFile) {
        MapItem mapItemForSection = dexBackedDexFile.getMapItemForSection(3);
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
