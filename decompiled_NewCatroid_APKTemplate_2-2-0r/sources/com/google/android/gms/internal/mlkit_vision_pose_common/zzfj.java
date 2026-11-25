package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.io.IOException;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
class zzfj extends zzfk {
    protected final byte[] zzb;

    zzfj(byte[] bArr) {
        bArr.getClass();
        this.zzb = bArr;
    }

    protected int zze() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    public byte zza(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    byte zzb(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    public int zza() {
        return this.zzb.length;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    public final zzez zza(int i, int i2) {
        int iZzb = zzb(0, i2, zza());
        if (iZzb == 0) {
            return zzez.zza;
        }
        return new zzfg(this.zzb, zze(), iZzb);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    final void zza(zzfa zzfaVar) throws IOException {
        zzfaVar.zza(this.zzb, zze(), zza());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    protected final String zza(Charset charset) {
        return new String(this.zzb, zze(), zza(), charset);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    public final boolean zzc() {
        int iZze = zze();
        return zzjc.zza(this.zzb, iZze, zza() + iZze);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzez) || zza() != ((zzez) obj).zza()) {
            return false;
        }
        if (zza() == 0) {
            return true;
        }
        if (obj instanceof zzfj) {
            zzfj zzfjVar = (zzfj) obj;
            int iZzd = zzd();
            int iZzd2 = zzfjVar.zzd();
            if (iZzd == 0 || iZzd2 == 0 || iZzd == iZzd2) {
                return zza(zzfjVar, 0, zza());
            }
            return false;
        }
        return obj.equals(this);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfk
    final boolean zza(zzez zzezVar, int i, int i2) {
        if (i2 > zzezVar.zza()) {
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(zza()).toString());
        }
        if (i2 > zzezVar.zza()) {
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: 0, ").append(i2).append(", ").append(zzezVar.zza()).toString());
        }
        if (zzezVar instanceof zzfj) {
            zzfj zzfjVar = (zzfj) zzezVar;
            byte[] bArr = this.zzb;
            byte[] bArr2 = zzfjVar.zzb;
            int iZze = zze() + i2;
            int iZze2 = zze();
            int iZze3 = zzfjVar.zze();
            while (iZze2 < iZze) {
                if (bArr[iZze2] != bArr2[iZze3]) {
                    return false;
                }
                iZze2++;
                iZze3++;
            }
            return true;
        }
        return zzezVar.zza(0, i2).equals(zza(0, i2));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    protected final int zza(int i, int i2, int i3) {
        return zzgg.zza(i, this.zzb, zze(), i3);
    }
}
