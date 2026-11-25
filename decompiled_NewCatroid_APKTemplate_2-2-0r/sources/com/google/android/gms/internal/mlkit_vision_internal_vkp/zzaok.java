package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzaok {
    private static final zzanc zzb = zzanc.zza();
    protected volatile zzapf zza;
    private volatile zzamk zzc;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaok)) {
            return false;
        }
        zzaok zzaokVar = (zzaok) obj;
        zzapf zzapfVar = this.zza;
        zzapf zzapfVar2 = zzaokVar.zza;
        if (zzapfVar == null && zzapfVar2 == null) {
            return zzb().equals(zzaokVar.zzb());
        }
        if (zzapfVar != null && zzapfVar2 != null) {
            return zzapfVar.equals(zzapfVar2);
        }
        if (zzapfVar != null) {
            zzaokVar.zzd(zzapfVar.zzv());
            return zzapfVar.equals(zzaokVar.zza);
        }
        zzd(zzapfVar2.zzv());
        return this.zza.equals(zzapfVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final int zza() {
        if (this.zzc != null) {
            return ((zzamh) this.zzc).zza.length;
        }
        if (this.zza != null) {
            return this.zza.zzy();
        }
        return 0;
    }

    public final zzamk zzb() {
        if (this.zzc != null) {
            return this.zzc;
        }
        synchronized (this) {
            if (this.zzc != null) {
                return this.zzc;
            }
            if (this.zza == null) {
                this.zzc = zzamk.zzb;
            } else {
                this.zzc = this.zza.zzs();
            }
            return this.zzc;
        }
    }

    public final zzapf zzc(zzapf zzapfVar) {
        zzapf zzapfVar2 = this.zza;
        this.zzc = null;
        this.zza = zzapfVar;
        return zzapfVar2;
    }

    protected final void zzd(zzapf zzapfVar) {
        if (this.zza != null) {
            return;
        }
        synchronized (this) {
            if (this.zza == null) {
                try {
                    this.zza = zzapfVar;
                    this.zzc = zzamk.zzb;
                } catch (zzaoe unused) {
                    this.zza = zzapfVar;
                    this.zzc = zzamk.zzb;
                }
            }
        }
    }
}
