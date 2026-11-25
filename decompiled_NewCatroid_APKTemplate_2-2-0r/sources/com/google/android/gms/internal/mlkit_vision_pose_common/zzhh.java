package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhh implements zzhi {
    zzhh() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhi
    public final zzhg<?, ?> zza(Object obj) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhi
    public final Map<?, ?> zzb(Object obj) {
        return (zzhf) obj;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhi
    public final Object zzc(Object obj) {
        ((zzhf) obj).zzb();
        return obj;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhi
    public final Object zza(Object obj, Object obj2) {
        zzhf zzhfVarZza = (zzhf) obj;
        zzhf zzhfVar = (zzhf) obj2;
        if (!zzhfVar.isEmpty()) {
            if (!zzhfVarZza.zzc()) {
                zzhfVarZza = zzhfVarZza.zza();
            }
            zzhfVarZza.zza(zzhfVar);
        }
        return zzhfVarZza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhi
    public final int zza(int i, Object obj, Object obj2) {
        zzhf zzhfVar = (zzhf) obj;
        if (zzhfVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzhfVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
