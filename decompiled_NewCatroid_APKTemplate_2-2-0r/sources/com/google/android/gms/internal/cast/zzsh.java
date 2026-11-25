package com.google.android.gms.internal.cast;

import com.google.android.gms.internal.cast.zzsg;
import com.google.android.gms.internal.cast.zzsh;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzsh<MessageType extends zzsh<MessageType, BuilderType>, BuilderType extends zzsg<MessageType, BuilderType>> implements zzux {
    protected int zza = 0;

    int zzq(zzvi zzviVar) {
        throw null;
    }

    @Override // com.google.android.gms.internal.cast.zzux
    public final zzsu zzr() {
        try {
            int iZzu = zzu();
            zzsu zzsuVar = zzsu.zzb;
            byte[] bArr = new byte[iZzu];
            zztc zztcVarZzz = zztc.zzz(bArr, 0, iZzu);
            zzJ(zztcVarZzz);
            zztcVarZzz.zzA();
            return new zzsr(bArr);
        } catch (IOException e) {
            throw new RuntimeException("Serializing " + getClass().getName() + " to a ByteString threw an IOException (should never happen).", e);
        }
    }
}
