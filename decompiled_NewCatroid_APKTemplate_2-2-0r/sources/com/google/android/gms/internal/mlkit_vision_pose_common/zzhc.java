package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhc implements zzie {
    private static final zzhm zzb = new zzhb();
    private final zzhm zza;

    public zzhc() {
        this(new zzhe(zzgb.zza(), zza()));
    }

    private zzhc(zzhm zzhmVar) {
        this.zza = (zzhm) zzgg.zza(zzhmVar, "messageInfoFactory");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzie
    public final <T> zzib<T> zza(Class<T> cls) {
        zzid.zza((Class<?>) cls);
        zzhj zzhjVarZzb = this.zza.zzb(cls);
        if (zzhjVarZzb.zzb()) {
            if (zzgd.class.isAssignableFrom(cls)) {
                return zzhs.zza(zzid.zzc(), zzfw.zza(), zzhjVarZzb.zzc());
            }
            return zzhs.zza(zzid.zza(), zzfw.zzb(), zzhjVarZzb.zzc());
        }
        if (zzgd.class.isAssignableFrom(cls)) {
            if (zza(zzhjVarZzb)) {
                return zzhp.zza(cls, zzhjVarZzb, zzhw.zzb(), zzgv.zzb(), zzid.zzc(), zzfw.zza(), zzhk.zzb());
            }
            return zzhp.zza(cls, zzhjVarZzb, zzhw.zzb(), zzgv.zzb(), zzid.zzc(), null, zzhk.zzb());
        }
        if (zza(zzhjVarZzb)) {
            return zzhp.zza(cls, zzhjVarZzb, zzhw.zza(), zzgv.zza(), zzid.zza(), zzfw.zzb(), zzhk.zza());
        }
        return zzhp.zza(cls, zzhjVarZzb, zzhw.zza(), zzgv.zza(), zzid.zzb(), null, zzhk.zza());
    }

    private static boolean zza(zzhj zzhjVar) {
        return zzhjVar.zza() == zzhx.zza;
    }

    private static zzhm zza() {
        try {
            return (zzhm) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzb;
        }
    }
}
