package org.jetbrains.kotlin.com.intellij.util.system;

import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes3.dex */
public enum CpuArch {
    X86(32),
    X86_64(64),
    ARM64(64),
    OTHER(0),
    UNKNOWN(0);

    public static final CpuArch CURRENT;
    public final int width;

    static {
        CpuArch cpuArch = X86;
        CpuArch cpuArch2 = X86_64;
        CpuArch cpuArch3 = ARM64;
        CpuArch cpuArch4 = OTHER;
        CpuArch cpuArch5 = UNKNOWN;
        String property = System.getProperty(SystemProperties.OS_ARCH);
        if ("x86_64".equals(property) || "amd64".equals(property)) {
            CURRENT = cpuArch2;
            return;
        }
        if ("i386".equals(property) || "x86".equals(property)) {
            CURRENT = cpuArch;
            return;
        }
        if ("aarch64".equals(property) || "arm64".equals(property)) {
            CURRENT = cpuArch3;
        } else if (property == null || property.trim().isEmpty()) {
            CURRENT = cpuArch5;
        } else {
            CURRENT = cpuArch4;
        }
    }

    CpuArch(int i) {
        if (i == 0) {
            try {
                i = Integer.parseInt(System.getProperty("sun.arch.data.model", "32"));
            } catch (NumberFormatException unused) {
            }
        }
        this.width = i;
    }

    public static boolean isIntel64() {
        return CURRENT == X86_64;
    }

    public static boolean isArm64() {
        return CURRENT == ARM64;
    }

    public static boolean is32Bit() {
        return CURRENT.width == 32;
    }
}
