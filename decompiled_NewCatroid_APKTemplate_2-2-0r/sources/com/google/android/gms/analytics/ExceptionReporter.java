package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.internal.gtm.zzfa;
import java.util.ArrayList;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public class ExceptionReporter implements Thread.UncaughtExceptionHandler {
    private final Thread.UncaughtExceptionHandler zza;
    private final Tracker zzb;
    private final Context zzc;
    private ExceptionParser zzd;
    private GoogleAnalytics zze;

    public ExceptionParser getExceptionParser() {
        return this.zzd;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.zzd = exceptionParser;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        String description;
        if (this.zzd != null) {
            description = this.zzd.getDescription(thread != null ? thread.getName() : null, th);
        } else {
            description = "UncaughtException";
        }
        String strValueOf = String.valueOf(description);
        zzfa.zzd(strValueOf.length() != 0 ? "Reporting uncaught exception: ".concat(strValueOf) : new String("Reporting uncaught exception: "));
        Tracker tracker = this.zzb;
        HitBuilders.ExceptionBuilder exceptionBuilder = new HitBuilders.ExceptionBuilder();
        exceptionBuilder.setDescription(description);
        exceptionBuilder.setFatal(true);
        tracker.send(exceptionBuilder.build());
        if (this.zze == null) {
            this.zze = GoogleAnalytics.getInstance(this.zzc);
        }
        GoogleAnalytics googleAnalytics = this.zze;
        googleAnalytics.dispatchLocalHits();
        googleAnalytics.zzb().zzf().zzn();
        if (this.zza != null) {
            zzfa.zzd("Passing exception to the original handler");
            this.zza.uncaughtException(thread, th);
        }
    }

    final Thread.UncaughtExceptionHandler zza() {
        return this.zza;
    }

    public ExceptionReporter(Tracker tracker, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        }
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.zza = uncaughtExceptionHandler;
        this.zzb = tracker;
        this.zzd = new StandardExceptionParser(context, new ArrayList());
        this.zzc = context.getApplicationContext();
        String strValueOf = String.valueOf(uncaughtExceptionHandler == null ? "null" : uncaughtExceptionHandler.getClass().getName());
        zzfa.zzd(strValueOf.length() != 0 ? "ExceptionReporter created, original handler is ".concat(strValueOf) : new String("ExceptionReporter created, original handler is "));
    }
}
