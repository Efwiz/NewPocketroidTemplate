package com.google.android.gms.internal.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zztk {
    DOUBLE(0, 1, zzub.DOUBLE),
    FLOAT(1, 1, zzub.FLOAT),
    INT64(2, 1, zzub.LONG),
    UINT64(3, 1, zzub.LONG),
    INT32(4, 1, zzub.INT),
    FIXED64(5, 1, zzub.LONG),
    FIXED32(6, 1, zzub.INT),
    BOOL(7, 1, zzub.BOOLEAN),
    STRING(8, 1, zzub.STRING),
    MESSAGE(9, 1, zzub.MESSAGE),
    BYTES(10, 1, zzub.BYTE_STRING),
    UINT32(11, 1, zzub.INT),
    ENUM(12, 1, zzub.ENUM),
    SFIXED32(13, 1, zzub.INT),
    SFIXED64(14, 1, zzub.LONG),
    SINT32(15, 1, zzub.INT),
    SINT64(16, 1, zzub.LONG),
    GROUP(17, 1, zzub.MESSAGE),
    DOUBLE_LIST(18, 2, zzub.DOUBLE),
    FLOAT_LIST(19, 2, zzub.FLOAT),
    INT64_LIST(20, 2, zzub.LONG),
    UINT64_LIST(21, 2, zzub.LONG),
    INT32_LIST(22, 2, zzub.INT),
    FIXED64_LIST(23, 2, zzub.LONG),
    FIXED32_LIST(24, 2, zzub.INT),
    BOOL_LIST(25, 2, zzub.BOOLEAN),
    STRING_LIST(26, 2, zzub.STRING),
    MESSAGE_LIST(27, 2, zzub.MESSAGE),
    BYTES_LIST(28, 2, zzub.BYTE_STRING),
    UINT32_LIST(29, 2, zzub.INT),
    ENUM_LIST(30, 2, zzub.ENUM),
    SFIXED32_LIST(31, 2, zzub.INT),
    SFIXED64_LIST(32, 2, zzub.LONG),
    SINT32_LIST(33, 2, zzub.INT),
    SINT64_LIST(34, 2, zzub.LONG),
    DOUBLE_LIST_PACKED(35, 3, zzub.DOUBLE),
    FLOAT_LIST_PACKED(36, 3, zzub.FLOAT),
    INT64_LIST_PACKED(37, 3, zzub.LONG),
    UINT64_LIST_PACKED(38, 3, zzub.LONG),
    INT32_LIST_PACKED(39, 3, zzub.INT),
    FIXED64_LIST_PACKED(40, 3, zzub.LONG),
    FIXED32_LIST_PACKED(41, 3, zzub.INT),
    BOOL_LIST_PACKED(42, 3, zzub.BOOLEAN),
    UINT32_LIST_PACKED(43, 3, zzub.INT),
    ENUM_LIST_PACKED(44, 3, zzub.ENUM),
    SFIXED32_LIST_PACKED(45, 3, zzub.INT),
    SFIXED64_LIST_PACKED(46, 3, zzub.LONG),
    SINT32_LIST_PACKED(47, 3, zzub.INT),
    SINT64_LIST_PACKED(48, 3, zzub.LONG),
    GROUP_LIST(49, 2, zzub.MESSAGE),
    MAP(50, 4, zzub.VOID);

    private static final zztk[] zzZ;
    private final zzub zzab;
    private final int zzac;
    private final Class zzad;

    static {
        zztk[] zztkVarArrValues = values();
        zzZ = new zztk[zztkVarArrValues.length];
        for (zztk zztkVar : zztkVarArrValues) {
            zzZ[zztkVar.zzac] = zztkVar;
        }
    }

    zztk(int i, int i2, zzub zzubVar) {
        this.zzac = i;
        this.zzab = zzubVar;
        int i3 = i2 - 1;
        if (i3 == 1 || i3 == 3) {
            this.zzad = zzubVar.zza();
        } else {
            this.zzad = null;
        }
        if (i2 == 1) {
            zzub zzubVar2 = zzub.VOID;
            zzubVar.ordinal();
        }
    }

    public final int zza() {
        return this.zzac;
    }
}
