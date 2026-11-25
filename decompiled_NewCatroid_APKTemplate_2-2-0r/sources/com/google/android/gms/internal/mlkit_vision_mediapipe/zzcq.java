package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.PrintStream;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcq extends zzcp {
    private final zzco zza = new zzco();

    zzcq() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzcp
    public final void zza(Throwable th, PrintStream printStream) {
        th.printStackTrace(printStream);
        List<Throwable> listZza = this.zza.zza(th, false);
        if (listZza == null) {
            return;
        }
        synchronized (listZza) {
            for (Throwable th2 : listZza) {
                printStream.print("Suppressed: ");
                th2.printStackTrace(printStream);
            }
        }
    }
}
