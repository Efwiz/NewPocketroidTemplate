package com.google.android.gms.internal.mlkit_vision_face;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcd {
    static boolean zza(Set<?> set, Iterator<?> it) {
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= set.remove(it.next());
        }
        return true == zRemove;
    }

    static boolean zzb(Set<?> set, Collection<?> collection) {
        collection.getClass();
        if (collection instanceof zzby) {
            collection = ((zzby) collection).zza();
        }
        if (!(collection instanceof Set) || collection.size() <= set.size()) {
            return zza(set, collection.iterator());
        }
        Iterator<?> it = set.iterator();
        collection.getClass();
        boolean z = false;
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }
}
