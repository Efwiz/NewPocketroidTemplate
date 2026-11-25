package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbfc extends zzans<zzbfc, zzbfb> implements zzapg {
    private static final zzbfc zzb;
    private zzarq zze;

    static {
        zzbfc zzbfcVar = new zzbfc();
        zzb = zzbfcVar;
        zzans.zzM(zzbfc.class, zzbfcVar);
    }

    private zzbfc() {
    }

    public static zzbfc zzf(InputStream inputStream, zzanc zzancVar) throws IOException {
        return (zzbfc) zzans.zzC(zzb, inputStream, zzancVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\t", new Object[]{"zze"});
        }
        if (i2 == 3) {
            return new zzbfc();
        }
        zzbfa zzbfaVar = null;
        if (i2 == 4) {
            return new zzbfb(zzbfaVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final zzarq zzc() {
        zzarq zzarqVar = this.zze;
        return zzarqVar == null ? zzarq.zze() : zzarqVar;
    }
}
