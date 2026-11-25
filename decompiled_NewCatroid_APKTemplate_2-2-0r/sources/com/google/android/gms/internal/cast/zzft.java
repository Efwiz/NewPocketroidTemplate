package com.google.android.gms.internal.cast;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzft implements Map, Serializable {

    @CheckForNull
    private transient zzfu zza;

    @CheckForNull
    private transient zzfu zzb;

    @CheckForNull
    private transient zzfm zzc;

    zzft() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static zzft zzc(Iterable iterable) {
        zzfs zzfsVar = new zzfs(iterable instanceof Collection ? iterable.size() : 4);
        zzfsVar.zza(iterable);
        zzfr zzfrVar = zzfsVar.zzc;
        if (zzfrVar != null) {
            throw zzfrVar.zza();
        }
        zzgc zzgcVarZzh = zzgc.zzh(zzfsVar.zzb, zzfsVar.zza, zzfsVar);
        zzfr zzfrVar2 = zzfsVar.zzc;
        if (zzfrVar2 == null) {
            return zzgcVarZzh;
        }
        throw zzfrVar2.zza();
    }

    public static zzft zzd() {
        return zzgc.zza;
    }

    @Override // java.util.Map
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final boolean containsKey(@CheckForNull Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public final boolean containsValue(@CheckForNull Object obj) {
        return values().contains(obj);
    }

    @Override // java.util.Map
    public final boolean equals(@CheckForNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    @Override // java.util.Map
    @CheckForNull
    public abstract Object get(@CheckForNull Object obj);

    @Override // java.util.Map
    @CheckForNull
    public final Object getOrDefault(@CheckForNull Object obj, @CheckForNull Object obj2) {
        Object obj3 = get(obj);
        return obj3 != null ? obj3 : obj2;
    }

    @Override // java.util.Map
    public final int hashCode() {
        return zzge.zza(entrySet());
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Set keySet() {
        zzfu zzfuVar = this.zzb;
        if (zzfuVar != null) {
            return zzfuVar;
        }
        zzfu zzfuVarZzf = zzf();
        this.zzb = zzfuVarZzf;
        return zzfuVarZzf;
    }

    @Override // java.util.Map
    @CheckForNull
    @Deprecated
    public final Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public final void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @CheckForNull
    @Deprecated
    public final Object remove(@CheckForNull Object obj) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        int size = size();
        if (size < 0) {
            throw new IllegalArgumentException("size cannot be negative but was: " + size);
        }
        StringBuilder sb = new StringBuilder((int) Math.min(((long) size) * 8, 1073741824L));
        sb.append('{');
        boolean z = true;
        for (Map.Entry entry : entrySet()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            z = false;
        }
        sb.append('}');
        return sb.toString();
    }

    abstract zzfm zza();

    @Override // java.util.Map
    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final zzfm values() {
        zzfm zzfmVar = this.zzc;
        if (zzfmVar != null) {
            return zzfmVar;
        }
        zzfm zzfmVarZza = zza();
        this.zzc = zzfmVarZza;
        return zzfmVarZza;
    }

    abstract zzfu zze();

    abstract zzfu zzf();

    @Override // java.util.Map
    /* JADX INFO: renamed from: zzg, reason: merged with bridge method [inline-methods] */
    public final zzfu entrySet() {
        zzfu zzfuVar = this.zza;
        if (zzfuVar != null) {
            return zzfuVar;
        }
        zzfu zzfuVarZze = zze();
        this.zza = zzfuVarZze;
        return zzfuVarZze;
    }
}
