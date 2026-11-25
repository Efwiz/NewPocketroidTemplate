package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Iterator;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final /* synthetic */ class zzoo implements Iterable {
    public final /* synthetic */ JSONObject zza;

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        JSONObject jSONObject = this.zza;
        long j = zzos.zza;
        return jSONObject.keys();
    }
}
