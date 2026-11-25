package org.luaj.vm2.lib.jse;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.luaj.vm2.LuaDouble;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;

/* JADX INFO: loaded from: classes6.dex */
public class CoerceJavaToLua {
    static final Map COERCIONS;
    static /* synthetic */ Class array$B;
    static final Coercion arrayCoercion;
    static /* synthetic */ Class class$java$lang$Boolean;
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Character;
    static /* synthetic */ Class class$java$lang$Class;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Short;
    static /* synthetic */ Class class$java$lang$String;
    static final Coercion instanceCoercion;
    static final Coercion luaCoercion;

    private static final class ArrayCoercion implements Coercion {
        private ArrayCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
        public LuaValue coerce(Object obj) {
            return new JavaArray(obj);
        }
    }

    private static final class BoolCoercion implements Coercion {
        private BoolCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
        public LuaValue coerce(Object obj) {
            return ((Boolean) obj).booleanValue() ? LuaValue.TRUE : LuaValue.FALSE;
        }
    }

    private static final class BytesCoercion implements Coercion {
        private BytesCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
        public LuaValue coerce(Object obj) {
            return LuaValue.valueOf((byte[]) obj);
        }
    }

    private static final class CharCoercion implements Coercion {
        private CharCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
        public LuaValue coerce(Object obj) {
            return LuaInteger.valueOf((int) ((Character) obj).charValue());
        }
    }

    private static final class ClassCoercion implements Coercion {
        private ClassCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
        public LuaValue coerce(Object obj) {
            return JavaClass.forClass((Class) obj);
        }
    }

    interface Coercion {
        LuaValue coerce(Object obj);
    }

    private static final class DoubleCoercion implements Coercion {
        private DoubleCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
        public LuaValue coerce(Object obj) {
            return LuaDouble.valueOf(((Number) obj).doubleValue());
        }
    }

    private static final class InstanceCoercion implements Coercion {
        private InstanceCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
        public LuaValue coerce(Object obj) {
            return new JavaInstance(obj);
        }
    }

    private static final class IntCoercion implements Coercion {
        private IntCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
        public LuaValue coerce(Object obj) {
            return LuaInteger.valueOf(((Number) obj).intValue());
        }
    }

    private static final class LuaCoercion implements Coercion {
        private LuaCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
        public LuaValue coerce(Object obj) {
            return (LuaValue) obj;
        }
    }

    private static final class StringCoercion implements Coercion {
        private StringCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
        public LuaValue coerce(Object obj) {
            return LuaString.valueOf(obj.toString());
        }
    }

    static {
        HashMap map = new HashMap();
        COERCIONS = map;
        BoolCoercion boolCoercion = new BoolCoercion();
        IntCoercion intCoercion = new IntCoercion();
        CharCoercion charCoercion = new CharCoercion();
        DoubleCoercion doubleCoercion = new DoubleCoercion();
        StringCoercion stringCoercion = new StringCoercion();
        BytesCoercion bytesCoercion = new BytesCoercion();
        ClassCoercion classCoercion = new ClassCoercion();
        Class clsClass$ = class$java$lang$Boolean;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_BOOLEAN);
            class$java$lang$Boolean = clsClass$;
        }
        map.put(clsClass$, boolCoercion);
        Class clsClass$2 = class$java$lang$Byte;
        if (clsClass$2 == null) {
            clsClass$2 = class$(CommonClassNames.JAVA_LANG_BYTE);
            class$java$lang$Byte = clsClass$2;
        }
        map.put(clsClass$2, intCoercion);
        Class clsClass$3 = class$java$lang$Character;
        if (clsClass$3 == null) {
            clsClass$3 = class$(CommonClassNames.JAVA_LANG_CHARACTER);
            class$java$lang$Character = clsClass$3;
        }
        map.put(clsClass$3, charCoercion);
        Class clsClass$4 = class$java$lang$Short;
        if (clsClass$4 == null) {
            clsClass$4 = class$(CommonClassNames.JAVA_LANG_SHORT);
            class$java$lang$Short = clsClass$4;
        }
        map.put(clsClass$4, intCoercion);
        Class clsClass$5 = class$java$lang$Integer;
        if (clsClass$5 == null) {
            clsClass$5 = class$(CommonClassNames.JAVA_LANG_INTEGER);
            class$java$lang$Integer = clsClass$5;
        }
        map.put(clsClass$5, intCoercion);
        Class clsClass$6 = class$java$lang$Long;
        if (clsClass$6 == null) {
            clsClass$6 = class$(CommonClassNames.JAVA_LANG_LONG);
            class$java$lang$Long = clsClass$6;
        }
        map.put(clsClass$6, doubleCoercion);
        Class clsClass$7 = class$java$lang$Float;
        if (clsClass$7 == null) {
            clsClass$7 = class$(CommonClassNames.JAVA_LANG_FLOAT);
            class$java$lang$Float = clsClass$7;
        }
        map.put(clsClass$7, doubleCoercion);
        Class clsClass$8 = class$java$lang$Double;
        if (clsClass$8 == null) {
            clsClass$8 = class$(CommonClassNames.JAVA_LANG_DOUBLE);
            class$java$lang$Double = clsClass$8;
        }
        map.put(clsClass$8, doubleCoercion);
        Class clsClass$9 = class$java$lang$String;
        if (clsClass$9 == null) {
            clsClass$9 = class$(CommonClassNames.JAVA_LANG_STRING);
            class$java$lang$String = clsClass$9;
        }
        map.put(clsClass$9, stringCoercion);
        Class clsClass$10 = array$B;
        if (clsClass$10 == null) {
            clsClass$10 = class$("[B");
            array$B = clsClass$10;
        }
        map.put(clsClass$10, bytesCoercion);
        Class clsClass$11 = class$java$lang$Class;
        if (clsClass$11 == null) {
            clsClass$11 = class$(CommonClassNames.JAVA_LANG_CLASS);
            class$java$lang$Class = clsClass$11;
        }
        map.put(clsClass$11, classCoercion);
        instanceCoercion = new InstanceCoercion();
        arrayCoercion = new ArrayCoercion();
        luaCoercion = new LuaCoercion();
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static LuaValue coerce(Object obj) {
        if (obj == null) {
            return LuaValue.NIL;
        }
        Class<?> cls = obj.getClass();
        Map map = COERCIONS;
        Coercion coercion = (Coercion) map.get(cls);
        if (coercion == null) {
            if (cls.isArray()) {
                coercion = arrayCoercion;
            } else {
                coercion = obj instanceof LuaValue ? luaCoercion : instanceCoercion;
            }
            map.put(cls, coercion);
        }
        return coercion.coerce(obj);
    }
}
