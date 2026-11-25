package com.google.android.gms.internal.mlkit_vision_text;

import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzv {
    private final String zza = "\n";

    private zzv(String str) {
    }

    public static zzv zza(String str) {
        return new zzv("\n");
    }

    public final String zzb(Iterable<?> iterable) {
        Iterator<?> it = iterable.iterator();
        StringBuilder sb = new StringBuilder();
        try {
            if (it.hasNext()) {
                sb.append(zzc(it.next()));
                while (it.hasNext()) {
                    sb.append((CharSequence) this.zza);
                    sb.append(zzc(it.next()));
                }
            }
            return sb.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    static final CharSequence zzc(Object obj) {
        obj.getClass();
        return obj instanceof CharSequence ? (CharSequence) obj : obj.toString();
    }
}
