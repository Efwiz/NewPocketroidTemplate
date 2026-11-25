package com.google.android.gms.internal.gtm;

import com.google.android.gms.internal.gtm.zzsg;
import com.google.android.gms.internal.gtm.zzsh;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzsh<MessageType extends zzsh<MessageType, BuilderType>, BuilderType extends zzsg<MessageType, BuilderType>> implements zzwk {
    protected int zzb = 0;

    /* JADX WARN: Multi-variable type inference failed */
    protected static <T> void zzS(Iterable<T> iterable, List<? super T> list) {
        zzvi.zze(iterable);
        if (list instanceof ArrayList) {
            ((ArrayList) list).ensureCapacity(list.size() + iterable.size());
        }
        int size = list.size();
        for (T t : iterable) {
            if (t == null) {
                int size2 = list.size();
                StringBuilder sb = new StringBuilder(37);
                sb.append("Element at index ");
                sb.append(size2 - size);
                sb.append(" is null.");
                String string = sb.toString();
                int size3 = list.size();
                while (true) {
                    size3--;
                    if (size3 < size) {
                        throw new NullPointerException(string);
                    }
                    list.remove(size3);
                }
            } else {
                list.add(t);
            }
        }
    }

    int zzQ() {
        throw null;
    }

    @Override // com.google.android.gms.internal.gtm.zzwk
    public final zztd zzR() {
        try {
            int iZzX = zzX();
            zztd zztdVar = zztd.zzb;
            byte[] bArr = new byte[iZzX];
            zzto zztoVarZzF = zzto.zzF(bArr);
            zzaq(zztoVarZzF);
            zztoVarZzF.zzG();
            return new zzta(bArr);
        } catch (IOException e) {
            String name2 = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name2).length() + 72);
            sb.append("Serializing ");
            sb.append(name2);
            sb.append(" to a ByteString threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    void zzT(int i) {
        throw null;
    }
}
