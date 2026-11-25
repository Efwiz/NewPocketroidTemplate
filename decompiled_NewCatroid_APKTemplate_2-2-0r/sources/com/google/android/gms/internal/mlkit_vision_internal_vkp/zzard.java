package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzard {
    static final long zza;
    static final boolean zzb;
    private static final Unsafe zzc;
    private static final Class<?> zzd;
    private static final boolean zze;
    private static final boolean zzf;
    private static final zzarc zzg;
    private static final boolean zzh;
    private static final boolean zzi;

    /* JADX WARN: Code duplicated, block: B:11:0x003a  */
    static {
        Unsafe unsafe;
        boolean z;
        Unsafe unsafe2;
        boolean z2;
        zzarc zzarcVar;
        Unsafe unsafeZzg = zzg();
        zzc = unsafeZzg;
        zzd = zzalr.zza();
        boolean zZzv = zzv(Long.TYPE);
        zze = zZzv;
        boolean zZzv2 = zzv(Integer.TYPE);
        zzf = zZzv2;
        zzarc zzaraVar = null;
        if (unsafeZzg != null) {
            if (zZzv) {
                zzaraVar = new zzarb(unsafeZzg);
            } else if (zZzv2) {
                zzaraVar = new zzara(unsafeZzg);
            }
        }
        zzg = zzaraVar;
        if (zzaraVar == null || (unsafe = zzaraVar.zza) == null) {
            z = false;
        } else {
            try {
                Class<?> cls = unsafe.getClass();
                cls.getMethod("objectFieldOffset", Field.class);
                cls.getMethod("getLong", Object.class, Long.TYPE);
                if (zzB() == null) {
                    z = false;
                } else {
                    z = true;
                }
            } catch (Throwable th) {
                zzh(th);
            }
        }
        zzh = z;
        zzarc zzarcVar2 = zzg;
        if (zzarcVar2 == null || (unsafe2 = zzarcVar2.zza) == null) {
            z2 = false;
        } else {
            try {
                Class<?> cls2 = unsafe2.getClass();
                cls2.getMethod("objectFieldOffset", Field.class);
                cls2.getMethod("arrayBaseOffset", Class.class);
                cls2.getMethod("arrayIndexScale", Class.class);
                cls2.getMethod("getInt", Object.class, Long.TYPE);
                cls2.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
                cls2.getMethod("getLong", Object.class, Long.TYPE);
                cls2.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
                cls2.getMethod("getObject", Object.class, Long.TYPE);
                cls2.getMethod("putObject", Object.class, Long.TYPE, Object.class);
                z2 = true;
            } catch (Throwable th2) {
                zzh(th2);
                z2 = false;
            }
        }
        zzi = z2;
        zza = zzz(byte[].class);
        zzz(boolean[].class);
        zzA(boolean[].class);
        zzz(int[].class);
        zzA(int[].class);
        zzz(long[].class);
        zzA(long[].class);
        zzz(float[].class);
        zzA(float[].class);
        zzz(double[].class);
        zzA(double[].class);
        zzz(Object[].class);
        zzA(Object[].class);
        Field fieldZzB = zzB();
        if (fieldZzB != null && (zzarcVar = zzg) != null) {
            zzarcVar.zzl(fieldZzB);
        }
        zzb = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    private zzard() {
    }

    private static int zzA(Class<?> cls) {
        if (zzi) {
            return zzg.zzi(cls);
        }
        return -1;
    }

    private static Field zzB() {
        int i = zzalr.zza;
        Field fieldZzC = zzC(Buffer.class, "effectiveDirectAddress");
        if (fieldZzC != null) {
            return fieldZzC;
        }
        Field fieldZzC2 = zzC(Buffer.class, "address");
        if (fieldZzC2 == null || fieldZzC2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZzC2;
    }

    private static Field zzC(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzD(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        zzarc zzarcVar = zzg;
        int iZzj = zzarcVar.zzj(obj, j2);
        int i = ((~((int) j)) & 3) << 3;
        zzarcVar.zzn(obj, j2, ((255 & b) << i) | (iZzj & (~(255 << i))));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzE(Object obj, long j, byte b) {
        long j2 = (-4) & j;
        zzarc zzarcVar = zzg;
        int i = (((int) j) & 3) << 3;
        zzarcVar.zzn(obj, j2, ((255 & b) << i) | (zzarcVar.zzj(obj, j2) & (~(255 << i))));
    }

    static double zza(Object obj, long j) {
        return zzg.zza(obj, j);
    }

    static float zzb(Object obj, long j) {
        return zzg.zzb(obj, j);
    }

    static int zzc(Object obj, long j) {
        return zzg.zzj(obj, j);
    }

    static long zzd(Object obj, long j) {
        return zzg.zzk(obj, j);
    }

    static <T> T zze(Class<T> cls) {
        try {
            return (T) zzc.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    static Object zzf(Object obj, long j) {
        return zzg.zzm(obj, j);
    }

    static Unsafe zzg() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzaqz());
        } catch (Throwable unused) {
            return null;
        }
    }

    static /* bridge */ /* synthetic */ void zzh(Throwable th) {
        Logger logger = Logger.getLogger(zzard.class.getName());
        Level level = Level.WARNING;
        String strValueOf = String.valueOf(th);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 71);
        sb.append("platform method missing - proto runtime falling back to safer methods: ");
        sb.append(strValueOf);
        logger.logp(level, "com.google.protobuf.UnsafeUtil", "logMissingMethod", sb.toString());
    }

    static void zzm(Object obj, long j, boolean z) {
        zzg.zzc(obj, j, z);
    }

    static void zzn(byte[] bArr, long j, byte b) {
        zzg.zzd(bArr, zza + j, b);
    }

    static void zzo(Object obj, long j, double d) {
        zzg.zze(obj, j, d);
    }

    static void zzp(Object obj, long j, float f) {
        zzg.zzf(obj, j, f);
    }

    static void zzq(Object obj, long j, int i) {
        zzg.zzn(obj, j, i);
    }

    static void zzr(Object obj, long j, long j2) {
        zzg.zzo(obj, j, j2);
    }

    static void zzs(Object obj, long j, Object obj2) {
        zzg.zzp(obj, j, obj2);
    }

    static /* bridge */ /* synthetic */ boolean zzt(Object obj, long j) {
        return ((byte) ((zzg.zzj(obj, (-4) & j) >>> ((int) (((~j) & 3) << 3))) & 255)) != 0;
    }

    static /* bridge */ /* synthetic */ boolean zzu(Object obj, long j) {
        return ((byte) ((zzg.zzj(obj, (-4) & j) >>> ((int) ((j & 3) << 3))) & 255)) != 0;
    }

    static boolean zzv(Class<?> cls) {
        int i = zzalr.zza;
        try {
            Class<?> cls2 = zzd;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    static boolean zzw(Object obj, long j) {
        return zzg.zzg(obj, j);
    }

    static boolean zzx() {
        return zzi;
    }

    static boolean zzy() {
        return zzh;
    }

    private static int zzz(Class<?> cls) {
        if (zzi) {
            return zzg.zzh(cls);
        }
        return -1;
    }
}
