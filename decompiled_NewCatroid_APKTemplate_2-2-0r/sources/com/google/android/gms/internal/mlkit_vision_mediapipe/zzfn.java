package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzfn {
    private static final zzeo zza = zzeo.zza();
    private zzdu zzb;
    private volatile zzgi zzc;
    private volatile zzdu zzd;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfn)) {
            return false;
        }
        zzfn zzfnVar = (zzfn) obj;
        zzgi zzgiVar = this.zzc;
        zzgi zzgiVar2 = zzfnVar.zzc;
        if (zzgiVar == null && zzgiVar2 == null) {
            return zzc().equals(zzfnVar.zzc());
        }
        if (zzgiVar != null && zzgiVar2 != null) {
            return zzgiVar.equals(zzgiVar2);
        }
        if (zzgiVar != null) {
            return zzgiVar.equals(zzfnVar.zzb(zzgiVar.zzh()));
        }
        return zzb(zzgiVar2.zzh()).equals(zzgiVar2);
    }

    private final zzgi zzb(zzgi zzgiVar) {
        if (this.zzc == null) {
            synchronized (this) {
                if (this.zzc == null) {
                    try {
                        this.zzc = zzgiVar;
                        this.zzd = zzdu.zza;
                    } catch (zzfi unused) {
                        this.zzc = zzgiVar;
                        this.zzd = zzdu.zza;
                    }
                }
            }
        }
        return this.zzc;
    }

    public final zzgi zza(zzgi zzgiVar) {
        zzgi zzgiVar2 = this.zzc;
        this.zzb = null;
        this.zzd = null;
        this.zzc = zzgiVar;
        return zzgiVar2;
    }

    public final int zzb() {
        if (this.zzd != null) {
            return this.zzd.zza();
        }
        if (this.zzc != null) {
            return this.zzc.zzg();
        }
        return 0;
    }

    public final zzdu zzc() {
        if (this.zzd != null) {
            return this.zzd;
        }
        synchronized (this) {
            if (this.zzd != null) {
                return this.zzd;
            }
            if (this.zzc == null) {
                this.zzd = zzdu.zza;
            } else {
                this.zzd = this.zzc.zze();
            }
            return this.zzd;
        }
    }
}
