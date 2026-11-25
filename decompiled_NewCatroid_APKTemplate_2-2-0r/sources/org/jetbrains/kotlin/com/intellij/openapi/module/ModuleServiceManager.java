package org.jetbrains.kotlin.com.intellij.openapi.module;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public final class ModuleServiceManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "module";
        } else {
            objArr[0] = "serviceClass";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/module/ModuleServiceManager";
        objArr[2] = "getService";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private ModuleServiceManager() {
    }

    public static <T> T getService(Module module, Class<T> cls) {
        if (module == null) {
            $$$reportNull$$$0(0);
        }
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        return (T) module.getService(cls);
    }
}
