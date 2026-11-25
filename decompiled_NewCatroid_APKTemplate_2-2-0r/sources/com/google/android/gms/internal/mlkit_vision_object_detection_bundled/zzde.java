package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.io.EOFException;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzde {
    public static zzcj zza(zzeo zzeoVar) throws zzcn {
        boolean z;
        try {
            try {
                zzeoVar.zzn();
                try {
                    return zzem.zzW.zza(zzeoVar);
                } catch (EOFException e) {
                    e = e;
                    z = false;
                    if (z) {
                        return zzcl.zza;
                    }
                    throw new zzcq(e);
                }
            } catch (EOFException e2) {
                e = e2;
                z = true;
            }
        } catch (zzer e3) {
            throw new zzcq(e3);
        } catch (IOException e4) {
            throw new zzck(e4);
        } catch (NumberFormatException e5) {
            throw new zzcq(e5);
        }
    }
}
