package org.jetbrains.kotlin.net.jpountz.xxhash;

import java.util.Random;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.net.jpountz.util.Native;
import org.jetbrains.kotlin.net.jpountz.util.Utils;

/* JADX INFO: loaded from: classes2.dex */
public final class XXHashFactory {
    private static XXHashFactory JAVA_SAFE_INSTANCE;
    private static XXHashFactory JAVA_UNSAFE_INSTANCE;
    private static XXHashFactory NATIVE_INSTANCE;
    private final XXHash32 hash32;
    private final XXHash64 hash64;
    private final String impl;
    private final StreamingXXHash32.Factory streamingHash32Factory;
    private final StreamingXXHash64.Factory streamingHash64Factory;

    private static XXHashFactory instance(String str) {
        try {
            return new XXHashFactory(str);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static synchronized XXHashFactory nativeInstance() {
        if (NATIVE_INSTANCE == null) {
            NATIVE_INSTANCE = instance("JNI");
        }
        return NATIVE_INSTANCE;
    }

    public static synchronized XXHashFactory safeInstance() {
        if (JAVA_SAFE_INSTANCE == null) {
            JAVA_SAFE_INSTANCE = instance("JavaSafe");
        }
        return JAVA_SAFE_INSTANCE;
    }

    public static synchronized XXHashFactory unsafeInstance() {
        if (JAVA_UNSAFE_INSTANCE == null) {
            JAVA_UNSAFE_INSTANCE = instance("JavaUnsafe");
        }
        return JAVA_UNSAFE_INSTANCE;
    }

    public static XXHashFactory fastestJavaInstance() {
        if (Utils.isUnalignedAccessAllowed()) {
            try {
                return unsafeInstance();
            } catch (Throwable unused) {
                return safeInstance();
            }
        }
        return safeInstance();
    }

    public static XXHashFactory fastestInstance() {
        if (Native.isLoaded() || Native.class.getClassLoader() == ClassLoader.getSystemClassLoader()) {
            try {
                return nativeInstance();
            } catch (Throwable unused) {
                return fastestJavaInstance();
            }
        }
        return fastestJavaInstance();
    }

    private static <T> T classInstance(String str) throws IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException {
        ClassLoader classLoader = XXHashFactory.class.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        return (T) classLoader.loadClass(str).getField(JvmAbi.INSTANCE_FIELD).get(null);
    }

    private XXHashFactory(String str) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, SecurityException, IllegalArgumentException {
        this.impl = str;
        XXHash32 xXHash32 = (XXHash32) classInstance("org.jetbrains.kotlin.net.jpountz.xxhash.XXHash32" + str);
        this.hash32 = xXHash32;
        this.streamingHash32Factory = (StreamingXXHash32.Factory) classInstance("org.jetbrains.kotlin.net.jpountz.xxhash.StreamingXXHash32" + str + "$Factory");
        XXHash64 xXHash64 = (XXHash64) classInstance("org.jetbrains.kotlin.net.jpountz.xxhash.XXHash64" + str);
        this.hash64 = xXHash64;
        this.streamingHash64Factory = (StreamingXXHash64.Factory) classInstance("org.jetbrains.kotlin.net.jpountz.xxhash.StreamingXXHash64" + str + "$Factory");
        byte[] bArr = new byte[100];
        Random random = new Random();
        random.nextBytes(bArr);
        int iNextInt = random.nextInt();
        int iHash = xXHash32.hash(bArr, 0, 100, iNextInt);
        StreamingXXHash32 streamingXXHash32NewStreamingHash32 = newStreamingHash32(iNextInt);
        streamingXXHash32NewStreamingHash32.update(bArr, 0, 100);
        int value = streamingXXHash32NewStreamingHash32.getValue();
        long j = iNextInt;
        long jHash = xXHash64.hash(bArr, 0, 100, j);
        StreamingXXHash64 streamingXXHash64NewStreamingHash64 = newStreamingHash64(j);
        streamingXXHash64NewStreamingHash64.update(bArr, 0, 100);
        long value2 = streamingXXHash64NewStreamingHash64.getValue();
        if (iHash != value) {
            throw new AssertionError();
        }
        if (jHash != value2) {
            throw new AssertionError();
        }
    }

    public XXHash32 hash32() {
        return this.hash32;
    }

    public StreamingXXHash32 newStreamingHash32(int i) {
        return this.streamingHash32Factory.newStreamingHash(i);
    }

    public StreamingXXHash64 newStreamingHash64(long j) {
        return this.streamingHash64Factory.newStreamingHash(j);
    }

    public String toString() {
        return getClass().getSimpleName() + ":" + this.impl;
    }
}
