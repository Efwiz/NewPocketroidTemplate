package com.google.android.gms.internal.gtm;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzwc implements zzwy {
    private static final zzwi zza = new zzwa();
    private final zzwi zzb;

    public zzwc() {
        zzwi zzwiVar;
        zzwi[] zzwiVarArr = new zzwi[2];
        zzwiVarArr[0] = zzus.zza();
        try {
            zzwiVar = (zzwi) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            zzwiVar = zza;
        }
        zzwiVarArr[1] = zzwiVar;
        zzwb zzwbVar = new zzwb(zzwiVarArr);
        zzvi.zzf(zzwbVar, "messageInfoFactory");
        this.zzb = zzwbVar;
    }

    private static boolean zzb(zzwh zzwhVar) {
        return zzwhVar.zzc() == 1;
    }

    @Override // com.google.android.gms.internal.gtm.zzwy
    public final <T> zzwx<T> zza(Class<T> cls) {
        zzwz.zzG(cls);
        zzwh zzwhVarZzb = this.zzb.zzb(cls);
        if (zzwhVarZzb.zzb()) {
            return zzuz.class.isAssignableFrom(cls) ? zzwo.zzc(zzwz.zzB(), zzum.zzb(), zzwhVarZzb.zza()) : zzwo.zzc(zzwz.zzz(), zzum.zza(), zzwhVarZzb.zza());
        }
        if (zzuz.class.isAssignableFrom(cls)) {
            return zzb(zzwhVarZzb) ? zzwn.zzl(cls, zzwhVarZzb, zzwr.zzb(), zzvy.zze(), zzwz.zzB(), zzum.zzb(), zzwg.zzb()) : zzwn.zzl(cls, zzwhVarZzb, zzwr.zzb(), zzvy.zze(), zzwz.zzB(), null, zzwg.zzb());
        }
        return zzb(zzwhVarZzb) ? zzwn.zzl(cls, zzwhVarZzb, zzwr.zza(), zzvy.zzd(), zzwz.zzz(), zzum.zza(), zzwg.zza()) : zzwn.zzl(cls, zzwhVarZzb, zzwr.zza(), zzvy.zzd(), zzwz.zzA(), null, zzwg.zza());
    }
}
