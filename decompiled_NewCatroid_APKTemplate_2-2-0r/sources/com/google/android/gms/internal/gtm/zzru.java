package com.google.android.gms.internal.gtm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzru {
    private final List<zzrw> zza = new ArrayList();
    private final Map<String, List<zzro>> zzb = new HashMap();
    private String zzc = "";
    private int zzd = 0;

    private zzru() {
    }

    public final zzrs zza() {
        return new zzrs(this.zza, this.zzb, this.zzc, this.zzd, null);
    }

    public final zzru zzb(zzro zzroVar) {
        String strZzn = com.google.android.gms.tagmanager.zzfv.zzn(com.google.android.gms.tagmanager.zzfv.zzl(zzroVar.zzc().get(zzb.INSTANCE_NAME.toString())));
        List<zzro> arrayList = this.zzb.get(strZzn);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.zzb.put(strZzn, arrayList);
        }
        arrayList.add(zzroVar);
        return this;
    }

    public final zzru zzc(zzrw zzrwVar) {
        this.zza.add(zzrwVar);
        return this;
    }

    public final zzru zzd(int i) {
        this.zzd = i;
        return this;
    }

    public final zzru zze(String str) {
        this.zzc = str;
        return this;
    }

    /* synthetic */ zzru(zzrt zzrtVar) {
    }
}
