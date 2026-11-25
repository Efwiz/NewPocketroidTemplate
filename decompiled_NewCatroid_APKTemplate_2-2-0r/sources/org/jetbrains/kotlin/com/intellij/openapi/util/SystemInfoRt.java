package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.Locale;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes3.dex */
public final class SystemInfoRt {
    private static final String ARCH_DATA_MODEL;
    public static final String OS_NAME;
    public static final String OS_VERSION;
    private static final String _OS_NAME;

    @Deprecated
    public static final boolean is64Bit;
    public static final boolean isFileSystemCaseSensitive;
    public static final boolean isFreeBSD;
    public static final boolean isLinux;
    public static final boolean isMac;
    public static final boolean isSolaris;
    public static final boolean isUnix;
    public static final boolean isWindows;
    public static final boolean isXWindow;

    static {
        String property = System.getProperty(SystemProperties.OS_NAME);
        OS_NAME = property;
        OS_VERSION = System.getProperty(SystemProperties.OS_VERSION).toLowerCase(Locale.ENGLISH);
        String lowerCase = property.toLowerCase(Locale.ENGLISH);
        _OS_NAME = lowerCase;
        boolean zStartsWith = lowerCase.startsWith("windows");
        isWindows = zStartsWith;
        boolean zStartsWith2 = lowerCase.startsWith("mac");
        isMac = zStartsWith2;
        isLinux = lowerCase.startsWith("linux");
        isFreeBSD = lowerCase.startsWith("freebsd");
        isSolaris = lowerCase.startsWith("sunos");
        isUnix = !zStartsWith;
        boolean z = false;
        isXWindow = (zStartsWith || zStartsWith2) ? false : true;
        isFileSystemCaseSensitive = !(zStartsWith || zStartsWith2) || "true".equalsIgnoreCase(System.getProperty("idea.case.sensitive.fs"));
        String property2 = System.getProperty("sun.arch.data.model");
        ARCH_DATA_MODEL = property2;
        if (property2 != null && !property2.equals("32")) {
            z = true;
        }
        is64Bit = z;
    }
}
