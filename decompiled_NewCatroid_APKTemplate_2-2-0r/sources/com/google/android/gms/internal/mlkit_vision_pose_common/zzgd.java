package com.google.android.gms.internal.mlkit_vision_pose_common;

import com.google.android.gms.internal.mlkit_vision_pose_common.zzgd;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzgd.zza;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzgd<MessageType extends zzgd<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzeu<MessageType, BuilderType> {
    private static Map<Object, zzgd<?, ?>> zzd = new ConcurrentHashMap();
    protected zziw zzb = zziw.zza();
    private int zzc = -1;

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    protected static class zzc<T extends zzgd<T, ?>> extends zzev<T> {
        private final T zza;

        public zzc(T t) {
            this.zza = t;
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    static final class zzd implements zzfx<zzd> {
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfx
        public final int zza() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfx
        public final zzjh zzb() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfx
        public final zzjo zzc() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfx
        public final boolean zzd() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfx
        public final boolean zze() {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfx
        public final zzho zza(zzho zzhoVar, zzhl zzhlVar) {
            throw new NoSuchMethodError();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfx
        public final zzhr zza(zzhr zzhrVar, zzhr zzhrVar2) {
            throw new NoSuchMethodError();
        }

        @Override // java.lang.Comparable
        public final /* synthetic */ int compareTo(Object obj) {
            throw new NoSuchMethodError();
        }
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static abstract class zze<MessageType extends zze<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zzgd<MessageType, BuilderType> implements zzhn {
        protected zzfv<zzd> zzc = zzfv.zza();
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
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

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static abstract class zzb<MessageType extends zze<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> extends zza<MessageType, BuilderType> implements zzhn {
        protected zzb(MessageType messagetype) {
            super(messagetype);
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd.zza
        protected void zzc() {
            super.zzc();
            ((zze) this.zza).zzc = (zzfv) ((zze) this.zza).zzc.clone();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd.zza
        /* JADX INFO: renamed from: zze */
        public /* synthetic */ zzgd zzg() {
            return (zze) zzg();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzgd.zza, com.google.android.gms.internal.mlkit_vision_pose_common.zzho
        public /* synthetic */ zzhl zzg() {
            if (this.zzb) {
                return (zze) this.zza;
            }
            ((zze) this.zza).zzc.zzb();
            return (zze) super.zzg();
        }
    }

    public String toString() {
        return zzhq.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zza != 0) {
            return this.zza;
        }
        this.zza = zzia.zza().zza(this).zza(this);
        return this.zza;
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public static abstract class zza<MessageType extends zzgd<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzet<MessageType, BuilderType> {
        protected MessageType zza;
        protected boolean zzb = false;
        private final MessageType zzc;

        protected zza(MessageType messagetype) {
            this.zzc = messagetype;
            this.zza = (MessageType) messagetype.zza(zzf.zzd, null, null);
        }

        protected void zzc() {
            MessageType messagetype = (MessageType) this.zza.zza(zzf.zzd, null, null);
            zza(messagetype, this.zza);
            this.zza = messagetype;
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhn
        public final boolean b_() {
            return zzgd.zza(this.zza, false);
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzho
        /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
        public MessageType zzg() {
            if (this.zzb) {
                return this.zza;
            }
            MessageType messagetype = this.zza;
            zzia.zza().zza(messagetype).zzc(messagetype);
            this.zzb = true;
            return this.zza;
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzho
        /* JADX INFO: renamed from: zzf, reason: merged with bridge method [inline-methods] */
        public final MessageType zzh() {
            MessageType messagetype = (MessageType) zzg();
            if (messagetype.b_()) {
                return messagetype;
            }
            throw new zziu(messagetype);
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzet
        public final BuilderType zza(MessageType messagetype) {
            if (this.zzb) {
                zzc();
                this.zzb = false;
            }
            zza(this.zza, messagetype);
            return this;
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzia.zza().zza(messagetype).zzb(messagetype, messagetype2);
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzet
        /* JADX INFO: renamed from: zzb */
        public final /* synthetic */ zzet clone() {
            return (zza) clone();
        }

        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhn
        public final /* synthetic */ zzhl zzi() {
            return this.zzc;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzet
        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zzaVar = (zza) this.zzc.zza(zzf.zze, null, null);
            zzaVar.zza((zzgd) zzg());
            return zzaVar;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return zzia.zza().zza(this).zza(this, (zzgd) obj);
        }
        return false;
    }

    protected final <MessageType extends zzgd<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> BuilderType zzh() {
        return (BuilderType) zza(zzf.zze, (Object) null, (Object) null);
    }

    protected final <MessageType extends zzgd<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> BuilderType zza(MessageType messagetype) {
        return (BuilderType) zzh().zza((zzgd) messagetype);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhn
    public final boolean b_() {
        return zza(this, Boolean.TRUE.booleanValue());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzeu
    final int zzg() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzeu
    final void zza(int i) {
        this.zzc = i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhl
    public final void zza(zzfq zzfqVar) throws IOException {
        zzia.zza().zza(this).zza(this, zzfs.zza(zzfqVar));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhl
    public final int zzj() {
        if (this.zzc == -1) {
            this.zzc = zzia.zza().zza(this).zzb(this);
        }
        return this.zzc;
    }

    static <T extends zzgd<?, ?>> T zza(Class<T> cls) {
        zzgd<?, ?> zzgdVar = zzd.get(cls);
        if (zzgdVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzgdVar = zzd.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzgdVar == null) {
            zzgdVar = (T) ((zzgd) zziz.zza(cls)).zza(zzf.zzf, (Object) null, (Object) null);
            if (zzgdVar == null) {
                throw new IllegalStateException();
            }
            zzd.put(cls, zzgdVar);
        }
        return (T) zzgdVar;
    }

    protected static <T extends zzgd<?, ?>> void zza(Class<T> cls, T t) {
        zzd.put(cls, t);
    }

    protected static Object zza(zzhl zzhlVar, String str, Object[] objArr) {
        return new zzic(zzhlVar, str, objArr);
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

    protected static final <T extends zzgd<T, ?>> boolean zza(T t, boolean z) {
        byte bByteValue = ((Byte) t.zza(zzf.zza, null, null)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zZzd = zzia.zza().zza(t).zzd(t);
        if (z) {
            t.zza(zzf.zzb, zZzd ? t : null, null);
        }
        return zZzd;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.gms.internal.mlkit_vision_pose_common.zzge, com.google.android.gms.internal.mlkit_vision_pose_common.zzgk] */
    protected static zzgk zzk() {
        return zzge.zzd();
    }

    protected static <E> zzgm<E> zzl() {
        return zzhz.zzd();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhl
    public final /* synthetic */ zzho zzm() {
        zza zzaVar = (zza) zza(zzf.zze, (Object) null, (Object) null);
        zzaVar.zza(this);
        return zzaVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzhn
    public final /* synthetic */ zzhl zzi() {
        return (zzgd) zza(zzf.zzf, (Object) null, (Object) null);
    }
}
