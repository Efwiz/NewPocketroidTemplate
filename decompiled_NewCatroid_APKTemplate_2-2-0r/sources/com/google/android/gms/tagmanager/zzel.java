package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzrl;
import com.google.android.gms.internal.gtm.zzto;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzel implements Runnable {
    final /* synthetic */ zzrl zza;
    final /* synthetic */ zzem zzb;

    zzel(zzem zzemVar, zzrl zzrlVar) {
        this.zzb = zzemVar;
        this.zza = zzrlVar;
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0063: INVOKE (r4 I:java.io.FileOutputStream) VIRTUAL call: java.io.FileOutputStream.close():void A[Catch: IOException -> 0x0067, MD:():void throws java.io.IOException (c), TRY_ENTER, TRY_LEAVE] (LINE:12), block:B:16:0x0063 */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x006b: INVOKE (r4 I:java.io.FileOutputStream) VIRTUAL call: java.io.FileOutputStream.close():void A[Catch: IOException -> 0x006f, MD:():void throws java.io.IOException (c), TRY_ENTER, TRY_LEAVE] (LINE:12), block:B:20:0x006b */
    @Override // java.lang.Runnable
    public final void run() {
        FileOutputStream fileOutputStreamClose;
        FileOutputStream fileOutputStreamClose2;
        zzem zzemVar = this.zzb;
        zzrl zzrlVar = this.zza;
        File fileZze = zzemVar.zze();
        try {
            try {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(fileZze);
                    try {
                        byte[] bArr = new byte[zzrlVar.zzX()];
                        zzto zztoVarZzF = zzto.zzF(bArr);
                        zzrlVar.zzaq(zztoVarZzF);
                        zztoVarZzF.zzG();
                        fileOutputStream.write(bArr);
                        try {
                            fileOutputStream.close();
                        } catch (IOException unused) {
                            zzdh.zzc("error closing stream for writing resource to disk");
                        }
                    } catch (IOException e) {
                        String name2 = zzrlVar.getClass().getName();
                        StringBuilder sb = new StringBuilder(String.valueOf(name2).length() + 72);
                        sb.append("Serializing ");
                        sb.append(name2);
                        sb.append(" to a byte array threw an IOException (should never happen).");
                        throw new RuntimeException(sb.toString(), e);
                    }
                } catch (FileNotFoundException unused2) {
                    zzdh.zza("Error opening resource file for writing");
                }
            } catch (Throwable th) {
                try {
                    fileOutputStreamClose2.close();
                } catch (IOException unused3) {
                    zzdh.zzc("error closing stream for writing resource to disk");
                }
                throw th;
            }
        } catch (IOException unused4) {
            zzdh.zzc("Error writing resource to disk. Removing resource from disk.");
            fileZze.delete();
            try {
                fileOutputStreamClose.close();
            } catch (IOException unused5) {
                zzdh.zzc("error closing stream for writing resource to disk");
            }
        }
    }
}
