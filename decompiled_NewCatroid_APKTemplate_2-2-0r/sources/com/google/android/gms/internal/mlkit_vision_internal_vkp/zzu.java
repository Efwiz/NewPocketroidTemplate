package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzu {
    final long zza;
    final long zzb;
    final boolean zzc;

    private zzu(long j, long j2, boolean z) {
        this.zza = j;
        this.zzb = j2;
        this.zzc = z;
    }

    static zzu zza(final FileDescriptor fileDescriptor) throws IOException {
        StructStat structStat = (StructStat) zzc(new Callable() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzo
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return Os.fstat(fileDescriptor);
            }
        });
        return new zzu(structStat.st_dev, structStat.st_ino, OsConstants.S_ISLNK(structStat.st_mode));
    }

    static zzu zzb(final String str) throws IOException {
        StructStat structStat = (StructStat) zzc(new Callable() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzp
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return Os.lstat(str);
            }
        });
        return new zzu(structStat.st_dev, structStat.st_ino, OsConstants.S_ISLNK(structStat.st_mode));
    }

    private static <T> T zzc(Callable<T> callable) throws IOException {
        try {
            return callable.call();
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }
}
