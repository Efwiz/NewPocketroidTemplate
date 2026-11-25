package com.google.android.gms.internal.mlkit_language_id;

import java.io.PrintStream;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzq {
    private static final zzt zza;
    private static final int zzb;

    /* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
    static final class zza extends zzt {
        zza() {
        }

        @Override // com.google.android.gms.internal.mlkit_language_id.zzt
        public final void zza(Throwable th, Throwable th2) {
        }
    }

    public static void zza(Throwable th, Throwable th2) {
        zza.zza(th, th2);
    }

    private static Integer zza() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0022 A[Catch: all -> 0x0028, TRY_LEAVE, TryCatch #0 {all -> 0x0028, blocks: (B:4:0x0006, B:6:0x000e, B:7:0x0014, B:9:0x001c, B:10:0x0022), top: B:24:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x0014 A[Catch: all -> 0x0028, TryCatch #0 {all -> 0x0028, blocks: (B:4:0x0006, B:6:0x000e, B:7:0x0014, B:9:0x001c, B:10:0x0022), top: B:24:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:9:0x001c A[Catch: all -> 0x0028, TryCatch #0 {all -> 0x0028, blocks: (B:4:0x0006, B:6:0x000e, B:7:0x0014, B:9:0x001c, B:10:0x0022), top: B:24:0x0006 }] */
    static {
        Integer numZza;
        zzt zzaVar;
        try {
            numZza = zza();
            if (numZza != null) {
                try {
                    if (numZza.intValue() >= 19) {
                        zzaVar = new zzw();
                    } else if (!Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic")) {
                        zzaVar = new zzu();
                    } else {
                        zzaVar = new zza();
                    }
                } catch (Throwable th) {
                    th = th;
                    PrintStream printStream = System.err;
                    String name2 = zza.class.getName();
                    printStream.println(new StringBuilder(String.valueOf(name2).length() + 133).append("An error has occurred when initializing the try-with-resources desuguring strategy. The default strategy ").append(name2).append("will be used. The error is: ").toString());
                    th.printStackTrace(System.err);
                    zzaVar = new zza();
                }
            } else if (!Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic")) {
                zzaVar = new zzu();
            } else {
                zzaVar = new zza();
            }
        } catch (Throwable th2) {
            th = th2;
            numZza = null;
        }
        zza = zzaVar;
        zzb = numZza == null ? 1 : numZza.intValue();
    }
}
