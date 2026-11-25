package com.google.android.gms.internal.mlkit_vision_face;

import com.google.android.datatransport.Priority;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final /* synthetic */ class zzky {
    static final /* synthetic */ int[] zza;

    static {
        int[] iArr = new int[Priority.values().length];
        zza = iArr;
        try {
            iArr[Priority.HIGHEST.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            zza[Priority.VERY_LOW.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            zza[Priority.DEFAULT.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
