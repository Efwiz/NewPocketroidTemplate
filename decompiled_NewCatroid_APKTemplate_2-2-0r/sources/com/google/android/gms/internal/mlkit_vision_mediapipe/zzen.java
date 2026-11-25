package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzen implements zzim {
    private final zzel zza;

    public static zzen zza(zzel zzelVar) {
        return zzelVar.zza != null ? zzelVar.zza : new zzen(zzelVar);
    }

    private zzen(zzel zzelVar) {
        zzel zzelVar2 = (zzel) zzfe.zza(zzelVar, "output");
        this.zza = zzelVar2;
        zzelVar2.zza = this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final int zza() {
        return zzil.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, int i2) throws IOException {
        this.zza.zze(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, long j) throws IOException {
        this.zza.zza(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzb(int i, long j) throws IOException {
        this.zza.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, float f) throws IOException {
        this.zza.zza(i, f);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, double d) throws IOException {
        this.zza.zza(i, d);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzb(int i, int i2) throws IOException {
        this.zza.zzb(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzc(int i, long j) throws IOException {
        this.zza.zza(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzc(int i, int i2) throws IOException {
        this.zza.zzb(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzd(int i, long j) throws IOException {
        this.zza.zzc(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzd(int i, int i2) throws IOException {
        this.zza.zze(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, boolean z) throws IOException {
        this.zza.zza(i, z);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, String str) throws IOException {
        this.zza.zza(i, str);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, zzdu zzduVar) throws IOException {
        this.zza.zza(i, zzduVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zze(int i, int i2) throws IOException {
        this.zza.zzc(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzf(int i, int i2) throws IOException {
        this.zza.zzd(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zze(int i, long j) throws IOException {
        this.zza.zzb(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, Object obj, zzgx zzgxVar) throws IOException {
        this.zza.zza(i, (zzgi) obj, zzgxVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzb(int i, Object obj, zzgx zzgxVar) throws IOException {
        zzel zzelVar = this.zza;
        zzelVar.zza(i, 3);
        zzgxVar.zza((zzgi) obj, zzelVar.zza);
        zzelVar.zza(i, 4);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i) throws IOException {
        this.zza.zza(i, 3);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzb(int i) throws IOException {
        this.zza.zza(i, 4);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, Object obj) throws IOException {
        if (obj instanceof zzdu) {
            this.zza.zzb(i, (zzdu) obj);
        } else {
            this.zza.zza(i, (zzgi) obj);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzf = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzf += zzel.zzf(list.get(i3).intValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzb(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzi = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzi += zzel.zzi(list.get(i3).intValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzc(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzd = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzd += zzel.zzd(list.get(i3).longValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzd(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZze = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZze += zzel.zze(list.get(i3).longValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zze(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzg = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzg += zzel.zzg(list.get(i3).longValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzf(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzb = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzb += zzel.zzb(list.get(i3).floatValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzb = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzb += zzel.zzb(list.get(i3).doubleValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzh(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzk = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzk += zzel.zzk(list.get(i3).intValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzi(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzb = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzb += zzel.zzb(list.get(i3).booleanValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (list instanceof zzfp) {
            zzfp zzfpVar = (zzfp) list;
            while (i2 < list.size()) {
                Object objZzb = zzfpVar.zzb(i2);
                if (objZzb instanceof String) {
                    this.zza.zza(i, (String) objZzb);
                } else {
                    this.zza.zza(i, (zzdu) objZzb);
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzb(int i, List<zzdu> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zza(i, list.get(i2));
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzg = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzg += zzel.zzg(list.get(i3).intValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzk(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzj = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzj += zzel.zzj(list.get(i3).intValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzl(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzh = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzh += zzel.zzh(list.get(i3).longValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzm(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzh = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzh += zzel.zzh(list.get(i3).intValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (z) {
            this.zza.zza(i, 2);
            int iZzf = 0;
            for (int i3 = 0; i3 < list.size(); i3++) {
                iZzf += zzel.zzf(list.get(i3).longValue());
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zza(int i, List<?> list, zzgx zzgxVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), zzgxVar);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final void zzb(int i, List<?> list, zzgx zzgxVar) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), zzgxVar);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzim
    public final <K, V> void zza(int i, zzfz<K, V> zzfzVar, Map<K, V> map) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.zza.zza(i, 2);
            this.zza.zzb(zzga.zza(zzfzVar, entry.getKey(), entry.getValue()));
            zzga.zza(this.zza, zzfzVar, entry.getKey(), entry.getValue());
        }
    }
}
