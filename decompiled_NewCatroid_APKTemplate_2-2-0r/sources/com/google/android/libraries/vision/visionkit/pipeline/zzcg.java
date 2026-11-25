package com.google.android.libraries.vision.visionkit.pipeline;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcg extends zzans<zzcg, zzcf> implements zzapg {
    private static final zzcg zzb;
    private int zze;
    private Object zzg;
    private zzdp zzh;
    private boolean zzi;
    private zzj zzj;
    private zzfd zzk;
    private zzg zzl;
    private int zzm;
    private int zzf = 0;
    private byte zzn = 2;

    static {
        zzcg zzcgVar = new zzcg();
        zzb = zzcgVar;
        zzans.zzM(zzcg.class, zzcgVar);
    }

    private zzcg() {
    }

    public static zzcf zzc() {
        return zzb.zzz();
    }

    static /* synthetic */ void zze(zzcg zzcgVar, zzfd zzfdVar) {
        zzfdVar.getClass();
        zzcgVar.zzk = zzfdVar;
        zzcgVar.zze |= 8;
    }

    static /* synthetic */ void zzf(zzcg zzcgVar, zzg zzgVar) {
        zzgVar.getClass();
        zzcgVar.zzl = zzgVar;
        zzcgVar.zze |= 64;
    }

    static /* synthetic */ void zzg(zzcg zzcgVar, zzdp zzdpVar) {
        zzdpVar.getClass();
        zzcgVar.zzh = zzdpVar;
        zzcgVar.zze |= 1;
    }

    static /* synthetic */ void zzh(zzcg zzcgVar, zzj zzjVar) {
        zzjVar.getClass();
        zzcgVar.zzj = zzjVar;
        zzcgVar.zze |= 4;
    }

    public final int zza() {
        return this.zzm;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzn);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\b\u0001\u0001\u0001\b\b\u0000\u0000\u0001\u0001ᐉ\u0000\u0002ဇ\u0001\u0003ဉ\u0003\u0004ဉ\u0002\u0005်\u0000\u0006်\u0000\u0007ဉ\u0006\bင\u0007", new Object[]{"zzg", "zzf", "zze", "zzh", "zzi", "zzk", "zzj", "zzl", "zzm"});
        }
        if (i2 == 3) {
            return new zzcg();
        }
        zzce zzceVar = null;
        if (i2 == 4) {
            return new zzcf(zzceVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzn = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final boolean zzi() {
        if (this.zzf == 6) {
            return ((Boolean) this.zzg).booleanValue();
        }
        return false;
    }

    public final boolean zzj() {
        if (this.zzf == 5) {
            return ((Boolean) this.zzg).booleanValue();
        }
        return false;
    }

    public final boolean zzk() {
        return (this.zze & 128) != 0;
    }
}
