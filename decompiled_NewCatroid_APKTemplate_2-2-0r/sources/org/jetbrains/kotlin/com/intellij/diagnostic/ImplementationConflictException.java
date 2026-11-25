package org.jetbrains.kotlin.com.intellij.diagnostic;

import java.util.HashSet;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.ide.plugins.cl.PluginAwareClassLoader;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;

/* JADX INFO: loaded from: classes3.dex */
public final class ImplementationConflictException extends RuntimeException {
    private boolean myConflictWithPlatform;
    private final Set<PluginId> myConflictingPluginIds;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "implementationObjects";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/diagnostic/ImplementationConflictException";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/diagnostic/ImplementationConflictException";
        } else {
            objArr[1] = "getConflictingPluginIds";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImplementationConflictException(String str, Throwable th, Object... objArr) {
        super(str, th);
        if (objArr == null) {
            $$$reportNull$$$0(0);
        }
        this.myConflictingPluginIds = new HashSet();
        for (Object obj : objArr) {
            Object classLoader = obj.getClass().getClassLoader();
            if (classLoader instanceof PluginAwareClassLoader) {
                this.myConflictingPluginIds.add(((PluginAwareClassLoader) classLoader).getPluginId());
            } else {
                this.myConflictWithPlatform = true;
            }
        }
    }
}
