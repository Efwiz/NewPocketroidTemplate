package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import org.apache.commons.compress.archivers.tar.TarConstants;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbez extends zzans<zzbez, zzbey> implements zzapg {
    private static final zzbez zzb;

    static {
        zzbez zzbezVar = new zzbez();
        zzb = zzbezVar;
        zzans.zzM(zzbez.class, zzbezVar);
    }

    private zzbez() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        zzbex zzbexVar = null;
        if (i2 == 2) {
            return zzL(zzb, TarConstants.VERSION_ANT, null);
        }
        if (i2 == 3) {
            return new zzbez();
        }
        if (i2 == 4) {
            return new zzbey(zzbexVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
