package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zziw {
    private static final zziw zza = new zziw(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    public static zziw zza() {
        return zza;
    }

    static zziw zza(zziw zziwVar, zziw zziwVar2) {
        int i = zziwVar.zzb + zziwVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zziwVar.zzc, i);
        System.arraycopy(zziwVar2.zzc, 0, iArrCopyOf, zziwVar.zzb, zziwVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zziwVar.zzd, i);
        System.arraycopy(zziwVar2.zzd, 0, objArrCopyOf, zziwVar.zzb, zziwVar2.zzb);
        return new zziw(i, iArrCopyOf, objArrCopyOf, true);
    }

    private zziw() {
        this(0, new int[8], new Object[8], true);
    }

    private zziw(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public final void zzb() {
        this.zzf = false;
    }

    final void zza(zzjn zzjnVar) throws IOException {
        if (zzjnVar.zza() == zzjq.zzb) {
            for (int i = this.zzb - 1; i >= 0; i--) {
                zzjnVar.zza(this.zzc[i] >>> 3, this.zzd[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzjnVar.zza(this.zzc[i2] >>> 3, this.zzd[i2]);
        }
    }

    public final void zzb(zzjn zzjnVar) throws IOException {
        if (this.zzb == 0) {
            return;
        }
        if (zzjnVar.zza() == zzjq.zza) {
            for (int i = 0; i < this.zzb; i++) {
                zza(this.zzc[i], this.zzd[i], zzjnVar);
            }
            return;
        }
        for (int i2 = this.zzb - 1; i2 >= 0; i2--) {
            zza(this.zzc[i2], this.zzd[i2], zzjnVar);
        }
    }

    private static void zza(int i, Object obj, zzjn zzjnVar) throws IOException {
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            zzjnVar.zza(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 1) {
            zzjnVar.zzd(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 2) {
            zzjnVar.zza(i2, (zzez) obj);
            return;
        }
        if (i3 != 3) {
            if (i3 == 5) {
                zzjnVar.zzd(i2, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzgl.zza());
        }
        if (zzjnVar.zza() == zzjq.zza) {
            zzjnVar.zza(i2);
            ((zziw) obj).zzb(zzjnVar);
            zzjnVar.zzb(i2);
        } else {
            zzjnVar.zzb(i2);
            ((zziw) obj).zzb(zzjnVar);
            zzjnVar.zza(i2);
        }
    }

    public final int zzc() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzd = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            iZzd += zzfq.zzd(this.zzc[i2] >>> 3, (zzez) this.zzd[i2]);
        }
        this.zze = iZzd;
        return iZzd;
    }

    public final int zzd() {
        int iZze;
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzb; i3++) {
            int i4 = this.zzc[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 == 0) {
                iZze = zzfq.zze(i5, ((Long) this.zzd[i3]).longValue());
            } else if (i6 == 1) {
                iZze = zzfq.zzg(i5, ((Long) this.zzd[i3]).longValue());
            } else if (i6 == 2) {
                iZze = zzfq.zzc(i5, (zzez) this.zzd[i3]);
            } else if (i6 == 3) {
                iZze = (zzfq.zze(i5) << 1) + ((zziw) this.zzd[i3]).zzd();
            } else if (i6 == 5) {
                iZze = zzfq.zzi(i5, ((Integer) this.zzd[i3]).intValue());
            } else {
                throw new IllegalStateException(zzgl.zza());
            }
            i2 += iZze;
        }
        this.zze = i2;
        return i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zziw)) {
            return false;
        }
        zziw zziwVar = (zziw) obj;
        int i = this.zzb;
        if (i == zziwVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zziwVar.zzc;
            for (int i2 = 0; i2 < i; i2++) {
                if (iArr[i2] == iArr2[i2]) {
                }
            }
            Object[] objArr = this.zzd;
            Object[] objArr2 = zziwVar.zzd;
            int i3 = this.zzb;
            for (int i4 = 0; i4 < i3; i4++) {
                if (objArr[i4].equals(objArr2[i4])) {
                }
            }
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zzb;
        int i2 = (i + 527) * 31;
        int[] iArr = this.zzc;
        int iHashCode = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < i; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        int i5 = (i2 + i3) * 31;
        Object[] objArr = this.zzd;
        int i6 = this.zzb;
        for (int i7 = 0; i7 < i6; i7++) {
            iHashCode = (iHashCode * 31) + objArr[i7].hashCode();
        }
        return i5 + iHashCode;
    }

    final void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzhq.zza(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }
}
