package com.google.android.gms.internal.mlkit_vision_text;

import com.google.android.datatransport.Priority;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final /* synthetic */ class zzkv {
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
