package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaoq extends zzaos {
    private zzaoq() {
        super(null);
    }

    /* synthetic */ zzaoq(zzaop zzaopVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaos
    final <L> List<L> zza(Object obj, long j) {
        zzaob zzaobVar = (zzaob) zzard.zzf(obj, j);
        if (zzaobVar.zzc()) {
            return zzaobVar;
        }
        int size = zzaobVar.size();
        zzaob zzaobVarZzd = zzaobVar.zzd(size == 0 ? 10 : size + size);
        zzard.zzs(obj, j, zzaobVarZzd);
        return zzaobVarZzd;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaos
    final void zzb(Object obj, long j) {
        ((zzaob) zzard.zzf(obj, j)).zzb();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaos
    final <E> void zzc(Object obj, Object obj2, long j) {
        zzaob zzaobVarZzd = (zzaob) zzard.zzf(obj, j);
        zzaob zzaobVar = (zzaob) zzard.zzf(obj2, j);
        int size = zzaobVarZzd.size();
        int size2 = zzaobVar.size();
        if (size > 0 && size2 > 0) {
            if (!zzaobVarZzd.zzc()) {
                zzaobVarZzd = zzaobVarZzd.zzd(size2 + size);
            }
            zzaobVarZzd.addAll(zzaobVar);
        }
        if (size > 0) {
            zzaobVar = zzaobVarZzd;
        }
        zzard.zzs(obj, j, zzaobVar);
    }
}
