package com.google.mlkit.common.sdkinternal;

import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes10.dex */
public class TaskQueue {
    private boolean zzb;
    private final Object zza = new Object();
    private final Queue<zzr> zzc = new ArrayDeque();
    private final AtomicReference<Thread> zzd = new AtomicReference<>();

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzc() {
        synchronized (this.zza) {
            if (this.zzc.isEmpty()) {
                this.zzb = false;
            } else {
                zzr zzrVarRemove = this.zzc.remove();
                zzd(zzrVarRemove.zza, zzrVarRemove.zzb);
            }
        }
    }

    private final void zzd(Executor executor, final Runnable runnable) {
        try {
            executor.execute(new Runnable() { // from class: com.google.mlkit.common.sdkinternal.zzp
                @Override // java.lang.Runnable
                public final void run() {
                    TaskQueue taskQueue = this.zza;
                    Runnable runnable2 = runnable;
                    zzt zztVar = new zzt(taskQueue, null);
                    try {
                        runnable2.run();
                        zztVar.close();
                    } catch (Throwable th) {
                        try {
                            zztVar.close();
                        } catch (Throwable unused) {
                        }
                        throw th;
                    }
                }
            });
        } catch (RejectedExecutionException unused) {
            zzc();
        }
    }

    public void checkIsRunningOnCurrentThread() {
        Preconditions.checkState(Thread.currentThread().equals(this.zzd.get()));
    }

    public void submit(Executor executor, Runnable runnable) {
        synchronized (this.zza) {
            if (this.zzb) {
                this.zzc.add(new zzr(executor, runnable, null));
            } else {
                this.zzb = true;
                zzd(executor, runnable);
            }
        }
    }
}
