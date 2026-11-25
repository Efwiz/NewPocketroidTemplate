package com.google.android.gms.internal.gtm;

import android.util.Log;
import com.google.android.gms.analytics.Logger;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcu implements Logger {
    private int zza = 2;
    private boolean zzb;

    zzcu() {
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void error(Exception exc) {
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void error(String str) {
    }

    @Override // com.google.android.gms.analytics.Logger
    public final int getLogLevel() {
        return this.zza;
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void info(String str) {
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void setLogLevel(int i) {
        this.zza = i;
        if (this.zzb) {
            return;
        }
        String strZzb = zzeu.zzc.zzb();
        String strZzb2 = zzeu.zzc.zzb();
        StringBuilder sb = new StringBuilder(strZzb2.length() + 91);
        sb.append("Logger is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.");
        sb.append(strZzb2);
        sb.append(" DEBUG");
        Log.i(strZzb, sb.toString());
        this.zzb = true;
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void verbose(String str) {
    }

    @Override // com.google.android.gms.analytics.Logger
    public final void warn(String str) {
    }
}
