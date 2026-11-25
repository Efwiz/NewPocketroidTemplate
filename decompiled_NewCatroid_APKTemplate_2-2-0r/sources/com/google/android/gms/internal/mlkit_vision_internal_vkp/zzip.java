package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzip extends zzans<zzip, zzio> implements zzapg {
    private static final zzip zzb;
    private int zze;
    private zzaob<zzgw> zzf = zzI();
    private int zzg;

    static {
        zzip zzipVar = new zzip();
        zzb = zzipVar;
        zzans.zzM(zzip.class, zzipVar);
    }

    private zzip() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002င\u0000", new Object[]{"zze", "zzf", zzgw.class, "zzg"});
        }
        if (i2 == 3) {
            return new zzip();
        }
        zzin zzinVar = null;
        if (i2 == 4) {
            return new zzio(zzinVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final List<zzgw> zzc() {
        return this.zzf;
    }
}
