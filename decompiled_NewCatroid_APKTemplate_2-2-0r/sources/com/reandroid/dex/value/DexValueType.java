package com.reandroid.dex.value;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockCreator;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.utils.HexUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class DexValueType<T extends DexValueBlock<?>> implements BlockCreator<T> {
    public static final DexValueType<AnnotationValue> ANNOTATION;
    public static final DexValueType<ArrayValue> ARRAY;
    public static final DexValueType<BooleanValue> BOOLEAN;
    public static final DexValueType<ByteValue> BYTE;
    public static final DexValueType<CharValue> CHAR;
    public static final DexValueType<DoubleValue> DOUBLE;
    public static final DexValueType<EnumValue> ENUM;
    public static final DexValueType<FieldIdValue> FIELD;
    public static final DexValueType<FloatValue> FLOAT;
    public static final DexValueType<IntValue> INT;
    public static final DexValueType<LongValue> LONG;
    public static final DexValueType<MethodIdValue> METHOD;
    public static final DexValueType<MethodHandleValue> METHOD_HANDLE;
    public static final DexValueType<NullValue> NULL;
    public static final DexValueType<ProtoValue> PROTO;
    public static final DexValueType<ShortValue> SHORT;
    public static final DexValueType<StringValue> STRING;
    public static final DexValueType<TypeValue> TYPE;
    private static final DexValueType<?>[] VALUES;
    private static final DexValueType<?>[] VALUES_COPY;
    private final BlockCreator<T> creator;
    private final int flag;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f256name;
    private final int size;
    private final int type;

    public static int decodeSize(int i) {
        return i >>> 5;
    }

    public boolean equals(Object obj) {
        return obj == this;
    }

    static {
        DexValueType<?>[] dexValueTypeArr = new DexValueType[32];
        VALUES = dexValueTypeArr;
        DexValueType<ByteValue> dexValueType = new DexValueType<>("BYTE", 0, 0, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda0
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new ByteValue();
            }
        });
        BYTE = dexValueType;
        dexValueTypeArr[0] = dexValueType;
        DexValueType<ShortValue> dexValueType2 = new DexValueType<>("SHORT", 2, 1, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda17
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new ShortValue();
            }
        });
        SHORT = dexValueType2;
        dexValueTypeArr[2] = dexValueType2;
        DexValueType<CharValue> dexValueType3 = new DexValueType<>("CHAR", 3, 1, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda1
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new CharValue();
            }
        });
        CHAR = dexValueType3;
        dexValueTypeArr[3] = dexValueType3;
        DexValueType<IntValue> dexValueType4 = new DexValueType<>("INT", 4, 3, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda2
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new IntValue();
            }
        });
        INT = dexValueType4;
        dexValueTypeArr[4] = dexValueType4;
        DexValueType<LongValue> dexValueType5 = new DexValueType<>("LONG", 6, 7, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda3
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new LongValue();
            }
        });
        LONG = dexValueType5;
        dexValueTypeArr[6] = dexValueType5;
        DexValueType<FloatValue> dexValueType6 = new DexValueType<>("FLOAT", 16, 3, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda4
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new FloatValue();
            }
        });
        FLOAT = dexValueType6;
        dexValueTypeArr[16] = dexValueType6;
        DexValueType<DoubleValue> dexValueType7 = new DexValueType<>("DOUBLE", 17, 7, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda5
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new DoubleValue();
            }
        });
        DOUBLE = dexValueType7;
        dexValueTypeArr[17] = dexValueType7;
        DexValueType<ProtoValue> dexValueType8 = new DexValueType<>("PROTO", 21, 3, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda6
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new ProtoValue();
            }
        });
        PROTO = dexValueType8;
        dexValueTypeArr[21] = dexValueType8;
        DexValueType<MethodHandleValue> dexValueType9 = new DexValueType<>("METHOD_HANDLE", 22, 3, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda7
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new MethodHandleValue();
            }
        });
        METHOD_HANDLE = dexValueType9;
        dexValueTypeArr[22] = dexValueType9;
        DexValueType<StringValue> dexValueType10 = new DexValueType<>("STRING", 23, 3, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda8
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new StringValue();
            }
        });
        STRING = dexValueType10;
        dexValueTypeArr[23] = dexValueType10;
        DexValueType<TypeValue> dexValueType11 = new DexValueType<>("TYPE", 24, 3, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda9
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new TypeValue();
            }
        });
        TYPE = dexValueType11;
        dexValueTypeArr[24] = dexValueType11;
        DexValueType<FieldIdValue> dexValueType12 = new DexValueType<>("FIELD", 25, 3, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda10
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new FieldIdValue();
            }
        });
        FIELD = dexValueType12;
        dexValueTypeArr[25] = dexValueType12;
        DexValueType<MethodIdValue> dexValueType13 = new DexValueType<>("METHOD", 26, 3, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda11
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new MethodIdValue();
            }
        });
        METHOD = dexValueType13;
        dexValueTypeArr[26] = dexValueType13;
        DexValueType<EnumValue> dexValueType14 = new DexValueType<>("ENUM", 27, 3, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda12
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new EnumValue();
            }
        });
        ENUM = dexValueType14;
        dexValueTypeArr[27] = dexValueType14;
        DexValueType<ArrayValue> dexValueType15 = new DexValueType<>("ARRAY", 28, 0, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda13
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new ArrayValue();
            }
        });
        ARRAY = dexValueType15;
        dexValueTypeArr[28] = dexValueType15;
        DexValueType<AnnotationValue> dexValueType16 = new DexValueType<>("ANNOTATION", 29, 0, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda14
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new AnnotationValue();
            }
        });
        ANNOTATION = dexValueType16;
        dexValueTypeArr[29] = dexValueType16;
        DexValueType<NullValue> dexValueType17 = new DexValueType<>("NULL", 30, 0, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda15
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new NullValue();
            }
        });
        NULL = dexValueType17;
        dexValueTypeArr[30] = dexValueType17;
        DexValueType<BooleanValue> dexValueType18 = new DexValueType<>("BOOLEAN", 31, 1, new BlockCreator() { // from class: com.reandroid.dex.value.DexValueType$$ExternalSyntheticLambda16
            @Override // com.reandroid.arsc.base.BlockCreator
            public final Block newInstance() {
                return new BooleanValue();
            }
        });
        BOOLEAN = dexValueType18;
        dexValueTypeArr[31] = dexValueType18;
        int i = 0;
        for (int i2 = 0; i2 < 32; i2++) {
            if (dexValueTypeArr[i2] != null) {
                i++;
            }
        }
        VALUES_COPY = new DexValueType[i];
        int i3 = 0;
        for (int i4 = 0; i4 < 32; i4++) {
            DexValueType<?> dexValueType19 = dexValueTypeArr[i4];
            if (dexValueType19 != null) {
                VALUES_COPY[i3] = dexValueType19;
                i3++;
            }
        }
    }

    private DexValueType(String str, int i, int i2, BlockCreator<T> blockCreator) {
        this.f256name = str;
        this.type = i;
        this.size = i2;
        this.creator = blockCreator;
        this.flag = (i2 << 5) | i;
    }

    public int getSize() {
        return this.size;
    }

    public int getType() {
        return this.type;
    }

    public int getFlag() {
        return this.flag;
    }

    public int getFlag(int i) {
        return (i << 5) | this.type;
    }

    @Override // com.reandroid.arsc.base.BlockCreator
    public T newInstance() {
        return (T) this.creator.newInstance();
    }

    public String toString() {
        return this.f256name;
    }

    public int hashCode() {
        return this.flag;
    }

    public static DexValueType<?> fromFlag(int i) {
        return VALUES[i & 31];
    }

    public static DexValueBlock<?> create(BlockReader blockReader) throws IOException {
        int i = blockReader.read();
        blockReader.offset(-1);
        DexValueType<?> dexValueTypeFromFlag = fromFlag(i);
        if (dexValueTypeFromFlag == null) {
            throw new IOException("Invalid value type: " + HexUtil.toHex2((byte) i) + ", " + blockReader);
        }
        return dexValueTypeFromFlag.newInstance();
    }

    public static DexValueType<?>[] values() {
        return VALUES_COPY;
    }

    public static <T1 extends IdItem> DexValueType<? extends SectionValue<T1>> get(SectionType<T1> sectionType) {
        DexValueType<? extends SectionValue<T1>> dexValueType = sectionType == SectionType.STRING_ID ? STRING : null;
        if (sectionType == SectionType.TYPE_ID) {
            dexValueType = TYPE;
        }
        if (sectionType == SectionType.FIELD_ID) {
            dexValueType = ENUM;
        }
        if (sectionType == SectionType.METHOD_ID) {
            dexValueType = METHOD;
        }
        if (sectionType == SectionType.METHOD_HANDLE) {
            dexValueType = METHOD_HANDLE;
        }
        return dexValueType;
    }
}
