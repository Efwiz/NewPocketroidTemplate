package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzas<E> extends zzj<E> {
    private final zzau<E> zza;

    zzas(zzau<E> zzauVar, int i) {
        super(zzauVar.size(), i);
        this.zza = zzauVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzj
    protected final E zza(int i) {
        return this.zza.get(i);
    }
}
