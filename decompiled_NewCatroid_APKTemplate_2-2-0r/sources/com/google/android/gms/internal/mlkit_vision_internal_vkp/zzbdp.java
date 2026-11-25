package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import org.apache.commons.compress.archivers.tar.TarConstants;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbdp extends zzans<zzbdp, zzbdo> implements zzapg {
    private static final zzbdp zzb;

    static {
        zzbdp zzbdpVar = new zzbdp();
        zzb = zzbdpVar;
        zzans.zzM(zzbdp.class, zzbdpVar);
    }

    private zzbdp() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        zzbdn zzbdnVar = null;
        if (i2 == 2) {
            return zzL(zzb, TarConstants.VERSION_ANT, null);
        }
        if (i2 == 3) {
            return new zzbdp();
        }
        if (i2 == 4) {
            return new zzbdo(zzbdnVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
