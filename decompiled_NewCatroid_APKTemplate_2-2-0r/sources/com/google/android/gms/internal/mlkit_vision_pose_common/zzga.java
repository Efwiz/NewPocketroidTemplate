package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.lang.reflect.Type;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzga {
    DOUBLE(0, zzgc.SCALAR, zzgn.DOUBLE),
    FLOAT(1, zzgc.SCALAR, zzgn.FLOAT),
    INT64(2, zzgc.SCALAR, zzgn.LONG),
    UINT64(3, zzgc.SCALAR, zzgn.LONG),
    INT32(4, zzgc.SCALAR, zzgn.INT),
    FIXED64(5, zzgc.SCALAR, zzgn.LONG),
    FIXED32(6, zzgc.SCALAR, zzgn.INT),
    BOOL(7, zzgc.SCALAR, zzgn.BOOLEAN),
    STRING(8, zzgc.SCALAR, zzgn.STRING),
    MESSAGE(9, zzgc.SCALAR, zzgn.MESSAGE),
    BYTES(10, zzgc.SCALAR, zzgn.BYTE_STRING),
    UINT32(11, zzgc.SCALAR, zzgn.INT),
    ENUM(12, zzgc.SCALAR, zzgn.ENUM),
    SFIXED32(13, zzgc.SCALAR, zzgn.INT),
    SFIXED64(14, zzgc.SCALAR, zzgn.LONG),
    SINT32(15, zzgc.SCALAR, zzgn.INT),
    SINT64(16, zzgc.SCALAR, zzgn.LONG),
    GROUP(17, zzgc.SCALAR, zzgn.MESSAGE),
    DOUBLE_LIST(18, zzgc.VECTOR, zzgn.DOUBLE),
    FLOAT_LIST(19, zzgc.VECTOR, zzgn.FLOAT),
    INT64_LIST(20, zzgc.VECTOR, zzgn.LONG),
    UINT64_LIST(21, zzgc.VECTOR, zzgn.LONG),
    INT32_LIST(22, zzgc.VECTOR, zzgn.INT),
    FIXED64_LIST(23, zzgc.VECTOR, zzgn.LONG),
    FIXED32_LIST(24, zzgc.VECTOR, zzgn.INT),
    BOOL_LIST(25, zzgc.VECTOR, zzgn.BOOLEAN),
    STRING_LIST(26, zzgc.VECTOR, zzgn.STRING),
    MESSAGE_LIST(27, zzgc.VECTOR, zzgn.MESSAGE),
    BYTES_LIST(28, zzgc.VECTOR, zzgn.BYTE_STRING),
    UINT32_LIST(29, zzgc.VECTOR, zzgn.INT),
    ENUM_LIST(30, zzgc.VECTOR, zzgn.ENUM),
    SFIXED32_LIST(31, zzgc.VECTOR, zzgn.INT),
    SFIXED64_LIST(32, zzgc.VECTOR, zzgn.LONG),
    SINT32_LIST(33, zzgc.VECTOR, zzgn.INT),
    SINT64_LIST(34, zzgc.VECTOR, zzgn.LONG),
    DOUBLE_LIST_PACKED(35, zzgc.PACKED_VECTOR, zzgn.DOUBLE),
    FLOAT_LIST_PACKED(36, zzgc.PACKED_VECTOR, zzgn.FLOAT),
    INT64_LIST_PACKED(37, zzgc.PACKED_VECTOR, zzgn.LONG),
    UINT64_LIST_PACKED(38, zzgc.PACKED_VECTOR, zzgn.LONG),
    INT32_LIST_PACKED(39, zzgc.PACKED_VECTOR, zzgn.INT),
    FIXED64_LIST_PACKED(40, zzgc.PACKED_VECTOR, zzgn.LONG),
    FIXED32_LIST_PACKED(41, zzgc.PACKED_VECTOR, zzgn.INT),
    BOOL_LIST_PACKED(42, zzgc.PACKED_VECTOR, zzgn.BOOLEAN),
    UINT32_LIST_PACKED(43, zzgc.PACKED_VECTOR, zzgn.INT),
    ENUM_LIST_PACKED(44, zzgc.PACKED_VECTOR, zzgn.ENUM),
    SFIXED32_LIST_PACKED(45, zzgc.PACKED_VECTOR, zzgn.INT),
    SFIXED64_LIST_PACKED(46, zzgc.PACKED_VECTOR, zzgn.LONG),
    SINT32_LIST_PACKED(47, zzgc.PACKED_VECTOR, zzgn.INT),
    SINT64_LIST_PACKED(48, zzgc.PACKED_VECTOR, zzgn.LONG),
    GROUP_LIST(49, zzgc.VECTOR, zzgn.MESSAGE),
    MAP(50, zzgc.MAP, zzgn.VOID);

    private static final zzga[] zzbe;
    private static final Type[] zzbf = new Type[0];
    private final zzgn zzaz;
    private final int zzba;
    private final zzgc zzbb;
    private final Class<?> zzbc;
    private final boolean zzbd;

    zzga(int i, zzgc zzgcVar, zzgn zzgnVar) {
        int i2;
        this.zzba = i;
        this.zzbb = zzgcVar;
        this.zzaz = zzgnVar;
        int i3 = zzfz.zza[zzgcVar.ordinal()];
        if (i3 == 1 || i3 == 2) {
            this.zzbc = zzgnVar.zza();
        } else {
            this.zzbc = null;
        }
        this.zzbd = (zzgcVar != zzgc.SCALAR || (i2 = zzfz.zzb[zzgnVar.ordinal()]) == 1 || i2 == 2 || i2 == 3) ? false : true;
    }

    public final int zza() {
        return this.zzba;
    }

    static {
        zzga[] zzgaVarArrValues = values();
        zzbe = new zzga[zzgaVarArrValues.length];
        for (zzga zzgaVar : zzgaVarArrValues) {
            zzbe[zzgaVar.zzba] = zzgaVar;
        }
    }
}
