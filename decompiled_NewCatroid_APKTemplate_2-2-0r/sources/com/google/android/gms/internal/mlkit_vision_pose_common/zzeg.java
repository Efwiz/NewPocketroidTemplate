package com.google.android.gms.internal.mlkit_vision_pose_common;

import android.content.Context;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.firebase.components.Component;
import com.google.firebase.components.Dependency;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzeg implements zzej.zzc {
    private final ClearcutLogger zzc;
    private static final GmsLogger zzb = new GmsLogger("ClearcutTransport", "");
    public static final Component<?> zza = Component.builder(zzeg.class).add(Dependency.required((Class<?>) Context.class)).factory(zzei.zza).build();

    public zzeg(Context context) {
        this.zzc = ClearcutLogger.anonymousLogger(context, "FIREBASE_ML_SDK");
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzej.zzc
    public final void zza(zzbm.zzad zzadVar) {
        GmsLogger gmsLogger = zzb;
        String strValueOf = String.valueOf(zzadVar);
        gmsLogger.d("ClearcutTransport", new StringBuilder(String.valueOf(strValueOf).length() + 30).append("Logging FirebaseMlSdkLogEvent ").append(strValueOf).toString());
        try {
            this.zzc.newEvent(zzadVar.zzf()).log();
        } catch (SecurityException e) {
            zzb.e("ClearcutTransport", "Exception thrown from the logging side", e);
        }
    }
}
