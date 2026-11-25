package org.jetbrains.kotlin.com.intellij.openapi.util.registry;

/* JADX INFO: loaded from: classes3.dex */
public interface RegistryValueListener {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "value";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/registry/RegistryValueListener";
        if (i != 1) {
            objArr[2] = "beforeValueChanged";
        } else {
            objArr[2] = "afterValueChanged";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    default void afterValueChanged(RegistryValue registryValue) {
        if (registryValue == null) {
            $$$reportNull$$$0(1);
        }
    }

    default void beforeValueChanged(RegistryValue registryValue) {
        if (registryValue == null) {
            $$$reportNull$$$0(0);
        }
    }
}
