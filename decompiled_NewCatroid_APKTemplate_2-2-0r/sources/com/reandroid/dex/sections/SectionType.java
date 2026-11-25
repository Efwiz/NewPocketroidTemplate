package com.reandroid.dex.sections;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.arsc.item.NumberIntegerReference;
import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.data.AnnotationGroup;
import com.reandroid.dex.data.AnnotationItem;
import com.reandroid.dex.data.AnnotationSet;
import com.reandroid.dex.data.AnnotationsDirectory;
import com.reandroid.dex.data.ClassData;
import com.reandroid.dex.data.CodeItem;
import com.reandroid.dex.data.DataItem;
import com.reandroid.dex.data.DebugInfo;
import com.reandroid.dex.data.EncodedArray;
import com.reandroid.dex.data.HiddenApiRestrictions;
import com.reandroid.dex.data.StringData;
import com.reandroid.dex.data.TypeList;
import com.reandroid.dex.header.DexHeader;
import com.reandroid.dex.id.CallSiteId;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.id.FieldId;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.id.MethodHandle;
import com.reandroid.dex.id.MethodId;
import com.reandroid.dex.id.ProtoId;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.id.TypeId;
import com.reandroid.utils.collection.ArrayIterator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;
import org.jf.dexlib2.dexbacked.raw.ItemType;

/* JADX INFO: loaded from: classes11.dex */
public class SectionType<T extends SectionItem> {
    public static final SectionType<AnnotationsDirectory> ANNOTATION_DIRECTORY;
    public static final SectionType<AnnotationGroup> ANNOTATION_GROUP;
    public static final SectionType<AnnotationItem> ANNOTATION_ITEM;
    public static final SectionType<AnnotationSet> ANNOTATION_SET;
    public static final SectionType<CallSiteId> CALL_SITE_ID;
    public static final SectionType<ClassData> CLASS_DATA;
    public static final SectionType<ClassId> CLASS_ID;
    public static final SectionType<CodeItem> CODE;
    private static final SectionType<?>[] DATA_REMOVE_ORDER;
    public static final SectionType<DebugInfo> DEBUG_INFO;
    private static final SectionType<?>[] DEX_LIB2_ORDER;
    public static final SectionType<EncodedArray> ENCODED_ARRAY;
    public static final SectionType<FieldId> FIELD_ID;
    public static final SectionType<DexHeader> HEADER;
    public static final SectionType<HiddenApiRestrictions> HIDDEN_API;
    public static final SectionType<?>[] ID_TYPES;
    public static final SectionType<MapList> MAP_LIST;
    public static final SectionType<MethodHandle> METHOD_HANDLE;
    public static final SectionType<MethodId> METHOD_ID;
    public static final SectionType<ProtoId> PROTO_ID;
    private static final SectionType<?>[] R8_ORDER;
    private static final SectionType<?>[] READ_ORDER;
    private static final SectionType<?>[] SORT_SECTIONS_ORDER;
    public static final SectionType<StringData> STRING_DATA;
    public static final SectionType<StringId> STRING_ID;
    public static final SectionType<TypeId> TYPE_ID;
    public static final SectionType<TypeList> TYPE_LIST;
    public static final SectionType<?>[] VALUES;
    private final Creator<T> creator;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f249name;
    private final int type;

    public Section<T> createSection(IntegerPair integerPair) {
        return null;
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    public int getReferenceType() {
        return 7;
    }

    public boolean isDataSection() {
        return false;
    }

    public boolean isIdSection() {
        return false;
    }

    public boolean isSpecialSection() {
        return false;
    }

    static {
        VALUES = new SectionType[]{specialSectionType, specialSectionType, stringIdSectionType, stringDataSectionType, idSectionType, dataSectionType, idSectionType, idSectionType, idSectionType, dataSectionType, annotationSetSectionType, dataSectionType, dataSectionType, idSectionType, idSectionType, dataSectionType, dataSectionType, dataSectionType, dataSectionType, idSectionType, dataSectionType};
        SpecialSectionType specialSectionType = new SpecialSectionType("HEADER", 0, new Creator<DexHeader>() { // from class: com.reandroid.dex.sections.SectionType.1
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public DexHeader[] newArrayInstance(int i) {
                return new DexHeader[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public DexHeader newInstance() {
                return new DexHeader();
            }
        });
        HEADER = specialSectionType;
        SpecialSectionType specialSectionType2 = new SpecialSectionType("MAP_LIST", 4096, new Creator<MapList>() { // from class: com.reandroid.dex.sections.SectionType.2
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public MapList[] newArrayInstance(int i) {
                return new MapList[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public MapList newInstance() {
                return new MapList(new NumberIntegerReference());
            }
        });
        MAP_LIST = specialSectionType2;
        StringIdSectionType stringIdSectionType = new StringIdSectionType("STRING_ID", 1, 2, 0, new Creator<StringId>() { // from class: com.reandroid.dex.sections.SectionType.3
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public StringId[] newArrayInstance(int i) {
                return new StringId[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public StringId newInstance() {
                return new StringId();
            }
        });
        STRING_ID = stringIdSectionType;
        StringDataSectionType stringDataSectionType = new StringDataSectionType("STRING_DATA", 8194, new Creator<StringData>() { // from class: com.reandroid.dex.sections.SectionType.4
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public StringData[] newArrayInstance(int i) {
                return new StringData[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public StringData newInstance() {
                return new StringData();
            }
        });
        STRING_DATA = stringDataSectionType;
        IdSectionType idSectionType = new IdSectionType("TYPE_ID", 2, 1, new Creator<TypeId>() { // from class: com.reandroid.dex.sections.SectionType.5
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public TypeId[] newArrayInstance(int i) {
                return new TypeId[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public TypeId newInstance() {
                return new TypeId();
            }
        });
        TYPE_ID = idSectionType;
        DataSectionType dataSectionType = new DataSectionType("TYPE_LIST", 4097, new Creator<TypeList>() { // from class: com.reandroid.dex.sections.SectionType.6
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public TypeList[] newArrayInstance(int i) {
                return new TypeList[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public TypeList newInstance() {
                return new TypeList();
            }
        });
        TYPE_LIST = dataSectionType;
        IdSectionType idSectionType2 = new IdSectionType("PROTO_ID", 3, 4, new Creator<ProtoId>() { // from class: com.reandroid.dex.sections.SectionType.7
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public ProtoId[] newArrayInstance(int i) {
                return new ProtoId[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public ProtoId newInstance() {
                return new ProtoId();
            }
        });
        PROTO_ID = idSectionType2;
        IdSectionType idSectionType3 = new IdSectionType("FIELD_ID", 4, 2, new Creator<FieldId>() { // from class: com.reandroid.dex.sections.SectionType.8
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public FieldId[] newArrayInstance(int i) {
                return new FieldId[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public FieldId newInstance() {
                return new FieldId();
            }
        });
        FIELD_ID = idSectionType3;
        IdSectionType idSectionType4 = new IdSectionType("METHOD_ID", 5, 3, new Creator<MethodId>() { // from class: com.reandroid.dex.sections.SectionType.9
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public MethodId[] newArrayInstance(int i) {
                return new MethodId[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public MethodId newInstance() {
                return new MethodId();
            }
        });
        METHOD_ID = idSectionType4;
        DataSectionType dataSectionType2 = new DataSectionType("ANNOTATION_ITEM", 8196, new Creator<AnnotationItem>() { // from class: com.reandroid.dex.sections.SectionType.10
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public AnnotationItem[] newArrayInstance(int i) {
                return new AnnotationItem[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public AnnotationItem newInstance() {
                return new AnnotationItem();
            }
        });
        ANNOTATION_ITEM = dataSectionType2;
        AnnotationSetSectionType annotationSetSectionType = new AnnotationSetSectionType("ANNOTATION_SET", 4099, new Creator<AnnotationSet>() { // from class: com.reandroid.dex.sections.SectionType.11
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public AnnotationSet[] newArrayInstance(int i) {
                return new AnnotationSet[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public AnnotationSet newInstance() {
                return new AnnotationSet();
            }
        });
        ANNOTATION_SET = annotationSetSectionType;
        DataSectionType dataSectionType3 = new DataSectionType("ANNOTATION_GROUP", 4098, new Creator<AnnotationGroup>() { // from class: com.reandroid.dex.sections.SectionType.12
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public AnnotationGroup[] newArrayInstance(int i) {
                return new AnnotationGroup[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public AnnotationGroup newInstance() {
                return new AnnotationGroup();
            }
        });
        ANNOTATION_GROUP = dataSectionType3;
        DataSectionType dataSectionType4 = new DataSectionType("ANNOTATIONS_DIRECTORY", ItemType.ANNOTATION_DIRECTORY_ITEM, new Creator<AnnotationsDirectory>() { // from class: com.reandroid.dex.sections.SectionType.13
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public AnnotationsDirectory[] newArrayInstance(int i) {
                return new AnnotationsDirectory[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public AnnotationsDirectory newInstance() {
                return new AnnotationsDirectory();
            }
        });
        ANNOTATION_DIRECTORY = dataSectionType4;
        IdSectionType idSectionType5 = new IdSectionType("CALL_SITE_ID", 7, 5, new Creator<CallSiteId>() { // from class: com.reandroid.dex.sections.SectionType.14
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public CallSiteId[] newArrayInstance(int i) {
                return new CallSiteId[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public CallSiteId newInstance() {
                return new CallSiteId();
            }
        });
        CALL_SITE_ID = idSectionType5;
        IdSectionType idSectionType6 = new IdSectionType("METHOD_HANDLE", 8, 6, new Creator<MethodHandle>() { // from class: com.reandroid.dex.sections.SectionType.15
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public MethodHandle[] newArrayInstance(int i) {
                return new MethodHandle[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public MethodHandle newInstance() {
                return new MethodHandle();
            }
        });
        METHOD_HANDLE = idSectionType6;
        DataSectionType dataSectionType5 = new DataSectionType("DEBUG_INFO", ItemType.DEBUG_INFO_ITEM, new Creator<DebugInfo>() { // from class: com.reandroid.dex.sections.SectionType.16
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public DebugInfo[] newArrayInstance(int i) {
                return new DebugInfo[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public DebugInfo newInstance() {
                return new DebugInfo();
            }
        });
        DEBUG_INFO = dataSectionType5;
        DataSectionType dataSectionType6 = new DataSectionType("CODE", ItemType.CODE_ITEM, new Creator<CodeItem>() { // from class: com.reandroid.dex.sections.SectionType.17
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public CodeItem[] newArrayInstance(int i) {
                return new CodeItem[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public CodeItem newInstance() {
                return new CodeItem();
            }
        });
        CODE = dataSectionType6;
        DataSectionType dataSectionType7 = new DataSectionType("ENCODED_ARRAY", 8197, new Creator<EncodedArray>() { // from class: com.reandroid.dex.sections.SectionType.18
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public EncodedArray[] newArrayInstance(int i) {
                return new EncodedArray[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public EncodedArray newInstance() {
                return new EncodedArray();
            }
        });
        ENCODED_ARRAY = dataSectionType7;
        DataSectionType dataSectionType8 = new DataSectionType("CLASS_DATA", 8192, new Creator<ClassData>() { // from class: com.reandroid.dex.sections.SectionType.19
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public ClassData[] newArrayInstance(int i) {
                return new ClassData[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public ClassData newInstance() {
                return new ClassData();
            }
        });
        CLASS_DATA = dataSectionType8;
        IdSectionType idSectionType7 = new IdSectionType("CLASS_ID", 6, 7, new Creator<ClassId>() { // from class: com.reandroid.dex.sections.SectionType.20
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public ClassId[] newArrayInstance(int i) {
                return new ClassId[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public ClassId newInstance() {
                return new ClassId();
            }
        });
        CLASS_ID = idSectionType7;
        DataSectionType dataSectionType9 = new DataSectionType("HIDDEN_API", 61440, new Creator<HiddenApiRestrictions>() { // from class: com.reandroid.dex.sections.SectionType.21
            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
            public HiddenApiRestrictions[] newArrayInstance(int i) {
                return new HiddenApiRestrictions[i];
            }

            @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
            public HiddenApiRestrictions newInstance() {
                return new HiddenApiRestrictions();
            }
        });
        HIDDEN_API = dataSectionType9;
        READ_ORDER = new SectionType[]{specialSectionType, specialSectionType2, stringIdSectionType, stringDataSectionType, idSectionType, dataSectionType, idSectionType2, idSectionType3, idSectionType4, idSectionType6, dataSectionType2, annotationSetSectionType, dataSectionType3, dataSectionType4, dataSectionType7, idSectionType5, dataSectionType5, dataSectionType6, dataSectionType8, idSectionType7, dataSectionType9};
        DATA_REMOVE_ORDER = new SectionType[]{dataSectionType8, dataSectionType6, dataSectionType5, dataSectionType4, dataSectionType3, annotationSetSectionType, dataSectionType2, dataSectionType7, idSectionType4, idSectionType3, idSectionType2, dataSectionType, idSectionType, stringIdSectionType};
        ID_TYPES = new SectionType[]{stringIdSectionType, idSectionType, idSectionType2, idSectionType3, idSectionType4, idSectionType7, idSectionType5, idSectionType6};
        R8_ORDER = new SectionType[]{specialSectionType, stringIdSectionType, idSectionType, idSectionType2, idSectionType3, idSectionType4, idSectionType7, idSectionType5, idSectionType6, dataSectionType6, dataSectionType5, dataSectionType, stringDataSectionType, dataSectionType2, dataSectionType8, dataSectionType7, annotationSetSectionType, dataSectionType3, dataSectionType4, specialSectionType2};
        SORT_SECTIONS_ORDER = new SectionType[]{stringDataSectionType, stringIdSectionType, idSectionType, dataSectionType, idSectionType2, idSectionType3, idSectionType4, idSectionType6, dataSectionType2, annotationSetSectionType, dataSectionType3, dataSectionType4, dataSectionType7, idSectionType5, dataSectionType5, dataSectionType6, dataSectionType8, idSectionType7, dataSectionType9, specialSectionType2, specialSectionType};
        DEX_LIB2_ORDER = new SectionType[]{specialSectionType, stringIdSectionType, idSectionType, idSectionType2, idSectionType3, idSectionType4, idSectionType7, stringDataSectionType, dataSectionType, dataSectionType7, dataSectionType2, annotationSetSectionType, dataSectionType3, dataSectionType4, dataSectionType5, dataSectionType6, dataSectionType8, specialSectionType2};
    }

    private SectionType(String str, int i, Creator<T> creator) {
        this.f249name = str;
        this.type = i;
        this.creator = creator;
    }

    public String getName() {
        return this.f249name;
    }

    public int getType() {
        return this.type;
    }

    public Section<T> createSpecialSection(IntegerReference integerReference) {
        throw new RuntimeException("Not implemented for: " + getName());
    }

    public int hashCode() {
        return this.type;
    }

    public String toString() {
        return getName();
    }

    public Creator<T> getCreator() {
        return this.creator;
    }

    public static <T1 extends SectionItem> SectionType<T1> get(int i) {
        for (Object obj : VALUES) {
            SectionType<T1> sectionType = (SectionType<T1>) obj;
            if (i == ((SectionType) sectionType).type) {
                return sectionType;
            }
        }
        return null;
    }

    public static SectionType<? extends IdItem> getReferenceType(int i) {
        switch (i) {
            case 0:
                return STRING_ID;
            case 1:
                return TYPE_ID;
            case 2:
                return FIELD_ID;
            case 3:
                return METHOD_ID;
            case 4:
                return PROTO_ID;
            case 5:
                return CALL_SITE_ID;
            case 6:
                return METHOD_HANDLE;
            default:
                return null;
        }
    }

    public static Iterator<SectionType<?>> getIdSectionTypes() {
        return new ArrayIterator(ID_TYPES);
    }

    public static <T1> Comparator<T1> comparator(SectionType<?>[] sectionTypeArr, Function<? super T1, SectionType<?>> function) {
        return new OrderBasedComparator(sectionTypeArr, function);
    }

    public static SectionType<?>[] getR8Order() {
        return (SectionType[]) R8_ORDER.clone();
    }

    public static SectionType<?>[] getDexLib2Order() {
        return (SectionType[]) DEX_LIB2_ORDER.clone();
    }

    public static SectionType<?>[] getReadOrderList() {
        return (SectionType[]) READ_ORDER.clone();
    }

    public static SectionType<?>[] getRemoveOrderList() {
        return (SectionType[]) DATA_REMOVE_ORDER.clone();
    }

    public static SectionType<?>[] getSortSectionsOrder() {
        return (SectionType[]) SORT_SECTIONS_ORDER.clone();
    }

    private static class SpecialSectionType<T1 extends SpecialItem> extends SectionType<T1> {
        @Override // com.reandroid.dex.sections.SectionType
        public boolean isSpecialSection() {
            return true;
        }

        SpecialSectionType(String str, int i, Creator<T1> creator) {
            super(str, i, creator);
        }

        @Override // com.reandroid.dex.sections.SectionType
        public Section<T1> createSection(IntegerPair integerPair) {
            return new SpecialSection(integerPair, this);
        }

        @Override // com.reandroid.dex.sections.SectionType
        public SpecialSection<T1> createSpecialSection(IntegerReference integerReference) {
            return new SpecialSection<>(integerReference, this);
        }
    }

    private static class IdSectionType<T1 extends IdItem> extends SectionType<T1> {
        private final int referenceType;

        @Override // com.reandroid.dex.sections.SectionType
        public boolean isIdSection() {
            return true;
        }

        private IdSectionType(String str, int i, int i2, Creator<T1> creator) {
            super(str, i, creator);
            this.referenceType = i2;
        }

        @Override // com.reandroid.dex.sections.SectionType
        public Section<T1> createSection(IntegerPair integerPair) {
            if (this == SectionType.CLASS_ID) {
                return new ClassIdSection(integerPair);
            }
            return new IdSection(integerPair, this);
        }

        @Override // com.reandroid.dex.sections.SectionType
        public int getReferenceType() {
            return this.referenceType;
        }
    }

    private static class StringIdSectionType extends IdSectionType<StringId> {
        StringIdSectionType(String str, int i, int i2, int i3, Creator<StringId> creator) {
            super(str, i, i3, creator);
        }

        @Override // com.reandroid.dex.sections.SectionType.IdSectionType, com.reandroid.dex.sections.SectionType
        public StringIdSection createSection(IntegerPair integerPair) {
            return new StringIdSection(integerPair, this);
        }
    }

    private static class DataSectionType<T1 extends DataItem> extends SectionType<T1> {
        @Override // com.reandroid.dex.sections.SectionType
        public boolean isDataSection() {
            return true;
        }

        DataSectionType(String str, int i, Creator<T1> creator) {
            super(str, i, creator);
        }

        @Override // com.reandroid.dex.sections.SectionType
        public Section<T1> createSection(IntegerPair integerPair) {
            return new DataSection(integerPair, this);
        }
    }

    private static class StringDataSectionType extends DataSectionType<StringData> {
        StringDataSectionType(String str, int i, Creator<StringData> creator) {
            super(str, i, creator);
        }

        @Override // com.reandroid.dex.sections.SectionType.DataSectionType, com.reandroid.dex.sections.SectionType
        public StringDataSection createSection(IntegerPair integerPair) {
            return new StringDataSection(integerPair, this);
        }
    }

    private static class AnnotationSetSectionType extends DataSectionType<AnnotationSet> {
        AnnotationSetSectionType(String str, int i, Creator<AnnotationSet> creator) {
            super(str, i, creator);
        }

        @Override // com.reandroid.dex.sections.SectionType.DataSectionType, com.reandroid.dex.sections.SectionType
        public AnnotationSetSection createSection(IntegerPair integerPair) {
            return new AnnotationSetSection(integerPair, this);
        }
    }

    static class OrderBasedComparator<T1> implements Comparator<T1> {
        private final Function<? super T1, SectionType<?>> function;
        private final SectionType<?>[] sortOrder;

        public OrderBasedComparator(SectionType<?>[] sectionTypeArr, Function<? super T1, SectionType<?>> function) {
            this.sortOrder = sectionTypeArr;
            this.function = function;
        }

        private int getOrder(SectionType<?> sectionType) {
            SectionType<?>[] sectionTypeArr = this.sortOrder;
            int length = sectionTypeArr.length;
            for (int i = 0; i < length; i++) {
                if (sectionTypeArr[i] == sectionType) {
                    return i;
                }
            }
            return length - 2;
        }

        private int getOrder(T1 t1) {
            if (t1 == null) {
                return this.sortOrder.length - 1;
            }
            return getOrder(this.function.apply(t1));
        }

        @Override // java.util.Comparator
        public int compare(T1 t1, T1 t2) {
            return Integer.compare(getOrder(t1), getOrder(t2));
        }
    }
}
