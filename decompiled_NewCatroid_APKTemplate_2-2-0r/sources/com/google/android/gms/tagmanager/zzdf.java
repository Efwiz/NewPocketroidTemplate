package com.google.android.gms.tagmanager;

import androidx.lifecycle.CoroutineLiveDataKt;
import com.google.android.gms.common.util.Clock;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdf implements zzec {
    private long zzb;
    private final Clock zze;
    private final Object zzc = new Object();
    private double zza = Math.min(1, 5);
    private final String zzd = "refreshing";

    public zzdf(int i, int i2, long j, long j2, String str, Clock clock) {
        this.zze = clock;
    }

    @Override // com.google.android.gms.tagmanager.zzec
    public final boolean zza() {
        synchronized (this.zzc) {
            long jCurrentTimeMillis = this.zze.currentTimeMillis();
            long j = jCurrentTimeMillis - this.zzb;
            if (j < CoroutineLiveDataKt.DEFAULT_TIMEOUT) {
                String str = this.zzd;
                StringBuilder sb = new StringBuilder(str.length() + 34);
                sb.append("Excessive ");
                sb.append(str);
                sb.append(" detected; call ignored.");
                zzdh.zzc(sb.toString());
                return false;
            }
            double dMin = this.zza;
            if (dMin < 5.0d) {
                double d = j / 900000.0d;
                if (d > 0.0d) {
                    dMin = Math.min(5.0d, dMin + d);
                    this.zza = dMin;
                }
            }
            this.zzb = jCurrentTimeMillis;
            if (dMin >= 1.0d) {
                this.zza = dMin - 1.0d;
                return true;
            }
            String str2 = this.zzd;
            StringBuilder sb2 = new StringBuilder(str2.length() + 34);
            sb2.append("Excessive ");
            sb2.append(str2);
            sb2.append(" detected; call ignored.");
            zzdh.zzc(sb2.toString());
            return false;
        }
    }
}
