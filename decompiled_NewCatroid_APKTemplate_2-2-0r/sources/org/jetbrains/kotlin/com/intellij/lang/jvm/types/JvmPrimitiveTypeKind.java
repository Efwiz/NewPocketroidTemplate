package org.jetbrains.kotlin.com.intellij.lang.jvm.types;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes3.dex */
public final class JvmPrimitiveTypeKind {
    public static final JvmPrimitiveTypeKind BOOLEAN;
    public static final JvmPrimitiveTypeKind BYTE;
    public static final JvmPrimitiveTypeKind CHAR;
    public static final JvmPrimitiveTypeKind DOUBLE;
    public static final JvmPrimitiveTypeKind FLOAT;
    public static final JvmPrimitiveTypeKind INT;
    public static final JvmPrimitiveTypeKind LONG;
    public static final JvmPrimitiveTypeKind SHORT;
    public static final JvmPrimitiveTypeKind VOID;
    private static final Map<String, JvmPrimitiveTypeKind> ourFqnToKind;
    private static final Map<String, JvmPrimitiveTypeKind> ourNameToKind;
    private final String myBinaryName;
    private final String myBoxedFqn;
    private final String myName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "boxedFqn";
                break;
            case 2:
                objArr[0] = "binaryName";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/lang/jvm/types/JvmPrimitiveTypeKind";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        if (i == 3) {
            objArr[1] = "getName";
        } else if (i == 4) {
            objArr[1] = "getBoxedFqn";
        } else if (i == 5) {
            objArr[1] = "getBinaryName";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/lang/jvm/types/JvmPrimitiveTypeKind";
        } else {
            objArr[1] = "getBoxedFqns";
        }
        if (i != 3 && i != 4 && i != 5 && i != 6) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind = new JvmPrimitiveTypeKind("boolean", CommonClassNames.JAVA_LANG_BOOLEAN, "Z");
        BOOLEAN = jvmPrimitiveTypeKind;
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind2 = new JvmPrimitiveTypeKind("byte", CommonClassNames.JAVA_LANG_BYTE, "B");
        BYTE = jvmPrimitiveTypeKind2;
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind3 = new JvmPrimitiveTypeKind(PsiKeyword.CHAR, CommonClassNames.JAVA_LANG_CHARACTER, "C");
        CHAR = jvmPrimitiveTypeKind3;
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind4 = new JvmPrimitiveTypeKind("double", CommonClassNames.JAVA_LANG_DOUBLE, "D");
        DOUBLE = jvmPrimitiveTypeKind4;
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind5 = new JvmPrimitiveTypeKind("float", CommonClassNames.JAVA_LANG_FLOAT, "F");
        FLOAT = jvmPrimitiveTypeKind5;
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind6 = new JvmPrimitiveTypeKind("int", CommonClassNames.JAVA_LANG_INTEGER, "I");
        INT = jvmPrimitiveTypeKind6;
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind7 = new JvmPrimitiveTypeKind("long", CommonClassNames.JAVA_LANG_LONG, "J");
        LONG = jvmPrimitiveTypeKind7;
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind8 = new JvmPrimitiveTypeKind("short", CommonClassNames.JAVA_LANG_SHORT, ExifInterface.LATITUDE_SOUTH);
        SHORT = jvmPrimitiveTypeKind8;
        JvmPrimitiveTypeKind jvmPrimitiveTypeKind9 = new JvmPrimitiveTypeKind(PsiKeyword.VOID, CommonClassNames.JAVA_LANG_VOID, ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        VOID = jvmPrimitiveTypeKind9;
        JvmPrimitiveTypeKind[] jvmPrimitiveTypeKindArr = {jvmPrimitiveTypeKind, jvmPrimitiveTypeKind2, jvmPrimitiveTypeKind3, jvmPrimitiveTypeKind4, jvmPrimitiveTypeKind5, jvmPrimitiveTypeKind6, jvmPrimitiveTypeKind7, jvmPrimitiveTypeKind8, jvmPrimitiveTypeKind9};
        HashMap map = new HashMap(9);
        HashMap map2 = new HashMap(9);
        for (int i = 0; i < 9; i++) {
            JvmPrimitiveTypeKind jvmPrimitiveTypeKind10 = jvmPrimitiveTypeKindArr[i];
            map.put(jvmPrimitiveTypeKind10.getName(), jvmPrimitiveTypeKind10);
            map2.put(jvmPrimitiveTypeKind10.getBoxedFqn(), jvmPrimitiveTypeKind10);
        }
        ourNameToKind = map;
        ourFqnToKind = map2;
    }

    private JvmPrimitiveTypeKind(String str, String str2, String str3) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (str2 == null) {
            $$$reportNull$$$0(1);
        }
        if (str3 == null) {
            $$$reportNull$$$0(2);
        }
        this.myName = str;
        this.myBoxedFqn = str2;
        this.myBinaryName = str3;
    }

    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str;
    }

    public String getBoxedFqn() {
        String str = this.myBoxedFqn;
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return str;
    }

    public String getBinaryName() {
        String str = this.myBinaryName;
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return str;
    }

    public static JvmPrimitiveTypeKind getKindByName(String str) {
        return ourNameToKind.get(str);
    }

    public static Collection<String> getBoxedFqns() {
        Collection<String> collectionUnmodifiableCollection = Collections.unmodifiableCollection(ourFqnToKind.keySet());
        if (collectionUnmodifiableCollection == null) {
            $$$reportNull$$$0(6);
        }
        return collectionUnmodifiableCollection;
    }
}
