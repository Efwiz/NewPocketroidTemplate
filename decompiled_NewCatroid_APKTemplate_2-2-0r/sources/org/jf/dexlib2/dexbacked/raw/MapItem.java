package org.jf.dexlib2.dexbacked.raw;

import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.dexlib2.util.AnnotatedBytes;

/* JADX INFO: loaded from: classes6.dex */
public class MapItem {
    public static final int ITEM_SIZE = 12;
    public static final int OFFSET_OFFSET = 8;
    public static final int SIZE_OFFSET = 4;
    public static final int TYPE_OFFSET = 0;
    private final DexBackedDexFile dexFile;
    private final int offset;

    public MapItem(DexBackedDexFile dexBackedDexFile, int i) {
        this.dexFile = dexBackedDexFile;
        this.offset = i;
    }

    public int getType() {
        return this.dexFile.getDataBuffer().readUshort(this.offset);
    }

    public String getName() {
        return ItemType.getItemTypeName(getType());
    }

    public int getItemCount() {
        return this.dexFile.getDataBuffer().readSmallUint(this.offset + 4);
    }

    public int getOffset() {
        return this.dexFile.getDataBuffer().readSmallUint(this.offset + 8);
    }

    public static SectionAnnotator makeAnnotator(DexAnnotator dexAnnotator, MapItem mapItem) {
        return new SectionAnnotator(dexAnnotator, mapItem) { // from class: org.jf.dexlib2.dexbacked.raw.MapItem.1
            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public String getItemName() {
                return "map_item";
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            protected void annotateItem(AnnotatedBytes annotatedBytes, int i, String str) {
                int ushort = this.dexFile.getBuffer().readUshort(annotatedBytes.getCursor());
                annotatedBytes.annotate(2, "type = 0x%x: %s", Integer.valueOf(ushort), ItemType.getItemTypeName(ushort));
                annotatedBytes.annotate(2, "unused", new Object[0]);
                annotatedBytes.annotate(4, "size = %d", Integer.valueOf(this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor())));
                annotatedBytes.annotate(4, "offset = 0x%x", Integer.valueOf(this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor())));
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public void annotateSection(AnnotatedBytes annotatedBytes) {
                annotatedBytes.moveTo(this.sectionOffset);
                int smallUint = this.dexFile.getBuffer().readSmallUint(annotatedBytes.getCursor());
                annotatedBytes.annotate(4, "size = %d", Integer.valueOf(smallUint));
                super.annotateSectionInner(annotatedBytes, smallUint);
            }
        };
    }
}
