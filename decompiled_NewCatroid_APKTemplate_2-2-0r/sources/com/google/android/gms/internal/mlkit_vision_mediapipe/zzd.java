package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzv;
import com.reandroid.dex.sections.Marker;
import java.io.PrintStream;
import java.util.logging.Level;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzd<API extends zzv<API>> {
    private final zzal zza;

    protected zzd(zzal zzalVar) {
        this.zza = (zzal) zzcj.zza(zzalVar, Marker.BACKEND);
    }

    public abstract API zza(Level level);

    protected final String zza() {
        return this.zza.zza();
    }

    protected final boolean zzb(Level level) {
        return this.zza.zza(level);
    }

    final void zza(zzai zzaiVar) {
        zzcj.zza(zzaiVar, "data");
        try {
            this.zza.zza(zzaiVar);
        } catch (RuntimeException e) {
            try {
                this.zza.zza(e, zzaiVar);
            } catch (zzak e2) {
                throw e2;
            } catch (RuntimeException e3) {
                PrintStream printStream = System.err;
                String strValueOf = String.valueOf(e3.getMessage());
                printStream.println(strValueOf.length() != 0 ? "logging error: ".concat(strValueOf) : new String("logging error: "));
                zzcm.zza(e3, System.err);
            }
        }
    }
}
