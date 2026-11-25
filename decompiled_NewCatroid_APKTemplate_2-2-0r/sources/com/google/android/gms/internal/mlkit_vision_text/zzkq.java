package com.google.android.gms.internal.mlkit_vision_text;

import android.content.Context;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.internal.GmsLogger;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkq implements zzkn {
    private static final GmsLogger zza = new GmsLogger("ClearcutTransport", "");
    private final ClearcutLogger zzb;

    public zzkq(Context context) {
        this.zzb = ClearcutLogger.anonymousLogger(context, "FIREBASE_ML_SDK");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzkn
    public final void zza(zzkp zzkpVar) {
        GmsLogger gmsLogger = zza;
        String strValueOf = String.valueOf(zzkpVar);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 30);
        sb.append("Logging FirebaseMlSdkLogEvent ");
        sb.append(strValueOf);
        gmsLogger.d("ClearcutTransport", sb.toString());
        try {
            this.zzb.newEvent(zzkpVar.zza(1, true)).log();
        } catch (SecurityException e) {
            zza.e("ClearcutTransport", "Exception thrown from the logging side", e);
        }
    }
}
