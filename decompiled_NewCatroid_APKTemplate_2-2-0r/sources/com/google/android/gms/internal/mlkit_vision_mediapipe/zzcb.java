package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcb extends zzbz {
    private static final Map<zzy, zzcb[]> zza;
    private final zzy zzb;
    private final String zzc;

    public static zzcb zza(int i, zzy zzyVar, zzab zzabVar) {
        return (i >= 10 || !zzabVar.zzb()) ? new zzcb(i, zzyVar, zzabVar) : zza.get(zzyVar)[i];
    }

    private zzcb(int i, zzy zzyVar, zzab zzabVar) {
        String string;
        super(zzabVar, i);
        this.zzb = (zzy) zzcj.zza(zzyVar, "format char");
        if (zzabVar.zzb()) {
            string = zzyVar.zzd();
        } else {
            char cZza = zzyVar.zza();
            string = zzabVar.zza(new StringBuilder("%")).append(zzabVar.zzf() ? (char) (cZza & (-33)) : cZza).toString();
        }
        this.zzc = string;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzbz
    protected final void zza(zzby zzbyVar, Object obj) {
        zzbyVar.zza(obj, this.zzb, zzb());
    }

    static {
        EnumMap enumMap = new EnumMap(zzy.class);
        for (zzy zzyVar : zzy.values()) {
            zzcb[] zzcbVarArr = new zzcb[10];
            for (int i = 0; i < 10; i++) {
                zzcbVarArr[i] = new zzcb(i, zzyVar, zzab.zza());
            }
            enumMap.put(zzyVar, zzcbVarArr);
        }
        zza = Collections.unmodifiableMap(enumMap);
    }
}
