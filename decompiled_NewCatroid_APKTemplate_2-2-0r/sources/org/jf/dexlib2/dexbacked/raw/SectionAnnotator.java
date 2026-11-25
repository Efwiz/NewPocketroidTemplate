package org.jf.dexlib2.dexbacked.raw;

import java.util.HashMap;
import java.util.Map;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.dexlib2.util.AlignmentUtils;
import org.jf.dexlib2.util.AnnotatedBytes;

/* JADX INFO: loaded from: classes6.dex */
public abstract class SectionAnnotator {
    public final DexAnnotator annotator;
    public final DexBackedDexFile dexFile;
    public final int itemCount;
    protected Map<Integer, String> itemIdentities = new HashMap();
    public final int itemType;
    public final int sectionOffset;

    protected abstract void annotateItem(AnnotatedBytes annotatedBytes, int i, String str);

    public int getItemAlignment() {
        return 1;
    }

    public abstract String getItemName();

    public SectionAnnotator(DexAnnotator dexAnnotator, MapItem mapItem) {
        this.annotator = dexAnnotator;
        DexBackedDexFile dexBackedDexFile = dexAnnotator.dexFile;
        this.dexFile = dexBackedDexFile;
        this.itemType = mapItem.getType();
        if (mapItem.getType() >= 4096) {
            this.sectionOffset = mapItem.getOffset() + dexBackedDexFile.getBaseDataOffset();
        } else {
            this.sectionOffset = mapItem.getOffset();
        }
        this.itemCount = mapItem.getItemCount();
    }

    public void annotateSection(AnnotatedBytes annotatedBytes) {
        annotatedBytes.moveTo(this.sectionOffset);
        annotateSectionInner(annotatedBytes, this.itemCount);
    }

    protected int getItemOffset(int i, int i2) {
        return AlignmentUtils.alignOffset(i2, getItemAlignment());
    }

    protected void annotateSectionInner(AnnotatedBytes annotatedBytes, int i) {
        String itemName = getItemName();
        if (i > 0) {
            annotatedBytes.annotate(0, "", new Object[0]);
            annotatedBytes.annotate(0, "-----------------------------", new Object[0]);
            annotatedBytes.annotate(0, "%s section", itemName);
            annotatedBytes.annotate(0, "-----------------------------", new Object[0]);
            annotatedBytes.annotate(0, "", new Object[0]);
            for (int i2 = 0; i2 < i; i2++) {
                annotatedBytes.moveTo(getItemOffset(i2, annotatedBytes.getCursor()));
                String itemIdentity = getItemIdentity(annotatedBytes.getCursor());
                if (itemIdentity != null) {
                    annotatedBytes.annotate(0, "[%d] %s: %s", Integer.valueOf(i2), itemName, itemIdentity);
                } else {
                    annotatedBytes.annotate(0, "[%d] %s", Integer.valueOf(i2), itemName);
                }
                annotatedBytes.indent();
                annotateItem(annotatedBytes, i2, itemIdentity);
                annotatedBytes.deindent();
            }
        }
    }

    private String getItemIdentity(int i) {
        return this.itemIdentities.get(Integer.valueOf(i));
    }

    public void setItemIdentity(int i, String str) {
        this.itemIdentities.put(Integer.valueOf(i + this.dexFile.getBaseDataOffset()), str);
    }
}
