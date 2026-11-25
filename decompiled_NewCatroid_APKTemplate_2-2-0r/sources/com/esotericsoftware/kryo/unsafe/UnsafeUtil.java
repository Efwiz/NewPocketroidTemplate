package com.esotericsoftware.kryo.unsafe;

import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import org.eclipse.jgit.lib.Constants;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class UnsafeUtil {
    public static final long booleanArrayBaseOffset;
    public static final long byteArrayBaseOffset;
    public static final long charArrayBaseOffset;
    private static Method cleanMethod;
    private static Method cleanerMethod;
    private static Constructor<? extends ByteBuffer> directByteBufferConstructor;
    public static final long doubleArrayBaseOffset;
    public static final long floatArrayBaseOffset;
    public static final long intArrayBaseOffset;
    public static final long longArrayBaseOffset;
    public static final long shortArrayBaseOffset;
    public static final Unsafe unsafe;

    /* JADX WARN: Code duplicated, block: B:52:0x00c9  */
    static {
        long jArrayBaseOffset;
        long j;
        Unsafe unsafe2;
        long jArrayBaseOffset2;
        long jArrayBaseOffset3;
        long jArrayBaseOffset4;
        long jArrayBaseOffset5;
        long j2;
        long j3;
        long j4;
        long j5;
        long jArrayBaseOffset6;
        long j6;
        try {
            if (!Util.isAndroid) {
                Field declaredField = Unsafe.class.getDeclaredField("theUnsafe");
                declaredField.setAccessible(true);
                unsafe2 = (Unsafe) declaredField.get(null);
                try {
                    jArrayBaseOffset2 = unsafe2.arrayBaseOffset(byte[].class);
                    try {
                        jArrayBaseOffset3 = unsafe2.arrayBaseOffset(char[].class);
                        try {
                            jArrayBaseOffset4 = unsafe2.arrayBaseOffset(short[].class);
                            try {
                                jArrayBaseOffset5 = unsafe2.arrayBaseOffset(int[].class);
                                try {
                                    long jArrayBaseOffset7 = unsafe2.arrayBaseOffset(float[].class);
                                    try {
                                        j6 = jArrayBaseOffset7;
                                        long jArrayBaseOffset8 = unsafe2.arrayBaseOffset(long[].class);
                                        try {
                                            j2 = jArrayBaseOffset8;
                                            jArrayBaseOffset = unsafe2.arrayBaseOffset(double[].class);
                                            try {
                                                jArrayBaseOffset6 = unsafe2.arrayBaseOffset(boolean[].class);
                                                unsafe2 = unsafe2;
                                                j4 = jArrayBaseOffset2;
                                            } catch (Exception e) {
                                                e = e;
                                                j = j6;
                                                if (Log.DEBUG) {
                                                    Log.debug("kryo", "Unsafe is not available.", e);
                                                }
                                                j3 = 0;
                                                long j7 = j;
                                                j4 = jArrayBaseOffset2;
                                                j5 = j7;
                                            }
                                        } catch (Exception e2) {
                                            e = e2;
                                            j2 = jArrayBaseOffset8;
                                            j = j6;
                                            jArrayBaseOffset = 0;
                                        }
                                    } catch (Exception e3) {
                                        e = e3;
                                        j = jArrayBaseOffset7;
                                        jArrayBaseOffset = 0;
                                        j2 = 0;
                                        if (Log.DEBUG) {
                                            Log.debug("kryo", "Unsafe is not available.", e);
                                        }
                                        j3 = 0;
                                        long j8 = j;
                                        j4 = jArrayBaseOffset2;
                                        j5 = j8;
                                        byteArrayBaseOffset = j4;
                                        charArrayBaseOffset = jArrayBaseOffset3;
                                        shortArrayBaseOffset = jArrayBaseOffset4;
                                        intArrayBaseOffset = jArrayBaseOffset5;
                                        floatArrayBaseOffset = j5;
                                        longArrayBaseOffset = j2;
                                        doubleArrayBaseOffset = jArrayBaseOffset;
                                        booleanArrayBaseOffset = j3;
                                        unsafe = unsafe2;
                                        Constructor declaredConstructor = ByteBuffer.allocateDirect(1).getClass().getDeclaredConstructor(Long.TYPE, Integer.TYPE);
                                        directByteBufferConstructor = declaredConstructor;
                                        declaredConstructor.setAccessible(true);
                                        Method method = DirectBuffer.class.getMethod("cleaner", new Class[0]);
                                        cleanerMethod = method;
                                        method.setAccessible(true);
                                        cleanMethod = cleanerMethod.getReturnType().getMethod(Constants.ATTR_FILTER_TYPE_CLEAN, new Class[0]);
                                    }
                                } catch (Exception e4) {
                                    e = e4;
                                    jArrayBaseOffset = 0;
                                    j = 0;
                                }
                            } catch (Exception e5) {
                                e = e5;
                                jArrayBaseOffset = 0;
                                j = 0;
                                jArrayBaseOffset5 = 0;
                                j2 = 0;
                                if (Log.DEBUG) {
                                    Log.debug("kryo", "Unsafe is not available.", e);
                                }
                                j3 = 0;
                                long j9 = j;
                                j4 = jArrayBaseOffset2;
                                j5 = j9;
                                byteArrayBaseOffset = j4;
                                charArrayBaseOffset = jArrayBaseOffset3;
                                shortArrayBaseOffset = jArrayBaseOffset4;
                                intArrayBaseOffset = jArrayBaseOffset5;
                                floatArrayBaseOffset = j5;
                                longArrayBaseOffset = j2;
                                doubleArrayBaseOffset = jArrayBaseOffset;
                                booleanArrayBaseOffset = j3;
                                unsafe = unsafe2;
                                Constructor declaredConstructor2 = ByteBuffer.allocateDirect(1).getClass().getDeclaredConstructor(Long.TYPE, Integer.TYPE);
                                directByteBufferConstructor = declaredConstructor2;
                                declaredConstructor2.setAccessible(true);
                                Method method2 = DirectBuffer.class.getMethod("cleaner", new Class[0]);
                                cleanerMethod = method2;
                                method2.setAccessible(true);
                                cleanMethod = cleanerMethod.getReturnType().getMethod(Constants.ATTR_FILTER_TYPE_CLEAN, new Class[0]);
                            }
                        } catch (Exception e6) {
                            e = e6;
                            jArrayBaseOffset = 0;
                            j = 0;
                            jArrayBaseOffset4 = 0;
                            jArrayBaseOffset5 = 0;
                            j2 = 0;
                            if (Log.DEBUG) {
                                Log.debug("kryo", "Unsafe is not available.", e);
                            }
                            j3 = 0;
                            long j10 = j;
                            j4 = jArrayBaseOffset2;
                            j5 = j10;
                            byteArrayBaseOffset = j4;
                            charArrayBaseOffset = jArrayBaseOffset3;
                            shortArrayBaseOffset = jArrayBaseOffset4;
                            intArrayBaseOffset = jArrayBaseOffset5;
                            floatArrayBaseOffset = j5;
                            longArrayBaseOffset = j2;
                            doubleArrayBaseOffset = jArrayBaseOffset;
                            booleanArrayBaseOffset = j3;
                            unsafe = unsafe2;
                            Constructor declaredConstructor3 = ByteBuffer.allocateDirect(1).getClass().getDeclaredConstructor(Long.TYPE, Integer.TYPE);
                            directByteBufferConstructor = declaredConstructor3;
                            declaredConstructor3.setAccessible(true);
                            Method method3 = DirectBuffer.class.getMethod("cleaner", new Class[0]);
                            cleanerMethod = method3;
                            method3.setAccessible(true);
                            cleanMethod = cleanerMethod.getReturnType().getMethod(Constants.ATTR_FILTER_TYPE_CLEAN, new Class[0]);
                        }
                    } catch (Exception e7) {
                        e = e7;
                        jArrayBaseOffset = 0;
                        j = 0;
                        jArrayBaseOffset3 = 0;
                        jArrayBaseOffset4 = 0;
                        jArrayBaseOffset5 = 0;
                        j2 = 0;
                        if (Log.DEBUG) {
                            Log.debug("kryo", "Unsafe is not available.", e);
                        }
                        j3 = 0;
                        long j11 = j;
                        j4 = jArrayBaseOffset2;
                        j5 = j11;
                        byteArrayBaseOffset = j4;
                        charArrayBaseOffset = jArrayBaseOffset3;
                        shortArrayBaseOffset = jArrayBaseOffset4;
                        intArrayBaseOffset = jArrayBaseOffset5;
                        floatArrayBaseOffset = j5;
                        longArrayBaseOffset = j2;
                        doubleArrayBaseOffset = jArrayBaseOffset;
                        booleanArrayBaseOffset = j3;
                        unsafe = unsafe2;
                        Constructor declaredConstructor4 = ByteBuffer.allocateDirect(1).getClass().getDeclaredConstructor(Long.TYPE, Integer.TYPE);
                        directByteBufferConstructor = declaredConstructor4;
                        declaredConstructor4.setAccessible(true);
                        Method method4 = DirectBuffer.class.getMethod("cleaner", new Class[0]);
                        cleanerMethod = method4;
                        method4.setAccessible(true);
                        cleanMethod = cleanerMethod.getReturnType().getMethod(Constants.ATTR_FILTER_TYPE_CLEAN, new Class[0]);
                    }
                } catch (Exception e8) {
                    e = e8;
                    jArrayBaseOffset = 0;
                    j = 0;
                    jArrayBaseOffset2 = 0;
                    jArrayBaseOffset3 = 0;
                    jArrayBaseOffset4 = 0;
                    jArrayBaseOffset5 = 0;
                    j2 = 0;
                    if (Log.DEBUG) {
                        Log.debug("kryo", "Unsafe is not available.", e);
                    }
                    j3 = 0;
                    long j12 = j;
                    j4 = jArrayBaseOffset2;
                    j5 = j12;
                    byteArrayBaseOffset = j4;
                    charArrayBaseOffset = jArrayBaseOffset3;
                    shortArrayBaseOffset = jArrayBaseOffset4;
                    intArrayBaseOffset = jArrayBaseOffset5;
                    floatArrayBaseOffset = j5;
                    longArrayBaseOffset = j2;
                    doubleArrayBaseOffset = jArrayBaseOffset;
                    booleanArrayBaseOffset = j3;
                    unsafe = unsafe2;
                    Constructor declaredConstructor5 = ByteBuffer.allocateDirect(1).getClass().getDeclaredConstructor(Long.TYPE, Integer.TYPE);
                    directByteBufferConstructor = declaredConstructor5;
                    declaredConstructor5.setAccessible(true);
                    Method method5 = DirectBuffer.class.getMethod("cleaner", new Class[0]);
                    cleanerMethod = method5;
                    method5.setAccessible(true);
                    cleanMethod = cleanerMethod.getReturnType().getMethod(Constants.ATTR_FILTER_TYPE_CLEAN, new Class[0]);
                }
            } else {
                if (Log.DEBUG) {
                    Log.debug("kryo", "Unsafe is not available on Android.");
                }
                jArrayBaseOffset = 0;
                j4 = 0;
                unsafe2 = null;
                jArrayBaseOffset3 = 0;
                jArrayBaseOffset4 = 0;
                jArrayBaseOffset5 = 0;
                jArrayBaseOffset6 = 0;
                j6 = 0;
                j2 = 0;
            }
            j5 = j6;
            j3 = jArrayBaseOffset6;
        } catch (Exception e9) {
            e = e9;
            jArrayBaseOffset = 0;
            j = 0;
            unsafe2 = null;
        }
        byteArrayBaseOffset = j4;
        charArrayBaseOffset = jArrayBaseOffset3;
        shortArrayBaseOffset = jArrayBaseOffset4;
        intArrayBaseOffset = jArrayBaseOffset5;
        floatArrayBaseOffset = j5;
        longArrayBaseOffset = j2;
        doubleArrayBaseOffset = jArrayBaseOffset;
        booleanArrayBaseOffset = j3;
        unsafe = unsafe2;
        try {
            Constructor declaredConstructor6 = ByteBuffer.allocateDirect(1).getClass().getDeclaredConstructor(Long.TYPE, Integer.TYPE);
            directByteBufferConstructor = declaredConstructor6;
            declaredConstructor6.setAccessible(true);
        } catch (Exception e10) {
            if (Log.DEBUG) {
                Log.debug("kryo", "No direct ByteBuffer constructor is available.", e10);
            }
            directByteBufferConstructor = null;
        }
        try {
            Method method6 = DirectBuffer.class.getMethod("cleaner", new Class[0]);
            cleanerMethod = method6;
            method6.setAccessible(true);
            cleanMethod = cleanerMethod.getReturnType().getMethod(Constants.ATTR_FILTER_TYPE_CLEAN, new Class[0]);
        } catch (Exception e11) {
            if (Log.DEBUG) {
                Log.debug("kryo", "No direct ByteBuffer clean method is available.", e11);
            }
            cleanerMethod = null;
        }
    }

    public static ByteBuffer newDirectBuffer(long j, int i) {
        Constructor<? extends ByteBuffer> constructor = directByteBufferConstructor;
        if (constructor == null) {
            throw new UnsupportedOperationException("No direct ByteBuffer constructor is available.");
        }
        try {
            return constructor.newInstance(Long.valueOf(j), Integer.valueOf(i));
        } catch (Exception e) {
            throw new KryoException("Error creating a ByteBuffer at address: " + j, e);
        }
    }

    public static boolean isNewDirectBufferAvailable() {
        return directByteBufferConstructor != null;
    }

    public static void dispose(ByteBuffer byteBuffer) {
        Method method;
        if ((byteBuffer instanceof DirectBuffer) && (method = cleanerMethod) != null) {
            try {
                cleanMethod.invoke(method.invoke(byteBuffer, new Object[0]), new Object[0]);
            } catch (Throwable unused) {
            }
        }
    }
}
