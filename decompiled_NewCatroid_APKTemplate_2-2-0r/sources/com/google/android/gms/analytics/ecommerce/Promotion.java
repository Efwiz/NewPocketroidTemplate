package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.Preconditions;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public class Promotion {
    public static final String ACTION_CLICK = "click";
    public static final String ACTION_VIEW = "view";
    Map<String, String> zza = new HashMap();

    public Promotion setCreative(String str) {
        zzb("cr", str);
        return this;
    }

    public Promotion setId(String str) {
        zzb("id", str);
        return this;
    }

    public Promotion setName(String str) {
        zzb("nm", str);
        return this;
    }

    public Promotion setPosition(String str) {
        zzb("ps", str);
        return this;
    }

    public String toString() {
        return zzj.zzb(this.zza);
    }

    public final Map<String, String> zza(String str) {
        HashMap map = new HashMap();
        for (Map.Entry<String, String> entry : this.zza.entrySet()) {
            String strValueOf = String.valueOf(str);
            String strValueOf2 = String.valueOf(entry.getKey());
            map.put(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf), entry.getValue());
        }
        return map;
    }

    final void zzb(String str, String str2) {
        Preconditions.checkNotNull(str, "Name should be non-null");
        this.zza.put(str, str2);
    }
}
