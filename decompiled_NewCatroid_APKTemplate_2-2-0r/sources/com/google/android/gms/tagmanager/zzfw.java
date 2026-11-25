package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfw extends zzft {
    private static final String zza = com.google.android.gms.internal.gtm.zza.UNIVERSAL_ANALYTICS.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.ACCOUNT.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.ANALYTICS_PASS_THROUGH.toString();
    private static final String zzd = com.google.android.gms.internal.gtm.zzb.ENABLE_ECOMMERCE.toString();
    private static final String zze = com.google.android.gms.internal.gtm.zzb.ECOMMERCE_USE_DATA_LAYER.toString();
    private static final String zzf = com.google.android.gms.internal.gtm.zzb.ECOMMERCE_MACRO_DATA.toString();
    private static final String zzg = com.google.android.gms.internal.gtm.zzb.ANALYTICS_FIELDS.toString();
    private static final String zzh = com.google.android.gms.internal.gtm.zzb.TRACK_TRANSACTION.toString();
    private static final String zzi = com.google.android.gms.internal.gtm.zzb.TRANSACTION_DATALAYER_MAP.toString();
    private static final String zzj = com.google.android.gms.internal.gtm.zzb.TRANSACTION_ITEM_DATALAYER_MAP.toString();
    private static final List<String> zzk = Arrays.asList(ProductAction.ACTION_DETAIL, ProductAction.ACTION_CHECKOUT, ProductAction.ACTION_CHECKOUT_OPTION, "click", ProductAction.ACTION_ADD, ProductAction.ACTION_REMOVE, "purchase", "refund");
    private static final Pattern zzl = Pattern.compile("dimension(\\d+)");
    private static final Pattern zzm = Pattern.compile("metric(\\d+)");
    private static Map<String, String> zzn;
    private static Map<String, String> zzo;
    private final Set<String> zzp;
    private final zzfs zzq;
    private final DataLayer zzr;

    public zzfw(Context context, DataLayer dataLayer) {
        zzfs zzfsVar = new zzfs(context);
        super(zza, new String[0]);
        this.zzr = dataLayer;
        this.zzq = zzfsVar;
        HashSet hashSet = new HashSet();
        this.zzp = hashSet;
        hashSet.add("");
        hashSet.add("0");
        hashSet.add("false");
    }

    private final String zzd(String str) {
        Object obj = this.zzr.get(str);
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    private final Map<String, String> zzh(com.google.android.gms.internal.gtm.zzak zzakVar) {
        if (zzakVar == null) {
            return new HashMap();
        }
        Map<String, String> mapZzm = zzm(zzakVar);
        if (mapZzm == null) {
            return new HashMap();
        }
        String str = mapZzm.get("&aip");
        if (str != null && this.zzp.contains(str.toLowerCase())) {
            mapZzm.remove("&aip");
        }
        return mapZzm;
    }

    private static final void zzi(Map<String, String> map, String str, String str2) {
        if (str2 != null) {
            map.put(str, str2);
        }
    }

    private static final boolean zzj(Map<String, com.google.android.gms.internal.gtm.zzak> map, String str) {
        com.google.android.gms.internal.gtm.zzak zzakVar = map.get(str);
        if (zzakVar == null) {
            return false;
        }
        return zzfv.zzg(zzfv.zzl(zzakVar)).booleanValue();
    }

    private static final Double zzk(Object obj) {
        if (obj instanceof String) {
            try {
                return Double.valueOf((String) obj);
            } catch (NumberFormatException e) {
                String strValueOf = String.valueOf(e.getMessage());
                throw new RuntimeException(strValueOf.length() != 0 ? "Cannot convert the object to Double: ".concat(strValueOf) : new String("Cannot convert the object to Double: "));
            }
        }
        if (obj instanceof Integer) {
            return Double.valueOf(((Integer) obj).doubleValue());
        }
        if (obj instanceof Double) {
            return (Double) obj;
        }
        String strValueOf2 = String.valueOf(obj.toString());
        throw new RuntimeException(strValueOf2.length() != 0 ? "Cannot convert the object to Double: ".concat(strValueOf2) : new String("Cannot convert the object to Double: "));
    }

    private static final Integer zzl(Object obj) {
        if (obj instanceof String) {
            try {
                return Integer.valueOf((String) obj);
            } catch (NumberFormatException e) {
                String strValueOf = String.valueOf(e.getMessage());
                throw new RuntimeException(strValueOf.length() != 0 ? "Cannot convert the object to Integer: ".concat(strValueOf) : new String("Cannot convert the object to Integer: "));
            }
        }
        if (obj instanceof Double) {
            return Integer.valueOf(((Double) obj).intValue());
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        String strValueOf2 = String.valueOf(obj.toString());
        throw new RuntimeException(strValueOf2.length() != 0 ? "Cannot convert the object to Integer: ".concat(strValueOf2) : new String("Cannot convert the object to Integer: "));
    }

    private static final Map<String, String> zzm(com.google.android.gms.internal.gtm.zzak zzakVar) {
        Object objZzl = zzfv.zzl(zzakVar);
        if (!(objZzl instanceof Map)) {
            return null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : ((Map) objZzl).entrySet()) {
            linkedHashMap.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return linkedHashMap;
    }

    private static final Product zzn(Map<String, Object> map) {
        Product product = new Product();
        Object obj = map.get("id");
        if (obj != null) {
            product.setId(String.valueOf(obj));
        }
        Object obj2 = map.get("name");
        if (obj2 != null) {
            product.setName(String.valueOf(obj2));
        }
        Object obj3 = map.get("brand");
        if (obj3 != null) {
            product.setBrand(String.valueOf(obj3));
        }
        Object obj4 = map.get("category");
        if (obj4 != null) {
            product.setCategory(String.valueOf(obj4));
        }
        Object obj5 = map.get("variant");
        if (obj5 != null) {
            product.setVariant(String.valueOf(obj5));
        }
        Object obj6 = map.get(FirebaseAnalytics.Param.COUPON);
        if (obj6 != null) {
            product.setCouponCode(String.valueOf(obj6));
        }
        Object obj7 = map.get("position");
        if (obj7 != null) {
            product.setPosition(zzl(obj7).intValue());
        }
        Object obj8 = map.get(FirebaseAnalytics.Param.PRICE);
        if (obj8 != null) {
            product.setPrice(zzk(obj8).doubleValue());
        }
        Object obj9 = map.get(FirebaseAnalytics.Param.QUANTITY);
        if (obj9 != null) {
            product.setQuantity(zzl(obj9).intValue());
        }
        for (String str : map.keySet()) {
            Matcher matcher = zzl.matcher(str);
            if (matcher.matches()) {
                try {
                    product.setCustomDimension(Integer.parseInt(matcher.group(1)), String.valueOf(map.get(str)));
                } catch (NumberFormatException unused) {
                    String strValueOf = String.valueOf(str);
                    zzdh.zzc(strValueOf.length() != 0 ? "illegal number in custom dimension value: ".concat(strValueOf) : new String("illegal number in custom dimension value: "));
                }
            } else {
                Matcher matcher2 = zzm.matcher(str);
                if (matcher2.matches()) {
                    try {
                        product.setCustomMetric(Integer.parseInt(matcher2.group(1)), zzl(map.get(str)).intValue());
                    } catch (NumberFormatException unused2) {
                        String strValueOf2 = String.valueOf(str);
                        zzdh.zzc(strValueOf2.length() != 0 ? "illegal number in custom metric value: ".concat(strValueOf2) : new String("illegal number in custom metric value: "));
                    }
                }
            }
        }
        return product;
    }

    @Override // com.google.android.gms.tagmanager.zzft, com.google.android.gms.tagmanager.zzbu
    public final /* bridge */ /* synthetic */ boolean zzb() {
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x020f A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    /* JADX WARN: Code duplicated, block: B:104:0x021e A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    /* JADX WARN: Code duplicated, block: B:107:0x022d A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    /* JADX WARN: Code duplicated, block: B:110:0x0240 A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    /* JADX WARN: Code duplicated, block: B:113:0x0253 A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    /* JADX WARN: Code duplicated, block: B:116:0x0266 A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    /* JADX WARN: Code duplicated, block: B:117:0x0272 A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    /* JADX WARN: Code duplicated, block: B:12:0x0060  */
    /* JADX WARN: Code duplicated, block: B:216:0x0188 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:217:? A[LOOP:2: B:70:0x0176->B:217:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x017c  */
    /* JADX WARN: Code duplicated, block: B:76:0x0198  */
    /* JADX WARN: Code duplicated, block: B:79:0x01a2  */
    /* JADX WARN: Code duplicated, block: B:90:0x01d3 A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    /* JADX WARN: Code duplicated, block: B:92:0x01e4 A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    /* JADX WARN: Code duplicated, block: B:95:0x01f3 A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    /* JADX WARN: Code duplicated, block: B:98:0x0202 A[Catch: RuntimeException -> 0x027b, TryCatch #4 {RuntimeException -> 0x027b, blocks: (B:88:0x01cd, B:90:0x01d3, B:92:0x01e4, B:93:0x01eb, B:95:0x01f3, B:96:0x01fa, B:98:0x0202, B:99:0x0209, B:101:0x020f, B:102:0x0216, B:104:0x021e, B:105:0x0225, B:107:0x022d, B:108:0x0238, B:110:0x0240, B:111:0x024b, B:113:0x0253, B:114:0x025e, B:116:0x0266, B:118:0x0277, B:117:0x0272), top: B:204:0x01cd }] */
    @Override // com.google.android.gms.tagmanager.zzft
    public final void zzc(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        Map<String, String> mapZzm;
        Map<String, String> mapZzm2;
        Map map2;
        Map map3;
        List list;
        ProductAction productAction;
        Object obj;
        Object obj2;
        Object obj3;
        Object obj4;
        Object obj5;
        Object obj6;
        Object obj7;
        Object obj8;
        Object obj9;
        Iterator it;
        Tracker trackerZza = this.zzq.zza("_GTM_DEFAULT_TRACKER_");
        trackerZza.enableAdvertisingIdCollection(zzj(map, "collect_adid"));
        List<Map> list2 = null;
        if (!zzj(map, zzd)) {
            if (zzj(map, zzc)) {
                trackerZza.send(zzh(map.get(zzg)));
                return;
            }
            if (!zzj(map, zzh)) {
                zzdh.zzc("Ignoring unknown tag.");
                return;
            }
            String strZzd = zzd("transactionId");
            if (strZzd == null) {
                zzdh.zza("Cannot find transactionId in data layer.");
                return;
            }
            ArrayList arrayList = new ArrayList();
            try {
                Map<String, String> mapZzh = zzh(map.get(zzg));
                mapZzh.put("&t", "transaction");
                com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzi);
                if (zzakVar != null) {
                    mapZzm = zzm(zzakVar);
                } else {
                    if (zzn == null) {
                        HashMap map4 = new HashMap();
                        map4.put("transactionId", "&ti");
                        map4.put("transactionAffiliation", "&ta");
                        map4.put("transactionTax", "&tt");
                        map4.put("transactionShipping", "&ts");
                        map4.put("transactionTotal", "&tr");
                        map4.put("transactionCurrency", "&cu");
                        zzn = map4;
                    }
                    mapZzm = zzn;
                }
                for (Map.Entry<String, String> entry : mapZzm.entrySet()) {
                    zzi(mapZzh, entry.getValue(), zzd(entry.getKey()));
                }
                arrayList.add(mapZzh);
                Object obj10 = this.zzr.get("transactionProducts");
                if (obj10 != null) {
                    if (!(obj10 instanceof List)) {
                        throw new IllegalArgumentException("transactionProducts should be of type List.");
                    }
                    Iterator it2 = ((List) obj10).iterator();
                    while (it2.hasNext()) {
                        if (!(it2.next() instanceof Map)) {
                            throw new IllegalArgumentException("Each element of transactionProducts should be of type Map.");
                        }
                    }
                    list2 = (List) obj10;
                }
                if (list2 != null) {
                    for (Map map5 : list2) {
                        if (map5.get("name") == null) {
                            zzdh.zza("Unable to send transaction item hit due to missing 'name' field.");
                            return;
                        }
                        Map<String, String> mapZzh2 = zzh(map.get(zzg));
                        mapZzh2.put("&t", "item");
                        mapZzh2.put("&ti", strZzd);
                        com.google.android.gms.internal.gtm.zzak zzakVar2 = map.get(zzj);
                        if (zzakVar2 != null) {
                            mapZzm2 = zzm(zzakVar2);
                        } else {
                            if (zzo == null) {
                                HashMap map6 = new HashMap();
                                map6.put("name", "&in");
                                map6.put("sku", "&ic");
                                map6.put("category", "&iv");
                                map6.put(FirebaseAnalytics.Param.PRICE, "&ip");
                                map6.put(FirebaseAnalytics.Param.QUANTITY, "&iq");
                                map6.put(FirebaseAnalytics.Param.CURRENCY, "&cu");
                                zzo = map6;
                            }
                            mapZzm2 = zzo;
                        }
                        for (Map.Entry<String, String> entry2 : mapZzm2.entrySet()) {
                            zzi(mapZzh2, entry2.getValue(), (String) map5.get(entry2.getKey()));
                        }
                        arrayList.add(mapZzh2);
                    }
                }
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    trackerZza.send((Map) it3.next());
                }
                return;
            } catch (IllegalArgumentException e) {
                zzdh.zzb("Unable to send transaction", e);
                return;
            }
        }
        HitBuilders.ScreenViewBuilder screenViewBuilder = new HitBuilders.ScreenViewBuilder();
        Map<String, String> mapZzh3 = zzh(map.get(zzg));
        screenViewBuilder.setAll(mapZzh3);
        if (zzj(map, zze)) {
            Object obj11 = this.zzr.get("ecommerce");
            if (obj11 instanceof Map) {
                map2 = (Map) obj11;
            } else {
                map2 = null;
            }
        } else {
            Object objZzl = zzfv.zzl(map.get(zzf));
            if (objZzl instanceof Map) {
                map2 = (Map) objZzl;
            } else {
                map2 = null;
            }
        }
        if (map2 != null) {
            String str = mapZzh3.get("&cu");
            if (str == null) {
                str = (String) map2.get("currencyCode");
            }
            if (str != null) {
                screenViewBuilder.set("&cu", str);
            }
            Object obj12 = map2.get("impressions");
            if (obj12 instanceof List) {
                for (Map map7 : (List) obj12) {
                    try {
                        screenViewBuilder.addImpression(zzn(map7), (String) map7.get(SchemaSymbols.ATTVAL_LIST));
                    } catch (RuntimeException e2) {
                        String strValueOf = String.valueOf(e2.getMessage());
                        zzdh.zza(strValueOf.length() != 0 ? "Failed to extract a product from DataLayer. ".concat(strValueOf) : new String("Failed to extract a product from DataLayer. "));
                    }
                }
            }
            if (map2.containsKey("promoClick")) {
                list2 = (List) ((Map) map2.get("promoClick")).get("promotions");
            } else if (map2.containsKey("promoView")) {
                list2 = (List) ((Map) map2.get("promoView")).get("promotions");
            }
            if (list2 == null) {
                while (r3.hasNext()) {
                    if (map2.containsKey(str)) {
                        map3 = (Map) map2.get(str);
                        list = (List) map3.get("products");
                        if (list != null) {
                            it = list.iterator();
                            while (it.hasNext()) {
                                screenViewBuilder.addProduct(zzn((Map) it.next()));
                            }
                        }
                        if (map3.containsKey("actionField")) {
                            Map map8 = (Map) map3.get("actionField");
                            productAction = new ProductAction(str);
                            obj = map8.get("id");
                            if (obj != null) {
                                productAction.setTransactionId(String.valueOf(obj));
                            }
                            obj2 = map8.get(FirebaseAnalytics.Param.AFFILIATION);
                            if (obj2 != null) {
                                productAction.setTransactionAffiliation(String.valueOf(obj2));
                            }
                            obj3 = map8.get(FirebaseAnalytics.Param.COUPON);
                            if (obj3 != null) {
                                productAction.setTransactionCouponCode(String.valueOf(obj3));
                            }
                            obj4 = map8.get(SchemaSymbols.ATTVAL_LIST);
                            if (obj4 != null) {
                                productAction.setProductActionList(String.valueOf(obj4));
                            }
                            obj5 = map8.get("option");
                            if (obj5 != null) {
                                productAction.setCheckoutOptions(String.valueOf(obj5));
                            }
                            obj6 = map8.get("revenue");
                            if (obj6 != null) {
                                productAction.setTransactionRevenue(zzk(obj6).doubleValue());
                            }
                            obj7 = map8.get(FirebaseAnalytics.Param.TAX);
                            if (obj7 != null) {
                                productAction.setTransactionTax(zzk(obj7).doubleValue());
                            }
                            obj8 = map8.get(FirebaseAnalytics.Param.SHIPPING);
                            if (obj8 != null) {
                                productAction.setTransactionShipping(zzk(obj8).doubleValue());
                            }
                            obj9 = map8.get("step");
                            if (obj9 != null) {
                                productAction.setCheckoutStep(zzl(obj9).intValue());
                            }
                        } else {
                            productAction = new ProductAction(str);
                        }
                        screenViewBuilder.setProductAction(productAction);
                        break;
                    }
                }
            } else {
                for (Map map9 : list2) {
                    try {
                        Promotion promotion = new Promotion();
                        String str2 = (String) map9.get("id");
                        if (str2 != null) {
                            promotion.setId(str2);
                        }
                        String str3 = (String) map9.get("name");
                        if (str3 != null) {
                            promotion.setName(str3);
                        }
                        String str4 = (String) map9.get("creative");
                        if (str4 != null) {
                            promotion.setCreative(str4);
                        }
                        String str5 = (String) map9.get("position");
                        if (str5 != null) {
                            promotion.setPosition(str5);
                        }
                        screenViewBuilder.addPromotion(promotion);
                    } catch (RuntimeException e3) {
                        String strValueOf2 = String.valueOf(e3.getMessage());
                        zzdh.zza(strValueOf2.length() != 0 ? "Failed to extract a promotion from DataLayer. ".concat(strValueOf2) : new String("Failed to extract a promotion from DataLayer. "));
                    }
                }
                if (!map2.containsKey("promoClick")) {
                    screenViewBuilder.set("&promoa", Promotion.ACTION_VIEW);
                    for (String str6 : zzk) {
                        if (map2.containsKey(str6)) {
                            map3 = (Map) map2.get(str6);
                            list = (List) map3.get("products");
                            if (list != null) {
                                it = list.iterator();
                                while (it.hasNext()) {
                                    try {
                                        screenViewBuilder.addProduct(zzn((Map) it.next()));
                                    } catch (RuntimeException e4) {
                                        String strValueOf3 = String.valueOf(e4.getMessage());
                                        zzdh.zza(strValueOf3.length() != 0 ? "Failed to extract a product from DataLayer. ".concat(strValueOf3) : new String("Failed to extract a product from DataLayer. "));
                                    }
                                }
                            }
                            try {
                                if (map3.containsKey("actionField")) {
                                    Map map10 = (Map) map3.get("actionField");
                                    productAction = new ProductAction(str6);
                                    obj = map10.get("id");
                                    if (obj != null) {
                                        productAction.setTransactionId(String.valueOf(obj));
                                    }
                                    obj2 = map10.get(FirebaseAnalytics.Param.AFFILIATION);
                                    if (obj2 != null) {
                                        productAction.setTransactionAffiliation(String.valueOf(obj2));
                                    }
                                    obj3 = map10.get(FirebaseAnalytics.Param.COUPON);
                                    if (obj3 != null) {
                                        productAction.setTransactionCouponCode(String.valueOf(obj3));
                                    }
                                    obj4 = map10.get(SchemaSymbols.ATTVAL_LIST);
                                    if (obj4 != null) {
                                        productAction.setProductActionList(String.valueOf(obj4));
                                    }
                                    obj5 = map10.get("option");
                                    if (obj5 != null) {
                                        productAction.setCheckoutOptions(String.valueOf(obj5));
                                    }
                                    obj6 = map10.get("revenue");
                                    if (obj6 != null) {
                                        productAction.setTransactionRevenue(zzk(obj6).doubleValue());
                                    }
                                    obj7 = map10.get(FirebaseAnalytics.Param.TAX);
                                    if (obj7 != null) {
                                        productAction.setTransactionTax(zzk(obj7).doubleValue());
                                    }
                                    obj8 = map10.get(FirebaseAnalytics.Param.SHIPPING);
                                    if (obj8 != null) {
                                        productAction.setTransactionShipping(zzk(obj8).doubleValue());
                                    }
                                    obj9 = map10.get("step");
                                    if (obj9 != null) {
                                        productAction.setCheckoutStep(zzl(obj9).intValue());
                                    }
                                } else {
                                    productAction = new ProductAction(str6);
                                }
                                screenViewBuilder.setProductAction(productAction);
                                break;
                            } catch (RuntimeException e5) {
                                String strValueOf4 = String.valueOf(e5.getMessage());
                                zzdh.zza(strValueOf4.length() != 0 ? "Failed to extract a product action from DataLayer. ".concat(strValueOf4) : new String("Failed to extract a product action from DataLayer. "));
                                break;
                            }
                        }
                    }
                } else {
                    screenViewBuilder.set("&promoa", "click");
                }
            }
        }
        trackerZza.send(screenViewBuilder.build());
    }
}
