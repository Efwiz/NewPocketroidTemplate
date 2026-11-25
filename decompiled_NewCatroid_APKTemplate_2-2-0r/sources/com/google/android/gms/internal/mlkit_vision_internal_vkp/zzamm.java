package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzamm extends zzamq {
    private final byte[] zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private int zzi;
    private int zzj;

    /* synthetic */ zzamm(byte[] bArr, int i, int i2, boolean z, zzaml zzamlVar) {
        super(null);
        this.zzj = Integer.MAX_VALUE;
        this.zze = bArr;
        this.zzf = 0;
        this.zzh = 0;
    }

    private final void zzI() {
        int i = this.zzf + this.zzg;
        this.zzf = i;
        int i2 = this.zzj;
        if (i <= i2) {
            this.zzg = 0;
            return;
        }
        int i3 = i - i2;
        this.zzg = i3;
        this.zzf = i - i3;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final void zzA(int i) {
        this.zzj = i;
        zzI();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final boolean zzC() throws IOException {
        return this.zzh == this.zzf;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final boolean zzD() throws IOException {
        return zzr() != 0;
    }

    public final byte zza() throws IOException {
        int i = this.zzh;
        if (i == this.zzf) {
            throw zzaoe.zzj();
        }
        byte[] bArr = this.zze;
        this.zzh = i + 1;
        return bArr[i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final double zzb() throws IOException {
        return Double.longBitsToDouble(zzq());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final float zzc() throws IOException {
        return Float.intBitsToFloat(zzi());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final int zzd() {
        return this.zzh;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final int zze(int i) throws zzaoe {
        if (i < 0) {
            throw zzaoe.zzf();
        }
        int i2 = i + this.zzh;
        if (i2 < 0) {
            throw zzaoe.zzg();
        }
        int i3 = this.zzj;
        if (i2 > i3) {
            throw zzaoe.zzj();
        }
        this.zzj = i2;
        zzI();
        return i3;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final int zzf() throws IOException {
        return zzj();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final int zzg() throws IOException {
        return zzi();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final int zzh() throws IOException {
        return zzj();
    }

    public final int zzi() throws IOException {
        int i = this.zzh;
        if (this.zzf - i < 4) {
            throw zzaoe.zzj();
        }
        byte[] bArr = this.zze;
        this.zzh = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final int zzk() throws IOException {
        return zzi();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final int zzl() throws IOException {
        return zzF(zzj());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final int zzm() throws IOException {
        if (zzC()) {
            this.zzi = 0;
            return 0;
        }
        int iZzj = zzj();
        this.zzi = iZzj;
        if ((iZzj >>> 3) != 0) {
            return iZzj;
        }
        throw zzaoe.zzc();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final int zzn() throws IOException {
        return zzj();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final long zzo() throws IOException {
        return zzq();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final long zzp() throws IOException {
        return zzr();
    }

    public final long zzq() throws IOException {
        int i = this.zzh;
        if (this.zzf - i < 8) {
            throw zzaoe.zzj();
        }
        byte[] bArr = this.zze;
        this.zzh = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    final long zzs() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte bZza = zza();
            j |= ((long) (bZza & 127)) << i;
            if ((bZza & 128) == 0) {
                return j;
            }
        }
        throw zzaoe.zze();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final long zzt() throws IOException {
        return zzq();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final long zzu() throws IOException {
        return zzG(zzr());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final long zzv() throws IOException {
        return zzr();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final zzamk zzw() throws IOException {
        int iZzj = zzj();
        if (iZzj > 0) {
            int i = this.zzf;
            int i2 = this.zzh;
            if (iZzj <= i - i2) {
                zzamk zzamkVarZzs = zzamk.zzs(this.zze, i2, iZzj);
                this.zzh += iZzj;
                return zzamkVarZzs;
            }
        }
        if (iZzj == 0) {
            return zzamk.zzb;
        }
        if (iZzj > 0) {
            int i3 = this.zzf;
            int i4 = this.zzh;
            if (iZzj <= i3 - i4) {
                int i5 = iZzj + i4;
                this.zzh = i5;
                return zzamk.zzv(Arrays.copyOfRange(this.zze, i4, i5));
            }
        }
        if (iZzj <= 0) {
            throw zzaoe.zzf();
        }
        throw zzaoe.zzj();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final String zzx() throws IOException {
        int iZzj = zzj();
        if (iZzj > 0) {
            int i = this.zzf;
            int i2 = this.zzh;
            if (iZzj <= i - i2) {
                String str = new String(this.zze, i2, iZzj, zzaoc.zza);
                this.zzh += iZzj;
                return str;
            }
        }
        if (iZzj == 0) {
            return "";
        }
        if (iZzj < 0) {
            throw zzaoe.zzf();
        }
        throw zzaoe.zzj();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final String zzy() throws IOException {
        int iZzj = zzj();
        if (iZzj > 0) {
            int i = this.zzf;
            int i2 = this.zzh;
            if (iZzj <= i - i2) {
                String strZzg = zzari.zzg(this.zze, i2, iZzj);
                this.zzh += iZzj;
                return strZzg;
            }
        }
        if (iZzj == 0) {
            return "";
        }
        if (iZzj <= 0) {
            throw zzaoe.zzf();
        }
        throw zzaoe.zzj();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final void zzz(int i) throws zzaoe {
        if (this.zzi != i) {
            throw zzaoe.zzb();
        }
    }

    public final void zzB(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.zzf;
            int i3 = this.zzh;
            if (i <= i2 - i3) {
                this.zzh = i3 + i;
                return;
            }
        }
        if (i >= 0) {
            throw zzaoe.zzj();
        }
        throw zzaoe.zzf();
    }

    public final int zzj() throws IOException {
        int i;
        int i2 = this.zzh;
        int i3 = this.zzf;
        if (i3 != i2) {
            byte[] bArr = this.zze;
            int i4 = i2 + 1;
            byte b = bArr[i2];
            if (b >= 0) {
                this.zzh = i4;
                return b;
            }
            if (i3 - i4 >= 9) {
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
                                                if (bArr[i7] >= 0) {
                                                    i5 = i12;
                                                    i = i11;
                                                }
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
                this.zzh = i5;
                return i;
            }
        }
        return (int) zzs();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamq
    public final boolean zzE(int i) throws IOException {
        int iZzm;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.zzf - this.zzh < 10) {
                while (i3 < 10) {
                    if (zza() < 0) {
                        i3++;
                    }
                }
                throw zzaoe.zze();
            }
            while (i3 < 10) {
                byte[] bArr = this.zze;
                int i4 = this.zzh;
                this.zzh = i4 + 1;
                if (bArr[i4] < 0) {
                    i3++;
                }
            }
            throw zzaoe.zze();
            return true;
        }
        if (i2 == 1) {
            zzB(8);
            return true;
        }
        if (i2 == 2) {
            zzB(zzj());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 != 5) {
                throw zzaoe.zza();
            }
            zzB(4);
            return true;
        }
        do {
            iZzm = zzm();
            if (iZzm == 0) {
                break;
            }
        } while (zzE(iZzm));
        zzz(((i >>> 3) << 3) | 4);
        return true;
    }

    public final long zzr() throws IOException {
        long j;
        long j2;
        int i = this.zzh;
        int i2 = this.zzf;
        if (i2 != i) {
            byte[] bArr = this.zze;
            int i3 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                this.zzh = i3;
                return b;
            }
            if (i2 - i3 >= 9) {
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
                                                if (bArr[i10] >= 0) {
                                                    j = j7;
                                                }
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
                this.zzh = i4;
                return j;
            }
        }
        return zzs();
    }
}
