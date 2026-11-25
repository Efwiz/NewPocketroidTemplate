package org.jetbrains.kotlin.com.intellij.util.loader;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.openapi.application.PathManager;
import org.jetbrains.kotlin.com.intellij.util.system.CpuArch;

/* JADX INFO: loaded from: classes3.dex */
public final class NativeLibraryLoader {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "libName", "org/jetbrains/kotlin/com/intellij/util/loader/NativeLibraryLoader", "loadPlatformLibrary"));
    }

    public static void loadPlatformLibrary(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (CpuArch.isIntel64()) {
            str = str.replace("32", "") + "64";
        }
        String strReplace = System.mapLibraryName(str).replace(".jnilib", ".dylib");
        Path pathFindBinFile = PathManager.findBinFile(strReplace);
        if (pathFindBinFile == null) {
            File file = new File(PathManager.getBinPath());
            throw new UnsatisfiedLinkError(FormatHelper.QUOTE + strReplace + "' not found in '" + file + "' among " + Arrays.toString(file.list()));
        }
        System.load(pathFindBinFile.toString());
    }
}
