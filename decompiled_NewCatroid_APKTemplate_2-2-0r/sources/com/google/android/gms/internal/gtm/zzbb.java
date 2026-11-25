package com.google.android.gms.internal.gtm;

import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbb extends com.google.android.gms.analytics.zzj<zzbb> {
    private final List<Product> zza = new ArrayList();
    private final List<Promotion> zzb = new ArrayList();
    private final Map<String, List<Product>> zzc = new HashMap();

    public final String toString() {
        HashMap map = new HashMap();
        if (!this.zza.isEmpty()) {
            map.put("products", this.zza);
        }
        if (!this.zzb.isEmpty()) {
            map.put("promotions", this.zzb);
        }
        if (!this.zzc.isEmpty()) {
            map.put("impressions", this.zzc);
        }
        map.put("productAction", null);
        return zza(map);
    }

    @Override // com.google.android.gms.analytics.zzj
    public final /* bridge */ /* synthetic */ void zzc(com.google.android.gms.analytics.zzj zzjVar) {
        zzbb zzbbVar = (zzbb) zzjVar;
        zzbbVar.zza.addAll(this.zza);
        zzbbVar.zzb.addAll(this.zzb);
        for (Map.Entry<String, List<Product>> entry : this.zzc.entrySet()) {
            String key = entry.getKey();
            for (Product product : entry.getValue()) {
                if (product != null) {
                    String str = key == null ? "" : key;
                    if (!zzbbVar.zzc.containsKey(str)) {
                        zzbbVar.zzc.put(str, new ArrayList());
                    }
                    zzbbVar.zzc.get(str).add(product);
                }
            }
        }
    }

    public final List<Product> zzd() {
        return Collections.unmodifiableList(this.zza);
    }

    public final List<Promotion> zze() {
        return Collections.unmodifiableList(this.zzb);
    }

    public final Map<String, List<Product>> zzf() {
        return this.zzc;
    }
}
