package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzarj {
    DOUBLE(zzark.DOUBLE, 1),
    FLOAT(zzark.FLOAT, 5),
    INT64(zzark.LONG, 0),
    UINT64(zzark.LONG, 0),
    INT32(zzark.INT, 0),
    FIXED64(zzark.LONG, 1),
    FIXED32(zzark.INT, 5),
    BOOL(zzark.BOOLEAN, 0),
    STRING(zzark.STRING, 2),
    GROUP(zzark.MESSAGE, 3),
    MESSAGE(zzark.MESSAGE, 2),
    BYTES(zzark.BYTE_STRING, 2),
    UINT32(zzark.INT, 0),
    ENUM(zzark.ENUM, 0),
    SFIXED32(zzark.INT, 5),
    SFIXED64(zzark.LONG, 1),
    SINT32(zzark.INT, 0),
    SINT64(zzark.LONG, 0);

    private final zzark zzt;
    private final int zzu;

    zzarj(zzark zzarkVar, int i) {
        this.zzt = zzarkVar;
        this.zzu = i;
    }

    public final int zza() {
        return this.zzu;
    }

    public final zzark zzb() {
        return this.zzt;
    }
}
