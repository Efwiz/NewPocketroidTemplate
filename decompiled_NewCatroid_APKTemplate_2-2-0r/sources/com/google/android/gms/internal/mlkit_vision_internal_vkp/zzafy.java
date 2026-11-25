package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzafy extends zzans<zzafy, zzafx> implements zzapg {
    private static final zzafy zzb;
    private int zze;
    private zzaqr zzf;
    private zzarq zzg;
    private String zzh = "";

    static {
        zzafy zzafyVar = new zzafy();
        zzb = zzafyVar;
        zzans.zzM(zzafy.class, zzafyVar);
    }

    private zzafy() {
    }

    public static zzafx zza() {
        return zzb.zzz();
    }

    public static zzafy zzd(byte[] bArr, zzanc zzancVar) throws zzaoe {
        return (zzafy) zzans.zzD(zzb, bArr, zzancVar);
    }

    static /* synthetic */ void zzh(zzafy zzafyVar, zzaqr zzaqrVar) {
        zzaqrVar.getClass();
        zzafyVar.zzf = zzaqrVar;
        zzafyVar.zze |= 1;
    }

    static /* synthetic */ void zzi(zzafy zzafyVar, zzarq zzarqVar) {
        zzarqVar.getClass();
        zzafyVar.zzg = zzarqVar;
        zzafyVar.zze |= 2;
    }

    static /* synthetic */ void zzj(zzafy zzafyVar, String str) {
        zzafyVar.zze |= 4;
        zzafyVar.zzh = str;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဈ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzafy();
        }
        zzafw zzafwVar = null;
        if (i2 == 4) {
            return new zzafx(zzafwVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final zzaqr zze() {
        zzaqr zzaqrVar = this.zzf;
        return zzaqrVar == null ? zzaqr.zzg() : zzaqrVar;
    }

    public final zzarq zzf() {
        zzarq zzarqVar = this.zzg;
        return zzarqVar == null ? zzarq.zze() : zzarqVar;
    }

    public final String zzg() {
        return this.zzh;
    }

    public final boolean zzk() {
        return (this.zze & 1) != 0;
    }
}
