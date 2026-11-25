package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzbe;
import com.google.android.gms.internal.gtm.zzbi;
import com.google.android.gms.internal.gtm.zzbv;
import java.util.ListIterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public class zza extends zzk<zza> {
    private final zzbv zzb;
    private boolean zzc;

    public zza(zzbv zzbvVar) {
        super(zzbvVar.zzd(), zzbvVar.zzr());
        this.zzb = zzbvVar;
    }

    public final zzh zza() {
        zzh zzhVar = new zzh(this.zza);
        zzhVar.zzg(this.zzb.zzh().zza());
        zzhVar.zzg(this.zzb.zzk().zza());
        zzn(zzhVar);
        return zzhVar;
    }

    final zzbv zzb() {
        return this.zzb;
    }

    public final void zzc(String str) {
        Preconditions.checkNotEmpty(str);
        Uri uriZza = zzb.zza(str);
        ListIterator<zzt> listIterator = this.zza.zzf().listIterator();
        while (listIterator.hasNext()) {
            if (uriZza.equals(listIterator.next().zzb())) {
                listIterator.remove();
            }
        }
        this.zza.zzf().add(new zzb(this.zzb, str));
    }

    public final void zzd(boolean z) {
        this.zzc = z;
    }

    @Override // com.google.android.gms.analytics.zzk
    protected final void zze(zzh zzhVar) {
        zzbe zzbeVar = (zzbe) zzhVar.zzb(zzbe.class);
        if (TextUtils.isEmpty(zzbeVar.zze())) {
            zzbeVar.zzj(this.zzb.zzi().zzb());
        }
        if (this.zzc && TextUtils.isEmpty(zzbeVar.zzd())) {
            zzbi zzbiVarZze = this.zzb.zze();
            zzbeVar.zzi(zzbiVarZze.zza());
            zzbeVar.zzh(zzbiVarZze.zzb());
        }
    }
}
