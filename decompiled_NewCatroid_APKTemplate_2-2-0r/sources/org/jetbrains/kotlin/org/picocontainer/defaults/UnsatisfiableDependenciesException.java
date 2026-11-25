package org.jetbrains.kotlin.org.picocontainer.defaults;

import java.util.Set;
import org.jetbrains.kotlin.org.picocontainer.PicoContainer;
import org.jetbrains.kotlin.org.picocontainer.PicoIntrospectionException;

/* JADX INFO: loaded from: classes4.dex */
public final class UnsatisfiableDependenciesException extends PicoIntrospectionException {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "componentImplementation", "org/jetbrains/kotlin/org/picocontainer/defaults/UnsatisfiableDependenciesException", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnsatisfiableDependenciesException(Class<?> cls, Class<?> cls2, Set set, PicoContainer picoContainer) {
        super(cls.getName() + " has unsatisfied dependency: " + cls2 + " among unsatisfiable dependencies: " + set + " where " + picoContainer + " was the leaf container being asked for dependencies.");
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
    }
}
