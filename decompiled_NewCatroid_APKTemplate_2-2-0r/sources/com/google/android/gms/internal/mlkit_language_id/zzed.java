package com.google.android.gms.internal.mlkit_language_id;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzed implements zzib {
    private final zzea zza;

    public static zzed zza(zzea zzeaVar) {
        return zzeaVar.zza != null ? zzeaVar.zza : new zzed(zzeaVar);
    }

    private zzed(zzea zzeaVar) {
        zzea zzeaVar2 = (zzea) zzeq.zza(zzeaVar, "output");
        this.zza = zzeaVar2;
        zzeaVar2.zza = this;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final int zza() {
        return zzia.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, int i2) throws IOException {
        this.zza.zze(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, long j) throws IOException {
        this.zza.zza(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzb(int i, long j) throws IOException {
        this.zza.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, float f) throws IOException {
        this.zza.zza(i, f);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, double d) throws IOException {
        this.zza.zza(i, d);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzb(int i, int i2) throws IOException {
        this.zza.zzb(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzc(int i, long j) throws IOException {
        this.zza.zza(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzc(int i, int i2) throws IOException {
        this.zza.zzb(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzd(int i, long j) throws IOException {
        this.zza.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzd(int i, int i2) throws IOException {
        this.zza.zze(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, boolean z) throws IOException {
        this.zza.zza(i, z);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, String str) throws IOException {
        this.zza.zza(i, str);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, zzdn zzdnVar) throws IOException {
        this.zza.zza(i, zzdnVar);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zze(int i, int i2) throws IOException {
        this.zza.zzc(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzf(int i, int i2) throws IOException {
        this.zza.zzd(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zze(int i, long j) throws IOException {
        this.zza.zzb(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, Object obj, zzgp zzgpVar) throws IOException {
        this.zza.zza(i, (zzfz) obj, zzgpVar);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzb(int i, Object obj, zzgp zzgpVar) throws IOException {
        zzea zzeaVar = this.zza;
        zzeaVar.zza(i, 3);
        zzgpVar.zza((zzfz) obj, zzeaVar.zza);
        zzeaVar.zza(i, 4);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i) throws IOException {
        this.zza.zza(i, 3);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzb(int i) throws IOException {
        this.zza.zza(i, 4);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, Object obj) throws IOException {
        if (obj instanceof zzdn) {
            this.zza.zzb(i, (zzdn) obj);
        } else {
            this.zza.zza(i, (zzfz) obj);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzf = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzf += zzea.zzf(list.get(i3).intValue());
            }
            this.zza.zzb(iZzf);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzb(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzb(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzi = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzi += zzea.zzi(list.get(i3).intValue());
            }
            this.zza.zzb(iZzi);
            while (i2 < list.size()) {
                this.zza.zzd(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zze(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzc(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzd = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzd += zzea.zzd(list.get(i3).longValue());
            }
            this.zza.zzb(iZzd);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzd(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZze = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZze += zzea.zze(list.get(i3).longValue());
            }
            this.zza.zzb(iZze);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zze(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzg = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzg += zzea.zzg(list.get(i3).longValue());
            }
            this.zza.zzb(iZzg);
            while (i2 < list.size()) {
                this.zza.zzc(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzc(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzf(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzb = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzb += zzea.zzb(list.get(i3).floatValue());
            }
            this.zza.zzb(iZzb);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).floatValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2).floatValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzb = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzb += zzea.zzb(list.get(i3).doubleValue());
            }
            this.zza.zzb(iZzb);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).doubleValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2).doubleValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzh(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzk = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzk += zzea.zzk(list.get(i3).intValue());
            }
            this.zza.zzb(iZzk);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzb(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzi(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzb = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzb += zzea.zzb(list.get(i3).booleanValue());
            }
            this.zza.zzb(iZzb);
            while (i2 < list.size()) {
                this.zza.zza(list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2).booleanValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (list instanceof zzfg) {
            zzfg zzfgVar = (zzfg) list;
            while (i2 < list.size()) {
                Object objZza = zzfgVar.zza(i2);
                if (objZza instanceof String) {
                    this.zza.zza(i, (String) objZza);
                } else {
                    this.zza.zza(i, (zzdn) objZza);
                }
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zza(i, list.get(i2));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzb(int i, List<zzdn> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zza(i, list.get(i2));
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzg = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzg += zzea.zzg(list.get(i3).intValue());
            }
            this.zza.zzb(iZzg);
            while (i2 < list.size()) {
                this.zza.zzb(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzc(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzk(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzj = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzj += zzea.zzj(list.get(i3).intValue());
            }
            this.zza.zzb(iZzj);
            while (i2 < list.size()) {
                this.zza.zzd(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zze(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzl(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzh = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzh += zzea.zzh(list.get(i3).longValue());
            }
            this.zza.zzb(iZzh);
            while (i2 < list.size()) {
                this.zza.zzc(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzc(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzm(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzh = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzh += zzea.zzh(list.get(i3).intValue());
            }
            this.zza.zzb(iZzh);
            while (i2 < list.size()) {
                this.zza.zzc(list.get(i2).intValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzd(i, list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzf = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzf += zzea.zzf(list.get(i3).longValue());
            }
            this.zza.zzb(iZzf);
            while (i2 < list.size()) {
                this.zza.zzb(list.get(i2).longValue());
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zza.zzb(i, list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zza(int i, List<?> list, zzgp zzgpVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzgpVar);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final void zzb(int i, List<?> list, zzgp zzgpVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzgpVar);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzib
    public final <K, V> void zza(int i, zzfq<K, V> zzfqVar, Map<K, V> map) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.zza.zza(i, 2);
            this.zza.zzb(zzej.zza(zzfqVar.zza, 1, entry.getKey()) + zzej.zza(zzfqVar.zzb, 2, entry.getValue()));
            zzea zzeaVar = this.zza;
            K key = entry.getKey();
            V value = entry.getValue();
            zzej.zza(zzeaVar, zzfqVar.zza, 1, key);
            zzej.zza(zzeaVar, zzfqVar.zzb, 2, value);
        }
    }
}
