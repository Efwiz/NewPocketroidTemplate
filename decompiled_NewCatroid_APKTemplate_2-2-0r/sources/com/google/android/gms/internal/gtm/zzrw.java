package com.google.android.gms.internal.gtm;

import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzrw {
    private final List<zzro> zza;
    private final List<zzro> zzb;
    private final List<zzro> zzc;
    private final List<zzro> zzd;
    private final List<zzro> zze;
    private final List<zzro> zzf;
    private final List<String> zzg;
    private final List<String> zzh;
    private final List<String> zzi;
    private final List<String> zzj;

    /* synthetic */ zzrw(List list, List list2, List list3, List list4, List list5, List list6, List list7, List list8, List list9, List list10, zzrv zzrvVar) {
        this.zza = Collections.unmodifiableList(list);
        this.zzb = Collections.unmodifiableList(list2);
        this.zzc = Collections.unmodifiableList(list3);
        this.zzd = Collections.unmodifiableList(list4);
        this.zze = Collections.unmodifiableList(list5);
        this.zzf = Collections.unmodifiableList(list6);
        this.zzg = Collections.unmodifiableList(list7);
        this.zzh = Collections.unmodifiableList(list8);
        this.zzi = Collections.unmodifiableList(list9);
        this.zzj = Collections.unmodifiableList(list10);
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        String strValueOf2 = String.valueOf(this.zzb);
        String strValueOf3 = String.valueOf(this.zzc);
        String strValueOf4 = String.valueOf(this.zzd);
        String strValueOf5 = String.valueOf(this.zze);
        String strValueOf6 = String.valueOf(this.zzf);
        int length = String.valueOf(strValueOf).length();
        int length2 = String.valueOf(strValueOf2).length();
        int length3 = String.valueOf(strValueOf3).length();
        int length4 = String.valueOf(strValueOf4).length();
        StringBuilder sb = new StringBuilder(length + 102 + length2 + length3 + length4 + String.valueOf(strValueOf5).length() + String.valueOf(strValueOf6).length());
        sb.append("Positive predicates: ");
        sb.append(strValueOf);
        sb.append("  Negative predicates: ");
        sb.append(strValueOf2);
        sb.append("  Add tags: ");
        sb.append(strValueOf3);
        sb.append("  Remove tags: ");
        sb.append(strValueOf4);
        sb.append("  Add macros: ");
        sb.append(strValueOf5);
        sb.append("  Remove macros: ");
        sb.append(strValueOf6);
        return sb.toString();
    }

    public final List<zzro> zza() {
        return this.zze;
    }

    public final List<String> zzb() {
        return this.zzi;
    }

    public final List<zzro> zzc() {
        return this.zzc;
    }

    public final List<zzro> zzd() {
        return this.zzb;
    }

    public final List<zzro> zze() {
        return this.zza;
    }

    public final List<zzro> zzf() {
        return this.zzf;
    }

    public final List<String> zzg() {
        return this.zzj;
    }

    public final List<zzro> zzh() {
        return this.zzd;
    }
}
