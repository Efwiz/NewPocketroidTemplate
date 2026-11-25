package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.Comparator;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdw implements Comparator<zzdu> {
    zzdw() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzdu zzduVar, zzdu zzduVar2) {
        zzdu zzduVar3 = zzduVar;
        zzdu zzduVar4 = zzduVar2;
        zzed zzedVar = (zzed) zzduVar3.iterator();
        zzed zzedVar2 = (zzed) zzduVar4.iterator();
        while (zzedVar.hasNext() && zzedVar2.hasNext()) {
            int iCompare = Integer.compare(zzdu.zzb(zzedVar.zza()), zzdu.zzb(zzedVar2.zza()));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return Integer.compare(zzduVar3.zza(), zzduVar4.zza());
    }
}
