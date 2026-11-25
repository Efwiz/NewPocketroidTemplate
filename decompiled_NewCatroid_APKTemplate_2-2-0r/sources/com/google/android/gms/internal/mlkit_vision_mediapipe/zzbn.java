package com.google.android.gms.internal.mlkit_vision_mediapipe;

import android.util.Log;
import java.util.logging.Level;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbn extends zzay {
    private final String zza;
    private final boolean zzb;
    private final boolean zzc;
    private final boolean zzd;

    private zzbn(String str, boolean z, boolean z2, boolean z3) {
        super(null);
        this.zza = str;
        this.zzb = z;
        this.zzc = z2;
        this.zzd = z3;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzal
    public final boolean zza(Level level) {
        return true;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzal
    public final void zza(zzai zzaiVar) {
        final String strZza = (String) zzaiVar.zzk().zza(zzz.zza);
        if (strZza == null) {
            strZza = zzbg.zza(this.zza, zzaiVar.zzf().zza(), this.zzb);
        }
        int iZza = zzbg.zza(zzaiVar.zzd()).zza();
        if (Log.isLoggable(strZza, iZza) || Log.isLoggable(DevModeOverwritingStrategies.ALL, iZza)) {
            if (!this.zzd) {
                zzaiVar = new zzbj(zzaiVar);
            }
            zzat.zza(zzaiVar, new zzaw(strZza) { // from class: com.google.android.gms.internal.mlkit_vision_mediapipe.zzbq
                private final String zza;

                {
                    this.zza = strZza;
                }

                @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzaw
                public final void zza(Level level, String str, Throwable th) {
                    zzbm.zzb(level, this.zza, str, th);
                }
            }, this.zzc ? zzax.zzb : zzax.zza, zzbm.zza);
        }
    }

    /* synthetic */ zzbn(String str, boolean z, boolean z2, boolean z3, zzbl zzblVar) {
        this(str, z, z2, z3);
    }
}
