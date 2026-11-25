package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzgu {
    private static final zzfr zza = zzfr.zza();
    private zzez zzb;
    private volatile zzhl zzc;
    private volatile zzez zzd;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgu)) {
            return false;
        }
        zzgu zzguVar = (zzgu) obj;
        zzhl zzhlVar = this.zzc;
        zzhl zzhlVar2 = zzguVar.zzc;
        if (zzhlVar == null && zzhlVar2 == null) {
            return zzc().equals(zzguVar.zzc());
        }
        if (zzhlVar != null && zzhlVar2 != null) {
            return zzhlVar.equals(zzhlVar2);
        }
        if (zzhlVar != null) {
            return zzhlVar.equals(zzguVar.zzb(zzhlVar.zzi()));
        }
        return zzb(zzhlVar2.zzi()).equals(zzhlVar2);
    }

    private final zzhl zzb(zzhl zzhlVar) {
        if (this.zzc == null) {
            synchronized (this) {
                if (this.zzc == null) {
                    try {
                        this.zzc = zzhlVar;
                        this.zzd = zzez.zza;
                    } catch (zzgl unused) {
                        this.zzc = zzhlVar;
                        this.zzd = zzez.zza;
                    }
                }
            }
        }
        return this.zzc;
    }

    public final zzhl zza(zzhl zzhlVar) {
        zzhl zzhlVar2 = this.zzc;
        this.zzb = null;
        this.zzd = null;
        this.zzc = zzhlVar;
        return zzhlVar2;
    }

    public final int zzb() {
        if (this.zzd != null) {
            return this.zzd.zza();
        }
        if (this.zzc != null) {
            return this.zzc.zzj();
        }
        return 0;
    }

    public final zzez zzc() {
        if (this.zzd != null) {
            return this.zzd;
        }
        synchronized (this) {
            if (this.zzd != null) {
                return this.zzd;
            }
            if (this.zzc == null) {
                this.zzd = zzez.zza;
            } else {
                this.zzd = this.zzc.zze();
            }
            return this.zzd;
        }
    }
}
