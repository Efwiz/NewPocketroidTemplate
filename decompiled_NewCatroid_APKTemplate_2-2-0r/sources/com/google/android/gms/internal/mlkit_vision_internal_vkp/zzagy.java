package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzagy implements zzahc {
    final List<zzahc> zza;

    public zzagy(Context context, zzagx zzagxVar) {
        ArrayList arrayList = new ArrayList();
        this.zza = arrayList;
        if (zzagxVar.zzc()) {
            arrayList.add(new zzahk(context, zzagxVar));
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahc
    public final void zza(zzagv zzagvVar) {
        Iterator<zzahc> it = this.zza.iterator();
        while (it.hasNext()) {
            it.next().zza(zzagvVar);
        }
    }
}
