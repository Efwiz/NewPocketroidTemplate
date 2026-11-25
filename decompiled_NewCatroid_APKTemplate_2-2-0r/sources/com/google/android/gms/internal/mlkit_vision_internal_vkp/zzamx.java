package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.luaj.vm2.Lua;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzamx extends zzaly {
    private static final Logger zza = Logger.getLogger(zzamx.class.getName());
    private static final boolean zzb = zzard.zzx();
    public static final /* synthetic */ int zzf = 0;
    zzamy zze;

    private zzamx() {
    }

    /* synthetic */ zzamx(zzamw zzamwVar) {
    }

    public static int zzB(byte[] bArr) {
        int length = bArr.length;
        return zzM(length) + length;
    }

    public static int zzC(zzamk zzamkVar) {
        int iZzd = zzamkVar.zzd();
        return zzM(iZzd) + iZzd;
    }

    @Deprecated
    static int zzD(int i, zzapf zzapfVar, zzapz zzapzVar) {
        int iZzM = zzM(i << 3);
        int i2 = iZzM + iZzM;
        zzalo zzaloVar = (zzalo) zzapfVar;
        int iZzr = zzaloVar.zzr();
        if (iZzr == -1) {
            iZzr = zzapzVar.zza(zzaloVar);
            zzaloVar.zzu(iZzr);
        }
        return i2 + iZzr;
    }

    @Deprecated
    public static int zzE(zzapf zzapfVar) {
        return zzapfVar.zzy();
    }

    public static int zzF(int i) {
        if (i >= 0) {
            return zzM(i);
        }
        return 10;
    }

    public static int zzG(zzaok zzaokVar) {
        int iZza = zzaokVar.zza();
        return zzM(iZza) + iZza;
    }

    public static int zzH(zzapf zzapfVar) {
        int iZzy = zzapfVar.zzy();
        return zzM(iZzy) + iZzy;
    }

    static int zzI(zzapf zzapfVar, zzapz zzapzVar) {
        zzalo zzaloVar = (zzalo) zzapfVar;
        int iZzr = zzaloVar.zzr();
        if (iZzr == -1) {
            iZzr = zzapzVar.zza(zzaloVar);
            zzaloVar.zzu(iZzr);
        }
        return zzM(iZzr) + iZzr;
    }

    static int zzJ(int i) {
        if (i > 4096) {
            return 4096;
        }
        return i;
    }

    public static int zzK(String str) {
        int length;
        try {
            length = zzari.zze(str);
        } catch (zzarh unused) {
            length = str.getBytes(zzaoc.zza).length;
        }
        return zzM(length) + length;
    }

    public static int zzL(int i) {
        return zzM(i << 3);
    }

    public static int zzM(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & Lua.MASK_Bx) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static int zzN(long j) {
        int i;
        if (((-128) & j) == 0) {
            return 1;
        }
        if (j < 0) {
            return 10;
        }
        if (((-34359738368L) & j) != 0) {
            j >>>= 28;
            i = 6;
        } else {
            i = 2;
        }
        if (((-2097152) & j) != 0) {
            i += 2;
            j >>>= 14;
        }
        return (j & (-16384)) != 0 ? i + 1 : i;
    }

    public static zzamx zzO(byte[] bArr) {
        return new zzamt(bArr, 0, bArr.length);
    }

    public static zzamx zzP(OutputStream outputStream, int i) {
        return new zzamv(outputStream, i);
    }

    public final void zzQ() {
        if (zzb() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    final void zzR(String str, zzarh zzarhVar) throws IOException {
        zza.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) zzarhVar);
        byte[] bytes = str.getBytes(zzaoc.zza);
        try {
            int length = bytes.length;
            zzx(length);
            zza(bytes, 0, length);
        } catch (zzamu e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new zzamu(e2);
        }
    }

    public abstract void zzV() throws IOException;

    public abstract void zzW(byte b) throws IOException;

    public abstract void zzX(int i, boolean z) throws IOException;

    abstract void zzY(byte[] bArr, int i, int i2) throws IOException;

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaly
    public abstract void zza(byte[] bArr, int i, int i2) throws IOException;

    public abstract int zzb();

    public abstract void zzh(int i, zzamk zzamkVar) throws IOException;

    public abstract void zzi(zzamk zzamkVar) throws IOException;

    public abstract void zzj(int i, int i2) throws IOException;

    public abstract void zzk(int i) throws IOException;

    public abstract void zzl(int i, long j) throws IOException;

    public abstract void zzm(long j) throws IOException;

    public abstract void zzn(int i, int i2) throws IOException;

    public abstract void zzo(int i) throws IOException;

    abstract void zzp(int i, zzapf zzapfVar, zzapz zzapzVar) throws IOException;

    public abstract void zzq(zzapf zzapfVar) throws IOException;

    public abstract void zzr(int i, zzapf zzapfVar) throws IOException;

    public abstract void zzs(int i, zzamk zzamkVar) throws IOException;

    public abstract void zzt(int i, String str) throws IOException;

    public abstract void zzu(String str) throws IOException;

    public abstract void zzv(int i, int i2) throws IOException;

    public abstract void zzw(int i, int i2) throws IOException;

    public abstract void zzx(int i) throws IOException;

    public abstract void zzy(int i, long j) throws IOException;

    public abstract void zzz(long j) throws IOException;
}
