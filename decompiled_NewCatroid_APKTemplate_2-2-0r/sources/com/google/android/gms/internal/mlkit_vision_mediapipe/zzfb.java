package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzfb;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzfb.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzfb<MessageType extends zzfb<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzdl<MessageType, BuilderType> {
    private static Map<Object, zzfb<?, ?>> zzd = new ConcurrentHashMap();
    protected zzhs zzb = zzhs.zza();
    private int zzc = -1;

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    protected static class zzc<T extends zzfb<T, ?>> extends zzdp<T> {
        private final T zza;

        public zzc(T t) {
            this.zza = t;
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public static class zzd<ContainingType extends zzgi, Type> extends zzep<ContainingType, Type> {
    }

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    static final class zze implements zzew<zze> {
        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzew
        public final int zza() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzew
        public final zzig zzb() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzew
        public final zzij zzc() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzew
        public final boolean zzd() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzew
        public final boolean zze() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzew
        public final zzgh zza(zzgh zzghVar, zzgi zzgiVar) {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzew
        public final zzgn zza(zzgn zzgnVar, zzgn zzgnVar2) {
            throw new NoSuchMethodError();
        }

        @Override // java.lang.Comparable
        public final /* synthetic */ int compareTo(Object obj) {
            throw new NoSuchMethodError();
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public static final enum zzf {
        public static final int zza = 1;
        public static final int zzb = 2;
        public static final int zzc = 3;
        public static final int zzd = 4;
        public static final int zze = 5;
        public static final int zzf = 6;
        public static final int zzg = 7;
        private static final /* synthetic */ int[] zzh = {1, 2, 3, 4, 5, 6, 7};

        public static int[] zza() {
            return (int[]) zzh.clone();
        }
    }

    protected abstract Object zza(int i, Object obj, Object obj2);

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public static abstract class zzb<MessageType extends zzb<MessageType, BuilderType>, BuilderType> extends zzfb<MessageType, BuilderType> implements zzgk {
        protected zzeu<zze> zzc = zzeu.zza();

        final zzeu<zze> zza() {
            if (this.zzc.zzc()) {
                this.zzc = (zzeu) this.zzc.clone();
            }
            return this.zzc;
        }
    }

    public String toString() {
        return zzgj.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zza != 0) {
            return this.zza;
        }
        this.zza = zzgw.zza().zza(this).zza(this);
        return this.zza;
    }

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    public static abstract class zza<MessageType extends zzfb<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzdn<MessageType, BuilderType> {
        private final MessageType zza;
        private MessageType zzb;
        private boolean zzc = false;

        protected zza(MessageType messagetype) {
            this.zza = messagetype;
            this.zzb = (MessageType) messagetype.zza(zzf.zzd, null, null);
        }

        protected void zzb() {
            MessageType messagetype = (MessageType) this.zzb.zza(zzf.zzd, null, null);
            zza(messagetype, this.zzb);
            this.zzb = messagetype;
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgk
        public final boolean a_() {
            return zzfb.zza(this.zzb, false);
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgh
        /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
        public MessageType zzf() {
            if (this.zzc) {
                return this.zzb;
            }
            MessageType messagetype = this.zzb;
            zzgw.zza().zza(messagetype).zzc(messagetype);
            this.zzc = true;
            return this.zzb;
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgh
        /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
        public final MessageType zzg() {
            MessageType messagetype = (MessageType) zzf();
            if (messagetype.a_()) {
                return messagetype;
            }
            throw new zzhq(messagetype);
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdn
        public final BuilderType zza(MessageType messagetype) {
            if (this.zzc) {
                zzb();
                this.zzc = false;
            }
            zza(this.zzb, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzgw.zza().zza(messagetype).zzb(messagetype, messagetype2);
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdn
        /* JADX INFO: renamed from: zza */
        public final /* synthetic */ zzdn clone() {
            return (zza) clone();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgk
        public final /* synthetic */ zzgi zzh() {
            return this.zza;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdn
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zzaVar = (zza) this.zza.zza(zzf.zze, null, null);
            zzaVar.zza((zzfb) zzf());
            return zzaVar;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzgw.zza().zza(this).zza(this, (zzfb) obj);
        }
        return false;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgk
    public final boolean a_() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdl
    final int zzf() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdl
    final void zza(int i) {
        this.zzc = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgi
    public final void zza(zzel zzelVar) throws IOException {
        zzgw.zza().zza(this).zza(this, zzen.zza(zzelVar));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgi
    public final int zzg() {
        if (this.zzc == -1) {
            this.zzc = zzgw.zza().zza(this).zzb(this);
        }
        return this.zzc;
    }

    static <T extends zzfb<?, ?>> T zza(Class<T> cls) {
        zzfb<?, ?> zzfbVar = zzd.get(cls);
        if (zzfbVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzfbVar = zzd.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzfbVar == null) {
            zzfbVar = (T) ((zzfb) zzhv.zza(cls)).zza(zzf.zzf, (Object) null, (Object) null);
            if (zzfbVar == null) {
                throw new IllegalStateException();
            }
            zzd.put(cls, zzfbVar);
        }
        return (T) zzfbVar;
    }

    protected static <T extends zzfb<?, ?>> void zza(Class<T> cls, T t) {
        zzd.put(cls, t);
    }

    protected static Object zza(zzgi zzgiVar, String str, Object[] objArr) {
        return new zzgy(zzgiVar, str, objArr);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
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

    protected static final <T extends zzfb<T, ?>> boolean zza(T t, boolean z) {
        byte bByteValue = ((Byte) t.zza(zzf.zza, null, null)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zZzd = zzgw.zza().zza(t).zzd(t);
        if (z) {
            t.zza(zzf.zzb, zZzd ? t : null, null);
        }
        return zZzd;
    }

    protected static <E> zzff<E> zzi() {
        return zzgv.zzd();
    }

    private static <T extends zzfb<T, ?>> T zza(T t, byte[] bArr, int i, int i2, zzeo zzeoVar) throws zzfi {
        T t2 = (T) t.zza(zzf.zzd, null, null);
        try {
            zzgx zzgxVarZza = zzgw.zza().zza(t2);
            zzgxVarZza.zza(t2, bArr, 0, i2, new zzdt(zzeoVar));
            zzgxVarZza.zzc(t2);
            if (t2.zza == 0) {
                return t2;
            }
            throw new RuntimeException();
        } catch (IOException e) {
            if (e.getCause() instanceof zzfi) {
                throw ((zzfi) e.getCause());
            }
            throw new zzfi(e.getMessage()).zza(t2);
        } catch (IndexOutOfBoundsException unused) {
            throw zzfi.zza().zza(t2);
        }
    }

    protected static <T extends zzfb<T, ?>> T zza(T t, byte[] bArr) throws zzfi {
        T t2 = (T) zza(t, bArr, 0, bArr.length, zzeo.zza());
        if (t2 == null || t2.a_()) {
            return t2;
        }
        throw new zzfi(new zzhq(t2).getMessage()).zza(t2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgi
    public final /* synthetic */ zzgh zzj() {
        zza zzaVar = (zza) zza(zzf.zze, (Object) null, (Object) null);
        zzaVar.zza(this);
        return zzaVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgi
    public final /* synthetic */ zzgh zzk() {
        return (zza) zza(zzf.zze, (Object) null, (Object) null);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgk
    public final /* synthetic */ zzgi zzh() {
        return (zzfb) zza(zzf.zzf, (Object) null, (Object) null);
    }
}
