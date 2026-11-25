package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Arrays;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
class zzan<E> extends zzao<E> {
    Object[] zza = new Object[4];
    int zzb = 0;
    boolean zzc;

    zzan(int i) {
    }

    private final void zzc(int i) {
        Object[] objArr = this.zza;
        int length = objArr.length;
        if (length < i) {
            this.zza = Arrays.copyOf(objArr, zzb(length, i));
            this.zzc = false;
        } else if (this.zzc) {
            this.zza = (Object[]) objArr.clone();
            this.zzc = false;
        }
    }

    public final zzan<E> zza(E e) {
        e.getClass();
        zzc(this.zzb + 1);
        Object[] objArr = this.zza;
        int i = this.zzb;
        this.zzb = i + 1;
        objArr[i] = e;
        return this;
    }
}
