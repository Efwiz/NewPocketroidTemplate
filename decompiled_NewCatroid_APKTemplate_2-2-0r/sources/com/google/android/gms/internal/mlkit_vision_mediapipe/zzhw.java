package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.Iterator;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhw implements Iterator<String> {
    private Iterator<String> zza;
    private final /* synthetic */ zzhu zzb;

    zzhw(zzhu zzhuVar) {
        this.zzb = zzhuVar;
        this.zza = zzhuVar.zza.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zza.next();
    }
}
