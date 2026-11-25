package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzax<E> extends zzap<E> implements Set<E> {

    @CheckForNull
    private transient zzau<E> zza;

    zzax() {
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(@CheckForNull Object obj) {
        if (obj == this || obj == this) {
            return true;
        }
        if (obj instanceof Set) {
            Set set = (Set) obj;
            try {
                if (size() == set.size() && containsAll(set)) {
                    return true;
                }
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        return zzbq.zza(this);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    /* JADX INFO: renamed from: zzd */
    public abstract zzbs<E> iterator();

    public final zzau<E> zzf() {
        zzau<E> zzauVar = this.zza;
        if (zzauVar != null) {
            return zzauVar;
        }
        zzau<E> zzauVarZzg = zzg();
        this.zza = zzauVarZzg;
        return zzauVarZzg;
    }

    zzau<E> zzg() {
        return zzau.zzg(toArray());
    }
}
