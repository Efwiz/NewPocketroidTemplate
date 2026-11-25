package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.io.File;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.PathExecLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;
import org.jetbrains.kotlin.com.intellij.util.lang.JavaVersion;
import org.jetbrains.kotlin.com.intellij.util.system.CpuArch;

/* JADX INFO: loaded from: classes3.dex */
public final class SystemInfo {

    @Deprecated
    public static final boolean IS_AT_LEAST_JAVA9;
    public static final String JAVA_RUNTIME_VERSION;
    public static final String JAVA_VENDOR;
    public static final String JAVA_VERSION;

    @Deprecated
    public static final boolean areSymLinksSupported;

    @Deprecated
    public static final boolean is32Bit;

    @Deprecated
    public static final boolean is64Bit;
    public static final boolean isAppleSystemMenu;
    public static final boolean isAzulJvm;
    public static final boolean isChromeOS;
    public static final boolean isFileSystemCaseSensitive;
    public static final boolean isFreeBSD;
    public static final boolean isGNOME;
    public static final boolean isI3;
    public static final boolean isIbmJvm;

    @Deprecated
    public static final boolean isIntel64;
    public static final boolean isJBSystemMenu;
    public static final boolean isJetBrainsJvm;
    public static final boolean isKDE;
    public static final boolean isLinux;
    public static final boolean isMac;

    @Deprecated
    public static final boolean isMacIntel64;
    public static final boolean isMacOSBigSur;
    public static final boolean isMacOSCatalina;
    public static final boolean isMacOSElCapitan;
    public static final boolean isMacOSHighSierra;

    @Deprecated
    public static final boolean isMacOSLeopard;

    @Deprecated
    public static final boolean isMacOSLion;

    @Deprecated
    public static final boolean isMacOSMavericks;
    public static final boolean isMacOSMojave;
    public static final boolean isMacOSMonterey;

    @Deprecated
    public static final boolean isMacOSMountainLion;
    public static final boolean isMacOSSierra;

    @Deprecated
    public static final boolean isMacOSSnowLeopard;

    @Deprecated
    public static final boolean isMacOSTiger;
    public static final boolean isMacOSYosemite;
    public static final boolean isMacSystemMenu;
    public static final boolean isOracleJvm;
    public static final boolean isSolaris;
    public static final boolean isUnix;
    public static final boolean isWayland;
    public static final boolean isWin10OrNewer;

    @Deprecated
    public static final boolean isWin2kOrNewer;
    public static final boolean isWin7OrNewer;
    public static final boolean isWin8OrNewer;

    @Deprecated
    public static final boolean isWinVistaOrNewer;
    public static final boolean isWindows;
    public static final boolean isXWindow;
    public static final boolean isXfce;
    private static final NotNullLazyValue<Boolean> ourHasXdgMime;
    private static final NotNullLazyValue<Boolean> ourHasXdgOpen;
    public static final String OS_NAME = SystemInfoRt.OS_NAME;
    public static final String OS_VERSION = SystemInfoRt.OS_VERSION;
    public static final String OS_ARCH = System.getProperty(SystemProperties.OS_ARCH);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 5 || i == 7) ? 2 : 3];
        if (i == 1 || i == 3 || i == 5 || i == 7) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/SystemInfo";
        } else {
            objArr[0] = "version";
        }
        if (i == 1) {
            objArr[1] = "getMacOSMajorVersion";
        } else if (i == 3) {
            objArr[1] = "getMacOSVersionCode";
        } else if (i == 5) {
            objArr[1] = "getMacOSMajorVersionCode";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/SystemInfo";
        } else {
            objArr[1] = "getMacOSMinorVersionCode";
        }
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 7:
                break;
            case 2:
                objArr[2] = "getMacOSVersionCode";
                break;
            case 4:
                objArr[2] = "getMacOSMajorVersionCode";
                break;
            case 6:
                objArr[2] = "getMacOSMinorVersionCode";
                break;
            case 8:
                objArr[2] = "getMacOSVersionParts";
                break;
            default:
                objArr[2] = "isOsVersionAtLeast";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        String property = System.getProperty("java.version");
        JAVA_VERSION = property;
        JAVA_RUNTIME_VERSION = getRtVersion(property);
        String property2 = System.getProperty(SystemProperties.JAVA_VM_VENDOR, "Unknown");
        JAVA_VENDOR = property2;
        boolean z = SystemInfoRt.isWindows;
        isWindows = z;
        boolean z2 = SystemInfoRt.isMac;
        isMac = z2;
        boolean z3 = SystemInfoRt.isLinux;
        isLinux = z3;
        isFreeBSD = SystemInfoRt.isFreeBSD;
        isSolaris = SystemInfoRt.isSolaris;
        boolean z4 = SystemInfoRt.isUnix;
        isUnix = z4;
        boolean z5 = true;
        isChromeOS = z3 && isCrostini();
        isOracleJvm = Strings.indexOfIgnoreCase(property2, "Oracle", 0) >= 0;
        isIbmJvm = Strings.indexOfIgnoreCase(property2, "IBM", 0) >= 0;
        isAzulJvm = Strings.indexOfIgnoreCase(property2, "Azul", 0) >= 0;
        isJetBrainsJvm = Strings.indexOfIgnoreCase(property2, "JetBrains", 0) >= 0;
        isWin7OrNewer = z && isOsVersionAtLeast("6.1");
        isWin8OrNewer = z && isOsVersionAtLeast("6.2");
        isWin10OrNewer = z && isOsVersionAtLeast("10.0");
        boolean z6 = SystemInfoRt.isXWindow;
        isXWindow = z6;
        if (z6) {
            isWayland = System.getenv("WAYLAND_DISPLAY") != null;
            String str = System.getenv("XDG_CURRENT_DESKTOP");
            String str2 = System.getenv("GDMSESSION");
            boolean z7 = (str != null && str.contains("GNOME")) || (str2 != null && str2.contains("gnome"));
            isGNOME = z7;
            boolean z8 = !z7 && ((str != null && str.contains("KDE")) || System.getenv("KDE_FULL_SESSION") != null);
            isKDE = z8;
            boolean z9 = (z7 || z8 || str == null || !str.contains("XFCE")) ? false : true;
            isXfce = z9;
            isI3 = (z7 || z8 || z9 || str == null || !str.contains("i3")) ? false : true;
        } else {
            isI3 = false;
            isXfce = false;
            isKDE = false;
            isGNOME = false;
            isWayland = false;
        }
        boolean z10 = z2 && Boolean.getBoolean("apple.laf.useScreenMenuBar");
        isAppleSystemMenu = z10;
        boolean z11 = z2 && Boolean.getBoolean("jbScreenMenuBar.enabled");
        isJBSystemMenu = z11;
        isMacSystemMenu = z10 || z11;
        isFileSystemCaseSensitive = SystemInfoRt.isFileSystemCaseSensitive;
        ourHasXdgOpen = PathExecLazyValue.create("xdg-open");
        ourHasXdgMime = PathExecLazyValue.create("xdg-mime");
        isMacOSYosemite = z2 && isOsVersionAtLeast("10.10");
        isMacOSElCapitan = z2 && isOsVersionAtLeast("10.11");
        isMacOSSierra = z2 && isOsVersionAtLeast("10.12");
        isMacOSHighSierra = z2 && isOsVersionAtLeast("10.13");
        isMacOSMojave = z2 && isOsVersionAtLeast("10.14");
        isMacOSCatalina = z2 && isOsVersionAtLeast("10.15");
        isMacOSBigSur = z2 && isOsVersionAtLeast("10.16");
        isMacOSMonterey = z2 && isOsVersionAtLeast("12.0");
        IS_AT_LEAST_JAVA9 = JavaVersion.current().feature >= 9;
        is32Bit = CpuArch.CURRENT.width == 32;
        is64Bit = CpuArch.CURRENT.width == 64;
        boolean zIsIntel64 = CpuArch.isIntel64();
        isIntel64 = zIsIntel64;
        isMacIntel64 = z2 && zIsIntel64;
        isMacOSTiger = z2;
        isMacOSLeopard = z2;
        isMacOSSnowLeopard = z2;
        isMacOSLion = z2;
        isMacOSMountainLion = z2;
        isMacOSMavericks = z2;
        isWin2kOrNewer = z;
        isWinVistaOrNewer = z;
        if (!z4 && !z) {
            z5 = false;
        }
        areSymLinksSupported = z5;
    }

    private static String getRtVersion(String str) {
        String property = System.getProperty(SystemProperties.JAVA_RUNTIME_VERSION);
        return Character.isDigit(property.charAt(0)) ? property : str;
    }

    private static boolean isCrostini() {
        return new File("/dev/.cros_milestone").exists();
    }

    public static boolean isOsVersionAtLeast(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return StringUtil.compareVersionNumbers(OS_VERSION, str) >= 0;
    }
}
