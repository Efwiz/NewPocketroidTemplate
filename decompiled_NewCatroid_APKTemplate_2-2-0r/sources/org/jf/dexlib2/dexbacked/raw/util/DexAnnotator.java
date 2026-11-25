package org.jf.dexlib2.dexbacked.raw.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jf.dexlib2.dexbacked.CDexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.raw.AnnotationDirectoryItem;
import org.jf.dexlib2.dexbacked.raw.AnnotationItem;
import org.jf.dexlib2.dexbacked.raw.AnnotationSetItem;
import org.jf.dexlib2.dexbacked.raw.AnnotationSetRefList;
import org.jf.dexlib2.dexbacked.raw.CallSiteIdItem;
import org.jf.dexlib2.dexbacked.raw.CdexDebugOffsetTable;
import org.jf.dexlib2.dexbacked.raw.ClassDataItem;
import org.jf.dexlib2.dexbacked.raw.ClassDefItem;
import org.jf.dexlib2.dexbacked.raw.CodeItem;
import org.jf.dexlib2.dexbacked.raw.DebugInfoItem;
import org.jf.dexlib2.dexbacked.raw.EncodedArrayItem;
import org.jf.dexlib2.dexbacked.raw.FieldIdItem;
import org.jf.dexlib2.dexbacked.raw.HeaderItem;
import org.jf.dexlib2.dexbacked.raw.HiddenApiClassDataItem;
import org.jf.dexlib2.dexbacked.raw.ItemType;
import org.jf.dexlib2.dexbacked.raw.MapItem;
import org.jf.dexlib2.dexbacked.raw.MethodHandleItem;
import org.jf.dexlib2.dexbacked.raw.MethodIdItem;
import org.jf.dexlib2.dexbacked.raw.ProtoIdItem;
import org.jf.dexlib2.dexbacked.raw.SectionAnnotator;
import org.jf.dexlib2.dexbacked.raw.StringDataItem;
import org.jf.dexlib2.dexbacked.raw.StringIdItem;
import org.jf.dexlib2.dexbacked.raw.TypeIdItem;
import org.jf.dexlib2.dexbacked.raw.TypeListItem;
import org.jf.dexlib2.util.AnnotatedBytes;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class DexAnnotator extends AnnotatedBytes {
    private static final Map<Integer, Integer> sectionAnnotationOrder = new HashMap();
    private final Map<Integer, SectionAnnotator> annotators;
    public final DexBackedDexFile dexFile;

    static {
        int[] iArr = {4096, 0, 1, 2, 3, 4, 5, 7, 8, 6, 8192, ItemType.CODE_ITEM, ItemType.DEBUG_INFO_ITEM, 4097, 4098, 4099, 8194, 8196, 8197, ItemType.ANNOTATION_DIRECTORY_ITEM, 61440};
        for (int i = 0; i < 21; i++) {
            sectionAnnotationOrder.put(Integer.valueOf(iArr[i]), Integer.valueOf(i));
        }
    }

    public DexAnnotator(DexBackedDexFile dexBackedDexFile, int i) {
        super(i);
        this.annotators = new HashMap();
        this.dexFile = dexBackedDexFile;
        for (MapItem mapItem : dexBackedDexFile.getMapItems()) {
            int type = mapItem.getType();
            if (type != 61440) {
                switch (type) {
                    case 0:
                        this.annotators.put(Integer.valueOf(mapItem.getType()), HeaderItem.makeAnnotator(this, mapItem));
                        break;
                    case 1:
                        this.annotators.put(Integer.valueOf(mapItem.getType()), StringIdItem.makeAnnotator(this, mapItem));
                        break;
                    case 2:
                        this.annotators.put(Integer.valueOf(mapItem.getType()), TypeIdItem.makeAnnotator(this, mapItem));
                        break;
                    case 3:
                        this.annotators.put(Integer.valueOf(mapItem.getType()), ProtoIdItem.makeAnnotator(this, mapItem));
                        break;
                    case 4:
                        this.annotators.put(Integer.valueOf(mapItem.getType()), FieldIdItem.makeAnnotator(this, mapItem));
                        break;
                    case 5:
                        this.annotators.put(Integer.valueOf(mapItem.getType()), MethodIdItem.makeAnnotator(this, mapItem));
                        break;
                    case 6:
                        this.annotators.put(Integer.valueOf(mapItem.getType()), ClassDefItem.makeAnnotator(this, mapItem));
                        break;
                    case 7:
                        this.annotators.put(Integer.valueOf(mapItem.getType()), CallSiteIdItem.makeAnnotator(this, mapItem));
                        break;
                    case 8:
                        this.annotators.put(Integer.valueOf(mapItem.getType()), MethodHandleItem.makeAnnotator(this, mapItem));
                        break;
                    default:
                        switch (type) {
                            case 4096:
                                this.annotators.put(Integer.valueOf(mapItem.getType()), MapItem.makeAnnotator(this, mapItem));
                                break;
                            case 4097:
                                this.annotators.put(Integer.valueOf(mapItem.getType()), TypeListItem.makeAnnotator(this, mapItem));
                                break;
                            case 4098:
                                this.annotators.put(Integer.valueOf(mapItem.getType()), AnnotationSetRefList.makeAnnotator(this, mapItem));
                                break;
                            case 4099:
                                this.annotators.put(Integer.valueOf(mapItem.getType()), AnnotationSetItem.makeAnnotator(this, mapItem));
                                break;
                            default:
                                switch (type) {
                                    case 8192:
                                        this.annotators.put(Integer.valueOf(mapItem.getType()), ClassDataItem.makeAnnotator(this, mapItem));
                                        break;
                                    case ItemType.CODE_ITEM /* 8193 */:
                                        this.annotators.put(Integer.valueOf(mapItem.getType()), CodeItem.makeAnnotator(this, mapItem));
                                        break;
                                    case 8194:
                                        this.annotators.put(Integer.valueOf(mapItem.getType()), StringDataItem.makeAnnotator(this, mapItem));
                                        break;
                                    case ItemType.DEBUG_INFO_ITEM /* 8195 */:
                                        this.annotators.put(Integer.valueOf(mapItem.getType()), DebugInfoItem.makeAnnotator(this, mapItem));
                                        break;
                                    case 8196:
                                        this.annotators.put(Integer.valueOf(mapItem.getType()), AnnotationItem.makeAnnotator(this, mapItem));
                                        break;
                                    case 8197:
                                        this.annotators.put(Integer.valueOf(mapItem.getType()), EncodedArrayItem.makeAnnotator(this, mapItem));
                                        break;
                                    case ItemType.ANNOTATION_DIRECTORY_ITEM /* 8198 */:
                                        this.annotators.put(Integer.valueOf(mapItem.getType()), AnnotationDirectoryItem.makeAnnotator(this, mapItem));
                                        break;
                                    default:
                                        throw new RuntimeException(String.format("Unrecognized item type: 0x%x", Integer.valueOf(mapItem.getType())));
                                }
                                break;
                        }
                        break;
                }
            } else {
                this.annotators.put(Integer.valueOf(mapItem.getType()), HiddenApiClassDataItem.makeAnnotator(this, mapItem));
            }
        }
    }

    public void writeAnnotations(Writer writer) throws IOException {
        List<MapItem> listSortedCopy = ListUtil.sortedCopy(this.dexFile.getMapItems(), new Comparator<MapItem>() { // from class: org.jf.dexlib2.dexbacked.raw.util.DexAnnotator.1
            @Override // java.util.Comparator
            public int compare(MapItem mapItem, MapItem mapItem2) {
                return Integer.compare(((Integer) DexAnnotator.sectionAnnotationOrder.get(Integer.valueOf(mapItem.getType()))).intValue(), ((Integer) DexAnnotator.sectionAnnotationOrder.get(Integer.valueOf(mapItem2.getType()))).intValue());
            }
        });
        try {
            DexBackedDexFile dexBackedDexFile = this.dexFile;
            if (dexBackedDexFile instanceof CDexBackedDexFile) {
                moveTo(dexBackedDexFile.getBaseDataOffset() + ((CDexBackedDexFile) this.dexFile).getDebugInfoOffsetsPos());
                CdexDebugOffsetTable.annotate(this, this.dexFile.getBuffer());
            }
            for (MapItem mapItem : listSortedCopy) {
                try {
                    this.annotators.get(Integer.valueOf(mapItem.getType())).annotateSection(this);
                } catch (Exception e) {
                    System.err.println(String.format("There was an error while dumping the %s section", ItemType.getItemTypeName(mapItem.getType())));
                    e.printStackTrace(System.err);
                }
            }
            writeAnnotations(writer, this.dexFile.getBuffer().getBuf(), this.dexFile.getBuffer().getBaseOffset());
        } catch (Throwable th) {
            writeAnnotations(writer, this.dexFile.getBuffer().getBuf(), this.dexFile.getBuffer().getBaseOffset());
            throw th;
        }
    }

    public SectionAnnotator getAnnotator(int i) {
        return this.annotators.get(Integer.valueOf(i));
    }
}
