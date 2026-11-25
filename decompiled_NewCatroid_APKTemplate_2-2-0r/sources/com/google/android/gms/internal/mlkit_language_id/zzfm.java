package com.google.android.gms.internal.mlkit_language_id;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfm implements zzgo {
    private static final zzfw zzb = new zzfp();
    private final zzfw zza;

    public zzfm() {
        this(new zzfo(zzep.zza(), zza()));
    }

    private zzfm(zzfw zzfwVar) {
        this.zza = (zzfw) zzeq.zza(zzfwVar, "messageInfoFactory");
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzgo
    public final <T> zzgp<T> zza(Class<T> cls) {
        zzgr.zza((Class<?>) cls);
        zzfx zzfxVarZzb = this.zza.zzb(cls);
        if (zzfxVarZzb.zzb()) {
            if (zzeo.class.isAssignableFrom(cls)) {
                return zzgc.zza(zzgr.zzc(), zzeg.zza(), zzfxVarZzb.zzc());
            }
            return zzgc.zza(zzgr.zza(), zzeg.zzb(), zzfxVarZzb.zzc());
        }
        if (zzeo.class.isAssignableFrom(cls)) {
            if (zza(zzfxVarZzb)) {
                return zzgd.zza(cls, zzfxVarZzb, zzgg.zzb(), zzfj.zzb(), zzgr.zzc(), zzeg.zza(), zzfu.zzb());
            }
            return zzgd.zza(cls, zzfxVarZzb, zzgg.zzb(), zzfj.zzb(), zzgr.zzc(), null, zzfu.zzb());
        }
        if (zza(zzfxVarZzb)) {
            return zzgd.zza(cls, zzfxVarZzb, zzgg.zza(), zzfj.zza(), zzgr.zza(), zzeg.zzb(), zzfu.zza());
        }
        return zzgd.zza(cls, zzfxVarZzb, zzgg.zza(), zzfj.zza(), zzgr.zzb(), null, zzfu.zza());
    }

    private static boolean zza(zzfx zzfxVar) {
        return zzfxVar.zza() == zzgl.zza;
    }

    private static zzfw zza() {
        try {
            return (zzfw) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzb;
        }
    }
}
