package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzxp {
    private static final zzxp zza = new zzxp(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzxp() {
        this(0, new int[8], new Object[8], true);
    }

    private zzxp(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzxp zzc() {
        return zza;
    }

    static zzxp zzd(zzxp zzxpVar, zzxp zzxpVar2) {
        int i = zzxpVar.zzb + zzxpVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzxpVar.zzc, i);
        System.arraycopy(zzxpVar2.zzc, 0, iArrCopyOf, zzxpVar.zzb, zzxpVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzxpVar.zzd, i);
        System.arraycopy(zzxpVar2.zzd, 0, objArrCopyOf, zzxpVar.zzb, zzxpVar2.zzb);
        return new zzxp(i, iArrCopyOf, objArrCopyOf, true);
    }

    static zzxp zze() {
        return new zzxp(0, new int[8], new Object[8], true);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzxp)) {
            return false;
        }
        zzxp zzxpVar = (zzxp) obj;
        int i = this.zzb;
        if (i == zzxpVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzxpVar.zzc;
            for (int i2 = 0; i2 < i; i2++) {
                if (iArr[i2] == iArr2[i2]) {
                }
            }
            Object[] objArr = this.zzd;
            Object[] objArr2 = zzxpVar.zzd;
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
        int iZzD;
        int iZzE;
        int iZzD2;
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzD3 = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2];
            int i4 = i3 >>> 3;
            int i5 = i3 & 7;
            if (i5 != 0) {
                if (i5 != 1) {
                    if (i5 == 2) {
                        zztd zztdVar = (zztd) this.zzd[i2];
                        int iZzD4 = zzto.zzD(i4 << 3);
                        int iZzd = zztdVar.zzd();
                        iZzD3 += iZzD4 + zzto.zzD(iZzd) + iZzd;
                    } else if (i5 == 3) {
                        int iZzC = zzto.zzC(i4);
                        iZzD = iZzC + iZzC;
                        iZzE = ((zzxp) this.zzd[i2]).zza();
                    } else {
                        if (i5 != 5) {
                            throw new IllegalStateException(zzvk.zza());
                        }
                        ((Integer) this.zzd[i2]).intValue();
                        iZzD2 = zzto.zzD(i4 << 3) + 4;
                    }
                } else {
                    ((Long) this.zzd[i2]).longValue();
                    iZzD2 = zzto.zzD(i4 << 3) + 8;
                }
                iZzD3 += iZzD2;
            } else {
                long jLongValue = ((Long) this.zzd[i2]).longValue();
                iZzD = zzto.zzD(i4 << 3);
                iZzE = zzto.zzE(jLongValue);
            }
            iZzD2 = iZzD + iZzE;
            iZzD3 += iZzD2;
        }
        this.zze = iZzD3;
        return iZzD3;
    }

    public final int zzb() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzD = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2];
            zztd zztdVar = (zztd) this.zzd[i2];
            int iZzD2 = zzto.zzD(8);
            int iZzd = zztdVar.zzd();
            iZzD += iZzD2 + iZzD2 + zzto.zzD(16) + zzto.zzD(i3 >>> 3) + zzto.zzD(24) + zzto.zzD(iZzd) + iZzd;
        }
        this.zze = iZzD;
        return iZzD;
    }

    public final void zzf() {
        this.zzf = false;
    }

    final void zzg(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzwm.zzb(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
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

    final void zzi(zztp zztpVar) throws IOException {
        for (int i = 0; i < this.zzb; i++) {
            zztpVar.zzw(this.zzc[i] >>> 3, this.zzd[i]);
        }
    }

    public final void zzj(zztp zztpVar) throws IOException {
        if (this.zzb != 0) {
            for (int i = 0; i < this.zzb; i++) {
                int i2 = this.zzc[i];
                Object obj = this.zzd[i];
                int i3 = i2 >>> 3;
                int i4 = i2 & 7;
                if (i4 == 0) {
                    zztpVar.zzt(i3, ((Long) obj).longValue());
                } else if (i4 == 1) {
                    zztpVar.zzm(i3, ((Long) obj).longValue());
                } else if (i4 == 2) {
                    zztpVar.zzd(i3, (zztd) obj);
                } else if (i4 == 3) {
                    zztpVar.zzF(i3);
                    ((zzxp) obj).zzj(zztpVar);
                    zztpVar.zzh(i3);
                } else {
                    if (i4 != 5) {
                        throw new RuntimeException(zzvk.zza());
                    }
                    zztpVar.zzk(i3, ((Integer) obj).intValue());
                }
            }
        }
    }
}
