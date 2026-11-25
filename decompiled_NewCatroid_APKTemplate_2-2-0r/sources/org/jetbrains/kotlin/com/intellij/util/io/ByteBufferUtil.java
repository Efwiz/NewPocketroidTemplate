package org.jetbrains.kotlin.com.intellij.util.io;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.ByteBuffer;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.lang.JavaVersion;

/* JADX INFO: loaded from: classes3.dex */
public final class ByteBufferUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "buffer", "org/jetbrains/kotlin/com/intellij/util/io/ByteBufferUtil", "cleanBuffer"));
    }

    public static boolean cleanBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            $$$reportNull$$$0(0);
        }
        if (!byteBuffer.isDirect()) {
            return true;
        }
        if (JavaVersion.current().feature >= 9) {
            Object unsafe = ReflectionUtil.getUnsafe();
            try {
                (void) MethodHandles.lookup().findVirtual(unsafe.getClass(), "invokeCleaner", MethodType.methodType((Class<?>) Void.TYPE, (Class<?>) ByteBuffer.class)).invoke(unsafe, byteBuffer);
                return true;
            } catch (Throwable th) {
                Logger.getInstance((Class<?>) ByteBufferUtil.class).warn(th);
                return false;
            }
        }
        try {
            Class<?> cls = Class.forName("sun.nio.ch.DirectBuffer");
            Class<?> cls2 = Class.forName("sun.misc.Cleaner");
            Object objInvoke = cls.getDeclaredMethod("cleaner", new Class[0]).invoke(byteBuffer, new Object[0]);
            if (objInvoke != null) {
                cls2.getDeclaredMethod(Constants.ATTR_FILTER_TYPE_CLEAN, new Class[0]).invoke(objInvoke, new Object[0]);
            }
            return true;
        } catch (Exception e) {
            Logger.getInstance((Class<?>) ByteBufferUtil.class).warn(e);
            return false;
        }
    }
}
