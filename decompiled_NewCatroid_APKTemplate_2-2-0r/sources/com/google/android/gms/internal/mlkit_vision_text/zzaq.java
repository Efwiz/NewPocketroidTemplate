package com.google.android.gms.internal.mlkit_vision_text;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzaq<K, V> implements zzcc<K, V> {

    @NullableDecl
    private transient Set<K> zza;

    @NullableDecl
    private transient Map<K, Collection<V>> zzb;

    zzaq() {
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzcc) {
            return zzq().equals(((zzcc) obj).zzq());
        }
        return false;
    }

    public final int hashCode() {
        return zzq().hashCode();
    }

    public final String toString() {
        return ((zzag) zzq()).zza.toString();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzcc
    public boolean zzd(@NullableDecl K k, @NullableDecl V v) {
        throw null;
    }

    abstract Set<K> zzh();

    abstract Map<K, Collection<V>> zzi();

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzcc
    public final Set<K> zzp() {
        Set<K> set = this.zza;
        if (set != null) {
            return set;
        }
        Set<K> setZzh = zzh();
        this.zza = setZzh;
        return setZzh;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzcc
    public final Map<K, Collection<V>> zzq() {
        Map<K, Collection<V>> map = this.zzb;
        if (map != null) {
            return map;
        }
        Map<K, Collection<V>> mapZzi = zzi();
        this.zzb = mapZzi;
        return mapZzi;
    }
}
