package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzawf extends zzans<zzawf, zzawe> implements zzapg {
    private static final zzawf zzb;
    private int zze;
    private byte zzh = 2;
    private String zzf = "";
    private float zzg = 1.0f;

    static {
        zzawf zzawfVar = new zzawf();
        zzb = zzawfVar;
        zzans.zzM(zzawf.class, zzawfVar);
    }

    private zzawf() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzh);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0001\u0001ᔈ\u0000\u0002ခ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzawf();
        }
        zzavr zzavrVar = null;
        if (i2 == 4) {
            return new zzawe(zzavrVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzh = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
