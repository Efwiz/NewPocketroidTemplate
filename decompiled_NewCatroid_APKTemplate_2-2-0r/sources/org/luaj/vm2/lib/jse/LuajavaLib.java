package org.luaj.vm2.lib.jse;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

/* JADX INFO: loaded from: classes6.dex */
public class LuajavaLib extends VarArgFunction {
    static final int BINDCLASS = 1;
    static final int CREATEPROXY = 4;
    static final int INIT = 0;
    static final int LOADLIB = 5;
    static final int METHOD_MODIFIERS_VARARGS = 128;
    static final String[] NAMES = {"bindClass", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, PsiKeyword.NEW, "createProxy", "loadLib"};
    static final int NEW = 3;
    static final int NEWINSTANCE = 2;
    static /* synthetic */ Class class$java$lang$Class;

    private static final class ProxyInvocationHandler implements InvocationHandler {
        private final LuaValue lobj;

        private ProxyInvocationHandler(LuaValue luaValue) {
            this.lobj = luaValue;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            LuaValue[] luaValueArr;
            LuaValue luaValue = this.lobj.get(method.getName());
            if (luaValue.isnil()) {
                return null;
            }
            int i = 0;
            boolean z = (method.getModifiers() & 128) != 0;
            int length = objArr != null ? objArr.length : 0;
            if (z) {
                int i2 = length - 1;
                Object obj2 = objArr[i2];
                int length2 = Array.getLength(obj2);
                luaValueArr = new LuaValue[i2 + length2];
                for (int i3 = 0; i3 < i2; i3++) {
                    luaValueArr[i3] = CoerceJavaToLua.coerce(objArr[i3]);
                }
                while (i < length2) {
                    luaValueArr[i + i2] = CoerceJavaToLua.coerce(Array.get(obj2, i));
                    i++;
                }
            } else {
                luaValueArr = new LuaValue[length];
                while (i < length) {
                    luaValueArr[i] = CoerceJavaToLua.coerce(objArr[i]);
                    i++;
                }
            }
            return CoerceLuaToJava.coerce(luaValue.invoke(luaValueArr).arg1(), method.getReturnType());
        }
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    protected Class classForName(String str) throws ClassNotFoundException {
        return Class.forName(str, true, ClassLoader.getSystemClassLoader());
    }

    @Override // org.luaj.vm2.lib.VarArgFunction, org.luaj.vm2.lib.LibFunction, org.luaj.vm2.LuaValue
    public Varargs invoke(Varargs varargs) {
        Class clsClassForName;
        try {
            int i = this.opcode;
            if (i == 0) {
                LuaValue luaValueArg = varargs.arg(2);
                LuaTable luaTable = new LuaTable();
                bind(luaTable, getClass(), NAMES, 1);
                luaValueArg.set("luajava", luaTable);
                luaValueArg.get("package").get("loaded").set("luajava", luaTable);
                return luaTable;
            }
            if (i == 1) {
                return JavaClass.forClass(classForName(varargs.checkjstring(1)));
            }
            if (i == 2 || i == 3) {
                LuaValue luaValueCheckvalue = varargs.checkvalue(1);
                if (this.opcode == 2) {
                    clsClassForName = classForName(luaValueCheckvalue.tojstring());
                } else {
                    Class clsClass$ = class$java$lang$Class;
                    if (clsClass$ == null) {
                        clsClass$ = class$(CommonClassNames.JAVA_LANG_CLASS);
                        class$java$lang$Class = clsClass$;
                    }
                    clsClassForName = (Class) luaValueCheckvalue.checkuserdata(clsClass$);
                }
                return JavaClass.forClass(clsClassForName).getConstructor().invoke(varargs.subargs(2));
            }
            int i2 = 0;
            if (i != 4) {
                if (i != 5) {
                    throw new LuaError(new StringBuffer("not yet supported: ").append(this).toString());
                }
                String strCheckjstring = varargs.checkjstring(1);
                String strCheckjstring2 = varargs.checkjstring(2);
                Class clsClassForName2 = classForName(strCheckjstring);
                Object objInvoke = clsClassForName2.getMethod(strCheckjstring2, new Class[0]).invoke(clsClassForName2, new Object[0]);
                return objInvoke instanceof LuaValue ? (LuaValue) objInvoke : NIL;
            }
            int iNarg = varargs.narg();
            int i3 = iNarg - 1;
            if (i3 <= 0) {
                throw new LuaError("no interfaces");
            }
            LuaTable luaTableChecktable = varargs.checktable(iNarg);
            Class[] clsArr = new Class[i3];
            while (i2 < i3) {
                int i4 = i2 + 1;
                clsArr[i2] = classForName(varargs.checkjstring(i4));
                i2 = i4;
            }
            return LuaValue.userdataOf(Proxy.newProxyInstance(getClass().getClassLoader(), clsArr, new ProxyInvocationHandler(luaTableChecktable)));
        } catch (InvocationTargetException e) {
            throw new LuaError(e.getTargetException());
        } catch (LuaError e2) {
            throw e2;
        } catch (Exception e3) {
            throw new LuaError(e3);
        }
    }
}
