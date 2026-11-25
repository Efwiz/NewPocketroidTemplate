package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzwp extends zzvj<zzva> {
    zzwp() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzvj
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzva zza(zzxf zzxfVar) throws IOException {
        int iZzn = zzxfVar.zzn() - 1;
        if (iZzn == 0) {
            zzuz zzuzVar = new zzuz();
            zzxfVar.zze();
            while (zzxfVar.zzk()) {
                zzuzVar.zza(zza(zzxfVar));
            }
            zzxfVar.zzg();
            return zzuzVar;
        }
        if (iZzn == 2) {
            zzvd zzvdVar = new zzvd();
            zzxfVar.zzf();
            while (zzxfVar.zzk()) {
                zzvdVar.zze(zzxfVar.zzc(), zza(zzxfVar));
            }
            zzxfVar.zzh();
            return zzvdVar;
        }
        if (iZzn == 5) {
            return new zzvg(zzxfVar.zzd());
        }
        if (iZzn == 6) {
            return new zzvg(new zzvm(zzxfVar.zzd()));
        }
        if (iZzn == 7) {
            return new zzvg(Boolean.valueOf(zzxfVar.zzm()));
        }
        if (iZzn != 8) {
            throw new IllegalArgumentException();
        }
        zzxfVar.zzi();
        return zzvc.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzvj
    /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
    public final void zzb(zzxh zzxhVar, zzva zzvaVar) throws IOException {
        if (zzvaVar == null || (zzvaVar instanceof zzvc)) {
            zzxhVar.zzf();
            return;
        }
        if (zzvaVar instanceof zzvg) {
            zzvg zzvgVar = (zzvg) zzvaVar;
            if (zzvgVar.zzg()) {
                zzxhVar.zzg(zzvgVar.zzc());
                return;
            } else if (zzvgVar.zzf()) {
                zzxhVar.zzi(zzvgVar.zze());
                return;
            } else {
                zzxhVar.zzh(zzvgVar.zzd());
                return;
            }
        }
        if (zzvaVar instanceof zzuz) {
            zzxhVar.zza();
            Iterator<zzva> it = ((zzuz) zzvaVar).iterator();
            while (it.hasNext()) {
                zzb(zzxhVar, it.next());
            }
            zzxhVar.zzc();
            return;
        }
        if (!(zzvaVar instanceof zzvd)) {
            String strValueOf = String.valueOf(zzvaVar.getClass());
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 15);
            sb.append("Couldn't write ");
            sb.append(strValueOf);
            throw new IllegalArgumentException(sb.toString());
        }
        zzxhVar.zzb();
        for (Map.Entry<String, zzva> entry : zzvaVar.zzb().zzd()) {
            zzxhVar.zze(entry.getKey());
            zzb(zzxhVar, entry.getValue());
        }
        zzxhVar.zzd();
    }
}
