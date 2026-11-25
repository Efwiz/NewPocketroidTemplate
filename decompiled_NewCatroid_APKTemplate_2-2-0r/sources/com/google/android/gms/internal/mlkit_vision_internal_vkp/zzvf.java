package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.io.StringReader;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzvf {
    public static zzva zza(zzxf zzxfVar) throws zzvb, zzvh {
        boolean zZzl = zzxfVar.zzl();
        zzxfVar.zzj(true);
        try {
            try {
                zzva zzvaVarZza = zzvv.zza(zzxfVar);
                zzxfVar.zzj(zZzl);
                return zzvaVarZza;
            } catch (OutOfMemoryError e) {
                String strValueOf = String.valueOf(zzxfVar);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 36);
                sb.append("Failed parsing JSON source: ");
                sb.append(strValueOf);
                sb.append(" to Json");
                throw new zzve(sb.toString(), e);
            } catch (StackOverflowError e2) {
                String strValueOf2 = String.valueOf(zzxfVar);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 36);
                sb2.append("Failed parsing JSON source: ");
                sb2.append(strValueOf2);
                sb2.append(" to Json");
                throw new zzve(sb2.toString(), e2);
            }
        } catch (Throwable th) {
            zzxfVar.zzj(zZzl);
            throw th;
        }
    }

    public static zzva zzb(String str) throws zzvh {
        try {
            zzxf zzxfVar = new zzxf(new StringReader(str));
            zzva zzvaVarZza = zza(zzxfVar);
            if (!(zzvaVarZza instanceof zzvc) && zzxfVar.zzn() != 10) {
                throw new zzvh("Did not consume the entire document.");
            }
            return zzvaVarZza;
        } catch (zzxi e) {
            throw new zzvh(e);
        } catch (IOException e2) {
            throw new zzvb(e2);
        } catch (NumberFormatException e3) {
            throw new zzvh(e3);
        }
    }
}
