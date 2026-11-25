package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzani {
    DOUBLE(0, 1, zzaof.DOUBLE),
    FLOAT(1, 1, zzaof.FLOAT),
    INT64(2, 1, zzaof.LONG),
    UINT64(3, 1, zzaof.LONG),
    INT32(4, 1, zzaof.INT),
    FIXED64(5, 1, zzaof.LONG),
    FIXED32(6, 1, zzaof.INT),
    BOOL(7, 1, zzaof.BOOLEAN),
    STRING(8, 1, zzaof.STRING),
    MESSAGE(9, 1, zzaof.MESSAGE),
    BYTES(10, 1, zzaof.BYTE_STRING),
    UINT32(11, 1, zzaof.INT),
    ENUM(12, 1, zzaof.ENUM),
    SFIXED32(13, 1, zzaof.INT),
    SFIXED64(14, 1, zzaof.LONG),
    SINT32(15, 1, zzaof.INT),
    SINT64(16, 1, zzaof.LONG),
    GROUP(17, 1, zzaof.MESSAGE),
    DOUBLE_LIST(18, 2, zzaof.DOUBLE),
    FLOAT_LIST(19, 2, zzaof.FLOAT),
    INT64_LIST(20, 2, zzaof.LONG),
    UINT64_LIST(21, 2, zzaof.LONG),
    INT32_LIST(22, 2, zzaof.INT),
    FIXED64_LIST(23, 2, zzaof.LONG),
    FIXED32_LIST(24, 2, zzaof.INT),
    BOOL_LIST(25, 2, zzaof.BOOLEAN),
    STRING_LIST(26, 2, zzaof.STRING),
    MESSAGE_LIST(27, 2, zzaof.MESSAGE),
    BYTES_LIST(28, 2, zzaof.BYTE_STRING),
    UINT32_LIST(29, 2, zzaof.INT),
    ENUM_LIST(30, 2, zzaof.ENUM),
    SFIXED32_LIST(31, 2, zzaof.INT),
    SFIXED64_LIST(32, 2, zzaof.LONG),
    SINT32_LIST(33, 2, zzaof.INT),
    SINT64_LIST(34, 2, zzaof.LONG),
    DOUBLE_LIST_PACKED(35, 3, zzaof.DOUBLE),
    FLOAT_LIST_PACKED(36, 3, zzaof.FLOAT),
    INT64_LIST_PACKED(37, 3, zzaof.LONG),
    UINT64_LIST_PACKED(38, 3, zzaof.LONG),
    INT32_LIST_PACKED(39, 3, zzaof.INT),
    FIXED64_LIST_PACKED(40, 3, zzaof.LONG),
    FIXED32_LIST_PACKED(41, 3, zzaof.INT),
    BOOL_LIST_PACKED(42, 3, zzaof.BOOLEAN),
    UINT32_LIST_PACKED(43, 3, zzaof.INT),
    ENUM_LIST_PACKED(44, 3, zzaof.ENUM),
    SFIXED32_LIST_PACKED(45, 3, zzaof.INT),
    SFIXED64_LIST_PACKED(46, 3, zzaof.LONG),
    SINT32_LIST_PACKED(47, 3, zzaof.INT),
    SINT64_LIST_PACKED(48, 3, zzaof.LONG),
    GROUP_LIST(49, 2, zzaof.MESSAGE),
    MAP(50, 4, zzaof.VOID);

    private static final zzani[] zzZ;
    private final zzaof zzab;
    private final int zzac;
    private final Class<?> zzad;

    static {
        zzani[] zzaniVarArrValues = values();
        zzZ = new zzani[zzaniVarArrValues.length];
        for (zzani zzaniVar : zzaniVarArrValues) {
            zzZ[zzaniVar.zzac] = zzaniVar;
        }
    }

    zzani(int i, int i2, zzaof zzaofVar) {
        this.zzac = i;
        this.zzab = zzaofVar;
        zzaof zzaofVar2 = zzaof.VOID;
        int i3 = i2 - 1;
        if (i3 == 1 || i3 == 3) {
            this.zzad = zzaofVar.zza();
        } else {
            this.zzad = null;
        }
        if (i2 == 1) {
            zzaofVar.ordinal();
        }
    }

    public final int zza() {
        return this.zzac;
    }
}
