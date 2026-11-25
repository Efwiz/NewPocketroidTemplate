package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcq extends zzans<zzcq, zzcn> implements zzapg {
    private static final zzcq zzb;
    private zzaob<zzcp> zze = zzI();

    static {
        zzcq zzcqVar = new zzcq();
        zzb = zzcqVar;
        zzans.zzM(zzcq.class, zzcqVar);
    }

    private zzcq() {
    }

    public static zzcn zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzcq zzcqVar, zzcp zzcpVar) {
        zzcpVar.getClass();
        zzaob<zzcp> zzaobVar = zzcqVar.zze;
        if (!zzaobVar.zzc()) {
            zzcqVar.zze = zzans.zzJ(zzaobVar);
        }
        zzcqVar.zze.add(zzcpVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzcp.class});
        }
        if (i2 == 3) {
            return new zzcq();
        }
        zzcm zzcmVar = null;
        if (i2 == 4) {
            return new zzcn(zzcmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
