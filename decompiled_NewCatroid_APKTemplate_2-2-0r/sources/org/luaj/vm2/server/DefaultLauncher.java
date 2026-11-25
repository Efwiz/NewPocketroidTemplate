package org.luaj.vm2.server;

import java.io.InputStream;
import java.io.Reader;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

/* JADX INFO: loaded from: classes6.dex */
public class DefaultLauncher implements Launcher {
    protected Globals g = JsePlatform.standardGlobals();

    private Object[] launchChunk(LuaValue luaValue, Object[] objArr) {
        int length = objArr.length;
        LuaValue[] luaValueArr = new LuaValue[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            luaValueArr[i2] = CoerceJavaToLua.coerce(objArr[i2]);
        }
        Varargs varargsInvoke = luaValue.invoke(LuaValue.varargsOf(luaValueArr));
        int iNarg = varargsInvoke.narg();
        Object[] objArr2 = new Object[iNarg];
        while (i < iNarg) {
            int i3 = i + 1;
            LuaValue luaValueArg = varargsInvoke.arg(i3);
            int iType = luaValueArg.type();
            if (iType == -2) {
                objArr2[i] = Integer.valueOf(luaValueArg.toint());
            } else if (iType == 7) {
                objArr2[i] = luaValueArg.touserdata();
            } else if (iType == 0) {
                objArr2[i] = null;
            } else if (iType == 1) {
                objArr2[i] = Boolean.valueOf(luaValueArg.toboolean());
            } else if (iType == 3) {
                objArr2[i] = Double.valueOf(luaValueArg.todouble());
            } else if (iType != 4) {
                objArr2[i] = luaValueArg;
            } else {
                objArr2[i] = luaValueArg.tojstring();
            }
            i = i3;
        }
        return objArr2;
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // org.luaj.vm2.server.Launcher
    public Object[] launch(InputStream inputStream, Object[] objArr) {
        Globals globals = this.g;
        return launchChunk(globals.load(inputStream, "main", "bt", globals), objArr);
    }

    @Override // org.luaj.vm2.server.Launcher
    public Object[] launch(Reader reader, Object[] objArr) {
        return launchChunk(this.g.load(reader, "main"), objArr);
    }

    @Override // org.luaj.vm2.server.Launcher
    public Object[] launch(String str, Object[] objArr) {
        return launchChunk(this.g.load(str, "main"), objArr);
    }
}
