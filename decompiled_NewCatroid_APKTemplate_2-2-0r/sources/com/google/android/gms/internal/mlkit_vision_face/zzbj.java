package com.google.android.gms.internal.mlkit_vision_face;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbj<E> extends zzac<E> {
    private final zzbl<E> zza;

    zzbj(zzbl<E> zzblVar, int i) {
        super(zzblVar.size(), i);
        this.zza = zzblVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzac
    protected final E zza(int i) {
        return this.zza.get(i);
    }
}
