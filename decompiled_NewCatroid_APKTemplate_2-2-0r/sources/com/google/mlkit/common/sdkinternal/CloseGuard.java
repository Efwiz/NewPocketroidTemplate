package com.google.mlkit.common.sdkinternal;

import android.util.Log;
import com.google.android.gms.internal.mlkit_common.zzgu;
import com.google.android.gms.internal.mlkit_common.zzgv;
import com.google.android.gms.internal.mlkit_common.zzgz;
import com.google.android.gms.internal.mlkit_common.zzha;
import com.google.android.gms.internal.mlkit_common.zzjr;
import com.google.android.gms.internal.mlkit_common.zzju;
import com.google.android.gms.internal.mlkit_common.zzkc;
import java.io.Closeable;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes10.dex */
public class CloseGuard implements Closeable {
    public static final int API_TRANSLATE = 1;
    private final AtomicBoolean zza = new AtomicBoolean();
    private final String zzb;
    private final Cleaner.Cleanable zzc;

    /* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
    public static class Factory {
        private final Cleaner zza;

        public Factory(Cleaner cleaner) {
            this.zza = cleaner;
        }

        public CloseGuard create(Object obj, int i, Runnable runnable) {
            return new CloseGuard(obj, i, this.zza, runnable, zzkc.zzb(NativeLibraryConstantsKt.KONAN_DISTRIBUTION_COMMON_LIBS_DIR));
        }
    }

    CloseGuard(Object obj, final int i, Cleaner cleaner, final Runnable runnable, final zzjr zzjrVar) {
        this.zzb = obj.toString();
        this.zzc = cleaner.register(obj, new Runnable() { // from class: com.google.mlkit.common.sdkinternal.zze
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zza(i, zzjrVar, runnable);
            }
        });
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.zza.set(true);
        this.zzc.clean();
    }

    final /* synthetic */ void zza(int i, zzjr zzjrVar, Runnable runnable) {
        if (!this.zza.get()) {
            Log.e("MlKitCloseGuard", String.format(Locale.ENGLISH, "%s has not been closed", this.zzb));
            zzha zzhaVar = new zzha();
            zzgv zzgvVar = new zzgv();
            zzgvVar.zzb(zzgu.zzb(i));
            zzhaVar.zzh(zzgvVar.zzc());
            zzjrVar.zzc(zzju.zzf(zzhaVar), zzgz.HANDLE_LEAKED);
        }
        runnable.run();
    }
}
