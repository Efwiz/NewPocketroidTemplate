package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzayx extends zzano<zzayx, zzayw> implements zzapg {
    private static final zzayx zze;
    private byte zzf = 2;

    static {
        zzayx zzayxVar = new zzayx();
        zze = zzayxVar;
        zzans.zzM(zzayx.class, zzayxVar);
    }

    private zzayx() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzf);
        }
        zzayv zzayvVar = null;
        if (i2 == 2) {
            return zzL(zze, "\u0001\u0000", null);
        }
        if (i2 == 3) {
            return new zzayx();
        }
        if (i2 == 4) {
            return new zzayw(zzayvVar);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzf = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
