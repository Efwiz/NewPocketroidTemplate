package androidx.tracing;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/tracing-1.0.0.aar:classes.jar:androidx/tracing/TraceApi18Impl.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
final class TraceApi18Impl {
    private TraceApi18Impl() {
    }

    public static void beginSection(String str) {
        android.os.Trace.beginSection(str);
    }

    public static void endSection() {
        android.os.Trace.endSection();
    }
}
