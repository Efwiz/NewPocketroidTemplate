package org.jf.dexlib2.dexbacked.raw;

import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.dexlib2.dexbacked.value.DexBackedArrayEncodedValue;
import org.jf.dexlib2.util.AnnotatedBytes;

/* JADX INFO: loaded from: classes6.dex */
public class CallSiteIdItem {
    public static final int ITEM_SIZE = 4;

    public static SectionAnnotator makeAnnotator(DexAnnotator dexAnnotator, MapItem mapItem) {
        return new SectionAnnotator(dexAnnotator, mapItem) { // from class: org.jf.dexlib2.dexbacked.raw.CallSiteIdItem.1
            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public String getItemName() {
                return "call_site_id_item";
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            protected void annotateItem(AnnotatedBytes annotatedBytes, int i, String str) {
                int smallUint = this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor());
                annotatedBytes.annotate(4, "call_site_id_item[0x%x] = %s", Integer.valueOf(smallUint), new DexBackedArrayEncodedValue(this.dexFile, this.dexFile.getDataBuffer().readerAt(smallUint)));
            }
        };
    }
}
