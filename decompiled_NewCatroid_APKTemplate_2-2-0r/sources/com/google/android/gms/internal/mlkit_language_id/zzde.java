package com.google.android.gms.internal.mlkit_language_id;

import com.google.android.gms.internal.mlkit_language_id.zzde;
import com.google.android.gms.internal.mlkit_language_id.zzdh;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzde<MessageType extends zzde<MessageType, BuilderType>, BuilderType extends zzdh<MessageType, BuilderType>> implements zzfz {
    protected int zza = 0;

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfz
    public final zzdn zze() {
        try {
            zzdv zzdvVarZzc = zzdn.zzc(zzj());
            zza(zzdvVarZzc.zzb());
            return zzdvVarZzc.zza();
        } catch (IOException e) {
            String name2 = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name2).length() + 62 + String.valueOf("ByteString").length()).append("Serializing ").append(name2).append(" to a ByteString threw an IOException (should never happen).").toString(), e);
        }
    }

    public final byte[] zzf() {
        try {
            byte[] bArr = new byte[zzj()];
            zzea zzeaVarZza = zzea.zza(bArr);
            zza(zzeaVarZza);
            zzeaVarZza.zzb();
            return bArr;
        } catch (IOException e) {
            String name2 = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name2).length() + 62 + String.valueOf("byte array").length()).append("Serializing ").append(name2).append(" to a byte array threw an IOException (should never happen).").toString(), e);
        }
    }

    int zzg() {
        throw new UnsupportedOperationException();
    }

    void zza(int i) {
        throw new UnsupportedOperationException();
    }

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzeq.zza(iterable);
        if (iterable instanceof zzfg) {
            List<?> listZzb = ((zzfg) iterable).zzb();
            zzfg zzfgVar = (zzfg) list;
            int size = list.size();
            for (Object obj : listZzb) {
                if (obj == null) {
                    String string = new StringBuilder(37).append("Element at index ").append(zzfgVar.size() - size).append(" is null.").toString();
                    for (int size2 = zzfgVar.size() - 1; size2 >= size; size2--) {
                        zzfgVar.remove(size2);
                    }
                    throw new NullPointerException(string);
                }
                if (obj instanceof zzdn) {
                    zzfgVar.zza((zzdn) obj);
                } else {
                    zzfgVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zzgi) {
            list.addAll((Collection) iterable);
            return;
        }
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
        }
        int size3 = list.size();
        for (T t : iterable) {
            if (t == null) {
                String string2 = new StringBuilder(37).append("Element at index ").append(list.size() - size3).append(" is null.").toString();
                for (int size4 = list.size() - 1; size4 >= size3; size4--) {
                    list.remove(size4);
                }
                throw new NullPointerException(string2);
            }
            list.add(t);
        }
    }
}
