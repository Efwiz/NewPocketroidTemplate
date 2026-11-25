package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaow implements zzaqa {
    private static final zzapd zza = new zzaou();
    private final zzapd zzb;

    public zzaow() {
        zzapd zzapdVar;
        zzapd[] zzapdVarArr = new zzapd[2];
        zzapdVarArr[0] = zzanl.zza();
        try {
            zzapdVar = (zzapd) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            zzapdVar = zza;
        }
        zzapdVarArr[1] = zzapdVar;
        zzaov zzaovVar = new zzaov(zzapdVarArr);
        zzaoc.zzf(zzaovVar, "messageInfoFactory");
        this.zzb = zzaovVar;
    }

    private static boolean zzb(zzapc zzapcVar) {
        return zzapcVar.zzc() == 1;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaqa
    public final <T> zzapz<T> zza(Class<T> cls) {
        zzaqb.zzG(cls);
        zzapc zzapcVarZzb = this.zzb.zzb(cls);
        if (zzapcVarZzb.zzb()) {
            return zzans.class.isAssignableFrom(cls) ? zzapj.zzc(zzaqb.zzB(), zzanf.zzb(), zzapcVarZzb.zza()) : zzapj.zzc(zzaqb.zzz(), zzanf.zza(), zzapcVarZzb.zza());
        }
        if (zzans.class.isAssignableFrom(cls)) {
            return zzb(zzapcVarZzb) ? zzapi.zzm(cls, zzapcVarZzb, zzapm.zzb(), zzaos.zze(), zzaqb.zzB(), zzanf.zzb(), zzapb.zzb()) : zzapi.zzm(cls, zzapcVarZzb, zzapm.zzb(), zzaos.zze(), zzaqb.zzB(), null, zzapb.zzb());
        }
        return zzb(zzapcVarZzb) ? zzapi.zzm(cls, zzapcVarZzb, zzapm.zza(), zzaos.zzd(), zzaqb.zzz(), zzanf.zza(), zzapb.zza()) : zzapi.zzm(cls, zzapcVarZzb, zzapm.zza(), zzaos.zzd(), zzaqb.zzA(), null, zzapb.zza());
    }
}
