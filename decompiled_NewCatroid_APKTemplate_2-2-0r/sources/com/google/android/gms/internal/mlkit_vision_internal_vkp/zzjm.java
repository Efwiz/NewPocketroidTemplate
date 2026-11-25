package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzjm extends zzans<zzjm, zzjl> implements zzapg {
    private static final zzjm zzb;
    private zzaob<zzjj> zze = zzI();

    static {
        zzjm zzjmVar = new zzjm();
        zzb = zzjmVar;
        zzans.zzM(zzjm.class, zzjmVar);
    }

    private zzjm() {
    }

    public static zzjm zze() {
        return zzb;
    }

    public final int zza() {
        return this.zze.size();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzjj.class});
        }
        if (i2 == 3) {
            return new zzjm();
        }
        zzjk zzjkVar = null;
        if (i2 == 4) {
            return new zzjl(zzjkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final zzjj zzc(int i) {
        return this.zze.get(i);
    }
}
