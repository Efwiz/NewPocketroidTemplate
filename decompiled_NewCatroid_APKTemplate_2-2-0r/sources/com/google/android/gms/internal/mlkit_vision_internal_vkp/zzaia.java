package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaia extends zzans<zzaia, zzahz> implements zzapg {
    private static final zzaia zzb;
    private int zze;
    private zzahy zzf;
    private double zzg;
    private boolean zzh;
    private zzaif zzi;
    private byte zzj = 2;

    static {
        zzaia zzaiaVar = new zzaia();
        zzb = zzaiaVar;
        zzans.zzM(zzaia.class, zzaiaVar);
    }

    private zzaia() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzj);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0001\u0001ဉ\u0000\u0002က\u0001\u0003ဇ\u0002\u0004ᐉ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzaia();
        }
        zzahu zzahuVar = null;
        if (i2 == 4) {
            return new zzahz(zzahuVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzj = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
