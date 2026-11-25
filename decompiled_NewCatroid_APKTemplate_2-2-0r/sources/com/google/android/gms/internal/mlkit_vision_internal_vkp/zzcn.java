package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.util.Log;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcn {
    public static final zzcn zza = new zzcn("VisionKit", 2);
    private final String zzb = "VisionKit";

    public zzcn(String str, int i) {
    }

    private final boolean zzd(int i) {
        return Log.isLoggable(this.zzb, i);
    }

    private static final String zze(Object obj, String str, Object... objArr) {
        String str2;
        if (obj instanceof String) {
            str2 = (String) obj;
        } else {
            String name2 = obj.getClass().getName();
            if (obj instanceof Class) {
                name2 = ((Class) obj).getName();
            }
            String[] strArrSplit = name2.split("\\.");
            int length = strArrSplit.length;
            str2 = length == 0 ? "" : strArrSplit[length - 1];
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 3 + String.valueOf(str).length());
        sb.append("[");
        sb.append(str2);
        sb.append("] ");
        sb.append(str);
        return sb.toString();
    }

    public final void zza(Throwable th, String str, Object... objArr) {
        if (zzd(6)) {
            Log.e(this.zzb, "Error in result from JNI layer", th);
        }
    }

    public final void zzb(@Nullable Object obj, String str, Object... objArr) {
        if (zzd(4)) {
            Log.i(this.zzb, zze(obj, str, objArr));
        }
    }

    public final void zzc(@Nullable Object obj, String str, Object... objArr) {
        if (zzd(5)) {
            Log.w(this.zzb, zze(obj, str, objArr));
        }
    }
}
