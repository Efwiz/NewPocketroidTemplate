package com.google.android.gms.internal.mlkit_vision_mediapipe;

import android.util.Log;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbm extends zzay implements zzaw {
    private static final zzau zza = new zzbl();
    private final String zzb;
    private final boolean zzc;
    private final boolean zzd;

    public zzbm(String str, String str2, boolean z) {
        this(str, str2, z, false, true);
    }

    private zzbm(String str, String str2, boolean z, boolean z2, boolean z3) {
        super(str2);
        this.zzb = zzbg.zza(str, str2, z);
        this.zzc = z2;
        this.zzd = z3;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzal
    public final boolean zza(Level level) {
        int iZza = zzbg.zza(level).zza();
        return Log.isLoggable(this.zzb, iZza) || Log.isLoggable(DevModeOverwritingStrategies.ALL, iZza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzal
    public final void zza(zzai zzaiVar) {
        if (!this.zzd) {
            zzaiVar = new zzbj(zzaiVar);
        }
        zzat.zza(zzaiVar, this, this.zzc ? zzax.zzb : zzax.zza, zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzaw
    public final void zza(Level level, String str, @NullableDecl Throwable th) {
        zzb(level, this.zzb, str, th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzb(Level level, String str, String str2, @NullableDecl Throwable th) {
        int iZza = zzbg.zza(level).zza();
        if (iZza == 2) {
            Log.v(str, str2, th);
            return;
        }
        if (iZza == 3) {
            Log.d(str, str2, th);
            return;
        }
        if (iZza == 4) {
            Log.i(str, str2, th);
            return;
        }
        if (iZza == 5) {
            Log.w(str, str2, th);
        } else if (iZza == 6) {
            Log.e(str, str2, th);
        } else {
            Log.wtf(str, String.format("Level \"%d\" is not a valid level", Integer.valueOf(level.intValue())));
        }
    }

    /* synthetic */ zzbm(String str, String str2, boolean z, boolean z2, boolean z3, zzbl zzblVar) {
        this(str, str2, z, z2, z3);
    }
}
