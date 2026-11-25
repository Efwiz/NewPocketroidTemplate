package com.google.android.gms.internal.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzsa extends zzrg.zzi implements Runnable {
    private final Runnable zzb;

    public zzsa(Runnable runnable) {
        runnable.getClass();
        this.zzb = runnable;
    }

    @Override // com.google.android.gms.internal.cast.zzrg
    protected final String zze() {
        return "task=[" + this.zzb.toString() + "]";
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            this.zzb.run();
        } catch (Error | RuntimeException e) {
            zzl(e);
            throw e;
        }
    }
}
