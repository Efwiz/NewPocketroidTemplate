package com.thoughtworks.xstream.core.util;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/Primitives.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public final class Primitives {
    static /* synthetic */ Class class$java$lang$Boolean;
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Character;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Short;
    static /* synthetic */ Class class$java$lang$Void;
    private static final Map BOX = new HashMap();
    private static final Map UNBOX = new HashMap();
    private static final Map NAMED_PRIMITIVE = new HashMap();
    private static final Map REPRESENTING_CHAR = new HashMap();

    static {
        Class[][] clsArr = new Class[9][];
        Class[] clsArr2 = new Class[2];
        clsArr2[0] = Byte.TYPE;
        Class clsClass$ = class$java$lang$Byte;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_BYTE);
            class$java$lang$Byte = clsClass$;
        }
        clsArr2[1] = clsClass$;
        clsArr[0] = clsArr2;
        Class[] clsArr3 = new Class[2];
        clsArr3[0] = Character.TYPE;
        Class clsClass$2 = class$java$lang$Character;
        if (clsClass$2 == null) {
            clsClass$2 = class$(CommonClassNames.JAVA_LANG_CHARACTER);
            class$java$lang$Character = clsClass$2;
        }
        clsArr3[1] = clsClass$2;
        clsArr[1] = clsArr3;
        Class[] clsArr4 = new Class[2];
        clsArr4[0] = Short.TYPE;
        Class clsClass$3 = class$java$lang$Short;
        if (clsClass$3 == null) {
            clsClass$3 = class$(CommonClassNames.JAVA_LANG_SHORT);
            class$java$lang$Short = clsClass$3;
        }
        clsArr4[1] = clsClass$3;
        clsArr[2] = clsArr4;
        Class[] clsArr5 = new Class[2];
        clsArr5[0] = Integer.TYPE;
        Class clsClass$4 = class$java$lang$Integer;
        if (clsClass$4 == null) {
            clsClass$4 = class$(CommonClassNames.JAVA_LANG_INTEGER);
            class$java$lang$Integer = clsClass$4;
        }
        clsArr5[1] = clsClass$4;
        clsArr[3] = clsArr5;
        Class[] clsArr6 = new Class[2];
        clsArr6[0] = Long.TYPE;
        Class clsClass$5 = class$java$lang$Long;
        if (clsClass$5 == null) {
            clsClass$5 = class$(CommonClassNames.JAVA_LANG_LONG);
            class$java$lang$Long = clsClass$5;
        }
        clsArr6[1] = clsClass$5;
        clsArr[4] = clsArr6;
        Class[] clsArr7 = new Class[2];
        clsArr7[0] = Float.TYPE;
        Class clsClass$6 = class$java$lang$Float;
        if (clsClass$6 == null) {
            clsClass$6 = class$(CommonClassNames.JAVA_LANG_FLOAT);
            class$java$lang$Float = clsClass$6;
        }
        clsArr7[1] = clsClass$6;
        clsArr[5] = clsArr7;
        Class[] clsArr8 = new Class[2];
        clsArr8[0] = Double.TYPE;
        Class clsClass$7 = class$java$lang$Double;
        if (clsClass$7 == null) {
            clsClass$7 = class$(CommonClassNames.JAVA_LANG_DOUBLE);
            class$java$lang$Double = clsClass$7;
        }
        clsArr8[1] = clsClass$7;
        clsArr[6] = clsArr8;
        Class[] clsArr9 = new Class[2];
        clsArr9[0] = Boolean.TYPE;
        Class clsClass$8 = class$java$lang$Boolean;
        if (clsClass$8 == null) {
            clsClass$8 = class$(CommonClassNames.JAVA_LANG_BOOLEAN);
            class$java$lang$Boolean = clsClass$8;
        }
        clsArr9[1] = clsClass$8;
        clsArr[7] = clsArr9;
        Class[] clsArr10 = new Class[2];
        clsArr10[0] = Void.TYPE;
        Class clsClass$9 = class$java$lang$Void;
        if (clsClass$9 == null) {
            clsClass$9 = class$(CommonClassNames.JAVA_LANG_VOID);
            class$java$lang$Void = clsClass$9;
        }
        clsArr10[1] = clsClass$9;
        clsArr[8] = clsArr10;
        Character[] chArr = {new Character(Util.C_BYTE), new Character(Util.C_CHAR), new Character(Util.C_SHORT), new Character('I'), new Character(Util.C_LONG), new Character(Util.C_FLOAT), new Character(Util.C_DOUBLE), new Character('Z'), null};
        for (int i = 0; i < 9; i++) {
            Class[] clsArr11 = clsArr[i];
            Class cls = clsArr11[0];
            Class cls2 = clsArr11[1];
            BOX.put(cls, cls2);
            UNBOX.put(cls2, cls);
            NAMED_PRIMITIVE.put(cls.getName(), cls);
            REPRESENTING_CHAR.put(cls, chArr[i]);
        }
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static Class box(Class cls) {
        return (Class) BOX.get(cls);
    }

    public static Class unbox(Class cls) {
        return (Class) UNBOX.get(cls);
    }

    public static boolean isBoxed(Class cls) {
        return UNBOX.containsKey(cls);
    }

    public static Class primitiveType(String str) {
        return (Class) NAMED_PRIMITIVE.get(str);
    }

    public static char representingChar(Class cls) {
        Character ch = (Character) REPRESENTING_CHAR.get(cls);
        if (ch == null) {
            return (char) 0;
        }
        return ch.charValue();
    }
}
