package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdg {
    private static final zzf zza = zzf.zza("com/google/mediapipe/framework/PacketGetter");

    private static native boolean zza(long j, ByteBuffer byteBuffer);

    private static native byte[] zza(long j);

    public static byte[] zza(zzdf zzdfVar) {
        return zza(zzdfVar.zza());
    }

    public static boolean zza(zzdf zzdfVar, ByteBuffer byteBuffer) {
        return zza(zzdfVar.zza(), byteBuffer);
    }
}
