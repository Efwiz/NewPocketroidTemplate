package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgx extends zzgv {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzgx() {
        super();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgv
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zziz.zzf(obj, j);
        if (list instanceof zzgw) {
            objUnmodifiableList = ((zzgw) list).zze();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzhy) && (list instanceof zzgm)) {
                zzgm zzgmVar = (zzgm) list;
                if (zzgmVar.zza()) {
                    zzgmVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zziz.zza(obj, j, objUnmodifiableList);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgv
    final <E> void zza(Object obj, Object obj2, long j) {
        List list;
        List list2;
        List listZzb;
        List listZzb2 = zzb(obj2, j);
        int size = listZzb2.size();
        List listZzb3 = zzb(obj, j);
        if (listZzb3.isEmpty()) {
            if (listZzb3 instanceof zzgw) {
                listZzb = new zzgt(size);
            } else if ((listZzb3 instanceof zzhy) && (listZzb3 instanceof zzgm)) {
                listZzb = ((zzgm) listZzb3).zzb(size);
            } else {
                listZzb = new ArrayList(size);
            }
            zziz.zza(obj, j, listZzb);
            list2 = listZzb;
        } else {
            if (zza.isAssignableFrom(listZzb3.getClass())) {
                ArrayList arrayList = new ArrayList(listZzb3.size() + size);
                arrayList.addAll(listZzb3);
                zziz.zza(obj, j, arrayList);
                list = arrayList;
            } else if (listZzb3 instanceof zziy) {
                zzgt zzgtVar = new zzgt(listZzb3.size() + size);
                zzgtVar.addAll((zziy) listZzb3);
                zziz.zza(obj, j, zzgtVar);
                list = zzgtVar;
            } else if ((listZzb3 instanceof zzhy) && (listZzb3 instanceof zzgm)) {
                zzgm zzgmVar = (zzgm) listZzb3;
                if (!zzgmVar.zza()) {
                    list2 = listZzb3;
                    list2 = listZzb3;
                    list2 = listZzb3;
                    zzgm<E> zzgmVarZzb = zzgmVar.zzb(listZzb3.size() + size);
                    zziz.zza(obj, j, zzgmVarZzb);
                    list2 = zzgmVarZzb;
                }
            }
            list2 = list;
        }
        list2 = listZzb3;
        list2 = listZzb3;
        list2 = listZzb3;
        list2 = listZzb3;
        list2 = listZzb3;
        list2 = listZzb3;
        int size2 = list2.size();
        int size3 = listZzb2.size();
        if (size2 > 0 && size3 > 0) {
            list2.addAll(listZzb2);
        }
        if (size2 > 0) {
            listZzb2 = list2;
        }
        zziz.zza(obj, j, listZzb2);
    }

    private static <E> List<E> zzb(Object obj, long j) {
        return (List) zziz.zzf(obj, j);
    }
}
