package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzdh {
    protected zzcx zza;

    public zzdh(zzcx zzcxVar) {
        this.zza = zzcxVar;
    }

    private final native long zza(long j, int i);

    private final native long zza(long j, ByteBuffer byteBuffer, int i, int i2);

    public final zzdf zza(ByteBuffer byteBuffer, int i, int i2) {
        int i3 = ((((i * 3) + 3) / 4) << 2) * i2;
        if (i3 != byteBuffer.capacity()) {
            throw new RuntimeException(new StringBuilder(45).append("The size of the buffer should be: ").append(i3).toString());
        }
        return zzdf.zza(zza(this.zza.zza(), byteBuffer, i, i2));
    }

    public final zzdf zza(int i) {
        return zzdf.zza(zza(this.zza.zza(), i));
    }

    private void zza(long j, zzdk zzdkVar) {
        zzdkVar.zza(new zzdc(j));
    }
}
