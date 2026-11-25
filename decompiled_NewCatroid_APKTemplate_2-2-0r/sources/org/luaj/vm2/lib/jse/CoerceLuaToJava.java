package org.luaj.vm2.lib.jse;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;

/* JADX INFO: loaded from: classes6.dex */
public class CoerceLuaToJava {
    static final Map COERCIONS;
    static int SCORE_NULL_VALUE = 16;
    static int SCORE_UNCOERCIBLE = 65536;
    static int SCORE_WRONG_TYPE = 256;
    static /* synthetic */ Class array$B;
    static /* synthetic */ Class class$java$lang$Boolean;
    static /* synthetic */ Class class$java$lang$Byte;
    static /* synthetic */ Class class$java$lang$Character;
    static /* synthetic */ Class class$java$lang$Double;
    static /* synthetic */ Class class$java$lang$Float;
    static /* synthetic */ Class class$java$lang$Integer;
    static /* synthetic */ Class class$java$lang$Long;
    static /* synthetic */ Class class$java$lang$Short;
    static /* synthetic */ Class class$java$lang$String;

    static final class ArrayCoercion implements Coercion {
        final Coercion componentCoercion;
        final Class componentType;

        public ArrayCoercion(Class cls) {
            this.componentType = cls;
            this.componentCoercion = CoerceLuaToJava.getCoercion(cls);
        }

        @Override // org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion
        public Object coerce(LuaValue luaValue) {
            int iType = luaValue.type();
            if (iType != 5) {
                if (iType != 7) {
                    return null;
                }
                return luaValue.touserdata();
            }
            int length = luaValue.length();
            Object objNewInstance = Array.newInstance((Class<?>) this.componentType, length);
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                Array.set(objNewInstance, i, this.componentCoercion.coerce(luaValue.get(i2)));
                i = i2;
            }
            return objNewInstance;
        }

        @Override // org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion
        public int score(LuaValue luaValue) {
            int iType = luaValue.type();
            if (iType == 0) {
                return CoerceLuaToJava.SCORE_NULL_VALUE;
            }
            if (iType != 5) {
                return iType != 7 ? CoerceLuaToJava.SCORE_UNCOERCIBLE : CoerceLuaToJava.inheritanceLevels(this.componentType, luaValue.touserdata().getClass().getComponentType());
            }
            if (luaValue.length() == 0) {
                return 0;
            }
            return this.componentCoercion.score(luaValue.get(1));
        }

        public String toString() {
            return new StringBuffer("ArrayCoercion(").append(this.componentType.getName()).append(Constant.AFTER_QUTO).toString();
        }
    }

    static final class BoolCoercion implements Coercion {
        BoolCoercion() {
        }

        @Override // org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion
        public Object coerce(LuaValue luaValue) {
            return luaValue.toboolean() ? Boolean.TRUE : Boolean.FALSE;
        }

        @Override // org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion
        public int score(LuaValue luaValue) {
            return luaValue.type() != 1 ? 1 : 0;
        }

        public String toString() {
            return "BoolCoercion()";
        }
    }

    interface Coercion {
        Object coerce(LuaValue luaValue);

        int score(LuaValue luaValue);
    }

    static final class NumericCoercion implements Coercion {
        static final int TARGET_TYPE_BYTE = 0;
        static final int TARGET_TYPE_CHAR = 1;
        static final int TARGET_TYPE_DOUBLE = 6;
        static final int TARGET_TYPE_FLOAT = 5;
        static final int TARGET_TYPE_INT = 3;
        static final int TARGET_TYPE_LONG = 4;
        static final int TARGET_TYPE_SHORT = 2;
        static final String[] TYPE_NAMES = {"byte", PsiKeyword.CHAR, "short", "int", "long", "float", "double"};
        final int targetType;

        NumericCoercion(int i) {
            this.targetType = i;
        }

        @Override // org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion
        public Object coerce(LuaValue luaValue) {
            switch (this.targetType) {
                case 0:
                    return new Byte((byte) luaValue.toint());
                case 1:
                    return new Character((char) luaValue.toint());
                case 2:
                    return new Short((short) luaValue.toint());
                case 3:
                    return new Integer(luaValue.toint());
                case 4:
                    return new Long((long) luaValue.todouble());
                case 5:
                    return new Float((float) luaValue.todouble());
                case 6:
                    return new Double(luaValue.todouble());
                default:
                    return null;
            }
        }

        @Override // org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion
        public int score(LuaValue luaValue) {
            int i = 0;
            int i2 = 4;
            if (luaValue.type() == 4) {
                luaValue = luaValue.tonumber();
                if (luaValue.isnil()) {
                    return CoerceLuaToJava.SCORE_UNCOERCIBLE;
                }
            } else {
                i2 = 0;
            }
            if (!luaValue.isint()) {
                if (!luaValue.isnumber()) {
                    return CoerceLuaToJava.SCORE_UNCOERCIBLE;
                }
                switch (this.targetType) {
                    case 0:
                        return CoerceLuaToJava.SCORE_WRONG_TYPE;
                    case 1:
                        return CoerceLuaToJava.SCORE_WRONG_TYPE;
                    case 2:
                        return CoerceLuaToJava.SCORE_WRONG_TYPE;
                    case 3:
                        return CoerceLuaToJava.SCORE_WRONG_TYPE;
                    case 4:
                        double d = luaValue.todouble();
                        return i2 + (d != ((double) ((long) d)) ? CoerceLuaToJava.SCORE_WRONG_TYPE : 0);
                    case 5:
                        double d2 = luaValue.todouble();
                        return i2 + (d2 != ((double) ((float) d2)) ? CoerceLuaToJava.SCORE_WRONG_TYPE : 0);
                    case 6:
                        double d3 = luaValue.todouble();
                        return i2 + ((d3 == ((double) ((long) d3)) || d3 == ((double) ((float) d3))) ? 1 : 0);
                    default:
                        return CoerceLuaToJava.SCORE_WRONG_TYPE;
                }
            }
            switch (this.targetType) {
                case 0:
                    int i3 = luaValue.toint();
                    return i2 + (i3 != ((byte) i3) ? CoerceLuaToJava.SCORE_WRONG_TYPE : 0);
                case 1:
                    int i4 = luaValue.toint();
                    if (i4 == ((byte) i4)) {
                        i = 1;
                    } else if (i4 != ((char) i4)) {
                        i = CoerceLuaToJava.SCORE_WRONG_TYPE;
                    }
                    return i2 + i;
                case 2:
                    int i5 = luaValue.toint();
                    if (i5 == ((byte) i5)) {
                        i = 1;
                    } else if (i5 != ((short) i5)) {
                        i = CoerceLuaToJava.SCORE_WRONG_TYPE;
                    }
                    return i2 + i;
                case 3:
                    int i6 = luaValue.toint();
                    if (i6 == ((byte) i6)) {
                        i = 2;
                    } else if (i6 == ((char) i6) || i6 == ((short) i6)) {
                        i = 1;
                    }
                    return i2 + i;
                case 4:
                    return i2 + 1;
                case 5:
                    return i2 + 1;
                case 6:
                    return i2 + 2;
                default:
                    return CoerceLuaToJava.SCORE_WRONG_TYPE;
            }
        }

        public String toString() {
            return new StringBuffer("NumericCoercion(").append(TYPE_NAMES[this.targetType]).append(Constant.AFTER_QUTO).toString();
        }
    }

    static final class ObjectCoercion implements Coercion {
        final Class targetType;

        ObjectCoercion(Class cls) {
            this.targetType = cls;
        }

        @Override // org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion
        public Object coerce(LuaValue luaValue) {
            int iType = luaValue.type();
            if (iType == 0) {
                return null;
            }
            if (iType == 1) {
                return luaValue.toboolean() ? Boolean.TRUE : Boolean.FALSE;
            }
            if (iType == 3) {
                return luaValue.isint() ? new Integer(luaValue.toint()) : new Double(luaValue.todouble());
            }
            if (iType != 4) {
                return iType != 7 ? luaValue : luaValue.optuserdata(this.targetType, (Object) null);
            }
            return luaValue.tojstring();
        }

        @Override // org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion
        public int score(LuaValue luaValue) {
            Class clsClass$;
            Class clsClass$2;
            Class clsClass$3;
            Class cls;
            LuaValue luaValue2;
            int iType = luaValue.type();
            if (iType == 0) {
                return CoerceLuaToJava.SCORE_NULL_VALUE;
            }
            if (iType == 1) {
                Class cls2 = this.targetType;
                if (CoerceLuaToJava.class$java$lang$Boolean == null) {
                    clsClass$ = CoerceLuaToJava.class$(CommonClassNames.JAVA_LANG_BOOLEAN);
                    CoerceLuaToJava.class$java$lang$Boolean = clsClass$;
                } else {
                    clsClass$ = CoerceLuaToJava.class$java$lang$Boolean;
                }
                return CoerceLuaToJava.inheritanceLevels(cls2, clsClass$);
            }
            if (iType == 3) {
                Class cls3 = this.targetType;
                if (luaValue.isint()) {
                    if (CoerceLuaToJava.class$java$lang$Integer == null) {
                        clsClass$2 = CoerceLuaToJava.class$(CommonClassNames.JAVA_LANG_INTEGER);
                        CoerceLuaToJava.class$java$lang$Integer = clsClass$2;
                    } else {
                        clsClass$2 = CoerceLuaToJava.class$java$lang$Integer;
                    }
                } else if (CoerceLuaToJava.class$java$lang$Double == null) {
                    clsClass$2 = CoerceLuaToJava.class$(CommonClassNames.JAVA_LANG_DOUBLE);
                    CoerceLuaToJava.class$java$lang$Double = clsClass$2;
                } else {
                    clsClass$2 = CoerceLuaToJava.class$java$lang$Double;
                }
                return CoerceLuaToJava.inheritanceLevels(cls3, clsClass$2);
            }
            if (iType != 4) {
                if (iType != 7) {
                    cls = this.targetType;
                    luaValue2 = luaValue;
                } else {
                    cls = this.targetType;
                    luaValue2 = luaValue.touserdata();
                }
                return CoerceLuaToJava.inheritanceLevels(cls, luaValue2.getClass());
            }
            Class cls4 = this.targetType;
            if (CoerceLuaToJava.class$java$lang$String == null) {
                clsClass$3 = CoerceLuaToJava.class$(CommonClassNames.JAVA_LANG_STRING);
                CoerceLuaToJava.class$java$lang$String = clsClass$3;
            } else {
                clsClass$3 = CoerceLuaToJava.class$java$lang$String;
            }
            return CoerceLuaToJava.inheritanceLevels(cls4, clsClass$3);
        }

        public String toString() {
            return new StringBuffer("ObjectCoercion(").append(this.targetType.getName()).append(Constant.AFTER_QUTO).toString();
        }
    }

    static final class StringCoercion implements Coercion {
        public static final int TARGET_TYPE_BYTES = 1;
        public static final int TARGET_TYPE_STRING = 0;
        final int targetType;

        public StringCoercion(int i) {
            this.targetType = i;
        }

        @Override // org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion
        public Object coerce(LuaValue luaValue) {
            if (luaValue.isnil()) {
                return null;
            }
            if (this.targetType == 0) {
                return luaValue.tojstring();
            }
            LuaString luaStringCheckstring = luaValue.checkstring();
            int i = luaStringCheckstring.m_length;
            byte[] bArr = new byte[i];
            luaStringCheckstring.copyInto(0, bArr, 0, i);
            return bArr;
        }

        @Override // org.luaj.vm2.lib.jse.CoerceLuaToJava.Coercion
        public int score(LuaValue luaValue) {
            int iType = luaValue.type();
            if (iType == 0) {
                return CoerceLuaToJava.SCORE_NULL_VALUE;
            }
            if (iType != 4) {
                return this.targetType == 0 ? CoerceLuaToJava.SCORE_WRONG_TYPE : CoerceLuaToJava.SCORE_UNCOERCIBLE;
            }
            if (luaValue.checkstring().isValidUtf8()) {
                return this.targetType == 0 ? 0 : 1;
            }
            if (this.targetType == 1) {
                return 0;
            }
            return CoerceLuaToJava.SCORE_WRONG_TYPE;
        }

        public String toString() {
            return new StringBuffer("StringCoercion(").append(this.targetType == 0 ? CommonClassNames.JAVA_LANG_STRING_SHORT : "byte[]").append(Constant.AFTER_QUTO).toString();
        }
    }

    static {
        Map mapSynchronizedMap = Collections.synchronizedMap(new HashMap());
        COERCIONS = mapSynchronizedMap;
        BoolCoercion boolCoercion = new BoolCoercion();
        NumericCoercion numericCoercion = new NumericCoercion(0);
        NumericCoercion numericCoercion2 = new NumericCoercion(1);
        NumericCoercion numericCoercion3 = new NumericCoercion(2);
        NumericCoercion numericCoercion4 = new NumericCoercion(3);
        NumericCoercion numericCoercion5 = new NumericCoercion(4);
        NumericCoercion numericCoercion6 = new NumericCoercion(5);
        NumericCoercion numericCoercion7 = new NumericCoercion(6);
        StringCoercion stringCoercion = new StringCoercion(0);
        StringCoercion stringCoercion2 = new StringCoercion(1);
        mapSynchronizedMap.put(Boolean.TYPE, boolCoercion);
        Class clsClass$ = class$java$lang$Boolean;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_BOOLEAN);
            class$java$lang$Boolean = clsClass$;
        }
        mapSynchronizedMap.put(clsClass$, boolCoercion);
        mapSynchronizedMap.put(Byte.TYPE, numericCoercion);
        Class clsClass$2 = class$java$lang$Byte;
        if (clsClass$2 == null) {
            clsClass$2 = class$(CommonClassNames.JAVA_LANG_BYTE);
            class$java$lang$Byte = clsClass$2;
        }
        mapSynchronizedMap.put(clsClass$2, numericCoercion);
        mapSynchronizedMap.put(Character.TYPE, numericCoercion2);
        Class clsClass$3 = class$java$lang$Character;
        if (clsClass$3 == null) {
            clsClass$3 = class$(CommonClassNames.JAVA_LANG_CHARACTER);
            class$java$lang$Character = clsClass$3;
        }
        mapSynchronizedMap.put(clsClass$3, numericCoercion2);
        mapSynchronizedMap.put(Short.TYPE, numericCoercion3);
        Class clsClass$4 = class$java$lang$Short;
        if (clsClass$4 == null) {
            clsClass$4 = class$(CommonClassNames.JAVA_LANG_SHORT);
            class$java$lang$Short = clsClass$4;
        }
        mapSynchronizedMap.put(clsClass$4, numericCoercion3);
        mapSynchronizedMap.put(Integer.TYPE, numericCoercion4);
        Class clsClass$5 = class$java$lang$Integer;
        if (clsClass$5 == null) {
            clsClass$5 = class$(CommonClassNames.JAVA_LANG_INTEGER);
            class$java$lang$Integer = clsClass$5;
        }
        mapSynchronizedMap.put(clsClass$5, numericCoercion4);
        mapSynchronizedMap.put(Long.TYPE, numericCoercion5);
        Class clsClass$6 = class$java$lang$Long;
        if (clsClass$6 == null) {
            clsClass$6 = class$(CommonClassNames.JAVA_LANG_LONG);
            class$java$lang$Long = clsClass$6;
        }
        mapSynchronizedMap.put(clsClass$6, numericCoercion5);
        mapSynchronizedMap.put(Float.TYPE, numericCoercion6);
        Class clsClass$7 = class$java$lang$Float;
        if (clsClass$7 == null) {
            clsClass$7 = class$(CommonClassNames.JAVA_LANG_FLOAT);
            class$java$lang$Float = clsClass$7;
        }
        mapSynchronizedMap.put(clsClass$7, numericCoercion6);
        mapSynchronizedMap.put(Double.TYPE, numericCoercion7);
        Class clsClass$8 = class$java$lang$Double;
        if (clsClass$8 == null) {
            clsClass$8 = class$(CommonClassNames.JAVA_LANG_DOUBLE);
            class$java$lang$Double = clsClass$8;
        }
        mapSynchronizedMap.put(clsClass$8, numericCoercion7);
        Class clsClass$9 = class$java$lang$String;
        if (clsClass$9 == null) {
            clsClass$9 = class$(CommonClassNames.JAVA_LANG_STRING);
            class$java$lang$String = clsClass$9;
        }
        mapSynchronizedMap.put(clsClass$9, stringCoercion);
        Class clsClass$10 = array$B;
        if (clsClass$10 == null) {
            clsClass$10 = class$("[B");
            array$B = clsClass$10;
        }
        mapSynchronizedMap.put(clsClass$10, stringCoercion2);
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static Object coerce(LuaValue luaValue, Class cls) {
        return getCoercion(cls).coerce(luaValue);
    }

    static Coercion getCoercion(Class cls) {
        Coercion objectCoercion;
        Map map = COERCIONS;
        Coercion coercion = (Coercion) map.get(cls);
        if (coercion != null) {
            return coercion;
        }
        if (cls.isArray()) {
            cls.getComponentType();
            objectCoercion = new ArrayCoercion(cls.getComponentType());
        } else {
            objectCoercion = new ObjectCoercion(cls);
        }
        map.put(cls, objectCoercion);
        return objectCoercion;
    }

    static final int inheritanceLevels(Class cls, Class cls2) {
        if (cls2 == null) {
            return SCORE_UNCOERCIBLE;
        }
        if (cls == cls2) {
            return 0;
        }
        int iMin = Math.min(SCORE_UNCOERCIBLE, inheritanceLevels(cls, cls2.getSuperclass()) + 1);
        for (Class<?> cls3 : cls2.getInterfaces()) {
            iMin = Math.min(iMin, inheritanceLevels(cls, cls3) + 1);
        }
        return iMin;
    }
}
