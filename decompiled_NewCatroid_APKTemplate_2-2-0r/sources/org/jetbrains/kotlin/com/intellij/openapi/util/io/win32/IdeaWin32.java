package org.jetbrains.kotlin.com.intellij.openapi.util.io.win32;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.zip.CRC32;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.com.intellij.util.loader.NativeLibraryLoader;
import org.jetbrains.kotlin.com.intellij.util.system.CpuArch;

/* JADX INFO: loaded from: classes3.dex */
public final class IdeaWin32 {
    private static final Logger LOG;
    private static final boolean TRACE_ENABLED;
    private static final IdeaWin32 ourInstance;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 3 : 2];
        if (i == 1 || i == 2 || i == 3) {
            objArr[0] = "path";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/win32/IdeaWin32";
        }
        if (i == 1 || i == 2 || i == 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/win32/IdeaWin32";
        } else {
            objArr[1] = "getInstance";
        }
        if (i == 1) {
            objArr[2] = "getInfo";
        } else if (i == 2) {
            objArr[2] = "resolveSymLink";
        } else if (i == 3) {
            objArr[2] = "listChildren";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    private native FileInfo getInfo0(String str);

    private static native void initIDs();

    static {
        Logger logger = Logger.getInstance((Class<?>) IdeaWin32.class);
        LOG = logger;
        TRACE_ENABLED = logger.isTraceEnabled();
        IdeaWin32 ideaWin32 = null;
        if (SystemInfoRt.isWindows && Boolean.parseBoolean(System.getProperty("idea.use.native.fs.for.win", "true"))) {
            try {
                if (!loadBundledLibrary()) {
                    NativeLibraryLoader.loadPlatformLibrary("IdeaWin32");
                }
                IdeaWin32 ideaWin33 = new IdeaWin32();
                try {
                    logger.info("Native filesystem for Windows is operational");
                    ideaWin32 = ideaWin33;
                } catch (Throwable th) {
                    th = th;
                    ideaWin32 = ideaWin33;
                    LOG.warn("Failed to initialize native filesystem for Windows", th);
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        ourInstance = ideaWin32;
    }

    private static boolean loadBundledLibrary() throws IOException {
        String str = CpuArch.isIntel64() ? "IdeaWin64" : "IdeaWin32";
        URL resource = IdeaWin32.class.getResource(str.concat(".dll"));
        if (resource == null) {
            return false;
        }
        byte[] bArrLoadBytes = FileUtilRt.loadBytes(resource.openStream());
        CRC32 crc32 = new CRC32();
        crc32.update(bArrLoadBytes, 0, bArrLoadBytes.length);
        Path path = Paths.get(FileUtilRt.getTempDirectory(), str + '.' + Math.abs(crc32.getValue()) + ".dll");
        if (!Files.exists(path, new LinkOption[0])) {
            Files.createDirectories(path.getParent(), new FileAttribute[0]);
            Files.write(path, bArrLoadBytes, new OpenOption[0]);
        }
        System.load(path.toString());
        return true;
    }

    public static boolean isAvailable() {
        return ourInstance != null;
    }

    public static IdeaWin32 getInstance() {
        if (!isAvailable()) {
            throw new IllegalStateException("Native filesystem for Windows is not loaded");
        }
        IdeaWin32 ideaWin32 = ourInstance;
        if (ideaWin32 == null) {
            $$$reportNull$$$0(0);
        }
        return ideaWin32;
    }

    private IdeaWin32() {
        initIDs();
    }

    public FileInfo getInfo(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        String strReplace = str.replace('/', '\\');
        if (TRACE_ENABLED) {
            Logger logger = LOG;
            logger.trace("getInfo(" + strReplace + Constant.AFTER_QUTO);
            long jNanoTime = System.nanoTime();
            FileInfo info0 = getInfo0(strReplace);
            logger.trace("  " + ((System.nanoTime() - jNanoTime) / 1000) + " mks");
            return info0;
        }
        return getInfo0(strReplace);
    }
}
