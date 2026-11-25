package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhs<T> implements zzib<T> {
    private final zzhl zza;
    private final zzit<?, ?> zzb;
    private final boolean zzc;
    private final zzfu<?> zzd;

    private zzhs(zzit<?, ?> zzitVar, zzfu<?> zzfuVar, zzhl zzhlVar) {
        this.zzb = zzitVar;
        this.zzc = zzfuVar.zza(zzhlVar);
        this.zzd = zzfuVar;
        this.zza = zzhlVar;
    }

    static <T> zzhs<T> zza(zzit<?, ?> zzitVar, zzfu<?> zzfuVar, zzhl zzhlVar) {
        return new zzhs<>(zzitVar, zzfuVar, zzhlVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final boolean zza(T t, T t2) {
        if (!this.zzb.zza(t).equals(this.zzb.zza(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zza(t).equals(this.zzd.zza(t2));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final int zza(T t) {
        int iHashCode = this.zzb.zza(t).hashCode();
        return this.zzc ? (iHashCode * 53) + this.zzd.zza(t).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final void zzb(T t, T t2) {
        zzid.zza(this.zzb, t, t2);
        if (this.zzc) {
            zzid.zza(this.zzd, t, t2);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final void zza(T t, zzjn zzjnVar) throws IOException {
        Iterator itZzd = this.zzd.zza(t).zzd();
        while (itZzd.hasNext()) {
            Map.Entry entry = (Map.Entry) itZzd.next();
            zzfx zzfxVar = (zzfx) entry.getKey();
            if (zzfxVar.zzc() != zzjo.MESSAGE || zzfxVar.zzd() || zzfxVar.zze()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zzgs) {
                zzjnVar.zza(zzfxVar.zza(), (Object) ((zzgs) entry).zza().zzc());
            } else {
                zzjnVar.zza(zzfxVar.zza(), entry.getValue());
            }
        }
        zzit<?, ?> zzitVar = this.zzb;
        zzitVar.zzb(zzitVar.zza(t), zzjnVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final void zzc(T t) {
        this.zzb.zzb(t);
        this.zzd.zzc(t);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final boolean zzd(T t) {
        return this.zzd.zza(t).zzf();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzib
    public final int zzb(T t) {
        zzit<?, ?> zzitVar = this.zzb;
        int iZzc = zzitVar.zzc(zzitVar.zza(t));
        return this.zzc ? iZzc + this.zzd.zza(t).zzg() : iZzc;
    }
}
