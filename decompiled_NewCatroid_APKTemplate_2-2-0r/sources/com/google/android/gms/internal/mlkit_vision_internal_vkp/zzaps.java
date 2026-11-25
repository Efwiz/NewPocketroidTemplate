package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaps extends zzamc {
    final zzapw zza;
    zzame zzb = zzb();
    final /* synthetic */ zzapy zzc;

    zzaps(zzapy zzapyVar) {
        this.zzc = zzapyVar;
        this.zza = new zzapw(zzapyVar, null);
    }

    private final zzame zzb() {
        if (this.zza.hasNext()) {
            return this.zza.next().iterator();
        }
        return null;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb != null;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzame
    public final byte zza() {
        zzame zzameVar = this.zzb;
        if (zzameVar == null) {
            throw new NoSuchElementException();
        }
        byte bZza = zzameVar.zza();
        if (!this.zzb.hasNext()) {
            this.zzb = zzb();
        }
        return bZza;
    }
}
