package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzbz {
    private final int zza;
    private final zzab zzb;

    protected zzbz(zzab zzabVar, int i) {
        if (zzabVar == null) {
            throw new IllegalArgumentException("format options cannot be null");
        }
        if (i < 0) {
            throw new IllegalArgumentException(new StringBuilder(26).append("invalid index: ").append(i).toString());
        }
        this.zza = i;
        this.zzb = zzabVar;
    }

    protected abstract void zza(zzby zzbyVar, Object obj);

    public final int zza() {
        return this.zza;
    }

    protected final zzab zzb() {
        return this.zzb;
    }

    public final void zza(zzby zzbyVar, Object[] objArr) {
        int i = this.zza;
        if (i < objArr.length) {
            Object obj = objArr[i];
            if (obj != null) {
                zza(zzbyVar, obj);
                return;
            } else {
                zzbyVar.zzb();
                return;
            }
        }
        zzbyVar.zza();
    }
}
