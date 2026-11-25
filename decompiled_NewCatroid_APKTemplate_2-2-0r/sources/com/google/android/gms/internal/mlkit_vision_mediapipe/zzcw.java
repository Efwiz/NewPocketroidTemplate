package com.google.android.gms.internal.mlkit_vision_mediapipe;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcw {
    private static native boolean zza(Context context, String str);

    public static byte[] zza(AssetManager assetManager, String str) {
        try {
            InputStream inputStreamOpen = assetManager.open(str);
            byte[] bArrZza = zzcl.zza(inputStreamOpen);
            inputStreamOpen.close();
            return bArrZza;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean zza(Context context) {
        return zza(context, context.getCacheDir().getAbsolutePath());
    }
}
