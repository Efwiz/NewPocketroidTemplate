package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaoo extends zzaos {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzaoo() {
        super(null);
    }

    /* synthetic */ zzaoo(zzaon zzaonVar) {
        super(null);
    }

    private static <L> List<L> zzf(Object obj, long j, int i) {
        Object obj2;
        List<L> listZzd;
        List<L> list = (List) zzard.zzf(obj, j);
        if (list.isEmpty()) {
            if (list instanceof zzaom) {
                listZzd = new zzaol(i);
            } else {
                listZzd = ((list instanceof zzapn) && (list instanceof zzaob)) ? ((zzaob) list).zzd(i) : new ArrayList<>(i);
            }
            zzard.zzs(obj, j, listZzd);
            return listZzd;
        }
        if (zza.isAssignableFrom(list.getClass())) {
            ArrayList arrayList = new ArrayList(list.size() + i);
            arrayList.addAll(list);
            zzard.zzs(obj, j, arrayList);
            obj2 = arrayList;
        } else {
            if (!(list instanceof zzaqy)) {
                if (!(list instanceof zzapn) || !(list instanceof zzaob)) {
                    return list;
                }
                zzaob zzaobVar = (zzaob) list;
                if (zzaobVar.zzc()) {
                    return list;
                }
                zzaob zzaobVarZzd = zzaobVar.zzd(list.size() + i);
                zzard.zzs(obj, j, zzaobVarZzd);
                return zzaobVarZzd;
            }
            zzaol zzaolVar = new zzaol(list.size() + i);
            zzaolVar.addAll(zzaolVar.size(), (zzaqy) list);
            zzard.zzs(obj, j, zzaolVar);
            obj2 = zzaolVar;
        }
        return (List<L>) obj2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaos
    final <L> List<L> zza(Object obj, long j) {
        return zzf(obj, j, 10);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaos
    final void zzb(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzard.zzf(obj, j);
        if (list instanceof zzaom) {
            objUnmodifiableList = ((zzaom) list).zze();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzapn) && (list instanceof zzaob)) {
                zzaob zzaobVar = (zzaob) list;
                if (zzaobVar.zzc()) {
                    zzaobVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzard.zzs(obj, j, objUnmodifiableList);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaos
    final <E> void zzc(Object obj, Object obj2, long j) {
        List list = (List) zzard.zzf(obj2, j);
        List listZzf = zzf(obj, j, list.size());
        int size = listZzf.size();
        int size2 = list.size();
        if (size > 0 && size2 > 0) {
            listZzf.addAll(list);
        }
        if (size > 0) {
            list = listZzf;
        }
        zzard.zzs(obj, j, list);
    }
}
