package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaln;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalo;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzalo<MessageType extends zzalo<MessageType, BuilderType>, BuilderType extends zzaln<MessageType, BuilderType>> implements zzapf {
    protected int zza = 0;

    /* JADX WARN: Multi-variable type inference failed */
    protected static <T> void zzt(Iterable<T> iterable, List<? super T> list) {
        zzaoc.zze(iterable);
        if (iterable instanceof zzaom) {
            List<?> listZzh = ((zzaom) iterable).zzh();
            zzaom zzaomVar = (zzaom) list;
            int size = list.size();
            for (Object obj : listZzh) {
                if (obj == null) {
                    int size2 = zzaomVar.size();
                    StringBuilder sb = new StringBuilder(37);
                    sb.append("Element at index ");
                    sb.append(size2 - size);
                    sb.append(" is null.");
                    String string = sb.toString();
                    int size3 = zzaomVar.size();
                    while (true) {
                        size3--;
                        if (size3 < size) {
                            throw new NullPointerException(string);
                        }
                        zzaomVar.remove(size3);
                    }
                } else if (obj instanceof zzamk) {
                    zzaomVar.zzi((zzamk) obj);
                } else {
                    zzaomVar.add((String) obj);
                }
            }
            return;
        }
        if (iterable instanceof zzapn) {
            list.addAll(iterable);
            return;
        }
        if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
            ((ArrayList) list).ensureCapacity(list.size() + iterable.size());
        }
        int size4 = list.size();
        for (T t : iterable) {
            if (t == null) {
                int size5 = list.size();
                StringBuilder sb2 = new StringBuilder(37);
                sb2.append("Element at index ");
                sb2.append(size5 - size4);
                sb2.append(" is null.");
                String string2 = sb2.toString();
                int size6 = list.size();
                while (true) {
                    size6--;
                    if (size6 < size4) {
                        throw new NullPointerException(string2);
                    }
                    list.remove(size6);
                }
            } else {
                list.add(t);
            }
        }
    }

    public final void zzP(OutputStream outputStream) throws IOException {
        zzamx zzamxVarZzP = zzamx.zzP(outputStream, zzamx.zzJ(zzy()));
        zzQ(zzamxVarZzP);
        zzamxVarZzP.zzV();
    }

    int zzr() {
        throw null;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapf
    public final zzamk zzs() {
        try {
            int iZzy = zzy();
            zzamk zzamkVar = zzamk.zzb;
            byte[] bArr = new byte[iZzy];
            zzamx zzamxVarZzO = zzamx.zzO(bArr);
            zzQ(zzamxVarZzO);
            return zzamf.zza(zzamxVarZzO, bArr);
        } catch (IOException e) {
            String name2 = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name2).length() + 72);
            sb.append("Serializing ");
            sb.append(name2);
            sb.append(" to a ByteString threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    void zzu(int i) {
        throw null;
    }

    public final byte[] zzw() {
        try {
            byte[] bArr = new byte[zzy()];
            zzamx zzamxVarZzO = zzamx.zzO(bArr);
            zzQ(zzamxVarZzO);
            zzamxVarZzO.zzQ();
            return bArr;
        } catch (IOException e) {
            String name2 = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name2).length() + 72);
            sb.append("Serializing ");
            sb.append(name2);
            sb.append(" to a byte array threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }
}
