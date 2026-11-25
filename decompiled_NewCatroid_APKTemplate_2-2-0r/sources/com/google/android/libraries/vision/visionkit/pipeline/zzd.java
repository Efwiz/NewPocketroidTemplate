package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalo;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanc;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaob;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaoe;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaup;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzd extends zzans<zzd, zzc> implements zzapg {
    private static final zzd zzb;
    private byte zzf = 2;
    private zzaob<zzaup> zze = zzI();

    static {
        zzd zzdVar = new zzd();
        zzb = zzdVar;
        zzans.zzM(zzd.class, zzdVar);
    }

    private zzd() {
    }

    public static zzd zzd() {
        return zzb;
    }

    public static zzd zze(byte[] bArr, zzanc zzancVar) throws zzaoe {
        return (zzd) zzans.zzD(zzb, bArr, zzancVar);
    }

    static /* synthetic */ void zzg(zzd zzdVar, Iterable iterable) {
        zzaob<zzaup> zzaobVar = zzdVar.zze;
        if (!zzaobVar.zzc()) {
            zzdVar.zze = zzans.zzJ(zzaobVar);
        }
        zzalo.zzt(iterable, zzdVar.zze);
    }

    public final int zza() {
        return this.zze.size();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzf);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001Л", new Object[]{"zze", zzaup.class});
        }
        if (i2 == 3) {
            return new zzd();
        }
        zzb zzbVar = null;
        if (i2 == 4) {
            return new zzc(zzbVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzf = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final List<zzaup> zzf() {
        return this.zze;
    }
}
