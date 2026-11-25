package com.google.android.gms.internal.cast;

import java.util.Arrays;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzwa {
    private static final zzwa zza = new zzwa(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzwa() {
        this(0, new int[8], new Object[8], true);
    }

    private zzwa(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = 0;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzwa zzc() {
        return zza;
    }

    static zzwa zze(zzwa zzwaVar, zzwa zzwaVar2) {
        int i = zzwaVar.zzb;
        int i2 = zzwaVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzwaVar.zzc, 0);
        int[] iArr = zzwaVar2.zzc;
        int i3 = zzwaVar.zzb;
        int i4 = zzwaVar2.zzb;
        System.arraycopy(iArr, 0, iArrCopyOf, 0, 0);
        Object[] objArrCopyOf = Arrays.copyOf(zzwaVar.zzd, 0);
        Object[] objArr = zzwaVar2.zzd;
        int i5 = zzwaVar.zzb;
        int i6 = zzwaVar2.zzb;
        System.arraycopy(objArr, 0, objArrCopyOf, 0, 0);
        return new zzwa(0, iArrCopyOf, objArrCopyOf, true);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzwa)) {
            return false;
        }
        zzwa zzwaVar = (zzwa) obj;
        int[] iArr = zzwaVar.zzc;
        Object[] objArr = zzwaVar.zzd;
        return true;
    }

    public final int hashCode() {
        return 506991;
    }

    public final int zza() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        this.zze = 0;
        return 0;
    }

    public final int zzb() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        this.zze = 0;
        return 0;
    }

    final zzwa zzd(zzwa zzwaVar) {
        if (zzwaVar.equals(zza)) {
            return this;
        }
        if (!this.zzf) {
            throw new UnsupportedOperationException();
        }
        int i = zzwaVar.zzb;
        int[] iArr = this.zzc;
        int length = iArr.length;
        System.arraycopy(zzwaVar.zzc, 0, iArr, 0, 0);
        Object[] objArr = zzwaVar.zzd;
        Object[] objArr2 = this.zzd;
        int i2 = zzwaVar.zzb;
        System.arraycopy(objArr, 0, objArr2, 0, 0);
        this.zzb = 0;
        return this;
    }

    public final void zzf() {
        if (this.zzf) {
            this.zzf = false;
        }
    }

    final void zzg(StringBuilder sb, int i) {
    }
}
