package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzft extends zzfs {
    private zzft() {
        super();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfs
    final void zza(Object obj, long j) {
        zzb(obj, j).c_();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfs
    final <E> void zza(Object obj, Object obj2, long j) {
        zzff zzffVarZzb = zzb(obj, j);
        zzff zzffVarZzb2 = zzb(obj2, j);
        int size = zzffVarZzb.size();
        int size2 = zzffVarZzb2.size();
        if (size > 0 && size2 > 0) {
            if (!zzffVarZzb.zza()) {
                zzffVarZzb = zzffVarZzb.zza(size2 + size);
            }
            zzffVarZzb.addAll(zzffVarZzb2);
        }
        if (size > 0) {
            zzffVarZzb2 = zzffVarZzb;
        }
        zzhv.zza(obj, j, zzffVarZzb2);
    }

    private static <E> zzff<E> zzb(Object obj, long j) {
        return (zzff) zzhv.zzf(obj, j);
    }
}
