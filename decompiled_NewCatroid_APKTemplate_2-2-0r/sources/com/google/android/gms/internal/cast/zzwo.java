package com.google.android.gms.internal.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzwo {
    DOUBLE(zzwp.DOUBLE, 1),
    FLOAT(zzwp.FLOAT, 5),
    INT64(zzwp.LONG, 0),
    UINT64(zzwp.LONG, 0),
    INT32(zzwp.INT, 0),
    FIXED64(zzwp.LONG, 1),
    FIXED32(zzwp.INT, 5),
    BOOL(zzwp.BOOLEAN, 0),
    STRING(zzwp.STRING, 2),
    GROUP(zzwp.MESSAGE, 3),
    MESSAGE(zzwp.MESSAGE, 2),
    BYTES(zzwp.BYTE_STRING, 2),
    UINT32(zzwp.INT, 0),
    ENUM(zzwp.ENUM, 0),
    SFIXED32(zzwp.INT, 5),
    SFIXED64(zzwp.LONG, 1),
    SINT32(zzwp.INT, 0),
    SINT64(zzwp.LONG, 0);

    private final zzwp zzt;

    zzwo(zzwp zzwpVar, int i) {
        this.zzt = zzwpVar;
    }

    public final zzwp zza() {
        return this.zzt;
    }
}
