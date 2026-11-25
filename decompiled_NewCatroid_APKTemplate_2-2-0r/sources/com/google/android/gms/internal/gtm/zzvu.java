package com.google.android.gms.internal.gtm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzvu extends zzvy {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzvu() {
        super(null);
    }

    /* synthetic */ zzvu(zzvt zzvtVar) {
        super(null);
    }

    private static <L> List<L> zzf(Object obj, long j, int i) {
        Object obj2;
        List<L> listZzd;
        List<L> list = (List) zzxy.zzf(obj, j);
        if (list.isEmpty()) {
            if (list instanceof zzvs) {
                listZzd = new zzvr(i);
            } else {
                listZzd = ((list instanceof zzws) && (list instanceof zzvh)) ? ((zzvh) list).zzd(i) : new ArrayList<>(i);
            }
            zzxy.zzs(obj, j, listZzd);
            return listZzd;
        }
        if (zza.isAssignableFrom(list.getClass())) {
            ArrayList arrayList = new ArrayList(list.size() + i);
            arrayList.addAll(list);
            zzxy.zzs(obj, j, arrayList);
            obj2 = arrayList;
        } else {
            if (!(list instanceof zzxt)) {
                if (!(list instanceof zzws) || !(list instanceof zzvh)) {
                    return list;
                }
                zzvh zzvhVar = (zzvh) list;
                if (zzvhVar.zzc()) {
                    return list;
                }
                zzvh zzvhVarZzd = zzvhVar.zzd(list.size() + i);
                zzxy.zzs(obj, j, zzvhVarZzd);
                return zzvhVarZzd;
            }
            zzvr zzvrVar = new zzvr(list.size() + i);
            zzvrVar.addAll(zzvrVar.size(), (zzxt) list);
            zzxy.zzs(obj, j, zzvrVar);
            obj2 = zzvrVar;
        }
        return (List<L>) obj2;
    }

    @Override // com.google.android.gms.internal.gtm.zzvy
    final <L> List<L> zza(Object obj, long j) {
        return zzf(obj, j, 10);
    }

    @Override // com.google.android.gms.internal.gtm.zzvy
    final void zzb(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzxy.zzf(obj, j);
        if (list instanceof zzvs) {
            objUnmodifiableList = ((zzvs) list).zze();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzws) && (list instanceof zzvh)) {
                zzvh zzvhVar = (zzvh) list;
                if (zzvhVar.zzc()) {
                    zzvhVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzxy.zzs(obj, j, objUnmodifiableList);
    }

    @Override // com.google.android.gms.internal.gtm.zzvy
    final <E> void zzc(Object obj, Object obj2, long j) {
        List list = (List) zzxy.zzf(obj2, j);
        List listZzf = zzf(obj, j, list.size());
        int size = listZzf.size();
        int size2 = list.size();
        if (size > 0 && size2 > 0) {
            listZzf.addAll(list);
        }
        if (size > 0) {
            list = listZzf;
        }
        zzxy.zzs(obj, j, list);
    }
}
