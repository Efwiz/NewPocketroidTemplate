package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zznr implements zznv {
    final List<zznv> zza;

    public zznr(Context context, zznq zznqVar) {
        ArrayList arrayList = new ArrayList();
        this.zza = arrayList;
        if (zznqVar.zzc()) {
            arrayList.add(new zzoe(context, zznqVar));
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznv
    public final void zza(zznz zznzVar) {
        Iterator<zznv> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().zza(zznzVar);
        }
    }
}
