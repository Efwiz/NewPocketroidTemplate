package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbfr extends zzans<zzbfr, zzbfq> implements zzapg {
    private static final zzbfr zzb;
    private zzaob<zzbfo> zze = zzI();

    static {
        zzbfr zzbfrVar = new zzbfr();
        zzb = zzbfrVar;
        zzans.zzM(zzbfr.class, zzbfrVar);
    }

    private zzbfr() {
    }

    public static zzbfq zzc() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzg(zzbfr zzbfrVar, zzbfo zzbfoVar) {
        zzbfoVar.getClass();
        zzaob<zzbfo> zzaobVar = zzbfrVar.zze;
        if (!zzaobVar.zzc()) {
            zzbfrVar.zze = zzans.zzJ(zzaobVar);
        }
        zzbfrVar.zze.add(zzbfoVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"zze", zzbfo.class});
        }
        if (i2 == 3) {
            return new zzbfr();
        }
        zzbfp zzbfpVar = null;
        if (i2 == 4) {
            return new zzbfq(zzbfpVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final List<zzbfo> zzf() {
        return this.zze;
    }
}
