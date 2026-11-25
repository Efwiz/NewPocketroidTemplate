package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zzro;
import com.google.android.gms.internal.gtm.zzrw;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzet {
    private zzro zzf;
    private final Set<zzrw> zza = new HashSet();
    private final Map<zzrw, List<zzro>> zzb = new HashMap();
    private final Map<zzrw, List<String>> zzd = new HashMap();
    private final Map<zzrw, List<zzro>> zzc = new HashMap();
    private final Map<zzrw, List<String>> zze = new HashMap();

    public final zzro zza() {
        return this.zzf;
    }

    public final Map<zzrw, List<String>> zzb() {
        return this.zzd;
    }

    public final Map<zzrw, List<zzro>> zzc() {
        return this.zzb;
    }

    public final Map<zzrw, List<String>> zzd() {
        return this.zze;
    }

    public final Map<zzrw, List<zzro>> zze() {
        return this.zzc;
    }

    public final Set<zzrw> zzf() {
        return this.zza;
    }

    public final void zzg(zzrw zzrwVar, zzro zzroVar) {
        List<zzro> arrayList = this.zzb.get(zzrwVar);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.zzb.put(zzrwVar, arrayList);
        }
        arrayList.add(zzroVar);
    }

    public final void zzh(zzrw zzrwVar, String str) {
        List<String> arrayList = this.zzd.get(zzrwVar);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.zzd.put(zzrwVar, arrayList);
        }
        arrayList.add("Unknown");
    }

    public final void zzi(zzrw zzrwVar, zzro zzroVar) {
        List<zzro> arrayList = this.zzc.get(zzrwVar);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.zzc.put(zzrwVar, arrayList);
        }
        arrayList.add(zzroVar);
    }

    public final void zzj(zzrw zzrwVar, String str) {
        List<String> arrayList = this.zze.get(zzrwVar);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.zze.put(zzrwVar, arrayList);
        }
        arrayList.add("Unknown");
    }

    public final void zzk(zzrw zzrwVar) {
        this.zza.add(zzrwVar);
    }

    public final void zzl(zzro zzroVar) {
        this.zzf = zzroVar;
    }
}
