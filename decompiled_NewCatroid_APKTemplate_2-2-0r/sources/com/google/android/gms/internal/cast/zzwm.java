package com.google.android.gms.internal.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzwm extends IllegalArgumentException {
    zzwm(int i, int i2) {
        super("Unpaired surrogate at index " + i + " of " + i2);
    }
}
