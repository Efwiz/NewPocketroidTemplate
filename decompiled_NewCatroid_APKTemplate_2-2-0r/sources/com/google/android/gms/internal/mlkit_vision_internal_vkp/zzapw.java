package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzapw implements Iterator<zzamg> {
    private final ArrayDeque<zzapy> zza;
    private zzamg zzb;

    /* synthetic */ zzapw(zzamk zzamkVar, zzapv zzapvVar) {
        if (!(zzamkVar instanceof zzapy)) {
            this.zza = null;
            this.zzb = (zzamg) zzamkVar;
            return;
        }
        zzapy zzapyVar = (zzapy) zzamkVar;
        ArrayDeque<zzapy> arrayDeque = new ArrayDeque<>(zzapyVar.zzf());
        this.zza = arrayDeque;
        arrayDeque.push(zzapyVar);
        this.zzb = zzb(zzapyVar.zzd);
    }

    private final zzamg zzb(zzamk zzamkVar) {
        while (zzamkVar instanceof zzapy) {
            zzapy zzapyVar = (zzapy) zzamkVar;
            this.zza.push(zzapyVar);
            zzamkVar = zzapyVar.zzd;
        }
        return (zzamg) zzamkVar;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb != null;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator
    /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
    public final zzamg next() {
        zzamg zzamgVarZzb;
        zzamg zzamgVar = this.zzb;
        if (zzamgVar == null) {
            throw new NoSuchElementException();
        }
        do {
            ArrayDeque<zzapy> arrayDeque = this.zza;
            zzamgVarZzb = null;
            if (arrayDeque == null || arrayDeque.isEmpty()) {
                break;
            }
            zzamgVarZzb = zzb(this.zza.pop().zze);
        } while (zzamgVarZzb.zzd() == 0);
        this.zzb = zzamgVarZzb;
        return zzamgVar;
    }
}
