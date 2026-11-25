package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzx<T> {
    private final String zza;
    private final Class<T> zzb;
    private final boolean zzc = false;

    public static <T> zzx<T> zza(String str, Class<T> cls) {
        return new zzx<>(str, cls, false);
    }

    private zzx(String str, Class<T> cls, boolean z) {
        this.zza = zzcj.zza(str);
        this.zzb = (Class) zzcj.zza(cls, "class");
    }

    public final String zza() {
        return this.zza;
    }

    public final T zza(Object obj) {
        return this.zzb.cast(obj);
    }

    public final void zza(Object obj, zzaj zzajVar) {
        zzajVar.zza(this.zza, obj);
    }

    public final String toString() {
        String name2 = getClass().getName();
        String str = this.zza;
        String name3 = this.zzb.getName();
        return new StringBuilder(String.valueOf(name2).length() + 3 + String.valueOf(str).length() + String.valueOf(name3).length()).append(name2).append("/").append(str).append("[").append(name3).append("]").toString();
    }
}
