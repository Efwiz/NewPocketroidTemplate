package com.google.android.gms.internal.mlkit_language_id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfl extends zzfj {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzfl() {
        super();
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfj
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzhn.zzf(obj, j);
        if (list instanceof zzfg) {
            objUnmodifiableList = ((zzfg) list).a_();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzgi) && (list instanceof zzew)) {
                zzew zzewVar = (zzew) list;
                if (zzewVar.zza()) {
                    zzewVar.b_();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzhn.zza(obj, j, objUnmodifiableList);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfj
    final <E> void zza(Object obj, Object obj2, long j) {
        List list;
        List list2;
        List listZzb;
        List listZzb2 = zzb(obj2, j);
        int size = listZzb2.size();
        List listZzb3 = zzb(obj, j);
        if (listZzb3.isEmpty()) {
            if (listZzb3 instanceof zzfg) {
                listZzb = new zzfh(size);
            } else if ((listZzb3 instanceof zzgi) && (listZzb3 instanceof zzew)) {
                listZzb = ((zzew) listZzb3).zzb(size);
            } else {
                listZzb = new ArrayList(size);
            }
            zzhn.zza(obj, j, listZzb);
            list2 = listZzb;
        } else {
            if (zza.isAssignableFrom(listZzb3.getClass())) {
                ArrayList arrayList = new ArrayList(listZzb3.size() + size);
                arrayList.addAll(listZzb3);
                zzhn.zza(obj, j, arrayList);
                list = arrayList;
            } else if (listZzb3 instanceof zzhi) {
                zzfh zzfhVar = new zzfh(listZzb3.size() + size);
                zzfhVar.addAll((zzhi) listZzb3);
                zzhn.zza(obj, j, zzfhVar);
                list = zzfhVar;
            } else if ((listZzb3 instanceof zzgi) && (listZzb3 instanceof zzew)) {
                zzew zzewVar = (zzew) listZzb3;
                if (!zzewVar.zza()) {
                    list2 = listZzb3;
                    list2 = listZzb3;
                    list2 = listZzb3;
                    zzew<E> zzewVarZzb = zzewVar.zzb(listZzb3.size() + size);
                    zzhn.zza(obj, j, zzewVarZzb);
                    list2 = zzewVarZzb;
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
        zzhn.zza(obj, j, listZzb2);
    }

    private static <E> List<E> zzb(Object obj, long j) {
        return (List) zzhn.zzf(obj, j);
    }
}
