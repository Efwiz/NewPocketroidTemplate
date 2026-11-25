package com.google.android.gms.internal.mlkit_vision_text;

import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaz extends zzap {
    final /* synthetic */ zzbb zza;

    @NullableDecl
    private final Object zzb;
    private int zzc;

    zzaz(zzbb zzbbVar, int i) {
        this.zza = zzbbVar;
        this.zzb = zzbbVar.zzb[i];
        this.zzc = i;
    }

    private final void zza() {
        int i = this.zzc;
        if (i == -1 || i >= this.zza.size() || !zzw.zza(this.zzb, this.zza.zzb[this.zzc])) {
            this.zzc = this.zza.zzr(this.zzb);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzap, java.util.Map.Entry
    @NullableDecl
    public final Object getKey() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzap, java.util.Map.Entry
    @NullableDecl
    public final Object getValue() {
        Map mapZzc = this.zza.zzc();
        if (mapZzc != null) {
            return mapZzc.get(this.zzb);
        }
        zza();
        int i = this.zzc;
        if (i == -1) {
            return null;
        }
        return this.zza.zzc[i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzap, java.util.Map.Entry
    public final Object setValue(Object obj) {
        Map mapZzc = this.zza.zzc();
        if (mapZzc != null) {
            return mapZzc.put(this.zzb, obj);
        }
        zza();
        int i = this.zzc;
        if (i == -1) {
            this.zza.put(this.zzb, obj);
            return null;
        }
        Object[] objArr = this.zza.zzc;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        return obj2;
    }
}
