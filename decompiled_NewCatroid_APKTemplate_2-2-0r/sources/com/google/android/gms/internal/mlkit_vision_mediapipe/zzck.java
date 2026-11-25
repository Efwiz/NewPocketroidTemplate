package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.OutputStream;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzck extends OutputStream {
    zzck() {
    }

    @Override // java.io.OutputStream
    public final void write(int i) {
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr) {
        zza.zza(bArr);
    }

    @Override // java.io.OutputStream
    public final void write(byte[] bArr, int i, int i2) {
        zza.zza(bArr);
    }

    public final String toString() {
        return "ByteStreams.nullOutputStream()";
    }
}
