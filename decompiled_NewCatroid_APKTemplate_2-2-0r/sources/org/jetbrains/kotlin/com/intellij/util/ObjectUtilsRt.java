package org.jetbrains.kotlin.com.intellij.util;

/* JADX INFO: loaded from: classes3.dex */
public class ObjectUtilsRt {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "o", "org/jetbrains/kotlin/com/intellij/util/ObjectUtilsRt", "reachabilityFence"));
    }

    public static void reachabilityFence(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(0);
        }
    }
}
