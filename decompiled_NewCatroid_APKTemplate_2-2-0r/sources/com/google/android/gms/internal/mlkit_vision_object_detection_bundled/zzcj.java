package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.io.IOException;
import java.io.StringWriter;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public class zzcj {
    public final String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            zzeq zzeqVar = new zzeq(stringWriter);
            zzeqVar.zzj(true);
            zzem.zzW.zzb(zzeqVar, this);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public final zzcm zzb() {
        if (this instanceof zzcm) {
            return (zzcm) this;
        }
        String strValueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 19);
        sb.append("Not a JSON Object: ");
        sb.append(strValueOf);
        throw new IllegalStateException(sb.toString());
    }
}
