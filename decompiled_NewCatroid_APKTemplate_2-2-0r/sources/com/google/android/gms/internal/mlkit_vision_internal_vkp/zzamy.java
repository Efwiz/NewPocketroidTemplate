package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzamy implements zzarl {
    private final zzamx zza;

    private zzamy(zzamx zzamxVar) {
        zzaoc.zzf(zzamxVar, "output");
        this.zza = zzamxVar;
        zzamxVar.zze = this;
    }

    public static zzamy zza(zzamx zzamxVar) {
        zzamy zzamyVar = zzamxVar.zze;
        return zzamyVar != null ? zzamyVar : new zzamy(zzamxVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzA(int i, long j) throws IOException {
        this.zza.zzl(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzC(int i, int i2) throws IOException {
        this.zza.zzw(i, (i2 >> 31) ^ (i2 + i2));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzE(int i, long j) throws IOException {
        this.zza.zzy(i, (j >> 63) ^ (j + j));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzG(int i) throws IOException {
        this.zza.zzv(i, 3);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzH(int i, String str) throws IOException {
        this.zza.zzt(i, str);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzI(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzaom)) {
            while (i2 < list.size()) {
                this.zza.zzt(i, list.get(i2));
                i2++;
            }
            return;
        }
        zzaom zzaomVar = (zzaom) list;
        while (i2 < list.size()) {
            Object objZzf = zzaomVar.zzf(i2);
            if (objZzf instanceof String) {
                this.zza.zzt(i, (String) objZzf);
            } else {
                this.zza.zzh(i, (zzamk) objZzf);
            }
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzJ(int i, int i2) throws IOException {
        this.zza.zzw(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzL(int i, long j) throws IOException {
        this.zza.zzy(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzb(int i, boolean z) throws IOException {
        this.zza.zzX(i, z);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzd(int i, zzamk zzamkVar) throws IOException {
        this.zza.zzh(i, zzamkVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zze(int i, List<zzamk> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zzh(i, list.get(i2));
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzf(int i, double d) throws IOException {
        this.zza.zzl(i, Double.doubleToRawLongBits(d));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzh(int i) throws IOException {
        this.zza.zzv(i, 4);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzi(int i, int i2) throws IOException {
        this.zza.zzn(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzk(int i, int i2) throws IOException {
        this.zza.zzj(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzm(int i, long j) throws IOException {
        this.zza.zzl(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzo(int i, float f) throws IOException {
        this.zza.zzj(i, Float.floatToRawIntBits(f));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzq(int i, Object obj, zzapz zzapzVar) throws IOException {
        zzamx zzamxVar = this.zza;
        zzamxVar.zzv(i, 3);
        zzapzVar.zzj((zzapf) obj, zzamxVar.zze);
        zzamxVar.zzv(i, 4);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzr(int i, int i2) throws IOException {
        this.zza.zzn(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzt(int i, long j) throws IOException {
        this.zza.zzy(i, j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final <K, V> void zzv(int i, zzaox<K, V> zzaoxVar, Map<K, V> map) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.zza.zzv(i, 2);
            this.zza.zzx(zzaoy.zzb(zzaoxVar, entry.getKey(), entry.getValue()));
            zzaoy.zze(this.zza, zzaoxVar, entry.getKey(), entry.getValue());
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzw(int i, Object obj, zzapz zzapzVar) throws IOException {
        this.zza.zzp(i, (zzapf) obj, zzapzVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzx(int i, Object obj) throws IOException {
        if (obj instanceof zzamk) {
            this.zza.zzs(i, (zzamk) obj);
        } else {
            this.zza.zzr(i, (zzapf) obj);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzy(int i, int i2) throws IOException {
        this.zza.zzj(i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzB(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzl(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).longValue();
            i3 += 8;
        }
        this.zza.zzx(i3);
        while (i2 < list.size()) {
            this.zza.zzm(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzD(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                zzamx zzamxVar = this.zza;
                int iIntValue = list.get(i2).intValue();
                zzamxVar.zzw(i, (iIntValue >> 31) ^ (iIntValue + iIntValue));
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int iZzM = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            int iIntValue2 = list.get(i3).intValue();
            iZzM += zzamx.zzM((iIntValue2 >> 31) ^ (iIntValue2 + iIntValue2));
        }
        this.zza.zzx(iZzM);
        while (i2 < list.size()) {
            zzamx zzamxVar2 = this.zza;
            int iIntValue3 = list.get(i2).intValue();
            zzamxVar2.zzx((iIntValue3 >> 31) ^ (iIntValue3 + iIntValue3));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzF(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                zzamx zzamxVar = this.zza;
                long jLongValue = list.get(i2).longValue();
                zzamxVar.zzy(i, (jLongValue >> 63) ^ (jLongValue + jLongValue));
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int iZzN = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            long jLongValue2 = list.get(i3).longValue();
            iZzN += zzamx.zzN((jLongValue2 >> 63) ^ (jLongValue2 + jLongValue2));
        }
        this.zza.zzx(iZzN);
        while (i2 < list.size()) {
            zzamx zzamxVar2 = this.zza;
            long jLongValue3 = list.get(i2).longValue();
            zzamxVar2.zzz((jLongValue3 >> 63) ^ (jLongValue3 + jLongValue3));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzK(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzw(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int iZzM = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzM += zzamx.zzM(list.get(i3).intValue());
        }
        this.zza.zzx(iZzM);
        while (i2 < list.size()) {
            this.zza.zzx(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzM(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzy(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int iZzN = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzN += zzamx.zzN(list.get(i3).longValue());
        }
        this.zza.zzx(iZzN);
        while (i2 < list.size()) {
            this.zza.zzz(list.get(i2).longValue());
            i2++;
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzc(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzX(i, list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).booleanValue();
            i3++;
        }
        this.zza.zzx(i3);
        while (i2 < list.size()) {
            this.zza.zzW(list.get(i2).booleanValue() ? (byte) 1 : (byte) 0);
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzn(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int iZzF = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzF += zzamx.zzF(list.get(i3).intValue());
        }
        this.zza.zzx(iZzF);
        while (i2 < list.size()) {
            this.zza.zzo(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzl(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzj(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).intValue();
            i3 += 4;
        }
        this.zza.zzx(i3);
        while (i2 < list.size()) {
            this.zza.zzk(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzl(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).longValue();
            i3 += 8;
        }
        this.zza.zzx(i3);
        while (i2 < list.size()) {
            this.zza.zzm(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzs(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzn(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int iZzF = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzF += zzamx.zzF(list.get(i3).intValue());
        }
        this.zza.zzx(iZzF);
        while (i2 < list.size()) {
            this.zza.zzo(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzu(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzy(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int iZzN = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzN += zzamx.zzN(list.get(i3).longValue());
        }
        this.zza.zzx(iZzN);
        while (i2 < list.size()) {
            this.zza.zzz(list.get(i2).longValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzz(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzj(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).intValue();
            i3 += 4;
        }
        this.zza.zzx(i3);
        while (i2 < list.size()) {
            this.zza.zzk(list.get(i2).intValue());
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzl(i, Double.doubleToRawLongBits(list.get(i2).doubleValue()));
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).doubleValue();
            i3 += 8;
        }
        this.zza.zzx(i3);
        while (i2 < list.size()) {
            this.zza.zzm(Double.doubleToRawLongBits(list.get(i2).doubleValue()));
            i2++;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarl
    public final void zzp(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzj(i, Float.floatToRawIntBits(list.get(i2).floatValue()));
                i2++;
            }
            return;
        }
        this.zza.zzv(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).floatValue();
            i3 += 4;
        }
        this.zza.zzx(i3);
        while (i2 < list.size()) {
            this.zza.zzk(Float.floatToRawIntBits(list.get(i2).floatValue()));
            i2++;
        }
    }
}
