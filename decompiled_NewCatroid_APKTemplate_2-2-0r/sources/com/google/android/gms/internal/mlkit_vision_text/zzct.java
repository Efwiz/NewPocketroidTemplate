package com.google.android.gms.internal.mlkit_vision_text;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzct extends zzcq {
    private final zzcs zza = new zzcs();

    zzct() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzcq
    public final void zza(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        }
        this.zza.zza(th, true).add(th2);
    }
}
