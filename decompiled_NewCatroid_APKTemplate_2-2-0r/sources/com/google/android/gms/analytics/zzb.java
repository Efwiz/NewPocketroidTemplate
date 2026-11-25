package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.android.dx.rop.code.RegisterSpec;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzav;
import com.google.android.gms.internal.gtm.zzaw;
import com.google.android.gms.internal.gtm.zzax;
import com.google.android.gms.internal.gtm.zzay;
import com.google.android.gms.internal.gtm.zzaz;
import com.google.android.gms.internal.gtm.zzba;
import com.google.android.gms.internal.gtm.zzbb;
import com.google.android.gms.internal.gtm.zzbc;
import com.google.android.gms.internal.gtm.zzbd;
import com.google.android.gms.internal.gtm.zzbe;
import com.google.android.gms.internal.gtm.zzbf;
import com.google.android.gms.internal.gtm.zzbg;
import com.google.android.gms.internal.gtm.zzbh;
import com.google.android.gms.internal.gtm.zzbr;
import com.google.android.gms.internal.gtm.zzbt;
import com.google.android.gms.internal.gtm.zzbv;
import com.google.android.gms.internal.gtm.zzbx;
import com.google.android.gms.internal.gtm.zzex;
import com.google.android.gms.internal.gtm.zzfs;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzb extends zzbr implements zzt {
    private static DecimalFormat zza;
    private final zzbv zzb;
    private final String zzc;
    private final Uri zzd;

    public zzb(zzbv zzbvVar, String str) {
        super(zzbvVar);
        Preconditions.checkNotEmpty(str);
        this.zzb = zzbvVar;
        this.zzc = str;
        this.zzd = zza(str);
    }

    static Uri zza(String str) {
        Preconditions.checkNotEmpty(str);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    static String zzc(double d) {
        if (zza == null) {
            zza = new DecimalFormat("0.######");
        }
        return zza.format(d);
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0030  */
    public static Map<String, String> zzd(zzh zzhVar) {
        String strValueOf;
        HashMap map = new HashMap();
        zzaz zzazVar = (zzaz) zzhVar.zzc(zzaz.class);
        if (zzazVar != null) {
            for (Map.Entry<String, Object> entry : zzazVar.zzd().entrySet()) {
                Object value = entry.getValue();
                if (value == null) {
                    strValueOf = null;
                } else if (value instanceof String) {
                    strValueOf = (String) value;
                    if (TextUtils.isEmpty(strValueOf)) {
                        strValueOf = null;
                    }
                } else if (value instanceof Double) {
                    Double d = (Double) value;
                    if (d.doubleValue() != 0.0d) {
                        strValueOf = zzc(d.doubleValue());
                    } else {
                        strValueOf = null;
                    }
                } else if (!(value instanceof Boolean)) {
                    strValueOf = String.valueOf(value);
                } else if (value != Boolean.FALSE) {
                    strValueOf = SchemaSymbols.ATTVAL_TRUE_1;
                } else {
                    strValueOf = null;
                }
                if (strValueOf != null) {
                    map.put(entry.getKey(), strValueOf);
                }
            }
        }
        zzbe zzbeVar = (zzbe) zzhVar.zzc(zzbe.class);
        if (zzbeVar != null) {
            zzf(map, "t", zzbeVar.zzf());
            zzf(map, "cid", zzbeVar.zze());
            zzf(map, "uid", zzbeVar.zzg());
            zzf(map, "sc", null);
            zzg(map, "ni", zzbeVar.zzo());
            zzf(map, "adid", zzbeVar.zzd());
            zzg(map, "ate", zzbeVar.zzn());
        }
        zzbf zzbfVar = (zzbf) zzhVar.zzc(zzbf.class);
        if (zzbfVar != null) {
            zzf(map, "cd", null);
            double dZzd = zzbfVar.zzd();
            if (dZzd != 0.0d) {
                map.put("a", zzc(dZzd));
            }
            zzf(map, "dr", null);
        }
        if (((zzbc) zzhVar.zzc(zzbc.class)) != null) {
            zzf(map, "ec", null);
            zzf(map, "ea", null);
            zzf(map, LanguageCodeUtil.EL, null);
        }
        zzaw zzawVar = (zzaw) zzhVar.zzc(zzaw.class);
        if (zzawVar != null) {
            zzf(map, "cn", zzawVar.zzl());
            zzf(map, LanguageCodeUtil.CS, zzawVar.zzm());
            zzf(map, "cm", zzawVar.zzk());
            zzf(map, "ck", zzawVar.zzj());
            zzf(map, "cc", zzawVar.zzf());
            zzf(map, "ci", zzawVar.zzi());
            zzf(map, "anid", zzawVar.zze());
            zzf(map, "gclid", zzawVar.zzh());
            zzf(map, "dclid", zzawVar.zzg());
            zzf(map, FirebaseAnalytics.Param.ACLID, zzawVar.zzd());
        }
        if (((zzbd) zzhVar.zzc(zzbd.class)) != null) {
            zzf(map, "exd", null);
        }
        if (((zzbg) zzhVar.zzc(zzbg.class)) != null) {
            zzf(map, "sn", null);
            zzf(map, "sa", null);
            zzf(map, "st", null);
        }
        if (((zzbh) zzhVar.zzc(zzbh.class)) != null) {
            zzf(map, "utv", null);
            zzf(map, "utc", null);
            zzf(map, "utl", null);
        }
        zzax zzaxVar = (zzax) zzhVar.zzc(zzax.class);
        if (zzaxVar != null) {
            for (Map.Entry<Integer, String> entry2 : zzaxVar.zzd().entrySet()) {
                String strZzb = zzd.zzb(entry2.getKey().intValue());
                if (!TextUtils.isEmpty(strZzb)) {
                    map.put(strZzb, entry2.getValue());
                }
            }
        }
        zzay zzayVar = (zzay) zzhVar.zzc(zzay.class);
        if (zzayVar != null) {
            for (Map.Entry<Integer, Double> entry3 : zzayVar.zzd().entrySet()) {
                String strZze = zzd.zze(entry3.getKey().intValue());
                if (!TextUtils.isEmpty(strZze)) {
                    map.put(strZze, zzc(entry3.getValue().doubleValue()));
                }
            }
        }
        zzbb zzbbVar = (zzbb) zzhVar.zzc(zzbb.class);
        if (zzbbVar != null) {
            Iterator<Promotion> it = zzbbVar.zze().iterator();
            int i = 1;
            while (it.hasNext()) {
                map.putAll(it.next().zza(zzd.zzm(i)));
                i++;
            }
            Iterator<Product> it2 = zzbbVar.zzd().iterator();
            int i2 = 1;
            while (it2.hasNext()) {
                map.putAll(it2.next().zza(zzd.zzk(i2)));
                i2++;
            }
            int i3 = 1;
            for (Map.Entry<String, List<Product>> entry4 : zzbbVar.zzf().entrySet()) {
                List<Product> value2 = entry4.getValue();
                String strZzh = zzd.zzh(i3);
                int i4 = 1;
                for (Product product : value2) {
                    String strValueOf2 = String.valueOf(strZzh);
                    String strValueOf3 = String.valueOf(zzd.zzi(i4));
                    map.putAll(product.zza(strValueOf3.length() != 0 ? strValueOf2.concat(strValueOf3) : new String(strValueOf2)));
                    i4++;
                }
                if (!TextUtils.isEmpty(entry4.getKey())) {
                    map.put(String.valueOf(strZzh).concat("nm"), entry4.getKey());
                }
                i3++;
            }
        }
        zzba zzbaVar = (zzba) zzhVar.zzc(zzba.class);
        if (zzbaVar != null) {
            zzf(map, "ul", zzbaVar.zzd());
            int i5 = zzbaVar.zza;
            int i6 = zzbaVar.zzb;
            if (i5 > 0 && i6 > 0) {
                StringBuilder sb = new StringBuilder(23);
                sb.append(i5);
                sb.append("x");
                sb.append(i6);
                map.put(LanguageCodeUtil.SR, sb.toString());
            }
        }
        zzav zzavVar = (zzav) zzhVar.zzc(zzav.class);
        if (zzavVar != null) {
            zzf(map, "an", zzavVar.zzf());
            zzf(map, "aid", zzavVar.zzd());
            zzf(map, "aiid", zzavVar.zze());
            zzf(map, "av", zzavVar.zzg());
        }
        return map;
    }

    private static void zzf(Map<String, String> map, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        map.put(str, str2);
    }

    private static void zzg(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, SchemaSymbols.ATTVAL_TRUE_1);
        }
    }

    @Override // com.google.android.gms.analytics.zzt
    public final Uri zzb() {
        return this.zzd;
    }

    @Override // com.google.android.gms.analytics.zzt
    public final void zze(zzh zzhVar) {
        Preconditions.checkNotNull(zzhVar);
        Preconditions.checkArgument(zzhVar.zzm(), "Can't deliver not submitted measurement");
        Preconditions.checkNotMainThread("deliver should be called on worker thread");
        zzh zzhVar2 = new zzh(zzhVar);
        zzbe zzbeVar = (zzbe) zzhVar2.zzb(zzbe.class);
        if (TextUtils.isEmpty(zzbeVar.zzf())) {
            zzz().zzc(zzd(zzhVar2), "Ignoring measurement without type");
            return;
        }
        if (TextUtils.isEmpty(zzbeVar.zze())) {
            zzz().zzc(zzd(zzhVar2), "Ignoring measurement without client id");
            return;
        }
        if (this.zzb.zzc().getAppOptOut()) {
            return;
        }
        if (zzfs.zzj(0.0d, zzbeVar.zze())) {
            zzG("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(0.0d));
            return;
        }
        Map<String, String> mapZzd = zzd(zzhVar2);
        mapZzd.put(RegisterSpec.PREFIX, SchemaSymbols.ATTVAL_TRUE_1);
        mapZzd.put(Namer.SHARED_BOX_V, zzbt.zzb);
        mapZzd.put("tid", this.zzc);
        if (this.zzb.zzc().isDryRunEnabled()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : mapZzd.entrySet()) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(entry.getKey());
                sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
                sb.append(entry.getValue());
            }
            zzN("Dry run is enabled. GoogleAnalytics would have sent", sb.toString());
            return;
        }
        HashMap map = new HashMap();
        zzfs.zzg(map, "uid", zzbeVar.zzg());
        zzav zzavVar = (zzav) zzhVar.zzc(zzav.class);
        if (zzavVar != null) {
            zzfs.zzg(map, "an", zzavVar.zzf());
            zzfs.zzg(map, "aid", zzavVar.zzd());
            zzfs.zzg(map, "av", zzavVar.zzg());
            zzfs.zzg(map, "aiid", zzavVar.zze());
        }
        mapZzd.put("_s", String.valueOf(zzs().zza(new zzbx(0L, zzbeVar.zze(), this.zzc, !TextUtils.isEmpty(zzbeVar.zzd()), 0L, map))));
        zzs().zzh(new zzex(zzz(), mapZzd, zzhVar.zza(), true));
    }
}
