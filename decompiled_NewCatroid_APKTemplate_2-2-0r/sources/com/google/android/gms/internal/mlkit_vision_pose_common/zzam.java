package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Arrays;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzam<E> extends zzap<E> {
    Object[] zza;
    int zzb;
    boolean zzc;

    zzam(int i) {
        zzy.zza(4, "initialCapacity");
        this.zza = new Object[4];
        this.zzb = 0;
    }

    public zzam<E> zza(E e) {
        zzh.zza(e);
        int i = this.zzb;
        int i2 = i + 1;
        Object[] objArr = this.zza;
        if (objArr.length < i2) {
            int length = objArr.length;
            if (i2 < 0) {
                throw new AssertionError("cannot store more than MAX_VALUE elements");
            }
            int iHighestOneBit = length + (length >> 1) + 1;
            if (iHighestOneBit < i2) {
                iHighestOneBit = Integer.highestOneBit(i) << 1;
            }
            if (iHighestOneBit < 0) {
                iHighestOneBit = Integer.MAX_VALUE;
            }
            this.zza = Arrays.copyOf(objArr, iHighestOneBit);
            this.zzc = false;
        } else if (this.zzc) {
            this.zza = (Object[]) objArr.clone();
            this.zzc = false;
        }
        Object[] objArr2 = this.zza;
        int i3 = this.zzb;
        this.zzb = i3 + 1;
        objArr2[i3] = e;
        return this;
    }
}
