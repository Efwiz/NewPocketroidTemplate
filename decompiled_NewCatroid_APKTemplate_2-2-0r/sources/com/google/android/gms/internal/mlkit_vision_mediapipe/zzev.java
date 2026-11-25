package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.lang.reflect.Type;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzev {
    DOUBLE(0, zzex.SCALAR, zzfk.DOUBLE),
    FLOAT(1, zzex.SCALAR, zzfk.FLOAT),
    INT64(2, zzex.SCALAR, zzfk.LONG),
    UINT64(3, zzex.SCALAR, zzfk.LONG),
    INT32(4, zzex.SCALAR, zzfk.INT),
    FIXED64(5, zzex.SCALAR, zzfk.LONG),
    FIXED32(6, zzex.SCALAR, zzfk.INT),
    BOOL(7, zzex.SCALAR, zzfk.BOOLEAN),
    STRING(8, zzex.SCALAR, zzfk.STRING),
    MESSAGE(9, zzex.SCALAR, zzfk.MESSAGE),
    BYTES(10, zzex.SCALAR, zzfk.BYTE_STRING),
    UINT32(11, zzex.SCALAR, zzfk.INT),
    ENUM(12, zzex.SCALAR, zzfk.ENUM),
    SFIXED32(13, zzex.SCALAR, zzfk.INT),
    SFIXED64(14, zzex.SCALAR, zzfk.LONG),
    SINT32(15, zzex.SCALAR, zzfk.INT),
    SINT64(16, zzex.SCALAR, zzfk.LONG),
    GROUP(17, zzex.SCALAR, zzfk.MESSAGE),
    DOUBLE_LIST(18, zzex.VECTOR, zzfk.DOUBLE),
    FLOAT_LIST(19, zzex.VECTOR, zzfk.FLOAT),
    INT64_LIST(20, zzex.VECTOR, zzfk.LONG),
    UINT64_LIST(21, zzex.VECTOR, zzfk.LONG),
    INT32_LIST(22, zzex.VECTOR, zzfk.INT),
    FIXED64_LIST(23, zzex.VECTOR, zzfk.LONG),
    FIXED32_LIST(24, zzex.VECTOR, zzfk.INT),
    BOOL_LIST(25, zzex.VECTOR, zzfk.BOOLEAN),
    STRING_LIST(26, zzex.VECTOR, zzfk.STRING),
    MESSAGE_LIST(27, zzex.VECTOR, zzfk.MESSAGE),
    BYTES_LIST(28, zzex.VECTOR, zzfk.BYTE_STRING),
    UINT32_LIST(29, zzex.VECTOR, zzfk.INT),
    ENUM_LIST(30, zzex.VECTOR, zzfk.ENUM),
    SFIXED32_LIST(31, zzex.VECTOR, zzfk.INT),
    SFIXED64_LIST(32, zzex.VECTOR, zzfk.LONG),
    SINT32_LIST(33, zzex.VECTOR, zzfk.INT),
    SINT64_LIST(34, zzex.VECTOR, zzfk.LONG),
    DOUBLE_LIST_PACKED(35, zzex.PACKED_VECTOR, zzfk.DOUBLE),
    FLOAT_LIST_PACKED(36, zzex.PACKED_VECTOR, zzfk.FLOAT),
    INT64_LIST_PACKED(37, zzex.PACKED_VECTOR, zzfk.LONG),
    UINT64_LIST_PACKED(38, zzex.PACKED_VECTOR, zzfk.LONG),
    INT32_LIST_PACKED(39, zzex.PACKED_VECTOR, zzfk.INT),
    FIXED64_LIST_PACKED(40, zzex.PACKED_VECTOR, zzfk.LONG),
    FIXED32_LIST_PACKED(41, zzex.PACKED_VECTOR, zzfk.INT),
    BOOL_LIST_PACKED(42, zzex.PACKED_VECTOR, zzfk.BOOLEAN),
    UINT32_LIST_PACKED(43, zzex.PACKED_VECTOR, zzfk.INT),
    ENUM_LIST_PACKED(44, zzex.PACKED_VECTOR, zzfk.ENUM),
    SFIXED32_LIST_PACKED(45, zzex.PACKED_VECTOR, zzfk.INT),
    SFIXED64_LIST_PACKED(46, zzex.PACKED_VECTOR, zzfk.LONG),
    SINT32_LIST_PACKED(47, zzex.PACKED_VECTOR, zzfk.INT),
    SINT64_LIST_PACKED(48, zzex.PACKED_VECTOR, zzfk.LONG),
    GROUP_LIST(49, zzex.VECTOR, zzfk.MESSAGE),
    MAP(50, zzex.MAP, zzfk.VOID);

    private static final zzev[] zzbe;
    private static final Type[] zzbf = new Type[0];
    private final zzfk zzaz;
    private final int zzba;
    private final zzex zzbb;
    private final Class<?> zzbc;
    private final boolean zzbd;

    zzev(int i, zzex zzexVar, zzfk zzfkVar) {
        int i2;
        this.zzba = i;
        this.zzbb = zzexVar;
        this.zzaz = zzfkVar;
        int i3 = zzey.zza[zzexVar.ordinal()];
        if (i3 == 1 || i3 == 2) {
            this.zzbc = zzfkVar.zza();
        } else {
            this.zzbc = null;
        }
        this.zzbd = (zzexVar != zzex.SCALAR || (i2 = zzey.zzb[zzfkVar.ordinal()]) == 1 || i2 == 2 || i2 == 3) ? false : true;
    }

    public final int zza() {
        return this.zzba;
    }

    static {
        zzev[] zzevVarArrValues = values();
        zzbe = new zzev[zzevVarArrValues.length];
        for (zzev zzevVar : zzevVarArrValues) {
            zzbe[zzevVar.zzba] = zzevVar;
        }
    }
}
