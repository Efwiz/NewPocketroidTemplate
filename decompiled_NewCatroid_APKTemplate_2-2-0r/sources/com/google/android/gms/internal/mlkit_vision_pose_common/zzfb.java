package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Comparator;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfb implements Comparator<zzez> {
    zzfb() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzez zzezVar, zzez zzezVar2) {
        zzez zzezVar3 = zzezVar;
        zzez zzezVar4 = zzezVar2;
        zzfi zzfiVar = (zzfi) zzezVar3.iterator();
        zzfi zzfiVar2 = (zzfi) zzezVar4.iterator();
        while (zzfiVar.hasNext() && zzfiVar2.hasNext()) {
            int iCompare = Integer.compare(zzez.zzb(zzfiVar.zza()), zzez.zzb(zzfiVar2.zza()));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return Integer.compare(zzezVar3.zza(), zzezVar4.zza());
    }
}
