package org.jetbrains.kotlin.js.config;

/* JADX INFO: loaded from: classes5.dex */
public enum EcmaVersion {
    v3,
    v5;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/config/EcmaVersion", "defaultVersion"));
    }

    public static EcmaVersion defaultVersion() {
        EcmaVersion ecmaVersion = v5;
        if (ecmaVersion == null) {
            $$$reportNull$$$0(0);
        }
        return ecmaVersion;
    }
}
