package com.google.android.gms.internal.mlkit_common;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzal<E> extends zzaf<E> {
    private final zzan<E> zza;

    zzal(zzan<E> zzanVar, int i) {
        super(zzanVar.size(), i);
        this.zza = zzanVar;
    }

    @Override // com.google.android.gms.internal.mlkit_common.zzaf
    protected final E zza(int i) {
        return this.zza.get(i);
    }
}
