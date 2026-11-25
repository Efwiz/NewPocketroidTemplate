package org.jf.dexlib2.dexbacked.raw;

import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexBuffer;
import org.jf.dexlib2.dexbacked.DexReader;
import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
import org.jf.dexlib2.util.AnnotatedBytes;
import org.jf.util.StringUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ClassDataItem {
    public static SectionAnnotator makeAnnotator(DexAnnotator dexAnnotator, MapItem mapItem) {
        return new SectionAnnotator(dexAnnotator, mapItem) { // from class: org.jf.dexlib2.dexbacked.raw.ClassDataItem.1
            private SectionAnnotator codeItemAnnotator = null;

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public void annotateSection(AnnotatedBytes annotatedBytes) {
                this.codeItemAnnotator = this.annotator.getAnnotator(ItemType.CODE_ITEM);
                super.annotateSection(annotatedBytes);
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            public String getItemName() {
                return "class_data_item";
            }

            @Override // org.jf.dexlib2.dexbacked.raw.SectionAnnotator
            protected void annotateItem(AnnotatedBytes annotatedBytes, int i, String str) {
                DexReader<? extends DexBuffer> dexReader = this.dexFile.getBuffer().readerAt(annotatedBytes.getCursor());
                int smallUleb128 = dexReader.readSmallUleb128();
                annotatedBytes.annotateTo(dexReader.getOffset(), "static_fields_size = %d", Integer.valueOf(smallUleb128));
                int smallUleb129 = dexReader.readSmallUleb128();
                annotatedBytes.annotateTo(dexReader.getOffset(), "instance_fields_size = %d", Integer.valueOf(smallUleb129));
                int smallUleb1210 = dexReader.readSmallUleb128();
                annotatedBytes.annotateTo(dexReader.getOffset(), "direct_methods_size = %d", Integer.valueOf(smallUleb1210));
                int smallUleb1211 = dexReader.readSmallUleb128();
                annotatedBytes.annotateTo(dexReader.getOffset(), "virtual_methods_size = %d", Integer.valueOf(smallUleb1211));
                if (smallUleb128 > 0) {
                    annotatedBytes.annotate(0, "static_fields:", new Object[0]);
                    annotatedBytes.indent();
                    int iAnnotateEncodedField = 0;
                    for (int i2 = 0; i2 < smallUleb128; i2++) {
                        annotatedBytes.annotate(0, "static_field[%d]", Integer.valueOf(i2));
                        annotatedBytes.indent();
                        iAnnotateEncodedField = annotateEncodedField(annotatedBytes, this.dexFile, dexReader, iAnnotateEncodedField);
                        annotatedBytes.deindent();
                    }
                    annotatedBytes.deindent();
                }
                if (smallUleb129 > 0) {
                    annotatedBytes.annotate(0, "instance_fields:", new Object[0]);
                    annotatedBytes.indent();
                    int iAnnotateEncodedField2 = 0;
                    for (int i3 = 0; i3 < smallUleb129; i3++) {
                        annotatedBytes.annotate(0, "instance_field[%d]", Integer.valueOf(i3));
                        annotatedBytes.indent();
                        iAnnotateEncodedField2 = annotateEncodedField(annotatedBytes, this.dexFile, dexReader, iAnnotateEncodedField2);
                        annotatedBytes.deindent();
                    }
                    annotatedBytes.deindent();
                }
                if (smallUleb1210 > 0) {
                    annotatedBytes.annotate(0, "direct_methods:", new Object[0]);
                    annotatedBytes.indent();
                    int iAnnotateEncodedMethod = 0;
                    for (int i4 = 0; i4 < smallUleb1210; i4++) {
                        annotatedBytes.annotate(0, "direct_method[%d]", Integer.valueOf(i4));
                        annotatedBytes.indent();
                        iAnnotateEncodedMethod = annotateEncodedMethod(annotatedBytes, this.dexFile, dexReader, iAnnotateEncodedMethod);
                        annotatedBytes.deindent();
                    }
                    annotatedBytes.deindent();
                }
                if (smallUleb1211 > 0) {
                    annotatedBytes.annotate(0, "virtual_methods:", new Object[0]);
                    annotatedBytes.indent();
                    int iAnnotateEncodedMethod2 = 0;
                    for (int i5 = 0; i5 < smallUleb1211; i5++) {
                        annotatedBytes.annotate(0, "virtual_method[%d]", Integer.valueOf(i5));
                        annotatedBytes.indent();
                        iAnnotateEncodedMethod2 = annotateEncodedMethod(annotatedBytes, this.dexFile, dexReader, iAnnotateEncodedMethod2);
                        annotatedBytes.deindent();
                    }
                    annotatedBytes.deindent();
                }
            }

            private int annotateEncodedField(AnnotatedBytes annotatedBytes, DexBackedDexFile dexBackedDexFile, DexReader dexReader, int i) {
                int largeUleb128 = dexReader.readLargeUleb128();
                int i2 = i + largeUleb128;
                annotatedBytes.annotateTo(dexReader.getOffset(), "field_idx_diff = %d: %s", Integer.valueOf(largeUleb128), FieldIdItem.getReferenceAnnotation(dexBackedDexFile, i2));
                int smallUleb128 = dexReader.readSmallUleb128();
                annotatedBytes.annotateTo(dexReader.getOffset(), "access_flags = 0x%x: %s", Integer.valueOf(smallUleb128), StringUtils.join("|", AccessFlags.getAccessFlagsForField(smallUleb128)));
                return i2;
            }

            private int annotateEncodedMethod(AnnotatedBytes annotatedBytes, DexBackedDexFile dexBackedDexFile, DexReader dexReader, int i) {
                int largeUleb128 = dexReader.readLargeUleb128();
                int i2 = i + largeUleb128;
                annotatedBytes.annotateTo(dexReader.getOffset(), "method_idx_diff = %d: %s", Integer.valueOf(largeUleb128), MethodIdItem.getReferenceAnnotation(dexBackedDexFile, i2));
                int smallUleb128 = dexReader.readSmallUleb128();
                annotatedBytes.annotateTo(dexReader.getOffset(), "access_flags = 0x%x: %s", Integer.valueOf(smallUleb128), StringUtils.join("|", AccessFlags.getAccessFlagsForMethod(smallUleb128)));
                int smallUleb129 = dexReader.readSmallUleb128();
                if (smallUleb129 == 0) {
                    annotatedBytes.annotateTo(dexReader.getOffset(), "code_off = code_item[NO_OFFSET]", new Object[0]);
                } else {
                    annotatedBytes.annotateTo(dexReader.getOffset(), "code_off = code_item[0x%x]", Integer.valueOf(smallUleb129));
                    addCodeItemIdentity(smallUleb129, MethodIdItem.asString(dexBackedDexFile, i2));
                }
                return i2;
            }

            private void addCodeItemIdentity(int i, String str) {
                SectionAnnotator sectionAnnotator = this.codeItemAnnotator;
                if (sectionAnnotator != null) {
                    sectionAnnotator.setItemIdentity(i, str);
                }
            }
        };
    }
}
