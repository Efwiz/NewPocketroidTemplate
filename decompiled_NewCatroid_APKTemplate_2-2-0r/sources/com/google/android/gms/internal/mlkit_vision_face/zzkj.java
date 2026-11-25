package com.google.android.gms.internal.mlkit_vision_face;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkj implements zzkp {
    final List<zzkp> zza;

    public zzkj(Context context, zzki zzkiVar) {
        ArrayList arrayList = new ArrayList();
        this.zza = arrayList;
        if (zzkiVar.zzc()) {
            arrayList.add(new zzkz(context, zzkiVar));
        }
        if (zzkiVar.zzb()) {
            arrayList.add(new zzkt(context));
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzkp
    public final void zza(zzks zzksVar) {
        Iterator<zzkp> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().zza(zzksVar);
        }
    }
}
