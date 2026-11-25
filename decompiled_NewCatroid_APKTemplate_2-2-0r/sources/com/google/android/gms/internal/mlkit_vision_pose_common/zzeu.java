package com.google.android.gms.internal.mlkit_vision_pose_common;

import com.google.android.gms.internal.mlkit_vision_pose_common.zzet;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzeu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzeu<MessageType extends zzeu<MessageType, BuilderType>, BuilderType extends zzet<MessageType, BuilderType>> implements zzhl {
    protected int zza = 0;

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhl
    public final zzez zze() {
        try {
            zzfh zzfhVarZzc = zzez.zzc(zzj());
            zza(zzfhVarZzc.zzb());
            return zzfhVarZzc.zza();
        } catch (IOException e) {
            String name2 = getClass().getName();
            throw new RuntimeException(new StringBuilder(String.valueOf(name2).length() + 62 + String.valueOf("ByteString").length()).append("Serializing ").append(name2).append(" to a ByteString threw an IOException (should never happen).").toString(), e);
        }
    }

    public final byte[] zzf() {
        try {
            byte[] bArr = new byte[zzj()];
            zzfq zzfqVarZza = zzfq.zza(bArr);
            zza(zzfqVarZza);
            zzfqVarZza.zzb();
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
        zzgg.zza(iterable);
        if (iterable instanceof zzgw) {
            List<?> listZzd = ((zzgw) iterable).zzd();
            zzgw zzgwVar = (zzgw) list;
            int size = list.size();
            for (Object obj : listZzd) {
                if (obj == null) {
                    String string = new StringBuilder(37).append("Element at index ").append(zzgwVar.size() - size).append(" is null.").toString();
                    for (int size2 = zzgwVar.size() - 1; size2 >= size; size2--) {
                        zzgwVar.remove(size2);
                    }
                    throw new NullPointerException(string);
                }
                if (obj instanceof zzez) {
                    zzgwVar.zza((zzez) obj);
                } else {
                    zzgwVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zzhy) {
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
