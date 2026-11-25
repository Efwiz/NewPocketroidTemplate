package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzajl extends zzans<zzajl, zzajk> implements zzapg {
    private static final zzajl zzb;
    private int zze;
    private zzajn zzf;
    private float zzg;
    private byte zzi = 2;
    private zzanx zzh = zzF();

    static {
        zzajl zzajlVar = new zzajl();
        zzb = zzajlVar;
        zzans.zzM(zzajl.class, zzajlVar);
    }

    private zzajl() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzi);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0001\u0001ᐉ\u0000\u0002ခ\u0001\u0003\u0013", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzajl();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzajk(zzaivVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzi = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
