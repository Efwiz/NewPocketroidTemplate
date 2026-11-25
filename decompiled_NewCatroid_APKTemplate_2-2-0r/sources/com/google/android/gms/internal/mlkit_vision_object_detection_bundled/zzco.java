package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.io.IOException;
import java.io.StringReader;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzco {
    public static zzcj zza(zzeo zzeoVar) throws zzck, zzcq {
        boolean zZzl = zzeoVar.zzl();
        zzeoVar.zzj(true);
        try {
            try {
                zzcj zzcjVarZza = zzde.zza(zzeoVar);
                zzeoVar.zzj(zZzl);
                return zzcjVarZza;
            } catch (OutOfMemoryError e) {
                String strValueOf = String.valueOf(zzeoVar);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 36);
                sb.append("Failed parsing JSON source: ");
                sb.append(strValueOf);
                sb.append(" to Json");
                throw new zzcn(sb.toString(), e);
            } catch (StackOverflowError e2) {
                String strValueOf2 = String.valueOf(zzeoVar);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 36);
                sb2.append("Failed parsing JSON source: ");
                sb2.append(strValueOf2);
                sb2.append(" to Json");
                throw new zzcn(sb2.toString(), e2);
            }
        } catch (Throwable th) {
            zzeoVar.zzj(zZzl);
            throw th;
        }
    }

    public static zzcj zzb(String str) throws zzcq {
        try {
            zzeo zzeoVar = new zzeo(new StringReader(str));
            zzcj zzcjVarZza = zza(zzeoVar);
            if (!(zzcjVarZza instanceof zzcl) && zzeoVar.zzn() != 10) {
                throw new zzcq("Did not consume the entire document.");
            }
            return zzcjVarZza;
        } catch (zzer e) {
            throw new zzcq(e);
        } catch (IOException e2) {
            throw new zzck(e2);
        } catch (NumberFormatException e3) {
            throw new zzcq(e3);
        }
    }
}
