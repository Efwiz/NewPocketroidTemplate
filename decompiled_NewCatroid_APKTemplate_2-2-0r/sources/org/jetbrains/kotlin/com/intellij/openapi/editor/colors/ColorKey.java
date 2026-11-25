package org.jetbrains.kotlin.com.intellij.openapi.editor.colors;

import java.awt.Color;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;

/* JADX INFO: loaded from: classes3.dex */
public final class ColorKey implements Comparable<ColorKey> {
    private final Color myDefaultColor;
    private final String myExternalName;
    private final ColorKey myFallbackColorKey;
    private static final Logger LOG = Logger.getInstance((Class<?>) ColorKey.class);
    public static final Key<Function<ColorKey, Color>> FUNCTION_KEY = Key.create("COLOR_KEY_FUNCTION");
    private static final ConcurrentMap<String, ColorKey> ourRegistry = new ConcurrentHashMap();

    /* JADX WARN: Code duplicated, block: B:30:0x0047  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 7 || i == 8 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 7 || i == 8 || i == 10 || i == 11) ? 2 : 3];
        if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/colors/ColorKey";
        } else if (i == 4) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else if (i == 7 || i == 8 || i == 10 || i == 11) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/editor/colors/ColorKey";
        } else {
            objArr[0] = "externalName";
        }
        if (i == 2) {
            objArr[1] = "find";
        } else if (i == 3) {
            objArr[1] = "getExternalName";
        } else if (i == 7 || i == 8) {
            objArr[1] = "createColorKeyWithFallback";
        } else if (i == 10 || i == 11) {
            objArr[1] = "createColorKey";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/editor/colors/ColorKey";
        }
        switch (i) {
            case 1:
                objArr[2] = "find";
                break;
            case 2:
            case 3:
            case 7:
            case 8:
            case 10:
            case 11:
                break;
            case 4:
                objArr[2] = "compareTo";
                break;
            case 5:
            case 9:
                objArr[2] = "createColorKey";
                break;
            case 6:
                objArr[2] = "createColorKeyWithFallback";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 7 && i != 8 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private ColorKey(String str, Color color, ColorKey colorKey) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myExternalName = str;
        this.myDefaultColor = color;
        this.myFallbackColorKey = colorKey;
    }

    public static ColorKey find(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        ColorKey colorKeyComputeIfAbsent = ourRegistry.computeIfAbsent(str, new Function() { // from class: org.jetbrains.kotlin.com.intellij.openapi.editor.colors.ColorKey$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ColorKey.lambda$find$0((String) obj);
            }
        });
        if (colorKeyComputeIfAbsent == null) {
            $$$reportNull$$$0(2);
        }
        return colorKeyComputeIfAbsent;
    }

    static /* synthetic */ ColorKey lambda$find$0(String str) {
        return new ColorKey(str, null, null);
    }

    public String toString() {
        return this.myExternalName;
    }

    @Override // java.lang.Comparable
    public int compareTo(ColorKey colorKey) {
        if (colorKey == null) {
            $$$reportNull$$$0(4);
        }
        return this.myExternalName.compareTo(colorKey.myExternalName);
    }

    public static ColorKey createColorKey(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return find(str);
    }

    public int hashCode() {
        return this.myExternalName.hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ColorKey) && this.myExternalName.equals(((ColorKey) obj).myExternalName);
    }
}
