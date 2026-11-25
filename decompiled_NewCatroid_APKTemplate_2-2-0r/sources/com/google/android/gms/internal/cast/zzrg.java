package com.google.android.gms.internal.cast;

import com.google.common.util.concurrent.ListenableFuture;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.mozilla.javascript.ES6Iterator;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzrg<V> extends zzsf implements ListenableFuture<V> {
    static final boolean zza;
    private static final Logger zzb;
    private static final zza zzc;
    private static final Object zzd;

    @CheckForNull
    private volatile zzd listeners;

    @CheckForNull
    private volatile Object value;

    @CheckForNull
    private volatile zzk waiters;

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    abstract class zza {
        /* synthetic */ zza(zzrf zzrfVar) {
        }

        abstract zzd zza(zzrg zzrgVar, zzd zzdVar);

        abstract zzk zzb(zzrg zzrgVar, zzk zzkVar);

        abstract void zzc(zzk zzkVar, @CheckForNull zzk zzkVar2);

        abstract void zzd(zzk zzkVar, Thread thread);

        abstract boolean zze(zzrg zzrgVar, @CheckForNull zzd zzdVar, zzd zzdVar2);

        abstract boolean zzf(zzrg zzrgVar, @CheckForNull Object obj, Object obj2);

        abstract boolean zzg(zzrg zzrgVar, @CheckForNull zzk zzkVar, @CheckForNull zzk zzkVar2);
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    final class zzb {

        @CheckForNull
        static final zzb zza;

        @CheckForNull
        static final zzb zzb;
        final boolean zzc;

        @CheckForNull
        final Throwable zzd;

        static {
            if (zzrg.zza) {
                zzb = null;
                zza = null;
            } else {
                zzb = new zzb(false, null);
                zza = new zzb(true, null);
            }
        }

        zzb(boolean z, @CheckForNull Throwable th) {
            this.zzc = z;
            this.zzd = th;
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    final class zzc {
        static final zzc zza = new zzc(new Throwable("Failure occurred while trying to finish a future.") { // from class: com.google.android.gms.internal.cast.zzrg.zzc.1
            {
                super("Failure occurred while trying to finish a future.");
            }

            @Override // java.lang.Throwable
            public final synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable zzb;

        zzc(Throwable th) {
            th.getClass();
            this.zzb = th;
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    final class zzd {
        static final zzd zza = new zzd();

        @CheckForNull
        zzd next;

        @CheckForNull
        final Runnable zzb;

        @CheckForNull
        final Executor zzc;

        zzd() {
            this.zzb = null;
            this.zzc = null;
        }

        zzd(Runnable runnable, Executor executor) {
            this.zzb = runnable;
            this.zzc = executor;
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    final class zze extends zza {
        final AtomicReferenceFieldUpdater<zzk, Thread> zza;
        final AtomicReferenceFieldUpdater<zzk, zzk> zzb;
        final AtomicReferenceFieldUpdater<zzrg, zzk> zzc;
        final AtomicReferenceFieldUpdater<zzrg, zzd> zzd;
        final AtomicReferenceFieldUpdater<zzrg, Object> zze;

        zze(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater5) {
            super(null);
            this.zza = atomicReferenceFieldUpdater;
            this.zzb = atomicReferenceFieldUpdater2;
            this.zzc = atomicReferenceFieldUpdater3;
            this.zzd = atomicReferenceFieldUpdater4;
            this.zze = atomicReferenceFieldUpdater5;
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final zzd zza(zzrg zzrgVar, zzd zzdVar) {
            return this.zzd.getAndSet(zzrgVar, zzdVar);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final zzk zzb(zzrg zzrgVar, zzk zzkVar) {
            return this.zzc.getAndSet(zzrgVar, zzkVar);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final void zzc(zzk zzkVar, @CheckForNull zzk zzkVar2) {
            this.zzb.lazySet(zzkVar, zzkVar2);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final void zzd(zzk zzkVar, Thread thread) {
            this.zza.lazySet(zzkVar, thread);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final boolean zze(zzrg zzrgVar, @CheckForNull zzd zzdVar, zzd zzdVar2) {
            return zzrh.zza(this.zzd, zzrgVar, zzdVar, zzdVar2);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final boolean zzf(zzrg zzrgVar, @CheckForNull Object obj, Object obj2) {
            return zzrh.zza(this.zze, zzrgVar, obj, obj2);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final boolean zzg(zzrg zzrgVar, @CheckForNull zzk zzkVar, @CheckForNull zzk zzkVar2) {
            return zzrh.zza(this.zzc, zzrgVar, zzkVar, zzkVar2);
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    final class zzf<V> implements Runnable {
        final zzrg<V> zza;
        final ListenableFuture<? extends V> zzb;

        @Override // java.lang.Runnable
        public final void run() {
            throw null;
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    final class zzg extends zza {
        private zzg() {
            super(null);
        }

        /* synthetic */ zzg(zzri zzriVar) {
            super(null);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final zzd zza(zzrg zzrgVar, zzd zzdVar) {
            zzd zzdVar2;
            synchronized (zzrgVar) {
                zzdVar2 = zzrgVar.listeners;
                if (zzdVar2 != zzdVar) {
                    zzrgVar.listeners = zzdVar;
                }
            }
            return zzdVar2;
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final zzk zzb(zzrg zzrgVar, zzk zzkVar) {
            zzk zzkVar2;
            synchronized (zzrgVar) {
                zzkVar2 = zzrgVar.waiters;
                if (zzkVar2 != zzkVar) {
                    zzrgVar.waiters = zzkVar;
                }
            }
            return zzkVar2;
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final void zzc(zzk zzkVar, @CheckForNull zzk zzkVar2) {
            zzkVar.next = zzkVar2;
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final void zzd(zzk zzkVar, Thread thread) {
            zzkVar.thread = thread;
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final boolean zze(zzrg zzrgVar, @CheckForNull zzd zzdVar, zzd zzdVar2) {
            synchronized (zzrgVar) {
                if (zzrgVar.listeners != zzdVar) {
                    return false;
                }
                zzrgVar.listeners = zzdVar2;
                return true;
            }
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final boolean zzf(zzrg zzrgVar, @CheckForNull Object obj, Object obj2) {
            synchronized (zzrgVar) {
                if (zzrgVar.value != obj) {
                    return false;
                }
                zzrgVar.value = obj2;
                return true;
            }
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final boolean zzg(zzrg zzrgVar, @CheckForNull zzk zzkVar, @CheckForNull zzk zzkVar2) {
            synchronized (zzrgVar) {
                if (zzrgVar.waiters != zzkVar) {
                    return false;
                }
                zzrgVar.waiters = zzkVar2;
                return true;
            }
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    interface zzh<V> extends ListenableFuture<V> {
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    abstract class zzi<V> extends zzrg<V> implements zzh<V> {
        zzi() {
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    final class zzj extends zza {
        static final Unsafe zza;
        static final long zzb;
        static final long zzc;
        static final long zzd;
        static final long zze;
        static final long zzf;

        static {
            Unsafe unsafe;
            try {
                try {
                    unsafe = Unsafe.getUnsafe();
                } catch (PrivilegedActionException e) {
                    throw new RuntimeException("Could not initialize intrinsics", e.getCause());
                }
            } catch (SecurityException unused) {
                unsafe = (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.android.gms.internal.cast.zzrg.zzj.1
                    @Override // java.security.PrivilegedExceptionAction
                    public final /* bridge */ /* synthetic */ Unsafe run() throws Exception {
                        for (Field field : Unsafe.class.getDeclaredFields()) {
                            field.setAccessible(true);
                            Object obj = field.get(null);
                            if (Unsafe.class.isInstance(obj)) {
                                return (Unsafe) Unsafe.class.cast(obj);
                            }
                        }
                        throw new NoSuchFieldError("the Unsafe");
                    }
                });
            }
            try {
                zzc = unsafe.objectFieldOffset(zzrg.class.getDeclaredField("waiters"));
                zzb = unsafe.objectFieldOffset(zzrg.class.getDeclaredField("listeners"));
                zzd = unsafe.objectFieldOffset(zzrg.class.getDeclaredField("value"));
                zze = unsafe.objectFieldOffset(zzk.class.getDeclaredField("thread"));
                zzf = unsafe.objectFieldOffset(zzk.class.getDeclaredField(ES6Iterator.NEXT_METHOD));
                zza = unsafe;
            } catch (NoSuchFieldException e2) {
                throw new RuntimeException(e2);
            } catch (RuntimeException e3) {
                throw e3;
            }
        }

        private zzj() {
            super(null);
        }

        /* synthetic */ zzj(zzrk zzrkVar) {
            super(null);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final zzd zza(zzrg zzrgVar, zzd zzdVar) {
            zzd zzdVar2;
            do {
                zzdVar2 = zzrgVar.listeners;
                if (zzdVar == zzdVar2) {
                    break;
                }
            } while (!zze(zzrgVar, zzdVar2, zzdVar));
            return zzdVar2;
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final zzk zzb(zzrg zzrgVar, zzk zzkVar) {
            zzk zzkVar2;
            do {
                zzkVar2 = zzrgVar.waiters;
                if (zzkVar == zzkVar2) {
                    break;
                }
            } while (!zzg(zzrgVar, zzkVar2, zzkVar));
            return zzkVar2;
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final void zzc(zzk zzkVar, @CheckForNull zzk zzkVar2) {
            zza.putObject(zzkVar, zzf, zzkVar2);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final void zzd(zzk zzkVar, Thread thread) {
            zza.putObject(zzkVar, zze, thread);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final boolean zze(zzrg zzrgVar, @CheckForNull zzd zzdVar, zzd zzdVar2) {
            return zzrj.zza(zza, zzrgVar, zzb, zzdVar, zzdVar2);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final boolean zzf(zzrg zzrgVar, @CheckForNull Object obj, Object obj2) {
            return zzrj.zza(zza, zzrgVar, zzd, obj, obj2);
        }

        @Override // com.google.android.gms.internal.cast.zzrg.zza
        final boolean zzg(zzrg zzrgVar, @CheckForNull zzk zzkVar, @CheckForNull zzk zzkVar2) {
            return zzrj.zza(zza, zzrgVar, zzc, zzkVar, zzkVar2);
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    final class zzk {
        static final zzk zza = new zzk(false);

        @CheckForNull
        volatile zzk next;

        @CheckForNull
        volatile Thread thread;

        zzk() {
            zzrg.zzc.zzd(this, Thread.currentThread());
        }

        zzk(boolean z) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        boolean z;
        Throwable th;
        Throwable th2;
        zza zzgVar;
        try {
            z = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
        } catch (SecurityException unused) {
            z = false;
        }
        zza = z;
        zzb = Logger.getLogger(zzrg.class.getName());
        Object[] objArr = 0;
        try {
            zzgVar = new zzj(null);
            th2 = null;
            th = null;
        } catch (Error | RuntimeException e) {
            try {
                th2 = e;
                zzgVar = new zze(AtomicReferenceFieldUpdater.newUpdater(zzk.class, Thread.class, "thread"), AtomicReferenceFieldUpdater.newUpdater(zzk.class, zzk.class, ES6Iterator.NEXT_METHOD), AtomicReferenceFieldUpdater.newUpdater(zzrg.class, zzk.class, "waiters"), AtomicReferenceFieldUpdater.newUpdater(zzrg.class, zzd.class, "listeners"), AtomicReferenceFieldUpdater.newUpdater(zzrg.class, Object.class, "value"));
                th = null;
            } catch (Error | RuntimeException e2) {
                th = e2;
                th2 = e;
                zzgVar = new zzg(objArr == true ? 1 : 0);
            }
        }
        zzc = zzgVar;
        if (th != null) {
            Logger logger = zzb;
            logger.logp(Level.SEVERE, "com.google.common.util.concurrent.AbstractFuture", MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, "UnsafeAtomicHelper is broken!", th2);
            logger.logp(Level.SEVERE, "com.google.common.util.concurrent.AbstractFuture", MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, "SafeAtomicHelper is broken!", th);
        }
        zzd = new Object();
    }

    protected zzrg() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static Object zzn(ListenableFuture listenableFuture) {
        Throwable thZzf;
        if (listenableFuture instanceof zzh) {
            Object zzbVar = ((zzrg) listenableFuture).value;
            if (zzbVar instanceof zzb) {
                zzb zzbVar2 = (zzb) zzbVar;
                if (zzbVar2.zzc) {
                    Throwable th = zzbVar2.zzd;
                    zzbVar = th != null ? new zzb(false, th) : zzb.zzb;
                }
            }
            zzbVar.getClass();
            return zzbVar;
        }
        if ((listenableFuture instanceof zzsf) && (thZzf = ((zzsf) listenableFuture).zzf()) != null) {
            return new zzc(thZzf);
        }
        boolean zIsCancelled = listenableFuture.isCancelled();
        if ((!zza) && zIsCancelled) {
            zzb zzbVar3 = zzb.zzb;
            zzbVar3.getClass();
            return zzbVar3;
        }
        try {
            Object objZzo = zzo(listenableFuture);
            if (!zIsCancelled) {
                return objZzo == null ? zzd : objZzo;
            }
            return new zzb(false, new IllegalArgumentException("get() did not throw CancellationException, despite reporting isCancelled() == true: " + String.valueOf(listenableFuture)));
        } catch (Error e) {
            e = e;
            return new zzc(e);
        } catch (CancellationException e2) {
            return !zIsCancelled ? new zzc(new IllegalArgumentException("get() threw CancellationException, despite reporting isCancelled() == false: ".concat(String.valueOf(String.valueOf(listenableFuture))), e2)) : new zzb(false, e2);
        } catch (RuntimeException e3) {
            e = e3;
            return new zzc(e);
        } catch (ExecutionException e4) {
            return zIsCancelled ? new zzb(false, new IllegalArgumentException("get() did not throw CancellationException, despite reporting isCancelled() == true: ".concat(String.valueOf(String.valueOf(listenableFuture))), e4)) : new zzc(e4.getCause());
        }
    }

    private static Object zzo(Future future) throws ExecutionException {
        Object obj;
        boolean z = false;
        while (true) {
            try {
                obj = future.get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return obj;
    }

    private final void zzp(StringBuilder sb) {
        try {
            Object objZzo = zzo(this);
            sb.append("SUCCESS, result=[");
            if (objZzo == null) {
                sb.append("null");
            } else if (objZzo == this) {
                sb.append("this future");
            } else {
                sb.append(objZzo.getClass().getName());
                sb.append(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
                sb.append(Integer.toHexString(System.identityHashCode(objZzo)));
            }
            sb.append("]");
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (RuntimeException e) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e.getClass());
            sb.append(" thrown from get()]");
        } catch (ExecutionException e2) {
            sb.append("FAILURE, cause=[");
            sb.append(e2.getCause());
            sb.append("]");
        }
    }

    private final void zzq(StringBuilder sb) {
        String strConcat;
        int length = sb.length();
        sb.append("PENDING");
        Object obj = this.value;
        if (obj instanceof zzf) {
            sb.append(", setFuture=[");
            zzr(sb, ((zzf) obj).zzb);
            sb.append("]");
        } else {
            try {
                strConcat = zzfb.zza(zze());
            } catch (RuntimeException | StackOverflowError e) {
                strConcat = "Exception thrown from implementation: ".concat(String.valueOf(String.valueOf(e.getClass())));
            }
            if (strConcat != null) {
                sb.append(", info=[");
                sb.append(strConcat);
                sb.append("]");
            }
        }
        if (isDone()) {
            sb.delete(length, sb.length());
            zzp(sb);
        }
    }

    private final void zzr(StringBuilder sb, @CheckForNull Object obj) {
        try {
            if (obj == this) {
                sb.append("this future");
            } else {
                sb.append(obj);
            }
        } catch (RuntimeException | StackOverflowError e) {
            sb.append("Exception thrown from implementation: ");
            sb.append(e.getClass());
        }
    }

    private static void zzs(zzrg zzrgVar, boolean z) {
        zzd zzdVar = null;
        while (true) {
            for (zzk zzkVarZzb = zzc.zzb(zzrgVar, zzk.zza); zzkVarZzb != null; zzkVarZzb = zzkVarZzb.next) {
                Thread thread = zzkVarZzb.thread;
                if (thread != null) {
                    zzkVarZzb.thread = null;
                    LockSupport.unpark(thread);
                }
            }
            zzrgVar.zzj();
            zzd zzdVar2 = zzdVar;
            zzd zzdVarZza = zzc.zza(zzrgVar, zzd.zza);
            zzd zzdVar3 = zzdVar2;
            while (zzdVarZza != null) {
                zzd zzdVar4 = zzdVarZza.next;
                zzdVarZza.next = zzdVar3;
                zzdVar3 = zzdVarZza;
                zzdVarZza = zzdVar4;
            }
            while (zzdVar3 != null) {
                Runnable runnable = zzdVar3.zzb;
                zzd zzdVar5 = zzdVar3.next;
                runnable.getClass();
                if (runnable instanceof zzf) {
                    zzf zzfVar = (zzf) runnable;
                    zzrgVar = zzfVar.zza;
                    if (zzrgVar.value == zzfVar) {
                        if (zzc.zzf(zzrgVar, zzfVar, zzn(zzfVar.zzb))) {
                            zzdVar = zzdVar5;
                        }
                    } else {
                        continue;
                    }
                } else {
                    Executor executor = zzdVar3.zzc;
                    executor.getClass();
                    zzt(runnable, executor);
                }
                zzdVar3 = zzdVar5;
            }
            return;
        }
    }

    private static void zzt(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            zzb.logp(Level.SEVERE, "com.google.common.util.concurrent.AbstractFuture", "executeListener", "RuntimeException while executing runnable " + String.valueOf(runnable) + " with executor " + String.valueOf(executor), (Throwable) e);
        }
    }

    private final void zzu(zzk zzkVar) {
        zzkVar.thread = null;
        while (true) {
            zzk zzkVar2 = this.waiters;
            if (zzkVar2 != zzk.zza) {
                zzk zzkVar3 = null;
                while (zzkVar2 != null) {
                    zzk zzkVar4 = zzkVar2.next;
                    if (zzkVar2.thread != null) {
                        zzkVar3 = zzkVar2;
                    } else if (zzkVar3 != null) {
                        zzkVar3.next = zzkVar4;
                        if (zzkVar3.thread == null) {
                        }
                    } else if (!zzc.zzg(this, zzkVar2, zzkVar4)) {
                    }
                    zzkVar2 = zzkVar4;
                }
                return;
            }
            return;
        }
    }

    private static final Object zzv(Object obj) throws ExecutionException {
        if (obj instanceof zzb) {
            Throwable th = ((zzb) obj).zzd;
            CancellationException cancellationException = new CancellationException("Task was cancelled.");
            cancellationException.initCause(th);
            throw cancellationException;
        }
        if (obj instanceof zzc) {
            throw new ExecutionException(((zzc) obj).zzb);
        }
        if (obj == zzd) {
            return null;
        }
        return obj;
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public final void addListener(Runnable runnable, Executor executor) {
        zzd zzdVar;
        zzez.zzc(runnable, "Runnable was null.");
        zzez.zzc(executor, "Executor was null.");
        if (!isDone() && (zzdVar = this.listeners) != zzd.zza) {
            zzd zzdVar2 = new zzd(runnable, executor);
            do {
                zzdVar2.next = zzdVar;
                if (zzc.zze(this, zzdVar, zzdVar2)) {
                    return;
                } else {
                    zzdVar = this.listeners;
                }
            } while (zzdVar != zzd.zza);
        }
        zzt(runnable, executor);
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        zzb zzbVar;
        Object obj = this.value;
        if (!(obj instanceof zzf) && !(obj == null)) {
            return false;
        }
        if (zza) {
            zzbVar = new zzb(z, new CancellationException("Future.cancel() was called."));
        } else {
            zzbVar = z ? zzb.zza : zzb.zzb;
            zzbVar.getClass();
        }
        zzrg<V> zzrgVar = this;
        boolean z2 = false;
        while (true) {
            if (zzc.zzf(zzrgVar, obj, zzbVar)) {
                zzs(zzrgVar, z);
                if (obj instanceof zzf) {
                    ListenableFuture<? extends V> listenableFuture = ((zzf) obj).zzb;
                    if (listenableFuture instanceof zzh) {
                        zzrgVar = (zzrg) listenableFuture;
                        obj = zzrgVar.value;
                        if ((obj == null) | (obj instanceof zzf)) {
                            z2 = true;
                        }
                    } else {
                        listenableFuture.cancel(z);
                    }
                }
                return true;
            }
            obj = zzrgVar.value;
            if (!(obj instanceof zzf)) {
                return z2;
            }
        }
    }

    @Override // java.util.concurrent.Future
    public final Object get() throws ExecutionException, InterruptedException {
        Object obj;
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object obj2 = this.value;
        if ((obj2 != null) && (!(obj2 instanceof zzf))) {
            return zzv(obj2);
        }
        zzk zzkVar = this.waiters;
        if (zzkVar != zzk.zza) {
            zzk zzkVar2 = new zzk();
            do {
                zza zzaVar = zzc;
                zzaVar.zzc(zzkVar2, zzkVar);
                if (zzaVar.zzg(this, zzkVar, zzkVar2)) {
                    do {
                        LockSupport.park(this);
                        if (Thread.interrupted()) {
                            zzu(zzkVar2);
                            throw new InterruptedException();
                        }
                        obj = this.value;
                    } while (!((obj != null) & (!(obj instanceof zzf))));
                    return zzv(obj);
                }
                zzkVar = this.waiters;
            } while (zzkVar != zzk.zza);
        }
        Object obj3 = this.value;
        obj3.getClass();
        return zzv(obj3);
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.value instanceof zzb;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        Object obj = this.value;
        return (obj != null) & (!(obj instanceof zzf));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        if (getClass().getName().startsWith("com.google.common.util.concurrent.")) {
            sb.append(getClass().getSimpleName());
        } else {
            sb.append(getClass().getName());
        }
        sb.append('@');
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("[status=");
        if (this.value instanceof zzb) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            zzp(sb);
        } else {
            zzq(sb);
        }
        sb.append("]");
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @CheckForNull
    protected String zze() {
        if (!(this instanceof ScheduledFuture)) {
            return null;
        }
        return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
    }

    @Override // com.google.android.gms.internal.cast.zzsf
    @CheckForNull
    protected final Throwable zzf() {
        if (!(this instanceof zzh)) {
            return null;
        }
        Object obj = this.value;
        if (obj instanceof zzc) {
            return ((zzc) obj).zzb;
        }
        return null;
    }

    protected void zzj() {
    }

    protected final boolean zzk(Object obj) {
        if (obj == null) {
            obj = zzd;
        }
        if (!zzc.zzf(this, null, obj)) {
            return false;
        }
        zzs(this, false);
        return true;
    }

    protected final boolean zzl(Throwable th) {
        if (!zzc.zzf(this, null, new zzc(th))) {
            return false;
        }
        zzs(this, false);
        return true;
    }

    protected final boolean zzm() {
        Object obj = this.value;
        return (obj instanceof zzb) && ((zzb) obj).zzc;
    }

    @Override // java.util.concurrent.Future
    public final Object get(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        long nanos = timeUnit.toNanos(j);
        if (!Thread.interrupted()) {
            Object obj = this.value;
            boolean z = true;
            if ((obj != null) & (!(obj instanceof zzf))) {
                return zzv(obj);
            }
            long jNanoTime = nanos > 0 ? System.nanoTime() + nanos : 0L;
            if (nanos >= 1000) {
                zzk zzkVar = this.waiters;
                if (zzkVar != zzk.zza) {
                    zzk zzkVar2 = new zzk();
                    while (true) {
                        zza zzaVar = zzc;
                        zzaVar.zzc(zzkVar2, zzkVar);
                        if (zzaVar.zzg(this, zzkVar, zzkVar2)) {
                            do {
                                LockSupport.parkNanos(this, Math.min(nanos, 2147483647999999999L));
                                if (Thread.interrupted()) {
                                    zzu(zzkVar2);
                                    throw new InterruptedException();
                                }
                                Object obj2 = this.value;
                                if (!((obj2 != null) & (!(obj2 instanceof zzf)))) {
                                    nanos = jNanoTime - System.nanoTime();
                                } else {
                                    return zzv(obj2);
                                }
                            } while (nanos >= 1000);
                            zzu(zzkVar2);
                            break;
                        }
                        zzkVar = this.waiters;
                        if (zzkVar == zzk.zza) {
                        }
                    }
                }
                Object obj3 = this.value;
                obj3.getClass();
                return zzv(obj3);
            }
            while (nanos > 0) {
                Object obj4 = this.value;
                if (!((obj4 != null) & (!(obj4 instanceof zzf)))) {
                    if (!Thread.interrupted()) {
                        nanos = jNanoTime - System.nanoTime();
                    } else {
                        throw new InterruptedException();
                    }
                } else {
                    return zzv(obj4);
                }
            }
            String string = toString();
            String lowerCase = timeUnit.toString().toLowerCase(Locale.ROOT);
            String strConcat = "Waited " + j + " " + timeUnit.toString().toLowerCase(Locale.ROOT);
            if (nanos + 1000 < 0) {
                String strConcat2 = strConcat.concat(" (plus ");
                long j2 = -nanos;
                long jConvert = timeUnit.convert(j2, TimeUnit.NANOSECONDS);
                long nanos2 = j2 - timeUnit.toNanos(jConvert);
                if (jConvert != 0 && nanos2 <= 1000) {
                    z = false;
                }
                if (jConvert > 0) {
                    String strConcat3 = strConcat2 + jConvert + " " + lowerCase;
                    if (z) {
                        strConcat3 = strConcat3.concat(",");
                    }
                    strConcat2 = strConcat3.concat(" ");
                }
                if (z) {
                    strConcat2 = strConcat2 + nanos2 + " nanoseconds ";
                }
                strConcat = strConcat2.concat("delay)");
            }
            if (isDone()) {
                throw new TimeoutException(strConcat.concat(" but future completed as timeout expired"));
            }
            throw new TimeoutException(strConcat + " for " + string);
        }
        throw new InterruptedException();
    }
}
