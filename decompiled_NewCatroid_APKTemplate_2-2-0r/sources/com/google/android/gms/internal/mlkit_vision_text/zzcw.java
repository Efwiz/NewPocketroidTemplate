package com.google.android.gms.internal.mlkit_vision_text;

import java.io.PrintStream;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcw {
    static final zzcq zza;

    static {
        zzcq zzcuVar;
        Integer num = null;
        try {
            try {
                num = (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
            } catch (Exception e) {
                System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
                e.printStackTrace(System.err);
            }
            if (num == null || num.intValue() < 19) {
                zzcuVar = !Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ? new zzct() : new zzcu();
            } else {
                zzcuVar = new zzcv();
            }
        } catch (Throwable th) {
            PrintStream printStream = System.err;
            String name2 = zzcu.class.getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name2).length() + 133);
            sb.append("An error has occurred when initializing the try-with-resources desuguring strategy. The default strategy ");
            sb.append(name2);
            sb.append("will be used. The error is: ");
            printStream.println(sb.toString());
            th.printStackTrace(System.err);
            zzcuVar = new zzcu();
        }
        zza = zzcuVar;
        if (num == null) {
            return;
        }
        num.intValue();
    }

    public static void zza(Throwable th, Throwable th2) {
        zza.zza(th, th2);
    }
}
