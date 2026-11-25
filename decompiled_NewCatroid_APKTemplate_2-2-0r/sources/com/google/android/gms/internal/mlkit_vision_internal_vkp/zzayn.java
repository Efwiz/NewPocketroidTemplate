package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzayn extends zzans<zzayn, zzaym> implements zzapg {
    private static final zzayn zzb;
    private byte zzf = 2;
    private zzaob<zzayk> zze = zzI();

    static {
        zzayn zzaynVar = new zzayn();
        zzb = zzaynVar;
        zzans.zzM(zzayn.class, zzaynVar);
    }

    private zzayn() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzf);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001Л", new Object[]{"zze", zzayk.class});
        }
        if (i2 == 3) {
            return new zzayn();
        }
        zzayl zzaylVar = null;
        if (i2 == 4) {
            return new zzaym(zzaylVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzf = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
