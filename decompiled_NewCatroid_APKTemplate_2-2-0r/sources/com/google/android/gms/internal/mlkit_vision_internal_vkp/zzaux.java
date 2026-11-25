package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaux extends zzans<zzaux, zzauu> implements zzapg {
    private static final zzaux zzb;
    private zzaob<zzauw> zze = zzI();

    static {
        zzaux zzauxVar = new zzaux();
        zzb = zzauxVar;
        zzans.zzM(zzaux.class, zzauxVar);
    }

    private zzaux() {
    }

    public static zzaux zze(byte[] bArr, zzanc zzancVar) throws zzaoe {
        return (zzaux) zzans.zzD(zzb, bArr, zzancVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzauw.class});
        }
        if (i2 == 3) {
            return new zzaux();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzauu(zzatrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final List<zzauw> zzf() {
        return this.zze;
    }
}
