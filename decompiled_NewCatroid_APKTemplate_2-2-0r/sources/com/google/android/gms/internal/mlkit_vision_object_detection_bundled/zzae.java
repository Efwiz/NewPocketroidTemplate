package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzae extends AbstractSet<Map.Entry> {
    final /* synthetic */ zzak zza;

    zzae(zzak zzakVar) {
        this.zza = zzakVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.zza.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(@CheckForNull Object obj) {
        Map mapZzl = this.zza.zzl();
        if (mapZzl != null) {
            return mapZzl.entrySet().contains(obj);
        }
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            int iZzw = this.zza.zzw(entry.getKey());
            if (iZzw != -1 && zzd.zza(zzak.zzj(this.zza, iZzw), entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry> iterator() {
        zzak zzakVar = this.zza;
        Map mapZzl = zzakVar.zzl();
        return mapZzl != null ? mapZzl.entrySet().iterator() : new zzac(zzakVar);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(@CheckForNull Object obj) {
        Map mapZzl = this.zza.zzl();
        if (mapZzl != null) {
            return mapZzl.entrySet().remove(obj);
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (this.zza.zzr()) {
            return false;
        }
        int iZzv = this.zza.zzv();
        int iZzb = zzal.zzb(entry.getKey(), entry.getValue(), iZzv, zzak.zzi(this.zza), this.zza.zzA(), this.zza.zzB(), this.zza.zzC());
        if (iZzb == -1) {
            return false;
        }
        this.zza.zzq(iZzb, iZzv);
        this.zza.zzg--;
        this.zza.zzo();
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.size();
    }
}
