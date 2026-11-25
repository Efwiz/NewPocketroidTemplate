package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.gtm.zzro;
import com.google.android.gms.internal.gtm.zzrs;
import com.google.android.gms.internal.gtm.zzrw;
import com.google.android.gms.internal.gtm.zzsa;
import io.netty.util.internal.StringUtil;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzeu {
    private static final zzds<com.google.android.gms.internal.gtm.zzak> zza = new zzds<>(zzfv.zzb(), true);
    private final zzrs zzb;
    private final Map<String, zzbu> zzc;
    private final Map<String, zzbu> zzd;
    private final Map<String, zzbu> zze;
    private final Set<zzrw> zzf;
    private final DataLayer zzg;
    private final Map<String, zzet> zzh;
    private volatile String zzi;
    private int zzj;
    private final zzdl zzk;
    private final zzdb zzl;
    private final zzdb zzm;

    public zzeu(Context context, zzrs zzrsVar, DataLayer dataLayer, zzaq zzaqVar, zzaq zzaqVar2, zzdl zzdlVar, byte[] bArr) {
        this.zzb = zzrsVar;
        HashSet<zzrw> hashSet = new HashSet(zzrsVar.zzc());
        this.zzf = hashSet;
        this.zzg = dataLayer;
        this.zzk = zzdlVar;
        zzen zzenVar = new zzen(this);
        new zzs();
        this.zzl = zzs.zza(1048576, zzenVar);
        zzeo zzeoVar = new zzeo(this);
        new zzs();
        this.zzm = zzs.zza(1048576, zzeoVar);
        HashMap map = new HashMap();
        this.zzc = map;
        zzj(map, new zzo(context));
        zzj(map, new zzar(zzaqVar2));
        zzj(map, new zzbf(dataLayer));
        zzj(map, new zzfw(context, dataLayer));
        HashMap map2 = new HashMap();
        this.zzd = map2;
        zzj(map2, new zzao());
        zzj(map2, new zzbr());
        zzj(map2, new zzbs());
        zzj(map2, new zzbw());
        zzj(map2, new zzbx());
        zzj(map2, new zzdd());
        zzj(map2, new zzde());
        zzj(map2, new zzee());
        zzj(map2, new zzfk());
        HashMap map3 = new HashMap();
        this.zze = map3;
        zzj(map3, new zze(zzd.zzb(context)));
        zzj(map3, new zzf(zzd.zzb(context)));
        zzj(map3, new zzh(context));
        zzj(map3, new zzi(context));
        zzj(map3, new zzj(context));
        zzj(map3, new zzk(context));
        zzj(map3, new zzl(context));
        zzj(map3, new zzt());
        zzj(map3, new zzan(zzrsVar.zzb()));
        zzj(map3, new zzar(zzaqVar));
        zzj(map3, new zzay(dataLayer));
        zzj(map3, new zzbi(context));
        zzj(map3, new zzbj());
        zzj(map3, new zzbq());
        zzj(map3, new zzbt(this));
        zzj(map3, new zzby());
        zzj(map3, new zzbz());
        zzj(map3, new zzcw(context));
        zzj(map3, new zzcy());
        zzj(map3, new zzdc());
        zzj(map3, new zzdi());
        zzj(map3, new zzdj(context));
        zzj(map3, new zzdt());
        zzj(map3, new zzdx());
        zzj(map3, new zzeb());
        zzj(map3, new zzed());
        zzj(map3, new zzef(context));
        zzj(map3, new zzev());
        zzj(map3, new zzew());
        zzj(map3, new zzfq());
        zzj(map3, new zzfx());
        this.zzh = new HashMap();
        for (zzrw zzrwVar : hashSet) {
            for (int i = 0; i < zzrwVar.zza().size(); i++) {
                zzro zzroVar = zzrwVar.zza().get(i);
                zzet zzetVarZzg = zzg(this.zzh, zzh(zzroVar));
                zzetVarZzg.zzk(zzrwVar);
                zzetVarZzg.zzg(zzrwVar, zzroVar);
                zzetVarZzg.zzh(zzrwVar, "Unknown");
            }
            for (int i2 = 0; i2 < zzrwVar.zzf().size(); i2++) {
                zzro zzroVar2 = zzrwVar.zzf().get(i2);
                zzet zzetVarZzg2 = zzg(this.zzh, zzh(zzroVar2));
                zzetVarZzg2.zzk(zzrwVar);
                zzetVarZzg2.zzi(zzrwVar, zzroVar2);
                zzetVarZzg2.zzj(zzrwVar, "Unknown");
            }
        }
        for (Map.Entry<String, List<zzro>> entry : this.zzb.zzd().entrySet()) {
            for (zzro zzroVar3 : entry.getValue()) {
                if (!zzfv.zzg(zzfv.zzl(zzroVar3.zzc().get(com.google.android.gms.internal.gtm.zzb.NOT_DEFAULT_MACRO.toString()))).booleanValue()) {
                    zzg(this.zzh, entry.getKey()).zzl(zzroVar3);
                }
            }
        }
    }

    private static zzet zzg(Map<String, zzet> map, String str) {
        zzet zzetVar = map.get(str);
        if (zzetVar != null) {
            return zzetVar;
        }
        zzet zzetVar2 = new zzet();
        map.put(str, zzetVar2);
        return zzetVar2;
    }

    private static String zzh(zzro zzroVar) {
        return zzfv.zzn(zzfv.zzl(zzroVar.zzc().get(com.google.android.gms.internal.gtm.zzb.INSTANCE_NAME.toString())));
    }

    private final String zzi() {
        if (this.zzj <= 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(this.zzj));
        for (int i = 2; i < this.zzj; i++) {
            sb.append(StringUtil.SPACE);
        }
        sb.append(": ");
        return sb.toString();
    }

    private static void zzj(Map<String, zzbu> map, zzbu zzbuVar) {
        if (map.containsKey(zzbuVar.zze())) {
            String strValueOf = String.valueOf(zzbuVar.zze());
            throw new IllegalArgumentException(strValueOf.length() != 0 ? "Duplicate function type name: ".concat(strValueOf) : new String("Duplicate function type name: "));
        }
        map.put(zzbuVar.zze(), zzbuVar);
    }

    private final void zzk(com.google.android.gms.internal.gtm.zzak zzakVar, Set<String> set) {
        zzds<com.google.android.gms.internal.gtm.zzak> zzdsVarZzo;
        if (zzakVar == null || (zzdsVarZzo = zzo(zzakVar, set, new zzdq())) == zza) {
            return;
        }
        Object objZzl = zzfv.zzl(zzdsVarZzo.zza());
        if (objZzl instanceof Map) {
            this.zzg.push((Map) objZzl);
            return;
        }
        if (!(objZzl instanceof List)) {
            zzdh.zzc("pushAfterEvaluate: value not a Map or List");
            return;
        }
        for (Object obj : (List) objZzl) {
            if (obj instanceof Map) {
                this.zzg.push((Map) obj);
            } else {
                zzdh.zzc("pushAfterEvaluate: value not a Map");
            }
        }
    }

    private final zzds<Set<zzro>> zzl(Set<zzrw> set, Set<String> set2, zzer zzerVar, zzdp zzdpVar) {
        zzds zzdsVar;
        Set<zzro> hashSet = new HashSet<>();
        Set<zzro> hashSet2 = new HashSet<>();
        Iterator<zzrw> it = set.iterator();
        while (true) {
            boolean z = true;
            while (true) {
                if (!it.hasNext()) {
                    hashSet.removeAll(hashSet2);
                    return new zzds<>(hashSet, z);
                }
                zzrw next = it.next();
                zzdo zzdoVar = new zzdo();
                Iterator<zzro> it2 = next.zzd().iterator();
                while (true) {
                    boolean z2 = true;
                    while (true) {
                        if (!it2.hasNext()) {
                            Iterator<zzro> it3 = next.zze().iterator();
                            while (true) {
                                if (!it3.hasNext()) {
                                    zzfv.zzc(true);
                                    zzdsVar = new zzds(true, z2);
                                    break;
                                }
                                zzds<Boolean> zzdsVarZzf = zzf(it3.next(), set2, new zzdn());
                                if (!zzdsVarZzf.zza().booleanValue()) {
                                    zzfv.zzc(false);
                                    zzdsVar = new zzds(false, zzdsVarZzf.zzb());
                                    break;
                                }
                                z2 = z2 && zzdsVarZzf.zzb();
                            }
                        } else {
                            zzds<Boolean> zzdsVarZzf2 = zzf(it2.next(), set2, new zzdn());
                            if (zzdsVarZzf2.zza().booleanValue()) {
                                zzfv.zzc(false);
                                zzdsVar = new zzds(false, zzdsVarZzf2.zzb());
                                break;
                            }
                            if (!z2 || !zzdsVarZzf2.zzb()) {
                                z2 = false;
                            }
                        }
                    }
                }
                if (((Boolean) zzdsVar.zza()).booleanValue()) {
                    zzerVar.zza(next, hashSet, hashSet2, zzdoVar);
                }
                if (!z || !zzdsVar.zzb()) {
                    z = false;
                }
            }
        }
    }

    private final zzds<com.google.android.gms.internal.gtm.zzak> zzm(String str, Set<String> set, zzdm zzdmVar) {
        zzro next;
        this.zzj++;
        zzes zzesVar = (zzes) this.zzm.zza(str);
        if (zzesVar != null) {
            zzk(zzesVar.zzb(), set);
            this.zzj--;
            return zzesVar.zzc();
        }
        zzet zzetVar = this.zzh.get(str);
        if (zzetVar == null) {
            String strZzi = zzi();
            StringBuilder sb = new StringBuilder(String.valueOf(strZzi).length() + 15 + String.valueOf(str).length());
            sb.append(strZzi);
            sb.append("Invalid macro: ");
            sb.append(str);
            zzdh.zza(sb.toString());
            this.zzj--;
            return zza;
        }
        zzds<Set<zzro>> zzdsVarZzl = zzl(zzetVar.zzf(), set, new zzep(this, zzetVar.zzc(), zzetVar.zzb(), zzetVar.zze(), zzetVar.zzd()), new zzdp());
        if (zzdsVarZzl.zza().isEmpty()) {
            next = zzetVar.zza();
        } else {
            if (zzdsVarZzl.zza().size() > 1) {
                String strZzi2 = zzi();
                StringBuilder sb2 = new StringBuilder(String.valueOf(strZzi2).length() + 37 + String.valueOf(str).length());
                sb2.append(strZzi2);
                sb2.append("Multiple macros active for macroName ");
                sb2.append(str);
                zzdh.zzc(sb2.toString());
            }
            next = zzdsVarZzl.zza().iterator().next();
        }
        if (next == null) {
            this.zzj--;
            return zza;
        }
        zzds<com.google.android.gms.internal.gtm.zzak> zzdsVarZzn = zzn(this.zze, next, set, new zzdn());
        boolean z = zzdsVarZzl.zzb() && zzdsVarZzn.zzb();
        zzds<com.google.android.gms.internal.gtm.zzak> zzdsVar = zza;
        if (zzdsVarZzn != zzdsVar) {
            zzdsVar = new zzds<>(zzdsVarZzn.zza(), z);
        }
        com.google.android.gms.internal.gtm.zzak zzakVarZza = next.zza();
        if (zzdsVar.zzb()) {
            this.zzm.zzb(str, new zzes(zzdsVar, zzakVarZza));
        }
        zzk(zzakVarZza, set);
        this.zzj--;
        return zzdsVar;
    }

    private final zzds<com.google.android.gms.internal.gtm.zzak> zzn(Map<String, zzbu> map, zzro zzroVar, Set<String> set, zzdn zzdnVar) {
        com.google.android.gms.internal.gtm.zzak zzakVar = zzroVar.zzc().get(com.google.android.gms.internal.gtm.zzb.FUNCTION.toString());
        if (zzakVar == null) {
            zzdh.zza("No function id in properties");
            return zza;
        }
        String strZzn = zzakVar.zzn();
        zzbu zzbuVar = map.get(strZzn);
        if (zzbuVar == null) {
            zzdh.zza(String.valueOf(strZzn).concat(" has no backing implementation."));
            return zza;
        }
        zzds<com.google.android.gms.internal.gtm.zzak> zzdsVar = (zzds) this.zzl.zza(zzroVar);
        if (zzdsVar != null) {
            return zzdsVar;
        }
        HashMap map2 = new HashMap();
        boolean z = true;
        for (Map.Entry<String, com.google.android.gms.internal.gtm.zzak> entry : zzroVar.zzc().entrySet()) {
            entry.getKey();
            com.google.android.gms.internal.gtm.zzak value = entry.getValue();
            entry.getValue();
            zzds<com.google.android.gms.internal.gtm.zzak> zzdsVarZzo = zzo(value, set, new zzdq());
            zzds<com.google.android.gms.internal.gtm.zzak> zzdsVar2 = zza;
            if (zzdsVarZzo == zzdsVar2) {
                return zzdsVar2;
            }
            if (zzdsVarZzo.zzb()) {
                zzroVar.zzd(entry.getKey(), zzdsVarZzo.zza());
            } else {
                z = false;
            }
            map2.put(entry.getKey(), zzdsVarZzo.zza());
        }
        if (zzbuVar.zzg(map2.keySet())) {
            boolean z2 = z && zzbuVar.zzb();
            zzds<com.google.android.gms.internal.gtm.zzak> zzdsVar3 = new zzds<>(zzbuVar.zza(map2), z2);
            if (z2) {
                this.zzl.zzb(zzroVar, zzdsVar3);
            }
            zzdsVar3.zza();
            return zzdsVar3;
        }
        String strValueOf = String.valueOf(zzbuVar.zzf());
        String strValueOf2 = String.valueOf(map2.keySet());
        StringBuilder sb = new StringBuilder(String.valueOf(strZzn).length() + 43 + String.valueOf(strValueOf).length() + String.valueOf(strValueOf2).length());
        sb.append("Incorrect keys for function ");
        sb.append(strZzn);
        sb.append(" required ");
        sb.append(strValueOf);
        sb.append(" had ");
        sb.append(strValueOf2);
        zzdh.zza(sb.toString());
        return zza;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final zzds<com.google.android.gms.internal.gtm.zzak> zzo(com.google.android.gms.internal.gtm.zzak zzakVar, Set<String> set, zzdq zzdqVar) {
        if (!zzakVar.zzN()) {
            return new zzds<>(zzakVar, true);
        }
        int iZzO = zzakVar.zzO();
        if (iZzO == 2) {
            com.google.android.gms.internal.gtm.zzal zzalVar = (com.google.android.gms.internal.gtm.zzal) zzsa.zza(zzakVar).zzZ();
            zzalVar.zzj();
            for (int i = 0; i < zzakVar.zza(); i++) {
                zzds<com.google.android.gms.internal.gtm.zzak> zzdsVarZzo = zzo(zzakVar.zzj(i), set, new zzdq());
                zzds<com.google.android.gms.internal.gtm.zzak> zzdsVar = zza;
                if (zzdsVarZzo == zzdsVar) {
                    return zzdsVar;
                }
                zzalVar.zze(zzdsVarZzo.zza());
            }
            return new zzds<>((com.google.android.gms.internal.gtm.zzak) zzalVar.zzC(), false);
        }
        if (iZzO == 3) {
            com.google.android.gms.internal.gtm.zzal zzalVar2 = (com.google.android.gms.internal.gtm.zzal) zzsa.zza(zzakVar).zzZ();
            if (zzakVar.zzc() != zzakVar.zzd()) {
                String strValueOf = String.valueOf(zzakVar.toString());
                zzdh.zza(strValueOf.length() != 0 ? "Invalid serving value: ".concat(strValueOf) : new String("Invalid serving value: "));
                return zza;
            }
            zzalVar2.zzk();
            zzalVar2.zzl();
            for (int i2 = 0; i2 < zzakVar.zzc(); i2++) {
                zzds<com.google.android.gms.internal.gtm.zzak> zzdsVarZzo2 = zzo(zzakVar.zzk(i2), set, new zzdq());
                zzds<com.google.android.gms.internal.gtm.zzak> zzdsVarZzo3 = zzo(zzakVar.zzl(i2), set, new zzdq());
                zzds<com.google.android.gms.internal.gtm.zzak> zzdsVar2 = zza;
                if (zzdsVarZzo2 == zzdsVar2 || zzdsVarZzo3 == zzdsVar2) {
                    return zzdsVar2;
                }
                zzalVar2.zzf(zzdsVarZzo2.zza());
                zzalVar2.zzg(zzdsVarZzo3.zza());
            }
            return new zzds<>((com.google.android.gms.internal.gtm.zzak) zzalVar2.zzC(), false);
        }
        if (iZzO != 4) {
            if (iZzO != 7) {
                String string = Integer.toString(zzakVar.zzO());
                StringBuilder sb = new StringBuilder(String.valueOf(string).length() + 14);
                sb.append("Unknown type: ");
                sb.append(string);
                zzdh.zza(sb.toString());
                return zza;
            }
            com.google.android.gms.internal.gtm.zzal zzalVar3 = (com.google.android.gms.internal.gtm.zzal) zzsa.zza(zzakVar).zzZ();
            zzalVar3.zzm();
            for (int i3 = 0; i3 < zzakVar.zze(); i3++) {
                zzds<com.google.android.gms.internal.gtm.zzak> zzdsVarZzo4 = zzo(zzakVar.zzm(i3), set, new zzdq());
                zzds<com.google.android.gms.internal.gtm.zzak> zzdsVar3 = zza;
                if (zzdsVarZzo4 == zzdsVar3) {
                    return zzdsVar3;
                }
                zzalVar3.zzh(zzdsVarZzo4.zza());
            }
            return new zzds<>((com.google.android.gms.internal.gtm.zzak) zzalVar3.zzC(), false);
        }
        if (set.contains(zzakVar.zzo())) {
            String strZzo = zzakVar.zzo();
            String string2 = set.toString();
            StringBuilder sb2 = new StringBuilder(String.valueOf(strZzo).length() + 79 + String.valueOf(string2).length());
            sb2.append("Macro cycle detected.  Current macro reference: ");
            sb2.append(strZzo);
            sb2.append(".  Previous macro references: ");
            sb2.append(string2);
            sb2.append(".");
            zzdh.zza(sb2.toString());
            return zza;
        }
        set.add(zzakVar.zzo());
        zzds<com.google.android.gms.internal.gtm.zzak> zzdsVarZzm = zzm(zzakVar.zzo(), set, new zzdm());
        for (com.google.android.gms.internal.gtm.zzao zzaoVar : (com.google.android.gms.internal.gtm.zzao[]) zzakVar.zzq().toArray(new com.google.android.gms.internal.gtm.zzao[0])) {
            if (zzfv.zzl(zzdsVarZzm.zza()) instanceof String) {
                com.google.android.gms.internal.gtm.zzao zzaoVar2 = com.google.android.gms.internal.gtm.zzao.ESCAPE_HTML;
                if (zzaoVar.ordinal() != 11) {
                    String strValueOf2 = String.valueOf(zzaoVar);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf2).length() + 28);
                    sb3.append("Unsupported Value Escaping: ");
                    sb3.append(strValueOf2);
                    zzdh.zza(sb3.toString());
                } else {
                    try {
                        zzdsVarZzm = new zzds<>(zzfv.zzc(zzfy.zza(zzfv.zzn(zzfv.zzl(zzdsVarZzm.zza())))), zzdsVarZzm.zzb());
                    } catch (UnsupportedEncodingException e) {
                        zzdh.zzb("Escape URI: unsupported encoding", e);
                    }
                }
            } else {
                zzdh.zza("Escaping can only be applied to strings.");
            }
        }
        set.remove(zzakVar.zzo());
        return zzdsVarZzm;
    }

    public final zzds<com.google.android.gms.internal.gtm.zzak> zza(String str) {
        this.zzj = 0;
        return zzm(str, new HashSet(), new zzdm());
    }

    final synchronized String zzb() {
        return this.zzi;
    }

    public final synchronized void zzc(String str) {
        zzd(str);
        Iterator<zzro> it = zzl(this.zzf, new HashSet(), new zzeq(this), new zzdp()).zza().iterator();
        while (it.hasNext()) {
            zzn(this.zzc, it.next(), new HashSet(), new zzdn());
        }
        zzd(null);
    }

    final synchronized void zzd(String str) {
        this.zzi = str;
    }

    public final synchronized void zze(List<com.google.android.gms.internal.gtm.zzag> list) {
        for (com.google.android.gms.internal.gtm.zzag zzagVar : list) {
            if (zzagVar.zzf() && zzagVar.zzd().startsWith("gaExperiment:")) {
                DataLayer dataLayer = this.zzg;
                if (zzagVar.zze()) {
                    Iterator<com.google.android.gms.internal.gtm.zzak> it = zzagVar.zza().zze().iterator();
                    while (it.hasNext()) {
                        dataLayer.zzd(zzfv.zzn(zzfv.zzl(it.next())));
                    }
                    Iterator<com.google.android.gms.internal.gtm.zzak> it2 = zzagVar.zza().zzf().iterator();
                    while (true) {
                        Map<String, Object> map = null;
                        if (!it2.hasNext()) {
                            break;
                        }
                        Object objZzl = zzfv.zzl(it2.next());
                        if (objZzl instanceof Map) {
                            map = (Map) objZzl;
                        } else {
                            String strValueOf = String.valueOf(objZzl);
                            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 36);
                            sb.append("value: ");
                            sb.append(strValueOf);
                            sb.append(" is not a map value, ignored.");
                            zzdh.zzc(sb.toString());
                        }
                        if (map != null) {
                            dataLayer.push(map);
                        }
                    }
                    for (com.google.android.gms.internal.gtm.zzu zzuVar : zzagVar.zza().zzd()) {
                        if (zzuVar.zzh()) {
                            Object objValueOf = dataLayer.get(zzuVar.zzf());
                            Long lValueOf = !(objValueOf instanceof Number) ? null : Long.valueOf(((Number) objValueOf).longValue());
                            long jZzd = zzuVar.zzd();
                            long jZzc = zzuVar.zzc();
                            if (!zzuVar.zzg() || lValueOf == null || lValueOf.longValue() < jZzd || lValueOf.longValue() > jZzc) {
                                if (jZzd <= jZzc) {
                                    objValueOf = Long.valueOf(Math.round((Math.random() * (jZzc - jZzd)) + jZzd));
                                } else {
                                    zzdh.zzc("GaExperimentRandom: random range invalid");
                                }
                            }
                            dataLayer.zzd(zzuVar.zzf());
                            Map<String, Object> mapZza = dataLayer.zza(zzuVar.zzf(), objValueOf);
                            if (zzuVar.zza() > 0) {
                                if (mapZza.containsKey("gtm")) {
                                    Object obj = mapZza.get("gtm");
                                    if (obj instanceof Map) {
                                        ((Map) obj).put("lifetime", Long.valueOf(zzuVar.zza()));
                                    } else {
                                        zzdh.zzc("GaExperimentRandom: gtm not a map");
                                    }
                                } else {
                                    mapZza.put("gtm", DataLayer.mapOf("lifetime", Long.valueOf(zzuVar.zza())));
                                }
                            }
                            dataLayer.push(mapZza);
                        } else {
                            zzdh.zzc("GaExperimentRandom: No key");
                        }
                    }
                } else {
                    zzdh.zzc("supplemental missing experimentSupplemental");
                }
            } else {
                String strValueOf2 = String.valueOf(zzagVar);
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf2).length() + 22);
                sb2.append("Ignored supplemental: ");
                sb2.append(strValueOf2);
                zzdh.zzb.zzd(sb2.toString());
            }
        }
    }

    final zzds<Boolean> zzf(zzro zzroVar, Set<String> set, zzdn zzdnVar) {
        zzds<com.google.android.gms.internal.gtm.zzak> zzdsVarZzn = zzn(this.zzd, zzroVar, set, zzdnVar);
        Boolean boolZzg = zzfv.zzg(zzfv.zzl(zzdsVarZzn.zza()));
        zzfv.zzc(boolZzg);
        return new zzds<>(boolZzg, zzdsVarZzn.zzb());
    }
}
