package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zztp {
    private final zzto zza;

    private zztp(zzto zztoVar) {
        zzvi.zzf(zztoVar, "output");
        this.zza = zztoVar;
        zztoVar.zza = this;
    }

    public static zztp zza(zzto zztoVar) {
        zztp zztpVar = zztoVar.zza;
        return zztpVar != null ? zztpVar : new zztp(zztoVar);
    }

    public final void zzB(int i, int i2) throws IOException {
        this.zza.zzp(i, (i2 >> 31) ^ (i2 + i2));
    }

    public final void zzD(int i, long j) throws IOException {
        this.zza.zzr(i, (j >> 63) ^ (j + j));
    }

    public final void zzF(int i) throws IOException {
        this.zza.zzo(i, 3);
    }

    public final void zzG(int i, String str) throws IOException {
        this.zza.zzm(i, str);
    }

    public final void zzH(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (!(list instanceof zzvs)) {
            while (i2 < list.size()) {
                this.zza.zzm(i, list.get(i2));
                i2++;
            }
            return;
        }
        zzvs zzvsVar = (zzvs) list;
        while (i2 < list.size()) {
            Object objZzf = zzvsVar.zzf(i2);
            if (objZzf instanceof String) {
                this.zza.zzm(i, (String) objZzf);
            } else {
                this.zza.zze(i, (zztd) objZzf);
            }
            i2++;
        }
    }

    public final void zzI(int i, int i2) throws IOException {
        this.zza.zzp(i, i2);
    }

    public final void zzK(int i, long j) throws IOException {
        this.zza.zzr(i, j);
    }

    public final void zzb(int i, boolean z) throws IOException {
        this.zza.zzd(i, z);
    }

    public final void zzd(int i, zztd zztdVar) throws IOException {
        this.zza.zze(i, zztdVar);
    }

    public final void zze(int i, List<zztd> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zza.zze(i, list.get(i2));
        }
    }

    public final void zzf(int i, double d) throws IOException {
        this.zza.zzh(i, Double.doubleToRawLongBits(d));
    }

    public final void zzh(int i) throws IOException {
        this.zza.zzo(i, 4);
    }

    public final void zzi(int i, int i2) throws IOException {
        this.zza.zzj(i, i2);
    }

    public final void zzk(int i, int i2) throws IOException {
        this.zza.zzf(i, i2);
    }

    public final void zzm(int i, long j) throws IOException {
        this.zza.zzh(i, j);
    }

    public final void zzo(int i, float f) throws IOException {
        this.zza.zzf(i, Float.floatToRawIntBits(f));
    }

    public final void zzq(int i, Object obj, zzwx zzwxVar) throws IOException {
        zzto zztoVar = this.zza;
        zztoVar.zzo(i, 3);
        zzwxVar.zzn((zzwk) obj, zztoVar.zza);
        zztoVar.zzo(i, 4);
    }

    public final void zzr(int i, int i2) throws IOException {
        this.zza.zzj(i, i2);
    }

    public final void zzt(int i, long j) throws IOException {
        this.zza.zzr(i, j);
    }

    public final void zzv(int i, Object obj, zzwx zzwxVar) throws IOException {
        zzwk zzwkVar = (zzwk) obj;
        zztl zztlVar = (zztl) this.zza;
        zztlVar.zzq((i << 3) | 2);
        zzsh zzshVar = (zzsh) zzwkVar;
        int iZzQ = zzshVar.zzQ();
        if (iZzQ == -1) {
            iZzQ = zzwxVar.zza(zzshVar);
            zzshVar.zzT(iZzQ);
        }
        zztlVar.zzq(iZzQ);
        zzwxVar.zzn(zzwkVar, zztlVar.zza);
    }

    public final void zzw(int i, Object obj) throws IOException {
        if (obj instanceof zztd) {
            zztl zztlVar = (zztl) this.zza;
            zztlVar.zzq(11);
            zztlVar.zzp(2, i);
            zztlVar.zze(3, (zztd) obj);
            zztlVar.zzq(12);
            return;
        }
        zzto zztoVar = this.zza;
        zzwk zzwkVar = (zzwk) obj;
        zztl zztlVar2 = (zztl) zztoVar;
        zztlVar2.zzq(11);
        zztlVar2.zzp(2, i);
        zztlVar2.zzq(26);
        zztlVar2.zzq(zzwkVar.zzX());
        zzwkVar.zzaq(zztoVar);
        zztlVar2.zzq(12);
    }

    public final void zzx(int i, int i2) throws IOException {
        this.zza.zzf(i, i2);
    }

    public final void zzz(int i, long j) throws IOException {
        this.zza.zzh(i, j);
    }

    public final void zzA(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzh(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).longValue();
            i3 += 8;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzi(list.get(i2).longValue());
            i2++;
        }
    }

    public final void zzC(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                zzto zztoVar = this.zza;
                int iIntValue = list.get(i2).intValue();
                zztoVar.zzp(i, (iIntValue >> 31) ^ (iIntValue + iIntValue));
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzD = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            int iIntValue2 = list.get(i3).intValue();
            iZzD += zzto.zzD((iIntValue2 >> 31) ^ (iIntValue2 + iIntValue2));
        }
        this.zza.zzq(iZzD);
        while (i2 < list.size()) {
            zzto zztoVar2 = this.zza;
            int iIntValue3 = list.get(i2).intValue();
            zztoVar2.zzq((iIntValue3 >> 31) ^ (iIntValue3 + iIntValue3));
            i2++;
        }
    }

    public final void zzE(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                zzto zztoVar = this.zza;
                long jLongValue = list.get(i2).longValue();
                zztoVar.zzr(i, (jLongValue >> 63) ^ (jLongValue + jLongValue));
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzE = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            long jLongValue2 = list.get(i3).longValue();
            iZzE += zzto.zzE((jLongValue2 >> 63) ^ (jLongValue2 + jLongValue2));
        }
        this.zza.zzq(iZzE);
        while (i2 < list.size()) {
            zzto zztoVar2 = this.zza;
            long jLongValue3 = list.get(i2).longValue();
            zztoVar2.zzs((jLongValue3 >> 63) ^ (jLongValue3 + jLongValue3));
            i2++;
        }
    }

    public final void zzJ(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzp(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzD = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzD += zzto.zzD(list.get(i3).intValue());
        }
        this.zza.zzq(iZzD);
        while (i2 < list.size()) {
            this.zza.zzq(list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzL(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzr(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzE = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzE += zzto.zzE(list.get(i3).longValue());
        }
        this.zza.zzq(iZzE);
        while (i2 < list.size()) {
            this.zza.zzs(list.get(i2).longValue());
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
    public final void zzc(int i, List<Boolean> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzd(i, list.get(i2).booleanValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).booleanValue();
            i3++;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzb(list.get(i2).booleanValue() ? (byte) 1 : (byte) 0);
            i2++;
        }
    }

    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzj(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzx = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzx += zzto.zzx(list.get(i3).intValue());
        }
        this.zza.zzq(iZzx);
        while (i2 < list.size()) {
            this.zza.zzk(list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzl(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzf(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).intValue();
            i3 += 4;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzg(list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzh(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).longValue();
            i3 += 8;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzi(list.get(i2).longValue());
            i2++;
        }
    }

    public final void zzs(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzj(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzx = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzx += zzto.zzx(list.get(i3).intValue());
        }
        this.zza.zzq(iZzx);
        while (i2 < list.size()) {
            this.zza.zzk(list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzu(int i, List<Long> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzr(i, list.get(i2).longValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int iZzE = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzE += zzto.zzE(list.get(i3).longValue());
        }
        this.zza.zzq(iZzE);
        while (i2 < list.size()) {
            this.zza.zzs(list.get(i2).longValue());
            i2++;
        }
    }

    public final void zzy(int i, List<Integer> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzf(i, list.get(i2).intValue());
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).intValue();
            i3 += 4;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzg(list.get(i2).intValue());
            i2++;
        }
    }

    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzh(i, Double.doubleToRawLongBits(list.get(i2).doubleValue()));
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).doubleValue();
            i3 += 8;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzi(Double.doubleToRawLongBits(list.get(i2).doubleValue()));
            i2++;
        }
    }

    public final void zzp(int i, List<Float> list, boolean z) throws IOException {
        int i2 = 0;
        if (!z) {
            while (i2 < list.size()) {
                this.zza.zzf(i, Float.floatToRawIntBits(list.get(i2).floatValue()));
                i2++;
            }
            return;
        }
        this.zza.zzo(i, 2);
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            list.get(i4).floatValue();
            i3 += 4;
        }
        this.zza.zzq(i3);
        while (i2 < list.size()) {
            this.zza.zzg(Float.floatToRawIntBits(list.get(i2).floatValue()));
            i2++;
        }
    }
}
