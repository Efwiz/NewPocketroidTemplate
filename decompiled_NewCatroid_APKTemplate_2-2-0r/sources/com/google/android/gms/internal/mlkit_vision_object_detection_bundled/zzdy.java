package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdy extends zzcs<zzcj> {
    zzdy() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzcs
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzcj zza(zzeo zzeoVar) throws IOException {
        int iZzn = zzeoVar.zzn() - 1;
        if (iZzn == 0) {
            zzci zzciVar = new zzci();
            zzeoVar.zze();
            while (zzeoVar.zzk()) {
                zzciVar.zza(zza(zzeoVar));
            }
            zzeoVar.zzg();
            return zzciVar;
        }
        if (iZzn == 2) {
            zzcm zzcmVar = new zzcm();
            zzeoVar.zzf();
            while (zzeoVar.zzk()) {
                zzcmVar.zze(zzeoVar.zzc(), zza(zzeoVar));
            }
            zzeoVar.zzh();
            return zzcmVar;
        }
        if (iZzn == 5) {
            return new zzcp(zzeoVar.zzd());
        }
        if (iZzn == 6) {
            return new zzcp(new zzcv(zzeoVar.zzd()));
        }
        if (iZzn == 7) {
            return new zzcp(Boolean.valueOf(zzeoVar.zzm()));
        }
        if (iZzn != 8) {
            throw new IllegalArgumentException();
        }
        zzeoVar.zzi();
        return zzcl.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzcs
    /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
    public final void zzb(zzeq zzeqVar, zzcj zzcjVar) throws IOException {
        if (zzcjVar == null || (zzcjVar instanceof zzcl)) {
            zzeqVar.zzf();
            return;
        }
        if (zzcjVar instanceof zzcp) {
            zzcp zzcpVar = (zzcp) zzcjVar;
            if (zzcpVar.zzg()) {
                zzeqVar.zzg(zzcpVar.zzc());
                return;
            } else if (zzcpVar.zzf()) {
                zzeqVar.zzi(zzcpVar.zze());
                return;
            } else {
                zzeqVar.zzh(zzcpVar.zzd());
                return;
            }
        }
        if (zzcjVar instanceof zzci) {
            zzeqVar.zza();
            Iterator<zzcj> it = ((zzci) zzcjVar).iterator();
            while (it.hasNext()) {
                zzb(zzeqVar, it.next());
            }
            zzeqVar.zzc();
            return;
        }
        if (!(zzcjVar instanceof zzcm)) {
            String strValueOf = String.valueOf(zzcjVar.getClass());
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 15);
            sb.append("Couldn't write ");
            sb.append(strValueOf);
            throw new IllegalArgumentException(sb.toString());
        }
        zzeqVar.zzb();
        for (Map.Entry<String, zzcj> entry : zzcjVar.zzb().zzd()) {
            zzeqVar.zze(entry.getKey());
            zzb(zzeqVar, entry.getValue());
        }
        zzeqVar.zzd();
    }
}
