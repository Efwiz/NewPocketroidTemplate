package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Arrays;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzav<K, V> {
    Object[] zza = new Object[8];
    int zzb = 0;

    public final zzav<K, V> zza(K k, V v) {
        int i = this.zzb + 1;
        int i2 = i + i;
        Object[] objArr = this.zza;
        int length = objArr.length;
        if (i2 > length) {
            this.zza = Arrays.copyOf(objArr, zzao.zzb(length, i2));
        }
        zzaa.zzb(k, v);
        Object[] objArr2 = this.zza;
        int i3 = this.zzb;
        int i4 = i3 + i3;
        objArr2[i4] = k;
        objArr2[i4 + 1] = v;
        this.zzb = i3 + 1;
        return this;
    }

    public final zzaw<K, V> zzb() {
        return zzbo.zzg(this.zzb, this.zza);
    }
}
