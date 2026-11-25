package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzalz extends zzamc {
    final /* synthetic */ zzamk zza;
    private int zzb = 0;
    private final int zzc;

    zzalz(zzamk zzamkVar) {
        this.zza = zzamkVar;
        this.zzc = zzamkVar.zzd();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb < this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzame
    public final byte zza() {
        int i = this.zzb;
        if (i >= this.zzc) {
            throw new NoSuchElementException();
        }
        this.zzb = i + 1;
        return this.zza.zzb(i);
    }
}
