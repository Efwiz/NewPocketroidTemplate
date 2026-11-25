package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhc extends zzans<zzhc, zzhb> implements zzapg {
    private static final zzhc zzb;
    private zzaob<zzgz> zze = zzI();

    static {
        zzhc zzhcVar = new zzhc();
        zzb = zzhcVar;
        zzans.zzM(zzhc.class, zzhcVar);
    }

    private zzhc() {
    }

    public static zzhb zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzhc zzhcVar, zzgz zzgzVar) {
        zzgzVar.getClass();
        zzaob<zzgz> zzaobVar = zzhcVar.zze;
        if (!zzaobVar.zzc()) {
            zzhcVar.zze = zzans.zzJ(zzaobVar);
        }
        zzhcVar.zze.add(zzgzVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzgz.class});
        }
        if (i2 == 3) {
            return new zzhc();
        }
        zzha zzhaVar = null;
        if (i2 == 4) {
            return new zzhb(zzhaVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
