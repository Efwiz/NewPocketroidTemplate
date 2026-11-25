package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.io.StringWriter;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzva {
    public final String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            zzxh zzxhVar = new zzxh(stringWriter);
            zzxhVar.zzj(true);
            zzxd.zzW.zzb(zzxhVar, this);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public final zzvd zzb() {
        if (this instanceof zzvd) {
            return (zzvd) this;
        }
        String strValueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 19);
        sb.append("Not a JSON Object: ");
        sb.append(strValueOf);
        throw new IllegalStateException(sb.toString());
    }
}
