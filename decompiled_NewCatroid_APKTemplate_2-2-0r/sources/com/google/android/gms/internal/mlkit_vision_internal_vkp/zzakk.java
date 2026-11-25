package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzakk extends zzans<zzakk, zzakj> implements zzapg {
    private static final zzakk zzb;
    private byte zzf = 2;
    private zzaob<zzaki> zze = zzI();

    static {
        zzakk zzakkVar = new zzakk();
        zzb = zzakkVar;
        zzans.zzM(zzakk.class, zzakkVar);
    }

    private zzakk() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzf);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001Л", new Object[]{"zze", zzaki.class});
        }
        if (i2 == 3) {
            return new zzakk();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzakj(zzaivVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzf = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
