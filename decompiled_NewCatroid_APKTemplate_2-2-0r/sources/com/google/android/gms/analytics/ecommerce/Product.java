package com.google.android.gms.analytics.ecommerce;

import com.google.android.gms.analytics.zzd;
import com.google.android.gms.analytics.zzj;
import com.google.android.gms.common.internal.Preconditions;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public class Product {
    Map<String, String> zza = new HashMap();

    public Product setBrand(String str) {
        zzb(CompressorStreamFactory.BROTLI, str);
        return this;
    }

    public Product setCategory(String str) {
        zzb(LanguageCodeUtil.CA, str);
        return this;
    }

    public Product setCouponCode(String str) {
        zzb("cc", str);
        return this;
    }

    public Product setCustomDimension(int i, String str) {
        zzb(zzd.zzc(i), str);
        return this;
    }

    public Product setCustomMetric(int i, int i2) {
        zzb(zzd.zzf(i), Integer.toString(i2));
        return this;
    }

    public Product setId(String str) {
        zzb("id", str);
        return this;
    }

    public Product setName(String str) {
        zzb("nm", str);
        return this;
    }

    public Product setPosition(int i) {
        zzb("ps", Integer.toString(i));
        return this;
    }

    public Product setPrice(double d) {
        zzb("pr", Double.toString(d));
        return this;
    }

    public Product setQuantity(int i) {
        zzb("qt", Integer.toString(i));
        return this;
    }

    public Product setVariant(String str) {
        zzb("va", str);
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
