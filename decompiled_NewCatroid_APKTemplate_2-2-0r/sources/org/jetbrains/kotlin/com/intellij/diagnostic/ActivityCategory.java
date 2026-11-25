package org.jetbrains.kotlin.com.intellij.diagnostic;

/* JADX INFO: loaded from: classes3.dex */
public enum ActivityCategory {
    DEFAULT("item"),
    MAIN("item"),
    PRELOAD_ACTIVITY("preloadActivity"),
    APP_OPTIONS_TOP_HIT_PROVIDER("appOptionsTopHitProvider"),
    PROJECT_OPTIONS_TOP_HIT_PROVIDER("projectOptionsTopHitProvider"),
    APP_COMPONENT("appComponents"),
    PROJECT_COMPONENT("projectComponents"),
    MODULE_COMPONENT("moduleComponents"),
    APP_SERVICE("appServices"),
    PROJECT_SERVICE("projectServices"),
    MODULE_SERVICE("moduleServices"),
    APP_EXTENSION("appExtensions"),
    PROJECT_EXTENSION("projectExtensions"),
    MODULE_EXTENSION("moduleExtensions"),
    PROJECT_OPEN_HANDLER("openHandler"),
    POST_STARTUP_ACTIVITY("projectPostStartupActivity"),
    GC("GC"),
    REOPENING_EDITOR("reopeningEditor"),
    SERVICE_WAITING("serviceWaiting"),
    GRADLE_IMPORT("importingProject");

    private final String jsonName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "jsonName";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/diagnostic/ActivityCategory";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/diagnostic/ActivityCategory";
        } else {
            objArr[1] = "getJsonName";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    ActivityCategory(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.jsonName = str;
    }
}
