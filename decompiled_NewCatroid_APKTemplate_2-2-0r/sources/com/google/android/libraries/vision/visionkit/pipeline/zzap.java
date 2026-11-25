package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbaj;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzhc;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zziu;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzap extends zzans<zzap, zzao> implements zzapg {
    private static final zzap zzb;
    private int zze;
    private zzbaj zzf;
    private boolean zzg;
    private zziu zzh;
    private boolean zzi;
    private zzhc zzj;
    private float zzk;
    private boolean zzl;
    private boolean zzm;
    private boolean zzo;
    private float zzp;
    private int zzq;
    private com.google.android.gms.internal.mlkit_vision_internal_vkp.zzbn zzr;
    private byte zzs = 2;
    private int zzn = -1;

    static {
        zzap zzapVar = new zzap();
        zzb = zzapVar;
        zzans.zzM(zzap.class, zzapVar);
    }

    private zzap() {
    }

    public static zzao zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zzap zzapVar, zzbaj zzbajVar) {
        zzbajVar.getClass();
        zzapVar.zzf = zzbajVar;
        zzapVar.zze |= 1;
    }

    static /* synthetic */ void zze(zzap zzapVar, zzhc zzhcVar) {
        zzhcVar.getClass();
        zzapVar.zzj = zzhcVar;
        zzapVar.zze |= 16;
    }

    static /* synthetic */ void zzf(zzap zzapVar, boolean z) {
        zzapVar.zze |= 128;
        zzapVar.zzm = z;
    }

    static /* synthetic */ void zzg(zzap zzapVar, int i) {
        zzapVar.zze |= 256;
        zzapVar.zzn = i;
    }

    static /* synthetic */ void zzh(zzap zzapVar, float f) {
        zzapVar.zze |= 1024;
        zzapVar.zzp = 0.6f;
    }

    static /* synthetic */ void zzi(zzap zzapVar, int i) {
        zzapVar.zze |= 2048;
        zzapVar.zzq = 0;
    }

    static /* synthetic */ void zzj(zzap zzapVar, zziu zziuVar) {
        zziuVar.getClass();
        zzapVar.zzh = zziuVar;
        zzapVar.zze |= 4;
    }

    static /* synthetic */ void zzk(zzap zzapVar, boolean z) {
        zzapVar.zze |= 8;
        zzapVar.zzi = true;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzs);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\r\u0000\u0001\u0001\r\r\u0000\u0000\u0001\u0001ᐉ\u0000\u0002ဉ\u0002\u0003ဉ\u0004\u0004ဇ\u0007\u0005င\b\u0006ဇ\t\u0007ဇ\u0001\bခ\n\tင\u000b\nဇ\u0003\u000bဉ\f\fခ\u0005\rဇ\u0006", new Object[]{"zze", "zzf", "zzh", "zzj", "zzm", "zzn", "zzo", "zzg", "zzp", "zzq", "zzi", "zzr", "zzk", "zzl"});
        }
        if (i2 == 3) {
            return new zzap();
        }
        zzan zzanVar = null;
        if (i2 == 4) {
            return new zzao(zzanVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzs = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
