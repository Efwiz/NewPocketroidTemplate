package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfv implements zzha {
    private static final zzgf zzb = new zzfy();
    private final zzgf zza;

    public zzfv() {
        this(new zzfx(zzez.zza(), zza()));
    }

    private zzfv(zzgf zzgfVar) {
        this.zza = (zzgf) zzfe.zza(zzgfVar, "messageInfoFactory");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzha
    public final <T> zzgx<T> zza(Class<T> cls) {
        zzgz.zza((Class<?>) cls);
        zzgg zzggVarZzb = this.zza.zzb(cls);
        if (zzggVarZzb.zzb()) {
            if (zzfb.class.isAssignableFrom(cls)) {
                return zzgo.zza(zzgz.zzc(), zzer.zza(), zzggVarZzb.zzc());
            }
            return zzgo.zza(zzgz.zza(), zzer.zzb(), zzggVarZzb.zzc());
        }
        if (zzfb.class.isAssignableFrom(cls)) {
            if (zza(zzggVarZzb)) {
                return zzgm.zza(cls, zzggVarZzb, zzgs.zzb(), zzfs.zzb(), zzgz.zzc(), zzer.zza(), zzgd.zzb());
            }
            return zzgm.zza(cls, zzggVarZzb, zzgs.zzb(), zzfs.zzb(), zzgz.zzc(), (zzeq<?>) null, zzgd.zzb());
        }
        if (zza(zzggVarZzb)) {
            return zzgm.zza(cls, zzggVarZzb, zzgs.zza(), zzfs.zza(), zzgz.zza(), zzer.zzb(), zzgd.zza());
        }
        return zzgm.zza(cls, zzggVarZzb, zzgs.zza(), zzfs.zza(), zzgz.zzb(), (zzeq<?>) null, zzgd.zza());
    }

    private static boolean zza(zzgg zzggVar) {
        return zzggVar.zza() == zzgt.zza;
    }

    private static zzgf zza() {
        try {
            return (zzgf) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            return zzb;
        }
    }
}
