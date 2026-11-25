package org.jetbrains.kotlin.com.intellij.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public final class PlatformUtils {
    private static final Set<String> COMMERCIAL_EDITIONS = new HashSet(Arrays.asList("idea", "AppCode", "CLion", "MobileIDE", "Python", "DataSpell", "Ruby", "PhpStorm", "WebStorm", "DataGrip", "Rider", "GoLand"));

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/PlatformUtils", "getPlatformPrefix"));
    }

    public static String getPlatformPrefix() {
        String platformPrefix = getPlatformPrefix("idea");
        if (platformPrefix == null) {
            $$$reportNull$$$0(0);
        }
        return platformPrefix;
    }

    public static String getPlatformPrefix(String str) {
        return System.getProperty("idea.platform.prefix", str);
    }

    public static boolean isIntelliJ() {
        return isIdeaUltimate() || isIdeaCommunity() || is("IdeaEdu");
    }

    public static boolean isIdeaUltimate() {
        return is("idea");
    }

    public static boolean isIdeaCommunity() {
        return is("Idea");
    }

    private static boolean is(String str) {
        return str.equals(getPlatformPrefix());
    }
}
