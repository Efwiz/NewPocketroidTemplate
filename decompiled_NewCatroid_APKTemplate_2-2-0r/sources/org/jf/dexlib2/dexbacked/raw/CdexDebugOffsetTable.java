package org.jf.dexlib2.dexbacked.raw;

import org.jf.dexlib2.dexbacked.CDexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexBuffer;
import org.jf.dexlib2.dexbacked.DexReader;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;

/* JADX INFO: loaded from: classes6.dex */
public class CdexDebugOffsetTable {
    public static void annotate(DexAnnotator dexAnnotator, DexBuffer dexBuffer) {
        DexReader<? extends DexBuffer> dexReader = dexBuffer.readerAt(dexAnnotator.getCursor());
        SectionAnnotator annotator = dexAnnotator.getAnnotator(ItemType.DEBUG_INFO_ITEM);
        int size = dexAnnotator.dexFile.getMethodSection().size();
        int i = 0;
        while (i < size) {
            int i2 = i + 16;
            dexAnnotator.annotate(0, "Offset chuck for methods %d-%d", Integer.valueOf(i), Integer.valueOf(Math.min(i2, size)));
            dexAnnotator.indent();
            int ubyte = (dexReader.readUbyte() << 8) | dexReader.readUbyte();
            StringBuilder sb = new StringBuilder();
            for (int i3 = 0; i3 < 16; i3++) {
                sb.append((ubyte >> i3) & 1);
            }
            dexAnnotator.annotate(2, "bitmask: 0b%s", sb.reverse());
            int debugInfoBase = ((CDexBackedDexFile) dexAnnotator.dexFile).getDebugInfoBase();
            for (int i4 = 0; i4 < 16; i4++) {
                if ((ubyte & 1) != 0) {
                    int bigUleb128 = dexReader.readBigUleb128();
                    debugInfoBase += bigUleb128;
                    int i5 = i + i4;
                    dexAnnotator.annotateTo(dexReader.getOffset(), "[method_id: %d]: offset_delta: %d  (offset=0x%x)", Integer.valueOf(i5), Integer.valueOf(bigUleb128), Integer.valueOf(debugInfoBase));
                    annotator.setItemIdentity(debugInfoBase, dexAnnotator.dexFile.getMethodSection().get(i5).toString());
                }
                ubyte >>= 1;
            }
            dexAnnotator.deindent();
            i = i2;
        }
    }
}
