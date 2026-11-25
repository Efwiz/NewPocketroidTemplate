package com.google.android.gms.internal.mlkit_vision_common;

import android.os.SystemClock;
import java.io.Closeable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.compress.harmony.pack200.PackingOptions;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzhm implements Closeable {
    private static final Map<String, zzhm> zza = new HashMap();
    private final String zzb;
    private int zzc;
    private double zzd;
    private long zze;
    private long zzf;
    private long zzg;
    private long zzh;

    private zzhm(String str) {
        this.zzg = 2147483647L;
        this.zzh = -2147483648L;
        this.zzb = str;
    }

    private final void zza() {
        this.zzc = 0;
        this.zzd = 0.0d;
        this.zze = 0L;
        this.zzg = 2147483647L;
        this.zzh = -2147483648L;
    }

    public static long zze() {
        return SystemClock.elapsedRealtimeNanos() / 1000;
    }

    public static zzhm zzf(String str) {
        zzik.zza();
        if (!zzik.zzb()) {
            return zzhk.zza;
        }
        Map<String, zzhm> map = zza;
        if (map.get("detectorTaskWithResource#run") == null) {
            map.put("detectorTaskWithResource#run", new zzhm("detectorTaskWithResource#run"));
        }
        return map.get("detectorTaskWithResource#run");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        long j = this.zze;
        if (j == 0) {
            throw new IllegalStateException("Did you forget to call start()?");
        }
        zzd(j);
    }

    public zzhm zzb() {
        this.zze = zze();
        return this;
    }

    public void zzc(long j) {
        long jZze = zze();
        long j2 = this.zzf;
        if (j2 != 0 && jZze - j2 >= PackingOptions.SEGMENT_LIMIT) {
            zza();
        }
        this.zzf = jZze;
        this.zzc++;
        this.zzd += j;
        this.zzg = Math.min(this.zzg, j);
        this.zzh = Math.max(this.zzh, j);
        if (this.zzc % 50 == 0) {
            String.format(Locale.US, "[%s] cur=%dus, counts=%d, min=%dus, max=%dus, avg=%dus", this.zzb, Long.valueOf(j), Integer.valueOf(this.zzc), Long.valueOf(this.zzg), Long.valueOf(this.zzh), Integer.valueOf((int) (this.zzd / ((double) this.zzc))));
            zzik.zza();
        }
        if (this.zzc % 500 == 0) {
            zza();
        }
    }

    public void zzd(long j) {
        zzc(zze() - j);
    }
}
