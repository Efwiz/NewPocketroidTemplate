package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzawa extends zzans<zzawa, zzavz> implements zzapg {
    private static final zzawa zzb;
    private int zze;
    private zzaif zzg;
    private float zzh;
    private byte zzi = 2;
    private int zzf = 2;

    static {
        zzawa zzawaVar = new zzawa();
        zzb = zzawaVar;
        zzans.zzM(zzawa.class, zzawaVar);
    }

    private zzawa() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzi);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0007\u0003\u0000\u0000\u0001\u0001ဌ\u0000\u0002ᐉ\u0001\u0007ခ\u0002", new Object[]{"zze", "zzf", zzavy.zza, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzawa();
        }
        zzavr zzavrVar = null;
        if (i2 == 4) {
            return new zzavz(zzavrVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzi = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
