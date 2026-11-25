package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzanm;
import com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzans<MessageType extends zzans<MessageType, BuilderType>, BuilderType extends zzanm<MessageType, BuilderType>> extends zzalo<MessageType, BuilderType> {
    private static final Map<Object, zzans<?, ?>> zzb = new ConcurrentHashMap();
    protected zzaqu zzc = zzaqu.zzc();
    protected int zzd = -1;

    static <T extends zzans> T zzB(Class<T> cls) {
        Map<Object, zzans<?, ?>> map = zzb;
        zzans<?, ?> zzansVar = map.get(cls);
        if (zzansVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzansVar = map.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzansVar == null) {
            zzansVar = (zzans) ((zzans) zzard.zze(cls)).zzb(6, null, null);
            if (zzansVar == null) {
                throw new IllegalStateException();
            }
            map.put(cls, zzansVar);
        }
        return zzansVar;
    }

    protected static <T extends zzans<T, ?>> T zzC(T t, InputStream inputStream, zzanc zzancVar) throws zzaoe {
        zzamq zzamoVar;
        int i = zzamq.zzd;
        zzamn zzamnVar = null;
        if (inputStream == null) {
            byte[] bArr = zzaoc.zzc;
            int length = bArr.length;
            zzamoVar = zzamq.zzH(bArr, 0, 0, false);
        } else {
            zzamoVar = new zzamo(inputStream, 4096, zzamnVar);
        }
        T t2 = (T) t.zzb(4, null, null);
        try {
            zzapz zzapzVarZzb = zzapo.zza().zzb(t2.getClass());
            zzapzVarZzb.zzh(t2, zzamr.zzq(zzamoVar), zzancVar);
            zzapzVarZzb.zzf(t2);
            zzc(t2);
            return t2;
        } catch (zzaoe e) {
            e = e;
            if (e.zzl()) {
                e = new zzaoe(e);
            }
            e.zzh(t2);
            throw e;
        } catch (IOException e2) {
            if (e2.getCause() instanceof zzaoe) {
                throw ((zzaoe) e2.getCause());
            }
            zzaoe zzaoeVar = new zzaoe(e2);
            zzaoeVar.zzh(t2);
            throw zzaoeVar;
        } catch (RuntimeException e3) {
            if (e3.getCause() instanceof zzaoe) {
                throw ((zzaoe) e3.getCause());
            }
            throw e3;
        }
    }

    protected static <T extends zzans<T, ?>> T zzD(T t, byte[] bArr, zzanc zzancVar) throws zzaoe {
        T t2 = (T) zzE(t, bArr, 0, bArr.length, zzancVar);
        zzc(t2);
        return t2;
    }

    static <T extends zzans<T, ?>> T zzE(T t, byte[] bArr, int i, int i2, zzanc zzancVar) throws zzaoe {
        T t2 = (T) t.zzb(4, null, null);
        try {
            zzapz zzapzVarZzb = zzapo.zza().zzb(t2.getClass());
            zzapzVarZzb.zzi(t2, bArr, 0, i2, new zzalv(zzancVar));
            zzapzVarZzb.zzf(t2);
            if (t2.zza == 0) {
                return t2;
            }
            throw new RuntimeException();
        } catch (zzaoe e) {
            e = e;
            if (e.zzl()) {
                e = new zzaoe(e);
            }
            e.zzh(t2);
            throw e;
        } catch (IOException e2) {
            if (e2.getCause() instanceof zzaoe) {
                throw ((zzaoe) e2.getCause());
            }
            zzaoe zzaoeVar = new zzaoe(e2);
            zzaoeVar.zzh(t2);
            throw zzaoeVar;
        } catch (IndexOutOfBoundsException unused) {
            zzaoe zzaoeVarZzj = zzaoe.zzj();
            zzaoeVarZzj.zzh(t2);
            throw zzaoeVarZzj;
        }
    }

    protected static zzanx zzF() {
        return zzanj.zze();
    }

    protected static zzany zzG() {
        return zzant.zzf();
    }

    protected static zzaoa zzH() {
        return zzaot.zzf();
    }

    protected static <E> zzaob<E> zzI() {
        return zzapp.zze();
    }

    protected static <E> zzaob<E> zzJ(zzaob<E> zzaobVar) {
        int size = zzaobVar.size();
        return zzaobVar.zzd(size == 0 ? 10 : size + size);
    }

    static Object zzK(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected static Object zzL(zzapf zzapfVar, String str, Object[] objArr) {
        return new zzapq(zzapfVar, str, objArr);
    }

    protected static <T extends zzans> void zzM(Class<T> cls, T t) {
        zzb.put(cls, t);
    }

    private static <T extends zzans<T, ?>> T zzc(T t) throws zzaoe {
        if (t == null || t.zzx()) {
            return t;
        }
        zzaoe zzaoeVar = new zzaoe(new zzaqs(t).getMessage());
        zzaoeVar.zzh(t);
        throw zzaoeVar;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzapo.zza().zzb(getClass()).zzk(this, (zzans) obj);
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zza;
        if (i != 0) {
            return i;
        }
        int iZzb = zzapo.zza().zzb(getClass()).zzb(this);
        this.zza = iZzb;
        return iZzb;
    }

    public final String toString() {
        return zzaph.zza(this, super.toString());
    }

    public final BuilderType zzA() {
        BuilderType buildertype = (BuilderType) zzb(5, null, null);
        buildertype.zzq(this);
        return buildertype;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapf
    public final /* bridge */ /* synthetic */ zzape zzN() {
        return (zzanm) zzb(5, null, null);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapf
    public final /* bridge */ /* synthetic */ zzape zzO() {
        zzanm zzanmVar = (zzanm) zzb(5, null, null);
        zzanmVar.zzq(this);
        return zzanmVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapf
    public final void zzQ(zzamx zzamxVar) throws IOException {
        zzapo.zza().zzb(getClass()).zzj(this, zzamy.zza(zzamxVar));
    }

    protected abstract Object zzb(int i, Object obj, Object obj2);

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalo
    final int zzr() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzalo
    final void zzu(int i) {
        this.zzd = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg
    public final /* bridge */ /* synthetic */ zzapf zzv() {
        return (zzans) zzb(6, null, null);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapg
    public final boolean zzx() {
        boolean zBooleanValue = Boolean.TRUE.booleanValue();
        byte bByteValue = ((Byte) zzb(1, null, null)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zZzl = zzapo.zza().zzb(getClass()).zzl(this);
        if (!zBooleanValue) {
            return zZzl;
        }
        zzb(2, true != zZzl ? null : this, null);
        return zZzl;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzapf
    public final int zzy() {
        int i = this.zzd;
        if (i != -1) {
            return i;
        }
        int iZza = zzapo.zza().zzb(getClass()).zza(this);
        this.zzd = iZza;
        return iZza;
    }

    protected final <MessageType extends zzans<MessageType, BuilderType>, BuilderType extends zzanm<MessageType, BuilderType>> BuilderType zzz() {
        return (BuilderType) zzb(5, null, null);
    }
}
