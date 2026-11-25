package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.EOFException;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzvv {
    public static zzva zza(zzxf zzxfVar) throws zzve {
        boolean z;
        try {
            try {
                zzxfVar.zzn();
                try {
                    return zzxd.zzW.zza(zzxfVar);
                } catch (EOFException e) {
                    e = e;
                    z = false;
                    if (z) {
                        return zzvc.zza;
                    }
                    throw new zzvh(e);
                }
            } catch (EOFException e2) {
                e = e2;
                z = true;
            }
        } catch (zzxi e3) {
            throw new zzvh(e3);
        } catch (IOException e4) {
            throw new zzvb(e4);
        } catch (NumberFormatException e5) {
            throw new zzvh(e5);
        }
    }
}
