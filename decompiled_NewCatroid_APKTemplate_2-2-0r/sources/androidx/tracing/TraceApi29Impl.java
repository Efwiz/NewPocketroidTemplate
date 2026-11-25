package androidx.tracing;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/tracing-1.0.0.aar:classes.jar:androidx/tracing/TraceApi29Impl.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
final class TraceApi29Impl {
    private TraceApi29Impl() {
    }

    public static void beginAsyncSection(String str, int i) {
        android.os.Trace.beginAsyncSection(str, i);
    }

    public static void endAsyncSection(String str, int i) {
        android.os.Trace.endAsyncSection(str, i);
    }

    public static void setCounter(String str, int i) {
        android.os.Trace.setCounter(str, i);
    }
}
