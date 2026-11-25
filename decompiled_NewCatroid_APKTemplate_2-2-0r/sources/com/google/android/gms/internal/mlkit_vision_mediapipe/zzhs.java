package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhs {
    private static final zzhs zza = new zzhs(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    public static zzhs zza() {
        return zza;
    }

    static zzhs zzb() {
        return new zzhs();
    }

    static zzhs zza(zzhs zzhsVar, zzhs zzhsVar2) {
        int i = zzhsVar.zzb + zzhsVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzhsVar.zzc, i);
        System.arraycopy(zzhsVar2.zzc, 0, iArrCopyOf, zzhsVar.zzb, zzhsVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzhsVar.zzd, i);
        System.arraycopy(zzhsVar2.zzd, 0, objArrCopyOf, zzhsVar.zzb, zzhsVar2.zzb);
        return new zzhs(i, iArrCopyOf, objArrCopyOf, true);
    }

    private zzhs() {
        this(0, new int[8], new Object[8], true);
    }

    private zzhs(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public final void zzc() {
        this.zzf = false;
    }

    final void zza(zzim zzimVar) throws IOException {
        if (zzimVar.zza() == zzil.zzb) {
            for (int i = this.zzb - 1; i >= 0; i--) {
                zzimVar.zza(this.zzc[i] >>> 3, this.zzd[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzimVar.zza(this.zzc[i2] >>> 3, this.zzd[i2]);
        }
    }

    public final void zzb(zzim zzimVar) throws IOException {
        if (this.zzb == 0) {
            return;
        }
        if (zzimVar.zza() == zzil.zza) {
            for (int i = 0; i < this.zzb; i++) {
                zza(this.zzc[i], this.zzd[i], zzimVar);
            }
            return;
        }
        for (int i2 = this.zzb - 1; i2 >= 0; i2--) {
            zza(this.zzc[i2], this.zzd[i2], zzimVar);
        }
    }

    private static void zza(int i, Object obj, zzim zzimVar) throws IOException {
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            zzimVar.zza(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 1) {
            zzimVar.zzd(i2, ((Long) obj).longValue());
            return;
        }
        if (i3 == 2) {
            zzimVar.zza(i2, (zzdu) obj);
            return;
        }
        if (i3 != 3) {
            if (i3 == 5) {
                zzimVar.zzd(i2, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzfi.zzd());
        }
        if (zzimVar.zza() == zzil.zza) {
            zzimVar.zza(i2);
            ((zzhs) obj).zzb(zzimVar);
            zzimVar.zzb(i2);
        } else {
            zzimVar.zzb(i2);
            ((zzhs) obj).zzb(zzimVar);
            zzimVar.zza(i2);
        }
    }

    public final int zzd() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzd = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            iZzd += zzel.zzd(this.zzc[i2] >>> 3, (zzdu) this.zzd[i2]);
        }
        this.zze = iZzd;
        return iZzd;
    }

    public final int zze() {
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
                iZze = zzel.zze(i5, ((Long) this.zzd[i3]).longValue());
            } else if (i6 == 1) {
                iZze = zzel.zzg(i5, ((Long) this.zzd[i3]).longValue());
            } else if (i6 == 2) {
                iZze = zzel.zzc(i5, (zzdu) this.zzd[i3]);
            } else if (i6 == 3) {
                iZze = (zzel.zze(i5) << 1) + ((zzhs) this.zzd[i3]).zze();
            } else if (i6 == 5) {
                iZze = zzel.zzi(i5, ((Integer) this.zzd[i3]).intValue());
            } else {
                throw new IllegalStateException(zzfi.zzd());
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
        if (obj == null || !(obj instanceof zzhs)) {
            return false;
        }
        zzhs zzhsVar = (zzhs) obj;
        int i = this.zzb;
        if (i == zzhsVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzhsVar.zzc;
            for (int i2 = 0; i2 < i; i2++) {
                if (iArr[i2] == iArr2[i2]) {
                }
            }
            Object[] objArr = this.zzd;
            Object[] objArr2 = zzhsVar.zzd;
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
            zzgj.zza(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    final void zza(int i, Object obj) {
        if (!this.zzf) {
            throw new UnsupportedOperationException();
        }
        int i2 = this.zzb;
        int[] iArr = this.zzc;
        if (i2 == iArr.length) {
            int i3 = i2 + (i2 < 4 ? 8 : i2 >> 1);
            this.zzc = Arrays.copyOf(iArr, i3);
            this.zzd = Arrays.copyOf(this.zzd, i3);
        }
        int[] iArr2 = this.zzc;
        int i4 = this.zzb;
        iArr2[i4] = i;
        this.zzd[i4] = obj;
        this.zzb = i4 + 1;
    }
}
