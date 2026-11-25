package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzkx<E> extends zzkr<E> {
    private final zzkz<E> zza;

    zzkx(zzkz<E> zzkzVar, int i) {
        super(zzkzVar.size(), i);
        this.zza = zzkzVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzkr
    protected final E zza(int i) {
        return this.zza.get(i);
    }
}
