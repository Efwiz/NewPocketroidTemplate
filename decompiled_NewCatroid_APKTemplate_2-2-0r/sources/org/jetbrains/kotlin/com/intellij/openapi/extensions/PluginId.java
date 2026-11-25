package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* JADX INFO: loaded from: classes3.dex */
public final class PluginId implements Comparable<PluginId> {
    public static final PluginId[] EMPTY_ARRAY = new PluginId[0];
    private static final ConcurrentHashMap<String, PluginId> registeredIds = new ConcurrentHashMap<>();
    private final String idString;

    /* JADX WARN: Code duplicated, block: B:24:0x0038  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 4 || i == 6) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId";
        } else if (i == 3) {
            objArr[0] = "idStrings";
        } else if (i == 4 || i == 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId";
        } else if (i != 7) {
            objArr[0] = "idString";
        } else {
            objArr[0] = "o";
        }
        if (i == 1) {
            objArr[1] = "getId";
        } else if (i == 4) {
            objArr[1] = "getRegisteredIds";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId";
        } else {
            objArr[1] = "getIdString";
        }
        switch (i) {
            case 1:
            case 4:
            case 6:
                break;
            case 2:
            case 3:
                objArr[2] = "findId";
                break;
            case 5:
                objArr[2] = "<init>";
                break;
            case 7:
                objArr[2] = "compareTo";
                break;
            default:
                objArr[2] = "getId";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static /* synthetic */ PluginId $r8$lambda$L9T34YLEq65ibOxOEqYVjnrhU6Q(String str) {
        return new PluginId(str);
    }

    public static PluginId getId(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        PluginId pluginIdComputeIfAbsent = registeredIds.computeIfAbsent(str, new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PluginId.$r8$lambda$L9T34YLEq65ibOxOEqYVjnrhU6Q((String) obj);
            }
        });
        if (pluginIdComputeIfAbsent == null) {
            $$$reportNull$$$0(1);
        }
        return pluginIdComputeIfAbsent;
    }

    private PluginId(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        this.idString = str;
    }

    public String getIdString() {
        String str = this.idString;
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PluginId) {
            return this.idString.equals(((PluginId) obj).idString);
        }
        return false;
    }

    public int hashCode() {
        return this.idString.hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(PluginId pluginId) {
        if (pluginId == null) {
            $$$reportNull$$$0(7);
        }
        return this.idString.compareTo(pluginId.idString);
    }

    public String toString() {
        return this.idString;
    }
}
