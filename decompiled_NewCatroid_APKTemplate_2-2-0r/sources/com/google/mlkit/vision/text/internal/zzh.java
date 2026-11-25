package com.google.mlkit.vision.text.internal;

import java.util.Comparator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
final /* synthetic */ class zzh implements Comparator {
    static final Comparator zza = new zzh();

    private zzh() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ((Integer) ((Map.Entry) obj).getValue()).compareTo((Integer) ((Map.Entry) obj2).getValue());
    }
}
