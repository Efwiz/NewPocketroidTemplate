package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaqu {
    private static final zzaqu zza = new zzaqu(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzaqu() {
        this(0, new int[8], new Object[8], true);
    }

    private zzaqu(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzaqu zzc() {
        return zza;
    }

    static zzaqu zzd(zzaqu zzaquVar, zzaqu zzaquVar2) {
        int i = zzaquVar.zzb + zzaquVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzaquVar.zzc, i);
        System.arraycopy(zzaquVar2.zzc, 0, iArrCopyOf, zzaquVar.zzb, zzaquVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzaquVar.zzd, i);
        System.arraycopy(zzaquVar2.zzd, 0, objArrCopyOf, zzaquVar.zzb, zzaquVar2.zzb);
        return new zzaqu(i, iArrCopyOf, objArrCopyOf, true);
    }

    static zzaqu zze() {
        return new zzaqu(0, new int[8], new Object[8], true);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzaqu)) {
            return false;
        }
        zzaqu zzaquVar = (zzaqu) obj;
        int i = this.zzb;
        if (i == zzaquVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzaquVar.zzc;
            for (int i2 = 0; i2 < i; i2++) {
                if (iArr[i2] == iArr2[i2]) {
                }
            }
            Object[] objArr = this.zzd;
            Object[] objArr2 = zzaquVar.zzd;
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

    public final int zza() {
        int iZzM;
        int iZzN;
        int iZzM2;
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzM3 = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2];
            int i4 = i3 >>> 3;
            int i5 = i3 & 7;
            if (i5 != 0) {
                if (i5 != 1) {
                    if (i5 == 2) {
                        zzamk zzamkVar = (zzamk) this.zzd[i2];
                        int iZzM4 = zzamx.zzM(i4 << 3);
                        int iZzd = zzamkVar.zzd();
                        iZzM3 += iZzM4 + zzamx.zzM(iZzd) + iZzd;
                    } else if (i5 == 3) {
                        int iZzL = zzamx.zzL(i4);
                        iZzM = iZzL + iZzL;
                        iZzN = ((zzaqu) this.zzd[i2]).zza();
                    } else {
                        if (i5 != 5) {
                            throw new IllegalStateException(zzaoe.zza());
                        }
                        ((Integer) this.zzd[i2]).intValue();
                        iZzM2 = zzamx.zzM(i4 << 3) + 4;
                    }
                } else {
                    ((Long) this.zzd[i2]).longValue();
                    iZzM2 = zzamx.zzM(i4 << 3) + 8;
                }
                iZzM3 += iZzM2;
            } else {
                long jLongValue = ((Long) this.zzd[i2]).longValue();
                iZzM = zzamx.zzM(i4 << 3);
                iZzN = zzamx.zzN(jLongValue);
            }
            iZzM2 = iZzM + iZzN;
            iZzM3 += iZzM2;
        }
        this.zze = iZzM3;
        return iZzM3;
    }

    public final int zzb() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzM = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2];
            zzamk zzamkVar = (zzamk) this.zzd[i2];
            int iZzM2 = zzamx.zzM(8);
            int iZzd = zzamkVar.zzd();
            iZzM += iZzM2 + iZzM2 + zzamx.zzM(16) + zzamx.zzM(i3 >>> 3) + zzamx.zzM(24) + zzamx.zzM(iZzd) + iZzd;
        }
        this.zze = iZzM;
        return iZzM;
    }

    public final void zzf() {
        this.zzf = false;
    }

    final void zzg(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzaph.zzb(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    final void zzh(int i, Object obj) {
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

    final void zzi(zzarl zzarlVar) throws IOException {
        for (int i = 0; i < this.zzb; i++) {
            zzarlVar.zzx(this.zzc[i] >>> 3, this.zzd[i]);
        }
    }

    public final void zzj(zzarl zzarlVar) throws IOException {
        if (this.zzb != 0) {
            for (int i = 0; i < this.zzb; i++) {
                int i2 = this.zzc[i];
                Object obj = this.zzd[i];
                int i3 = i2 >>> 3;
                int i4 = i2 & 7;
                if (i4 == 0) {
                    zzarlVar.zzt(i3, ((Long) obj).longValue());
                } else if (i4 == 1) {
                    zzarlVar.zzm(i3, ((Long) obj).longValue());
                } else if (i4 == 2) {
                    zzarlVar.zzd(i3, (zzamk) obj);
                } else if (i4 == 3) {
                    zzarlVar.zzG(i3);
                    ((zzaqu) obj).zzj(zzarlVar);
                    zzarlVar.zzh(i3);
                } else {
                    if (i4 != 5) {
                        throw new RuntimeException(zzaoe.zza());
                    }
                    zzarlVar.zzk(i3, ((Integer) obj).intValue());
                }
            }
        }
    }
}
