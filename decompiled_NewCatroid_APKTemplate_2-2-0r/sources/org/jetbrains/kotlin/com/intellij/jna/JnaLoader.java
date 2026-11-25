package org.jetbrains.kotlin.com.intellij.jna;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.sun.jna.Native;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.com.intellij.util.system.CpuArch;

/* JADX INFO: loaded from: classes3.dex */
public final class JnaLoader {
    private static Boolean ourJnaLoaded;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "logger", "org/jetbrains/kotlin/com/intellij/jna/JnaLoader", "load"));
    }

    /* JADX WARN: Code duplicated, block: B:9:0x0011 A[Catch: all -> 0x006e, TRY_LEAVE, TryCatch #1 {, blocks: (B:6:0x000a, B:7:0x000d, B:9:0x0011, B:13:0x0048, B:10:0x0015), top: B:21:0x000a, inners: #0 }] */
    public static synchronized void load(Logger logger) {
        if (logger == null) {
            $$$reportNull$$$0(0);
            if (ourJnaLoaded == null) {
                ourJnaLoaded = Boolean.FALSE;
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    logger.info("JNA library (" + (Native.POINTER_SIZE << 3) + "-bit) loaded in " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
                    ourJnaLoaded = Boolean.TRUE;
                } catch (Throwable th) {
                    logger.warn("Unable to load JNA library (OS: " + SystemInfoRt.OS_NAME + " " + SystemInfoRt.OS_VERSION + Constant.AFTER_QUTO, th);
                }
            }
        } else if (ourJnaLoaded == null) {
            ourJnaLoaded = Boolean.FALSE;
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            logger.info("JNA library (" + (Native.POINTER_SIZE << 3) + "-bit) loaded in " + (System.currentTimeMillis() - jCurrentTimeMillis2) + " ms");
            ourJnaLoaded = Boolean.TRUE;
        }
        throw th;
    }

    public static synchronized boolean isLoaded() {
        if (ourJnaLoaded == null) {
            load(Logger.getInstance((Class<?>) JnaLoader.class));
        }
        return ourJnaLoaded.booleanValue();
    }

    public static boolean isSupportsDirectMapping() {
        return (SystemInfoRt.isMac && CpuArch.isArm64()) ? false : true;
    }
}
