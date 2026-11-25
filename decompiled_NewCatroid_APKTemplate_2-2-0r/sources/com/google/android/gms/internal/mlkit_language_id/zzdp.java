package com.google.android.gms.internal.mlkit_language_id;

import java.util.Comparator;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdp implements Comparator<zzdn> {
    zzdp() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(zzdn zzdnVar, zzdn zzdnVar2) {
        zzdn zzdnVar3 = zzdnVar;
        zzdn zzdnVar4 = zzdnVar2;
        zzds zzdsVar = (zzds) zzdnVar3.iterator();
        zzds zzdsVar2 = (zzds) zzdnVar4.iterator();
        while (zzdsVar.hasNext() && zzdsVar2.hasNext()) {
            int iCompare = Integer.compare(zzdn.zzb(zzdsVar.zza()), zzdn.zzb(zzdsVar2.zza()));
            if (iCompare != 0) {
                return iCompare;
            }
        }
        return Integer.compare(zzdnVar3.zza(), zzdnVar4.zza());
    }
}
