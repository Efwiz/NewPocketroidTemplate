package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzsn extends zzsp {
    private final byte[] zza;
    private int zzb;
    private int zzc;
    private int zzd;
    private int zze;

    public zzsn(ByteBuffer byteBuffer, boolean z) {
        super(null);
        this.zza = byteBuffer.array();
        this.zzb = byteBuffer.arrayOffset() + byteBuffer.position();
        this.zzc = byteBuffer.arrayOffset() + byteBuffer.limit();
    }

    private final byte zzU() throws IOException {
        int i = this.zzb;
        if (i == this.zzc) {
            throw zzvk.zzj();
        }
        byte[] bArr = this.zza;
        this.zzb = i + 1;
        return bArr[i];
    }

    private final int zzV() throws IOException {
        zzad(4);
        return zzW();
    }

    private final int zzW() {
        int i = this.zzb;
        byte[] bArr = this.zza;
        this.zzb = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private final int zzX() throws IOException {
        int i;
        int i2 = this.zzb;
        int i3 = this.zzc;
        if (i3 == i2) {
            throw zzvk.zzj();
        }
        byte[] bArr = this.zza;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            this.zzb = i4;
            return b;
        }
        if (i3 - i4 < 9) {
            return (int) zzaa();
        }
        int i5 = i2 + 2;
        int i6 = (bArr[i4] << 7) ^ b;
        if (i6 < 0) {
            i = i6 ^ (-128);
        } else {
            int i7 = i2 + 3;
            int i8 = (bArr[i5] << 14) ^ i6;
            if (i8 >= 0) {
                i = i8 ^ 16256;
            } else {
                int i9 = i2 + 4;
                int i10 = i8 ^ (bArr[i7] << 21);
                if (i10 < 0) {
                    i = (-2080896) ^ i10;
                } else {
                    i7 = i2 + 5;
                    byte b2 = bArr[i9];
                    int i11 = (i10 ^ (b2 << 28)) ^ 266354560;
                    if (b2 < 0) {
                        i9 = i2 + 6;
                        if (bArr[i7] < 0) {
                            i7 = i2 + 7;
                            if (bArr[i9] < 0) {
                                i9 = i2 + 8;
                                if (bArr[i7] < 0) {
                                    i7 = i2 + 9;
                                    if (bArr[i9] < 0) {
                                        int i12 = i2 + 10;
                                        if (bArr[i7] < 0) {
                                            throw zzvk.zze();
                                        }
                                        i5 = i12;
                                        i = i11;
                                    }
                                }
                            }
                        }
                        i = i11;
                    }
                    i = i11;
                }
                i5 = i9;
            }
            i5 = i7;
        }
        this.zzb = i5;
        return i;
    }

    private final long zzY() throws IOException {
        zzad(8);
        return zzZ();
    }

    private final long zzZ() {
        int i = this.zzb;
        byte[] bArr = this.zza;
        this.zzb = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    private final long zzaa() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte bZzU = zzU();
            j |= ((long) (bZzU & 127)) << i;
            if ((bZzU & 128) == 0) {
                return j;
            }
        }
        throw zzvk.zze();
    }

    private final <T> T zzab(zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        int i = this.zze;
        this.zze = ((this.zzd >>> 3) << 3) | 4;
        try {
            T tZze = zzwxVar.zze();
            zzwxVar.zzh(tZze, this, zzujVar);
            zzwxVar.zzf(tZze);
            if (this.zzd != this.zze) {
                throw zzvk.zzg();
            }
            this.zze = i;
            return tZze;
        } catch (Throwable th) {
            this.zze = i;
            throw th;
        }
    }

    private final <T> T zzac(zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        int iZzX = zzX();
        zzad(iZzX);
        int i = this.zzc;
        int i2 = this.zzb + iZzX;
        this.zzc = i2;
        try {
            T tZze = zzwxVar.zze();
            zzwxVar.zzh(tZze, this, zzujVar);
            zzwxVar.zzf(tZze);
            if (this.zzb != i2) {
                throw zzvk.zzg();
            }
            this.zzc = i;
            return tZze;
        } catch (Throwable th) {
            this.zzc = i;
            throw th;
        }
    }

    private final void zzad(int i) throws IOException {
        if (i < 0 || i > this.zzc - this.zzb) {
            throw zzvk.zzj();
        }
    }

    private final void zzae(int i) throws IOException {
        if (this.zzb != i) {
            throw zzvk.zzj();
        }
    }

    private final void zzaf(int i) throws IOException {
        if ((this.zzd & 7) != i) {
            throw zzvk.zza();
        }
    }

    private final void zzag(int i) throws IOException {
        zzad(i);
        this.zzb += i;
    }

    private final void zzah(int i) throws IOException {
        zzad(i);
        if ((i & 3) != 0) {
            throw zzvk.zzg();
        }
    }

    private final void zzai(int i) throws IOException {
        zzad(i);
        if ((i & 7) != 0) {
            throw zzvk.zzg();
        }
    }

    private final boolean zzaj() {
        return this.zzb == this.zzc;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzA(List<Double> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzug)) {
            int i3 = this.zzd & 7;
            if (i3 == 1) {
                do {
                    list.add(Double.valueOf(zza()));
                    if (zzaj()) {
                        return;
                    } else {
                        i = this.zzb;
                    }
                } while (zzX() == this.zzd);
                this.zzb = i;
                return;
            }
            if (i3 != 2) {
                throw zzvk.zza();
            }
            int iZzX = zzX();
            zzai(iZzX);
            int i4 = this.zzb + iZzX;
            while (this.zzb < i4) {
                list.add(Double.valueOf(Double.longBitsToDouble(zzZ())));
            }
            return;
        }
        zzug zzugVar = (zzug) list;
        int i5 = this.zzd & 7;
        if (i5 == 1) {
            do {
                zzugVar.zze(zza());
                if (zzaj()) {
                    return;
                } else {
                    i2 = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i2;
            return;
        }
        if (i5 != 2) {
            throw zzvk.zza();
        }
        int iZzX2 = zzX();
        zzai(iZzX2);
        int i6 = this.zzb + iZzX2;
        while (this.zzb < i6) {
            zzugVar.zze(Double.longBitsToDouble(zzZ()));
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzB(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzva)) {
            int i3 = this.zzd & 7;
            if (i3 != 0) {
                if (i3 != 2) {
                    throw zzvk.zza();
                }
                int iZzX = this.zzb + zzX();
                while (this.zzb < iZzX) {
                    list.add(Integer.valueOf(zzX()));
                }
                return;
            }
            do {
                list.add(Integer.valueOf(zze()));
                if (zzaj()) {
                    return;
                } else {
                    i = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i;
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i4 = this.zzd & 7;
        if (i4 != 0) {
            if (i4 != 2) {
                throw zzvk.zza();
            }
            int iZzX2 = this.zzb + zzX();
            while (this.zzb < iZzX2) {
                zzvaVar.zzh(zzX());
            }
            return;
        }
        do {
            zzvaVar.zzh(zze());
            if (zzaj()) {
                return;
            } else {
                i2 = this.zzb;
            }
        } while (zzX() == this.zzd);
        this.zzb = i2;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzC(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzva)) {
            int i3 = this.zzd & 7;
            if (i3 == 2) {
                int iZzX = zzX();
                zzah(iZzX);
                int i4 = this.zzb + iZzX;
                while (this.zzb < i4) {
                    list.add(Integer.valueOf(zzW()));
                }
                return;
            }
            if (i3 != 5) {
                throw zzvk.zza();
            }
            do {
                list.add(Integer.valueOf(zzf()));
                if (zzaj()) {
                    return;
                } else {
                    i = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i;
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i5 = this.zzd & 7;
        if (i5 == 2) {
            int iZzX2 = zzX();
            zzah(iZzX2);
            int i6 = this.zzb + iZzX2;
            while (this.zzb < i6) {
                zzvaVar.zzh(zzW());
            }
            return;
        }
        if (i5 != 5) {
            throw zzvk.zza();
        }
        do {
            zzvaVar.zzh(zzf());
            if (zzaj()) {
                return;
            } else {
                i2 = this.zzb;
            }
        } while (zzX() == this.zzd);
        this.zzb = i2;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzD(List<Long> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzvz)) {
            int i3 = this.zzd & 7;
            if (i3 == 1) {
                do {
                    list.add(Long.valueOf(zzk()));
                    if (zzaj()) {
                        return;
                    } else {
                        i = this.zzb;
                    }
                } while (zzX() == this.zzd);
                this.zzb = i;
                return;
            }
            if (i3 != 2) {
                throw zzvk.zza();
            }
            int iZzX = zzX();
            zzai(iZzX);
            int i4 = this.zzb + iZzX;
            while (this.zzb < i4) {
                list.add(Long.valueOf(zzZ()));
            }
            return;
        }
        zzvz zzvzVar = (zzvz) list;
        int i5 = this.zzd & 7;
        if (i5 == 1) {
            do {
                zzvzVar.zzf(zzk());
                if (zzaj()) {
                    return;
                } else {
                    i2 = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i2;
            return;
        }
        if (i5 != 2) {
            throw zzvk.zza();
        }
        int iZzX2 = zzX();
        zzai(iZzX2);
        int i6 = this.zzb + iZzX2;
        while (this.zzb < i6) {
            zzvzVar.zzf(zzZ());
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzE(List<Float> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzuq)) {
            int i3 = this.zzd & 7;
            if (i3 == 2) {
                int iZzX = zzX();
                zzah(iZzX);
                int i4 = this.zzb + iZzX;
                while (this.zzb < i4) {
                    list.add(Float.valueOf(Float.intBitsToFloat(zzW())));
                }
                return;
            }
            if (i3 != 5) {
                throw zzvk.zza();
            }
            do {
                list.add(Float.valueOf(zzb()));
                if (zzaj()) {
                    return;
                } else {
                    i = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i;
            return;
        }
        zzuq zzuqVar = (zzuq) list;
        int i5 = this.zzd & 7;
        if (i5 == 2) {
            int iZzX2 = zzX();
            zzah(iZzX2);
            int i6 = this.zzb + iZzX2;
            while (this.zzb < i6) {
                zzuqVar.zze(Float.intBitsToFloat(zzW()));
            }
            return;
        }
        if (i5 != 5) {
            throw zzvk.zza();
        }
        do {
            zzuqVar.zze(zzb());
            if (zzaj()) {
                return;
            } else {
                i2 = this.zzb;
            }
        } while (zzX() == this.zzd);
        this.zzb = i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> void zzF(List<T> list, zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        int i;
        int i2 = this.zzd;
        if ((i2 & 7) != 3) {
            throw zzvk.zza();
        }
        do {
            list.add(zzab(zzwxVar, zzujVar));
            if (zzaj()) {
                return;
            } else {
                i = this.zzb;
            }
        } while (zzX() == i2);
        this.zzb = i;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzG(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzva)) {
            int i3 = this.zzd & 7;
            if (i3 == 0) {
                do {
                    list.add(Integer.valueOf(zzg()));
                    if (zzaj()) {
                        return;
                    } else {
                        i = this.zzb;
                    }
                } while (zzX() == this.zzd);
                this.zzb = i;
                return;
            }
            if (i3 != 2) {
                throw zzvk.zza();
            }
            int iZzX = this.zzb + zzX();
            while (this.zzb < iZzX) {
                list.add(Integer.valueOf(zzX()));
            }
            zzae(iZzX);
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i4 = this.zzd & 7;
        if (i4 == 0) {
            do {
                zzvaVar.zzh(zzg());
                if (zzaj()) {
                    return;
                } else {
                    i2 = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i2;
            return;
        }
        if (i4 != 2) {
            throw zzvk.zza();
        }
        int iZzX2 = this.zzb + zzX();
        while (this.zzb < iZzX2) {
            zzvaVar.zzh(zzX());
        }
        zzae(iZzX2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzH(List<Long> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzvz)) {
            int i3 = this.zzd & 7;
            if (i3 == 0) {
                do {
                    list.add(Long.valueOf(zzl()));
                    if (zzaj()) {
                        return;
                    } else {
                        i = this.zzb;
                    }
                } while (zzX() == this.zzd);
                this.zzb = i;
                return;
            }
            if (i3 != 2) {
                throw zzvk.zza();
            }
            int iZzX = this.zzb + zzX();
            while (this.zzb < iZzX) {
                list.add(Long.valueOf(zzp()));
            }
            zzae(iZzX);
            return;
        }
        zzvz zzvzVar = (zzvz) list;
        int i4 = this.zzd & 7;
        if (i4 == 0) {
            do {
                zzvzVar.zzf(zzl());
                if (zzaj()) {
                    return;
                } else {
                    i2 = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i2;
            return;
        }
        if (i4 != 2) {
            throw zzvk.zza();
        }
        int iZzX2 = this.zzb + zzX();
        while (this.zzb < iZzX2) {
            zzvzVar.zzf(zzp());
        }
        zzae(iZzX2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> void zzI(List<T> list, zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        int i;
        int i2 = this.zzd;
        if ((i2 & 7) != 2) {
            throw zzvk.zza();
        }
        do {
            list.add(zzac(zzwxVar, zzujVar));
            if (zzaj()) {
                return;
            } else {
                i = this.zzb;
            }
        } while (zzX() == i2);
        this.zzb = i;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzJ(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzva)) {
            int i3 = this.zzd & 7;
            if (i3 == 2) {
                int iZzX = zzX();
                zzah(iZzX);
                int i4 = this.zzb + iZzX;
                while (this.zzb < i4) {
                    list.add(Integer.valueOf(zzW()));
                }
                return;
            }
            if (i3 != 5) {
                throw zzvk.zza();
            }
            do {
                list.add(Integer.valueOf(zzh()));
                if (zzaj()) {
                    return;
                } else {
                    i = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i;
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i5 = this.zzd & 7;
        if (i5 == 2) {
            int iZzX2 = zzX();
            zzah(iZzX2);
            int i6 = this.zzb + iZzX2;
            while (this.zzb < i6) {
                zzvaVar.zzh(zzW());
            }
            return;
        }
        if (i5 != 5) {
            throw zzvk.zza();
        }
        do {
            zzvaVar.zzh(zzh());
            if (zzaj()) {
                return;
            } else {
                i2 = this.zzb;
            }
        } while (zzX() == this.zzd);
        this.zzb = i2;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzK(List<Long> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzvz)) {
            int i3 = this.zzd & 7;
            if (i3 == 1) {
                do {
                    list.add(Long.valueOf(zzm()));
                    if (zzaj()) {
                        return;
                    } else {
                        i = this.zzb;
                    }
                } while (zzX() == this.zzd);
                this.zzb = i;
                return;
            }
            if (i3 != 2) {
                throw zzvk.zza();
            }
            int iZzX = zzX();
            zzai(iZzX);
            int i4 = this.zzb + iZzX;
            while (this.zzb < i4) {
                list.add(Long.valueOf(zzZ()));
            }
            return;
        }
        zzvz zzvzVar = (zzvz) list;
        int i5 = this.zzd & 7;
        if (i5 == 1) {
            do {
                zzvzVar.zzf(zzm());
                if (zzaj()) {
                    return;
                } else {
                    i2 = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i2;
            return;
        }
        if (i5 != 2) {
            throw zzvk.zza();
        }
        int iZzX2 = zzX();
        zzai(iZzX2);
        int i6 = this.zzb + iZzX2;
        while (this.zzb < i6) {
            zzvzVar.zzf(zzZ());
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzL(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzva)) {
            int i3 = this.zzd & 7;
            if (i3 != 0) {
                if (i3 != 2) {
                    throw zzvk.zza();
                }
                int iZzX = this.zzb + zzX();
                while (this.zzb < iZzX) {
                    list.add(Integer.valueOf(zztj.zzs(zzX())));
                }
                return;
            }
            do {
                list.add(Integer.valueOf(zzi()));
                if (zzaj()) {
                    return;
                } else {
                    i = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i;
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i4 = this.zzd & 7;
        if (i4 != 0) {
            if (i4 != 2) {
                throw zzvk.zza();
            }
            int iZzX2 = this.zzb + zzX();
            while (this.zzb < iZzX2) {
                zzvaVar.zzh(zztj.zzs(zzX()));
            }
            return;
        }
        do {
            zzvaVar.zzh(zzi());
            if (zzaj()) {
                return;
            } else {
                i2 = this.zzb;
            }
        } while (zzX() == this.zzd);
        this.zzb = i2;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzM(List<Long> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzvz)) {
            int i3 = this.zzd & 7;
            if (i3 != 0) {
                if (i3 != 2) {
                    throw zzvk.zza();
                }
                int iZzX = this.zzb + zzX();
                while (this.zzb < iZzX) {
                    list.add(Long.valueOf(zztj.zzt(zzp())));
                }
                return;
            }
            do {
                list.add(Long.valueOf(zzn()));
                if (zzaj()) {
                    return;
                } else {
                    i = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i;
            return;
        }
        zzvz zzvzVar = (zzvz) list;
        int i4 = this.zzd & 7;
        if (i4 != 0) {
            if (i4 != 2) {
                throw zzvk.zza();
            }
            int iZzX2 = this.zzb + zzX();
            while (this.zzb < iZzX2) {
                zzvzVar.zzf(zztj.zzt(zzp()));
            }
            return;
        }
        do {
            zzvzVar.zzf(zzn());
            if (zzaj()) {
                return;
            } else {
                i2 = this.zzb;
            }
        } while (zzX() == this.zzd);
        this.zzb = i2;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzN(List<String> list) throws IOException {
        zzO(list, false);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzP(List<String> list) throws IOException {
        zzO(list, true);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzQ(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzva)) {
            int i3 = this.zzd & 7;
            if (i3 != 0) {
                if (i3 != 2) {
                    throw zzvk.zza();
                }
                int iZzX = this.zzb + zzX();
                while (this.zzb < iZzX) {
                    list.add(Integer.valueOf(zzX()));
                }
                return;
            }
            do {
                list.add(Integer.valueOf(zzj()));
                if (zzaj()) {
                    return;
                } else {
                    i = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i;
            return;
        }
        zzva zzvaVar = (zzva) list;
        int i4 = this.zzd & 7;
        if (i4 != 0) {
            if (i4 != 2) {
                throw zzvk.zza();
            }
            int iZzX2 = this.zzb + zzX();
            while (this.zzb < iZzX2) {
                zzvaVar.zzh(zzX());
            }
            return;
        }
        do {
            zzvaVar.zzh(zzj());
            if (zzaj()) {
                return;
            } else {
                i2 = this.zzb;
            }
        } while (zzX() == this.zzd);
        this.zzb = i2;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzR(List<Long> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzvz)) {
            int i3 = this.zzd & 7;
            if (i3 == 0) {
                do {
                    list.add(Long.valueOf(zzo()));
                    if (zzaj()) {
                        return;
                    } else {
                        i = this.zzb;
                    }
                } while (zzX() == this.zzd);
                this.zzb = i;
                return;
            }
            if (i3 != 2) {
                throw zzvk.zza();
            }
            int iZzX = this.zzb + zzX();
            while (this.zzb < iZzX) {
                list.add(Long.valueOf(zzp()));
            }
            zzae(iZzX);
            return;
        }
        zzvz zzvzVar = (zzvz) list;
        int i4 = this.zzd & 7;
        if (i4 == 0) {
            do {
                zzvzVar.zzf(zzo());
                if (zzaj()) {
                    return;
                } else {
                    i2 = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i2;
            return;
        }
        if (i4 != 2) {
            throw zzvk.zza();
        }
        int iZzX2 = this.zzb + zzX();
        while (this.zzb < iZzX2) {
            zzvzVar.zzf(zzp());
        }
        zzae(iZzX2);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final boolean zzS() throws IOException {
        zzaf(0);
        return zzX() != 0;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final double zza() throws IOException {
        zzaf(1);
        return Double.longBitsToDouble(zzY());
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final float zzb() throws IOException {
        zzaf(5);
        return Float.intBitsToFloat(zzV());
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzc() throws IOException {
        if (zzaj()) {
            return Integer.MAX_VALUE;
        }
        int iZzX = zzX();
        this.zzd = iZzX;
        if (iZzX == this.zze) {
            return Integer.MAX_VALUE;
        }
        return iZzX >>> 3;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzd() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zze() throws IOException {
        zzaf(0);
        return zzX();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzf() throws IOException {
        zzaf(5);
        return zzV();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzg() throws IOException {
        zzaf(0);
        return zzX();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzh() throws IOException {
        zzaf(5);
        return zzV();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzi() throws IOException {
        zzaf(0);
        return zztj.zzs(zzX());
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final int zzj() throws IOException {
        zzaf(0);
        return zzX();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final long zzk() throws IOException {
        zzaf(1);
        return zzY();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final long zzl() throws IOException {
        zzaf(0);
        return zzp();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final long zzm() throws IOException {
        zzaf(1);
        return zzY();
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final long zzn() throws IOException {
        zzaf(0);
        return zztj.zzt(zzp());
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final long zzo() throws IOException {
        zzaf(0);
        return zzp();
    }

    public final long zzp() throws IOException {
        long j;
        long j2;
        int i = this.zzb;
        int i2 = this.zzc;
        if (i2 == i) {
            throw zzvk.zzj();
        }
        byte[] bArr = this.zza;
        int i3 = i + 1;
        byte b = bArr[i];
        if (b >= 0) {
            this.zzb = i3;
            return b;
        }
        if (i2 - i3 < 9) {
            return zzaa();
        }
        int i4 = i + 2;
        int i5 = (bArr[i3] << 7) ^ b;
        if (i5 < 0) {
            j = i5 ^ (-128);
        } else {
            int i6 = i + 3;
            int i7 = (bArr[i4] << 14) ^ i5;
            if (i7 >= 0) {
                j = i7 ^ 16256;
            } else {
                int i8 = i + 4;
                int i9 = i7 ^ (bArr[i6] << 21);
                if (i9 < 0) {
                    long j3 = (-2080896) ^ i9;
                    i4 = i8;
                    j = j3;
                } else {
                    i6 = i + 5;
                    long j4 = ((long) i9) ^ (((long) bArr[i8]) << 28);
                    if (j4 >= 0) {
                        j = j4 ^ 266354560;
                    } else {
                        i4 = i + 6;
                        long j5 = (((long) bArr[i6]) << 35) ^ j4;
                        if (j5 < 0) {
                            j2 = -34093383808L;
                        } else {
                            int i10 = i + 7;
                            long j6 = j5 ^ (((long) bArr[i4]) << 42);
                            if (j6 >= 0) {
                                j = j6 ^ 4363953127296L;
                            } else {
                                i4 = i + 8;
                                j5 = j6 ^ (((long) bArr[i10]) << 49);
                                if (j5 < 0) {
                                    j2 = -558586000294016L;
                                } else {
                                    i10 = i + 9;
                                    long j7 = (j5 ^ (((long) bArr[i4]) << 56)) ^ 71499008037633920L;
                                    if (j7 < 0) {
                                        i4 = i + 10;
                                        if (bArr[i10] < 0) {
                                            throw zzvk.zze();
                                        }
                                        j = j7;
                                    } else {
                                        j = j7;
                                    }
                                }
                            }
                            i4 = i10;
                        }
                        j = j5 ^ j2;
                    }
                }
            }
            i4 = i6;
        }
        this.zzb = i4;
        return j;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final zztd zzq() throws IOException {
        zzaf(2);
        int iZzX = zzX();
        if (iZzX == 0) {
            return zztd.zzb;
        }
        zzad(iZzX);
        zztd zztdVarZzq = zztd.zzq(this.zza, this.zzb, iZzX);
        this.zzb += iZzX;
        return zztdVarZzq;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> T zzr(Class<T> cls, zzuj zzujVar) throws IOException {
        zzaf(3);
        return (T) zzab(zzwt.zza().zzb(cls), zzujVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> T zzs(zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        zzaf(3);
        return (T) zzab(zzwxVar, zzujVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> T zzt(Class<T> cls, zzuj zzujVar) throws IOException {
        zzaf(2);
        return (T) zzac(zzwt.zza().zzb(cls), zzujVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final <T> T zzu(zzwx<T> zzwxVar, zzuj zzujVar) throws IOException {
        zzaf(2);
        return (T) zzac(zzwxVar, zzujVar);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final String zzv() throws IOException {
        return zzw(false);
    }

    public final String zzw(boolean z) throws IOException {
        zzaf(2);
        int iZzX = zzX();
        if (iZzX == 0) {
            return "";
        }
        zzad(iZzX);
        if (z) {
            byte[] bArr = this.zza;
            int i = this.zzb;
            if (!zzyd.zzf(bArr, i, i + iZzX)) {
                throw zzvk.zzd();
            }
        }
        String str = new String(this.zza, this.zzb, iZzX, zzvi.zza);
        this.zzb += iZzX;
        return str;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final String zzx() throws IOException {
        return zzw(true);
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzy(List<Boolean> list) throws IOException {
        int i;
        int i2;
        if (!(list instanceof zzsr)) {
            int i3 = this.zzd & 7;
            if (i3 != 0) {
                if (i3 != 2) {
                    throw zzvk.zza();
                }
                int iZzX = this.zzb + zzX();
                while (this.zzb < iZzX) {
                    list.add(Boolean.valueOf(zzX() != 0));
                }
                zzae(iZzX);
                return;
            }
            do {
                list.add(Boolean.valueOf(zzS()));
                if (zzaj()) {
                    return;
                } else {
                    i = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i;
            return;
        }
        zzsr zzsrVar = (zzsr) list;
        int i4 = this.zzd & 7;
        if (i4 != 0) {
            if (i4 != 2) {
                throw zzvk.zza();
            }
            int iZzX2 = this.zzb + zzX();
            while (this.zzb < iZzX2) {
                zzsrVar.zze(zzX() != 0);
            }
            zzae(iZzX2);
            return;
        }
        do {
            zzsrVar.zze(zzS());
            if (zzaj()) {
                return;
            } else {
                i2 = this.zzb;
            }
        } while (zzX() == this.zzd);
        this.zzb = i2;
    }

    @Override // com.google.android.gms.internal.gtm.zzww
    public final void zzz(List<zztd> list) throws IOException {
        int i;
        if ((this.zzd & 7) != 2) {
            throw zzvk.zza();
        }
        do {
            list.add(zzq());
            if (zzaj()) {
                return;
            } else {
                i = this.zzb;
            }
        } while (zzX() == this.zzd);
        this.zzb = i;
    }

    public final void zzO(List<String> list, boolean z) throws IOException {
        int i;
        int i2;
        if ((this.zzd & 7) != 2) {
            throw zzvk.zza();
        }
        if (!(list instanceof zzvs) || z) {
            do {
                list.add(zzw(z));
                if (zzaj()) {
                    return;
                } else {
                    i = this.zzb;
                }
            } while (zzX() == this.zzd);
            this.zzb = i;
            return;
        }
        zzvs zzvsVar = (zzvs) list;
        do {
            zzvsVar.zzi(zzq());
            if (zzaj()) {
                return;
            } else {
                i2 = this.zzb;
            }
        } while (zzX() == this.zzd);
        this.zzb = i2;
    }

    /* JADX WARN: Code duplicated, block: B:44:0x0076  */
    /* JADX WARN: Code duplicated, block: B:46:0x007c A[LOOP:2: B:43:0x0074->B:46:0x007c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:57:0x007f A[SYNTHETIC] */
    @Override // com.google.android.gms.internal.gtm.zzww
    public final boolean zzT() throws IOException {
        int i;
        int i2;
        if (zzaj() || (i = this.zzd) == (i2 = this.zze)) {
            return false;
        }
        int i3 = i & 7;
        if (i3 != 0) {
            if (i3 == 1) {
                zzag(8);
                return true;
            }
            if (i3 == 2) {
                zzag(zzX());
                return true;
            }
            if (i3 != 3) {
                if (i3 != 5) {
                    throw zzvk.zza();
                }
                zzag(4);
                return true;
            }
            this.zze = ((i >>> 3) << 3) | 4;
            while (zzc() != Integer.MAX_VALUE && zzT()) {
            }
            if (this.zzd != this.zze) {
                throw zzvk.zzg();
            }
            this.zze = i2;
            return true;
        }
        int i4 = this.zzc;
        int i5 = this.zzb;
        if (i4 - i5 < 10) {
            for (int i6 = 0; i6 < 10; i6++) {
                if (zzU() < 0) {
                }
            }
            throw zzvk.zze();
        }
        byte[] bArr = this.zza;
        int i7 = 0;
        while (i7 < 10) {
            int i8 = i5 + 1;
            if (bArr[i5] >= 0) {
                this.zzb = i8;
            } else {
                i7++;
                i5 = i8;
            }
        }
        while (i6 < 10) {
            if (zzU() < 0) {
            }
        }
        throw zzvk.zze();
        return true;
    }
}
