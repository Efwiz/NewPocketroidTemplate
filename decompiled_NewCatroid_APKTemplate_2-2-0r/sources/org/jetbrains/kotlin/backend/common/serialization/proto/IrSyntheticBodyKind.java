package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.Internal;

/* JADX INFO: loaded from: classes2.dex */
public enum IrSyntheticBodyKind implements Internal.EnumLite {
    ENUM_VALUES(0, 1),
    ENUM_VALUEOF(1, 2),
    ENUM_ENTRIES(2, 3);

    public static final int ENUM_ENTRIES_VALUE = 3;
    public static final int ENUM_VALUEOF_VALUE = 2;
    public static final int ENUM_VALUES_VALUE = 1;
    private static Internal.EnumLiteMap<IrSyntheticBodyKind> internalValueMap = new Internal.EnumLiteMap<IrSyntheticBodyKind>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBodyKind.1
        @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLiteMap
        public IrSyntheticBodyKind findValueByNumber(int i) {
            return IrSyntheticBodyKind.valueOf(i);
        }
    };
    private final int value;

    @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
    public final int getNumber() {
        return this.value;
    }

    public static IrSyntheticBodyKind valueOf(int i) {
        if (i == 1) {
            return ENUM_VALUES;
        }
        if (i == 2) {
            return ENUM_VALUEOF;
        }
        if (i != 3) {
            return null;
        }
        return ENUM_ENTRIES;
    }

    public static Internal.EnumLiteMap<IrSyntheticBodyKind> internalGetValueMap() {
        return internalValueMap;
    }

    IrSyntheticBodyKind(int i, int i2) {
        this.value = i2;
    }
}
