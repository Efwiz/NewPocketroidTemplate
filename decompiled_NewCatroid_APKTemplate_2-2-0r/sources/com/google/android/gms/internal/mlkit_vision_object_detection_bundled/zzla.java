package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public enum zzla implements zzbz {
    CATEGORY_UNKNOWN(0),
    CATEGORY_HOME_GOOD(1),
    CATEGORY_FASHION_GOOD(2),
    CATEGORY_ANIMAL(3),
    CATEGORY_FOOD(4),
    CATEGORY_PLACE(5),
    CATEGORY_PLANT(6);

    private final int zzi;

    zzla(int i) {
        this.zzi = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbz
    public final int zza() {
        return this.zzi;
    }
}
