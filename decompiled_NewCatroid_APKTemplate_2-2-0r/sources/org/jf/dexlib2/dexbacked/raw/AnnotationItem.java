package org.jf.dexlib2.dexbacked.raw;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexBuffer;
import org.jf.dexlib2.dexbacked.DexReader;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.dexlib2.util.AnnotatedBytes;

/* JADX INFO: loaded from: classes6.dex */
public class AnnotationItem {
    public static final int ANNOTATION_OFFSET = 1;
    public static final int VISIBILITY_OFFSET = 0;

    public static SectionAnnotator makeAnnotator(DexAnnotator dexAnnotator, MapItem mapItem) {
        return new SectionAnnotator(dexAnnotator, mapItem) { // from class: org.jf.dexlib2.dexbacked.raw.AnnotationItem.1
            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public String getItemName() {
                return "annotation_item";
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            protected void annotateItem(AnnotatedBytes annotatedBytes, int i, String str) {
                int ubyte = this.dexFile.getBuffer().readUbyte(annotatedBytes.getCursor());
                annotatedBytes.annotate(1, "visibility = %d: %s", Integer.valueOf(ubyte), AnnotationItem.getAnnotationVisibility(ubyte));
                EncodedValue.annotateEncodedAnnotation(this.dexFile, annotatedBytes, this.dexFile.getBuffer().readerAt(annotatedBytes.getCursor()));
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getAnnotationVisibility(int i) {
        if (i == 0) {
            return JsonPOJOBuilder.DEFAULT_BUILD_METHOD;
        }
        if (i == 1) {
            return "runtime";
        }
        if (i == 2) {
            return "system";
        }
        return "invalid visibility";
    }

    public static String getReferenceAnnotation(DexBackedDexFile dexBackedDexFile, int i) {
        try {
            DexReader<? extends DexBuffer> dexReader = dexBackedDexFile.getDataBuffer().readerAt(i);
            dexReader.readUbyte();
            return String.format("annotation_item[0x%x]: %s", Integer.valueOf(i), dexBackedDexFile.getTypeSection().get(dexReader.readSmallUleb128()));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return String.format("annotation_item[0x%x]", Integer.valueOf(i));
        }
    }
}
