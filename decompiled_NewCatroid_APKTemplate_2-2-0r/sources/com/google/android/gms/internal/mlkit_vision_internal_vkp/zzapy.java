package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.gaurav.avnc.vnc.XKeySym;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzapy extends zzamk {
    static final int[] zza = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, XKeySym.XK_eacute, 377, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296, 433494437, 701408733, 1134903170, 1836311903, Integer.MAX_VALUE};
    private final int zzc;
    private final zzamk zzd;
    private final zzamk zze;
    private final int zzf;
    private final int zzg;

    private zzapy(zzamk zzamkVar, zzamk zzamkVar2) {
        this.zzd = zzamkVar;
        this.zze = zzamkVar2;
        int iZzd = zzamkVar.zzd();
        this.zzf = iZzd;
        this.zzc = iZzd + zzamkVar2.zzd();
        this.zzg = Math.max(zzamkVar.zzf(), zzamkVar2.zzf()) + 1;
    }

    static zzamk zzA(zzamk zzamkVar, zzamk zzamkVar2) {
        if (zzamkVar2.zzd() == 0) {
            return zzamkVar;
        }
        if (zzamkVar.zzd() == 0) {
            return zzamkVar2;
        }
        int iZzd = zzamkVar.zzd() + zzamkVar2.zzd();
        if (iZzd < 128) {
            return zzB(zzamkVar, zzamkVar2);
        }
        if (zzamkVar instanceof zzapy) {
            zzapy zzapyVar = (zzapy) zzamkVar;
            if (zzapyVar.zze.zzd() + zzamkVar2.zzd() < 128) {
                return new zzapy(zzapyVar.zzd, zzB(zzapyVar.zze, zzamkVar2));
            }
            if (zzapyVar.zzd.zzf() > zzapyVar.zze.zzf() && zzapyVar.zzg > zzamkVar2.zzf()) {
                return new zzapy(zzapyVar.zzd, new zzapy(zzapyVar.zze, zzamkVar2));
            }
        }
        return iZzd >= zzc(Math.max(zzamkVar.zzf(), zzamkVar2.zzf()) + 1) ? new zzapy(zzamkVar, zzamkVar2) : zzapu.zza(new zzapu(null), zzamkVar, zzamkVar2);
    }

    private static zzamk zzB(zzamk zzamkVar, zzamk zzamkVar2) {
        int iZzd = zzamkVar.zzd();
        int iZzd2 = zzamkVar2.zzd();
        byte[] bArr = new byte[iZzd + iZzd2];
        zzamkVar.zzy(bArr, 0, 0, iZzd);
        zzamkVar2.zzy(bArr, 0, iZzd, iZzd2);
        return new zzamh(bArr);
    }

    static int zzc(int i) {
        int[] iArr = zza;
        int length = iArr.length;
        if (i >= 47) {
            return Integer.MAX_VALUE;
        }
        return iArr[i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzamk)) {
            return false;
        }
        zzamk zzamkVar = (zzamk) obj;
        if (this.zzc != zzamkVar.zzd()) {
            return false;
        }
        if (this.zzc == 0) {
            return true;
        }
        int iZzp = zzp();
        int iZzp2 = zzamkVar.zzp();
        if (iZzp != 0 && iZzp2 != 0 && iZzp != iZzp2) {
            return false;
        }
        zzapv zzapvVar = null;
        zzapw zzapwVar = new zzapw(this, zzapvVar);
        zzamg zzamgVarZza = zzapwVar.next();
        zzapw zzapwVar2 = new zzapw(zzamkVar, zzapvVar);
        zzamg zzamgVarZza2 = zzapwVar2.next();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int iZzd = zzamgVarZza.zzd() - i;
            int iZzd2 = zzamgVarZza2.zzd() - i2;
            int iMin = Math.min(iZzd, iZzd2);
            if (!(i == 0 ? zzamgVarZza.zzg(zzamgVarZza2, i2, iMin) : zzamgVarZza2.zzg(zzamgVarZza, i, iMin))) {
                return false;
            }
            i3 += iMin;
            int i4 = this.zzc;
            if (i3 >= i4) {
                if (i3 == i4) {
                    return true;
                }
                throw new IllegalStateException();
            }
            if (iMin == iZzd) {
                zzamgVarZza = zzapwVar.next();
                i = 0;
            } else {
                i += iMin;
            }
            if (iMin == iZzd2) {
                zzamgVarZza = zzamgVarZza;
                zzamgVarZza2 = zzapwVar2.next();
                i2 = 0;
            } else {
                zzamgVarZza = zzamgVarZza;
                i2 += iMin;
            }
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk, java.lang.Iterable
    public final /* bridge */ /* synthetic */ Iterator<Byte> iterator() {
        return new zzaps(this);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    public final byte zza(int i) {
        zzx(i, this.zzc);
        return zzb(i);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    final byte zzb(int i) {
        int i2 = this.zzf;
        return i < i2 ? this.zzd.zzb(i) : this.zze.zzb(i - i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    public final int zzd() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    protected final void zze(byte[] bArr, int i, int i2, int i3) {
        int i4 = this.zzf;
        if (i + i3 <= i4) {
            this.zzd.zze(bArr, i, i2, i3);
        } else {
            if (i >= i4) {
                this.zze.zze(bArr, i - i4, i2, i3);
                return;
            }
            int i5 = i4 - i;
            this.zzd.zze(bArr, i, i2, i5);
            this.zze.zze(bArr, 0, i2 + i5, i3 - i5);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    protected final int zzf() {
        return this.zzg;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    protected final boolean zzh() {
        return this.zzc >= zzc(this.zzg);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    protected final int zzi(int i, int i2, int i3) {
        int i4 = this.zzf;
        if (i2 + i3 <= i4) {
            return this.zzd.zzi(i, i2, i3);
        }
        if (i2 >= i4) {
            return this.zze.zzi(i, i2 - i4, i3);
        }
        int i5 = i4 - i2;
        return this.zze.zzi(this.zzd.zzi(i, i2, i5), 0, i3 - i5);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    protected final int zzj(int i, int i2, int i3) {
        int i4 = this.zzf;
        if (i2 + i3 <= i4) {
            return this.zzd.zzj(i, i2, i3);
        }
        if (i2 >= i4) {
            return this.zze.zzj(i, i2 - i4, i3);
        }
        int i5 = i4 - i2;
        return this.zze.zzj(this.zzd.zzj(i, i2, i5), 0, i3 - i5);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    public final zzamk zzk(int i, int i2) {
        int iZzo = zzo(i, i2, this.zzc);
        if (iZzo == 0) {
            return zzamk.zzb;
        }
        if (iZzo == this.zzc) {
            return this;
        }
        int i3 = this.zzf;
        if (i2 <= i3) {
            return this.zzd.zzk(i, i2);
        }
        if (i >= i3) {
            return this.zze.zzk(i - i3, i2 - i3);
        }
        zzamk zzamkVar = this.zzd;
        return new zzapy(zzamkVar.zzk(i, zzamkVar.zzd()), this.zze.zzk(0, i2 - this.zzf));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    protected final String zzl(Charset charset) {
        byte[] bArr;
        int i = this.zzc;
        if (i == 0) {
            bArr = zzaoc.zzc;
        } else {
            byte[] bArr2 = new byte[i];
            zze(bArr2, 0, 0, i);
            bArr = bArr2;
        }
        return new String(bArr, charset);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    final void zzm(zzaly zzalyVar) throws IOException {
        this.zzd.zzm(zzalyVar);
        this.zze.zzm(zzalyVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    public final boolean zzn() {
        int iZzj = this.zzd.zzj(0, 0, this.zzf);
        zzamk zzamkVar = this.zze;
        return zzamkVar.zzj(iZzj, 0, zzamkVar.zzd()) == 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    /* JADX INFO: renamed from: zzq */
    public final zzame iterator() {
        return new zzaps(this);
    }
}
