package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
class zzta extends zzsz {
    protected final byte[] zza;

    zzta(byte[] bArr) {
        bArr.getClass();
        this.zza = bArr;
    }

    @Override // com.google.android.gms.internal.gtm.zztd
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zztd) || zzd() != ((zztd) obj).zzd()) {
            return false;
        }
        if (zzd() == 0) {
            return true;
        }
        if (!(obj instanceof zzta)) {
            return obj.equals(this);
        }
        zzta zztaVar = (zzta) obj;
        int iZzl = zzl();
        int iZzl2 = zztaVar.zzl();
        if (iZzl != 0 && iZzl2 != 0 && iZzl != iZzl2) {
            return false;
        }
        int iZzd = zzd();
        if (iZzd > zztaVar.zzd()) {
            int iZzd2 = zzd();
            StringBuilder sb = new StringBuilder(40);
            sb.append("Length too large: ");
            sb.append(iZzd);
            sb.append(iZzd2);
            throw new IllegalArgumentException(sb.toString());
        }
        if (iZzd > zztaVar.zzd()) {
            int iZzd3 = zztaVar.zzd();
            StringBuilder sb2 = new StringBuilder(59);
            sb2.append("Ran off end of other: 0, ");
            sb2.append(iZzd);
            sb2.append(", ");
            sb2.append(iZzd3);
            throw new IllegalArgumentException(sb2.toString());
        }
        if (!(zztaVar instanceof zzta)) {
            return zztaVar.zzg(0, iZzd).equals(zzg(0, iZzd));
        }
        byte[] bArr = this.zza;
        byte[] bArr2 = zztaVar.zza;
        int iZzc = zzc() + iZzd;
        int iZzc2 = zzc();
        int iZzc3 = zztaVar.zzc();
        while (iZzc2 < iZzc) {
            if (bArr[iZzc2] != bArr2[iZzc3]) {
                return false;
            }
            iZzc2++;
            iZzc3++;
        }
        return true;
    }

    @Override // com.google.android.gms.internal.gtm.zztd
    public byte zza(int i) {
        return this.zza[i];
    }

    @Override // com.google.android.gms.internal.gtm.zztd
    byte zzb(int i) {
        return this.zza[i];
    }

    protected int zzc() {
        return 0;
    }

    @Override // com.google.android.gms.internal.gtm.zztd
    public int zzd() {
        return this.zza.length;
    }

    @Override // com.google.android.gms.internal.gtm.zztd
    protected void zze(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zza, 0, bArr, 0, i3);
    }

    @Override // com.google.android.gms.internal.gtm.zztd
    protected final int zzf(int i, int i2, int i3) {
        return zzvi.zzd(i, this.zza, zzc(), i3);
    }

    @Override // com.google.android.gms.internal.gtm.zztd
    public final zztd zzg(int i, int i2) {
        int iZzk = zzk(0, i2, zzd());
        return iZzk == 0 ? zztd.zzb : new zzsx(this.zza, zzc(), iZzk);
    }

    @Override // com.google.android.gms.internal.gtm.zztd
    protected final String zzh(Charset charset) {
        return new String(this.zza, zzc(), zzd(), charset);
    }

    @Override // com.google.android.gms.internal.gtm.zztd
    final void zzi(zzss zzssVar) throws IOException {
        ((zztl) zzssVar).zzc(this.zza, zzc(), zzd());
    }

    @Override // com.google.android.gms.internal.gtm.zztd
    public final boolean zzj() {
        int iZzc = zzc();
        return zzyd.zzf(this.zza, iZzc, zzd() + iZzc);
    }
}
