package com.google.android.gms.internal.mlkit_vision_face;

import android.content.Context;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.internal.GmsLogger;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkt implements zzkp {
    private static final GmsLogger zza = new GmsLogger("ClearcutTransport", "");
    private final ClearcutLogger zzb;

    public zzkt(Context context) {
        this.zzb = ClearcutLogger.anonymousLogger(context, "FIREBASE_ML_SDK");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzkp
    public final void zza(zzks zzksVar) {
        GmsLogger gmsLogger = zza;
        String strValueOf = String.valueOf(zzksVar);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 30);
        sb.append("Logging FirebaseMlSdkLogEvent ");
        sb.append(strValueOf);
        gmsLogger.d("ClearcutTransport", sb.toString());
        try {
            this.zzb.newEvent(zzksVar.zza(1, true)).log();
        } catch (SecurityException e) {
            zza.e("ClearcutTransport", "Exception thrown from the logging side", e);
        }
    }
}
