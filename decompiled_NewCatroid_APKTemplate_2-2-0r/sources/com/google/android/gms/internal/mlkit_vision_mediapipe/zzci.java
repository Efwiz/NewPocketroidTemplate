package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzci {
    private final Object zza;
    private final Method zzb;
    private final Method zzc;

    @NullableDecl
    public static zzci zza() {
        try {
            Object objInvoke = Class.forName("sun.misc.SharedSecrets").getMethod("getJavaLangAccess", new Class[0]).invoke(null, new Object[0]);
            Method method = Class.forName("sun.misc.JavaLangAccess").getMethod("getStackTraceElement", Throwable.class, Integer.TYPE);
            Method method2 = Class.forName("sun.misc.JavaLangAccess").getMethod("getStackTraceDepth", Throwable.class);
            ((Integer) method2.invoke(objInvoke, new Throwable())).intValue();
            return new zzci(objInvoke, method, method2);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable unused) {
            return null;
        }
    }

    private zzci(Object obj, Method method, Method method2) {
        this.zza = obj;
        this.zzb = method;
        this.zzc = method2;
    }

    public final StackTraceElement zza(Throwable th, int i) {
        try {
            return (StackTraceElement) this.zzb.invoke(this.zza, th, Integer.valueOf(i));
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            if (e2.getCause() instanceof Error) {
                throw ((Error) e2.getCause());
            }
            throw new RuntimeException(e2.getCause());
        }
    }

    public final int zza(Throwable th) {
        try {
            return ((Integer) this.zzc.invoke(this.zza, th)).intValue();
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            if (e2.getCause() instanceof Error) {
                throw ((Error) e2.getCause());
            }
            throw new RuntimeException(e2.getCause());
        }
    }
}
