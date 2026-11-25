package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfu extends zzfs {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzfu() {
        super();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfs
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzhv.zzf(obj, j);
        if (list instanceof zzfp) {
            objUnmodifiableList = ((zzfp) list).b_();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzgu) && (list instanceof zzff)) {
                zzff zzffVar = (zzff) list;
                if (zzffVar.zza()) {
                    zzffVar.c_();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzhv.zza(obj, j, objUnmodifiableList);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzfs
    final <E> void zza(Object obj, Object obj2, long j) {
        List list;
        List list2;
        List listZza;
        List listZzb = zzb(obj2, j);
        int size = listZzb.size();
        List listZzb2 = zzb(obj, j);
        if (listZzb2.isEmpty()) {
            if (listZzb2 instanceof zzfp) {
                listZza = new zzfq(size);
            } else if ((listZzb2 instanceof zzgu) && (listZzb2 instanceof zzff)) {
                listZza = ((zzff) listZzb2).zza(size);
            } else {
                listZza = new ArrayList(size);
            }
            zzhv.zza(obj, j, listZza);
            list2 = listZza;
        } else {
            if (zza.isAssignableFrom(listZzb2.getClass())) {
                ArrayList arrayList = new ArrayList(listZzb2.size() + size);
                arrayList.addAll(listZzb2);
                zzhv.zza(obj, j, arrayList);
                list = arrayList;
            } else if (listZzb2 instanceof zzhu) {
                zzfq zzfqVar = new zzfq(listZzb2.size() + size);
                zzfqVar.addAll((zzhu) listZzb2);
                zzhv.zza(obj, j, zzfqVar);
                list = zzfqVar;
            } else if ((listZzb2 instanceof zzgu) && (listZzb2 instanceof zzff)) {
                zzff zzffVar = (zzff) listZzb2;
                if (!zzffVar.zza()) {
                    list2 = listZzb2;
                    list2 = listZzb2;
                    list2 = listZzb2;
                    zzff<E> zzffVarZza = zzffVar.zza(listZzb2.size() + size);
                    zzhv.zza(obj, j, zzffVarZza);
                    list2 = zzffVarZza;
                }
            }
            list2 = list;
        }
        list2 = listZzb2;
        list2 = listZzb2;
        list2 = listZzb2;
        list2 = listZzb2;
        list2 = listZzb2;
        list2 = listZzb2;
        int size2 = list2.size();
        int size3 = listZzb.size();
        if (size2 > 0 && size3 > 0) {
            list2.addAll(listZzb);
        }
        if (size2 > 0) {
            listZzb = list2;
        }
        zzhv.zza(obj, j, listZzb);
    }

    private static <E> List<E> zzb(Object obj, long j) {
        return (List) zzhv.zzf(obj, j);
    }
}
