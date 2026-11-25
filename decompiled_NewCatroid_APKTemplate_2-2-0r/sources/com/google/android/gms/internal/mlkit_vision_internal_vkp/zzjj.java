package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzjj extends zzans<zzjj, zzji> implements zzapg {
    private static final zzjj zzb;
    private int zze;
    private long zzf;
    private zzgl zzg;
    private zzgt zzh;
    private zzaob<zzgw> zzi = zzI();

    static {
        zzjj zzjjVar = new zzjj();
        zzb = zzjjVar;
        zzans.zzM(zzjj.class, zzjjVar);
    }

    private zzjj() {
    }

    public final int zza() {
        return this.zzi.size();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001ဂ\u0000\u0002ဉ\u0001\u0003\u001b\u0004ဉ\u0002", new Object[]{"zze", "zzf", "zzg", "zzi", zzgw.class, "zzh"});
        }
        if (i2 == 3) {
            return new zzjj();
        }
        zzjh zzjhVar = null;
        if (i2 == 4) {
            return new zzji(zzjhVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final long zzc() {
        return this.zzf;
    }

    public final zzgl zzd() {
        zzgl zzglVar = this.zzg;
        return zzglVar == null ? zzgl.zzg() : zzglVar;
    }

    public final zzgw zze(int i) {
        return this.zzi.get(i);
    }

    public final boolean zzg() {
        return (this.zze & 1) != 0;
    }
}
