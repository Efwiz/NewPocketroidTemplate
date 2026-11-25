package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.logging.Level;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzf extends zzd<zzh> {
    private static final zzj zza = new zzj();

    @Deprecated
    public static zzf zza(String str) {
        if (str.isEmpty()) {
            throw new IllegalArgumentException("injected class name is empty");
        }
        return new zzf(zzap.zza(str.replace('/', '.')));
    }

    private zzf(zzal zzalVar) {
        super(zzalVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzd
    public final /* synthetic */ zzv zza(Level level) {
        boolean zZzb = zzb(level);
        boolean zZza = zzap.zza(zza(), level, zZzb);
        if (zZzb || zZza) {
            return new zzg(this, level, zZza);
        }
        return zza;
    }
}
