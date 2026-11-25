package com.google.android.libraries.intelligence.acceleration;

import android.util.LongSparseArray;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaup;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzb {
    private final LongSparseArray<zza> zza;

    public zzb() {
        this(10L);
    }

    public final List<zzaup> zza(List<zzaup> list) {
        zza zzaVar;
        ArrayList arrayList = new ArrayList();
        for (zzaup zzaupVar : list) {
            synchronized (this.zza) {
                if (this.zza.get(zzaupVar.zzc()) == null) {
                    this.zza.put(zzaupVar.zzc(), new zza(10L));
                }
                zzaVar = this.zza.get(zzaupVar.zzc());
            }
            if (zzaVar.zza()) {
                arrayList.add(zzaupVar);
            }
        }
        return arrayList;
    }

    public zzb(long j) {
        this.zza = new LongSparseArray<>();
    }
}
