package org.jetbrains.kotlin.com.intellij.util;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfoRt;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;

/* JADX INFO: loaded from: classes3.dex */
public final class EnvironmentUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) EnvironmentUtil.class);
    private static final AtomicReference<CompletableFuture<Map<String, String>>> ourEnvGetter = new AtomicReference<>();

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:10:0x0016 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:19:0x0029 A[FALL_THROUGH] */
    /* JADX WARN: Code duplicated, block: B:31:0x004f  */
    /* JADX WARN: Code duplicated, block: B:34:0x005e  */
    /* JADX WARN: Code duplicated, block: B:61:0x00c4  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1 && i != 5 && i != 7 && i != 9) {
            switch (i) {
                default:
                    switch (i) {
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                            break;
                        default:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        }
        if (i != 1 && i != 5 && i != 7 && i != 9) {
            switch (i) {
                default:
                    switch (i) {
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                            break;
                        default:
                            i2 = 2;
                            break;
                    }
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 3;
        }
        Object[] objArr = new Object[i2];
        if (i == 1) {
            objArr[0] = "activity";
        } else if (i == 5) {
            objArr[0] = "name";
        } else if (i == 7) {
            objArr[0] = "shellScript";
        } else if (i != 9) {
            switch (i) {
                case 11:
                    objArr[0] = "text";
                    break;
                case 12:
                case 13:
                    objArr[0] = "process";
                    break;
                case 14:
                case 15:
                    objArr[0] = "env";
                    break;
                case 16:
                    objArr[0] = "charset";
                    break;
                default:
                    switch (i) {
                        case 18:
                            objArr[0] = "env";
                            break;
                        case 19:
                        case 20:
                            objArr[0] = "envs";
                            break;
                        case 21:
                            objArr[0] = "parentEnv";
                            break;
                        case 22:
                            objArr[0] = "lines";
                            break;
                        default:
                            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/EnvironmentUtil";
                            break;
                    }
                    break;
            }
        } else {
            objArr[0] = "lines";
        }
        switch (i) {
            case 1:
            case 5:
            case 7:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/EnvironmentUtil";
                break;
            case 2:
            case 3:
            case 4:
                objArr[1] = "getSystemEnv";
                break;
            case 6:
                objArr[1] = "getShellEnv";
                break;
            case 8:
                objArr[1] = "buildShellProcessCommand";
                break;
            case 10:
                objArr[1] = "parseEnv";
                break;
            case 17:
                objArr[1] = "setLocaleEnv";
                break;
            default:
                objArr[1] = "getEnvironmentMap";
                break;
        }
        if (i == 1) {
            objArr[2] = "loadEnvironment";
        } else if (i == 5) {
            objArr[2] = "getValue";
        } else if (i == 7) {
            objArr[2] = "buildShellProcessCommand";
        } else if (i != 9) {
            switch (i) {
                case 11:
                    objArr[2] = "parseEnv";
                    break;
                case 12:
                    objArr[2] = "waitAndTerminateAfter";
                    break;
                case 13:
                    objArr[2] = "waitFor";
                    break;
                case 14:
                    objArr[2] = "setCharsetVar";
                    break;
                case 15:
                case 16:
                    objArr[2] = "setLocaleEnv";
                    break;
                default:
                    switch (i) {
                        case 18:
                            objArr[2] = "isCharsetVarDefined";
                            break;
                        case 19:
                        case 20:
                        case 21:
                            objArr[2] = "inlineParentOccurrences";
                            break;
                        case 22:
                            objArr[2] = "testParser";
                            break;
                    }
                    break;
            }
        } else {
            objArr[2] = "parseEnv";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 5 && i != 7 && i != 9) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    break;
                default:
                    switch (i) {
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                            break;
                        default:
                            throw new IllegalStateException(str2);
                    }
                    break;
            }
        }
        throw new IllegalArgumentException(str2);
    }

    private EnvironmentUtil() {
    }

    public static Map<String, String> getEnvironmentMap() {
        AtomicReference<CompletableFuture<Map<String, String>>> atomicReference = ourEnvGetter;
        CompletableFuture<Map<String, String>> completableFutureCompletedFuture = atomicReference.get();
        if (completableFutureCompletedFuture == null) {
            completableFutureCompletedFuture = CompletableFuture.completedFuture(getSystemEnv());
            if (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, null, completableFutureCompletedFuture)) {
                completableFutureCompletedFuture = atomicReference.get();
            }
        }
        try {
            Map<String, String> mapJoin = completableFutureCompletedFuture.join();
            if (mapJoin == null) {
                $$$reportNull$$$0(0);
            }
            return mapJoin;
        } catch (Throwable th) {
            throw new AssertionError(th);
        }
    }

    private static Map<String, String> getSystemEnv() {
        if (SystemInfoRt.isWindows) {
            Map<String, String> mapUnmodifiableMap = Collections.unmodifiableMap(CollectionFactory.createCaseInsensitiveStringMap(System.getenv()));
            if (mapUnmodifiableMap == null) {
                $$$reportNull$$$0(2);
            }
            return mapUnmodifiableMap;
        }
        if (SystemInfoRt.isXWindow) {
            Map<String, String> mapUnmodifiableMap2 = System.getenv();
            if (mapUnmodifiableMap2.containsKey("DESKTOP_STARTUP_ID")) {
                HashMap map = new HashMap(mapUnmodifiableMap2);
                map.remove("DESKTOP_STARTUP_ID");
                mapUnmodifiableMap2 = Collections.unmodifiableMap(map);
            }
            if (mapUnmodifiableMap2 == null) {
                $$$reportNull$$$0(3);
            }
            return mapUnmodifiableMap2;
        }
        Map<String, String> map2 = System.getenv();
        if (map2 == null) {
            $$$reportNull$$$0(4);
        }
        return map2;
    }

    public static String getValue(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return getEnvironmentMap().get(str);
    }
}
